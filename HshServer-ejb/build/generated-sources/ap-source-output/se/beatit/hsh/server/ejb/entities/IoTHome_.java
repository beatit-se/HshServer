package se.beatit.hsh.server.ejb.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.beatit.hsh.server.ejb.entities.IoTDevice;
import se.beatit.hsh.server.ejb.entities.IoTMinuteUsage;
import se.beatit.hsh.server.ejb.entities.IoTTemperature;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-10T21:15:20")
@StaticMetamodel(IoTHome.class)
public class IoTHome_ { 

    public static volatile ListAttribute<IoTHome, IoTDevice> ioTDevices;
    public static volatile ListAttribute<IoTHome, IoTMinuteUsage> ioTMinuteUsages;
    public static volatile SingularAttribute<IoTHome, String> name;
    public static volatile SingularAttribute<IoTHome, Long> id;
    public static volatile ListAttribute<IoTHome, IoTTemperature> ioTTemperature;

}