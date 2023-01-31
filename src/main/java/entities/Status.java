package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Status {
    private SimpleIntegerProperty id_status;
    private SimpleStringProperty name_status;
    public Status(int id_status, String name_status) {
        this.id_status = new SimpleIntegerProperty(id_status);
        this.name_status = new SimpleStringProperty(name_status);
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

    public String getName_status() {
        return name_status.get();
    }

    public SimpleStringProperty name_statusProperty() {
        return name_status;
    }

    public void setName_status(String name_status) {
        this.name_status.set(name_status);
    }

    @Override
    public String toString() {
        return this.getName_status();
    }
}
