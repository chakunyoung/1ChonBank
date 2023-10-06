create database ssafy_banking;
use ssafy_banking;

create table if not exists family
(
    id          bigint auto_increment
        primary key,
    family_name varchar(255) null
);

create table if not exists quiz
(
    id              bigint auto_increment
        primary key,
    quiz_answer     varchar(1000) null,
    quiz_choice1    varchar(1000) null,
    quiz_choice2    varchar(1000) null,
    quiz_choice3    varchar(1000) null,
    quiz_choice4    varchar(1000) null,
    quiz_choice5    varchar(1000) null,
    quiz_commentary varchar(1000) null,
    quiz_question   varchar(1000) null
);

create table if not exists user
(
    id        int auto_increment
        primary key,
    money     bigint default 0 not null,
    nickname  varchar(255)     null,
    quiz      bigint           null,
    roles     varchar(255)     null,
    score     int              null,
    user_id   varchar(255)     null,
    family_id bigint           null,
    constraint FKik9u91b6e9oc9cebjhrh5wftk
        foreign key (family_id) references family (id)
);

create table if not exists financial_product
(
    id           bigint auto_increment
        primary key,
    info         varchar(255) null,
    name         varchar(255) null,
    period int not null,
    product_type varchar(255) null,
    rate         int          not null,
    family_id    bigint       null,
    parent_id    int          null,
    constraint FKkxpuwj6hytlnhplnisln0p1x3
        foreign key (family_id) references family (id),
    constraint FKsu0o0bvxhw0yk692diltjc7a4
        foreign key (parent_id) references user (id)
);

create table if not exists depositor
(
    id                   bigint auto_increment
        primary key,
    allow_product        tinyint(1) not null,
    date                 datetime   null,
    money                int        not null,
    financial_product_id bigint     null,
    user_id              int        null,
    constraint FKninfnonahd8mcwgkef9imwya2
        foreign key (user_id) references user (id),
    constraint FKnodepttu3do1scg2vilb5j1hp
        foreign key (financial_product_id) references financial_product (id)
);

create table if not exists invitation
(
    id        bigint auto_increment
        primary key,
    family_id bigint null,
    from_id   int    null,
    to_id     int    null,
    constraint FKgjr8o1qd0vspp4ck4rv1jk15l
        foreign key (family_id) references family (id),
    constraint FKhn3p4kbj3cfhcy5pjpshnps9c
        foreign key (to_id) references user (id),
    constraint FKl1gyseycgrq46b9alxdn5co13
        foreign key (from_id) references user (id)
);

create table if not exists loaner
(
    id                   bigint auto_increment
        primary key,
    allow_product        tinyint(1) not null,
    date                 datetime   null,
    money                int        not null,
    financial_product_id bigint     null,
    user_id              int        null,
    constraint FKeai0hpfjlmeeghimilox6hy2o
        foreign key (financial_product_id) references financial_product (id),
    constraint FKrpgsbaly8rlkf3m9iorf6bx2o
        foreign key (user_id) references user (id)
);

create table if not exists mission
(
    mission_id             bigint auto_increment
        primary key,
    mission_description    varchar(255)               null,
    mission_name           varchar(255)               null,
    mission_point          int                        null,
    mission_status         varchar(255) default '시작전' null,
    mission_terminate_date datetime                   null,
    mission_child_id       int                        null,
    mission_family_id      bigint                     null,
    mission_parent_id      int                        null,
    child_nickname         varchar(255)               null,
    parent_nickname        varchar(255)               null,
    constraint FKdvkmbfsisqgt5jhuqgwpduweh
        foreign key (mission_child_id) references user (id),
    constraint FKkt0byon7ea1ly0g7jq91q2nlp
        foreign key (mission_family_id) references family (id),
    constraint FKoi5dir7w8awlxr96t5smuknmq
        foreign key (mission_parent_id) references user (id)
);

create table if not exists pin_money
(
    id           bigint auto_increment
        primary key,
    pin_money    int  not null,
    receive_time date null,
    user_id      int  null,
    constraint FKnc5poofsjuon7qfe6bd1ubk4y
        foreign key (user_id) references user (id)
);

create table if not exists point
(
    id          bigint auto_increment
        primary key,
    amount      int          not null,
    memo        varchar(255) null,
    time        datetime     null,
    receiver_id int          null,
    sender_id   int          null,
    constraint FKlcgdgd648jl4hxlhsyew7o65w
        foreign key (sender_id) references user (id),
    constraint FKo91g5b5428ybst8ceo0ewhkmv
        foreign key (receiver_id) references user (id)
);

create table if not exists savingser
(
    id                   bigint auto_increment
        primary key,
    allow_product        tinyint(1) not null,
    date                 datetime   null,
    money                int        not null,
    regular_money        int        not null,
    financial_product_id bigint     null,
    user_id              int        null,
    constraint FK9hisghctrymb4s7fylf1o1o6u
        foreign key (user_id) references user (id),
    constraint FKfor2oqjhsbl4jglnadkuyvnqq
        foreign key (financial_product_id) references financial_product (id)
);

