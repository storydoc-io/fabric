package io.storydoc.fabric.snapshot.app;

import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.snapshot.app.descriptor.SnapshotDescriptorDTO;
import io.storydoc.fabric.snapshot.app.result.SnapshotDTO;
import io.storydoc.fabric.snapshot.app.result.SnapshotSummaryDTO;
import io.storydoc.fabric.snapshot.domain.SnapshotId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/snaphot")
public class SnapshotController {

    private final SnapshotService snapshotService;

    public SnapshotController(SnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @GetMapping(value ="/snapshots", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SnapshotSummaryDTO> list() {
        return snapshotService.list();
    }

    @PostMapping(value="/snapshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public SnapshotId create(@RequestBody SnapshotDescriptorDTO snapshotDescriptor) {
        return snapshotService.createSnapshot(snapshotDescriptor);
    }

    @GetMapping(value = "/snapshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public SnapshotDTO getById(SnapshotId snapshotId) {
        return snapshotService.getSnapshot(snapshotId);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(SnapshotId snapshotId) {
        snapshotService.deleteSnapshot(snapshotId);
    }

    @PostMapping(value="/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExecutionId upload(SnapshotId snapshotId, String environmentKey) {
        return snapshotService.upload(snapshotId, environmentKey);
    }
}
