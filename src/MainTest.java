import java.util.*;
import java.time.*;

public class MainTest {
    public static void main(String[] args) {
        Event event;
        Calendar calendar;
        Profile myProfile;
        LocalDate date = LocalDate.now();
        User user;
        // System.out.println(date.plusDays(2));

        event = new Event("Math Exam", "School", date);
        calendar = new DailyCalendar("calendar_one", date + "");
        myProfile = new Profile("user1", "123456", "user@gmail.com");
        user = new User(myProfile, calendar);

        calendar.addEvent(event);
        calendar.addComment("bir comment");

        System.out.println(event);
        System.out.println(calendar);
        System.out.println(myProfile);
        System.out.println(user);

    }
}
