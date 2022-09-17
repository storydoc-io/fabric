package io.storydoc.fabric.console.domain;

import io.storydoc.fabric.core.domain.HandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@Slf4j
public class ConsoleHandlerRegistry extends HandlerRegistry<ConsoleHandler> {

    public ConsoleHandlerRegistry(List<ConsoleHandler> handlerList) {
        super(handlerList);
    }

}
