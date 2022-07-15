package io.storydoc.fabric.snapshot.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/snaphot")
public class SnapshotController {

    private final SnapshotService snapshotService;

    public SnapshotController(SnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @GetMapping(value ="/snapshots", produces = MediaType.APPLICATION_JSON_VALUE)
    public DashboardDTO list() {
        return DashboardDTO.builder()
                .att("hello")
                .build();
    }

    @PostMapping(value="/snapshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public SnapshotDTO create() {
        //snapshotService.createSnapshot();
        return SnapshotDTO.builder()
                .content("hello")
                .build();
    }

    @GetMapping(value = "/snapshot", produces = MediaType.APPLICATION_JSON_VALUE)
    public SnapshotDTO getById() {
        return SnapshotDTO.builder()
                .content(getDummyJson())
                .build();
    }

    String getDummyJson() {
        return "{\n" +
                "}";
    }


}
