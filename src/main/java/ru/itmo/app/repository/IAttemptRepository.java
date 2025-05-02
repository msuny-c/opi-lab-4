package ru.itmo.app.repository;

import java.util.List;

import ru.itmo.app.entity.AttemptEntity;

public interface IAttemptRepository {
    void save(AttemptEntity attempt);
    List<AttemptEntity> getAll();
}