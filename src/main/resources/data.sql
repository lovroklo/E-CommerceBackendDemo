delete from HARDWARE;
delete from USER_AUTHORITY;
delete from USER;
delete from REVIEW;
delete from AUTHORITY;
delete from CART_ITEM;

insert into HARDWARE (id, code, name, type, STOCKNUMBER, price, specification)
values (-1,'ryz_5_2600', 'Ryzen 5 2600', 'CPU', 11, 1299.99, 'The Ryzen 5 2600 Processor from AMD is a AM4 3.4 GHz base clock 6-core,
                                                             12-thread 12nm FinFET CMOS desktop processor. It supports 2-channel DDR4 memory up to 2933 MHz.
                                                             This PCIe 3.0 x16 version CPU provides 576 Kb L1 cache, 3 MB L2 cache, and 16 MB L3 cache.');
insert into HARDWARE (id, code, name, type, STOCKNUMBER, price, specification)
values (-2,'ryz_5_3600', 'Ryzen 5 3600', 'CPU', 10, 1699.99, 'The AMD Ryzen 5 3600 3.6 GHz Six-Core AM4 Processor is a powerful
                                                            six-core processor with 12 threads, designed for socket AM4 motherboards.
                                                            Built with Zen 2 architecture, the third-generation 7nm Ryzen processor
                                                            offers increased performance compared to its predecessor. It has a base clock
                                                            speed of 3.6 GHz and can reach a max boost clock speed');
insert into HARDWARE (id, code, name, type, STOCKNUMBER, price, specification)
values (-3,'rx6600', 'RX 6600 XT', 'GPU', 10, 4500, 'The AMD Radeon RX 6600 XT is a mid-range desktop graphics card that is based on the RDNA 2 architecture and uses the Navi 23 chip.
                                                   It offers 2,048 shaders and 8 GB GDDR6 VRAM with a 128 bit memory interface (16 Gbps, 256 GB/s max).');


insert into USER (id, username, password, email)
values
    (-2, 'user', '$2a$12$h0HcS2QDb/7zPASbLa2GoOTSRP6CWK0oX7pCK.dPjkM6L5N4pNovi','user@gmail.com'), -- password = user
    (-1, 'admin', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG', 'admin@gmail.com');-- password = admin

insert into REVIEW (ID, HEADLINE, TEXT, REVIEW_GRADE, HARDWARE_ID, USER_ID, DATE_OF_CREATION)
values (-1,'Bang for buck!', 'It was a great deal for this procesor', 5, -1, -2, CURRENT_TIMESTAMP());
insert into REVIEW (ID, HEADLINE, TEXT, REVIEW_GRADE, HARDWARE_ID, USER_ID, DATE_OF_CREATION)
values (-2,'Not bad, could be better', 'I wish it was a little bit faster.', 2, -1, -2, CURRENT_TIMESTAMP());
insert into REVIEW (ID, HEADLINE, TEXT, REVIEW_GRADE, HARDWARE_ID, USER_ID, DATE_OF_CREATION)
values (-3,'GREAT!', 'GPU is very strong.', 5, -3, -2, CURRENT_TIMESTAMP());


insert into AUTHORITY (id, authority_name)
values
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_STAFF');

insert into USER_AUTHORITY (user_id, authority_id)
values
    (-1, 1),
    (-2, 2);

insert into CART_ITEM (id,quantity, hardware_id, user_id)
values (-1,5,-1,-2),
       (-2,3,-2,-2);

insert into USER_ORDER (id,total_price,created_date,first_name,last_name,city,address,email,phone_number,post_code)
values ( -1,10.55,NOW(), 'user','user','user','user','user','user','user');

insert into USER_ORDER_CART_ITEM(order_id, cart_item_id)
values (-1, -1),
       (-1, -2);