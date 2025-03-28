# Internet shop prototype

Сервис представляет собой фронтэнд и бэкэнд интернет-магазина. Реализован вывод списка товаров, пагинация, поиск, сортировка, корзина заказов, страница с перечнем совершенных ранее заказов 
Сервис на данном этапе не поддерживает в полной мере многопользовательский режим, пользователи задаются через файл конфигурации.

Версия: Java 21

Зависимости: Spring MVC, Spring Data, Thymeleaf, Tomcat, Hibernate, Ehcache, Postgres, Maven, JUnit, Lombok, Testcontainers

Для запуска программы необходим Docker.
1) Перейдите в папку /intershop и выполните команду "mvnw clean package". Дождитесь сборки контейнера
2) Выполните команду "docker-compose up" 
3) После запуска контейнеров блог будет доступен по адресу http://localhost:8080.
   В случае конфликта портов внесите исправления в docker-compose.yaml.

Настройки в файлу properties:
application.user_id - активирует корзину пользователя c заданным id
application.items.perline - количество товаров, отображаемых в одном ряду
application.items.load.enabled - включение возможности загрузки картинок
application.items.load.images.directory: директория для хранения загруженных картинок

Схема базы данных приведена ниже:
![](\src\main\resources\schema.png)

