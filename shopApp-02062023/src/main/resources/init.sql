DROP SCHEMA IF EXISTS shop;
CREATE SCHEMA IF NOT EXISTS shop;

DROP TABLE IF EXISTS shop.users;
CREATE TABLE IF NOT EXISTS shop.users
(
    id       VARCHAR(60)    NOT NULL,
    name     VARCHAR(45)    NOT NULL,
    surname  VARCHAR(60)    NOT NULL,
    balance  DECIMAL(10, 2) NOT NULL,
    email    VARCHAR(45)    NOT NULL,
    password VARCHAR(45)    NOT NULL,
    PRIMARY KEY (id)
);


INSERT INTO shop.users(id, name, surname, balance, email, password)
VALUES ('42d3cc41-6a7b-4aa2-bfa3-6a8fe0929ec0',
        'Katya',
        'Zhurila',
        0.00,
        'katya26@gmail.com',
        'MTExMQ==');
INSERT INTO shop.users(id, name, surname, balance, email, password)
VALUES ('4548b689-c8a7-413e-9be0-8133e5763f06',
        'Anna',
        'Ivanova',
        0.00,
        'anna18@gmail.com',
        'MTExMQ==');



DROP TABLE IF EXISTS shop.categories;
CREATE TABLE IF NOT EXISTS shop.categories
(
    id        INT          NOT NULL AUTO_INCREMENT,
    name      VARCHAR(45)  NOT NULL,
    imagePath VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
);


INSERT INTO shop.categories(name, imagePath)
VALUES ('Головные уборы и аксессуары',
        'https://static.zara.net/photos///2023/V/0/2/p/5875/314/300/2/w/824/5875314300_2_1_1.jpg?ts=1680187573164');
INSERT INTO shop.categories(name, imagePath)
VALUES ('Сумки',
        'https://contents.mediadecathlon.com/p2116631/k$63a1a528bf6c1a18f58c78ddf7e058b5/2116631.jpg?format=auto&quality=70&f=800x800');
INSERT INTO shop.categories(name, imagePath)
VALUES ('Спортивная обувь', 'https://basket-04.wb.ru/vol511/part51175/51175606/images/big/2.jpg');
INSERT INTO shop.categories(name, imagePath)
VALUES ('Перчатки', 'https://belok.ua/blog/wp-content/uploads/2021/07/image1-3.jpg');
INSERT INTO shop.categories(name, imagePath)
VALUES ('Спортивный инвентарь',
        'https://sportishka.com/uploads/posts/2023-03/1678232155_sportishka-com-p-sportivnii-inventar-dlya-doma-sport-instag-18.jpg');
INSERT INTO shop.categories(name, imagePath)
VALUES ('Термобелье', 'https://ae04.alicdn.com/kf/Hb4a243e8706e48baa6da4ce83d1c6ff7A.jpg_640x640.jpg');


DROP TABLE IF EXISTS shop.products;
CREATE TABLE IF NOT EXISTS shop.products
(
    id          INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(45)  NOT NULL,
    description VARCHAR(210) NOT NULL,
    price       DOUBLE       NOT NULL,
    categoryId  INT          NOT NULL,
    imagePath   VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Кепка',
        'Жокейка (кепка) спортивная мужская/женская Columbia',
        55.5,
        1,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/667/395_380_77f2/58111040299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Кепка',
        'Жокейка (кепка) для мальчиков FILA',
        25.84,
        1,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/55f/395_380_93e4/73898090299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Сумка',
        'Мешок для обуви для девочек FILA',
        21.8,
        2,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/efd/395_380_d2a2/81122110299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Сумка',
        'Сумка мужская/женская FILA',
        27,
        2,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/1c8/395_380_ddc9/57333570299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Сумка',
        'Сумка мужская/женская FILA',
        32.3,
        2,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/123/395_380_40ae/74428530299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Полуботинки',
        'Полуботинки мужские GSD',
        63.3,
        3,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/6e3/395_380_f6a8/78073780299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Полуботинки',
        'Полуботинки мужские Head',
        240.5,
        3,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/5c4/395_380_10ad/80797320299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Полуботинки',
        'Полуботинки мужские Reebok',
        296,
        3,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/c36/395_380_51fa/79724880299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Перчатки',
        'Перчатки мужские/женские Northland',
        39.5,
        4,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/77a/395_380_d588/73899720299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Фляга',
        'Фляга Stern',
        13.04,
        5,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/a37/395_380_f6fe/61381440299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Насос',
        'Насос Stern',
        15.61,
        5,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/871/395_380_653a/57988220299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Брюки',
        'Брюки женские Glissade',
        84,
        6,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/e2e/395_380_caef/54563010299.jpg');