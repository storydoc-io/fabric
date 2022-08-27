package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.metamodel.infra.MetaModel;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MetaModelCommandRunner {

    private final MetaModelHandlerRegistry handlerRegistry;

    private final SystemDescriptionService systemDescriptionService;

    private final MetaModelStorage metaModelStorage;

    public MetaModelCommandRunner(MetaModelHandlerRegistry handlerRegistry, SystemDescriptionService systemDescriptionService, MetaModelStorage metaModelStorage) {
        this.handlerRegistry = handlerRegistry;
        this.systemDescriptionService = systemDescriptionService;
        this.metaModelStorage = metaModelStorage;
    }


    public void runMetaModelCommand(String environmentKey, String systemComponentKey, MetaModelId metamodelId) {
        SystemDescriptionDTO systemDescriptionDTO  = systemDescriptionService.getSystemDescription();
        SystemComponentDTO systemComponentDTO = systemDescriptionService.getSystemComponentDTO(systemComponentKey);
        Map<String, String> settings = systemDescriptionDTO.getSettings()
                .get(environmentKey)
                .get(systemComponentKey);
        MetaModelHandler metaModelHandler = handlerRegistry.getHandler(systemComponentDTO.getSystemType());
        MetaModel metaModel = metaModelHandler.createMetaModel(metamodelId, systemComponentDTO, settings);
        metaModelStorage.saveMetaModel(metaModel, systemComponentKey, metamodelId, metaModelHandler.getMetaModelSerializer());

    }
}
