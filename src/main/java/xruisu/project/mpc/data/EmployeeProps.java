package xruisu.project.mpc.data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeProps {

    private final SimpleStringProperty name;
    private final SimpleDoubleProperty handicap;

    public EmployeeProps(String name, double handicap) {
        this.name = new SimpleStringProperty(name);
        this.handicap = new SimpleDoubleProperty(handicap);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getHandicap() {
        return handicap.get();
    }

    public void setHandicap(double handicap) {
        this.handicap.set(handicap);
    }
}