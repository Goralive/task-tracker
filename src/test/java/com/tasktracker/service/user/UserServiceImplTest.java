package com.tasktracker.service.user;

import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.TaskRepository;
import com.tasktracker.repository.inmem.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    public static final String NAME = "Klistan Orbertovich";
    public static final String EMAIL = "Bobby@test.com";

    public static final UserTO USER = new UserTO(null, NAME, EMAIL, false);
    public static final UserEntity ENTITY = new UserEntity(1L, NAME, EMAIL, USER.deleted);

    private final UserRepository userRepository = mock(UserRepository.class);
    private final TaskRepository taskRepository = mock(TaskRepository.class);

    private final UserServiceImpl userService;

    {
        UserValidator userValidator = new UserValidator(userRepository);
        userService = new UserServiceImpl(userRepository, taskRepository, userValidator);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(taskRepository, userRepository);
    }

    @Test
    void createUser() {
        when(userRepository.create(any(UserEntity.class)))
                .thenReturn(ENTITY);


        UserTO result = userService.create(USER);
        UserTO expected = UserTO.fromEntity(ENTITY);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        verify(userRepository).create(captor.capture());

        assertThat(result).isEqualTo(expected);
        assertThat(captor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(USER);
    }
}
