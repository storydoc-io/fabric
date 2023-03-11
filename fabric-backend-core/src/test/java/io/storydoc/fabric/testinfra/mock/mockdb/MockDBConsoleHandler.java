package io.storydoc.fabric.testinfra.mock.mockdb;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.query.app.QueryDTO;
import io.storydoc.fabric.query.app.ResultDTO;
import io.storydoc.fabric.testinfra.mock.TestConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MockDBConsoleHandler implements ConsoleHandler {

    @Override
    public ResultDTO runRequest(QueryDTO queryDTO, Map<String, String> settings) {
        return null;
    }

    @Override
    public ConsoleDescriptorDTO getDescriptor() {
        return null;
    }

    @Override
    public List<NavItem> getNavigation(NavigationRequest navigationRequest) {
        List<NavItem> result = new ArrayList<>();
        return result;
    }

    @Override
    public String systemType() {
        return TestConstants.MOCK_DB_TYPE;
    }

}
