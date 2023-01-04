package io.storydoc.fabric.testinfra;

import io.storydoc.fabric.workspace.app.WorkspaceQueryService;
import io.storydoc.fabric.workspace.domain.ResourceUrn;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkspaceTestUtils {

    @Autowired
    private WorkspaceQueryService queryService;

    @SneakyThrows
    public void logResourceContent(String msg, ResourceUrn resourceUrn)  {
        log.trace("");
        log.trace(msg + ":" );
        log.trace("\turl: " + format(resourceUrn));
        log.trace("\tcontent:" );

        log.trace("\n" + new String(queryService.getInputStream(resourceUrn).readAllBytes()));
    }

    private String format(ResourceUrn resourceUrn) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String pathElem: resourceUrn.getFolder().getPath()) {
            stringBuilder.append('/');
            stringBuilder.append(pathElem);
        }
        stringBuilder.append('/');
        stringBuilder.append(resourceUrn.getFileName());
        return stringBuilder.toString();
    }


}
