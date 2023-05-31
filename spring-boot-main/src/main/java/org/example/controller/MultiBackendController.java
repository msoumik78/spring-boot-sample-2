package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.BankCustomer;
import org.example.service.MultiBackendCallerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/multi-backend")
@RequiredArgsConstructor
public class MultiBackendController {

    private final MultiBackendCallerService multiBackendCallerService;

    @GetMapping(produces = {"application/json"})
    @SneakyThrows
    public String getCustomerDetails()  {
        return multiBackendCallerService.callBackend();
    }

}
