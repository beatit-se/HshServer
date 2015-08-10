/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.ejb.dao;

import se.beatit.hsh.server.ejb.IoTHomeEJB;
import se.beatit.hsh.server.ejb.entities.IoTHome;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


/**
 *
 * @author stefan
 */
public class IoTHomeDAO {

    public IoTHomeDAO() {
    }

    public IoTHome getHome(EntityManager em, String name) {
        TypedQuery<IoTHome> q = em.createQuery("SELECT h FROM IoTHome h WHERE h.name = ?1", IoTHome.class);
        q.setParameter(1, name);
        try {
            return q.getSingleResult();    
        } catch(NoResultException nre) {
            return null;
        }
    }

    public IoTHome getHomeWithElectricityUsage(EntityManager em, String name) {
        TypedQuery<IoTHome> q = em.createQuery("SELECT h FROM IoTHome h JOIN FETCH h.ioTMinuteUsages WHERE h.name = ?1", IoTHome.class);
        q.setParameter(1, name);
        try {
            return q.getSingleResult();    
        } catch(NoResultException nre) {
            return null;
        }
    }

    public List<IoTHome> findAll(EntityManager em) {
        TypedQuery<IoTHome> q = em.createQuery("SELECT h FROM IoTHome h", IoTHome.class);
        try {
            return q.getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public long getElectricityUsage(EntityManager em, String home, IoTHomeEJB.Timespan timespan) {
        IoTHome ioTHome = getHome(em, home);
        
        TypedQuery<Long> q = em.createQuery("SELECT sum(u.whUsage) from IoTMinuteUsage u WHERE u.iotHome = ?1 AND u.whYear = ?2 AND u.whMonth = ?3 AND u.whDay = ?4", Long.class);
        q.setParameter(1, ioTHome);
        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Stockholm"));
        q.setParameter(2, cal.get(Calendar.YEAR));
        q.setParameter(3, cal.get(Calendar.MONTH));
        
        if(timespan == IoTHomeEJB.Timespan.TODAY) {
            q.setParameter(4, cal.get(Calendar.DAY_OF_MONTH));
        } else if(timespan == IoTHomeEJB.Timespan.YESTERDAY) {
            q.setParameter(4, cal.get(Calendar.DAY_OF_MONTH)-1);
        }

        return q.getSingleResult();
    }
    
    public List<Long> getElectricityUsage(EntityManager em, String home, Calendar start, Calendar stop, int calendarRollField) {
        IoTHome ioTHome = getHome(em, home);
        List<Long> result = new ArrayList<Long>();
        
        while(start.before(stop)) {
            TypedQuery<Long> q;
            switch (calendarRollField) {
                case Calendar.MINUTE:
                    q = em.createQuery("SELECT sum(u.whUsage) from IoTMinuteUsage u WHERE u.iotHome = ?1 AND u.whYear = ?2 AND u.whMonth = ?3 AND u.whDay = ?4 AND u.whHour = ?5 AND u.whMinute = ?6", Long.class);
                    q.setParameter(1, ioTHome);
                    q.setParameter(2, start.get(Calendar.YEAR));
                    q.setParameter(3, start.get(Calendar.MONTH));
                    q.setParameter(4, start.get(Calendar.DAY_OF_MONTH));
                    q.setParameter(5, start.get(Calendar.HOUR_OF_DAY));
                    q.setParameter(6, start.get(Calendar.MINUTE));
                    break;
                case Calendar.HOUR_OF_DAY:
                    q = em.createQuery("SELECT sum(u.whUsage) from IoTMinuteUsage u WHERE u.iotHome = ?1 AND u.whYear = ?2 AND u.whMonth = ?3 AND u.whDay = ?4 AND u.whHour = ?5", Long.class);
                    q.setParameter(1, ioTHome);
                    q.setParameter(2, start.get(Calendar.YEAR));
                    q.setParameter(3, start.get(Calendar.MONTH));
                    q.setParameter(4, start.get(Calendar.DAY_OF_MONTH));
                    q.setParameter(5, start.get(Calendar.HOUR_OF_DAY));
                    break;
                case Calendar.DAY_OF_MONTH:
                    q = em.createQuery("SELECT sum(u.whUsage) from IoTMinuteUsage u WHERE u.iotHome = ?1 AND u.whYear = ?2 AND u.whMonth = ?3 AND u.whDay = ?4", Long.class);
                    q.setParameter(1, ioTHome);
                    q.setParameter(2, start.get(Calendar.YEAR));
                    q.setParameter(3, start.get(Calendar.MONTH));
                    q.setParameter(4, start.get(Calendar.DAY_OF_MONTH));
                    break;
                case Calendar.MONTH:
                    q = em.createQuery("SELECT sum(u.whUsage) from IoTMinuteUsage u WHERE u.iotHome = ?1 AND u.whYear = ?2 AND u.whMonth = ?3", Long.class);
                    q.setParameter(1, ioTHome);
                    q.setParameter(2, start.get(Calendar.YEAR));
                    q.setParameter(3, start.get(Calendar.MONTH));
                    break;
                default:
                    q = em.createQuery("SELECT sum(u.whUsage) from IoTMinuteUsage u WHERE u.iotHome = ?1 AND u.whYear = ?2", Long.class);
                    q.setParameter(1, ioTHome);
                    q.setParameter(2, start.get(Calendar.YEAR));
                    break;
            }
            
            Long sum = q.getSingleResult();
            
            if(sum != null) {
                result.add(sum);    
            } else {
                result.add((long)0);
            }
            
            start.add(calendarRollField, 1);
        }      
        return result;
    }
    
    public List<Float> getTemperature(EntityManager em, String home, String location, Calendar start, Calendar stop, int calendarRollField) {
        IoTHome ioTHome = getHome(em, home);
        List<Float> result = new ArrayList<Float>();
        
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT avg(u.temperature) from IoTTemperature u");
        queryString.append(" WHERE u.iotHome = ?1");
        queryString.append(" AND u.location = ?2");
        queryString.append(" AND u.tYear = ?3");
        
        if(rollLessThanYear(calendarRollField)) {
            queryString.append(" AND u.tMonth = ?4");
        }
        if(rollLessThanMonth(calendarRollField)) {
            queryString.append(" AND u.tDay = ?5");
        }
        if(rollLessThanDay(calendarRollField)) {
            queryString.append(" AND u.tHour = ?6");
        }
        if(rollLessThanHour(calendarRollField)) {
            queryString.append(" AND u.tMinute = ?7");
        }
        
        
        while(start.before(stop)) {
            TypedQuery<Double> q = em.createQuery(queryString.toString(), Double.class);
            q.setParameter(1, ioTHome);
            q.setParameter(2, location);
            q.setParameter(3, start.get(Calendar.YEAR));
            
            if(rollLessThanYear(calendarRollField)) {
                q.setParameter(4, start.get(Calendar.MONTH));
            }
            if(rollLessThanMonth(calendarRollField)) {
                q.setParameter(5, start.get(Calendar.DAY_OF_MONTH));
            }
            if(rollLessThanDay(calendarRollField)){
                q.setParameter(6, start.get(Calendar.HOUR_OF_DAY));
            }
            if(rollLessThanHour(calendarRollField)){
                q.setParameter(7, start.get(Calendar.MINUTE));
            }
            
            Double sum = q.getSingleResult();
            
            if(sum != null) {
                result.add(sum.floatValue());    
            } else {
                result.add((float)0);
            }
            
            start.add(calendarRollField, 1);
        }      
        return result;
    }
    
    private boolean rollLessThanYear(int calendarRollField) {
        return calendarRollField == Calendar.MINUTE || 
                calendarRollField == Calendar.HOUR_OF_DAY || 
                calendarRollField == Calendar.DAY_OF_MONTH || 
                calendarRollField == Calendar.MONTH;
    }
    
    private boolean rollLessThanMonth(int calendarRollField) {
        return calendarRollField == Calendar.MINUTE || 
                calendarRollField == Calendar.HOUR_OF_DAY || 
                calendarRollField == Calendar.DAY_OF_MONTH;
    }
    
    private boolean rollLessThanDay(int calendarRollField) {
        return calendarRollField == Calendar.MINUTE || 
                calendarRollField == Calendar.HOUR_OF_DAY;
    }
    
    private boolean rollLessThanHour(int calendarRollField) {
        return calendarRollField == Calendar.MINUTE;
    }
    
        
}
