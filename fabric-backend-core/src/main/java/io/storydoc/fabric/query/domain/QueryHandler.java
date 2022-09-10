package io.storydoc.fabric.query.domain;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.query.app.QueryRequestDTO;
import io.storydoc.fabric.query.app.QueryResponseItemDTO;

import java.util.Map;

public interface QueryHandler extends CommandHandler {
    QueryResponseItemDTO doQuery(QueryRequestDTO queryRequestDTO, Map<String, String> settings);
}
