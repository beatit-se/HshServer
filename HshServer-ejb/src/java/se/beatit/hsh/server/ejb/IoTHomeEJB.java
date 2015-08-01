/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.ejb;

import se.beatit.hsh.server.ejb.dao.IoTHomeDAO;
import se.beatit.hsh.server.ejb.entities.IoTDevice;
import se.beatit.hsh.server.ejb.entities.IoTHome;
import se.beatit.hsh.server.ejb.entities.IoTMinuteUsage;
import se.beatit.hsh.server.ejb.exception.HomeNotFountException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author stefan
 */
@Stateless
//@LocalBean
public class IoTHomeEJB {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;
            
    private IoTHomeDAO ioTHomeDao;
    
    public enum Timespan {TODAY, YESTERDAY, THISMONTH, LASTMONTH};
    
    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Stockholm");
    
    public IoTHomeEJB() {
    }
    
    
    public IoTHome getHome(String name) {
        IoTHome home = getDao().getHome(em, name);
        
        if(home != null) {
            return home;
        }
        throw new HomeNotFountException();
    }
    
    public IoTHome createHome(String name) {
        IoTHome home = getDao().getHome(em, name);
        
        if(home == null) {
            home = new IoTHome(name);
            em.persist(home);
            System.out.println("Home " + name + " created.");
        } else {
            System.out.println("Home " + name + " already exist.");
        }
        return home;
    }
    
    public List<IoTHome> getHomes() {
        return getDao().findAll(em);
    }
    
    public void addDeviceToHome(String home, String deviceName) {
        IoTHome ioTHome = getHome(home);
        
        IoTDevice iotDevice = new IoTDevice(deviceName);
        iotDevice.setIotHome(ioTHome);
        em.persist(iotDevice);
    }
    
    
    public List<IoTDevice> getHomeDevices(String home) {
        IoTHome ioTHome = getHome(home);
        return ioTHome.getIoTDevices();
    }
    
    public List<IoTDevice> getDevices(String homeName) {
        IoTHome ioTHome = getHome(homeName);
        return ioTHome.getIoTDevices();
    } 
    
    public long getTotalElectricityUsage(String home) {
        IoTHome ioTHome = getDao().getHomeWithElectricityUsage(em, home);
        
        List<IoTMinuteUsage> ioTMinuteUsages = ioTHome.getIoTMinuteUsages();
        long sum = 0;
        
        for (IoTMinuteUsage ioTMinuteUsage : ioTMinuteUsages) {
            sum += ioTMinuteUsage.getWhUsage();
        }
        return sum;
    }
    
    public long getTotalElectricityUsage(String home, Timespan timespan) {
        return getDao().getElectricityUsage(em, home, timespan);
    }
    
    public List<Long> getElectricityUsageList(String home) {
        int calendarRollField = Calendar.HOUR;
        
        Calendar start = Calendar.getInstance(TIME_ZONE);
        Calendar stop = Calendar.getInstance(TIME_ZONE);
        
        start.add(Calendar.DAY_OF_YEAR, -1);
        stop.add(Calendar.HOUR, 1);
        
        return getDao().getElectricityUsage(em, home, start, stop, calendarRollField);

    }
    
    public List<Long> getElectricityUsageStat(String home, Date start, Date stop, int rollFiealdResolution) {  
        Calendar startCalendar = Calendar.getInstance(TIME_ZONE);
        startCalendar.setTime(start);
        
        Calendar stopCalendar = Calendar.getInstance(TIME_ZONE);
        stopCalendar.setTime(stop);

        return getDao().getElectricityUsage(em, home, startCalendar, stopCalendar, rollFiealdResolution);
    }
    
    public void addElectricityUsage(String home, int wh) {
        IoTHome ioTHome = getHome(home);
        
        Calendar cal = Calendar.getInstance(TIME_ZONE);
        
        IoTMinuteUsage usage = new IoTMinuteUsage();
        usage.setIotHome(ioTHome);
        usage.setWhUsage(wh);
        
        usage.setWhYear(cal.get(Calendar.YEAR));
        usage.setWhMonth(cal.get(Calendar.MONTH));
        usage.setWhDay(cal.get(Calendar.DAY_OF_MONTH));
        usage.setWhHour(cal.get(Calendar.HOUR_OF_DAY));
        usage.setWhMinute(cal.get(Calendar.MINUTE));
        
        em.persist(usage);
    }
    
    
    
    private IoTHomeDAO getDao() {
        if(ioTHomeDao == null) {
            ioTHomeDao = new IoTHomeDAO();
        }
        return ioTHomeDao;
    }


}
