package io.storydoc.fabric.metamodel.app;

import io.storydoc.fabric.metamodel.domain.MetaModelId;
import org.springframework.http.MediaType;
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
        return metaModelService.createMetamodel(environmentKey, systemComponentKey);
    }


}
