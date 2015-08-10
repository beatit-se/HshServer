/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.ejb.entities;

import java.io.Serializable;
import java.util.Objects;
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
public class IoTTemperature implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private IoTHome iotHome;
    
    private String location;
    
    private float temperature;
    
    private int tYear;
    
    private int tMonth;
    
    private int tDay;
    
    private int tHour;
    
    private int tMinute;
    
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int gettYear() {
        return tYear;
    }

    public void settYear(int tYear) {
        this.tYear = tYear;
    }

    public int gettMonth() {
        return tMonth;
    }

    public void settMonth(int tMonth) {
        this.tMonth = tMonth;
    }

    public int gettDay() {
        return tDay;
    }

    public void settDay(int tDay) {
        this.tDay = tDay;
    }

    public int gettHour() {
        return tHour;
    }

    public void settHour(int tHour) {
        this.tHour = tHour;
    }

    public int gettMinute() {
        return tMinute;
    }

    public void settMinute(int tMinute) {
        this.tMinute = tMinute;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.iotHome);
        hash = 67 * hash + Objects.hashCode(this.location);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IoTTemperature other = (IoTTemperature) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IoTTemperature{" + "id=" + id + ", iotHome=" + iotHome + ", location=" + location + ", temperature=" + temperature + ", tYear=" + tYear + ", tMonth=" + tMonth + ", tDay=" + tDay + ", tHour=" + tHour + ", tMinute=" + tMinute + '}';
    }    
}
