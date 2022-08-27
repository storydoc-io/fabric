package io.storydoc.fabric.systemdescription.domain;

import io.storydoc.fabric.core.domain.HandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@Slf4j
public class SystemStructureHandlerRegistry extends HandlerRegistry<SystemStructureHandler> {

    public SystemStructureHandlerRegistry(List<SystemStructureHandler> handlerList) {
        super(handlerList);
    }

}
