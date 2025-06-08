# Internet shop prototype

Сервис представляет собой фронтэнд и бэкэнд интернет-магазина, а также прототип платежного сервиса. 
Реализована авторизация, вывод списка товаров, пагинация, поиск, сортировка, корзина заказов, страница с перечнем совершенных ранее заказов,
покупка товаров через внешний платежный сервис.
Бэкенд построен на реактивном стеке. Поддерживается кеширование товаров через БД Redis. Взаимодействие между магазином и
платежным сервисом построено на авторизации через Keycloak.   

Версия: Java 21

Зависимости: 
* Spring Boot 3.5.0 
* Spring Security
* Keycloak
* Spring WebFlux
* Thymeleaf
* Netty
* Spring Data R2DBC
* Postgres
* Redis
* Maven
* JUnit 5
* Testcontainers
* Lombok

Для запуска программы необходим Docker.
1) Перейдите в папку /intershop и выполните команду "mvnw clean package". Дождитесь сборки приложения.
2) Выполните команду "docker-compose up". 
3) После запуска контейнеров магазин будет доступен по адресу http://localhost:8082
   В случае конфликта портов внесите исправления в docker-compose.yaml.

По умолчанию доступны 3 пользователя со следующими username и паролем:
'admin'/'admin'; 'anna1980'/'anna1980'; 'boris1980'/'boris1980'

Настройки в файле application.yaml:
* application.items.perline - количество товаров, отображаемых в одном ряду
* application.items.load.enabled - включение возможности загрузки картинок
* application.items.load.images.directory: директория для хранения загруженных картинок
* shop.payment_id: идентификатор магазина внутри платежной системы

Загрузка картинок:
* после запуска приложения по адресу "/admin/items/download" доступна загрузка картинок в базу данных при условии, что
application.items.load.enabled установлен в true и задан каталог для хранения статических изображений.

Добавление пользователей: 
* приложение позволяет добавлять пользователей. Обратите внимание, что платежный сервис будет их видеть только при условии,
что в его БД содержится соответствующий client_id (можно добавить через соответствующий data.sql)  

Платежный сервис реализует следующий API:
[спецификация OpenApi](https://github.com/mrchcat/intershop_reactive_withRedisCache/blob/main/payservice/PayServiceOpenApi.yaml)

Схема базы данных основного приложения:
![](https://github.com/mrchcat/intershop_reactive_withRedisCache/blob/main/shop/src/main/resources/schema.png)

Схема базы данных платежного сервиса:
![](https://github.com/mrchcat/intershop_reactive_withRedisCache/blob/main/payservice/server/src/main/resources/schema.png)

Загрузка исходных данных как в БД магазина, так и в платежный сервис осуществляется скриптом при загрузке приложения. 
