package ru.itmo.app.mbean;

public interface PointStatisticsMXBean {
    int getTotalPoints();
    int getPointsInArea();
    int getConsecutiveMisses();
} 