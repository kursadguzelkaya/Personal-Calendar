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
    private ArrayList<User> friends;
    private ArrayList<Calendar> sharedWithMe;
    private ArrayList<Calendar> myCalendars;
    private ArrayList<Calendar> favoCalendars;
    private ArrayList<Event> myEvents;
    private ArrayList<Event> favoEvents;
    private Calendar selectedCalendar;

    // default user constructor
    public User() {
    }

    public User(Profile myProfile, Calendar selectedCalendar) {
        this.myProfile = myProfile;
        friends = new ArrayList<User>();
        sharedWithMe = new ArrayList<Calendar>();
        myCalendars = new ArrayList<Calendar>();
        favoCalendars = new ArrayList<Calendar>();
        myEvents = new ArrayList<Event>();
        favoEvents = new ArrayList<Event>();
        this.selectedCalendar = selectedCalendar;
    }

    public User(Profile myProfile, ArrayList<User> friends, ArrayList<Calendar> sharedWithMe,
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

    /**
     * add given friend
     * 
     * @param user friend
     */
    public void addFriend(User friend) {
        friends.add(friend);
    }

    /**
     * remove given friend
     * 
     * @param user friend
     */
    public void removeFriend(User friend) {
        friends.remove(friend);
    }

    /**
     * create a calendar
     * 
     * @param String            title, date
     * @param Arraylist<Event>  events
     * @param ArrayList<String> comments
     */
    public void createCalendar(String title, String type) {

        if (type.equals("daily")) {
            myCalendars.add(new DailyCalendar(title, "today"));
        } else if (type.equals("weekly")) {
            myCalendars.add(new WeeklyCalendar(title));
        } else {
            myCalendars.add(new MonthlyCalendar(title, "month"));
        }
    }

    /**
     * create a event
     * 
     * @param String
     * @param type
     * @param dueDate
     * @param ArrayList<Date> assignedDates
     */
    public void createEvent(String name, String type, LocalDate dueDate) {
        myEvents.add(new Event(name, type, dueDate));
    }

    /**
     * edit given calendar
     * 
     * @param Calendar cal
     */
    public void editCalendar(Calendar cal) {

    }

    /**
     * edit given event
     * 
     * @param Event ev
     */
    public void editEvent(Event ev) {

    }

    /**
     * assign given calendar to selectedCalendar
     * 
     * @param Calendar cal
     */
    public void selectCalendar(Calendar cal) {
        selectedCalendar = cal;
    }

    /**
     * add given calendar to the favorite list
     * 
     * @param Calendar cal
     */
    public void addFavCal(Calendar cal) {
        favoCalendars.add(cal);
    }

    /**
     * remove given calendar from the favorite list
     * 
     * @param Calendar cal
     */
    public void removeFavCal(Calendar cal) {
        favoCalendars.remove(cal);
    }

    /**
     * add given event to the favorite list
     * 
     * @param Event ev
     */
    public void addFavEvent(Event ev) {
        favoEvents.add(ev);
    }

    /**
     * remove given event from the favorite list
     * 
     * @param Event ev
     */
    public void removeFavEvent(Event ev) {
        favoEvents.remove(ev);
    }

    /**
     * Add shared calendar to shared with me
     * 
     * @param shared
     */
    public void addSharedWithMe(Calendar shared) {
        sharedWithMe.add(shared);
    }

}
