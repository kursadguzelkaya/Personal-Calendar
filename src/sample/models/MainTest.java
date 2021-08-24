package sample.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args) {
        Event event;
        Event event2;
        Event event3;

        ArrayList<Event> toAdd;
        ArrayList<Event> toRemove;

        Calendar calendar;
        Profile myProfile;
        LocalDate date = LocalDate.now();
        User user;
        // System.out.println(date.plusDays(2));
        toAdd = new ArrayList<Event>();
        toRemove = new ArrayList<Event>();

        event = new Event("Math Exam", "School", date, 1,1);
        event2 = new Event("Meeting", "Social", date, 2, 1);
        event3 = new Event("Party", "work", date, 3, 1 );

        toAdd.add(event2);
        toAdd.add(event3);
        toRemove.add(event);

        calendar = new Calendar("calendar_one", 1);
        myProfile = new Profile("user1", "123456", "user@gmail.com", 1);
        user = new User(myProfile, calendar);

        calendar.addEvent(event);
        calendar.addComment("bir comment");

        user.addMyEvent(event2);
        user.editEvent(event2, "new_event", "work--", null);

        user.addMyCal(calendar);
        user.editCalendar(calendar, "new_name", toAdd, toRemove, null, null);

        // System.out.println(event);
        System.out.println(calendar);
        // System.out.println(myProfile);
        System.out.println(user);

    }
}
