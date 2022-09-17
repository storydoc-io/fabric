package io.storydoc.fabric.elastic;

import io.storydoc.fabric.core.domain.CommandHandler;
import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.elastic.settings.ElasticSettings;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Map;

public class ElasticServiceBase extends StorageBase implements CommandHandler {

    public static final String SETTING_KEY__USER_NAME = "userName";
    public static final String SETTING_KEY__PASSWORD = "password";
    public static final String SETTING_KEY__HOST_NAME = "hostName";
    public static final String SETTING_KEY__PORT = "port";
    public static final String SETTING_KEY__SCHEMA_PATTERN = "schemaPattern";

    @Override
    public String systemType() {
        return "ELASTICSEARCH";
    }

    protected RestHighLevelClient getClient(ElasticSettings settings) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(settings.getUserName(), settings.getPassword()));

        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback = new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        };

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(settings.getHostName(), settings.getPort()))
                .setHttpClientConfigCallback(httpClientConfigCallback);

        return new RestHighLevelClient(restClientBuilder);

    }

    protected ElasticSettings toSettings(Map<String, String> settings) {
        return ElasticSettings.builder()
                .userName(settings.get(SETTING_KEY__USER_NAME))
                .password(settings.get(SETTING_KEY__PASSWORD))
                .hostName(settings.get(SETTING_KEY__HOST_NAME))
                .port(Integer.parseInt(settings.get(SETTING_KEY__PORT)))
                .schemaPattern(settings.get(SETTING_KEY__SCHEMA_PATTERN))
                .build();
    }

}
