package admindistribution.com.admindistribution;

/**
 * Created by POPO on 1/21/2018.
 */

public class User4 {

    private String idAct, actQr, serCod, iniCod, nameAct, statusAct;

    public User4(String idAct,String actQr,String serCod,String iniCod,String nameAct,String statusAct) {


        this.idAct = idAct;
        this.actQr = actQr;
        this.serCod = serCod;
        this.iniCod = iniCod;
        this.nameAct = nameAct;
        this.statusAct = statusAct;

    }

    public String getIdAct() {
        return idAct;
    }
    public String getActQr() { return actQr;}
    public String getSerCod() { return serCod;}
    public String getIniCod() { return iniCod;}
    public String getNameAct() { return nameAct;}
    public String getStatusAct() { return statusAct;}
}
