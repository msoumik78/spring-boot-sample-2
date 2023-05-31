package org.example.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@EnableAsync
public class SyncAsyncService {

    public String getResponseSync() throws Exception{
        Thread.sleep(10000);
        return "Sync Response";
    }

    @Async
    public Future<String> getResponseASync() throws Exception{
        Thread.sleep(10000);
        return CompletableFuture.completedFuture("ASync Response");
    }


    @Async
    public void performLongRunningOperation(String uuid) throws Exception{
        Thread.sleep(10000);
    }

}
