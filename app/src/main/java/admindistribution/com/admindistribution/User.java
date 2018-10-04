package admindistribution.com.admindistribution;

/**
 * Created by POPO on 1/21/2018.
 */

public class User {

    private String username, email, name, role, salt, address, poin, phone;

    public User(String username, String email, String name,String role, String salt, String address, String poin, String phone) {

        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.salt = salt;
        this.address = address;
        this.poin = poin;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {return email;}

    public String getName() {return name;}

    public String getRole() {return role;}

    public String getSalt() {return salt;}

    public String getAddress() {return address;}

    public String getPoin() {return poin;}

    public String getPhone() {return phone;}
}