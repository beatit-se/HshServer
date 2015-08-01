/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.web.rest;

import se.beatit.hsh.server.ejb.entities.IoTHome;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stefan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IoTHomeRS  implements Serializable {
    private String name;
    private long id;
    private IoTDevicesRS devices;

    public IoTHomeRS() {
        
    }

    public IoTHomeRS(IoTHome ioTHome) {
        this.name = ioTHome.getName();
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    public IoTDevicesRS getDevices() {
        return devices;
    }

    public void setDevices(IoTDevicesRS devices) {
        this.devices = devices;
    }

    public void addDevice(IoTDeviceRS ioTDeviceRS) {
        if(devices == null) {
            devices = new IoTDevicesRS();
        }
        devices.add(ioTDeviceRS);
    }
    
    
}
