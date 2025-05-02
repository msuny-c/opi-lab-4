package ru.itmo.app.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ru.itmo.app.entity.AttemptEntity;

@Default
@ApplicationScoped
public class AttemptRepository implements IAttemptRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(AttemptEntity attempt) {
        try {
            entityManager.persist(attempt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<AttemptEntity> getAll() {
        try {
            var res = new ArrayList<>(entityManager
                    .createQuery("select attempt from AttemptEntity attempt", AttemptEntity.class).getResultList());
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
