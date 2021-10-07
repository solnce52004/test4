package dev.example.dao.interfaces;

import java.util.List;

public interface CRUDDAO<T, R> {
    R create(T t);

    void remove(T t);

//    N findById(Long id);

    List<T> findAll();
}
