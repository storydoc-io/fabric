create table sales.CUSTOMERS (
    customer_id INT primary key,
    name VARCHAR(200)
);

create table sales.PRODUCTS (
    product_id INT primary key,
    name VARCHAR(200),
    price numeric(10, 2)

);

create table sales.ORDERS (
    order_id INT primary key ,
    customer_id INT,
    constraint fk_order_customer
        foreign key (customer_id)
        references sales.CUSTOMERS(customer_id)
);

create table sales.ORDER_ITEM
(
    order_item_id INT primary key,
    order_id INT,
    product_id    INT,
    constraint fk_order_item_order
        foreign key (order_id)
            references sales.ORDERS (order_id),
    constraint fk_order_item_product
        foreign key (product_id)
            references sales.PRODUCTS (product_id)
);