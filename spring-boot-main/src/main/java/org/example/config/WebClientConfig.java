package org.example.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;


@Configuration
public class WebClientConfig {

    @Value("${webclient.connection-time-out-millis}")
    private int webClientConnectionTimeout;

    @Value("${webclient.read-time-out-secs}")
    private int webClientReadTimeout;

    @Value("${webclient.write-time-out-secs}")
    private int webClientWriteTimeout;

    @Bean
    public WebClient.Builder webClientBuilder() {
        ConnectionProvider provider =
                ConnectionProvider.builder("custom")
                        .maxConnections(50)
                        .maxIdleTime(Duration.ofSeconds(20))
                        .maxLifeTime(Duration.ofSeconds(60))
                        .pendingAcquireTimeout(Duration.ofSeconds(60))
                        .evictInBackground(Duration.ofSeconds(120))
                        .build();

        HttpClient reactorHttpClient = HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientConnectionTimeout)
                .doOnConnected(connection -> connection
                                .addHandlerLast(new ReadTimeoutHandler(webClientReadTimeout))
                                .addHandlerLast(new WriteTimeoutHandler(webClientWriteTimeout)));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(reactorHttpClient));
    }
}
