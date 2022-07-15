package io.storydoc.fabric.systemdescription.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.infra.WorkspaceTestUtils;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemDescriptionServiceTest extends TestBase {

    @Autowired
    private WorkspaceStructure workspaceStructure;

    @Autowired
    private WorkspaceTestUtils workspaceTestUtils;

    @Autowired
    private SystemDescriptionService systemDescriptionService;

    @Test
    public void getSystemDescription() {
        // I can get the system description
        SystemDescriptionDTO systemDescriptionDTO = systemDescriptionService.getSystemDescription();
        Assert.assertNotNull(systemDescriptionDTO);

        workspaceTestUtils.logResourceContent("initial system description", workspaceStructure.getSystemDescriptionUrn());

    }

}