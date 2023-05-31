package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Slf4j
public class BankCustomersScheduledService {

    @Scheduled(fixedRateString = "${schedule.batchjob1.fixed-rate}", initialDelayString = "${schedule.batchjob1.initial-delay}")
    public void runBatchConsolidationJob1(){
        log.info("Running batch job 1");
    }

    @Scheduled(cron = "${schedule.batchjob2}")
    public void runBatchConsolidationJob2(){
        log.info("Running batch job 2");
    }
}
