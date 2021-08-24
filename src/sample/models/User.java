package sample.models;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * User Class
 * 
 * @author Utku Kurtulmus
 * @version 17.04.2021
 */
public class User {

    private Profile myProfile;
    private ArrayList<Profile> friends;
    private ArrayList<Calendar> sharedWithMe;
    private ArrayList<Calendar> myCalendars;
    private ArrayList<Calendar> favoCalendars;
    private ArrayList<Event> myEvents;
    private ArrayList<Event> favoEvents;
    private Calendar selectedCalendar;

    // default user constructor
    public User() {
        myProfile = new Profile();
        friends = new ArrayList<Profile>();
        sharedWithMe = new ArrayList<Calendar>();
        myCalendars = new ArrayList<Calendar>();
        favoCalendars = new ArrayList<Calendar>();
        myEvents = new ArrayList<Event>();
        favoEvents = new ArrayList<Event>();
        selectedCalendar = new Calendar();
    }

    public User(Profile myProfile, Calendar selectedCalendar) {
        this.myProfile = myProfile;
        friends = new ArrayList<Profile>();
        sharedWithMe = new ArrayList<Calendar>();
        myCalendars = new ArrayList<Calendar>();
        favoCalendars = new ArrayList<Calendar>();
        myEvents = new ArrayList<Event>();
        favoEvents = new ArrayList<Event>();
        this.selectedCalendar = selectedCalendar;
    }

    public User(Profile myProfile, ArrayList<Profile> friends, ArrayList<Calendar> sharedWithMe,
            ArrayList<Calendar> myCalendars, ArrayList<Calendar> favoCalendars, ArrayList<Event> myEvents,
            ArrayList<Event> favoEvents, Calendar selectedCalendar) {
        this.myProfile = myProfile;
        this.friends = friends;
        this.sharedWithMe = sharedWithMe;
        this.myCalendars = myCalendars;
        this.favoCalendars = favoCalendars;
        this.myEvents = myEvents;
        this.favoEvents = favoEvents;
        this.selectedCalendar = selectedCalendar;
    }

    public ArrayList<Profile> getFriends() {
        return friends;
    }

    public ArrayList<Event> getMyEvents() {
        return myEvents;
    }

    public ArrayList<Event> getFavoEvents() {
        return favoEvents;
    }

    public ArrayList<Calendar> getSharedWithMe() {
        return sharedWithMe;
    }

    public ArrayList<Calendar> getMyCalendars() {
        return myCalendars;
    }

    public ArrayList<Calendar> getFavoCalendars() {
        return favoCalendars;
    }

    /**
     *
     * @return user's profile
     */
    public Profile getMyProfile() {
        return myProfile;
    }

    /**
     * Set given profile to myProfile
     * @param myProfile
     */
    public void setMyProfile(Profile myProfile) {
        this.myProfile = myProfile;
    }

    /**
     * add given friend
     * 
     * @param friend
     */
    public void addFriend(Profile friend) {
        friends.add(friend);
    }

    /**
     * remove given friend
     * 
     * @param friend
     */
    public void removeFriend(User friend) {
        friends.remove(friend);
    }

    /**
     * Create calendar
     * @param title
     * @param type
     * @param id
     */
    public void createCalendar(String title, String type, int id) {
        /**
        if (type.equals("daily")) {
            myCalendars.add(new DailyCalendar(title, "today", id));
        } else if (type.equals("weekly")) {
            myCalendars.add(new WeeklyCalendar(title, id));
        } else {
            myCalendars.add(new MonthlyCalendar(title, "month", id));
        }
         */
    }

    /**
     * Create event
     * @param name
     * @param type
     * @param dueDate
     */
    public void createEvent(String name, String type, LocalDate dueDate, int id, int calendarID) {
        myEvents.add(new Event(name, type, dueDate, id, calendarID));
    }

