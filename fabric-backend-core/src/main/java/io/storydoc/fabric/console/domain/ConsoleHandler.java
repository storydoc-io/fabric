package io.storydoc.fabric.console.domain;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.query.app.QueryDTO;
import io.storydoc.fabric.query.app.ResultDTO;

import java.util.List;
import java.util.Map;

public interface ConsoleHandler extends CommandHandler {

    ResultDTO runRequest(QueryDTO queryDTO, Map<String, String> settings);

    ConsoleDescriptorDTO getDescriptor();

    List<NavItem> getNavigation(NavigationRequest navigationRequest);
}
