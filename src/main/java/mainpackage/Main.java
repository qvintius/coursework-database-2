package mainpackage;

import entities.Passenger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:/C:/courseworkbd 3.0/target/classes/mainpackage/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        //Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Междугородние перевозки");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}