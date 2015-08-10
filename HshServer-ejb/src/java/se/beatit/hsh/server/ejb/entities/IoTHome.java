/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author stefan
 */
@Entity
public class IoTHome implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    @OneToMany(mappedBy = "iotHome")
    private List<IoTDevice> ioTDevices;

    @OneToMany(mappedBy = "iotHome")
    private List<IoTMinuteUsage> ioTMinuteUsages;
    
    @OneToMany(mappedBy = "iotHome")
    private List<IoTTemperature> ioTTemperature;
    
    public IoTHome() {
    }
    
    public IoTHome(String name) {
        this.name = name;
        ioTDevices = new ArrayList<IoTDevice>();
    }
    
    
    public String getName() {
        return name;
    }
    
    public IoTDevice getDevice(String name) {
        for (IoTDevice ioTDevice : ioTDevices) {
            if(ioTDevice.getName().equals(name)) {
                return ioTDevice;
            }
        }
        return null;
    }

    public List<IoTDevice> getIoTDevices() {
        return ioTDevices;
    }

    public void setIoTDevices(List<IoTDevice> ioTDevices) {
        this.ioTDevices = ioTDevices;
    }

    public List<IoTMinuteUsage> getIoTMinuteUsages() {
        return ioTMinuteUsages;
    }

    public void setIoTMinuteUsages(List<IoTMinuteUsage> ioTMinuteUsages) {
        this.ioTMinuteUsages = ioTMinuteUsages;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof IoTHome)) {
            return false;
        }
        IoTHome other = (IoTHome) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.beatit.se.jo.server.ejb.entities.IoTHome[ id=" + id + " ]";
    }

}
