package entities;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Passenger {
    private SimpleIntegerProperty id_passenger;
    private SimpleStringProperty name_passenger;
    private SimpleLongProperty telephone;
    private SimpleStringProperty mail;
    private SimpleLongProperty passport;

    public Passenger(int id_passenger, String name_passenger, long telephone, String mail, long passport) {
        this.id_passenger = new SimpleIntegerProperty(id_passenger);
        this.name_passenger = new SimpleStringProperty(name_passenger);
        this.telephone = new SimpleLongProperty(telephone);
        this.mail = new SimpleStringProperty(mail);
        this.passport = new SimpleLongProperty(passport);
    }

    public Passenger() {
    }

    public int getId_passenger() {
        return id_passenger.get();
    }

    public SimpleIntegerProperty id_passengerProperty() {
        return id_passenger;
    }

    public void setId_passenger(int id_passenger) {
        this.id_passenger.set(id_passenger);
    }

    public String getName_passenger() {
        return name_passenger.get();
    }

    public SimpleStringProperty name_passengerProperty() {
        return name_passenger;
    }

    public void setName_passenger(String name_passenger) {
        this.name_passenger.set(name_passenger);
    }

    public long getTelephone() {
        return telephone.get();
    }

    public SimpleLongProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone.set(telephone);
    }

    public String getMail() {
        return mail.get();
    }

    public SimpleStringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public long getPassport() {
        return passport.get();
    }

    public SimpleLongProperty passportProperty() {
        return passport;
    }

    public void setPassport(long passport) {
        this.passport.set(passport);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id_passenger=" + id_passenger +
                ", name_passenger=" + name_passenger +
                ", telephone=" + telephone +
                ", mail=" + mail +
                ", passport=" + passport +
                '}';
    }
}
