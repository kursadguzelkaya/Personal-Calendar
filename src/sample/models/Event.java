package sample.models;

import java.time.LocalDate;

/**
 * This class represents the event in the calendar
 * 
 * @author Mustafa Kaan Koç
 * @version 17.02.2021
 */
public class Event {

    // properties
    private String name;
    private String type;
    private LocalDate dueDate;
    private LocalDate assignDate;
    private int id;
    private int calendarID;

    // constructors
    public Event() {
    }

    public Event(String name, String type, LocalDate dueDate, int id, int calendarID) {
        this.name = name;
        this.type = type;
        this.dueDate = dueDate;
        assignDate = null;
    }

    public Event(String name, String type, LocalDate dueDate, LocalDate assignDate, int id, int calendarID) {
        this.name = name;
        this.type = type;
        this.dueDate = dueDate;
        this.assignDate = assignDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(int calendarID) {
        this.calendarID = calendarID;
    }
    /**
     * This method returns the name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the type
     * 
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * This method returns the due date
     * 
     * @return dueDate
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * This method sets the the name to given name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method sets the type to given type
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method sets the dueDate to given dueDate
     * 
     * @param dueDate
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     *
     * @return assign date
     */
    public LocalDate getAssignDate() { return assignDate; }

    /**
     * Set assign date to given date
     * @param assignDate
     */
    public void setAssignDate(LocalDate assignDate) { this.assignDate = assignDate; }

    @Override
    public String toString() {
        String result;
        result = "ID: " + id + ", Calendar ID: " + calendarID + ", Event: " + name + ", type: " + type + ", due date: " + dueDate + ", assign date: " + assignDate + "\n";

        return result;
    }


}