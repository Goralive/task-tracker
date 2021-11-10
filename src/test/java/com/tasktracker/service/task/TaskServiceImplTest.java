package com.tasktracker.service.task;

import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.TaskRepository;
import com.tasktracker.repository.inmem.UserRepository;
import com.tasktracker.service.exception.TaskException;
import com.tasktracker.service.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    void failedTaskCreationMissingReporter() {
        TaskTO request = new TaskTO(null, "No reporter", "Test desc", null, null);

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id 'null'");
        verify(taskRepository, never()).create(any());
    }

    @Test
    void failedTaskCreationMissingTitle() {
        when(userRepository.getById(1L))
                .thenReturn(userOne);
        TaskTO request = new TaskTO(null, null, "Test desc", 1L, null);

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(TaskException.class)
                .hasMessage("title is mandatory");
        verify(taskRepository, never()).create(any());
    }

    @Test
    void failedTaskCreationMissingDescription() {
        when(userRepository.getById(1L))
                .thenReturn(userOne);
        TaskTO request = new TaskTO(null, "Test title", null, 1L, null);

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(TaskException.class)
                .hasMessage("description is mandatory");
        verify(taskRepository, never()).create(any());
    }

    @Test
    void failedTaskReporterNotFound() {
        TaskTO request = new TaskTO(null, "Test title", null, 1L, null);

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id '1'");
        verify(taskRepository, never()).create(any());
    }

    @Test
    void failedTaskReporterDeleted() {
        TaskTO request = new TaskTO(null, "Test title", null, 1L, null);
        UserEntity userDeleted =
                new UserEntity(1L, "Deleted Test User", "at1Del@gmail.com", true);

        when(userRepository.getById(1L))
                .thenReturn(userDeleted);

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Reporter not found with id '1'");
        verify(taskRepository, never()).create(any());

    }
}
