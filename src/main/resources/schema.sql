

create table if not exists HARDWARE (
                    id identity,
                    code varchar(100) not null unique,
                    name varchar(100) not null,
                    type varchar(10) not null,
                    stocknumber number not null,
                    price number not null,
                    specification varchar(1000) not null
);




create table if not exists  USER(
                    id identity primary key,
                    username varchar(30) not null unique,
                    password varchar(100) not null,
                    email varchar(50) not null unique,
                    first_name varchar(30),
                    last_name varchar(30)
);

create table if not exists  CART_ITEM (
                   id identity primary key,
                   quantity int not null,
                   hardware_id bigint not null,
                   user_id bigint not null,
                   constraint fk_hardware_cart foreign key (hardware_id) references HARDWARE(id),
                   constraint fk_user_cart foreign key (user_id) references USER(id)
);


create table if not exists  REVIEW (
                   id identity primary key,
                   headline varchar(100) not null,
                   text varchar(255) not null,
                   review_grade int not null,
                   hardware_id bigint not null,
                   user_id bigint not null,
                   date_of_creation date not null,
                   FOREIGN KEY (hardware_id) references HARDWARE(id) ON DELETE CASCADE,
                   FOREIGN KEY (user_id) references USER(id) ON DELETE CASCADE

);
create table if not exists  AUTHORITY (
                  id identity primary key,
                  authority_name varchar(100) not null unique
);

create table if not exists  USER_AUTHORITY (
                    user_id bigint not null,
                    authority_id bigint not null,
                    constraint fk_user foreign key (user_id) references USER(id),
                    constraint fk_authority foreign key (authority_id) references AUTHORITY(id)
);

create table if not exists USER_ORDER(
                    id identity primary key,
                    total_price number not null,
                    created_date datetime not null,
                    first_name varchar(50) not null,
                    last_name varchar(50) not null,
                    city varchar(50) not null,
                    address varchar(50) not null,
                    email varchar(50) not null,
                    phone_number varchar(20) not null,
                    post_code varchar(10) not null
);

create table  if not exists  USER_ORDER_CART_ITEM(
                    order_id bigint not null,
                    cart_item_id bigint not null,
                    constraint fk_order foreign key (order_id) references USER_ORDER(id),
                    constraint fk_cart_item foreign key (cart_item_id) references CART_ITEM(id)
);



