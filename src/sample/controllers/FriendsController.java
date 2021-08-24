package sample.controllers;

import sample.models.DataSource;
import sample.models.User;

public class FriendsController {


    User user = MainScreenController.getUser();

    public void addFriend(){
        DataSource ds = DataSource.getInstance();
        //user.addFriend();
    }

    public void openChatBox() {

    }
}
