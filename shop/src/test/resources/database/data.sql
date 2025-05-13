INSERT INTO users (email,name,payment_id)
VALUES ('anna@mail.ru','Anna','db1673e9-bf97-4dfa-acdb-88a280d4d144'),('boris@mail.ru','Boris','73c474e3-264f-4d8d-8f5d-4f566eb341a0');


INSERT INTO carts (user_id)
VALUES (1),(2);

INSERT INTO items(article_number, name,description,picture_path,price,unit)
VALUES ('16763be4-6022-406e-a950-fcd5018633ca','Ветровка BRADLY Мода и стиль','Удобная осенняя и весенняя, она подходит для прохладных дней, а также для переходного сезона осень-зима-весна. Эта водоотталкивающая верхняя одежда непромокаемая и обладает ветрозащитными свойствами, а наличие мембраны позволяет отводить влагу и не потеть. Размерный ряд расширен до больших размеров, со съемным капюшоном, воротник стойка. Капюшон отстегивается, карманы на молнии, манжеты на кнопках.','images/16763be4-6022-406e-a950-fcd5018633ca.png',14200,'PIECE');

INSERT INTO items(article_number, name,description,picture_path,price,unit)
VALUES ('61f0c404-5cb3-11e7-907b-a6006ad3dba0','Блендер Centek CT-1343 White 600Вт, турбо режим, специальная заточка лезвий, легкая очистка, 2 скорости','Блендер Centek — ваш незаменимый помощник! Благодаря мощному двигателю и острым лезвиям, он легко измельчает фрукты, овощи, орехи и другие продукты. Не упустите возможность обновить свою кухню с блендером Centek! ','images/61f0c404-5cb3-11e7-907b-a6006ad3dba0.png',669,'PIECE');

INSERT INTO items(article_number, name,description,picture_path,price,unit)
VALUES ('36a0b67c-b74a-4640-803b-e44bb4547e3c','Курага медовая 1кг натуральная, сухофрукты без сахара абрикос сушёный без косточки','Курага медовая 1кг – это незаменимый продукт для тех, кто ценит натуральные и полезные продукты. Наши сухофрукты - это настоящая находка для всех любителей здорового питания и поклонников ЗОЖ. Курага идеально подходит для включения в меню в рамках вегетарианского рациона, а также является отличным постным продуктом.','images/36a0b67c-b74a-4640-803b-e44bb4547e3c.png',665,'PIECE');

INSERT INTO items(article_number, name,description,picture_path,price,unit)
VALUES ('3a00aeb8-2605-4eec-8215-08c0ecb51112','Товар1','Описание 1','images/nophoto.jpg',10000,'PIECE');

INSERT INTO items(article_number, name,description,picture_path,price,unit)
VALUES ('3fda7c49-282e-421a-85ab-c5684ef1d350','Товар2','Описание 2','images/nophoto.jpg',11000,'PIECE');

INSERT INTO items(article_number, name,description,picture_path,price,unit)
VALUES ('16ab55a7-45f6-44a8-873c-7a0b44346b3e','Товар3','Описание 3','images/nophoto.jpg',12000,'PIECE');

INSERT INTO items(article_number, name,description,picture_path,price,unit)
VALUES ('e3776711-6359-4f22-878d-bf290d052c85','Товар4','Описание 4','images/nophoto.jpg',13000,'PIECE');
