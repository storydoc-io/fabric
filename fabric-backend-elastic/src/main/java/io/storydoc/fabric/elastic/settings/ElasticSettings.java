package io.storydoc.fabric.elastic.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticSettings {

    private String userName;
    private String password;
    private String hostName;
    private int port;
    private String schemaPattern;

}
