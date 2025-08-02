package com.example.app;

import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "groups") //без этого пытается создать group, а это слово зарезервировано
public class Group {
    // ТУТ МЫ НЕ БУДЕМ ВЛАЗИТЬ В ПОЛЯ ЮЗЕРОВ, ВСЕ ЭТО БУДЕТ ДЕЛАТЬ КОНТРОЛЛЕР
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new HashSet<>();

    public Group() {}

    Group(String Name) {
        name = new String(Name);
    }

    Group(String Name, Set<User> Users) {
        name = new String(Name);
        users.clear();
        users = new HashSet<User>(Users);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    } 

    public void setName(String newName) {
        name = new String(newName);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void deleteUser(User user) { 
        users.remove(user);
    }
    
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Группа номер ").append(id)
        .append(" под названием \"").append(name)
        .append("\". Пользователи(id):\n");

        users.stream()
            .sorted(Comparator.comparingInt(User::getId))
            .forEach(user -> ret.append(" -- ").append(user.getId()).append("\n"));
        
        ret.append("\n");
        return ret.toString();
    }
}
