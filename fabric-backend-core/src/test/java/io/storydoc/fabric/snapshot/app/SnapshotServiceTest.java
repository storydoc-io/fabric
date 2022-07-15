package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.Environment;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemComponent;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SnapshotServiceTest extends TestBase {

    @Autowired
    SnapshotService snapshotService;

    @Autowired
    SystemDescriptionService systemDescriptionService;

    @Autowired
    SystemDescriptionStorage systemDescriptionStorage;

    @Test
    public void create_snapshot() {
        // given an system description
        systemDescriptionStorage.saveSystemDescription(SystemDescription.builder()
            .systemComponents(List.of(
                SystemComponent.builder()
                    .key("PRODUCTS")
                    .systemType("MONGO")
                    .label("Mongo DB")
                    .build()
            ))
            .environments(List.of(
                Environment.builder()
                    .key("DEV")
                    .label("Develop environment")
                    .build()))
            .settings(new HashMap<>())
            .build());

        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();

        // when I create a snapshot
        SnapshotId snapshotId = null; //snapshotService.createSnapshot();

        // I can query for it
        SnapshotDTO snapshotDTO = snapshotService.getSnapshot(snapshotId);
        assertNotNull(snapshotDTO);
        assertEquals(snapshotId, snapshotDTO.getSnapshotId());
    }


}