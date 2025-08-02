package com.example.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    //конструктор должен быть публичный, 
    // чтоб для него спринг мог забиндить добавление нужных
    // ему полей и методов
    @Autowired
    public Controller(UserRepository UR, GroupRepository GR) {
        userRepository = UR;
        groupRepository = GR;
    }

    public void addUser() { //чисто чтоб юзеров накидать. В ТЗ такого нет даже
        userRepository.save(new User());
    }

    public Integer addGroup(String name, List<Integer> usersIncluded) {
        Group NG = new Group(name);
        groupRepository.save(NG);

        usersIncluded.stream()
            .map(userRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(user -> {
                user.addGroup(NG);
                userRepository.save(user);
                NG.addUser(user);
            });
        groupRepository.save(NG);
        return NG.getId();
    }

    public void deleteGroup(int id) {
        groupRepository.findById(id).ifPresent(group -> {
            for (User user : new HashSet<>(group.getUsers())) {
                user.deleteGroup(group);
                userRepository.save(user);
                //и на всякий:
                group.deleteUser(user);
            }
            //тоже на всякий:
            groupRepository.save(group);

            groupRepository.deleteById(id);
        });
    }

    public void distributeGroupRandomly(int id, int percentige) {
        if (percentige > 100) {
            distributeGroupRandomly(id, 100);
            return;
        }
        if (percentige < 0) {
            distributeGroupRandomly(id, 0);
            return;
        }
        groupRepository.findById(id).ifPresent(group -> {
            //сначала всех перетираем
            for (User user : group.getUsers()) {
                user.deleteGroup(group);
                userRepository.save(user);
            }
            group.getUsers().clear();
            groupRepository.save(group);
            //а потом рандом уже
            Random random = new Random();
            for (User user : userRepository.findAll()) {
                if (random.nextInt(100) < percentige) {
                    user.addGroup(group);
                    userRepository.save(user);
                    group.addUser(user);
                }
            }
            groupRepository.save(group);
        });
    }

    //тут сделаем APIшные ручки:
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/add_user")
    @Transactional
    public String add_user() {
        addUser();
        Long users_amount = userRepository.count();
        return "Добавлен новый пользователь. Теперь их " + users_amount.toString() + "\n";
    }

    @GetMapping("/get_groups")
    @Transactional
    public String get_groups() {
        Long groups_amount = groupRepository.count();
        Long users_amount = userRepository.count();
        String ret = "Всего: " + groups_amount.toString() + " групп и " + users_amount.toString() + " пользователей\n";
        for (Group gr : groupRepository.findAll()) {
            ret += gr.toString() + "\n";
        }
        return ret;
    }
    
    //Оба параметра передаются в URL, например: 
    // curl -X POST "http://localhost:8080/distribute_group_randomly?id=2&percentige=30"
    @PostMapping("/distribute_group_randomly")
    @Transactional
    public String distribute_group_randomly(@RequestParam int id, @RequestParam int percentige) {
        Integer ID = id;
        AtomicReference<String> ret = new AtomicReference<>(new String(" \"Группа с id = \" + ID.toString() + \" не была создана\""));
        groupRepository.findById(id).ifPresent(group -> {
            distributeGroupRandomly(id, percentige);
            ret.set("Группа \"" + group.getName() + "\" c номером(id) " + ID.toString() + " распределена случайным образом");  
        });
        return ret.get();
    }

    // same:
    // curl -X POST "http://localhost:8080/add_group?name=TypicalGroupName&usersIncluded=1&usersIncluded=3&usersIncluded=5&usersIncluded=7&usersIncluded=9"
    //проблема с громоздким перечислением массива но пока так
    @PostMapping("/add_group")
    @Transactional
    public String add_group(@RequestParam String name, @RequestParam ArrayList<Integer> usersIncluded) {
        Integer ID = addGroup(name, usersIncluded);
        return "Группа \"" + name + "\" с номером(id) " + ID.toString() + " создана";
    }

    //и тд
    @PostMapping("/delete_group")
    @Transactional
    public String delete_group(@RequestParam int id) {
        Integer ID = id;
        AtomicReference<String> ret = new AtomicReference<>(new String("Группу с id = " + ID.toString() + " нельзя удалить, тк она не была создана"));
        groupRepository.findById(id).ifPresent(group -> {
            deleteGroup(id);
            ret.set("Группа с id = " + ID.toString() + " удалена");  
        });
        return ret.get();
    }

    @PostMapping("/add_user_to_group")
    @Transactional
    public String add_user_to_group(@RequestParam int user_id, @RequestParam int group_id) {
        if (user_id > userRepository.count() | user_id <= 0) {
            return "Пользователя с таким id не существует";
        }
        Integer groupID = group_id;
        AtomicReference<String> ret = new AtomicReference<>(new String("Группы с таким id не существует"));
        groupRepository.findById(group_id).ifPresent(group -> {
            User user = userRepository.findById(user_id).get();
            Integer userId = user_id;
            group.addUser(user);
            groupRepository.save(group);
            user.addGroup(group);
            userRepository.save(user);
            ret.set("Пользователь с id = " + userId.toString() + " добавлен в группу с id = " + groupID.toString());  
        });
        return ret.get();
    }

    @PostMapping("/remove_user_from_group") 
    @Transactional
    public String remove_user_from_group(@RequestParam int user_id, @RequestParam int group_id) {
        if (user_id > userRepository.count() | user_id <= 0) {
            return "Пользователя с таким id не существует";
        }
        Integer groupID = group_id;
        AtomicReference<String> ret = new AtomicReference<>(new String("Группы с таким id не существует"));
        groupRepository.findById(group_id).ifPresent(group -> {
            User user = userRepository.findById(user_id).get();
            Integer userId = user_id;
            group.deleteUser(user);
            groupRepository.save(group);
            user.deleteGroup(group);
            userRepository.save(user);
            ret.set("Пользователь с id = " + userId.toString() + " удален из группы с id = " + groupID.toString());  
        });
        return ret.get();
    }
}
