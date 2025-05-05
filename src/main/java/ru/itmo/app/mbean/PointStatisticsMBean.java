package ru.itmo.app.mbean;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class PointStatisticsMBean extends NotificationBroadcasterSupport 
    implements PointStatisticsMXBean, Serializable {

    private final AtomicInteger totalPoints = new AtomicInteger(0);
    private final AtomicInteger pointsInArea = new AtomicInteger(0);
    private int consecutiveMisses = 0;
    private long sequenceNumber = 1;
    
    @PostConstruct
    public void init() {
    }
    
    @Override
    public int getTotalPoints() {
        return totalPoints.get();
    }
    
    @Override
    public int getPointsInArea() {
        return pointsInArea.get();
    }
    
    @Override
    public int getConsecutiveMisses() {
        return consecutiveMisses;
    }
    
    public void addPoint(double x, double y, double r, boolean inArea) {
        totalPoints.incrementAndGet();
        
        if (inArea) {
            pointsInArea.incrementAndGet();
            consecutiveMisses = 0;
        } else {
            consecutiveMisses++;
            
            if (consecutiveMisses == 4) {
                Notification notification = new Notification(
                    "ConsecutiveMisses",
                    this.getClass().getName(),
                    sequenceNumber++,
                    System.currentTimeMillis(),
                    "Reached 4 consecutive misses"
                );
                
                notification.setUserData("Number of consecutive misses: 4");
                
                sendNotification(notification);
            }
        }
    }
    
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] {
            "ConsecutiveMisses"
        };
        
        String name = Notification.class.getName();
        String description = "Notification about reaching 4 consecutive misses";
        MBeanNotificationInfo info = new MBeanNotificationInfo(
            types, name, description
        );
        
        return new MBeanNotificationInfo[] {info};
    }
} 