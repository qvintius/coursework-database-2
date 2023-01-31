package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



public class TypeBus {
    private SimpleIntegerProperty id_type;
    private SimpleStringProperty name_type;
    public TypeBus(int id_type, String name_type) {
        this.id_type = new SimpleIntegerProperty(id_type);
        this.name_type = new SimpleStringProperty(name_type);
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
        return this.getName_type();
    }
}
