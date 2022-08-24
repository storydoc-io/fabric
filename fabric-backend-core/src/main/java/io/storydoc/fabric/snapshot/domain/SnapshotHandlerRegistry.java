package io.storydoc.fabric.snapshot.domain;

import io.storydoc.fabric.core.domain.HandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@Slf4j
public class SnapshotHandlerRegistry extends HandlerRegistry<SnapshotHandler> {

    public SnapshotHandlerRegistry(List<SnapshotHandler> handlerList) {
        super(handlerList);
    }

}
