package admindistribution.com.admindistribution;

/**
 * Created by POPO on 2/3/2018.
 */

public class User8 {
    private String serial,initial,name,poindealer,actid, actqr,time,redeem,salesname,test,testing;

    public User8(String serial, String initial, String name, String poindealer, String actid, String actqr, String time, String redeem, String salesname, String test,String testing){
        this.serial=serial;
        this.initial=initial;
        this.name = name;
        this.poindealer = poindealer;
        this.actid= actid;
        this.actqr = actqr;
        this.time = time;
        this.redeem = redeem;
        this.salesname = salesname;
        this.test = test;
        this.testing = testing;


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

    public String getRedeem() {return redeem;}

    public String getSalesname() {return salesname;}

    public String getTest() {return  test;}

    public String getTesting() {return testing;}
}
