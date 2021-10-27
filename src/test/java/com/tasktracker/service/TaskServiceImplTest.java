package com.tasktracker.service;

import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.TaskRepository;
import com.tasktracker.repository.inmem.UserRepository;
import com.tasktracker.service.task.TaskServiceImpl;
import com.tasktracker.service.task.TaskTO;
import com.tasktracker.service.task.TaskValidator;
import org.junit.jupiter.api.AfterEach;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {
    private final TaskRepository taskRepository = mock(TaskRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final TaskServiceImpl taskService;

    private static final TaskEntity testTask =
            new TaskEntity(1L, "Test Title", "Test Description", 1L, 2L);

    private static final UserEntity userOne =
            new UserEntity(1L, "First Test User", "at1@gmail.com", false);

    private static final UserEntity userTwo =
            new UserEntity(2L, "Second Test User", "at2@gmail.com", false);


    {
        TaskValidator taskValidator = new TaskValidator(userRepository, taskRepository);
        taskService = new TaskServiceImpl(taskRepository, taskValidator);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(taskRepository, userRepository);
    }

    @Test
    void createTask() {
        when(taskRepository.create(any(TaskEntity.class)))
                .thenReturn(testTask);
        when(userRepository.getById(testTask.getReporter()))
                .thenReturn(userOne);
        when(userRepository.getById(testTask.getAssignee()))
                .thenReturn(userTwo);

        TaskTO expected = TaskTO.fromEntity(testTask);

        TaskTO requestToService =
                new TaskTO(null, testTask.getTitle(), testTask.getDescription(),
                        testTask.getReporter(), testTask.getAssignee());

        TaskTO result = taskService.create(requestToService);

        ArgumentCaptor<TaskEntity> captorTask = ArgumentCaptor.forClass(TaskEntity.class);
        ArgumentCaptor<Long> userId = ArgumentCaptor.forClass(Long.class);

        verify(taskRepository).create(captorTask.capture());
        verify(userRepository, times(2)).getById(userId.capture());
        assertThat(result).isEqualTo(expected);
        assertThat(captorTask.getValue())
                .usingRecursiveComparison()
                .isEqualTo(requestToService);
    }
}
