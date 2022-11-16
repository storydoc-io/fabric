package io.storydoc.fabric.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.mongo.settings.MongoSettings;

import java.util.Map;

public class MongoServiceBase extends StorageBase {

    public static final String SETTING_KEY__CONNECTION_URL = "connectionUrl";
    public static final String SETTING_KEY__DB_NAME = "dbName";

    public static final String MONGO_SYSTEM_TYPE = "MONGO";

    public String systemType() {
        return MONGO_SYSTEM_TYPE;
    }

    protected MongoSettings toMongoSettings(Map<String, String> settingsMap) {
        return MongoSettings.builder()
                .connectionUrl(settingsMap.get(SETTING_KEY__CONNECTION_URL))
                .dbName(settingsMap.get(SETTING_KEY__DB_NAME))
                .build();
    }

    protected MongoClient getMongoClient(MongoSettings mongoSettings) {
        return MongoClients.create(mongoSettings.getConnectionUrl());
    }


}
