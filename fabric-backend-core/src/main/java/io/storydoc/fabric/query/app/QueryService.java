package io.storydoc.fabric.query.app;

import io.storydoc.fabric.query.domain.QueryHandlerRegistry;
import io.storydoc.fabric.systemdescription.app.EnvironmentDTO;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QueryService {

    private final QueryHandlerRegistry handlerRegistry;
    private final SystemDescriptionService systemDescriptionService;

    public QueryService(QueryHandlerRegistry handlerRegistry, SystemDescriptionService systemDescriptionService) {
        this.handlerRegistry = handlerRegistry;
        this.systemDescriptionService = systemDescriptionService;
    }

    public QueryResponseItemDTO doQuery(QueryRequestDTO request) {
        SystemComponentDTO systemComponentDTO = systemDescriptionService.getSystemComponentDTO(request.getSystemComponentKey());
        Map<String, String> settings = systemDescriptionService.getSettings(request.getSystemComponentKey(), request.getEnvironmentKey());
        return handlerRegistry.getHandler(systemComponentDTO.getSystemType()).doQuery(request, settings);
    }
}
