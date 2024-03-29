package io.storydoc.fabric.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("io.storydoc")
@Data
public class FabricServerProperties {

    String workspaceFolder;

}
