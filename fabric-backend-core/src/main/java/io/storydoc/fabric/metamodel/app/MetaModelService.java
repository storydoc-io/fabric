package io.storydoc.fabric.metamodel.app;

import io.storydoc.fabric.infra.IDGenerator;
import io.storydoc.fabric.metamodel.domain.MetaModelCommandRunner;
import io.storydoc.fabric.metamodel.domain.MetaModelId;
import org.springframework.stereotype.Service;

@Service
public class MetaModelService {

    private final IDGenerator idGenerator;

    private final MetaModelCommandRunner metaModelCommandRunner;

    public MetaModelService(IDGenerator idGenerator, MetaModelCommandRunner metaModelCommandRunner) {
        this.idGenerator = idGenerator;
        this.metaModelCommandRunner = metaModelCommandRunner;
    }

    public MetaModelId createMetamodel(String environmentKey, String systemComponentKey) {
        MetaModelId metamodelId = MetaModelId.fromString(idGenerator.generateID(MetaModelId.CATEGORY));
        metaModelCommandRunner.runMetaModelCommand(environmentKey, systemComponentKey,  metamodelId);
        return metamodelId;
    }


}
