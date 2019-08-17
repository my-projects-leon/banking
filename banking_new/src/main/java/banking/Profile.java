package banking;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable{
    public int profileId;
    private String username;
    private String firstname;
    private String lastname;
    private String birthday;
    private String password;


    public Profile(String username, String password, String fn, String ln, String birthday){
        //this.profileId = profileId;
        this.username = username;
        this.password = password;
        this.firstname = fn;
        this.lastname = ln;
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
