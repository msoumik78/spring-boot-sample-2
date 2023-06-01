package org.demo;

import com.hazelcast.config.CacheSimpleConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;

public class HazelcastServer {
    public static void main(String[] args) {
        Hazelcast.newHazelcastInstance(
                new Config()
                        .setClusterName("cluster.name")
                        .addCacheConfig(new CacheSimpleConfig().setName("test-cache")));
    }
}
