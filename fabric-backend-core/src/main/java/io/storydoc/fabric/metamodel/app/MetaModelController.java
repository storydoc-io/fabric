package io.storydoc.fabric.metamodel.app;

import io.storydoc.fabric.metamodel.domain.MetaModelId;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metamodel")
public class MetaModelController {

    private final MetaModelService metaModelService;

    public MetaModelController(MetaModelService metaModelService) {
        this.metaModelService = metaModelService;
    }

    @PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public MetaModelId createMetaModel(String environmentKey, String systemComponentKey) {
        return metaModelService.createMetamodel(SystemComponentCoordinate.builder()
                .environmentKey(environmentKey)
                .systemComponentKey(systemComponentKey)
                .build());
    }

    @GetMapping(value = "entity/", produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    EntityDTO getMetaModelAsEntity(String envKey, String systemComponentKey) {
        return metaModelService.getMetaModelAsEntity(SystemComponentCoordinate.builder()
                .environmentKey(envKey)
                .systemComponentKey(systemComponentKey)
                .build());
    }

}
