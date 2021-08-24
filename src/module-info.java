module Personal.Calendar.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    requires java.sql;
    requires java.mail;

    opens sample.models to javafx.base;
    opens sample.controllers;
    exports sample.controllers;

    opens sample;
    opens sample.javaMailAPI;
}