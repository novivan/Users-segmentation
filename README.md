# Users-segmentation
Данное приложение хранит группы и пользователей в h2 базе данных, чтоб не терять данные между запусками и не тратить слишком много оперативной памяти
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
Все примеры запросов тут.
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
---
Если вы хотите заранее задать приложению конкретный порт, нужно в файле application.properties (в папке Users-segmentation/app/src/main/resources) добавить следующую строку:
```
server.port=8080
```
---
Swagger UI и OpenAPI можно открыть по адресам(как и все остальное, с поправкой на ваш порт):
Swagger
```
http://localhost:8080/swagger-ui.html
```
OpenAPI
```
http://localhost:8080/v3/api-docs
```

---
PS
Для того, чтоб приложение было легче тестировать руками (для оценки User Experience), изначально создается 1000 пользователей и 3 группы с ними. Если пользователю не нужны группы, их можно удалить через запросы. Если же это не понадобится уже никогда потом, или хочется убрать еще и 1000 пользователей, то можно просто удалить/закомментировать код из файла InintService.java и удалить перед сборкой папку data.