package org.example.dao;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.BankCustomer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "crm", url = "http://localhost:2345")
public interface IDao {

    @PostMapping(value = "/CRM/spi/1/users")
    void createCustomer(BankCustomer bankCustomer);

    @GetMapping(value = "/CRM/spi/1/users/{customerName}")
    @CircuitBreaker(name="crmRemoteCall")
    BankCustomer getCustomerDetail(@PathVariable("customerName") String bankCustomerName);

    @DeleteMapping(value = "/CRM/spi/1/users/{customerName}")
    void removeCustomerDetail(@PathVariable("customerName") String bankCustomerName);

    @PutMapping(value = "/CRM/spi/1/users/{customerName}")
    void updateCustomerDetail(@PathVariable("customerName") String bankCustomerName, BankCustomer bankCustomer);


}
