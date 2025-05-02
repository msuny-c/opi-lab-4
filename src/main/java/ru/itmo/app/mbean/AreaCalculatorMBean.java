package ru.itmo.app.mbean;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class AreaCalculatorMBean implements AreaCalculatorMXBean, Serializable {
    
    @PostConstruct
    public void init() {
    }
    
    @Override
    public double calculateArea(double r) {
        // Calculate the area of the figure based on radius r
        // Circle (quarter): πr²/4
        double circleArea = Math.PI * Math.pow(r, 2) / 4;
        
        // Rectangle: r × (r/2)
        double rectangleArea = r * (r / 2);
        
        // Triangle: (r × r) / 2
        double triangleArea = (r * r) / 2;
        
        // Total area
        return circleArea + rectangleArea + triangleArea;
    }
} 