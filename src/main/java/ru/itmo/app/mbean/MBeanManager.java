package ru.itmo.app.mbean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class MBeanManager {
    
    @Inject
    private PointStatisticsMBean pointStatisticsMBean;
    
    @Inject
    private ClickIntervalMBean clickIntervalMBean;
    
    private ObjectName pointStatisticsName;
    private ObjectName clickIntervalName;
    
    @PostConstruct
    public void registerMBeans() {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            
            pointStatisticsName = new ObjectName("ru.itmo.app.mbean:type=PointStatistics");
            mBeanServer.registerMBean(pointStatisticsMBean, pointStatisticsName);
            
            clickIntervalName = new ObjectName("ru.itmo.app.mbean:type=ClickInterval");
            mBeanServer.registerMBean(clickIntervalMBean, clickIntervalName);
            
            System.out.println("MBeans registered successfully");
        } catch (Exception e) {
            System.err.println("Error registering MBeans: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @PreDestroy
    public void unregisterMBeans() {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            
            if (pointStatisticsName != null) {
                mBeanServer.unregisterMBean(pointStatisticsName);
            }
            if (clickIntervalName != null) {
                mBeanServer.unregisterMBean(clickIntervalName);
            }
            
            System.out.println("MBeans unregistered successfully");
        } catch (Exception e) {
            System.err.println("Error unregistering MBeans: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 