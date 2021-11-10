package com.tasktracker.suites;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktracker.TaskTrackerApplication;
import com.tasktracker.configuration.ResetTable;
import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.function.Consumer;

@SpringBootTest(classes = TaskTrackerApplication.class)
@AutoConfigureMockMvc
public abstract class BaseTestSuite {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private List<ResetTable> resetTables;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clearUp() {
        resetTables.forEach(ResetTable::reset);
    }

    public String asJson(Object object) throws Exception {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(object);
    }

    public <T> T fromResponse(MvcResult result, Class<T> type) throws Exception {
        return new ObjectMapper()
                .readerFor(type)
                .readValue(result.getResponse().getContentAsString());
    }

    public <T> T fromJson(String json, Class<T> type) throws Exception {
        return new ObjectMapper()
                .readerFor(type)
                .readValue(json);
    }

    protected UserEntity createUser() {
        return createUser(userEntity -> {
        });
    }

    protected UserEntity createUser(Consumer<UserEntity> builder) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);
        userEntity.setDeleted(false);
        userEntity.setEmail("at-test@gmail.com");
        userEntity.setName("At-test_User");
        builder.accept(userEntity);
        return userRepository.create(userEntity);
    }
}
