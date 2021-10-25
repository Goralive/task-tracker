package com.tasktracker.suites;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestSuiteConfiguration.class)
@AutoConfigureMockMvc
public interface BaseTestSuite {
}
