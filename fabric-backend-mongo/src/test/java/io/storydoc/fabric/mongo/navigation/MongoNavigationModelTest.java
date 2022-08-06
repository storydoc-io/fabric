package io.storydoc.fabric.mongo.navigation;

import io.storydoc.fabric.mongo.snapshot.CollectionSnapshot;
import io.storydoc.fabric.mongo.snapshot.MongoSnapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MongoNavigationModelTest {

    @Test
    public void dummy() {

        MongoNavigationModel mongoNavigationModel = new MongoNavigationModel();

        CollectionNavItem customers = new CollectionNavItem("CUSTOMERS");

        CollectionNavItem orders = new CollectionNavItem("ORDERS");

        CollectionNavItem products = new CollectionNavItem("PRODUCTS");

        mongoNavigationModel.setRoots(List.of(customers, orders, products));


        MongoSnapshot mongoSnapshot = new MongoSnapshot();
        mongoSnapshot.setCollectionSnapshots(new ArrayList<>());

        {
            CollectionSnapshot productSnapshot = new CollectionSnapshot();

            productSnapshot.setCollectionName("PRODUCTS");
            productSnapshot.setDocuments(List.of(product(101), product(102), product(103)));

            mongoSnapshot.getCollectionSnapshots().add(productSnapshot);
        }

        {
            CollectionSnapshot customerSnapshot = new CollectionSnapshot();

            customerSnapshot.setCollectionName("CUSTOMERS");
            customerSnapshot.setDocuments(List.of(customer(200), customer(201)));

            mongoSnapshot.getCollectionSnapshots().add(customerSnapshot);
        }

        {
            CollectionSnapshot orderSnapshot = new CollectionSnapshot();

            orderSnapshot.setCollectionName("ORDERS");
            orderSnapshot.setDocuments(
                List.of(
                    order(300, customerId(200),
                        List.of(
                            new OrderLine(2, productId(101)),
                            new OrderLine(5, productId(102))
                        )
                    )
                ));

            mongoSnapshot.getCollectionSnapshots().add(orderSnapshot);
        }

        System.out.println(mongoSnapshot);


    }

    @AllArgsConstructor
    @Getter
    class OrderLine {
        int quantity;
        String productId;
    }

    @AllArgsConstructor
    @Getter
    class Order {
        String orderId;
        String customerId;
        List<OrderLine> orderLines;
    }


    private String order(int orderNr, String customerId, List<OrderLine> orderLines) {
        String orderId = "order-" + orderNr;
        Order order = new Order(orderId, customerId, orderLines);
        StringBuffer b = new StringBuffer("{\n");
        b.append(String.format("     \"id\" : \"%s\",\n", orderId));
        b.append(String.format("     \"customerId\" : \"%s\",\n", customerId));
        b.append("     \"orderLines\" = [\n");
        for (int i = 0; i < orderLines.size(); i++) {
            OrderLine orderLine = orderLines.get(i);
            b.append("          {\n");
            b.append(String.format("               \"quantity\" : \"%d\",\n", orderLine.quantity));
            b.append(String.format("               \"productId\" : \"%s\"\n", orderLine.productId));
            if (i < orderLines.size()-1) {
                b.append("          },\n");
            } else {
                b.append("          }\n");
            }
        }
        b.append("     ]\n");
        b.append("}\n");
        return b.toString();
    }



    private String customerId(int idx) {
        return "customer-" + idx;
    }

    private String customer(int idx) {

        String id = customerId(idx);
        String name = id + "-name";

        return String.format("{\n" +
                "     \"id\" : \"%s\",\n" +
                "     \"firstName\": \"John\",\n" +
                "     \"lastName\": \"%s\"\n" +
                "}", id, name);

    }

    private String productId(int idx) {
        return "product-" + idx;
    }

    private String product(int idx) {

        String id = productId(idx);
        String name = id + "-name";

        return String.format("{\n" +
                "     \"id\" : \"%s\",\n" +
                "     \"name\": \"%s\"\n" +
                "}", id, name);

    }



}