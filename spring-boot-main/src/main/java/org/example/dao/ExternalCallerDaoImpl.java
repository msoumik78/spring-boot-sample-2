package org.example.dao;

import org.example.BankCustomer;
import org.example.exception.CRMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component(value="ExternalCallerDao")
public class ExternalCallerDaoImpl implements IDao{

    private final WebClient webClient;

    public ExternalCallerDaoImpl(WebClient.Builder webClientBuilder, @Value("${crm.url}") String CRMUrl) {
        this.webClient = webClientBuilder.baseUrl(CRMUrl).build();
    }

    @Override
    public void createCustomer(BankCustomer bankCustomer) {
        try {
            String response = this.webClient.post()
                    .uri("/CRM/spi/1/users")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(bankCustomer), BankCustomer.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Posted successfully to CRM");
        } catch (Exception e) {
            throw new CRMException("Unable to post to CRM , error = "+e.getMessage());
        }
    }

    @Override
    public BankCustomer getCustomerDetail(String bankCustomerName) {
        return this.webClient
                .get()
                .uri("/CRM/spi/1/users/{bankCustomerName}", bankCustomerName)
                .retrieve()
                .bodyToMono(BankCustomer.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
                .block();
    }

    @Override
    public void removeCustomerDetail(String bankCustomerName) {
        try {
            String response = this.webClient.delete()
                    .uri("/CRM/spi/1/users/{bankCustomerName}", bankCustomerName)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Deleted successfully to CRM");
        } catch (Exception e) {
            throw new CRMException("Unable to delete in CRM , error = "+e.getMessage());
        }
    }

    @Override
    public void updateCustomerDetail(String bankCustomerName, BankCustomer bankCustomer) {
        try {
            String response = this.webClient.put()
                    .uri("/CRM/spi/1/users/{bankCustomerName}", bankCustomerName)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(bankCustomer), BankCustomer.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Updated successfully to CRM");
        } catch (Exception e) {
            throw new CRMException("Unable to update in CRM , error = "+e.getMessage());
        }
    }
}
