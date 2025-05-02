package ru.itmo.app.bean;

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Named;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import ru.itmo.app.validation.Scale;

@Named
@RequestScoped
public @Data class AttemptBackingBean implements Serializable {
    @Scale(value = 1)
    @Min(value = -4)
    @Max(value = 4)
    @ManagedProperty("#{param.x}")
    private double x;
    @Min(value = -3)
    @Max(value = 5)
    @ManagedProperty("#{param.y}")
    private double y;
    @Scale(value = 1)
    @DecimalMin(value = "0.1")
    @Max(value = 3)
    @ManagedProperty("#{param.r}")
    private double r;
    @ManagedProperty("#{param.type}")
    private String type;
}
