create sequence bucket_seq start 1 increment 1;
create sequence category_seq start 1 increment 1;
create sequence images_seq start 1 increment 1;
create sequence order_details_seq start 1 increment 1;
create sequence order_seq start 1 increment 1;
create sequence product_seq start 1 increment 1;
create sequence sizes_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;
create table buckets
(
    id      int8 not null,
    user_id int8,
    primary key (id)
);
create table buckets_products
(
    bucket_id  int8 not null,
    product_id int8 not null
);
create table categories
(
    id    int8 not null,
    title varchar(255),
    primary key (id)
);
create table images
(
    id         int8 not null,
    url        varchar(255),
    product_id int8,
    primary key (id)
);
create table orders
(
    id           int8 not null,
    address      varchar(255),
    created      timestamp,
    order_status varchar(255),
    sum          numeric(19, 2),
    updated      timestamp,
    user_id      int8,
    primary key (id)
);
create table orders_details
(
    id         int8 not null,
    amount     numeric(19, 2),
    price      numeric(19, 2),
    order_id   int8,
    product_id int8,
    details_id int8 not null,
    primary key (id)
);
create table products
(
    id    int8 not null,
    price numeric(19, 2),
    title varchar(255),
    primary key (id)
);
create table products_categories
(
    product_id  int8 not null,
    category_id int8 not null
);
create table products_sizes
(
    product_id int8 not null,
    size_id    int8 not null
);
create table sizes
(
    id   int8 not null,
    size varchar(255),
    primary key (id)
);
create table users
(
    id        int8    not null,
    archive   boolean not null,
    email     varchar(255),
    name      varchar(255),
    password  varchar(255),
    role      varchar(255),
    bucket_id int8,
    primary key (id)
);
alter table if exists orders_details
    add constraint UK_kk6y3pyhjt6kajomtjbhsoajo unique (details_id);
alter table if exists buckets
    add constraint FKnl0ltaj67xhydcrfbq8401nvj foreign key (user_id) references users;
alter table if exists buckets_products
    add constraint FKloyxdc1uy11tayedf3dpu9lci foreign key (product_id) references products;
alter table if exists buckets_products
    add constraint FKc49ah45o66gy2f2f4c3os3149 foreign key (bucket_id) references buckets;
alter table if exists images
    add constraint FKghwsjbjo7mg3iufxruvq6iu3q foreign key (product_id) references products;
alter table if exists orders
    add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table if exists orders_details
    add constraint FK5o977kj2vptwo70fu7w7so9fe foreign key (order_id) references orders;
alter table if exists orders_details
    add constraint FKs0r9x49croribb4j6tah648gt foreign key (product_id) references products;
alter table if exists orders_details
    add constraint FKgvp1k7a3ubdboj3yhnawd5m1p foreign key (details_id) references orders_details;
alter table if exists products_categories
    add constraint FKqt6m2o5dly3luqcm00f5t4h2p foreign key (category_id) references categories;
alter table if exists products_categories
    add constraint FKtj1vdea8qwerbjqie4xldl1el foreign key (product_id) references products;
alter table if exists products_sizes
    add constraint FKt7lhs1lrbb2w48cjdxvax5m9g foreign key (size_id) references sizes;
alter table if exists products_sizes
    add constraint FKddbtdcgrf05hypy7y2rol12tc foreign key (product_id) references products;
alter table if exists users
    add constraint FK8l2qc4c6gihjdyoch727guci foreign key (bucket_id) references buckets;