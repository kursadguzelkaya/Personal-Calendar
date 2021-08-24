package sample.models;
/**
 * Calendar class to create a calendar that have a title, events and comments
 * @author Kürşad Güzelkaya
 * @version 1.0 17.04.21
 */

import java.util.ArrayList;
import java.util.Date;

public class Calendar {

    // properties
    private String title;
    private Date date;
    private ArrayList<Event> events;
    private ArrayList<String> comments;
    private int id;

    // constructor

    /**
     * Initial constructor to create an empty calendar with title
     */
    public Calendar() {
        this.title = "";
        this.id = 0;
        this.events = new ArrayList<Event>();
        this.comments = new ArrayList<String>();
        this.date = new Date();
    }

    /**
     * Initial constructor to create an empty calendar with title
     */
    public Calendar(String title, int id) {
        this.title = title;
        this.id = id;
        this.events = new ArrayList<Event>();
        this.comments = new ArrayList<String>();
        this.date = new Date();
    }

    /**
     * Create a calendar with given title, events and comments
     *  @param title
     * @param events
     * @param comments
     * @param id
     */
    public Calendar(String title, ArrayList<Event> events, ArrayList<String> comments, int id) {
        this.title = title;
        this.events = events;
        this.comments = comments;
        this.id = id;
        this.date = new Date();
    }

    // methods

    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Add given event to events of the calendar
     * 
     * @param event
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * remove given event from events of the calendar
     * 
     * @param event
     */
    public void removeEvent(Event event) {
        events.remove(event);
    }

    /**
     * Add given commet to comments of the calendar
     * 
     * @param comment
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * Remove given comment from comments of the calendar
     * 
     * @param comment
     */
    public void removeComment(String comment) {
        comments.remove(comment);
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
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id to given id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Display calendar
     */
    // public abstract void display();

    /**
     * @return the string representation of calendar
     */
    @Override
    public String toString() {
        String result = "";
        result += "\nID: " + id + ", Calendar: " + title;
        result += "\n\tCalendar's Events are: ";
        for (int i = 0; i < events.size(); i++) {
            result += "\n\t\t" + events.get(i);
        }
        result += "\n\tCalendar's Comments are: ";
        for (int i = 0; i < comments.size(); i++) {
            result += "\n\t\t" + "Comment: " + comments.get(i);
        }
        return result;
    }
}
