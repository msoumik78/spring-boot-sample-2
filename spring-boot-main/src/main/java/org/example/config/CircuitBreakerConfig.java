package org.example.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CircuitBreakerConfig {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    private void postConstruct() {
        circuitBreakerRegistry.getAllCircuitBreakers()
                .forEach(circuitBreaker -> circuitBreaker.getEventPublisher().onStateTransition(
                        event -> {
                            String name = event.getCircuitBreakerName();
                            String fromState = event.getStateTransition().getFromState().toString();
                            String toState = event.getStateTransition().getToState().toString();
                            if (toState.equals("OPEN")) {
                                log.error("{} circuitbreaker changed state from {} to {}", name, fromState, toState);
                            } else {
                                log.info("{} circuitbreaker changed state from {} to {}", name, fromState, toState);
                            }
                        }
                ));

    }

}
