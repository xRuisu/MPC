package xruisu.project.mpc.logic;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.logic.logical.Scannable;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.display.Broadcaster;
import xruisu.project.mpc.utility.display.DisplayUpdater;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;

public class ScansHandler extends FXMLVariables implements Scannable {

    private static Logger logger = new Logger();

    private Broadcaster broadcaster;
    private DisplayUpdater displayUpdater;

    @Override
    public void calculateScans() {

        onEnable();

        scanListPane.getChildren().stream().forEach(element -> {
            element.setOnKeyTyped(keyEvent -> handleScans());
            element.setOnKeyPressed(keyEvent -> handleScans());
            element.setOnKeyReleased(keyEvent -> {
                handleScans();
                displayUpdater.updateScansDisplay();
            });
        });
    }

    private void handleScans() {
        var totalScans = 0;
        var userScans = 0;
        var average = 0.0;
        var rate = 0.0;
        if (DataManager.getRate().size() < rateLabels.size()) {
            for (int i = 0; i < DataManager.getEmp().size(); i++) {
                var scanText = scanFields.get(i).getText().strip();
                if (!scanText.isBlank()) {
                    try {

                        userScans = +Integer.parseInt(scanText);
                        rate = +userScans / DataManager.getDuration();
                        totalScans += Integer.parseInt(scanText);
                        average = +Math.round(
                                DataManager.getVolume()
                                        / (DataManager.getDuration() * DataManager.getEmployeeTotal()));

                        averageValue.setText(Double.toString(average));

                    } catch (NumberFormatException e) {
                        broadcaster.broadcast("Invalid format!\nOnly numbers are allowed here!",
                                Color.RED);
                        logger.warn(ExceptionHandling.getInvalidInput());
                    }
                }
                valueLabels.get(i).setText(Double.toString(userScans));
                rateLabels.get(i).setText(Double.toString(rate));
            }
        }

        if (!rateLabels.get(LogicHandler.getIndex()).getText().isBlank()) {
            DataManager.setAverage(Double.parseDouble(averageValue.getText()));
        }

        if (DataManager.getExplicitVolume() == 0) {
            DataManager.setVolume((double) totalScans);
        } else {
            DataManager.setVolume(DataManager.getExplicitVolume());
        }

    }

    public void assignNameToScanLabel() {

        onEnable();

        if (employeeFields.size() != scanLabels.size()) {
            logger.warn("Mismatch in the filtered number of employee fields and scan labels.");
        }

        var employeeTextField = employeeFields.get(LogicHandler.getIndex());
        var scansLabel = scanLabels.get(LogicHandler.getIndex());
        var employeeName = employeeTextField.getText().toUpperCase().strip();

        if (employeeTextField.getLength() <= 15) {
            NodeHandler.setTextFor(scansLabel, employeeName);
        } else {
            broadcaster.broadcast("Character limit exceeds 15!\n Please try again.", Color.RED);
            NodeHandler.setTextFor(employeeTextField, "");
            logger.warn(ExceptionHandling.getCharacterLimit());
        }
    }

    private void onEnable() {

        scanFields = scanListPane.getChildren().stream().filter(node -> node instanceof TextField)
                .map(node -> (TextField) node).toList();

        scanLabels = scanListPane.getChildren().stream().filter(node -> node instanceof Label)
                .map(node -> (Label) node).toList();

        rateLabels = employeeListPane.getChildren().stream().filter(node -> node instanceof Label)
                .map(node -> (Label) node).toList();

        valueLabels = valuesListPane.getChildren().stream().filter(node -> node instanceof Label)
                .map(node -> (Label) node).toList();

        employeeFields = employeeListPane.getChildren().stream().filter(node -> node instanceof TextField)
                .map(node -> (TextField) node).toList();
    }

    public ScansHandler(Label broadcast, List<Label> valueLabels, List<TextField> scanFields,
            List<Label> rateLabels, List<TextField> employeeFields, FlowPane employeeListPane, FlowPane scanListPane,
            FlowPane valuesListPane, Label averageValue, TextField durationInput, TextFlow textFlowField) {

        this.broadcast = broadcast;
        this.scanFields = scanFields;
        this.valueLabels = valueLabels;
        this.employeeFields = employeeFields;
        this.valuesListPane = valuesListPane;
        this.scanListPane = scanListPane;
        this.employeeListPane = employeeListPane;
        this.averageValue = averageValue;
        this.rateLabels = rateLabels;
        this.durationInput = durationInput;
        this.textFlowField = textFlowField;

        broadcaster = new Broadcaster(broadcast);
        displayUpdater = new DisplayUpdater(averageValue, averageValue, broadcast, textFlowField, employeeListPane,
                scanListPane, durationInput, averageValue, scanFields, employeeFields, rateLabels);

    }
}
