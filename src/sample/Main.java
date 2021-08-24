package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.WelcomeController;
import sample.models.DataSource;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();

        if (!DataSource.getInstance().connectDb()) {
            System.out.println("Couldn't connect to database!");
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().disConnectDb();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login_screen.fxml"));
        Parent root = loader.load();

        WelcomeController controller = loader.getController();
        //System.out.println(controller);
        controller.getUsers();

        primaryStage.setTitle("Personal Calendar");
        primaryStage.setScene(new Scene(root, 900 , 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
