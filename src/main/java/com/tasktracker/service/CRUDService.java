package com.tasktracker.service;


import java.util.Collection;

public interface CRUDService<T> {

    T create(T entity);

    Collection<T> fetchAll();

    T update(Long id, T entity);

    void delete(Long id);

}
