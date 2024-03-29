package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.testinfra.stepdefs.StepDefs;
import io.storydoc.fabric.snapshot.app.descriptor.SnapshotDescriptorDTO;
import io.storydoc.fabric.snapshot.app.result.SnapshotComponentDTO;
import io.storydoc.fabric.snapshot.app.result.SnapshotDTO;
import io.storydoc.fabric.snapshot.app.result.SnapshotSummaryDTO;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import io.storydoc.fabric.workspace.WorkspaceTestFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class SnapshotServiceTest extends TestBase {

    @Autowired
    StepDefs canon;

    @Autowired
    SnapshotService snapshotService;

    @Autowired
    SystemDescriptionService systemDescriptionService;

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
        canon.given_a_system_description();
        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();

        // when I create a snapshot
        String name = "name-" + UUID.randomUUID();
        String environmentKey = "DEV";
        SnapshotId snapshotId = snapshotService.createSnapshot(SnapshotDescriptorDTO.builder()
                .environmentKey(environmentKey)
                .name(name)
                .build());

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

    @Test
    public void delete_snapshot() {
        // given an system description
        canon.given_a_system_description();
        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();

        // and a snapshot
        String name = "name-" + UUID.randomUUID();
        String environmentKey = "DEV";
        SnapshotId snapshotId = snapshotService.createSnapshot(SnapshotDescriptorDTO.builder()
                .environmentKey(environmentKey)
                .name(name)
                .build());

        // when I delete the snapshot

        workspaceTestFixture.logFolderStructure("before delete");
        workspaceTestFixture.logResourceContent(String.format("summaries", snapshotId), workspaceStructure.getSummariesUrn());


        snapshotService.deleteSnapshot(snapshotId);

        workspaceTestFixture.logFolderStructure("after delete");
        workspaceTestFixture.logResourceContent(String.format("summaries", snapshotId), workspaceStructure.getSummariesUrn());

        // then the snapshot is gone
        List<SnapshotSummaryDTO> snapshotSummaryDTOS = snapshotService.list();
        assertNotNull(snapshotSummaryDTOS);
        assertEquals(0, snapshotSummaryDTOS.size());

    }

    @Test
    public void list_snapshots() {
        // given a system description
        canon.given_a_system_description();
        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();

        // when I take multiple snapshots
        List<SnapshotId> snapshotIds = IntStream.range(0, 2)
                .mapToObj(i -> snapshotService.createSnapshot(SnapshotDescriptorDTO.builder()
                        .environmentKey("DEV")
                        .name("name")
                        .build()))
                .collect(Collectors.toList());

        // I can list the snapshots
        List<SnapshotSummaryDTO> summaries = snapshotService.list();

    }

    @Test
    public void testUpload() {
        // given a system description
        canon.given_a_system_description();
        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();

        // given a snapshot from the INT environment
        String name = "name-" + UUID.randomUUID();
        String environmentKey = "INT";
        SnapshotId snapshotId = snapshotService.createSnapshot(SnapshotDescriptorDTO.builder()
                .environmentKey(environmentKey)
                .name(name)
                .build());

        // then I can upload it to the DEV environment
        ExecutionId executionId = snapshotService.upload(snapshotId, "DEV");
        waitUntilExecutionFinished(executionId);


    }
}