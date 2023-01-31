package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import mainpackage.Const;
import mainpackage.DatabaseHandler;

public class ExtraFlightController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackButton;

    @FXML
    private Label ErrorLabel;

    @FXML
    private TextField NumberField;

    @FXML
    private TextField DateDepField;

    @FXML
    private TextField DateDesField;

    @FXML
    private Button OkButton;

    @FXML
    private ComboBox BusBox;

    @FXML
    private ComboBox StatusBox;

    @FXML
    private ComboBox AdresDesBox;

    @FXML
    private ComboBox AdresDepBox;

    private Flight flight;
    DatabaseHandler dbHandler = new DatabaseHandler();
    ObservableList<Adress> adresslist = FXCollections.observableArrayList();
    ObservableList<Status> statuslist = FXCollections.observableArrayList();
    ObservableList<Bus> buslist = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        fillComboBox();
        BackButton.setOnAction(actionEvent -> actionClose());
        OkButton.setOnAction(actionEvent -> {
            boolean t = NumberField.getText() != "" && DateDepField.getText() != "" && DateDesField.getText() != "" &&  isParsable(NumberField)
                    && BusBox.getSelectionModel().getSelectedItem()!=null && StatusBox.getSelectionModel().getSelectedItem()!=null &&
                    AdresDepBox.getSelectionModel().getSelectedItem()!=null && AdresDesBox.getSelectionModel().getSelectedItem()!=null;
            if (flight != null && t)
                changeFlight();
            else if (t)
                addFlight();
            else ErrorLabel.setText("Не все данные корректны");
        });
    }
    public static boolean isParsable(TextField textField){//в номере рейса только цифры
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
    private  void changeFlight(){
        ErrorLabel.setText(null);
        int id_flight = flight.getId_flight();
        int numberFlight = Integer.parseInt(NumberField.getText());
        String dateDep = DateDepField.getText();
        String dateDes = DateDesField.getText();
        String departure = adresslist.get(AdresDepBox.getSelectionModel().getSelectedIndex()).getName_adress();
        String destination = adresslist.get(AdresDesBox.getSelectionModel().getSelectedIndex()).getName_adress();
        String name_type = buslist.get(BusBox.getSelectionModel().getSelectedIndex()).getName_type();
        int id_type = buslist.get(BusBox.getSelectionModel().getSelectedIndex()).getType_id();
        int seats_number = buslist.get(BusBox.getSelectionModel().getSelectedIndex()).getSeats_number();
        int id_bus = buslist.get(BusBox.getSelectionModel().getSelectedIndex()).getId_bus();
        int id_status = statuslist.get(StatusBox.getSelectionModel().getSelectedIndex()).getId_status();
        String name_status = statuslist.get(StatusBox.getSelectionModel().getSelectedIndex()).getName_status();
        int id_departure = adresslist.get(AdresDepBox.getSelectionModel().getSelectedIndex()).getId_adress();
        int id_destination = adresslist.get(AdresDesBox.getSelectionModel().getSelectedIndex()).getId_adress();

        flight.setNumber_flight(numberFlight);
        flight.setId_departure(id_departure);
        flight.setId_destination(id_destination);
        flight.setDeparture(departure);
        flight.setDestination(destination);
        flight.setDate_departure(dateDep);
        flight.setDate_destination(dateDes);
        flight.setBus_id(id_bus);
        flight.setSeats_number(seats_number);
        flight.setId_status(id_status);
        flight.setName_status(name_status);
        flight.setId_type(id_type);
        flight.setName_type(name_type);
        try {
            Connection con = dbHandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("UPDATE " + Const.FLIGHTS_TABLE + " SET " +
                    Const.FLIGHT_NUMBER + " = " + numberFlight  + ", " +
                    Const.ADRESS_DEPARTURE + " = " + id_departure + ", " +
                    Const.ADRESS_DESTINATION + " = " + id_destination + ", " +
                    Const.DATE_DEPARTURE + " = TO_DATE('" + dateDep + "', 'yyyy-mm-dd'), " +
                    Const.DATE_DESTINATION + " = TO_DATE('" + dateDes + "', 'yyyy-mm-dd'), " +
                    Const.FLIGHT_BUS_ID + " = " + id_bus + ", " +
                    Const.STATUS + " = " + id_status +
                    " WHERE " + Const.FLIGHT_ID + " = " + id_flight);
            prst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        actionClose();
    }
    private void addFlight(){
        ErrorLabel.setText(null);
        int id_flight = 0;
        int numberFlight = Integer.parseInt(NumberField.getText());
        String dateDep = DateDepField.getText();
        String dateDes = DateDesField.getText();
        String departure = adresslist.get(AdresDepBox.getSelectionModel().getSelectedIndex()).getName_adress();
        String destination = adresslist.get(AdresDesBox.getSelectionModel().getSelectedIndex()).getName_adress();
        String name_type = buslist.get(BusBox.getSelectionModel().getSelectedIndex()).getName_type();
        int id_type = buslist.get(BusBox.getSelectionModel().getSelectedIndex()).getType_id();
        int seats_number = buslist.get(BusBox.getSelectionModel().getSelectedIndex()).getSeats_number();
        int id_bus = buslist.get(BusBox.getSelectionModel().getSelectedIndex()).getId_bus();
        int id_status = statuslist.get(StatusBox.getSelectionModel().getSelectedIndex()).getId_status();
        String name_status = statuslist.get(StatusBox.getSelectionModel().getSelectedIndex()).getName_status();
        int id_departure = adresslist.get(AdresDepBox.getSelectionModel().getSelectedIndex()).getId_adress();
        int id_destination = adresslist.get(AdresDesBox.getSelectionModel().getSelectedIndex()).getId_adress();

        try {
            Connection con = dbHandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("INSERT INTO " + Const.FLIGHTS_TABLE + " (" + Const.FLIGHT_NUMBER + ", " +
                    Const.ADRESS_DEPARTURE +  ", " + Const.ADRESS_DESTINATION +  ", " + Const.DATE_DEPARTURE + ", " + Const.DATE_DESTINATION + ", " +
                    Const.FLIGHT_BUS_ID + ", " + Const.STATUS + ") VALUES (" + numberFlight + ", " + id_departure + ", " + id_destination + ", TO_DATE('" +
                    dateDep + "', 'yyyy-mm-dd'), TO_DATE('" + dateDes + "', 'yyyy-mm-dd'), " + id_bus + ", " + id_status + ")");
            prst.execute();
            ResultSet rs = con.createStatement().executeQuery("SELECT " + Const.FLIGHT_ID + " FROM " + Const.FLIGHTS_TABLE + " WHERE ROWNUM=1 ORDER BY " + Const.FLIGHT_ID + " DESC");
            while (rs.next())
                id_flight = rs.getInt(Const.FLIGHT_ID);
            LocalDate dateDeparture = LocalDate.parse(dateDep);
            LocalDate dateDestination = LocalDate.parse(dateDes);
            Flight flight = new Flight(id_flight, numberFlight, id_departure, departure, id_destination, destination, dateDeparture,
                    dateDestination, id_bus, seats_number, id_status, name_status,id_type, name_type);
            Controller.listFlights.add(flight);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        actionClose();
    }

    private void actionClose() {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.hide();//закрыть модальное окно
    }//закрытие модального окна
    private void fillComboBox() {
        try {
            Connection con1 = dbHandler.getDbConnection();
            ResultSet rs1 = con1.createStatement().executeQuery("SELECT * FROM " + Const.ADRESS_TABLE +
                    " ORDER BY " + Const.ADRESS_ID);
            while (rs1.next()){
                adresslist.add(new Adress(rs1.getInt(Const.ADRESS_ID), rs1.getString(Const.ADRES_NAME)));
            }
            Connection con2 = dbHandler.getDbConnection();
            ResultSet rs2 = con2.createStatement().executeQuery("SELECT * FROM " + Const.BUSES_TABLE + " INNER JOIN " +
                    Const.BUS_TYPE_TABLE + " ON " + Const.BUS_TYPE_ID + "=" + Const.TYPE_ID + " ORDER BY " + Const.BUS_ID);
            while (rs2.next()){
                buslist.add(new Bus(rs2.getInt(Const.BUS_ID), rs2.getString(Const.BUS_NUMBER),rs2.getDate(Const.DATE_BEGIN).toLocalDate(), rs2.getDate(Const.DATE_END).toLocalDate(),
                        rs2.getInt(Const.SEATS_NUMBER), rs2.getInt(Const.TYPE_ID), rs2.getString(Const.TYPE_NAME)));
            }
            Connection con3 = dbHandler.getDbConnection();
            ResultSet rs3 = con3.createStatement().executeQuery("SELECT * FROM " + Const.STATUS_TABLE +
                    " ORDER BY " + Const.STATUS_ID);
            while (rs3.next()){
                statuslist.add(new Status(rs3.getInt(Const.STATUS_ID), rs3.getString(Const.STATUS_NAME)));
            }
            AdresDepBox.setItems(adresslist);
            AdresDesBox.setItems(adresslist);
            BusBox.setItems(buslist);
            StatusBox.setItems(statuslist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void setFlight(Flight selectedFlight) {
        this.flight = selectedFlight;
        NumberField.setText(String.valueOf(flight.getNumber_flight()));
        DateDepField.setText(flight.getDate_departure());
        DateDesField.setText(flight.getDate_destination());
        BusBox.getSelectionModel().select(selectedFlight.getBus_id()-19);//соответствие с id в бд
        AdresDepBox.getSelectionModel().select(selectedFlight.getId_departure()-1);//в бд индексы с 1, в списке с 0
        AdresDesBox.getSelectionModel().select(selectedFlight.getId_destination()-1);
        StatusBox.getSelectionModel().select(selectedFlight.getId_status()-1);
    }
}
