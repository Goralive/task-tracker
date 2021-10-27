package com.tasktracker.service;

import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.UserRepository;
import com.tasktracker.service.user.UserServiceImpl;
import com.tasktracker.service.user.UserTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.ArgumentMatchers.any;

public class UserServiceImplTest {


    public static final String NAME = "Klistan Orbertovich";
    public static final String EMAIL = "Bobby@test.com";

    public static final UserTO USER = new UserTO(1L, NAME, EMAIL, false);
    public static final UserEntity ENTITY = new UserEntity(USER.id, NAME, EMAIL, USER.deleted);

    private final UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    @BeforeEach
    public void tearDown() {
        Mockito.reset(userService, userRepository);
    }

    @Test
    void createUser() {
        Mockito.when(userService.create(any(UserTO.class)))
                .thenReturn(USER);
        Mockito.when(userRepository.create(any(UserEntity.class)))
                .thenReturn(ENTITY);

        Mockito.verify(userService).create(USER);
    }
}
