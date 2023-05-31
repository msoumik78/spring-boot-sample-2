package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.service.SyncAsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/1/sync-async")
@RequiredArgsConstructor
public class SyncAsyncController {

    private final SyncAsyncService syncAsyncService;

    @GetMapping(value = "/sync", produces = {"application/json"})
    @SneakyThrows
    public String getSyncResponse() {
        return syncAsyncService.getResponseSync();
    }

    @GetMapping(value = "/async", produces = {"application/json"})
    @SneakyThrows
    public Future<String> getAsyncResponse() {
        return syncAsyncService.getResponseASync();
    }


    @GetMapping(value = "/async-void", produces = {"application/json"})
    @SneakyThrows
    public void asyncVoidJob() {
        String uuid = UUID.randomUUID().toString();
        syncAsyncService.performLongRunningOperation(uuid);
    }

}
