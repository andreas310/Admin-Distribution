package admindistribution.com.admindistribution;

/**
 * Created by POPO on 2/3/2018.
 */

public class User9 {
    private String serial,initial,name,poindealer,actid, actqr,time;

    public User9(String serial, String initial, String name, String poindealer, String actid, String actqr, String time){
        this.serial=serial;
        this.initial=initial;
        this.name = name;
        this.poindealer = poindealer;
        this.actid= actid;
        this.actqr = actqr;
        this.time = time;


    }

    public String getSerial() {return serial; }

    public String getInitial() {return initial;}

    public String getName() {return name; }

    public String getPoindealer() {
        return poindealer;
    }

    public String getActid() {
        return actid;
    }

    public String getActqr() { return actqr;}

    public String getTime() {return time;}

}
