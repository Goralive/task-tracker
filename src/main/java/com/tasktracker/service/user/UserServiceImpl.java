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
        UserEntity user = new UserEntity(userTO.id, userTO.name, userTO.email);
        log.info("Creating user {} with email {}", user.getName(), user.getEmail());
        userValidator.validateNew(user);
        UserEntity saved = userRepository.create(user);
        return UserTO.fromEntity(saved);
    }

    @Override
    public UserTO update(Long id, UserTO updateUserData) {
        userValidator.checkUpdate(id, updateUserData);
        UserEntity existing = userRepository.getById(id);

        log.info("Update user {} with data {}", id, updateUserData);
        UserEntity response = userRepository.update(id, existing);
        return UserTO.fromEntity(response);
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
        UserEntity user = userRepository.getById(id);
        List<TaskEntity> taskList = taskRepository.getByAssigneeId(id);
        return new UserTaskTO(user, taskList);
    }

    @Override
    public TaskTO assignTask(Long userId, Long taskId) {
        userValidator.findById(userId);
        TaskEntity tasksById = taskRepository.getById(taskId);
        if (tasksById == null) {
            throw new TaskException(taskId);
        }
        tasksById.setAssignee(userId);
        taskRepository.update(taskId, tasksById);
        return new TaskTO(tasksById.getId(),tasksById.getTitle(),tasksById.getDescription(),1L,tasksById.getAssignee());
    }

    @Override
    public List<UserTO> fetchAll() {
        return null;
    }

}
