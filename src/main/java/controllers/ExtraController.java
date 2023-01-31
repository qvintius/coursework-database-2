package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Passenger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainpackage.Const;
import mainpackage.DatabaseHandler;

public class ExtraController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackButton;

    @FXML
    private Label ErrorLabel;

    @FXML
    private TextField MailField;

    @FXML
    private TextField NameField;

    @FXML
    private Button OkButton;

    @FXML
    private TextField PassportField;

    @FXML
    private TextField TelephoneField;

    private Passenger passenger;
    DatabaseHandler dbHandler = new DatabaseHandler();
    @FXML
    void initialize() {
        BackButton.setOnAction(actionEvent -> actionClose());

        OkButton.setOnAction(actionEvent -> {
            if (passenger != null && TelephoneField.getText() != "" && MailField.getText() != "" &&
                    isParsable(TelephoneField) && isParsable(PassportField))
                changePassenger();
            else if (TelephoneField.getText() != "" && MailField.getText() != "" &&
                    isParsable(TelephoneField) && isParsable(PassportField))
                addPassenger();
                else ErrorLabel.setText("Неверный формат");
        });


    }
    private  void changePassenger(){
        ErrorLabel.setText(null);
        int id_passenger = passenger.getId_passenger();
        String oldName = passenger.getName_passenger();
        Long oldTelephone = passenger.getTelephone();
        String oldMail = passenger.getMail();
        Long oldPassport = passenger.getPassport();

        String newName = NameField.getText();
        String newMail = MailField.getText();
        long newTelephone = Long.parseLong(TelephoneField.getText());
        long newPassport = Long.parseLong(PassportField.getText());
        passenger.setName_passenger(newName);
        passenger.setTelephone(newTelephone);
        passenger.setMail(newMail);
        passenger.setPassport(newPassport);
        try {
            Connection con = dbHandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("UPDATE " + Const.PASSENGERS_TABLE + " SET " +
                    Const.PASSENGER_NAME + " = '" + newName + "', " +
                    Const.PASSENGER_TELEPHONE + " = " + newTelephone + ", " +
                    Const.PASSENGER_MAIL + " = '" + newMail + "', " +
                    Const.PASSENGER_PASSPORT + " = " + newPassport + " WHERE " + Const.PASSENGER_ID + " = " + id_passenger);
            prst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ErrorLabel.setText(null);
        actionClose();
    }
    private void addPassenger(){
        ErrorLabel.setText(null);
        int id_passenger = 0;
        String name_pas = NameField.getText();
        String mail = MailField.getText();
        long telephone = Long.parseLong(TelephoneField.getText());
        long passport = Long.parseLong(PassportField.getText());
        try {
            Connection con = dbHandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("INSERT INTO " + Const.PASSENGERS_TABLE + " ( " +
                    Const.PASSENGER_NAME + ", " + Const.PASSENGER_TELEPHONE +  ", " + Const.PASSENGER_MAIL +  ", " +
                    Const.PASSENGER_PASSPORT + " ) VALUES ('" + name_pas + "', " + telephone + ", '" + mail + "', " + passport + ")");

            prst.execute();
            ResultSet rs = con.createStatement().executeQuery("SELECT " + Const.PASSENGER_ID + " FROM " + Const.PASSENGERS_TABLE + " WHERE ROWNUM=1 ORDER BY " + Const.PASSENGER_ID + " DESC");
            while (rs.next())
                id_passenger = rs.getInt(Const.PASSENGER_ID);
            Passenger passenger = new Passenger(id_passenger, name_pas, telephone, mail, passport);
            Controller.listPassengers.add(passenger);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        actionClose();
    }
    private boolean isParsable(TextField textField){
        long var = 0;
        try {
            var = Long.parseLong(textField.getText());
            if (var > 0) return true;
        }
        catch (NumberFormatException e){
            System.out.println("err");
            return false;
        }
        return false;//default
    }
    private void actionClose() {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.hide();//закрыть модальное окно
    }//закрытие модального окна

    public void setPassenger(Passenger selectedPassenger) {
        this.passenger = selectedPassenger;
        NameField.setText(passenger.getName_passenger());
        TelephoneField.setText(String.valueOf(passenger.getTelephone()));
        MailField.setText(passenger.getMail());
        PassportField.setText(String.valueOf(passenger.getPassport()));
    }
}
