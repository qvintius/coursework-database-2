package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Adress {
    private SimpleIntegerProperty id_adress;
    private SimpleStringProperty name_adress;
    public Adress(int id_adress, String name_adress) {
        this.id_adress = new SimpleIntegerProperty(id_adress);
        this.name_adress = new SimpleStringProperty(name_adress);
    }

    public int getId_adress() {
        return id_adress.get();
    }

    public SimpleIntegerProperty id_adressProperty() {
        return id_adress;
    }

    public void setId_adress(int id_adress) {
        this.id_adress.set(id_adress);
    }

    public String getName_adress() {
        return name_adress.get();
    }

    public SimpleStringProperty name_adressProperty() {
        return name_adress;
    }

    public void setName_adress(String name_adress) {
        this.name_adress.set(name_adress);
    }

    @Override
    public String toString() {
        return this.getName_adress();
    }
}
