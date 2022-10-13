package io.storydoc.fabric;

import io.storydoc.fabric.command.app.CommandService;
import io.storydoc.fabric.command.app.ExecutionStatus;
import io.storydoc.fabric.command.domain.ExecutionId;
import io.storydoc.fabric.config.FabricServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Slf4j
abstract public class TestBase {

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.debug("**");
            log.debug("* Test: " + description.getClassName() + "." + description.getMethodName());
            log.debug("**");
        }
        protected void finished(Description description) {
        }
    };

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Autowired
    private FabricServerProperties serverProperties;

    @Autowired
    protected CommandService commandService;

    @Before
    public void setUp() throws IOException {
        String workspaceFolder = temporaryFolder.newFolder("workspace").getAbsolutePath();
        serverProperties.setWorkspaceFolder(workspaceFolder);
    }

    protected void waitUntilExecutionFinished(ExecutionId executionId) {
        waitUntilExecutionFinished(executionId, 3000);
    }

    protected void waitUntilExecutionFinished(ExecutionId executionId, int milliSeconds) {
        await()
                .atMost(milliSeconds, TimeUnit.MILLISECONDS)
                .failFast("command ended with error", ()-> ExecutionStatus.ERROR.equals(commandService.getContextInfo(executionId).getStatus()))
                .until( () -> commandService.getContextInfo(executionId).getStatus(), equalTo(ExecutionStatus.DONE))
        ;
    }


}
