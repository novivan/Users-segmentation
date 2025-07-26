package com.example.app;

import java.util.ArrayList;

public class Controller {
    private ArrayList<Group> groups;
    private ArrayList<User> users;

    Controller() {
        groups = new ArrayList<Group>();
        users = new ArrayList<User>();
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
        System.out.println("Всего: " + groups_amount.toString() + " групп и " + users_amount.toString() + " пользователей\n");
        for (Group gr: groups) {
            System.out.println(gr.toString());
        }
    }
}
