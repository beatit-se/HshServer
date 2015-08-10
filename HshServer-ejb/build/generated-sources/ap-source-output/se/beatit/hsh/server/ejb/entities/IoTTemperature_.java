package se.beatit.hsh.server.ejb.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.beatit.hsh.server.ejb.entities.IoTHome;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-10T21:15:20")
@StaticMetamodel(IoTTemperature.class)
public class IoTTemperature_ { 

    public static volatile SingularAttribute<IoTTemperature, IoTHome> iotHome;
    public static volatile SingularAttribute<IoTTemperature, Float> temperature;
    public static volatile SingularAttribute<IoTTemperature, String> location;
    public static volatile SingularAttribute<IoTTemperature, Long> id;
    public static volatile SingularAttribute<IoTTemperature, Integer> tMinute;
    public static volatile SingularAttribute<IoTTemperature, Integer> tYear;
    public static volatile SingularAttribute<IoTTemperature, Integer> tDay;
    public static volatile SingularAttribute<IoTTemperature, Integer> tHour;
    public static volatile SingularAttribute<IoTTemperature, Integer> tMonth;

}