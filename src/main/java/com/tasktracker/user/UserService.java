package com.tasktracker.user;

import com.tasktracker.common.CRUDService;

public interface UserService extends CRUDService<User> {
//    User create(User user);
//
//    Collection<User> read();
//
//    User update(Long id,User user);
//
//    void delete(Long id);

    UserTasks getUserById(Long id);

}
