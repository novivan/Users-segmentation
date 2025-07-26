package com.example.app;

//импорты из stl:
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

//импорты из сторонних библиотек/фреймворковЖ
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//импорты из уже написанных классов:
// - 

@RestController
public class Controller {
    private ArrayList<Group> groups;
    private ArrayList<User> users;

    //конструктор должен быть публичный, 
    // чтоб для него спринг мог забиндить добавление нужных
    // ему полей и методов
    public Controller() {
        groups = new ArrayList<Group>();
        users = new ArrayList<User>();
    }

    

    @PostConstruct
    public void init() {
        for (int i = 0; i < 1000; ++i) {
            addUser();
        }

        ArrayList<Integer> allUsers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) allUsers.add(i);

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
            users.get(usrID.intValue() - 1).addGroup(NG.getId());
        }
        groups.add(NG);
    }

    public void printGroups() { // для дебага
        Integer groups_amount = groups.size();
        Integer users_amount = users.size();
        System.out.println("Всего: " + groups_amount.toString() + " групп и " + users_amount.toString() + " пользователей\n\n");
        for (Group gr: groups) {
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

    //тут должно быть что-то с Post'ом для addGroup

    @GetMapping("/get_groups")
    public String get_groups() {
        Integer groups_amount = groups.size();
        Integer users_amount = users.size();
        String ret = "Всего: " + groups_amount.toString() + " групп и " + users_amount.toString() + " пользователей\n";
        for (Group gr : groups) {
            ret += gr.toString() + "\n";
        }
        return ret;
    }
}
