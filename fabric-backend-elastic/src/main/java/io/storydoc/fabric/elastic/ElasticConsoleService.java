package io.storydoc.fabric.elastic;

import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import io.storydoc.fabric.connection.domain.ConnectionHandler;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.describe.ConsoleDescriptorItemDTO;
import io.storydoc.fabric.console.app.describe.ConsoleInputType;
import io.storydoc.fabric.console.app.describe.ConsoleOutputType;
import io.storydoc.fabric.console.app.metanav.MetaNavItem;
import io.storydoc.fabric.console.app.query.ConsoleRequestDTO;
import io.storydoc.fabric.console.app.query.ConsoleResponseItemDTO;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.elastic.settings.ElasticSettings;
import lombok.SneakyThrows;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ElasticConsoleService extends ElasticServiceBase implements ConnectionHandler, ConsoleHandler {

    // connection

    @Override
    public ConnectionTestResponseDTO testConnection(ConnectionTestRequestDTO connectionTestRequestDTO) {
        try {
            ElasticSettings settings = toSettings(connectionTestRequestDTO.getSettings());
            RestHighLevelClient client = getClient(settings);
            return new ConnectionTestResponseDTO(true, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ConnectionTestResponseDTO(false, e.getMessage());
        }
    }

    // Console

    public static final String CONSOLE_FIELD_JSON_ENTITY = "jsonEntity";
    public static final String CONSOLE_FIELD_ENDPOINT = "endpoint";
    public static final String CONSOLE_FIELD_HTTP_METHOD = "httpMethod";

    @Override
    public ConsoleDescriptorDTO getDescriptor() {
        return ConsoleDescriptorDTO.builder()
                .items(List.of(
                        ConsoleDescriptorItemDTO.builder()
                                .name(CONSOLE_FIELD_HTTP_METHOD)
                                .inputType(ConsoleInputType.SELECT)
                                .selectValues(List.of("GET","POST","PUT"))
                                .build(),
                        ConsoleDescriptorItemDTO.builder()
                                .name(CONSOLE_FIELD_ENDPOINT)
                                .inputType(ConsoleInputType.TEXT)
                                .build(),
                        ConsoleDescriptorItemDTO.builder()
                                .name(CONSOLE_FIELD_JSON_ENTITY)
                                .inputType(ConsoleInputType.TEXTAREA)
                                .build()
                ))
                .build();
    }

    @Override
    @SneakyThrows
    public ConsoleResponseItemDTO runRequest(ConsoleRequestDTO requestDTO, Map<String, String> settings) {
        try {
            ElasticSettings elasticSettings = toSettings(settings);
            RestHighLevelClient client = getClient(elasticSettings);

            String httpMethod = requestDTO.getAttributes().get(CONSOLE_FIELD_HTTP_METHOD);
            String endpoint = requestDTO.getAttributes().get(CONSOLE_FIELD_ENDPOINT);
            String requestBody = requestDTO.getAttributes().get(CONSOLE_FIELD_JSON_ENTITY);

            Request request = new Request(httpMethod, endpoint);
            if (requestBody != null) {
                request.setJsonEntity(requestBody);
            }
            Response response = client.getLowLevelClient().performRequest(request);
            return ConsoleResponseItemDTO.builder()
                    .consoleOutputType(ConsoleOutputType.JSON)
                    .content(EntityUtils.toString(response.getEntity()))
                    .build();
        } catch (Exception e ) {
            return ConsoleResponseItemDTO.builder()
                    .consoleOutputType(ConsoleOutputType.STACKTRACE)
                    .content(e.getMessage())
                    .build();
        }
    }


    @Override
    public List<MetaNavItem> getMetaNav(String systemComponentKey) {
        return null;
    }
}
