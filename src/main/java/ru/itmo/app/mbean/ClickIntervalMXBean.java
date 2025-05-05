package ru.itmo.app.mbean;

public interface ClickIntervalMXBean {
    double getAverageInterval();
    long getLastInterval();
    int getClickCount();
    int getIntervalCount();
} 