package io.storydoc.fabric.systemdescription.app;

import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/systemdescription")
public class SystemDescriptionController {

    private final SystemDescriptionService systemDescriptionService;

    public SystemDescriptionController(SystemDescriptionService systemDescriptionService) {
        this.systemDescriptionService = systemDescriptionService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    SystemDescriptionDTO getSystemDescription() {
        return systemDescriptionService.getSystemDescription();
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    void setSystemDescription(@RequestBody SystemDescriptionDTO systemDescription) {
        systemDescriptionService.updateSystemDescription(systemDescription);
    }

}
