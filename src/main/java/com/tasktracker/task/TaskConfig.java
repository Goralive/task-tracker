package com.tasktracker.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TaskConfig {

    @Bean
    public Map<Long, Task> taskMap() {
        return new HashMap<>();
    }
}
