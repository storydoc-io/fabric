package io.storydoc.fabric.systemdescription.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
