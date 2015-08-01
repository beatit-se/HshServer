/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.web.rest;

import se.beatit.hsh.server.ejb.entities.IoTDevice;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stefan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IoTDeviceRS {
        private String name;

    public IoTDeviceRS(IoTDevice device) {
        this.name = device.getName();
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
        private long id;
}
