# Social Media API

## Стэк:
Spring Boot, Spring Security, Hibernate, PostgreSQL, MinIO, Maven, Lombok.

## Функциональность:
RESTful API для социальной медиаплатформы.
Включает в себя следующие возможности:
- Для неавторизованных пользователей:
  - регистрация;
  - авторизация;
- Для авторизованных пользователей:
  - Создание, редактирование и удаление собственных постов;
  - Просмотр постов других пользователей;
  - Добавление и удаление друзей;
  - Просмотр ленты активности (последних постов) друзей и пользователей, на которых подписан авторизованный пользователь. Включает возможность сортировки по времени создания постов.

## Запуск:
Приложение использует MinIO для хранения изображений, прикрепленных к постам.
Инструкции по установке и сами установочные файлы можно найти на [официальном ресурсе](https://min.io/download).
После установки необходимо создать в MinIO bucket и secret key и 
ввести в application.properties актуальные параметры bucket.name, access.key, 
access.secret, url.

Приложение использует PostgreSQL для хранения сущностей. Перед запуском приложения необходимо
ввести в application.properties актуальные значения db.name, datasource.url, datasource.username,
datasource.password.
