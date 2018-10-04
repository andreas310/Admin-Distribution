package admindistribution.com.admindistribution;

/**
 * Created by POPO on 1/21/2018.
 */

public class User3 {

    private String point, serial, initial, nameproduct, activation,id;

    public User3(String point, String serial, String initial, String nameproduct, String activation, String id) {


        this.point = point;
        this.serial = serial;
        this.initial = initial;
        this.nameproduct = nameproduct;
        this.activation = activation;
        this.id = id;
    }

    public String getPoin() {return point;}
    public String getSerial() {return serial;}
    public String getInitial() {return initial;}
    public String getNamePro() {return nameproduct;}
    public String getActivation() {return activation;}
    public String getId() {return id;}
}