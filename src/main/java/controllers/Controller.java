package controllers;


import entities.Bus;
import entities.Flight;
import entities.Passenger;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainpackage.Const;
import mainpackage.DatabaseHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox DirectionComboBox1;

    @FXML
    private ComboBox DirectionComboBox2;

    @FXML
    private TableView<Flight> FlightsTable;

    @FXML
    private TableColumn<Flight, SimpleIntegerProperty> NumbFlightCol;

    @FXML
    private TableColumn<Flight, String> AdressDepCol;

    @FXML
    private TableColumn<Flight, String> AdressDesCol;

    @FXML
    private TableColumn<Flight, Date> DateDepCol;

    @FXML
    private TableColumn<Flight, Date> DateDesCol;

    @FXML
    private TableColumn<Flight, String> TypeCol;

    @FXML
    private TableColumn<Flight, String> StatusCol;

    @FXML
    private TableView<Passenger> PassengersTable;

    @FXML
    private TableColumn<Passenger, String> NameCol;

    @FXML
    private TableColumn<Passenger, LongProperty> TelephoneCol;

    @FXML
    private TableColumn<Passenger, String> MailCol;

    @FXML
    private TableColumn<Passenger, String> SeatsCol;

    @FXML
    private TableColumn<Passenger, LongProperty> PassportCol;

    @FXML
    private CheckBox CheckAll;

    @FXML
    private Button AddFlightButton;

    @FXML
    private Button ChangeFlightButton;

    @FXML
    private Button BuyButton;

    @FXML
    private Button CountButton;

    @FXML
    private Button ChangePasButton;

    @FXML
    private Button DelPasButton;

    @FXML
    private Button AddPasButton;

    @FXML
    private Label ErrorLabel;

    @FXML
    private Label LostSeatsLabel;

    @FXML
    private Label CountLabel;

    @FXML
    private TextField DateTextField1;

    @FXML
    private TextField DateTextField2;
    @FXML
    private Button DeleteFlight;
    @FXML
    private Label ListOfPass;

    @FXML
    private Button AllPassButton;

    @FXML
    private Button BusAndAdressButton;

    DatabaseHandler dbhandler = new DatabaseHandler();
    public static ObservableList<Flight> listFlights = FXCollections.observableArrayList();
    ObservableList<String> listDirectionDeparture = FXCollections.observableArrayList();
    ObservableList<String> listDirectionDestination = FXCollections.observableArrayList();
    public static ObservableList<Passenger> listPassengers = FXCollections.observableArrayList();
    ObservableList<Passenger> passOnFlight = FXCollections.observableArrayList();
    int count_left_tickets = 0;
    @FXML
    void initialize() {
        fillTableFlights();
        fillTablePassengers();
        fillDirectionComboBox();
        AllPassButton.setOnAction(actionEvent -> fillTablePassengers());
        FlightsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        PassengersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        AddPasButton.setOnAction(actionEvent -> showDialog(AddPasButton, "extra"));
        ChangePasButton.setOnAction(actionEvent -> {
            Passenger selectedPassenger = (Passenger) PassengersTable.getSelectionModel().getSelectedItem();
            if (selectedPassenger != null){
                showDialog(ChangePasButton, "extra");
                ErrorLabel.setText(null);
            } else ErrorLabel.setText("Не выбран\nпассажир");
        });
        DelPasButton.setOnAction(actionEvent -> {
            Passenger selectedPassenger = (Passenger) PassengersTable.getSelectionModel().getSelectedItem();
            if (selectedPassenger != null)
                deletePass(selectedPassenger);
            else ErrorLabel.setText("Не выбран\nпассажир");
        });
        FlightsTable.setOnMouseClicked(mouseEvent -> remainingTickets());//куплено и осталось билетов
        BuyButton.setOnAction(actionEvent -> {
            Flight selectedFlight = (Flight) FlightsTable.getSelectionModel().getSelectedItem();
            Passenger selectedPasenger = (Passenger) PassengersTable.getSelectionModel().getSelectedItem();
            if (selectedFlight !=null && selectedPasenger !=null && listPassengers.size() != 0)
                buyTicket(selectedFlight, selectedPasenger);
            else ErrorLabel.setText("Не выбран\nрейс/пассажир");
        });//купить билет
        CountButton.setOnAction(actionEvent -> {
            if (DateTextField1.getText() != "" && DateTextField2.getText() != "" &&
                    (DirectionComboBox1.getSelectionModel().getSelectedItem() != null &&
            DirectionComboBox2.getSelectionModel().getSelectedItem() != null && !CheckAll.isSelected() || CheckAll.isSelected()) )
            countOfPass();
            else ErrorLabel.setText("Не все\nпараметры\nуказаны");
        });//количествопассажиров по направлениям
        AddFlightButton.setOnAction(actionEvent -> {
            showDialog(AddFlightButton, "extraflight");
        }); //добавить рейс
        ChangeFlightButton.setOnAction(actionEvent -> {
            Flight selectedFlight = (Flight) FlightsTable.getSelectionModel().getSelectedItem();
            if (selectedFlight != null){
                showDialog(ChangeFlightButton, "extraflight");
                ErrorLabel.setText(null);
            } else ErrorLabel.setText("Не выбран\nрейс");
        });// редактировать рейс
        DeleteFlight.setOnAction(actionEvent -> {
            Flight selectedFlight = (Flight) FlightsTable.getSelectionModel().getSelectedItem();
            if (selectedFlight != null)
                deleteFlight(selectedFlight);
            else ErrorLabel.setText("Не выбран\nрейс");
        });
        BusAndAdressButton.setOnAction(actionEvent -> {
            showDialog(BusAndAdressButton, "busandadress");
        });

    }

    private void deleteFlight(Flight selectedFlight) {
        ErrorLabel.setText(null);
        try {
            Connection con = dbhandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("DELETE FROM " + Const.FLIGHTS_TABLE +
                    " WHERE " + Const.FLIGHT_ID + " = ?");
            prst.setString(1, String.valueOf(selectedFlight.getId_flight()));
            prst.execute();
            listFlights.remove(selectedFlight);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDirectionComboBox() {
        try {
            Connection con = dbhandler.getDbConnection();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT * FROM " + Const.ADRESS_TABLE +
                    " ORDER BY " + Const.ADRES_NAME);
            while (rs1.next()){
                listDirectionDeparture.add(rs1.getString(Const.ADRES_NAME));
                listDirectionDestination.add(rs1.getString(Const.ADRES_NAME));
            }
            DirectionComboBox1.setItems(listDirectionDeparture);
            DirectionComboBox2.setItems(listDirectionDestination);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void countOfPass() {
        ErrorLabel.setText("");
        if (!CheckAll.isSelected())
        try {
            Connection con = dbhandler.getDbConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) AS count FROM " + Const.TICKETS_TABLE +
                    " INNER JOIN " + Const.FLIGHTS_TABLE + " ON " + Const.FLIGHT_ID + "=" + Const.TICKET_FLIGHT_ID +
                    " INNER JOIN " + Const.ADRESS_TABLE + " adep ON " + Const.ADRESS_DEPARTURE + "= adep." + Const.ADRESS_ID +
                    " INNER JOIN " + Const.ADRESS_TABLE + " adest ON " + Const.ADRESS_DESTINATION + "= adest." + Const.ADRESS_ID +
                    " WHERE " + Const.DATE_DEPARTURE + ">=TO_DATE('" + DateTextField1.getText() + "', 'dd-mm-yyyy') AND " +
                    Const.DATE_DESTINATION + "<=TO_DATE('" + DateTextField2.getText() + "', 'dd-mm-yyyy') AND adep." +
                    Const.ADRES_NAME + "='" + DirectionComboBox1.getSelectionModel().getSelectedItem().toString() + "' AND adest."
                    + Const.ADRES_NAME + "='" + DirectionComboBox2.getSelectionModel().getSelectedItem().toString() + "'");
            while (rs.next()){
                CountLabel.setText(String.valueOf(rs.getInt("count")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        else
            try {
                Connection con = dbhandler.getDbConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) AS count FROM " + Const.TICKETS_TABLE +
                        " INNER JOIN " + Const.FLIGHTS_TABLE + " ON " + Const.FLIGHT_ID + "=" + Const.TICKET_FLIGHT_ID +
                        " INNER JOIN " + Const.ADRESS_TABLE + " adep ON " + Const.ADRESS_DEPARTURE + "= adep." + Const.ADRESS_ID +
                        " INNER JOIN " + Const.ADRESS_TABLE + " adest ON " + Const.ADRESS_DESTINATION + "= adest." + Const.ADRESS_ID +
                        " WHERE " + Const.DATE_DEPARTURE + ">=TO_DATE('" + DateTextField1.getText() + "', 'dd-mm-yyyy') AND " +
                        Const.DATE_DESTINATION + "<=TO_DATE('" + DateTextField2.getText() + "', 'dd-mm-yyyy')");
                while (rs.next()){
                    CountLabel.setText(String.valueOf(rs.getInt("count")));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    private void buyTicket(Flight selectedFlight, Passenger selectedPassenger) {
        ErrorLabel.setText(null);
        if (count_left_tickets != 0 && selectedFlight.getName_status().equals("Не отменен")){
            int id_flight = selectedFlight.getId_flight();
            int id_passenger = selectedPassenger.getId_passenger();
            System.out.println(id_flight + " " + id_passenger);
            ArrayList arr = new ArrayList();
            try {
                Connection con = dbhandler.getDbConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + Const.TICKETS_TABLE +
                        " WHERE " + Const.TICKET_FLIGHT_ID + " = " + selectedFlight.getId_flight());
                while (rs.next()){
                    arr.add(rs.getInt(Const.PLACE_NUMBER));
                }//получить число мест на рейс

                Random rand = new Random();
                int s = rand.nextInt(selectedFlight.getSeats_number())+1;
                while(arr.contains(s)){
                    s = rand.nextInt(selectedFlight.getSeats_number())+1;
                }

                System.out.println(arr);
                System.out.println(s);
                Date date = new Date();
                int style = DateFormat.SHORT;
                DateFormat df = DateFormat.getDateInstance(style, Locale.ENGLISH);
                System.out.println(df.format(date));
                PreparedStatement prst = con.prepareStatement("INSERT INTO " + Const.TICKETS_TABLE +
                        " (" + Const.TICKET_PASSENGER_ID + ", " + Const.PLACE_NUMBER + ", " + Const.PRICE + ", " +
                        Const.DATE_BOOKING + ", " + Const.TICKET_FLIGHT_ID + ") VALUES (" + selectedPassenger.getId_passenger() + ", " +
                        s + ", " + 200 + ", TO_DATE('" + df.format(date) + "', 'mm/dd/yyyy'), " + selectedFlight.getId_flight() + ")");
                prst.execute();//купить билет
                remainingTickets();//обновить количество оставшихся билетов
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else ErrorLabel.setText("Не осталось\n билетов\nвыберите не отмененный рейс");
    }

    private void remainingTickets() {
        ListOfPass.setText("Пассажиры рейса");
        passOnFlight.clear();
        listPassengers.clear();
        Flight selectedflight = (Flight) FlightsTable.getSelectionModel().getSelectedItem();
        //количество билетов
        try {
            Connection con = dbhandler.getDbConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(" + Const.TICKET_ID + ") AS bought, " + Const.SEATS_NUMBER + "-COUNT(" + Const.TICKET_ID + ") AS left FROM " + Const.TICKETS_TABLE +
                    " INNER JOIN " +  Const.FLIGHTS_TABLE + " ON " + Const.TICKET_FLIGHT_ID + "=" + Const.FLIGHT_ID +
                    " INNER JOIN " +  Const.BUSES_TABLE + " ON " + Const.BUS_ID + "=" + Const.FLIGHT_BUS_ID +
                    " WHERE " + Const.FLIGHT_ID + "=" + selectedflight.getId_flight() +
                    " GROUP BY " + Const.SEATS_NUMBER);
            if (rs.next()){
                count_left_tickets = rs.getInt("left");
                LostSeatsLabel.setText("Осталось билетов: " + String.valueOf(count_left_tickets) + "\nКуплено билетов: " + String.valueOf(rs.getInt("bought")));
            }
            else {
                count_left_tickets = selectedflight.getSeats_number();
                LostSeatsLabel.setText("Осталось билетов: " + count_left_tickets + "\nКуплено билетов: " + "0");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //список пассажиров определенного рейса
        try {
            Connection con = dbhandler.getDbConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + Const.PASSENGERS_TABLE +
                    " INNER JOIN " +  Const.TICKETS_TABLE + " ON " + Const.PASSENGER_ID + "=" + Const.TICKET_PASSENGER_ID +
                    " WHERE " + Const.TICKET_FLIGHT_ID + "=" + selectedflight.getId_flight() +
                    " ORDER BY " + Const.PASSENGER_NAME);
            while (rs.next()){
                passOnFlight.add(new Passenger(rs.getInt(Const.PASSENGER_ID),
                        rs.getString(Const.PASSENGER_NAME), rs.getLong(Const.PASSENGER_TELEPHONE),
                        rs.getString(Const.PASSENGER_MAIL), rs.getLong(Const.PASSENGER_PASSPORT)));
                /*count_left_tickets = rs.getInt("left");
                LostSeatsLabel.setText("Осталось билетов: " + String.valueOf(count_left_tickets) + "\nКуплено билетов: " + String.valueOf(rs.getInt("bought")));*/
            }
            NameCol.setCellValueFactory(new PropertyValueFactory<>(Const.PASSENGER_NAME));
            TelephoneCol.setCellValueFactory(new PropertyValueFactory<>(Const.PASSENGER_TELEPHONE));
            MailCol.setCellValueFactory(new PropertyValueFactory<>(Const.PASSENGER_MAIL));
            PassportCol.setCellValueFactory(new PropertyValueFactory<>(Const.PASSENGER_PASSPORT));
            PassengersTable.setItems(passOnFlight);
            /*else {
                count_left_tickets = 0;
                LostSeatsLabel.setText("Осталось билетов: " + selectedflight.getSeats_number() + "\nКуплено билетов: " + count_left_tickets);
            }*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePass(Passenger selectedPassenger) {
        ErrorLabel.setText(null);
        try {
            Connection con = dbhandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("DELETE FROM " + Const.PASSENGERS_TABLE +
                    " WHERE " + Const.PASSENGER_ID + " = ?");
            prst.setString(1, String.valueOf(selectedPassenger.getId_passenger()));
            prst.execute();
            Flight selectedFlight = (Flight) FlightsTable.getSelectionModel().getSelectedItem();
            if (selectedFlight !=null)
                remainingTickets();
            passOnFlight.remove(selectedPassenger);
            listPassengers.remove(selectedPassenger);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showDialog(Button clickedButton, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new URL("file:/C:/courseworkbd 3.0/target/classes/mainpackage/" + title + ".fxml"));
            Parent parent = fxmlLoader.load();
            if (clickedButton.getId().equals("ChangePasButton") || clickedButton.getId().equals("AddPasButton")){
                ExtraController extraController = fxmlLoader.getController();
                if (clickedButton.getId().equals("ChangePasButton")){
                    Passenger selectedPassenger = (Passenger) PassengersTable.getSelectionModel().getSelectedItem();//если кнопка изменить, то вывести изменяемые поля
                    extraController.setPassenger(selectedPassenger);//если кнопка изменить, то вывести изменяемые поля
                }
            }
            else if (clickedButton.getId().equals("AddFlightButton") || clickedButton.getId().equals("ChangeFlightButton")){
                ExtraFlightController extraFlightController = fxmlLoader.getController();
                if (clickedButton.getId().equals("ChangeFlightButton")){
                    Flight selectedFlight = (Flight) FlightsTable.getSelectionModel().getSelectedItem();
                    extraFlightController.setFlight(selectedFlight);
                }
            } else if (clickedButton.getId().equals("BusAndAdressButton")){
                BusAndAdresController busAndAdresController = fxmlLoader.getController();
            }
            Stage stage = new Stage();
            stage.setTitle("Редактирование записи");
            //stage.setMinHeight(150);
            //stage.setMinWidth(300);
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);//создание дочернего модального
            stage.initOwner(clickedButton.getScene().getWindow());//определение родительского окна
            stage.show();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        };
    }

    private void fillTableFlights() {
        try {
            Connection con = dbhandler.getDbConnection();
            /*ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + Const.FLIGHTS_TABLE +
                    " INNER JOIN " +  Const.BUSES_TABLE + " ON " + Const.FLIGHT_BUS_ID + "=" + Const.BUS_ID +
                    " INNER JOIN " +  Const.BUS_TYPE_TABLE + " ON " + Const.BUS_TYPE_ID + "=" + Const.TYPE_ID +
                    " ORDER BY " + Const.FLIGHT_ID + " ASC");*/
            ResultSet rs = con.createStatement().executeQuery("SELECT " + Const.FLIGHT_ID + ", " + Const.FLIGHT_NUMBER +
                    ", adep." +
                    Const.ADRESS_ID + " as id_departure, adest." + Const.ADRESS_ID + " as id_destination" +
                    ", adep." +
                     Const.ADRES_NAME + " as departure, adest." + Const.ADRES_NAME + " as destination, " + Const.DATE_DEPARTURE + ", " +
                     Const.DATE_DESTINATION + ", " + Const.FLIGHT_BUS_ID + ", " + Const.TYPE_ID + ", " + Const.TYPE_NAME + ", " + Const.SEATS_NUMBER + ", " + Const.STATUS_ID + ", " + Const.STATUS_NAME + " FROM " +
                     Const.FLIGHTS_TABLE + " INNER JOIN " +  Const.ADRESS_TABLE + " adep ON " + Const.ADRESS_DEPARTURE + "= adep." + Const.ADRESS_ID +
                    " INNER JOIN " +  Const.ADRESS_TABLE + " adest ON " + Const.ADRESS_DESTINATION + "= adest." + Const.ADRESS_ID +
                    " INNER JOIN " +  Const.STATUS_TABLE + " ON " + Const.STATUS + "=" + Const.STATUS_ID +
                    " INNER JOIN " +  Const.BUSES_TABLE + " ON " + Const.FLIGHT_BUS_ID + "=" + Const.BUS_ID +
                    " INNER JOIN " +  Const.BUS_TYPE_TABLE + " ON " + Const.BUS_TYPE_ID + "=" + Const.TYPE_ID +
                    " ORDER BY " + Const.FLIGHT_ID + " ASC");
            while(rs.next()){
                listFlights.add(new Flight(rs.getInt(Const.FLIGHT_ID), rs.getInt(Const.FLIGHT_NUMBER), rs.getInt("id_departure"),
                        rs.getString("departure"), rs.getInt("id_destination"), rs.getString("destination"),
                        rs.getDate(Const.DATE_DEPARTURE).toLocalDate(), rs.getDate(Const.DATE_DESTINATION).toLocalDate(),
                        rs.getInt(Const.FLIGHT_BUS_ID), rs.getInt(Const.SEATS_NUMBER), rs.getInt(Const.STATUS_ID), rs.getString(Const.STATUS_NAME), rs.getInt(Const.TYPE_ID), rs.getString(Const.TYPE_NAME)));
            }
            NumbFlightCol.setCellValueFactory(new PropertyValueFactory<>(Const.FLIGHT_NUMBER));
            AdressDepCol.setCellValueFactory(new PropertyValueFactory<>("departure"));
            AdressDesCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
            DateDepCol.setCellValueFactory(new PropertyValueFactory<>(Const.DATE_DEPARTURE));
            DateDesCol.setCellValueFactory(new PropertyValueFactory<>(Const.DATE_DESTINATION));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>(Const.TYPE_NAME));
            SeatsCol.setCellValueFactory(new PropertyValueFactory<>(Const.SEATS_NUMBER));
            StatusCol.setCellValueFactory(new PropertyValueFactory<>(Const.STATUS_NAME));
            FlightsTable.setItems(listFlights);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillTablePassengers() {
        ListOfPass.setText("Все пассажиры");
        passOnFlight.clear();
        listPassengers.clear();
        try {
            Connection con = dbhandler.getDbConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + Const.PASSENGERS_TABLE +
                    " ORDER BY " + Const.PASSENGER_ID + " ASC");
            while(rs.next()){
                listPassengers.add(new Passenger(rs.getInt(Const.PASSENGER_ID),
                        rs.getString(Const.PASSENGER_NAME), rs.getLong(Const.PASSENGER_TELEPHONE),
                        rs.getString(Const.PASSENGER_MAIL), rs.getLong(Const.PASSENGER_PASSPORT)));
            }
            NameCol.setCellValueFactory(new PropertyValueFactory<>(Const.PASSENGER_NAME));
            TelephoneCol.setCellValueFactory(new PropertyValueFactory<>(Const.PASSENGER_TELEPHONE));
            MailCol.setCellValueFactory(new PropertyValueFactory<>(Const.PASSENGER_MAIL));
            PassportCol.setCellValueFactory(new PropertyValueFactory<>(Const.PASSENGER_PASSPORT));
            PassengersTable.setItems(listPassengers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}