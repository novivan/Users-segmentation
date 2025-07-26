# Users-segmentation
---
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