package sample.models;

import java.util.Date;

/**
 * Personal Calendar Chat Class
 * @author Ege Ayan
 * @version 17.04.2021
 */

public class Chat {

    // Properties
    private Profile user1;
    private Profile user2;
    private String chatID;
    private Date date;
    private boolean readByUser1;
    private boolean readByUser2;

    // Constructors
    public Chat( Profile user1, Profile user2, String chatID, Date date, boolean readByUser1, boolean readByUser2)
    {
        this.user1 = user1;
        this.user2 = user2;
        this.chatID = chatID;
        this.date = date;
        this.readByUser1 = readByUser1;
        this.readByUser2 = readByUser2;
    }

    public Chat()
    {
    }

    // Methods
    /**
     * This method sends a chat message to an another profile
     * @param message is content of the text that will be send
     * @param profile is the other profile that the message will be send to
     */
    public void sendMessage( String message, Profile profile)
    {
        // This method will be completed via JavaFX and MySql later on
    }

    /**
     * This method displays all properties of the Chat Class
     */
    public String toString()
    {
        return "" + "User1: " + user1 + "User2: " + user2 + "Chat ID: " 
                + chatID + "Date: " + date + "Read By User1: " + readByUser1 + "Read By User2: " + readByUser2;
            
    }
    
}
