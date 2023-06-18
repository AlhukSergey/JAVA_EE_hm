DROP SCHEMA IF EXISTS shop;
CREATE SCHEMA IF NOT EXISTS shop;

DROP TABLE IF EXISTS shop.users;
CREATE TABLE IF NOT EXISTS shop.users
(
    id       INT    NOT NULL AUTO_INCREMENT,
    name     VARCHAR(45)    NOT NULL,
    surname  VARCHAR(60)    NOT NULL,
    birthday Timestamp      NOT NULL,
    balance  DECIMAL(10, 2) NOT NULL,
    email    VARCHAR(45)    NOT NULL,
    password VARCHAR(45)    NOT NULL,
    PRIMARY KEY (id)
);


INSERT INTO shop.users(name, surname, birthday, balance, email, password)
VALUES ('Anna',
        'Ivanova',
        '1990-12-12',
        0.00,
        'anna18@gmail.com',
        'QSExKz1hc2FzYXNhYXM=');



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
    name        VARCHAR(90)  NOT NULL,
    description VARCHAR(300) NOT NULL,
    price       DOUBLE       NOT NULL,
    categoryId  INT          NOT NULL,
    imagePath   VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Жокейка (кепка) спортивная мужская/женская Columbia',
        'Бейсболка от Columbia незаменима для летнего активного отдыха на природе. В 4 из 6 стандартных панелей этой классической модели используется легкая сетчатая ткань. Натуральный хлопок с небольшим добавлением эластана приятен на ощупь и хорошо пропускает воздух даже в жару.',
        55.5,
        1,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/667/395_380_77f2/58111040299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Жокейка (кепка) для мальчиков FILA',
        'Бейсболка FILA пригодится ребенку во время активных игр на улице. Модель, выполненная из натурального хлопка, гарантирует комфорт в жаркую погоду.',
        25.84,
        1,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/55f/395_380_93e4/73898090299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Мешок для обуви для девочек FILA',
        'Практичный мешок для обуви от FILA. Модель выполнена из прочного полиэстера. Удобный карман позволит захватить с собой необходимые мелочи.',
        21.8,
        2,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/efd/395_380_d2a2/81122110299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Сумка мужская/женская FILA',
        'Вместительная сумка-шоппер из прочного хлопка. Предусмотрены длинные ручки, чтобы носить ее и в руках, и на плече.',
        27,
        2,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/1c8/395_380_ddc9/57333570299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Сумка мужская/женская FILA',
        'Сумка кросс-боди от бренда FILA поможет держать все важные мелочи под рукой, когда вы отправляетесь по делам или на тренировку. Найти нужные вещи несложно, так как в модели предусмотрено основное отделение и 2 дополнительных кармана.',
        32.3,
        2,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/123/395_380_40ae/74428530299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Полуботинки мужские GSD',
        'Кроссовки GSD One подойдут для прогулок по городу.',
        63.3,
        3,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/6e3/395_380_f6a8/78073780299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Полуботинки мужские Head',
        'Кроссовки Head Leon X Tms Fluo пригодятся для комфортных и размеренных пробежек в свое удовольствие.',
        240.5,
        3,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/5c4/395_380_10ad/80797320299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Полуботинки мужские Reebok',
        'Бескомпромиссно удобные кроссовки Reebok Glide Ripple Clip — идеальный вариант на каждый день. Узнаваемый минималистичный дизайн дополнен деталями, которые отсылают к архивам бренда.',
        296,
        3,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/c36/395_380_51fa/79724880299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Перчатки мужские/женские Northland',
        'Перчатки Northland для треккинговых походов выполнены из материала с технологией защиты от УФ-излучения. Нескользящие накладки обеспечивают надежный хват.',
        39.5,
        4,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/77a/395_380_d588/73899720299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Фляга Stern',
        'Фляга Stern объемом 700 мл позволит держать воду под рукой во время велосипедных прогулок.',
        13.04,
        5,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/a37/395_380_f6fe/61381440299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Насос Stern',
        'Велосипедный насос Stern.',
        15.61,
        5,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/871/395_380_653a/57988220299.jpg');
INSERT INTO shop.products(name, description, price, categoryId, imagePath)
VALUES ('Брюки женские Glissade',
        'Женские термокальсоны Glissade станут отличной защитой от холода во время занятий зимними видами спорта. Модель рекомендована для низкого уровня физической активности.',
        84,
        6,
        'https://cdnby.sportmaster.com/upload/mdm/media_content/resize/e2e/395_380_caef/54563010299.jpg');