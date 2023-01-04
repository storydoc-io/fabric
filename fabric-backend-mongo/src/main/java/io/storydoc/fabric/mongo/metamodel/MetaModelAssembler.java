package io.storydoc.fabric.mongo.metamodel;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class MetaModelAssembler {

    public MongoMetaModel getMetaModel(MongoDatabase database, String dbName) {
        return MongoMetaModel.builder()
                .dbName(dbName)
                .collections(getCollections(database))
                .build();
    }

    private List<Collection> getCollections(MongoDatabase database) {
        List<Collection> collections = new ArrayList<>();

        for(String collectionName: database.listCollectionNames()) {
            collections.add(getCollectionMetaData(database, collectionName));
        }

        return collections;
    }

    private Collection getCollectionMetaData(MongoDatabase database, String collectionName) {
        MongoCollection<Document> mongoCollection = database.getCollection(collectionName);

        FindIterable<Document> documents = mongoCollection.find();
        Map<String, Object> attributes;
        Map<String, Object> schema = new HashMap<>();
        for(Document document: documents) {
            attributes = document.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            schema = toSchema(attributes);
            break;
        }

        return Collection.builder()
                .name(collectionName)
                .schema(schema)
                .build();
    }

    private Map<String, Object> toSchema(Map<String, Object> attributes) {
        return attributes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e-> typeOf(e.getValue())));
    }

    private Object typeOf(Object value) {
        if (value instanceof String) {
            return "string";
        }
        if (value instanceof Boolean) {
            return "boolean";
        }
        if (value instanceof Date) {
            return "date";
        }
        if (value instanceof Long) {
            return "long";
        }
        if (value instanceof Integer) {
            return "integer";
        }
        if (value instanceof List) {
            List<Object> list = (List) value;
            List<Object> type = new ArrayList<>();
            if (list.size()>0) {
                type.add(typeOf(list.get(0)));
            }
            return type;
        }
        if (value instanceof Document) {
            Map<String, Object> type = new HashMap<>();
            Document document = (Document) value;
            for(Map.Entry<String, Object> entry: document.entrySet()) {
                type.put(entry.getKey(), typeOf(entry.getValue()));
            }
            return type;
        }
        return "could not map: " + value.getClass();
    }


}
