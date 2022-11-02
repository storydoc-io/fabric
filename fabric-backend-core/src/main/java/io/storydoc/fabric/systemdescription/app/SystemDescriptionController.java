package io.storydoc.fabric.systemdescription.app;

import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/systemdescription")
public class SystemDescriptionController {

    private final SystemDescriptionService systemDescriptionService;

    public SystemDescriptionController(SystemDescriptionService systemDescriptionService) {
        this.systemDescriptionService = systemDescriptionService;
    }

    @GetMapping(value = "types", produces = MediaType.APPLICATION_JSON_VALUE)
    List<SystemTypeDescriptorDTO> getSystemTypeDescriptors() {
        return systemDescriptionService.getSystemTypeDescriptors();
    }



    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    SystemDescriptionDTO getSystemDescription() {
        return systemDescriptionService.getSystemDescription();
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    void setSystemDescription(@RequestBody SystemDescriptionDTO systemDescription) {
        systemDescriptionService.updateSystemDescription(systemDescription);
    }

    @GetMapping(value = "structure/env", produces = MediaType.APPLICATION_JSON_VALUE)
    StructureDTO getEnvironmentStructure(String envKey) {
        return systemDescriptionService.getStructure(envKey);
    }

    @GetMapping(value = "structure/", produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    StructureDTO getSystemComponentEnvironmentStructure(String envKey, String systemComponentKey) {
        return systemDescriptionService.getStructure(envKey, systemComponentKey);
    }

}
