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
public class IoTHomesRS implements Serializable {
    
    List<IoTHomeRS> ioTHomes = new ArrayList<IoTHomeRS>();

    public IoTHomesRS(List<IoTHomeRS> homes) {
        this.ioTHomes = homes;
    }

    public IoTHomesRS() {
    }
    
    public void add(IoTHomeRS iotHomeRS) {
        ioTHomes.add(iotHomeRS);
    }
    
    @XmlElement(name = "ioTHomes")
    public List<IoTHomeRS> getIoTHomes() {
        return ioTHomes;
    }

    public void setIoTHomes(List<IoTHomeRS> ioTHomes) {
        this.ioTHomes = ioTHomes;
    }
            
    
}
