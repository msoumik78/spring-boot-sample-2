package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@EnableScheduling
@Slf4j
@ConditionalOnProperty(value="schedule.batchjobs.enabled", havingValue="true")
@RequiredArgsConstructor
public class BankCustomersScheduledService {

    private final SyncAsyncService syncAsyncService;

    @Scheduled(fixedRateString = "${schedule.batchjob1.fixed-rate}", initialDelayString = "${schedule.batchjob1.initial-delay}")
    public void runBatchConsolidationJob1(){
        log.info("Running batch job 1");
    }

    @Scheduled(cron = "${schedule.batchjob2}")
    public void runBatchConsolidationJob2(){
        log.info("Running batch job 2");
    }

    @Scheduled(cron = "${schedule.batchjob3}")
    @SneakyThrows
    public void runBatchConsolidationJob3(){
        String uuid = UUID.randomUUID().toString();
        log.info("Running batch job 3 with UUID = {} ", uuid);
        syncAsyncService.performLongRunningOperation(uuid);
    }

}
