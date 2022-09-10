package io.storydoc.fabric.query.domain;

import io.storydoc.fabric.core.domain.HandlerRegistry;
import io.storydoc.fabric.metamodel.domain.MetaModelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@Slf4j
public class QueryHandlerRegistry extends HandlerRegistry<QueryHandler> {

    public QueryHandlerRegistry(List<QueryHandler> handlerList) {
        super(handlerList);
    }

}
