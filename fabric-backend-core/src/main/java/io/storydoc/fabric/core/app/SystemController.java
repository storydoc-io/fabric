package io.storydoc.fabric.core.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    private final SystemService systemService;

    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @GetMapping(value ="/checkConfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public SystemCheckResultDTO checkSystemConfig() {
        return systemService.checkSystem();
    }

}
