/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.ejb.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author stefan
 */
@Entity
public class IoTMinuteUsage implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private int whUsage;
    
    private int whYear;
    
    private int whMonth;
    
    private int whDay;
    
    private int whHour;
    
    private int whMinute;

    @ManyToOne
    private IoTHome iotHome;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IoTHome getIotHome() {
        return iotHome;
    }

    public void setIotHome(IoTHome iotHome) {
        this.iotHome = iotHome;
    }

    public int getWhUsage() {
        return whUsage;
    }

    public void setWhUsage(int whUsage) {
        this.whUsage = whUsage;
    }

    public int getWhYear() {
        return whYear;
    }

    public void setWhYear(int whYear) {
        this.whYear = whYear;
    }

    public int getWhMonth() {
        return whMonth;
    }

    public void setWhMonth(int whMonth) {
        this.whMonth = whMonth;
    }

    public int getWhDay() {
        return whDay;
    }

    public void setWhDay(int whDay) {
        this.whDay = whDay;
    }

    public int getWhHour() {
        return whHour;
    }

    public void setWhHour(int whHour) {
        this.whHour = whHour;
    }

    public int getWhMinute() {
        return whMinute;
    }

    public void setWhMinute(int whMinute) {
        this.whMinute = whMinute;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IoTMinuteUsage)) {
            return false;
        }
        IoTMinuteUsage other = (IoTMinuteUsage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.beatit.se.jo.server.ejb.entities.IoTMinuteUsage[ id=" + id + " ]";
    }
    
}
