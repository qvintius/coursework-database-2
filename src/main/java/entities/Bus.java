package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Bus {
    private SimpleIntegerProperty id_bus;
    private SimpleStringProperty bus_number;
    private SimpleStringProperty date__begin;
    private SimpleStringProperty date__end;
    private SimpleIntegerProperty seats_number;
    private SimpleIntegerProperty type_id;
    private SimpleStringProperty name_type;

    public Bus(int id_bus, String bus_number, LocalDate date__begin, LocalDate date__end, int seats_number, int type_id, String name_type) {
        this.id_bus = new SimpleIntegerProperty(id_bus);
        this.bus_number = new SimpleStringProperty(bus_number);
        this.date__begin = new SimpleStringProperty(String.valueOf(date__begin));
        this.date__end = new SimpleStringProperty(String.valueOf(date__end));
        this.seats_number = new SimpleIntegerProperty(seats_number);
        this.type_id = new SimpleIntegerProperty(type_id);
        this.name_type = new SimpleStringProperty(name_type);
    }

    public Bus() {
    }

    public int getId_bus() {
        return id_bus.get();
    }

    public SimpleIntegerProperty id_busProperty() {
        return id_bus;
    }

    public void setId_bus(int id_bus) {
        this.id_bus.set(id_bus);
    }

    public String getBus_number() {
        return bus_number.get();
    }

    public SimpleStringProperty bus_numberProperty() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number.set(bus_number);
    }

    public String getDate__begin() {
        return date__begin.get();
    }

    public SimpleStringProperty date__beginProperty() {
        return date__begin;
    }

    public void setDate__begin(String date__begin) {
        this.date__begin.set(date__begin);
    }

    public String getDate__end() {
        return date__end.get();
    }

    public SimpleStringProperty date__endProperty() {
        return date__end;
    }

    public void setDate__end(String date__end) {
        this.date__end.set(date__end);
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

    public int getType_id() {
        return type_id.get();
    }

    public SimpleIntegerProperty type_idProperty() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id.set(type_id);
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


    @Override
    public String toString() {
        return this.getBus_number() + " " + this.getSeats_number() + " " +  this.getName_type();
    }
}
