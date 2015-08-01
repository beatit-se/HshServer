/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.server.web;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author stefan
 */
@ApplicationPath("/")
public class IoTHomeRestApplication extends Application { 
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        // register root resource
        classes.add(IoTHomeRestRoot.class);
       // classes.add(IoTDeviceRS.class);
       // classes.add(IoTDevicesRS.class);
       // classes.add(IoTHomeRS.class);
       // classes.add(IoTHomesRS.class);
        return classes;
    }
}

