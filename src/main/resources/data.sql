INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name, address, telephone)
VALUES ('Tutto Bento', 'Ufa', '279-09-03'),
       ('Ашхана', 'Сызрань', '54-95-56'),
       ('Мясо', 'проспект Салавата Юлаева, 1', '+7(347) 777-11-33');

INSERT INTO DISH (restaurant_id, dish_date, name, price)
VALUES (1, '2022-09-8', 'Суп 1', 50),
       (1, '2022-09-8', 'Чай 1', 25),
       (1, '2022-09-8', 'Пшенка 1', 60),
       (1, '2022-09-8', 'Рыба 1', 150),
       (2, '2022-09-8', 'Манты 1', 250),
       (2, '2022-09-8', 'Гречка 1', 65),
       (2, '2022-09-8', 'Борщ 1', 75),
       (3, '2022-09-8', 'Солянка 1', 100),
       (3, '2022-09-8', 'Котлета 1', 160),
       (3, '2022-09-8', 'Кофе 1', 60),
       (1, CURRENT_DATE, 'Суп', 50),
       (1, CURRENT_DATE, 'Чай', 25),
       (1, CURRENT_DATE, 'Пшенка', 60),
       (1, CURRENT_DATE, 'Рыба', 150),
       (3, CURRENT_DATE, 'Солянка', 100),
       (3, CURRENT_DATE, 'Котлета', 160),
       (3, CURRENT_DATE, 'Кофе', 60);

INSERT INTO VOTE (vote_date, restaurant_id, user_id)
VALUES ('2022-09-05', 1, 1),
       ('2022-09-05', 3, 2),
       ('2022-09-04', 2, 1),
       ('2022-09-04', 2, 2),
       ('2022-09-03', 1, 1),
       ('2022-09-03', 1, 2),
       (CURRENT_DATE, 3, 2);