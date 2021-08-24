package sample.models;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataSource {
    // DB CONNECTION INFO
    public static final String DB_NAME = "bvutoqrxdqnivvsvwy9i";
    public static final String CONNECTION_STRING = "jdbc:mysql://u1h7mky1yivrbc0x:rSNuhLBKR5uaZG7cn7Wz@bvutoqrxdqnivvsvwy9i-mysql.services.clever-cloud.com:3306/" + DB_NAME;
    public static final String USER_NAME = "u1h7mky1yivrbc0x";
    public static final String PASSWORD = "rSNuhLBKR5uaZG7cn7Wz";
    // USERS TABLE
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_ID = "id";
    public static final String COLUMN_USERS_USER_NAME = "user_name";
    public static final String COLUMN_USERS_EMAIL = "email";
    public static final String COLUMN_USERS_PASSWORD = "password";
    public static final String COLUMN_USERS_REGISTER_DATE = "register_date";
    public static final String COLUMN_USERS_AVATAR = "avatar";

    // CALENDARS TABLE
    public static final String TABLE_CALENDARS = "calendars";
    public static final String COLUMN_CALENDARS_ID = "id";
    public static final String COLUMN_CALENDARS_TITLE = "title";
    public static final String COLUMN_CALENDARS_CREATE_DATE = "create_date";

    // CALENDARS TABLE
    public static final String TABLE_FRIENDS = "friends";
    public static final String COLUMN_FRIENDS_FRIEND_ID = "friend_id";
    public static final String COLUMN_FRIENDS_USER_ID = "user_id";

    // MY CALENDARS TABLE
    public static final String TABLE_MY_CALENDARS = "my_calendars";
    public static final String COLUMN_MY_CALENDARS_USER_ID = "user_id";
    public static final String COLUMN_MY_CALENDARS_CALENDAR_ID = "calendar_id";

    // FAV CALENDARS TABLE
    public static final String TABLE_FAV_CALENDARS = "favorite_calendars";
    public static final String COLUMN_FAV_CALENDARS_USER_ID = "user_id";
    public static final String COLUMN_FAV_CALENDARS_CALENDAR_ID = "calendar_id";

    // MY CALENDARS TABLE
    public static final String TABLE_SHARED_WITH_ME = "shared_with_me";
    public static final String COLUMN_SHARED_WITH_ME_USER_ID = "user_id";
    public static final String COLUMN_SHARED_WITH_ME_CALENDAR_ID = "calendar_id";

    // EVENTS
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_EVENTS_ID = "id";
    public static final String COLUMN_EVENTS_CALENDAR_ID = "calendar_id";
    public static final String COLUMN_EVENTS_NAME = "name";
    public static final String COLUMN_EVENTS_TYPE = "type";
    public static final String COLUMN_EVENTS_DUE_DATE = "due_date";
    public static final String COLUMN_EVENTS_ASSIGN_DATE = "assigned_date";

    // MY EVENTS TABLE
    public static final String TABLE_MY_EVENTS = "my_events";
    public static final String COLUMN_MY_EVENTS_USER_ID = "user_id";
    public static final String COLUMN_MY_EVENTS_EVENT_ID = "event_id";

    // FAV EVENTS TABLE
    public static final String TABLE_FAV_EVENTS = "favorite_events";
    public static final String COLUMN_FAV_EVENTS_USER_ID = "user_id";
    public static final String COLUMN_FAV_EVENTS_EVENT_ID = "event_id";

    private DataSource(){}

    private static DataSource instance = new DataSource();

    public static DataSource getInstance() { return instance; }

    private Connection connection;

    /**
     * Connect to database
     * @return true if successful
     */
    public boolean connectDb()  {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
            return true;
        } catch (SQLException e) {
            System.out.println("Could not connect to the database: " + e);
            return false;
        }
    }

    /**
     * Disconnect from the database
     */
    public void disConnectDb() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Could not disconnect from the database");
        }
    }

    // GET DATA FROM DATABASE //
    /**
     * Get user information from database and create profiles
     * @return the list of profiles
     */
    public ArrayList<Profile> getAllProfiles() {
        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_USERS)){

            ArrayList<Profile> allUsers = new ArrayList<Profile>();

            while (result.next()) {
                Profile profile = new Profile();
                profile.setId(result.getInt((COLUMN_USERS_ID)));
                profile.setUsername(result.getString(COLUMN_USERS_USER_NAME));
                profile.seteMail(result.getString(COLUMN_USERS_EMAIL));
                profile.setPassword(result.getString(COLUMN_USERS_PASSWORD));
                System.out.println("Avatar: " + result.getString(COLUMN_USERS_AVATAR));

                allUsers.add(profile);
            }
            return allUsers;
        }catch (SQLException e) {
            System.out.println("(getAllProfiles) Query is wrong: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get calendars from database and add related events to calendars
     * @param events
     * @return the list of calendars
     */
    public ArrayList<Calendar> getAllCalendars(ArrayList<Event> events) {

        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_CALENDARS)){

            ArrayList<Calendar> allCalendars = new ArrayList<Calendar>();

            while (result.next()) {
                Calendar calendar = new Calendar();
                calendar.setId(result.getInt((COLUMN_CALENDARS_ID)));
                calendar.setTitle(result.getString(COLUMN_CALENDARS_TITLE));

                allCalendars.add(calendar);
            }

            addRelatedEventsToCalendars(allCalendars, events);
            return allCalendars;
        }catch (SQLException e) {
            System.out.println("(GetAllCalendars) Query is wrong: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get all events from database
     * @return the list of events
     */
    public ArrayList<Event> getAllEvents() {

        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_EVENTS)){

            ArrayList<Event> allEvents = new ArrayList<Event>();

            while (result.next()) {
                Event event = new Event();
                event.setId(result.getInt((COLUMN_EVENTS_ID)));
                event.setCalendarID(result.getInt(COLUMN_EVENTS_CALENDAR_ID));
                event.setName(result.getString(COLUMN_EVENTS_NAME));
                event.setType(result.getString(COLUMN_EVENTS_TYPE));
                event.setDueDate(result.getDate(COLUMN_EVENTS_DUE_DATE).toLocalDate());
                event.setAssignDate(result.getDate(COLUMN_EVENTS_ASSIGN_DATE).toLocalDate());

                allEvents.add(event);
            }
            return allEvents;
        }catch (SQLException e) {
            System.out.println("(getAllEvents)Query is wrong: " + e.getMessage());
            return null;
        }
    }

    /**
     * Add related events to the calendars
     * @param calendars
     * @param events
     */
    private void addRelatedEventsToCalendars(ArrayList<Calendar> calendars, ArrayList<Event> events) {
        for (Event event: events) {
            for (Calendar calendar: calendars) {
                if (calendar.getId() == event.getCalendarID()) {
                    calendar.addEvent(event);
                }
            }
        }
    }

    /**
     * Add friends of user
     * @param user
     * @param profiles
     */
    private void addFriendsToUser(User user, ArrayList<Profile> profiles) {
        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " +  TABLE_FRIENDS +
                    " INNER JOIN " +  TABLE_USERS + " ON " + TABLE_FRIENDS + "." + COLUMN_FRIENDS_FRIEND_ID + " = " + TABLE_USERS + "." + COLUMN_USERS_ID +
                    " LEFT JOIN " + TABLE_USERS + " u2 ON " + TABLE_FRIENDS + "." + COLUMN_FRIENDS_USER_ID + "= " + "u2" + "." + COLUMN_USERS_ID)){

            while (result.next()) {
                if (result.getInt("user_id") == user.getMyProfile().getId()) {
                    System.out.println(111);
                    System.out.println(profiles.size());
                    for (Profile friend : profiles) {
                        System.out.println(2222);
                        System.out.println(friend);
                        if (friend.getId() == result.getInt("friend_id")) {
                            user.addFriend(friend);
                        }
                    }
                }
            }
        }catch (SQLException e) {
            System.out.println("(addFriendsToUser)Query is wrong: " + e.getMessage());
        }
    }

    /**
     * Add myCalendars to user's my calendars
     * @param user
     * @param calendars
     */
    private void addMyCalendarsToUser(User user, ArrayList<Calendar> calendars) {
        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " +  TABLE_MY_CALENDARS +
                    " INNER JOIN " +  TABLE_CALENDARS + " ON " + TABLE_MY_CALENDARS + "." + COLUMN_MY_CALENDARS_CALENDAR_ID + " = " + TABLE_CALENDARS + "." + COLUMN_CALENDARS_ID +
                    " INNER JOIN " + TABLE_USERS + " ON " + TABLE_MY_CALENDARS + "." + COLUMN_MY_CALENDARS_USER_ID + "=" + TABLE_USERS + "." + COLUMN_USERS_ID)){

            while (result.next()) {
                if (result.getInt("user_id") == user.getMyProfile().getId()) {
                    for (Calendar calendar : calendars) {
                        if (calendar.getId() == result.getInt("calendar_id")) {
                            user.addMyCal(calendar);
                        }
                    }
                }
            }
        }catch (SQLException e) {
            System.out.println("(addMyCalendarsToUser) Query is wrong: " + e.getMessage());
        }
    }

    /**
     * Add myCalendars to user's my calendars
     * @param user
     * @param calendars
     */
    private void addFavCalendarsToUser(User user, ArrayList<Calendar> calendars) {
        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " +  TABLE_FAV_CALENDARS +
                    " INNER JOIN " +  TABLE_CALENDARS + " ON " + TABLE_FAV_CALENDARS + "." + COLUMN_FAV_CALENDARS_CALENDAR_ID + " = " + TABLE_CALENDARS + "." + COLUMN_CALENDARS_ID +
                    " INNER JOIN " + TABLE_USERS + " ON " + TABLE_FAV_CALENDARS + "." + COLUMN_FAV_CALENDARS_USER_ID + "=" + TABLE_USERS + "." + COLUMN_USERS_ID)){

            while (result.next()) {
                if (result.getInt("user_id") == user.getMyProfile().getId()) {
                    for (Calendar calendar : calendars) {
                        if (calendar.getId() == result.getInt("calendar_id")) {
                            user.addFavCal(calendar);
                        }
                    }
                }
            }
        }catch (SQLException e) {
            System.out.println("(addFavCalendarsToUser)Query is wrong: " + e.getMessage());
        }
    }

    /**
     * Add shared with me calendars to user's shared with me
     * @param user
     * @param calendars
     */
    private void addSharedWithMeToUser(User user, ArrayList<Calendar> calendars) {
        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " +  TABLE_SHARED_WITH_ME +
                    " INNER JOIN " +  TABLE_CALENDARS + " ON " + TABLE_SHARED_WITH_ME + "." + COLUMN_SHARED_WITH_ME_CALENDAR_ID + " = " + TABLE_CALENDARS + "." + COLUMN_CALENDARS_ID +
                    " INNER JOIN " + TABLE_USERS + " ON " + TABLE_SHARED_WITH_ME + "." + COLUMN_SHARED_WITH_ME_USER_ID + "=" + TABLE_USERS + "." + COLUMN_USERS_ID)){

            while (result.next()) {
                if (result.getInt("user_id") == user.getMyProfile().getId()) {
                    for (Calendar calendar : calendars) {
                        if (calendar.getId() == result.getInt("calendar_id")) {
                            user.addSharedWithMe(calendar);
                        }
                    }
                }
            }
        }catch (SQLException e) {
            System.out.println("(addSharedWithMeToUser)Query is wrong: " + e.getMessage());
        }
    }

    /**
     * Add my events to user's my events
     * @param user
     * @param events
     */
    private void addMyEventsToUser(User user, ArrayList<Event> events) {
        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " +  TABLE_MY_EVENTS +
                    " INNER JOIN " +  TABLE_EVENTS + " ON " + TABLE_MY_EVENTS + "." + COLUMN_MY_EVENTS_EVENT_ID + " = " + TABLE_EVENTS + "." + COLUMN_EVENTS_ID +
                    " INNER JOIN " + TABLE_USERS + " ON " + TABLE_MY_EVENTS + "." + COLUMN_MY_EVENTS_USER_ID + "=" + TABLE_USERS + "." + COLUMN_USERS_ID)){

            while (result.next()) {
                if (result.getInt("user_id") == user.getMyProfile().getId()) {
                    for (Event event : events) {
                        if (event.getId() == result.getInt("event_id")) {
                            user.addMyEvent(event);
                        }
                    }
                }
            }
        }catch (SQLException e) {
            System.out.println("(addMyEventsToUser) Query is wrong: " + e.getMessage());
        }
    }

    /**
     * Add my events to user's my events
     * @param user
     * @param events
     */
    private void addFavEventsToUser(User user, ArrayList<Event> events) {
        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " +  TABLE_FAV_EVENTS +
                    " INNER JOIN " +  TABLE_EVENTS + " ON " + TABLE_FAV_EVENTS + "." + COLUMN_FAV_EVENTS_EVENT_ID + " = " + TABLE_EVENTS + "." + COLUMN_EVENTS_ID +
                    " INNER JOIN " + TABLE_USERS + " ON " + TABLE_FAV_EVENTS + "." + COLUMN_FAV_EVENTS_USER_ID + "=" + TABLE_USERS + "." + COLUMN_USERS_ID)){

            while (result.next()) {
                if (result.getInt("user_id") == user.getMyProfile().getId()) {
                    for (Event event : events) {
                        if (event.getId() == result.getInt("event_id")) {
                            user.addFavEvent(event);
                        }
                    }
                }
            }
        }catch (SQLException e) {
            System.out.println("(addFavEventsToUser)Query is wrong: " + e.getMessage());
        }
    }

    /**
     * Get users by combining the calendars, events and profiles
     * @return
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Event> events = getAllEvents();
        ArrayList<Calendar> calendars = getAllCalendars(events);
        ArrayList<Profile> profiles = getAllProfiles();

        for (Profile profile : profiles) {
            if (profile.getUsername() != null && profile.getPassword() != null && profile.geteMail() != null) {
                User user = new User();
                user.setMyProfile(profile);
                addMyCalendarsToUser(user, calendars);
                addFavCalendarsToUser(user, calendars);
                addSharedWithMeToUser(user, calendars);
                addFavEventsToUser(user, events);
                addMyEventsToUser(user, events);
                addFriendsToUser(user, profiles);
                users.add(user);
            }
        }
        return users;
    }

    // UPDATE DATABASE //
    /**
     * Update given user on db
     * @param id
     * @param userName
     * @param password
     * @param email
     * @return true if successful
     */
    public boolean updateProfile(int id, String userName, String password, String email) {

        String query = "UPDATE " + TABLE_USERS + " SET " +
                COLUMN_USERS_EMAIL + "= ?" + "," +
                COLUMN_USERS_USER_NAME + "= ?" + "," +
                COLUMN_USERS_PASSWORD + "= ?" +
                " WHERE " + COLUMN_USERS_ID + "= ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,email);
            statement.setString(2,userName);
            statement.setString(3,password);
            statement.setInt(4,id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update given Calendar on Database
     * @param id
     * @param title
     * @return true if successful
     */
    public boolean updateCalendar( int id, String title) {

        String query = " UPDATE " + TABLE_CALENDARS + " SET " +
                COLUMN_CALENDARS_TITLE  + "= ?" +
                " WHERE " + COLUMN_CALENDARS_ID + "= ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1,title);
                statement.setInt(2,id);

                int result  = statement.executeUpdate();
                return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update Shared Calendars on Database
     * @param userID
     * @param calendarID
     * @return true if successful
     */
    public boolean updateSharedWithMe( int userID, int calendarID) {
        String query = " UPDATE " + TABLE_SHARED_WITH_ME + " SET " +
                COLUMN_SHARED_WITH_ME_CALENDAR_ID + "= ?" +
                " WHERE " + COLUMN_SHARED_WITH_ME_USER_ID + "= ?";
         try( PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,calendarID);
            statement.setInt(2,userID);

            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * adds given sharedWithMe calendar to database
     * @param userID
     * @param calendarID
     * @return true if successful
     */
    public boolean addSharedWithMeToDataBase( int userID, int calendarID) {
        String query = " INSERT INTO " + TABLE_SHARED_WITH_ME + "(" + COLUMN_SHARED_WITH_ME_USER_ID + "," +  COLUMN_SHARED_WITH_ME_CALENDAR_ID +
                ")" + " VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, userID);
            statement.setInt(2, calendarID);

            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * adds given calendar to fav calendar database
     * @param id
     * @param userId
     * @return true if the operation is successful
     */
    public boolean addFavCalendarToDataBase(int id, int userId) {
        String query = " INSERT INTO " + TABLE_FAV_CALENDARS + "(" + COLUMN_FAV_CALENDARS_USER_ID + "," + COLUMN_FAV_CALENDARS_CALENDAR_ID +
                ")" + " VALUES (?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,userId);
            statement.setInt(2,id);

            int result  = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * adds given calendar to my calendar database
     * @param id
     * @param userId
     * @return true if the operation is successful
     */
    public boolean addMyCalendarToDataBase(int id, int userId) {
        String query = "INSERT INTO " + TABLE_MY_CALENDARS + "(" + COLUMN_MY_CALENDARS_USER_ID + "," + COLUMN_MY_CALENDARS_CALENDAR_ID +
                ")" + " VALUES (?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,userId);
            statement.setInt(2,id);

            int result  = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update given Event on Database
     * @param id
     * @param name
     * @param type
     * @param dueDate
     * @param assignDate
     * @param calendarId
     * @return true if successful
     */
    public boolean updateEvent(int id, String name, String type, LocalDate dueDate, LocalDate assignDate, int calendarId){

        String query = " UPDATE " + TABLE_EVENTS + " SET " +
                COLUMN_EVENTS_NAME + "= ?," +
                COLUMN_EVENTS_TYPE + "= ?," +
                COLUMN_EVENTS_DUE_DATE +"= ?," +
                COLUMN_EVENTS_ASSIGN_DATE + "= ?," +
                COLUMN_EVENTS_CALENDAR_ID + "= ?" +
                " WHERE " + COLUMN_EVENTS_ID + "= ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,name);
            statement.setString(2,type);
            statement.setDate(3, Date.valueOf(dueDate));
            statement.setDate(4, Date.valueOf(assignDate));
            statement.setInt(5,calendarId);
            statement.setInt(6,id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * adds given event to my event database
     * @param id
     * @param userId
     * @return true if the operation is successful
     */
    public boolean addFavEventToDataBase(int id, int userId) {
        String query = "INSERT INTO " + TABLE_FAV_EVENTS + "(" + COLUMN_FAV_EVENTS_USER_ID + "," + COLUMN_FAV_EVENTS_EVENT_ID +
                ")" + " VALUES (?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,userId);
            statement.setInt(2,id);

            int result  = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * adds given event to my event database
     * @param id
     * @param userId
     * @return true if the operation is successful
     */
    public boolean addMyEventToDataBase(int id, int userId) {
        String query = "INSERT INTO " + TABLE_MY_EVENTS + "(" + COLUMN_MY_EVENTS_USER_ID + "," + COLUMN_MY_EVENTS_EVENT_ID +
                ")" + " VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, id);

            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Post given user to db
     * @param userName
     * @param password
     * @param email
     * @return
     */
    public boolean postProfile(String userName, String password, String email) {

        String query = "INSERT INTO " + TABLE_USERS + "(" + COLUMN_USERS_EMAIL + "," + COLUMN_USERS_USER_NAME + "," +
                COLUMN_USERS_PASSWORD + "," + COLUMN_USERS_REGISTER_DATE + ")" + " VALUES (?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,email);
            statement.setString(2,userName);
            statement.setString(3,password);
            statement.setDate(4, Date.valueOf(LocalDate.now()));

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean postCalendar(String title) {

        String query = "INSERT INTO " + TABLE_CALENDARS + "(" + COLUMN_CALENDARS_TITLE + "," + COLUMN_CALENDARS_CREATE_DATE  + ")" + " VALUES (?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,title);
            statement.setDate(2, Date.valueOf(LocalDate.now()));

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * post given event to db
     * @param name
     * @param type
     * @param dueDate
     * @param assignDate
     * @param calendarId
     * @return true if the operation is successful
     */
    public boolean postEvent(String name, String type, LocalDate dueDate, LocalDate assignDate, int calendarId) {
        String query = "INSERT INTO " + TABLE_EVENTS + "(" + COLUMN_EVENTS_CALENDAR_ID + "," + COLUMN_EVENTS_NAME + ","
                + COLUMN_EVENTS_TYPE + "," +COLUMN_EVENTS_DUE_DATE + "," + COLUMN_EVENTS_ASSIGN_DATE + ")" +
                " VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,calendarId);
            statement.setString(2,name);
            statement.setString(3,type);
            statement.setDate(4, Date.valueOf(dueDate));
            statement.setDate(5, Date.valueOf(assignDate));

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete the event with given id from database
     * @param id
     * @return true if the operation is successful
     */
    public boolean deleteEvent(int id) {
        String query = "DELETE FROM  " + TABLE_EVENTS + " WHERE " + COLUMN_EVENTS_ID + " = ?" ;

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete the user with given id
     * @param id
     * @return
     */
    public boolean deleteProfile(int id) {
        String query = "DELETE FROM  " + TABLE_USERS + " WHERE " + COLUMN_USERS_ID + " = ?" ;

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete calendar with given id
     * @param id
     * @return
     */
    public boolean deleteCalendar(int id) {
        String query = "DELETE FROM  " + TABLE_CALENDARS + " WHERE " + COLUMN_CALENDARS_ID + " = ?" ;

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete calendar with given id
     * @param id
     * @return
     */
    public boolean deleteFavCalendar(int id, int user_id) {
        String query = "DELETE FROM  " + TABLE_FAV_CALENDARS + " WHERE " + COLUMN_FAV_CALENDARS_CALENDAR_ID + " = ?" + " AND " + COLUMN_FAV_CALENDARS_USER_ID + " = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);
            statement.setInt(2,user_id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete calendar with given id
     * @param id
     * @return
     */
    public boolean deleteMyCalendar(int id, int user_id) {
        String query = "DELETE FROM  " + TABLE_MY_CALENDARS + " WHERE " + COLUMN_MY_CALENDARS_CALENDAR_ID + " = ?" + " AND " + COLUMN_MY_CALENDARS_USER_ID + " = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);
            statement.setInt(2,user_id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete calendar with given id
     * @param id
     * @return
     */
    public boolean deleteMyEvent(int id, int user_id) {
        String query = "DELETE FROM  " + TABLE_MY_EVENTS + " WHERE " + COLUMN_MY_EVENTS_EVENT_ID+ " = ?" + " AND " + COLUMN_MY_EVENTS_USER_ID + " = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);
            statement.setInt(2,user_id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete calendar with given id
     * @param id
     * @return
     */
    public boolean deleteFavEvent(int id, int user_id) {
        String query = "DELETE FROM  " + TABLE_FAV_EVENTS + " WHERE " + COLUMN_FAV_EVENTS_EVENT_ID+ " = ?" + " AND " + COLUMN_FAV_EVENTS_USER_ID + " = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,id);
            statement.setInt(2,user_id);

            int result = statement.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
