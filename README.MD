# SpringAOP
Приложение реализует систему учета времени выполнения методов в приложении с использованием Spring AOP,
оно способно асинхронно логировать и анализировать данные о времени выполнения методов.

___


_by Ruslan Rodionov_

___

### Общее описание приложения:
- реализует CRUD методы для работы с сущностями Owner и Solutions;
- аннотации @TrackTime и @TrackAsyncTime, применяются к методам работы с демо-сущностями для отслеживания времени их выполнения;
- сервис TrackTimeService асинхронно сохраняет время работы методов, а так же реализует возможность получения этих данных;

---
### Струтура БД:

![img.png](data/solutions-owners.png)
![img.png](data/track_times.png)
___
### Запуск приложения:
Поднимаем контейнер Docker с БД из файла:
[docker-compose-spring-aop-db.yaml](docker-compose-spring-aop-db.yaml)

после запускаем приложение в IDE;

---
### API:
[spring-aop-api-docs.json](api/spring-aop-api-docs.json)   

--- 
###  Postman 
Для полного тестирования приложения предусмотрена коллекция Postman.
Запуск коллекции необходимо производить последовательно, так как тесты зависят от результатов выполнения предшествующих
[SpringAOP.postman_collection.json](postman/SpringAOP.postman_collection.json)
___
### _Техстэк:_
- #### Java 17
- #### Spring Boot
- #### PostgreSQL
- #### Maven
- #### Hibernate
- #### Mapstruct
- #### Lombok
- #### Slf4j
- #### OpenAPI
- #### MockMVC
- #### Docker
