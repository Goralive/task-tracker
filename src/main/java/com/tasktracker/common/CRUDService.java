package com.tasktracker.common;

import java.util.Collection;

public interface CRUDService<T> {

    T create(T entity);

    Collection<T> read();

    T update(Long id, T entity);

    void delete(Long id);

}
