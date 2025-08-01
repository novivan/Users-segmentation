package com.example.app;

import java.util.ArrayList;
import java.util.TreeSet;

public class Group {
    // ТУТ МЫ НЕ БУДЕМ ВЛАЗИТЬ В ПОЛЯ ЮЗЕРОВ, ВСЕ ЭТО БУДЕТ ДЕЛАТЬ КОНТРОЛЛЕР
    private static int counter = 0;

    private final int id;
    private String name;
    //private ArrayList<Integer> users_id;
    private TreeSet<Integer> users_id;

    Group(String Name) {
        id = ++counter;
        name = new String(Name);
        //users_id = new ArrayList<Integer>();
        users_id = new TreeSet<Integer>();
    }

    Group(String Name, ArrayList<Integer> Users) {
        id = ++counter;
        name = new String(Name);
        //users_id = new ArrayList<Integer>(Users);
        users_id = new TreeSet<Integer>();
        users_id.addAll(Users);
        // ну или: users_id = new TreeSet<Integer>(Users);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    } 

    public void setName(String newName) {
        name = new String(newName);
    }

    public TreeSet<Integer> getUsers() {
        return users_id;
    }

    public void addUser(int user_id) {
        users_id.add(user_id);
    }

    public void deleteUser(int user_id) { 
        users_id.remove(user_id);
        //Integer U_Id = user_id;
        //users_id.remove(U_Id);
    }

    public void setNewUsers(ArrayList<Integer> NU) {
        //users_id = new ArrayList<Integer>(NU);
        users_id.clear();
        users_id = new TreeSet<Integer>();
        users_id.addAll(NU);
    }

    public String toString() {  // в основном для дебага
        String ret = new String("");
        Integer ID = id;
        ret += "Группа номер " + ID.toString() + " под названием \"" + name + "\". Пользователи(id):\n";
        for (Integer usr: users_id) {
            ret += " -- " + usr.toString() + "\n";
        }
        ret += "\n";
        return ret;
    }
}
