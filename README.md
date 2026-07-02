# Hotel Management System (gp-solution)

RESTful API приложение для управления информацией об отелях. Проект выполнен в рамках технического задания.

## Основные возможности
- Получение списка всех отелей с краткой информацией.
- Получение подробной информации по конкретному отелю (включая адрес, контакты, удобства и время заезда).
- Поиск отелей по параметрам: название, бренд, город, страна, удобства.
- Создание новых отелей.
- Добавление удобств (amenities) к существующим отелям.
- Просмотр гистограмм по городам, брендам, странам и удобствам.

## Технологический стек
- **Java 21**
- **Spring Boot 3.3.4**
- **Spring Data JPA** (с использованием Specifications для поиска)
- **H2 Database** (In-memory)
- **Liquibase** (Миграции БД и тестовые данные)
- **MapStruct** (Маппинг сущностей в DTO)
- **Lombok**
- **SpringDoc OpenAPI** (Swagger UI)
- **Jakarta Validation** (Валидация входных данных)

## Запуск приложения
Приложение можно запустить из корня проекта с помощью Maven:

```bash
mvn spring-boot:run
```

Приложение будет доступно по адресу: `http://localhost:8092/property-view`

## Документация API и инструменты
После запуска приложения доступны следующие ссылки:

- **Swagger UI**: [http://localhost:8092/property-view/swagger-ui.html](http://localhost:8092/property-view/swagger-ui.html)
- **API Docs (JSON)**: [http://localhost:8092/property-view/api-docs](http://localhost:8092/property-view/api-docs)
- **H2 Console**: [http://localhost:8092/property-view/h2-console](http://localhost:8092/property-view/h2-console)
  - JDBC URL: `jdbc:h2:mem:hoteldb`
  - User: `sa`
  - Password: (пусто)

## Тестовые данные
При запуске приложения Liquibase автоматически создает схему и наполняет базу данных тестовыми данными.

## Тестирование
Для запуска тестов используйте команду:
```bash
mvn test
```
