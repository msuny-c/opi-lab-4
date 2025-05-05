package ru.itmo.app.mbean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ClickIntervalMBean implements ClickIntervalMXBean, Serializable {
    
    private static final int MAX_INTERVALS = 100;
    private final Queue<Long> clickTimestamps = new LinkedList<>();
    private final Queue<Long> intervals = new LinkedList<>();
    private long totalIntervalTime = 0;
    private long lastClickTime = 0;
    private int intervalCount = 0;
    
    @PostConstruct
    public void init() {
    }
    
    @Override
    public double getAverageInterval() {
        if (intervalCount == 0) {
            return 0;
        }
        return (double) totalIntervalTime / intervalCount;
    }
    
    @Override
    public long getLastInterval() {
        if (intervals.isEmpty()) {
            return 0;
        }
        return intervals.peek();
    }
    
    @Override
    public int getClickCount() {
        return clickTimestamps.size();
    }
    
    @Override
    public int getIntervalCount() {
        return intervalCount;
    }
    
    public void registerClick() {
        long currentTime = System.currentTimeMillis();
        
        clickTimestamps.add(currentTime);
        if (clickTimestamps.size() > MAX_INTERVALS + 1) {
            clickTimestamps.poll();
        }
        
        if (lastClickTime > 0) {
            long interval = currentTime - lastClickTime;
            intervals.add(interval);
            totalIntervalTime += interval;
            intervalCount++;
            
            if (intervals.size() > MAX_INTERVALS) {
                long oldestInterval = intervals.poll();
                totalIntervalTime -= oldestInterval;
                intervalCount--;
            }
        }
        
        lastClickTime = currentTime;
    }
} 