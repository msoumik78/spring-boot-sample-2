package org.example.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class MultiBackendCallerService {

    private final AsyncTaskExecutor asyncTaskExecutor;

    public MultiBackendCallerService(@Qualifier("async-task-executor") AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    public String callBackend() throws ExecutionException, InterruptedException, TimeoutException {
        StringBuilder responseCollated = new StringBuilder();
        List<String> backendList = List.of("crm1","crm2", "crm3");
        for (Future<String> backendFutures : createFutures(backendList)) {
            String actualResponse = backendFutures.get(20, TimeUnit.SECONDS);
            responseCollated.append(actualResponse);
        }
        return responseCollated.toString();
    }


    private List<Future<String>> createFutures(List<String> backendList) {
        List<Future<String>> futureOfResponses = new ArrayList<>();
        for (String backend : backendList) {
            futureOfResponses.add(getDataFromBackend(backend));
        }
        return futureOfResponses;
    }


    private Future<String> getDataFromBackend(String backendName) {
        return asyncTaskExecutor.submit(() -> {
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = "http://localhost:2345/"+backendName;
            ResponseEntity<String> response
                    = restTemplate.getForEntity(resourceUrl, String.class);
            return response.getBody();
        });
    }

}
