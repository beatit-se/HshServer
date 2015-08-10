package se.beatit.hsh.server.web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import se.beatit.hsh.server.ejb.IoTHomeEJB;
import se.beatit.hsh.server.ejb.entities.IoTDevice;
import se.beatit.hsh.server.ejb.entities.IoTHome;
import se.beatit.hsh.server.ejb.exception.HomeNotFountException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
 
/**
 *
 * @author stefan
 */
@Path("/iothome")
public class IoTHomeRestRoot {

    @Context
    private UriInfo context;
    
    @EJB
    IoTHomeEJB ioTHomeEjb;
    
    SimpleDateFormat simpleDateFormatter;
    
    SimpleDateFormat urlDateFormatter;

    
    public IoTHomeRestRoot() {
        if(ioTHomeEjb == null) {
            ioTHomeEjb = lookupIoTHomeEJBBean();   
        }
        
        simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
        
        urlDateFormatter = new SimpleDateFormat("yyyyMMddHHmm");
        urlDateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{home}")
    public String createHome(@PathParam("home") String home) {
        log("Starting to create home " + home);
        ioTHomeEjb.createHome(home);
        log("Home " + home + " is created");
        
        return "ok " + home + " created";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{home}")
    public String getHome(@PathParam("home") String home) {
        log("Get home " + home + " is called");
        
        StringBuilder sb = new StringBuilder();       
        try {
            List<IoTDevice> ioTHomeDevices = ioTHomeEjb.getHomeDevices(home);
        
            /*
            sb.append("Devices: ");
            if(ioTHomeDevices != null) {
                for (IoTDevice d : ioTHomeDevices) {
                    sb.append(d.getName()).append(" ");
                }
            } else {
                sb.append("no registered devices.");
            }
                    */
            
            sb.append("Devices: no registered devices.");
            sb.append(" Total electricity used: ").append(ioTHomeEjb.getTotalElectricityUsage(home)).append("wh");
            sb.append(" yesterday: ").append(ioTHomeEjb.getTotalElectricityUsage(home, IoTHomeEJB.Timespan.YESTERDAY)).append("wh");
            sb.append(" today: ").append(ioTHomeEjb.getTotalElectricityUsage(home, IoTHomeEJB.Timespan.TODAY)).append("wh");
            
            sb.append(" hour history: ");
            List<Long> hourHistory = ioTHomeEjb.getElectricityUsageList(home);
            for (Long hourUsage : hourHistory) {
                sb.append(hourUsage).append("wh - ");
                
            }
            
        } catch(Exception e)  {
            System.out.println("Failed to get info about home " + home + " " + e.toString());
            sb.append("Home with name ").append(home).append(" not found on server. ");
        }

        return sb.toString();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public String getHomes() {
        log("Get homes is called.");
        StringBuilder sb = new StringBuilder();
        
        try {
            List<IoTHome> ioTHomes = ioTHomeEjb.getHomes();
        
            sb.append(ioTHomes.size()).append(" ");
        
            for (IoTHome ioTHome : ioTHomes) {
                sb.append(ioTHome.getName()).append(" ");
            }
        } catch(Exception e) {
            sb.append("No homes found on server");
        }
        return sb.toString();
    }
    
     
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{home}/electricityuse")
    public String getUsage(@PathParam("home") String home) {
        log(home + " gets electricity usage");
        
        IoTHome ioTHome;
        try {
            ioTHome = ioTHomeEjb.getHome(home);
        } catch(HomeNotFountException e) {
            return "Home " + home + " not found.";
        }
        
        return home + " total usage: " + ioTHomeEjb.getTotalElectricityUsage(home) + "wh";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{home}/electricityuse/{from}/{to}/{resolution}")
    public String gethistoricUsage(@PathParam("home") String home, @PathParam("from") String from, @PathParam("to") String to, @PathParam("resolution") String resolution) {
        log(home + " gets electricity usage from "+ from + " to " + to + " with resolution " + resolution);
        long reqStart = System.currentTimeMillis();
        
        try {
            System.out.println("From is " + from + " to is " + to);
            
            Date fromDate = urlDateFormatter.parse(from);
            Date toDate = urlDateFormatter.parse(to);
            
            System.out.println("Fromdate is " + fromDate + " todate is " + toDate);
            
            int calendarResolution = Calendar.YEAR;
            
            if(resolution.equals("m")) {
                calendarResolution = Calendar.MINUTE;
            } else if(resolution.equals("h")) {
                calendarResolution = Calendar.HOUR_OF_DAY;
            } else if(resolution.equals("D")) {
                calendarResolution = Calendar.DAY_OF_MONTH;
            } else if(resolution.equals("M")) {
                calendarResolution = Calendar.MONTH;
            } else if(resolution.equals("y")) {
                calendarResolution = Calendar.YEAR;
            } else {
                throw new ParseException("Invalid resolution", 1);
            }
            
            List<Long> stats = ioTHomeEjb.getElectricityUsageStat(home, fromDate, toDate, calendarResolution);
            
            StringBuilder result = new StringBuilder("{\"electricityuse\":[");
            Iterator<Long> statIterator = stats.iterator();
            
            while (statIterator.hasNext()) {
                result.append(statIterator.next());
                if(statIterator.hasNext()) {
                    result.append(", ");
                }
            }
            result.append("]}");
            
            long timeToExec = System.currentTimeMillis() - reqStart;
            log("Electricity usage request handled in " + timeToExec + "ms");
            return result.toString();
        } catch(HomeNotFountException e) {
            return "Home " + home + " not found.";
        } catch (ParseException pe) {
            return "From and to date must be formatted: yyyyMMddHHmm resolution can be y, M, D, h or m";
        }
        
        
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{home}/electricityuse/{wh}")
    public String addUsage(@PathParam("home") String home, @PathParam("wh") Integer wh) {
        log(home + " adds electricity usage " + wh + "wh");
        
        try {
            ioTHomeEjb.addElectricityUsage(home, wh);
        } catch(HomeNotFountException e) {
            return "Home " + home + " not found.";
        }
        
        return wh + "wh added to " + home + " electicity usage";
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{home}/{location}/temperature/{temp}")
    public String addTemperature(@PathParam("home") String home, @PathParam("location") String location, @PathParam("temp") Float temperature) {
        log(home + " adds temperature " + temperature + " to location " + location);
        
        try {
            ioTHomeEjb.addTemperature(home, location, temperature);
        } catch(HomeNotFountException e) {
            return "Home " + home + " not found.";
        }
        
        return "Temp " + temperature + " added to " + home + " location " + location;
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{home}/{location}/temperature/{from}/{to}/{resolution}")
    public String getHistoricTemperature(@PathParam("home") String home, @PathParam("location") String location, @PathParam("from") String from, @PathParam("to") String to, @PathParam("resolution") String resolution) {
        log(home + " gets temperature for location " + location + " from "+ from + " to " + to + " with resolution " + resolution);
        long reqStart = System.currentTimeMillis();
        
        try {
            System.out.println("From is " + from + " to is " + to);
            
            Date fromDate = urlDateFormatter.parse(from);
            Date toDate = urlDateFormatter.parse(to);
            
            System.out.println("Fromdate is " + fromDate + " todate is " + toDate);
            
            int calendarResolution = Calendar.YEAR;
            
            if(resolution.equals("m")) {
                calendarResolution = Calendar.MINUTE;
            } else if(resolution.equals("h")) {
                calendarResolution = Calendar.HOUR_OF_DAY;
            } else if(resolution.equals("D")) {
                calendarResolution = Calendar.DAY_OF_MONTH;
            } else if(resolution.equals("M")) {
                calendarResolution = Calendar.MONTH;
            } else if(resolution.equals("y")) {
                calendarResolution = Calendar.YEAR;
            } else {
                throw new ParseException("Invalid resolution", 1);
            }
            
            List<Float> stats = ioTHomeEjb.getTemperatureStat(home, location, fromDate, toDate, calendarResolution);
            
            StringBuilder result = new StringBuilder("{\"temperature\":[");
            Iterator<Float> statIterator = stats.iterator();
            
            while (statIterator.hasNext()) {
                result.append(statIterator.next());
                if(statIterator.hasNext()) {
                    result.append(", ");
                }
            }
            result.append("]}");
            
            long timeToExec = System.currentTimeMillis() - reqStart;
            log("Temperature request handled in " + timeToExec + "ms");
            return result.toString();
        } catch(HomeNotFountException e) {
            return "Home " + home + " not found.";
        } catch (ParseException pe) {
            return "From and to date must be formatted: yyyyMMddHHmm resolution can be y, M, D, h or m";
        }
    }

    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{home}/{device}/status/{status}")
    public String setStatus(@PathParam("home") String home, @PathParam("device") String device, @PathParam("status") String status) {
        log("Home " + home + " is setting status " + status + " for device " + device);
        
        IoTHome ioTHome;
        try {
            ioTHome = ioTHomeEjb.getHome(home);
        } catch(HomeNotFountException e) {
            ioTHome = ioTHomeEjb.createHome(home);
        }
        
        if(ioTHome.getDevice(device) == null) {
            //ioTHome.addDevice(new IoTDevice(device));
        }
        
        if(status.equalsIgnoreCase("ON")) {
            ioTHome.getDevice(device).setTurnedOn(true);
        } else if(status.equalsIgnoreCase("OFF")) {
            ioTHome.getDevice(device).setTurnedOn(false);
        } else {
            return "Only status ON or OFF allowed.";
        }
        
        return "OK status updated";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{home}/{device}/status")
    public String getStatus(@PathParam("home") String home, @PathParam("device") String device) {
        log("Home " + home + " is getting status for device " + device);
        
        IoTHome ioTHome;
        try {
            ioTHome = ioTHomeEjb.getHome(home);
        } catch(HomeNotFountException e) {
            return "Home " + home + " not found.";
        }
        
        if(ioTHome.getDevice(device) == null) {
            return "Device " + device + " not found.";
        }
        
        //return String.valueOf(ioTHome.getDevice(device).getStatus());
        return "off";
    }

    private void log(String text) {
        System.out.println(simpleDateFormatter.format(new Date()) + ": " + text);
    }
    
    private IoTHomeEJB lookupIoTHomeEJBBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (IoTHomeEJB) c.lookup("java:global/HshServer/HshServer-ejb/IoTHomeEJB!se.beatit.hsh.server.ejb.IoTHomeEJB");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}