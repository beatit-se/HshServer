/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.web.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stefan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IoTDevicesRS implements Serializable {
    
    private List<IoTDeviceRS> devices = new ArrayList<IoTDeviceRS>();

    @XmlElement(name = "devices")
    public List<IoTDeviceRS> getDevices() {
        return devices;
    }

    public void setDevices(List<IoTDeviceRS> devices) {
        this.devices = devices;
    }

    void add(IoTDeviceRS ioTDeviceRS) {
        devices.add(ioTDeviceRS);
    }
}
