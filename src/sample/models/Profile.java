package sample.models;

import java.io.File;

/**
 * User Profile
 * 
 * @author Utku Kurtulmus
 * @version 17.04.2021
 */
public class Profile {
    private String username;
    private String password;
    private String eMail;
    private File avatar;
    private int id;

    // default constructor
    public Profile() {
    }

    public Profile(String username, String password, String eMail, int id) {
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.id = id;
        this.avatar = null;
    }

    public Profile(String username, String password, String eMail, File avatar, int id) {
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.avatar = avatar;
        this.id = id;
    }

    /**
     * get method for the username
     * 
     * @return this.username
     */
    public String getUsername() {
        return username;
    }

    /**
     * get method for the password
     * 
     * @return this.password
     */
    public String getPassword() {
        return password;
    }

    /**
     * get method for the eMail
     * 
     * @return this.eMail
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * get method for the avatar
     * 
     * @return this.avatar
     */
    public File getAvatar() {
        return avatar;
    }

    /**
     * sets this.username to given username
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * sets this.password to given password
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * sets this.eMail to given eMail
     * 
     * @param eMail
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * sets this.avatar to given avatar
     * 
     * @param avatar
     */
    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id to the given id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return String representation of profile
     */
    public String toString() {
        return "ID: " + id + " , Name: " + username + " , Password: " + password + " , email: " + eMail;
    }

}
