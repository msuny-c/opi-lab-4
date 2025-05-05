package ru.itmo.app.service;

import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import ru.itmo.app.bean.AttemptBackingBean;
import ru.itmo.app.checker.IAreaChecker;
import ru.itmo.app.entity.AntEntity;
import ru.itmo.app.entity.AttemptEntity;
import ru.itmo.app.entity.SpiderEntity;
import ru.itmo.app.mbean.ClickIntervalMBean;
import ru.itmo.app.mbean.PointStatisticsMBean;
import ru.itmo.app.repository.IAttemptRepository;

@Default
@ApplicationScoped
public class AttemptService implements IAttemptService {
    @Inject private IAttemptRepository repository;
    @Inject private IAreaChecker checker;
    @Inject private PointStatisticsMBean pointStatistics;
    @Inject private ClickIntervalMBean clickInterval;
    
    @Override
    public void save(AttemptBackingBean attempt) {
        AttemptEntity entity;
        if (attempt.getType().equals("ant")) entity = new AntEntity();
        else if (attempt.getType().equals("spider")) entity = new SpiderEntity();
        else return;
        
        var success = checker.inArea(attempt);
        entity.setX(attempt.getX());
        entity.setY(attempt.getY());
        entity.setR(attempt.getR());
        entity.setCreatedAt(new Date());
        entity.setSuccess(success);
        repository.save(entity);
        
        pointStatistics.addPoint(attempt.getX(), attempt.getY(), attempt.getR(), success);
        clickInterval.registerClick();
    }
    
    @Override
    public List<AttemptEntity> getAll() {
        return repository.getAll();
    }
}
