package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(value="management.endpoints.customization", havingValue="true")
public class WebSecurityConfig {

    @Value("${management.endpoints.web.userid}")
    private String userId;

    @Value("${management.endpoints.web.password}")
    private String password;

    @Autowired
    WebSecurityConfigProps webSecurityConfigProps;

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        System.out.println("Management userid = "+userId+", password = "+password);
        UserDetails admin = User.withUsername(userId)
                .password(passwordEncoder.encode(password))
                .roles("ADMIN_ROLE")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        // Regular endpoints (protected by JWT)
                        .requestMatchers(webSecurityConfigProps.getEndpoints().toArray(String[]::new))
                        .permitAll()
                        // Other management-endponts require management-role
                        .requestMatchers("/management/**")
                        .hasRole("ADMIN_ROLE")
                )
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable() //NOSONAR
                .formLogin().disable();

        return http.build();
    }

}
