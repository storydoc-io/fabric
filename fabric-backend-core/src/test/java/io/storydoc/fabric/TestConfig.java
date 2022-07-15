package io.storydoc.fabric;

import io.storydoc.fabric.config.FabricServerProperties;
import io.storydoc.fabric.snapshot.domain.SnapshotCommandRunner;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages={
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

    @Bean  @Primary
    public SnapshotCommandRunner snapshotRunner() {
        return Mockito.mock(SnapshotCommandRunner.class);
    }


}


