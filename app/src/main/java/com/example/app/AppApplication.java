package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);


        /* 
        для работы с API Spring создает свой отдельный контроллер, 
        поэтому вот это все не влияет:
         
        Controller controller = new Controller();
        controller.addUser();
        controller.addUser();
        controller.addUser();
        controller.addUser();
        controller.addUser();
        
        controller.addGroup("All Users", new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5)));
        controller.addGroup("Children", new ArrayList<Integer>(Arrays.asList(3, 4, 5)));
        controller.addGroup("Adults", new ArrayList<Integer>(Arrays.asList(1, 2)));


        controller.printGroups(); // проверка


        Если я хочу сделать что-то такое в Spring'овом контроллере, мне нужно написать такой метод класса:
        
        @PostConstruct
        public void init() {
            // Здесь инициализация пользователей и групп
            addUser();
            addUser();
            addUser();
            addUser();
            addUser();

            ArrayList<Integer> allUsers = new ArrayList<>();
            for (int i = 1; i <= 5; i++) allUsers.add(i);

            addGroup("All Users", allUsers);
            addGroup("Children", new ArrayList<>(Arrays.asList(3, 4, 5)));
            addGroup("Adults", new ArrayList<>(Arrays.asList(1, 2)));
        }
        */
	}

}
