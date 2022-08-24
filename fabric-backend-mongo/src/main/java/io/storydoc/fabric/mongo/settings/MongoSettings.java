package io.storydoc.fabric.mongo.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoSettings {
    private String connectionUrl;
    private String dbName;
}
