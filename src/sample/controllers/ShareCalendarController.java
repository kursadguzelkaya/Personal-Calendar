/**
 * Share calendar pop-up controller
 * @author Kürşad Güzelkaya,
 * @version 1.0
 */
package sample.controllers;
import sample.models.Calendar;


public class ShareCalendarController {


    private Calendar currentCalender;

    /**
     * get calendar
     * @param cal
     */
    public void getCalendar(Calendar cal) {
        currentCalender = cal;
    }

}
