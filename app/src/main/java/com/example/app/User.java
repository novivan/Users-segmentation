package com.example.app;

import java.util.ArrayList;
import java.util.TreeSet;


public class User {
    // ТУТ МЫ НЕ БУДЕМ ВЛАЗИТЬ В ПОЛЯ ГРУПП, ВСЕ ЭТО БУДЕТ ДЕЛАТЬ КОНТРОЛЛЕР
    private static int counter = 0;

    private final int id;
    //private ArrayList<Integer> groups; //id-шники
    private TreeSet<Integer> groups; //id-шники
    

    User() {
        id = ++counter;
        groups = new TreeSet<Integer>();
    }

    User(ArrayList<Integer> Groups) {
        id = ++counter;
        groups = new TreeSet<Integer>();
        groups.addAll(Groups);
    }

    public int getId() {
        return id;
    }

    public TreeSet<Integer> getGroups() {
        return groups;
    }

    public void addGroup(int gr_id) {
        groups.add(gr_id);
    }

    public void deleteGroup(int gr_id) { 
        groups.remove(gr_id);
    }

    public void setNewGroups(ArrayList<Integer> NG) {
        groups.clear();
        groups.addAll(NG);
    }

}
