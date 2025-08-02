package com.example.app;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") //без этого пытается создать user, а это слово зарезервировано
public class User implements Comparable<User> {
    // ТУТ МЫ НЕ БУДЕМ ВЛАЗИТЬ В ПОЛЯ ГРУПП, ВСЕ ЭТО БУДЕТ ДЕЛАТЬ КОНТРОЛЛЕР
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToMany
    @JoinTable(
        name = "user_group",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
    )

    private Set<Group> groups = new HashSet<>();

    @Override
    public int compareTo(User other) {
        return Integer.compare(this.id, other.id);
    }
    
    //конструктор по умолчанию для JPA
    User() {}

    public int getId() {
        return id;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void deleteGroup(Group group) {
        groups.remove(group);
    }
}
