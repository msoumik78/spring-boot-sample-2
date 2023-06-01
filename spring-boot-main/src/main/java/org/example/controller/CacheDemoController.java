package org.example.controller;

import com.hazelcast.spring.cache.HazelcastCache;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/cache")
@RequiredArgsConstructor
public class CacheDemoController {

    private final CacheManager cacheManager;

    @GetMapping(produces = {"application/json"})
    @SneakyThrows
    public String getFromCache() {
        Cache cache = cacheManager.getCache("test-cache1");
        if (cache.get("key1") == null)
            return "cache not present";
        else
            return cache.get("key1").get().toString();
    }


    @PostMapping
    @SneakyThrows
    public void putInCache() {
        Cache cache = cacheManager.getCache("test-cache1");
        cache.put("key1", "value1");
    }

}
