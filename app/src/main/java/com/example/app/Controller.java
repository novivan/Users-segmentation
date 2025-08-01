package com.example.app;

//импорты из stl:
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//import java.util.TreeSet;
import java.util.TreeMap;

//импорты из сторонних библиотек/фреймворковЖ

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.annotation.JsonTypeInfo;


@RestController
public class Controller {
    //private ArrayList<Group> groups;
    private TreeMap<Integer, Group> groups; // будем удалять, поэтому делаю дерево

    private ArrayList<User> users; // по тз удалять ничего не надо, поэтому будет ArrayList
    // добавляю в PostConstruct для тестирования

    //конструктор должен быть публичный, 
    // чтоб для него спринг мог забиндить добавление нужных
    // ему полей и методов
    public Controller() {
        groups = new TreeMap<Integer, Group>();
        users = new ArrayList<User>();
    }

    

    @PostConstruct //чисто чтоб юзеров накидать. В ТЗ такого нет даже
    public void init() {
        for (int i = 0; i < 1000; ++i) {
            addUser();
        }

        ArrayList<Integer> allUsers = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) allUsers.add(i);

        addGroup("All Users", allUsers);
        addGroup("Children", new ArrayList<>(Arrays.asList(3, 4, 5)));
        addGroup("Adults", new ArrayList<>(Arrays.asList(1, 2)));
    }

    public void addUser() { //чисто чтоб юзеров накидать. В ТЗ такого нет даже
        users.add(new User());
    }

    public void addGroup(String name, ArrayList<Integer> usersIncluded) {
        Group NG = new Group(name, usersIncluded);
        for (Integer usrID : usersIncluded) {
            if (usrID.intValue() - 1 < users.size() & usrID.intValue() >= 0) {
                users.get(usrID.intValue() - 1).addGroup(NG.getId());
            }
        }
        groups.put(NG.getId(), NG);
    }

    public void deleteGroup(int id) {
        Group GroupToDelete = groups.get(id);
        if (GroupToDelete == null) {
            return;
        }
        for (Integer usrID: GroupToDelete.getUsers()) {
            users.get(usrID.intValue() - 1).deleteGroup(id);
        }
        groups.remove(id);
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
        //сначала всех перетираем
        Group currentGroup = groups.get(id);
        for (Integer i : currentGroup.getUsers()) {
            User currentUser = users.get(i - 1);
            currentUser.deleteGroup(id);
            //currentGroup.deleteUser(i);
        }
        currentGroup.getUsers().clear();

        //а потом рандом уже
        Random random = new Random();
        for (int i = 0; i < users.size(); ++i) {
            int randomInt = random.nextInt(100);
            if (randomInt < percentige) {
                currentGroup.addUser(i + 1);
                users.get(i).addGroup(id);
            }
        }
    }

    public void printGroups() { // для дебага
        Integer groups_amount = groups.size();
        Integer users_amount = users.size();
        System.out.println("Всего: " + groups_amount.toString() + " групп и " + users_amount.toString() + " пользователей\n\n");
        for (Group gr: groups.values()) {
            System.out.println(gr.toString());
        }
    }

    //тут сделаем APIшные штуки:
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/add_user")
    public String add_user() {
        addUser();
        Integer users_amount = users.size();
        return "Добавлен новый пользователь. Теперь их " + users_amount.toString() + "\n";
    }

    @GetMapping("/get_groups")
    public String get_groups() {
        Integer groups_amount = groups.size();
        Integer users_amount = users.size();
        String ret = "Всего: " + groups_amount.toString() + " групп и " + users_amount.toString() + " пользователей\n";
        for (Group gr : groups.values()) {
            ret += gr.toString() + "\n";
        }
        return ret;
    }
    
    //Оба параметра передаются в URL, например: 
    // curl -X POST "http://localhost:8080/distribute_group_randomly?id=2&percentige=30"
    @PostMapping("/distribute_group_randomly")
    public String distribute_group_randomly(@RequestParam int id, @RequestParam int percentige) {
        Group currentGroup = groups.get(id);
        Integer ID = id;
        if (currentGroup == null) {
            return "Группа с id = " + ID.toString() + " не была создана";
        }
        distributeGroupRandomly(id, percentige);
        return "Группа \"" + groups.get(id).getName() + "\" c номером(id) " + ID.toString() + " распределена случайным образом";
    }

    // same:
    // curl -X POST "http://localhost:8080/add_group?name=TypicalGroupName&usersIncluded=1&usersIncluded=3&usersIncluded=5&usersIncluded=7&usersIncluded=9"
    //проблема с громоздким перечислением массива но пока так
    @PostMapping("/add_group")
    public String add_group(@RequestParam String name, @RequestParam ArrayList<Integer> usersIncluded) {
        addGroup(name, usersIncluded);

        Integer amount = groups.size();
        return "Группа \"" + name + "\" с номером(id) " + amount.toString() + " создана";
    }

    @PostMapping("/delete_group")
    public String delete_group(@RequestParam int id) {
        Group currentGroup = groups.get(id);
        Integer ID = id;
        if (currentGroup == null) {
            return "Группу с id = " + ID.toString() + " нельзя удалить, тк она не была создана";
        }
        deleteGroup(id);
        return "Группа с id = " + ID.toString() + " удалена";
    }
}
