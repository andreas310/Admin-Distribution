package admindistribution.com.admindistribution;

/**
 * Created by POPO on 2/3/2018.
 */

public class ShopItem {
    private String id,nama,poin,description,picture, status;

    public ShopItem(String id, String nama, String poin, String description, String picture, String status){
        this.id=id;
        this.nama = nama;
        this.poin = poin;
        this.description= description;
        this.picture = picture;
        this.status = status;

    }

    public String getId() {return id; }

    public String getNama() {return nama; }

    public String getPoin() {
        return poin;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() { return picture;}

    public String getStatus() {return status;}
}
