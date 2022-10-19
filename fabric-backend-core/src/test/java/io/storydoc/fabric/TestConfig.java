package io.storydoc.fabric;

import io.storydoc.fabric.command.domain.ProgressMonitor;
import io.storydoc.fabric.config.FabricServerProperties;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.app.query.ConsoleRequestDTO;
import io.storydoc.fabric.console.app.query.ConsoleResponseItemDTO;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.snapshot.domain.SnapshotHandler_ModelBased;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.snapshot.domain.SnapshotSerializer;
import io.storydoc.fabric.snapshot.infra.jsonmodel.Snapshot;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {
        "io.storydoc.fabric.core",
        "io.storydoc.fabric.command",
        "io.storydoc.fabric.systemdescription",
        "io.storydoc.fabric.snapshot",
        "io.storydoc.fabric.workspace",
        "io.storydoc.fabric.infra",
        "io.storydoc.fabric.console",
})
public class TestConfig {

    @Bean
    FabricServerProperties serverProperties() {
        return new FabricServerProperties();
    }


    @Bean
    ConsoleHandler mockJDBCHandler()   {
        return new ConsoleHandler() {
            @Override
            public ConsoleResponseItemDTO runRequest(ConsoleRequestDTO consoleRequestDTO, Map<String, String> settings) {
                return null;
            }

            @Override
            public ConsoleDescriptorDTO getDescriptor() {
                return null;
            }

            @Override
            public List<NavItem> getNavigation(NavigationRequest navigationRequest) {
                List<NavItem> result = new ArrayList<>();
                return result;
            }

            @Override
            public String systemType() {
                return "JDBC";
            }
        };
    }

    @Bean
    public SnapshotHandler_ModelBased<DummySnapshot> mockMongoHandler() {
        return new SnapshotHandler_ModelBased<DummySnapshot>() {
            @Override
            public String systemType() {
                return "MONGO";
            }

            @Override
            public DummySnapshot takeComponentSnapshot(SnapshotId snapshotId, SystemComponentDTO systemComponent, Map<String, String> settings) {
                DummySnapshot snapshot = new DummySnapshot();
                snapshot.setContent( "{ \"dummy\" = \"content\" } ");
                return snapshot;
            }

            @Override
            public SnapshotSerializer<DummySnapshot> getSerializer() {
                return ((snapshotComponent, outputStream) -> outputStream.write( snapshotComponent.getContent().getBytes(StandardCharsets.UTF_8)));
            }

            @Override
            public void upload(Snapshot snapshot, String environmentKey, String componentKey, ProgressMonitor progressMonitor) {

                int recordCount = 20;

                progressMonitor.setPercentDone(0);
                for (int i = 0; i < recordCount; i++) {
                    progressMonitor.setPercentDone(100 * (i+1) / recordCount);
                }

            }
        };
    }

}


