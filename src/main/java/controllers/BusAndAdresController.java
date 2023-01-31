package controllers;

import java.lang.reflect.Field;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mainpackage.Const;
import mainpackage.DatabaseHandler;

public class BusAndAdresController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddAdressButton;

    @FXML
    private Button AddBusButton;

    @FXML
    private TextField AdressField;

    @FXML
    private Label AdressLabel;

    @FXML
    private TableView<Adress> AdressTable;

    @FXML
    private Label BusLabel;

    @FXML
    private TableView<Bus> BusTable;

    @FXML
    private Button ChangeAdressButton;

    @FXML
    private Button ChangeBusButton;

    @FXML
    private TableColumn<Bus, Date> DateBeginCol;

    @FXML
    private TextField DateBeginField;

    @FXML
    private TableColumn<Bus, Date> DateEndCol;

    @FXML
    private TextField DateEndField;

    @FXML
    private Button DelAdressButton;

    @FXML
    private Button DeleteBusButton;

    @FXML
    private Label ErrorLabel;

    @FXML
    private TableColumn<Adress, String> NameAdressCol;

    @FXML
    private TableColumn<Bus, Integer> NumbCol;

    @FXML
    private TextField NumbField;

    @FXML
    private TableColumn<Bus, Integer> SeatsCol;

    @FXML
    private TextField SeatsField;

    @FXML
    private ComboBox<TypeBus> TypeBox;

    @FXML
    private TableColumn<Bus, String> TypeCol;
    DatabaseHandler dbhandler = new DatabaseHandler();
    ObservableList<Bus> buslist = FXCollections.observableArrayList();
    ObservableList<Adress> adresslist = FXCollections.observableArrayList();
    ObservableList<TypeBus> typelist = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        BusTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        AdressTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fillTablesAndBox();
        BusTable.setOnMouseClicked(mouseEvent -> {
            Bus selectedBus = (Bus) BusTable.getSelectionModel().getSelectedItem();
            NumbField.setText(selectedBus.getBus_number());
            DateBeginField.setText(selectedBus.getDate__begin());
            DateEndField.setText(selectedBus.getDate__end());
            SeatsField.setText(String.valueOf(selectedBus.getSeats_number()));
            TypeBox.getSelectionModel().select(selectedBus.getType_id()-1);// списке нумерация с 0
        });
        AdressTable.setOnMouseClicked(mouseEvent -> {
            Adress adress = (Adress) AdressTable.getSelectionModel().getSelectedItem();
            AdressField.setText(adress.getName_adress());
        });
        AddBusButton.setOnAction(actionEvent -> {
            if (NumbField.getText() != "" && DateBeginField.getText() != "" && DateEndField.getText() != "" &&
                    isParsable(SeatsField) && TypeBox.getSelectionModel().getSelectedItem()!=null)
                addBus(); else ErrorLabel.setText("Не все данные заполнены");
        });
        ChangeBusButton.setOnAction(actionEvent -> {
            Bus selectedBus = (Bus) BusTable.getSelectionModel().getSelectedItem();
            if (NumbField.getText() != "" && DateBeginField.getText() != "" && DateEndField.getText() != "" &&
                    isParsable(SeatsField) && TypeBox.getSelectionModel().getSelectedItem()!=null && selectedBus!=null)
                changeBus(selectedBus); else ErrorLabel.setText("Не все данные заполнены");
        });
        AddAdressButton.setOnAction(actionEvent -> {
            if (AdressField.getText() != "") addAdress(); else ErrorLabel.setText("Не все данные введены");
        });
        ChangeAdressButton.setOnAction(actionEvent -> {
            Adress selectedAdress = (Adress) AdressTable.getSelectionModel().getSelectedItem();
            if (AdressField.getText() != "" && selectedAdress != null) changeAdress(selectedAdress);
            else ErrorLabel.setText("Не все данные введены");
        });
        DeleteBusButton.setOnAction(actionEvent -> {
            Bus selectedBus = (Bus) BusTable.getSelectionModel().getSelectedItem();
            if (selectedBus != null) deleteBus(selectedBus); else ErrorLabel.setText("Не выбрано");
        });
        DelAdressButton.setOnAction(actionEvent -> {
            Adress selectedAdress = (Adress) AdressTable.getSelectionModel().getSelectedItem();
            if (selectedAdress != null) deleteAdress(selectedAdress); else ErrorLabel.setText("Не выбрано");
        });
    }

    private void changeAdress(Adress adress) {
        ErrorLabel.setText(null);
        int id_adress = adress.getId_adress();
        String name_adress = AdressField.getText();
        adress.setName_adress(name_adress);
        try {
            Connection con = dbhandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("UPDATE " + Const.ADRESS_TABLE + " SET " +
                    Const.ADRES_NAME + " = '" + name_adress + "' " + " WHERE " + Const.ADRESS_ID + " = " + id_adress);
            prst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAdress() {
        ErrorLabel.setText(null);
        int id_adress = 0;
        String name_adress = AdressField.getText();
        try {
            Connection con = dbhandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("INSERT INTO " + Const.ADRESS_TABLE + " (" + Const.ADRES_NAME + ") VALUES ('" +
                    name_adress + "')");
            prst.execute();
            ResultSet rs = con.createStatement().executeQuery("SELECT " + Const.ADRESS_ID + " FROM " + Const.ADRESS_TABLE + " WHERE ROWNUM=1 ORDER BY " + Const.ADRESS_ID + " DESC");
            while (rs.next())
                id_adress = rs.getInt(Const.ADRESS_ID);
            Adress adress = new Adress(id_adress, name_adress);
            adresslist.add(adress);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteAdress(Adress adress) {
        ErrorLabel.setText(null);
        try {
            Connection con = dbhandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("DELETE FROM " + Const.ADRESS_TABLE +
                    " WHERE " + Const.ADRESS_ID + " = ?");
            prst.setString(1, String.valueOf(adress.getId_adress()));
            prst.execute();
            adresslist.remove(adress);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteBus(Bus bus) {
        ErrorLabel.setText(null);
        try {
            Connection con = dbhandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("DELETE FROM " + Const.BUSES_TABLE +
                    " WHERE " + Const.BUS_ID + " = ?");
            prst.setString(1, String.valueOf(bus.getId_bus()));
            prst.execute();
            buslist.remove(bus);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeBus(Bus bus) {
        ErrorLabel.setText(null);
        int id_bus = bus.getId_bus();
        String bus_number = NumbField.getText();
        String date__begin = DateBeginField.getText();
        String date__end = DateEndField.getText();
        int seats_number = Integer.parseInt(SeatsField.getText());
        int type_id = typelist.get(TypeBox.getSelectionModel().getSelectedIndex()).getId_type();
        String name_type = typelist.get(TypeBox.getSelectionModel().getSelectedIndex()).getName_type();
        bus.setBus_number(bus_number);
        bus.setDate__begin(date__begin);
        bus.setDate__end(date__end);
        bus.setSeats_number(seats_number);
        bus.setType_id(type_id);
        bus.setName_type(name_type);
        try {
            Connection con = dbhandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("UPDATE " + Const.BUSES_TABLE + " SET " +
                    Const.BUS_NUMBER + " = '" + bus_number  + "', " +
                    Const.DATE_BEGIN + " = TO_DATE('" + date__begin + "', 'yyyy-mm-dd'), " +
                    Const.DATE_END + " = TO_DATE('" + date__end + "', 'yyyy-mm-dd'), " +
                    Const.SEATS_NUMBER + " = " + seats_number + ", " +
                    Const.BUS_TYPE_ID + " = " + type_id +
                    " WHERE " + Const.BUS_ID + " = " + id_bus);
            prst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addBus() {
        ErrorLabel.setText(null);
        int id_bus = 0;
        String bus_number = NumbField.getText();
        String date__begin = DateBeginField.getText();
        String date__end = DateEndField.getText();
        int seats_number = Integer.parseInt(SeatsField.getText());
        int type_id = typelist.get(TypeBox.getSelectionModel().getSelectedIndex()).getId_type();
        String name_type = typelist.get(TypeBox.getSelectionModel().getSelectedIndex()).getName_type();
        try {
            Connection con = dbhandler.getDbConnection();
            PreparedStatement prst = con.prepareStatement("INSERT INTO " + Const.BUSES_TABLE + " (" + Const.BUS_NUMBER + ", " +
                    Const.DATE_BEGIN +  ", " + Const.DATE_END +  ", " + Const.SEATS_NUMBER + ", " + Const.BUS_TYPE_ID + ") VALUES ('" +
                    bus_number  + "', TO_DATE('" + date__begin + "', 'yyyy-mm-dd'), TO_DATE('" +
                    date__end + "', 'yyyy-mm-dd'), " + seats_number + ", " + type_id + ")");
            prst.execute();
            ResultSet rs = con.createStatement().executeQuery("SELECT " + Const.BUS_ID + " FROM " + Const.BUSES_TABLE + " WHERE ROWNUM=1 ORDER BY " + Const.BUS_ID + " DESC");
            while (rs.next())
                id_bus = rs.getInt(Const.BUS_ID);
            LocalDate date__Begin = LocalDate.parse(date__begin);
            LocalDate date__End = LocalDate.parse(date__end);
            Bus bus = new Bus(id_bus, bus_number, date__Begin, date__End, seats_number, type_id, name_type);
            buslist.add(bus);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isParsable(TextField textField){//в номере автобуса и числе мест только цифры
        long var = 0;
        try {
            var = Long.parseLong(textField.getText());
            if (var > 0) return true;
        }
        catch (NumberFormatException e){
            return false;
        }
        return false;//default
    }
    private void fillTablesAndBox() {
        try {
            Connection con1 = dbhandler.getDbConnection();
            ResultSet rs1 = con1.createStatement().executeQuery("SELECT * FROM " + Const.BUSES_TABLE + " INNER JOIN " +
                    Const.BUS_TYPE_TABLE + " ON " + Const.BUS_TYPE_ID + "=" + Const.TYPE_ID + " ORDER BY " + Const.BUS_ID);
            while (rs1.next()){
                buslist.add(new Bus(rs1.getInt(Const.BUS_ID), rs1.getString(Const.BUS_NUMBER), rs1.getDate(Const.DATE_BEGIN).toLocalDate(),
                        rs1.getDate(Const.DATE_END).toLocalDate(), rs1.getInt(Const.SEATS_NUMBER), rs1.getInt(Const.TYPE_ID), rs1.getString(Const.TYPE_NAME)));
            }
            Connection con2 = dbhandler.getDbConnection();
            ResultSet rs2 = con2.createStatement().executeQuery("SELECT * FROM " + Const.ADRESS_TABLE + " ORDER BY " + Const.ADRESS_ID);
            while (rs2.next()){
                adresslist.add(new Adress(rs2.getInt(Const.ADRESS_ID), rs2.getString(Const.ADRES_NAME)));
            }
            Connection con3 = dbhandler.getDbConnection();
            ResultSet rs3 = con3.createStatement().executeQuery("SELECT * FROM " + Const.BUS_TYPE_TABLE + " ORDER BY " + Const.TYPE_ID);
            while (rs3.next()){
                typelist.add(new TypeBus(rs3.getInt(Const.TYPE_ID), rs3.getString(Const.TYPE_NAME)));
            }
            TypeBox.setItems(typelist);
            NumbCol.setCellValueFactory(new PropertyValueFactory<>(Const.BUS_NUMBER));
            DateBeginCol.setCellValueFactory(new PropertyValueFactory<>(Const.DATE_BEGIN));
            DateEndCol.setCellValueFactory(new PropertyValueFactory<>(Const.DATE_END));
            SeatsCol.setCellValueFactory(new PropertyValueFactory<>(Const.SEATS_NUMBER));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>(Const.TYPE_NAME));
            NameAdressCol.setCellValueFactory(new PropertyValueFactory<>(Const.ADRES_NAME));
            BusTable.setItems(buslist);
            AdressTable.setItems(adresslist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
