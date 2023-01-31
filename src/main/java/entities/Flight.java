package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Flight {
    private SimpleIntegerProperty id_flight;
    private SimpleIntegerProperty number_flight;
    private SimpleIntegerProperty id_departure;
    private SimpleStringProperty departure;
    private SimpleStringProperty destination;
    private SimpleIntegerProperty id_destination;
    private SimpleStringProperty date_departure;
    private SimpleStringProperty date_destination;
    private SimpleIntegerProperty bus_id;
    private SimpleIntegerProperty seats_number;
    private SimpleIntegerProperty id_status;
    private SimpleStringProperty name_status;
    private SimpleIntegerProperty id_type;
    private SimpleStringProperty name_type;

    public Flight(int id_flight, int number_flight, int id_departure, String departure, int id_destination, String destination, LocalDate date_departure, LocalDate date_destination, int bus_id, int seats_number, int id_status, String name_status, int id_type, String name_type) {
        this.id_flight = new SimpleIntegerProperty(id_flight);
        this.number_flight = new SimpleIntegerProperty(number_flight);
        this.id_departure = new SimpleIntegerProperty(id_departure);
        this.departure = new SimpleStringProperty(departure);
        this.id_destination = new SimpleIntegerProperty(id_destination);
        this.destination = new SimpleStringProperty(destination);
        this.date_departure = new SimpleStringProperty(String.valueOf(date_departure));
        this.date_destination = new SimpleStringProperty(String.valueOf(date_destination));
        this.bus_id = new SimpleIntegerProperty(bus_id);
        this.seats_number = new SimpleIntegerProperty(seats_number);
        this.id_status = new SimpleIntegerProperty(id_status);
        this.name_status = new SimpleStringProperty(name_status);
        this.id_type = new SimpleIntegerProperty(id_type);
        this.name_type = new SimpleStringProperty(name_type);
    }

    public Flight() {
    }

    public int getId_flight() {
        return id_flight.get();
    }

    public SimpleIntegerProperty id_flightProperty() {
        return id_flight;
    }

    public void setId_flight(int id_flight) {
        this.id_flight.set(id_flight);
    }

    public int getNumber_flight() {
        return number_flight.get();
    }

    public SimpleIntegerProperty number_flightProperty() {
        return number_flight;
    }

    public void setNumber_flight(int number_flight) {
        this.number_flight.set(number_flight);
    }

    public String getDeparture() {
        return departure.get();
    }

    public SimpleStringProperty departureProperty() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure.set(departure);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getDate_departure() {
        return date_departure.get();
    }

    public SimpleStringProperty date_departureProperty() {
        return date_departure;
    }

    public void setDate_departure(String date_departure) {
        this.date_departure.set(date_departure);
    }

    public String getDate_destination() {
        return date_destination.get();
    }

    public SimpleStringProperty date_destinationProperty() {
        return date_destination;
    }

    public void setDate_destination(String date_destination) {
        this.date_destination.set(date_destination);
    }

    public int getBus_id() {
        return bus_id.get();
    }

    public SimpleIntegerProperty bus_idProperty() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id.set(bus_id);
    }

    public String getName_status() {
        return name_status.get();
    }

    public SimpleStringProperty name_statusProperty() {
        return name_status;
    }

    public void setName_status(String name_status) {
        this.name_status.set(name_status);
    }

    public String getName_type() {
        return name_type.get();
    }

    public SimpleStringProperty name_typeProperty() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type.set(name_type);
    }

    public int getSeats_number() {
        return seats_number.get();
    }

    public SimpleIntegerProperty seats_numberProperty() {
        return seats_number;
    }

    public void setSeats_number(int seats_number) {
        this.seats_number.set(seats_number);
    }

    public int getId_status() {
        return id_status.get();
    }

    public SimpleIntegerProperty id_statusProperty() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status.set(id_status);
    }

    public int getId_type() {
        return id_type.get();
    }

    public SimpleIntegerProperty id_typeProperty() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type.set(id_type);
    }

    public int getId_departure() {
        return id_departure.get();
    }

    public SimpleIntegerProperty id_departureProperty() {
        return id_departure;
    }

    public void setId_departure(int id_departure) {
        this.id_departure.set(id_departure);
    }

    public int getId_destination() {
        return id_destination.get();
    }

    public SimpleIntegerProperty id_destinationProperty() {
        return id_destination;
    }

    public void setId_destination(int id_destination) {
        this.id_destination.set(id_destination);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id_flight=" + id_flight +
                ", number_flight=" + number_flight +
                ", id_departure=" + id_departure +
                ", departure=" + departure +
                ", destination=" + destination +
                ", id_destination=" + id_destination +
                ", date_departure=" + date_departure +
                ", date_destination=" + date_destination +
                ", bus_id=" + bus_id +
                ", seats_number=" + seats_number +
                ", id_status=" + id_status +
                ", name_status=" + name_status +
                ", id_type=" + id_type +
                ", name_type=" + name_type +
                '}';
    }
}
