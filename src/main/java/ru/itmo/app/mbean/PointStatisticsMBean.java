package ru.itmo.app.mbean;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.AttributeChangeNotification;
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
    private long sequenceNumber = 1;
    
    @PostConstruct
    public void init() {
        // Initialize if needed
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
    public double getSuccessRate() {
        int total = totalPoints.get();
        return total == 0 ? 0 : (double) pointsInArea.get() / total * 100.0;
    }
    
    public void addPoint(double x, double y, double r, boolean inArea) {
        totalPoints.incrementAndGet();
        if (inArea) {
            pointsInArea.incrementAndGet();
        }
        
        // Check if point is outside the displayed area
        if (x < -4 || x > 4 || y < -3 || y > 5) {
            Notification notification = new AttributeChangeNotification(
                this,
                sequenceNumber++,
                System.currentTimeMillis(),
                "Point out of bounds",
                "Point coordinates",
                "Point",
                "In bounds",
                "Out of bounds: x=" + x + ", y=" + y
            );
            sendNotification(notification);
        }
    }
    
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] {
            AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(
            types, name, description
        );
        
        return new MBeanNotificationInfo[] {info};
    }
} 