package org.example.config;

import lombok.RequiredArgsConstructor;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfig {

    private final FeignProperties feignProperties;

    @Bean
    public OkHttpClient feignClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(feignProperties.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(feignProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(feignProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(10,5,TimeUnit.SECONDS))
                .build();
    }
}
