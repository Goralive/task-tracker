package com.tasktracker.service.user;

import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.UserRepository;
import com.tasktracker.service.exception.TaskException;
import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.repository.inmem.TaskRepository;
import com.tasktracker.service.task.TaskTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserValidator userValidator;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository,
                           UserValidator userValidator) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.userValidator = userValidator;
    }

    @Override
    public UserTO create(UserTO userTO) {
        log.info("Creating user {} with email {}", userTO.name, userTO.email);
        userValidator.validateNew(userTO);

        UserEntity toSave = new UserEntity(userTO.id, userTO.name, userTO.email);

        UserEntity saved = userRepository.create(toSave);
        return UserTO.fromEntity(saved);
    }

    @Override
    public UserTO update(Long id, UserTO requestedData) {
        userValidator.checkUpdate(id, requestedData);
        UserEntity toSave = updateUserEntity(userRepository.getById(id), requestedData);

        log.info("Update user {} with data {}", id, toSave);
        userRepository.update(id, toSave);
        return UserTO.fromEntity(toSave);
    }


    @Override
    public void delete(Long id) {
        userValidator.findById(id);

        UserEntity existing = userRepository.getById(id);
        log.info("Delete user {}", existing);
        existing.setDeleted(true);
        userRepository.update(existing.getId(), existing);
    }

    @Override
    public UserTaskTO getUserById(Long id) {
        log.info("Get user by id {}", id);
        userValidator.findById(id);
        UserTO user = UserTO.fromEntity(userRepository.getById(id));
        List<TaskTO> taskList = taskRepository.getTasksByAssigneeId(id).stream()
                .map(TaskTO::fromEntity)
                .collect(Collectors.toList());
        return new UserTaskTO(user, taskList);
    }

    @Override
    public TaskTO assignTask(Long userId, Long taskId) {
        userValidator.findById(userId);
        TaskEntity toSave = taskRepository.getById(taskId);
        if (toSave == null) {
            throw new TaskException(taskId);
        }
        toSave.setAssignee(userId);
        TaskEntity saved = taskRepository.assignTask(taskId, toSave);
        return TaskTO.fromEntity(saved);
    }

    @Override
    public List<UserTO> fetchAll() {
        return userRepository.getAll().stream()
                .map(UserTO::fromEntity)
                .filter(u -> !u.deleted)
                .collect(Collectors.toList());
    }

    private UserEntity updateUserEntity(UserEntity existing, UserTO updateUserData) {
        UserEntity entity = new UserEntity();

        entity.setId(existing.getId());
        if (updateUserData.name == null || updateUserData.name.isBlank()) {
            entity.setName(existing.getName());
        } else {
            entity.setName(updateUserData.name);
        }

        if (updateUserData.email == null || updateUserData.email.isBlank()) {
            entity.setEmail(existing.getEmail());
        } else {
            entity.setEmail(updateUserData.email);
        }
        entity.setDeleted(updateUserData.deleted);

        return entity;
    }
}
