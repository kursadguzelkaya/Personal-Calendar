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

    // default constructor
    public Profile() {
    }

    public Profile(String username, String password, String eMail) {
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.avatar = null;
    }

    public Profile(String username, String password, String eMail, File avatar) {
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.avatar = avatar;
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
     * @param String username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * sets this.password to given password
     * 
     * @param String password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * sets this.eMail to given eMail
     * 
     * @param String eMail
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * sets this.avatar to given avatar
     * 
     * @param File avatar
     */
    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    public String toString() {
        return "Name: " + username + " , Password: " + password + " , email: " + eMail;
    }
}
