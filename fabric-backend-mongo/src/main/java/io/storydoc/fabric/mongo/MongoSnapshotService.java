package io.storydoc.fabric.mongo;

import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.snapshot.domain.*;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MongoSnapshotService extends StorageBase implements SnapshotHandler<MongoSnapshot> {

    private final SnapshotStorage snapshotStorage;

    public MongoSnapshotService(SnapshotStorage snapshotStorage) {
        this.snapshotStorage = snapshotStorage;
    }

    @Override
    public String systemType() {
        return "MONGO";
    }

    @Override
    public MongoSnapshot takeComponentSnapshot(SystemComponentDTO systemComponent, SnapshotId snapshotId) {
        MongoSnapshot mongoSnapshot = new MongoSnapshot();
        mongoSnapshot.setCollectionSnapshots(new ArrayList<>());
        {
            CollectionSnapshot collectionSnapshot = new CollectionSnapshot();
            collectionSnapshot.setCollectionName("Customers");
            collectionSnapshot.setDocuments(new ArrayList<>());
            collectionSnapshot.getDocuments().add("{\n" +
                    "     \"firstName\": \"John\",\n" +
                    "     \"lastName\": \"Smith\",\n" +
                    "     \"age\": 25,\n" +
                    "     \"address\":\n" +
                    "     {\n" +
                    "         \"streetAddress\": \"21 2nd Street\",\n" +
                    "         \"city\": \"New York\",\n" +
                    "         \"state\": \"NY\",\n" +
                    "         \"postalCode\": \"10021\"\n" +
                    "     },\n" +
                    "     \"phoneNumber\":\n" +
                    "     [\n" +
                    "         {\n" +
                    "           \"type\": \"home\",\n" +
                    "           \"number\": \"212 555-1234\"\n" +
                    "         },\n" +
                    "         {\n" +
                    "           \"type\": \"fax\",\n" +
                    "           \"number\": \"646 555-4567\"\n" +
                    "         }\n" +
                    "     ]\n" +
                    " }" +
                    "");
            mongoSnapshot.getCollectionSnapshots().add(collectionSnapshot);
        }
        {
            CollectionSnapshot collectionSnapshot = new CollectionSnapshot();
            collectionSnapshot.setCollectionName("Orders");
            collectionSnapshot.setDocuments(new ArrayList<>());
            collectionSnapshot.getDocuments().add("[\n" +
                    "{\n" +
                    "“advance_paid”: 0.0,\n" +
                    "“company”: “_Test Company”,\n" +
                    "“conversion_rate”: 1.0,\n" +
                    "“currency”: “INR”,\n" +
                    "“customer”: “_Test Customer”,\n" +
                    "“customer_group”: “_Test Customer Group”,\n" +
                    "“customer_name”: “_Test Customer”,\n" +
                    "“delivery_date”: “2013-02-23”,\n" +
                    "“doctype”: “Sales Order”,\n" +
                    "“base_grand_total”: 1000.0,\n" +
                    "“grand_total”: 1000.0,\n" +
                    "“naming_series”: “_T-Sales Order-”,\n" +
                    "“order_type”: “Sales”,\n" +
                    "“plc_conversion_rate”: 1.0,\n" +
                    "“price_list_currency”: “INR”,\n" +
                    "“items”: [\n" +
                    "{\n" +
                    "“base_amount”: 1000.0,\n" +
                    "“base_rate”: 100.0,\n" +
                    "“description”: “CPU”,\n" +
                    "“doctype”: “Sales Order Item”,\n" +
                    "“item_code”: “_Test Item Home Desktop 100”,\n" +
                    "“item_name”: “CPU”,\n" +
                    "“parentfield”: “items”,\n" +
                    "“qty”: 10.0,\n" +
                    "“rate”: 100.0,\n" +
                    "“warehouse”: “_Test Warehouse - _TC”,\n" +
                    "“stock_uom”: “_Test UOM”,\n" +
                    "“conversion_factor”: 1.0,\n" +
                    "“uom”: “_Test UOM”\n" +
                    "}\n" +
                    "],\n" +
                    "“selling_price_list”: “_Test Price List”,\n" +
                    "“territory”: “_Test Territory”,\n" +
                    "“transaction_date”: “2013-02-21”\n" +
                    "}\n" +
                    "]");
            mongoSnapshot.getCollectionSnapshots().add(collectionSnapshot);

        }

        return mongoSnapshot;
    }



    @Override
    public SnapshotSerializer<MongoSnapshot> getSerializer() {
        return ((mongoSnapshot, outputStream) -> objectMapper.writeValue(outputStream, mongoSnapshot));
    }


    public MongoSnapshot getMongoSnapshot(SnapshotId snapshotId, String componentKey) {
        SnapshotDeserializer<MongoSnapshot> deserializer = (inputStream -> objectMapper.readValue(inputStream, MongoSnapshot.class));
        return snapshotStorage.loadSnapshotComponent(snapshotId, componentKey, deserializer, systemType());

    }
}
