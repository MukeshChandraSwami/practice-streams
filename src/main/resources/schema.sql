-- Dropping all the tables if they already exists
drop table if exists customer;
drop table if exists product;
drop table if exists product_order;


create table customer (
	id bigint not null COMMENT 'Primary key',
	name varchar(255) COMMENT 'Name',
	tier int COMMENT 'tier',
	primary key (id)
);


create table order_product_relationship (
	order_id bigint not null,
	product_id bigint not null,
	primary key (order_id, product_id)
);

create table product (
	id bigint not null,
	category varchar(255),
	name varchar(255),
	price double,
	primary key (id)
);

create table product_order (
	id bigint not null,
	order_date date,
	delivery_date date,
	status varchar(50),
	customer_id bigint,
	primary key (id)
);

alter table order_product_relationship add constraint FKn8aeo7cic1d0ejbbxu3vxlb4c
foreign key (product_id) references product;

alter table order_product_relationship add constraint FK722amt5gugjshh8fhjt6i66i3
foreign key (order_id) references product_order;

alter table product_order add constraint FKa90wgrcf86ft7kh3pjivc5c5e
foreign key (customer_id) references customer;