package admindistribution.com.admindistribution;

/**
 * Created by POPO on 2/3/2018.
 */

public class ListItem {
    private String nama,poin,redeemtime,serialcode,initial,actid,actqr,rstatus;

    public ListItem(String nama,String poin,String redeemtime,String serialcode,String initial, String actid,String actqr, String rstatus){
        this.nama = nama;
        this.poin = poin;
        this.redeemtime = redeemtime;
        this.serialcode = serialcode;
        this.initial = initial;
        this.actid =actid;
        this.actqr=actqr;
        this.rstatus=rstatus;

    }

    public String getNama() {return nama; }

    public String getPoin() {
        return poin;
    }

    public String getRedeemtime() {
        return redeemtime;
    }


    public String getSerialcode() { return serialcode;}

    public String getInitial() { return initial;}

    public String getActid() {return actid;}

    public String getActqr() {return actqr;}

    public String getRstatus() {return rstatus;}
}
