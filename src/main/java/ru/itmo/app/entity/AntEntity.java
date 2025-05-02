package ru.itmo.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class AntEntity extends AttemptEntity {
    @Column(name="body_color")
    private String bodyColor;
    public AntEntity() {
        super();
        String[] colors = {"black", "green", "purple"};
        bodyColor = colors[(int) (Math.random() * colors.length)];
    }
}
