package com.tasktracker.common;

import com.tasktracker.task.Task;
import com.tasktracker.user.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class ApplicationConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public AtomicLong atomicLong() {
        return new AtomicLong(1);
    }

    @Bean
    public Map<Long, User> userMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Task> taskMap() {
        return new HashMap<>();
    }
}
