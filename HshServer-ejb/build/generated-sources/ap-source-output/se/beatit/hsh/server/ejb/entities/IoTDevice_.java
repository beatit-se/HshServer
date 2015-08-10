package se.beatit.hsh.server.ejb.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.beatit.hsh.server.ejb.entities.IoTHome;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-10T21:15:20")
@StaticMetamodel(IoTDevice.class)
public class IoTDevice_ { 

    public static volatile SingularAttribute<IoTDevice, IoTHome> iotHome;
    public static volatile SingularAttribute<IoTDevice, String> name;
    public static volatile SingularAttribute<IoTDevice, Long> id;
    public static volatile SingularAttribute<IoTDevice, Boolean> turnedOn;

}