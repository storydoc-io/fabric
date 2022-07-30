package io.storydoc.fabric;

import io.storydoc.fabric.config.FabricServerProperties;
import io.storydoc.fabric.snapshot.domain.SnapshotHandler;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.snapshot.domain.SnapshotSerializer;
import io.storydoc.fabric.snapshot.infra.jsonmodel.SnapshotComponent;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
@ComponentScan(basePackages = {
        "io.storydoc.fabric.core",
        "io.storydoc.fabric.systemdescription",
        "io.storydoc.fabric.snapshot",
        "io.storydoc.fabric.workspace",
        "io.storydoc.fabric.infra",
})
public class TestConfig {

    @Bean
    FabricServerProperties serverProperties() {
        return new FabricServerProperties();
    }

    @Bean
    public SnapshotHandler<DummySnapshot> mockMongoHandler() {
        return new SnapshotHandler<DummySnapshot>() {
            @Override
            public String systemType() {
                return "MONGO";
            }

            @Override
            public DummySnapshot takeComponentSnapshot(SystemComponentDTO systemComponent, SnapshotId snapshotId) {
                DummySnapshot snapshot = new DummySnapshot();
                snapshot.setContent( "{ \"dummy\" = \"content\" } ");
                return snapshot;
            }

            @Override
            public SnapshotSerializer<DummySnapshot> getSerializer() {
                return ((snapshotComponent, outputStream) -> outputStream.write( snapshotComponent.getContent().getBytes(StandardCharsets.UTF_8)));
            }
        };
    }

}


