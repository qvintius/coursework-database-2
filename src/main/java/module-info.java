module mainpackage.courseworkbd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens mainpackage to javafx.fxml;
    exports mainpackage;
    exports entities;
    opens entities to javafx.fxml;
    exports controllers;
    opens controllers to javafx.fxml;
}