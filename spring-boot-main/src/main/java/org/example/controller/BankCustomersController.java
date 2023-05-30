package org.example.controller;

import jakarta.validation.Valid;
import org.example.BankCustomer;
import org.example.service.BankCustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/1/bank-customers")
public class BankCustomersController {

    @Autowired
    BankCustomersService bankCustomersService;

    @GetMapping(value = "/{customerName}", produces = {"application/json"})
    public BankCustomer getCustomerDetails(@PathVariable("customerName") final String bankCustomerName) {
        return bankCustomersService.getCustomerDetail(bankCustomerName);
    }

    @PostMapping(produces = {"application/json"})
    public void createCustomer(@Valid @RequestBody BankCustomer bankCustomer) {
        bankCustomersService.createCustomer(bankCustomer);
    }

    @DeleteMapping(value = "/{customerName}", produces = {"application/json"})
    public void deleteCustomer(@PathVariable("customerName") final String bankCustomerName) {
        bankCustomersService.removeCustomer(bankCustomerName);
    }

    @PutMapping(value = "/{customerName}", produces = {"application/json"})
    public void updateCustomer(@PathVariable("customerName") final String bankCustomerName, @RequestBody BankCustomer bankCustomerUpdated) {
        bankCustomersService.updateCustomer(bankCustomerName, bankCustomerUpdated);
    }

}
