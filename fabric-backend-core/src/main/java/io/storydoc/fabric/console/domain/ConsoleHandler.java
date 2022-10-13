package io.storydoc.fabric.console.domain;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.metanav.MetaNavItem;
import io.storydoc.fabric.console.app.query.ConsoleRequestDTO;
import io.storydoc.fabric.console.app.query.ConsoleResponseItemDTO;
import io.storydoc.fabric.core.domain.CommandHandler;

import java.util.List;
import java.util.Map;

public interface ConsoleHandler extends CommandHandler {

    ConsoleResponseItemDTO runRequest(ConsoleRequestDTO consoleRequestDTO, Map<String, String> settings);

    ConsoleDescriptorDTO getDescriptor();

    List<MetaNavItem> getMetaNav(String systemComponentKey);
}
