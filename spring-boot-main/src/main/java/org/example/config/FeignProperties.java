package org.example.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.cloud.openfeign.httpclient")
@Data
public class FeignProperties {
    public static final int DEFAULT_TIMEOUT = 250;
    public static final int DEFAULT_MAX_CONNECTIONS = 200;
    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 150;
    @NotNull
    private int connectionRequestTimeoutMilliSeconds = DEFAULT_TIMEOUT;

    @NotNull
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;

    @NotNull
    private int maxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;

    @NotNull
    private int connectTimeout = DEFAULT_TIMEOUT;

    @NotNull
    private int readTimeout = DEFAULT_TIMEOUT;

    @NotNull
    private int socketTimeout = DEFAULT_TIMEOUT;

    @NotNull
    private String baseUrlOfTarget;

    @NotNull
    private String originatingApplicationName;
}
