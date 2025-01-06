package xruisu.project.mpc.logic;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.logic.logical.Countable;

public class EmployeeCountHandler extends FXMLVariables implements Countable {

    public EmployeeCountHandler(FlowPane employeeListPane) {
        this.employeeListPane = employeeListPane;
    }

    @Override
    public void calculateEmployeeCount() {
        var count = 0;
        for (Node node : employeeListPane.getChildren()) {
            if (node instanceof TextField textField && !textField.getText().isBlank()) {
                count++;
            }
        }
        if (DataManager.getExplicitEmployeeTotal() != 0) {
            DataManager.setEmployeeTotal(DataManager.getExplicitEmployeeTotal());
        } else {
            DataManager.setEmployeeTotal(count);
        }
    }

}
