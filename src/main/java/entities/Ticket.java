package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Ticket {
    private SimpleIntegerProperty id_ticket;
    private SimpleIntegerProperty passenger_id;
    private SimpleIntegerProperty place_number;
    private SimpleIntegerProperty price;
    private SimpleStringProperty date_booking;
    private SimpleIntegerProperty flight_id;

    public Ticket(int id_ticket, int passenger_id, int place_number, int price, LocalDate date_booking, int flight_id) {
        this.id_ticket = new SimpleIntegerProperty(id_ticket);
        this.passenger_id = new SimpleIntegerProperty(passenger_id);
        this.place_number = new SimpleIntegerProperty(place_number);
        this.price = new SimpleIntegerProperty(price);
        this.date_booking =new SimpleStringProperty(String.valueOf(date_booking));
        this.flight_id = new SimpleIntegerProperty(flight_id);
    }

    public Ticket() {
    }

    public int getId_ticket() {
        return id_ticket.get();
    }

    public SimpleIntegerProperty id_ticketProperty() {
        return id_ticket;
    }

    public void setId_ticket(int id_ticket) {
        this.id_ticket.set(id_ticket);
    }

    public int getPassenger_id() {
        return passenger_id.get();
    }

    public SimpleIntegerProperty passenger_idProperty() {
        return passenger_id;
    }

    public void setPassenger_id(int passenger_id) {
        this.passenger_id.set(passenger_id);
    }

    public int getPlace_number() {
        return place_number.get();
    }

    public SimpleIntegerProperty place_numberProperty() {
        return place_number;
    }

    public void setPlace_number(int place_number) {
        this.place_number.set(place_number);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public String getDate_booking() {
        return date_booking.get();
    }

    public SimpleStringProperty date_bookingProperty() {
        return date_booking;
    }

    public void setDate_booking(String date_booking) {
        this.date_booking.set(date_booking);
    }

    public int getFlight_id() {
        return flight_id.get();
    }

    public SimpleIntegerProperty flight_idProperty() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id.set(flight_id);
    }
}
