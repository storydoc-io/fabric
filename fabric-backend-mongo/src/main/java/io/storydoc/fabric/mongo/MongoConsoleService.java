package io.storydoc.fabric.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorItemDTO;
import io.storydoc.fabric.console.app.describe.ConsoleInputType;
import io.storydoc.fabric.console.app.describe.ConsoleOutputType;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.app.query.ConsoleRequestDTO;
import io.storydoc.fabric.console.app.query.ConsoleResponseItemDTO;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.mongo.settings.MongoSettings;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MongoConsoleService extends MongoServiceBase implements ConsoleHandler {

    static private final String CONSOLE_FIELD_COLLECTION_NAME = "collectionName";
    static private final String CONSOLE_FIELD_FILTER = "filter";

    @Override
    public ConsoleDescriptorDTO getDescriptor() {
        return ConsoleDescriptorDTO.builder()
                .items(List.of(
                        ConsoleDescriptorItemDTO.builder()
                                .name(CONSOLE_FIELD_COLLECTION_NAME)
                                .inputType(ConsoleInputType.TEXT)
                                .placeholder("collection name")
                                .build(),
                        ConsoleDescriptorItemDTO.builder()
                                .name(CONSOLE_FIELD_FILTER)
                                .inputType(ConsoleInputType.TEXTAREA)
                                .placeholder("filter, e.g. { \"<field>\" : \"<value>\" }")
                                .build()
                ))
                .build();
    }

    @Override
    public ConsoleResponseItemDTO runRequest(ConsoleRequestDTO requestDTO, Map<String, String> settings) {
        String systemComponentKey = requestDTO.getSystemComponentKey();
        MongoSettings mongoSettings = toMongoSettings(settings);
        MongoClient mongoClient = getMongoClient(mongoSettings);
        MongoDatabase database = mongoClient.getDatabase(mongoSettings.getDbName());

        String collectionName = requestDTO.getAttributes().get(CONSOLE_FIELD_COLLECTION_NAME);
        String filter = requestDTO.getAttributes().get(CONSOLE_FIELD_FILTER);
        if (filter == null || filter.length() == 0) {
            filter = "{}";
        }

        Bson bsonFilter = Document.parse(filter);

        MongoCollection<Document> collection = database.getCollection(collectionName);
        FindIterable<Document> documents = collection.find(bsonFilter);

        StringBuilder contentBuilder = new StringBuilder("[");

        boolean first = true;
        for (Document document : documents) {
            if (!first) {
                contentBuilder.append("\n,");
            } else {
                first = false;
            }
            contentBuilder.append(document.toJson());
        }
        contentBuilder.append("\n]");

        return ConsoleResponseItemDTO.builder()
                .systemType(systemType())
                .consoleOutputType(ConsoleOutputType.JSON)
                .content(contentBuilder.toString())
                .build();

    }

    @Override
    public List<NavItem> getNavigation(NavigationRequest navigationRequest) {
        return null;
    }

}