    /**
     * Edit calendar according to the given params
     * 
     * @param cal
     * @param title
     * @param eventsToAdd
     * @param eventsToDelete
     * @param commentsToAdd
     * @param commentsToDelete
     */
    public void editCalendar(Calendar cal, String title, ArrayList<Event> eventsToAdd, ArrayList<Event> eventsToDelete,
            ArrayList<String> commentsToAdd, ArrayList<String> commentsToDelete) {
        for (int i = 0; i < myCalendars.size(); i++) {
            if (cal.getTitle().equals(myCalendars.get(i).getTitle())) {
                Calendar myCal = myCalendars.get(i);
                // Change name if not empty
                if (!title.equals("")) {
                    myCal.setTitle(title);
                }
                // Add given list of events to calendar
                if (eventsToAdd != null && eventsToAdd.size() != 0) {
                    for (Event event : eventsToAdd) {
                        myCal.addEvent(event);
                    }
                }
                // Remove given list of events from calendar
                if (eventsToDelete != null && eventsToDelete.size() != 0) {
                    for (Event event : eventsToDelete) {
                        myCal.removeEvent(event);
                    }
                }
                // Add given list of comments to calendar
                if (commentsToAdd != null && commentsToAdd.size() != 0) {
                    for (String comment : commentsToAdd) {
                        myCal.addComment(comment);
                    }
                }
                // Remove given list of comments from calendar
                if (commentsToAdd != null && commentsToAdd.size() != 0) {
                    for (String comment : commentsToAdd) {
                        myCal.removeComment(comment);
                    }
                }
            }
        }
    }

    /**
     * Edit given event
     * @param event
     * @param name
     * @param type
     * @param dueDate
     */
    public void editEvent(Event event, String name, String type, LocalDate dueDate) {
        for (int i = 0; i < myEvents.size(); i++) {
            if (event.getName().equals(myEvents.get(i).getName())) {
                Event myEvent = myEvents.get(i);
                // Change name if not empty
                if (!name.equals("")) {
                    myEvent.setName(name);
                }
                // Change name if not empty
                if (!type.equals("")) {
                    myEvent.setType(type);
                }
                // Change name if not empty
                if (dueDate != null) {
                    myEvent.setDueDate(dueDate);
                }
            }
        }
    }

    /**
     * assign given calendar to selectedCalendar
     * 
     * @param cal
     */
    public void selectCalendar(Calendar cal) {
        selectedCalendar = cal;
    }

    /**
     * add given calendar to the favorite list
     * 
     * @param cal
     */
    public void addFavCal(Calendar cal) {
        favoCalendars.add(cal);
    }

    /**
     * remove given calendar from the favorite list
     * 
     * @param cal
     */
    public void removeFavCal(Calendar cal) {
        favoCalendars.remove(cal);
    }

    /**
     * add given calendar to the my calendars
     * 
     * @param cal
     */
    public void addMyCal(Calendar cal) {
        myCalendars.add(cal);
    }

    /**
     * remove given calendar from the my calendars
     * 
     * @param cal
     */
    public void removeMyCal(Calendar cal) {
        myCalendars.remove(cal);
    }

    /**
     * add given event to the favorite list
     * 
     * @param ev
     */
    public void addFavEvent(Event ev) {
        favoEvents.add(ev);
    }

    /**
     * remove given event from the favorite list
     * 
     * @param ev
     */
    public void removeFavEvent(Event ev) {
        favoEvents.remove(ev);
    }

    /**
     * add given event to the my events list
     * 
     * @param ev
     */
    public void addMyEvent(Event ev) {
        myEvents.add(ev);
    }

    /**
     * remove given event from the my events list
     * 
     * @param ev
     */
    public void removeMyEvent(Event ev) {
        myEvents.remove(ev);
    }

    /**
     * Add shared calendar to shared with me
     * 
     * @param shared
     */
    public void addSharedWithMe(Calendar shared) {
        sharedWithMe.add(shared);
    }

    /**
     * 
     * @param list
     * @return the string representation of the given list
     */
    private String listToString(ArrayList list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += "\n" + list.get(i);
        }
        return result;
    }

    /**
     * @return the string representation of calendar
     */
    @Override
    public String toString() {
        String result = "";
        result += "User: " + myProfile;
        result += "\nMy Selected Calendar is: " + selectedCalendar.getTitle();
        result += "\n\nMyCalendars: " + listToString(myCalendars);
        result += "\n\nFavCalendars: " + listToString(favoCalendars);
        result += "\n\nShared With Me: " + listToString(sharedWithMe);
        result += "\n\nMy Events are: " + listToString(myEvents);
        result += "\n\nFavo Events are: " + listToString(favoEvents);
        result += "\n\nMy Friends are: " + listToString(friends);
        return result;
    }

}
