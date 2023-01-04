package io.storydoc.fabric.metamodel.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.metamodel.domain.MetaModelId;
import io.storydoc.fabric.systemdescription.app.entity.EntityDTO;
import io.storydoc.fabric.systemdescription.domain.SystemComponentCoordinate;
import io.storydoc.fabric.testinfra.mock.TestConstants;
import io.storydoc.fabric.testinfra.stepdefs.StepDefs;
import io.storydoc.fabric.workspace.WorkspaceTestFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Slf4j
public class MetaModelServiceTest extends TestBase {

    @Autowired
    StepDefs canon;

    @Autowired
    WorkspaceTestFixture workspaceTestFixture;

    @Autowired
    private WorkspaceStructure workspaceStructure;

    @Autowired
    private MetaModelService metaModelService;

    @Test
    public void no_meta_model() {
        // given a system description
        canon.given_a_system_description();
        String environmentKey = TestConstants.DEV_ENVIRONMENT;
        String systemComponentKey = TestConstants.PRODUCTS_SYSTEM_COMPONENT;
        SystemComponentCoordinate coordinate = SystemComponentCoordinate.builder()
                .environmentKey(environmentKey)
                .systemComponentKey(systemComponentKey)
                .build();

        // when no metamodel is created for a systemcomponent/env combination

        // The list of metamodels for this systemcomponent is empty
        EntityDTO entityDTO = metaModelService.getMetaModelAsEntity(coordinate);
        assertNull(entityDTO);


    }

    @Test
    public void create_meta_model() {
        // given a system description
        canon.given_a_system_description();

        // when I create a metamodel for a systemcomponent/env combination
        String environmentKey = TestConstants.DEV_ENVIRONMENT;
        String systemComponentKey = TestConstants.PRODUCTS_SYSTEM_COMPONENT;
        SystemComponentCoordinate coordinate = SystemComponentCoordinate.builder()
                .environmentKey(environmentKey)
                .systemComponentKey(systemComponentKey)
                .build();

        MetaModelId metaModelId =  metaModelService.createMetamodel(coordinate);

        workspaceTestFixture.logFolderStructure("after snapshot");
        workspaceTestFixture.logResourceContent("metamodel " + metaModelId, workspaceStructure.getMetaModelUrn(coordinate));

        // I can fetch the metamodel

        {
            EntityDTO entityDTO = metaModelService.getMetaModelAsEntity(coordinate);
            assertNotNull(entityDTO);
        }

        // and I cannot fetch the metamodel for another systemcomponent/env combination
        {
            String anotherEnvironmentKey = TestConstants.INT_ENVIRONMENT;
            SystemComponentCoordinate anotherCoordinate = SystemComponentCoordinate.builder()
                    .environmentKey(anotherEnvironmentKey)
                    .systemComponentKey(systemComponentKey)
                    .build();

            EntityDTO entityDTO = metaModelService.getMetaModelAsEntity(anotherCoordinate);
            assertNull(entityDTO);
        }

    }


    @Test
    public void update_settings() {

    }

}