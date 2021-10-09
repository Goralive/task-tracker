package com.tasktracker.common;

import java.util.Collection;

public interface CommonDAO<T> {

    T create(final T entity);

    T update(final Long id, final T entity);

    T getById(final Long id);

    Collection<T> getAll();
}
