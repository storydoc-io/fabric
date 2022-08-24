package io.storydoc.fabric.metamodel.domain;

import io.storydoc.fabric.core.domain.HandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@Slf4j
public class MetaModelHandlerRegistry extends HandlerRegistry<MetaModelHandler> {

    public MetaModelHandlerRegistry(List<MetaModelHandler> handlerList) {
        super(handlerList);
    }

}
