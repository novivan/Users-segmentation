# Users-segmentation
---
перед сборкой и запуском перейти в каталог проекта:
```
cd app
```
очистка target и сборка проекта заново:
```
mvn clean package  
```
(clean для очистки, package - сборки)

запуск SpringBoot приложения:
```
mvn spring-boot:run
```
---
Все примеры запросов пока будут тут.
Порт в запросах нужно указывать в соответствии с логом в консоли при запуске:
```
Tomcat initialized with port 8080
```
(примерно такое должно быть в логах)
1) проверка связи:
```
curl http://localhost:8080/hello 
```
2) добавить пользователя:
```
curl http://localhost:8080/add_user
```
3) посмотреть список и содержимое групп:
```
curl http://localhost:8080/get_groups
```
4) перераспределить группу (по id) рандомно так, чтобы каждый пользователь попал в нее с определенной вероятностью (percentiege)
```
curl -X POST "http://localhost:8080/distribute_group_randomly?id=2&percentige=5"
```
5) создать новую группу людей и перечислить их id (для добавления):
```
curl -X POST "http://localhost:8080/add_group?name=TypicalGroupName&usersIncluded=1&usersIncluded=3&usersIncluded=5&usersIncluded=7&usersIncluded=9"
```
6) удалить группу по id:
```
curl -X POST "http://localhost:8080/delete_group?id=2"
```
7) добавить пользователя в группу:
```
curl -X POST "http://localhost:8080/add_user_to_group?user_id=704&group_id=2"
```
8) удалить пользователя из группы:
```
curl -X POST "http://localhost:8080/remove_user_from_group?user_id=704&group_id=2"
```