package org.example.config;

import feign.Feign;
import feign.hc5.ApacheHttp5Client;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfig {

    private final FeignProperties feignProperties;


    @Bean
    public CloseableHttpClient feignHttpClient() {
        RequestConfig config = getRequestConfig();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultConnectionConfig(ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(feignProperties.getConnectTimeout()))
                .setSocketTimeout(Timeout.ofMilliseconds(feignProperties.getSocketTimeout()))
                .build());
        connectionManager.setDefaultMaxPerRoute(feignProperties.getMaxConnectionsPerRoute());
        connectionManager.setMaxTotal(feignProperties.getMaxConnections());
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config)
                .setConnectionManager(connectionManager)
                .disableConnectionState()
                .build();
        return httpClient;
    }

    @Bean
    public Feign.Builder feignBuilder(CloseableHttpClient client) {
        return Feign.builder()
                .client(new ApacheHttp5Client(client));
    }


    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(
                        Timeout.ofMilliseconds(feignProperties.getConnectionRequestTimeoutMilliSeconds()))
                .build();
    }

}
