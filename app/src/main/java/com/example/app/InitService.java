package com.example.app;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitService implements ApplicationRunner {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final Controller controller;

    public InitService(UserRepository userRepository, GroupRepository groupRepository, Controller controller) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.controller = controller;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.count() != 0) {
            return;
        }

        for (int i = 0; i < 1000; ++i) {
            controller.addUser();
        }

        List <User> allUsers = userRepository.findAll();
        controller.addGroup("All Users", allUsers.stream()
                                    .map(User::getId)
                                    .collect(Collectors.toList())
        );

        controller.addGroup("Children", allUsers.stream().filter(u -> List.of(1, 2).contains(u.getId()))
                                    .map(User::getId)
                                    .collect(Collectors.toList())
        );

        controller.addGroup("Parents", allUsers.stream().filter(u -> List.of(3, 4, 5).contains(u.getId()))
                                    .map(User::getId)
                                    .collect(Collectors.toList())
        );

    }
}