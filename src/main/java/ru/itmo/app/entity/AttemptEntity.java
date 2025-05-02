package ru.itmo.app.entity;

import java.util.Date;
import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Inheritance
@Table(name = "attempt")
public @Data abstract class AttemptEntity {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    @Id private long id;
    @Column(name="x", nullable=false)
    private double x;
    @Column(name="y", nullable=false)
    private double y;
    @Column(name="r", nullable=false)
    private double r;
    @Column(name="created_at", nullable=false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name="success", nullable=false)
    private boolean success;
    @Override
    public String toString() {
        return String.format(Locale.US, "{\"x\": \"%f\", \"y\": \"%f\", \"r\": \"%f\", \"success\": \"%b\"}", x, y, r, success);
    }
}