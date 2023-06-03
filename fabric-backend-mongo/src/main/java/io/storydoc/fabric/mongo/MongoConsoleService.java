package io.storydoc.fabric.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorItemDTO;
import io.storydoc.fabric.console.app.describe.ConsoleInputType;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.mongo.settings.MongoSettings;
import io.storydoc.fabric.query.app.PagingDTO;
import io.storydoc.fabric.query.app.QueryDTO;
import io.storydoc.fabric.query.app.ResultDTO;
import io.storydoc.fabric.query.app.ResultType;
import io.storydoc.fabric.query.app.documents.DocumentsResultSet;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public ResultDTO runRequest(QueryDTO requestDTO, Map<String, String> settings) {
        MongoSettings mongoSettings = toMongoSettings(settings);
        MongoClient mongoClient = getMongoClient(mongoSettings);
        MongoDatabase database = mongoClient.getDatabase(mongoSettings.getDbName());

        String collectionName = requestDTO.getAttributes().get(CONSOLE_FIELD_COLLECTION_NAME);
        String filter = requestDTO.getAttributes().get(CONSOLE_FIELD_FILTER);
        if (filter == null || filter.length() == 0) {
            filter = "{}";
        }
        PagingDTO pagingDTO = requestDTO.getPaging();

        Bson bsonFilter = Document.parse(filter);

        MongoCollection<Document> collection = database.getCollection(collectionName);
        FindIterable<Document> mongoDocuments = collection.find(bsonFilter);

        List<String> documents = new ArrayList<>();
        long min = 0L, max = 0L;

        if (pagingDTO!= null) {
            min = (long) pagingDTO.getPageSize() * (pagingDTO.getPageNr()-1);
            max = ((long) pagingDTO.getPageSize() * (pagingDTO.getPageNr()))-1 ;
        }

        long idx = 0L;
        for (Document document : mongoDocuments) {
            idx++;
            if (pagingDTO == null || (min <= idx && idx <= max)) {
                    documents.add(document.toJson());
            }

        }
        long nrOfResults = idx +1;

        return ResultDTO.builder()
                .systemType(systemType())
                .resultType(ResultType.JSON)
                .documentsResultSet(DocumentsResultSet.builder()
                        .documents(documents)
                        .pagingInfo(pagingDTO != null ? PagingDTO.builder()
                                .pageSize(pagingDTO.getPageSize())
                                .pageNr(pagingDTO.getPageNr())
                                .nrOfResults(nrOfResults)
                                .build()
                                : null)
                        .build())
                .build();

    }

    @Override
    public List<NavItem> getNavigation(NavigationRequest navigationRequest) {
        return null;
    }

}
