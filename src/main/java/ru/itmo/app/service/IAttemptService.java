package ru.itmo.app.service;

import java.util.List;

import ru.itmo.app.bean.AttemptBackingBean;
import ru.itmo.app.entity.AttemptEntity;

public interface IAttemptService {
    void save(AttemptBackingBean attempt);
    List<AttemptEntity> getAll();
}
