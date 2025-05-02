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
    private AreaCalculatorMBean areaCalculatorMBean;
    
    private ObjectName pointStatisticsName;
    private ObjectName areaCalculatorName;
    
    @PostConstruct
    public void registerMBeans() {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            
            // Register PointStatisticsMBean
            pointStatisticsName = new ObjectName("ru.itmo.app.mbean:type=PointStatistics");
            mBeanServer.registerMBean(pointStatisticsMBean, pointStatisticsName);
            
            // Register AreaCalculatorMBean
            areaCalculatorName = new ObjectName("ru.itmo.app.mbean:type=AreaCalculator");
            mBeanServer.registerMBean(areaCalculatorMBean, areaCalculatorName);
            
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
            
            // Unregister MBeans
            if (pointStatisticsName != null) {
                mBeanServer.unregisterMBean(pointStatisticsName);
            }
            if (areaCalculatorName != null) {
                mBeanServer.unregisterMBean(areaCalculatorName);
            }
            
            System.out.println("MBeans unregistered successfully");
        } catch (Exception e) {
            System.err.println("Error unregistering MBeans: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 