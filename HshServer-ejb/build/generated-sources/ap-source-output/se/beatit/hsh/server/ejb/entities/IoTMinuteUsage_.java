package se.beatit.hsh.server.ejb.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.beatit.hsh.server.ejb.entities.IoTHome;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-10T21:15:20")
@StaticMetamodel(IoTMinuteUsage.class)
public class IoTMinuteUsage_ { 

    public static volatile SingularAttribute<IoTMinuteUsage, IoTHome> iotHome;
    public static volatile SingularAttribute<IoTMinuteUsage, Integer> whUsage;
    public static volatile SingularAttribute<IoTMinuteUsage, Integer> whMonth;
    public static volatile SingularAttribute<IoTMinuteUsage, Integer> whMinute;
    public static volatile SingularAttribute<IoTMinuteUsage, Integer> whDay;
    public static volatile SingularAttribute<IoTMinuteUsage, Integer> whHour;
    public static volatile SingularAttribute<IoTMinuteUsage, Integer> whYear;
    public static volatile SingularAttribute<IoTMinuteUsage, Long> id;

}