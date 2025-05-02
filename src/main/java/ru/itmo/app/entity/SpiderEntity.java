package ru.itmo.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class SpiderEntity extends AttemptEntity {
    @Column(name="legs_quantity")
    private int legsQuantity;
    public SpiderEntity() {
        super();
        legsQuantity = (int) Math.random() * 10;
    }
}
