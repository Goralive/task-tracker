package com.tasktracker.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class UserConfig {
    @Bean
    public AtomicLong atomicLong() {
        return new AtomicLong(1);
    }

    @Bean
    public Map<Long, User> userMap() {
        return new HashMap<>();
    }

    @Bean
    public EmailValidation emailValidation() {
        return new EmailValidation();
    }

    @Bean
    public UserValidation userValidation() {
        return new UserValidation();
    }
}
