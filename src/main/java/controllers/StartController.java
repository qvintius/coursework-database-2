package controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Passenger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mainpackage.Const;
import mainpackage.DatabaseHandler;

public class StartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField LoginTextField;

    @FXML
    private Button SignUpButton;

    @FXML
    private Label ErrorLabel;

    @FXML
    void initialize() {
        LoginButton.setOnAction(this::handle);
        //SignUpButton.setOnAction();
    }

    private void handle(ActionEvent actionEvent) {
        ErrorLabel.setText("");
        String loginText = LoginTextField.getText().trim();
        if (!loginText.equals(""))
            loginUser(loginText);
        else
            ErrorLabel.setText("Неверный логин");
    }

    private void loginUser(String loginText) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Passenger passenger=null;
        try {
            Connection dbConnection = dbHandler.getDbConnection();
            long counter = 0;
            ResultSet rs = dbConnection.createStatement().executeQuery("SELECT * FROM " + Const.PASSENGERS_TABLE + " WHERE " + Const.PASSENGER_MAIL + "='" + loginText+ "'");
            while (rs.next()) {
                counter++;
                passenger = new Passenger(rs.getInt(Const.PASSENGER_ID), rs.getString(Const.PASSENGER_NAME), rs.getLong(Const.PASSENGER_TELEPHONE),
                        rs.getString(Const.PASSENGER_MAIL), rs.getLong(Const.PASSENGER_PASSPORT));
                passenger.id_passengerProperty().set(rs.getInt(Const.PASSENGER_ID));
                passenger.name_passengerProperty().set(rs.getString(Const.PASSENGER_NAME));
                passenger.telephoneProperty().set(rs.getLong(Const.PASSENGER_TELEPHONE));
                passenger.mailProperty().set(rs.getString(Const.PASSENGER_MAIL));
                passenger.passportProperty().set(rs.getLong(Const.PASSENGER_PASSPORT));
            }
            if (passenger != null){
                openNewScene("file:/C:/courseworkbd/target/classes/mainpackage/main.fxml");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void openNewScene(String window) {
        LoginButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new URL(window));
            loader.load();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Междугородние рейсы");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


}
