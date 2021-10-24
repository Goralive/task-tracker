package com.tasktracker.repository;

import java.util.Collection;

public interface IRepository<T> {

    T create(final T entity);

    T update(final Long id, final T entity);

    T getById(final Long id);

    Collection<T> getAll();
}
