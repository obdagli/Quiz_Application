package tr.yildiz.OmerBurakDagli;

import java.util.ArrayList;

public class Users{
    String username;
    String salt;
    String hash;
    String name;
    String surname;
    String email;
    String date;
    byte[] userPhoto;
    String phonenumber;
    public static ArrayList<Users> users = new ArrayList<Users>();

    public Users(String username, String salt, String hash, String name, String surname, String email, String date, byte[] userPhoto, String phonenumber) {
        this.username = username;
        this.salt = salt;
        this.hash = hash;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.date = date;
        this.userPhoto = userPhoto;
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public static ArrayList<Users> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<Users> users) {
        Users.users = users;
    }
}