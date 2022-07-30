package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.Environment;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemComponent;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import io.storydoc.fabric.workspace.WorkspaceTestFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SnapshotServiceTest extends TestBase {

    @Autowired
    SnapshotService snapshotService;

    @Autowired
    SystemDescriptionService systemDescriptionService;

    @Autowired
    SystemDescriptionStorage systemDescriptionStorage;

    @Autowired
    WorkspaceStructure workspaceStructure;

    @Autowired
    WorkspaceTestFixture workspaceTestFixture;

    @Test
    public void empty_list_of_snapshots() {
        // given an uninitialized snapshot repo

        // when I query the list of snapshots
        List<SnapshotSummaryDTO> snapshotSummaryDTOS = snapshotService.list();

        // then the list is empty
        assertNotNull(snapshotSummaryDTOS);
        assertEquals(0, snapshotSummaryDTOS.size());

    }


    @Test
    public void create_snapshot() {
        // given an system description
        createdummySystemDescription();
        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();

        // when I create a snapshot
        String name = "name-" + UUID.randomUUID();
        String environmentKey = "DEV";
        SnapshotId snapshotId = snapshotService.createSnapshot(environmentKey, name);

        workspaceTestFixture.logFolderStructure("after snapshot");
        workspaceTestFixture.logResourceContent(String.format("snapshot %s", snapshotId), workspaceStructure.getSnapshotUrn(snapshotId));

        systemDescriptionDTO.getSystemComponents().forEach(systemComponentDTO ->
            workspaceTestFixture.logResourceContent(String.format("%s snapshot (%s) ", systemComponentDTO.getKey(), systemComponentDTO.getSystemType()), workspaceStructure.getComponentSnapshotUrn(snapshotId, systemComponentDTO))
        );

        // then I can query for it
        SnapshotDTO snapshotDTO = snapshotService.getSnapshot(snapshotId);
        assertNotNull(snapshotDTO);
        assertEquals(snapshotId, snapshotDTO.getSnapshotId());
        assertEquals(name, snapshotDTO.getName());
        assertEquals(environmentKey, snapshotDTO.getEnvironmentKey());

        // and The snapshot has a component for every system component
        assertNotNull(snapshotDTO.getComponentSnapshots());
        systemDescriptionDTO.getSystemComponents().forEach(systemComponentDTO -> {
            SnapshotComponentDTO snapshotComponentDTO = snapshotDTO.getComponentSnapshots().stream()
                    .filter(component -> component.getComponentKey().equals(systemComponentDTO.getKey()))
                    .findFirst()
                    .orElse(null);
            assertNotNull(snapshotComponentDTO);
            assertEquals(systemComponentDTO.getKey(), snapshotComponentDTO.getComponentKey());
            assertEquals(systemComponentDTO.getSystemType(), snapshotComponentDTO.getSystemType());
        });


        // and it is in the list of snapshots
        List<SnapshotSummaryDTO> snapshotSummaryDTOS = snapshotService.list();
        assertNotNull(snapshotSummaryDTOS);
        assertEquals(1, snapshotSummaryDTOS.size());

        SnapshotSummaryDTO snapshotSummaryDTO = snapshotSummaryDTOS.get(0);
        assertEquals(snapshotId, snapshotSummaryDTO.getSnapshotId());
        assertEquals(name, snapshotSummaryDTO.getName());
        assertEquals(environmentKey, snapshotSummaryDTO.getEnvironmentKey());

    }

    private void createdummySystemDescription() {
        systemDescriptionStorage.saveSystemDescription(SystemDescription.builder()
                .environments(List.of(
                        Environment.builder()
                                .key("DEV")
                                .label("Develop environment")
                                .build())
                )
                .systemComponents(List.of(
                        SystemComponent.builder()
                                .key("PRODUCTS")
                                .systemType("MONGO")
                                .label("Mongo DB")
                                .build())
                )
                .settings(new HashMap<>())
                .build());
    }

    @Test
    public void list_snapshots() {
        // given a system description
        createdummySystemDescription();
        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();

        // when I take multiple snapshots
        List<SnapshotId> snapshotIds = IntStream.range(0, 2)
                .mapToObj(i -> snapshotService.createSnapshot("DEV", "name"))
                .collect(Collectors.toList());

        // I can list the snapshots
        List<SnapshotSummaryDTO> summaries = snapshotService.list();

    }


}