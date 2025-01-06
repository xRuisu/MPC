package xruisu.project.mpc.utility.display;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.logic.LogicHandler;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;

public class DisplayUpdater extends FXMLVariables {

    private static final Logger logger = new Logger();

    private DisplayManager display;
    private Broadcaster broadcaster;

    public void updateDisplayData() {

        onEnable();

        if (employeeFields.size() != scanLabels.size()) {
            logger.warn("Mismatch in the filtered number of employee fields and scan labels.");
        }

        for (var i = 0; i < scanLabels.size(); i++) {
            int realIndex = i + 1;
            var scansField = scanFields.get(i);
            var employeeField = employeeFields.get(i);

            handleEmployeeFocus(employeeField, i, realIndex);
            handleScansFocus(scansField, employeeField, i, realIndex);
        }
    }

    private void handleEmployeeFocus(TextField employeeField, int index, int realIndex) {
        employeeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {

                LogicHandler.setIndex(index);

                var employee = employeeField.getText().strip();
                var scans = scanFields.get(LogicHandler.getIndex()).getText().strip();

                display.set(true, "  Employee ➔ " + employee + "\n ➥   Scans ➔ " + scans,
                        14, Color.web("#00fffc"), Color.BLACK, "");

                display.update();
                broadcaster.broadcast("Employee: " + employeeField.getText(), Color.web("#00fffc"));
            }
        });
    }

    private void handleScansFocus(TextField scansField, TextField employeeField, int index, int realIndex) {
        scansField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {

                LogicHandler.setIndex(index);

                var employee = employeeField.getText().strip();
                var scans = scansField.getText().strip();

                display.set(true, "  Employee ➔ " + employee + "\n ➥   Scans ➔ " + scans,
                        14, Color.web("#00fffc"), Color.BLACK, "");

                display.update();
                broadcaster.broadcast("Scans: " + scansField.getText(), Color.web("#00fffc"));

            }
        });
    }

    public void updateEmployeeDisplay() {
        var employee = employeeFields.get(LogicHandler.getIndex()).getText().strip();
        var scans = scanFields.get(LogicHandler.getIndex()).getText().strip();

        display.set(true,
                "  Typing...  Employee ➔ " + employee + "\n ➥   Scans ➔ "
                        + scans,
                14, Color.LIGHTGRAY, Color.BLACK, "");

        broadcaster.broadcast("Employee: " + employee, Color.LIGHTGREEN);

        if (employee.length() <= 12) {
            broadcaster.broadcast("Employee: " + employee,
                    Color.LIGHTGREEN);
        } else {
            updateDisplayData();
            broadcaster.broadcast(
                    "Character limit exceeds 15!" + "\n Please try again.",
                    Color.RED);
            logger.warn(ExceptionHandling.getCharacterLimit());
        }

        display.update();
        updateDisplayData();
    }

    public void updateScansDisplay() {

        onEnable();

        var employee = employeeFields.get(LogicHandler.getIndex()).getText().strip();
        var scans = scanFields.get(LogicHandler.getIndex()).getText().strip();

        display.set(true,
                "  Typing...  Employee ➔ " + employee + "\n ➥   Scans ➔ "
                        + scans,
                14, Color.LIGHTGRAY, Color.BLACK, "");

        broadcaster.broadcast("Scans: " + scans, Color.LIGHTGREEN);

        display.update();
        updateDisplayData();
    }

    private void onEnable() {

        scanFields = scanListPane.getChildren().stream().filter(node -> node instanceof TextField)
                .map(node -> (TextField) node).toList();

        rateLabels = employeeListPane.getChildren().stream().filter(node -> node instanceof Label)
                .map(node -> (Label) node).toList();

        employeeFields = employeeListPane.getChildren().stream().filter(node -> node instanceof TextField)
                .map(node -> (TextField) node).toList();

        scanLabels = scanListPane.getChildren().stream().filter(node -> node instanceof Label)
                .map(node -> (Label) node).toList();
    }

    public DisplayUpdater(Label title, Label subTitle, Label broadcast, TextFlow textFlowField,
            FlowPane employeeListPane, FlowPane scanListPane,
            TextField durationInput, Label durationValue, List<TextField> scanFields, List<TextField> employeeFields,
            List<Label> rateLabels) {

        this.title = title;
        this.subTitle = subTitle;
        this.employeeListPane = employeeListPane;
        this.employeeFields = employeeFields;
        this.scanListPane = scanListPane;
        this.scanFields = scanFields;
        this.rateLabels = rateLabels;
        this.broadcast = broadcast;

        display = new DisplayManager(textFlowField, title, subTitle);
        broadcaster = new Broadcaster(broadcast);
    }
}
