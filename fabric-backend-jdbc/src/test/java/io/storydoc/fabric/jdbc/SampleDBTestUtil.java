package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.jdbc.metadata.*;

import java.util.List;
import java.util.Map;

public class SampleDBTestUtil {

    public static final String TABLE_ORDERS = "ORDERS";
    public static final String TABLE_CUSTOMERS = "CUSTOMERS";
    public static final String TABLE_PRODUCTS = "PRODUCTS";

    public DBMetaData getSalesDBMataData() {

        TableMetaData customers = TableMetaData.builder()
                .name(TABLE_CUSTOMERS)
                .columns(Map.of(
                        "customer_id", ColumnMetaData.builder()
                                .name("customer_id")
                                .type("id")
                                .ordinalPosition(0)
                                .build(),
                        "name", ColumnMetaData.builder()
                                .name("name")
                                .type("string")
                                .ordinalPosition(1)
                                .build()

                ))
                .foreignKeys(List.of())
                .build();

        TableMetaData products = TableMetaData.builder()
                .name(TABLE_PRODUCTS)
                .columns(Map.of(
                        "product_id", ColumnMetaData.builder()
                                .name("product_id")
                                .type("id")
                                .ordinalPosition(0)
                                .build(),
                        "name", ColumnMetaData.builder()
                                .name("name")
                                .type("string")
                                .ordinalPosition(1)
                                .build(),
                        "price", ColumnMetaData.builder()
                                .name("price")
                                .type("number")
                                .ordinalPosition(2)
                                .build(),
                        "customer_id", ColumnMetaData.builder()
                                .name("customer_id")
                                .type("id")
                                .ordinalPosition(3)
                                .build()
                ))
                .foreignKeys(List.of())
                .build();

        TableMetaData orders = TableMetaData.builder()
                .name(TABLE_ORDERS)
                .columns(Map.of(
                        "order_id", ColumnMetaData.builder()
                                .name("order_id")
                                .type("id")
                                .ordinalPosition(0)
                                .build(),
                        "product_id", ColumnMetaData.builder()
                                .name("product_id")
                                .type("id")
                                .ordinalPosition(0)
                                .build(),
                        "quantity", ColumnMetaData.builder()
                                .name("quantity")
                                .type("string")
                                .ordinalPosition(1)
                                .build(),
                        "customer_id", ColumnMetaData.builder()
                                .name("customer_id")
                                .type("id")
                                .ordinalPosition(0)
                                .build()
                ))
                .build();
        orders.setForeignKeys(List.of(
                FKMetaData.builder()
                    .fkName("fk_order_product")
                    .pkTableName(TABLE_PRODUCTS)
                    .columns(List.of(FKColumnMetaData.builder()
                        .fkColumnName("product_id")
                        .pkColumnName("product_id")
                        .build()
                    ))
                .build(),
                FKMetaData.builder()
                    .fkName("fk_order_customer")
                    .pkTableName(TABLE_CUSTOMERS)
                    .columns(List.of(FKColumnMetaData.builder()
                            .fkColumnName("customer_id")
                            .pkColumnName("customer_id")
                            .build()
                    ))
                    .build()
        ));


        return DBMetaData.builder()
                .schemaName("SALES_SCHEMA")
                .tables(List.of(
                        customers, products,orders
                ))
                .build();


    }


}
