package org.example.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientConnectionStrategyConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@EnableCaching
@Data
@Configuration
@ConfigurationProperties("hazelcast.credentials")
public class HazelcastConfig {
    private List<String> ipList;
    private String clusterName;
    private String connectionTimeoutMillis;
    private String heartbeatTimeoutMillis;

    @Bean
    public CacheManager getHazelcastCacheManager(){
        List<String> ipList = new ArrayList<>();
        ipList.addAll(ipList);

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(clusterName);
        clientConfig.getNetworkConfig().setAddresses(ipList);
        clientConfig.getNetworkConfig().setSmartRouting(true);
        clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig().
                setClusterConnectTimeoutMillis(Integer.parseInt(connectionTimeoutMillis));
        clientConfig.getConnectionStrategyConfig().setAsyncStart(true);
        clientConfig.getConnectionStrategyConfig().setReconnectMode(ClientConnectionStrategyConfig.ReconnectMode.ASYNC);
        clientConfig.setProperty("hazelcast.client.heartbeat.timeout", heartbeatTimeoutMillis);

        HazelcastInstance hazelcastInstance =  HazelcastClient.newHazelcastClient(clientConfig);
        return new HazelcastCacheManager(hazelcastInstance);
    }

}
