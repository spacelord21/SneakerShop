insert into categories(id, title) values (1,'Мужские кроссовки');
insert into categories(id,title) values (2, 'Женские кроссовки');
alter sequence category_seq restart with 3;

insert into products(id,title,price) values (1,'Nike Air Force 1', 15000);
insert into products(id,title,price) values (2,'Nike Dunk Low Retro SE', 19000);
insert into products(id,title,price) values (3,'Nike WMNS Air Force', 16000);
insert into products(id,title,price) values (4,'Ozweego Meta', 17000);
insert into products(id,title,price) values (5,'Puma Cali Dream', 20000);
insert into products(id,title,price) values (6,'Puma Mirage Sport Hacked', 18000);
insert into products(id,title,price) values (7,'Puma RS-X Pop', 10000);
insert into products(id,title,price) values (8,'Puma Slipstream LO Retro', 25000);
alter sequence product_seq restart with 9;

insert into products_categories(product_id, category_id) values (1,1);
insert into products_categories(product_id, category_id) values (2,1);
insert into products_categories(product_id, category_id) values (3,1);
insert into products_categories(product_id, category_id) values (4,1);
insert into products_categories(product_id, category_id) values (5,1);
insert into products_categories(product_id, category_id) values (6,1);
insert into products_categories(product_id, category_id) values (7,1);
insert into products_categories(product_id, category_id) values (8,1);