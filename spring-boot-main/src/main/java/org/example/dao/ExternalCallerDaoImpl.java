package org.example.dao;

import org.example.BankCustomer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component(value="ExternalCallerDao")
public class ExternalCallerDaoImpl implements IDao{

    private final WebClient webClient;

    public ExternalCallerDaoImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:2345").build();
    }

    @Override
    public void createCustomer(BankCustomer bankCustomer) {

    }

    @Override
    public BankCustomer getCustomerDetail(String bankCustomerName) {
        return this.webClient.get().uri("/CRM/spi/1/users/{bankCustomerName}", bankCustomerName)
                .retrieve().bodyToMono(BankCustomer.class).block();
    }

    @Override
    public void removeCustomerDetail(String bankCustomerName) {

    }

    @Override
    public void updateCustomerDetail(String bankCustomerName, BankCustomer bankCustomer) {

    }
}
