
/**
 * Calendar class to create a calendar that have a title, events and comments
 * @author Kürşad Güzelkaya
 * @version 1.0 17.04.21
 */

import java.util.ArrayList;
import java.util.Date;

public abstract class Calendar {

    // properties
    private String title;
    private Date date;
    private ArrayList<Event> events;
    private ArrayList<String> comments;

    // constructor

    /**
     * Initial constructor to create an empty calendar with title
     */
    public Calendar(String title) {
        this.title = title;
        this.events = new ArrayList<Event>();
        this.comments = new ArrayList<String>();
        this.date = new Date();
    }

    /**
     * Create a calendar with given title, events and comments
     * 
     * @param title
     * @param events
     * @param comments
     */
    public Calendar(String title, ArrayList<Event> events, ArrayList<String> comments) {
        this.title = title;
        this.events = events;
        this.comments = comments;
        this.date = new Date();
    }

    // methods

    /**
     * Add given event to events of the calendar
     * 
     * @param event
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Add given event to events of the calendar
     * 
     * @param event
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * 
     * @return the title of the calendar
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title to given title
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return the current date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Display calendar
     */
    public abstract void display();

    /**
     * @return the string representation of calendar
     */
    @Override
    public String toString() {
        String result = "";
        result += "Calendar: " + title;
        result += "\nCalendar's Events are: ";
        for (int i = 0; i < events.size(); i++) {
            result += "Event: " + events.get(i) + "\n";
        }
        result += "\nCalendar's Comments are: ";
        for (int i = 0; i < comments.size(); i++) {
            result += "Event: " + comments.get(i) + "\n";
        }
        return result;
    }

}
