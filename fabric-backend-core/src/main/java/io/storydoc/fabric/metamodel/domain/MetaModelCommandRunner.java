package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.metamodel.infra.MetaModel;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
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


    public void runMetaModelCommand(MetaModelId metaModelId, SystemComponentCoordinate coordinate) {
        String systemType = systemDescriptionService.getSystemType(coordinate.getSystemComponentKey());
        Map<String, String> settings = systemDescriptionService.getSettings(coordinate);
        MetaModelHandler metaModelHandler = (MetaModelHandler<? extends MetaModel>) handlerRegistry.getHandler(systemType);
        MetaModel metaModel = metaModelHandler.createMetaModel(metaModelId, coordinate, settings);
        metaModelStorage.saveMetaModel(coordinate, metaModelId, metaModel, metaModelHandler.getMetaModelSerializer());

    }

    public EntityDTO getMetaModelAsEntity(SystemComponentCoordinate coordinate) {
        String systemType = systemDescriptionService.getSystemType(coordinate.getSystemComponentKey());
        MetaModelHandler<? extends MetaModel> metaModelHandler = (MetaModelHandler<? extends MetaModel>)handlerRegistry.getHandler(systemType);
        return metaModelHandler.getAsEntity(coordinate);
    }

}
