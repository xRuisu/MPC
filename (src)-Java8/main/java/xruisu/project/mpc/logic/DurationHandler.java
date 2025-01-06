package xruisu.project.mpc.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeParseException;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.display.Broadcaster;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;

public class DurationHandler extends FXMLVariables {

    private static final Logger logger = new Logger();
    private Broadcaster broadcaster;

    public void calculateDurationToMinutes() {

        durationInput.setOnAction(event -> {

            String regex = "^\\d{2}\\d{2}-\\d{2}\\d{2}$";
            if (!durationInput.getText().matches(regex)) {
                NodeHandler.setStyleOn(durationInput, "-fx-text-fill: red; -fx-border-color: red;");
                broadcaster.broadcast("Invalid format used", Color.RED);
                logger.warn(ExceptionHandling.getInvalidInput());
                return;
            }

            NodeHandler.setStyleOn(durationInput,
                    "-fx-background-radius: 30; -fx-background-color: BLACK; -fx-text-fill: #00fffc;");
            try {
                String[] times = durationInput.getText().split("-");

                String startTimeStr = times[0];
                String endTimeStr = times[1];

                int hourStart = Integer.parseInt(startTimeStr.substring(0, 2));
                int minuteStart = Integer.parseInt(startTimeStr.substring(2, 4));

                int hourEnd = Integer.parseInt(endTimeStr.substring(0, 2));
                int minuteEnd = Integer.parseInt(endTimeStr.substring(2, 4));

                if (hourEnd < hourStart || (hourEnd == hourStart && minuteEnd < minuteStart)) {
                    hourEnd += 24;
                }

                double DURATION = ((hourEnd - hourStart) * 60) - minuteStart + minuteEnd;

                if (DURATION >= 60) {
                    if (!durationValue.getText().trim().isEmpty()) {
                        employeeTab.setDisable(false);
                    } else {
                        employeeTab.setDisable(true);
                    }

                    int durationToText = (int) DURATION;
                    durationValue.setText(Integer.toString(durationToText));

                    DataManager.setDuration(DURATION);
                    DataManager.setFinalized(true);

                    if (DataManager.getVolume() != 0 && DataManager.getEmployeeTotal() != 0) {
                        double average = 0.0;
                        average = DataManager.getVolume()
                                / (DataManager.getDuration()
                                        * DataManager.getEmployeeTotal());
                        DataManager.setAverage(average);
                    }
                    if (DataManager.getAverage() != 0.0) {
                        BigDecimal bd2 = new BigDecimal(DataManager.getAverage()).setScale(1, RoundingMode.HALF_UP);
                        double averageRounded = bd2.doubleValue();
                        averageValue.setText(Double.toString(averageRounded));
                    }

                } else {
                    NodeHandler.setStyleOn(durationInput,
                            "-fx-background-radius: 30; -fx-background-color: DARKRED; -fx-text-fill: WHITE;");
                    broadcaster.broadcast("Duration is too short!", Color.RED);
                    logger.warn(ExceptionHandling.getCharacterLimit());
                }
            } catch (DateTimeParseException e) {
                broadcaster.broadcast(ExceptionHandling.getInvalidInput(), Color.RED);
                logger.warn(ExceptionHandling.getInvalidInput());
                logger.warn(ExceptionHandling.critical());
                logger.warn(e.toString());
                e.printStackTrace();
            }
            tabPane.getSelectionModel().select(employeeTab);
            employeeListPane.getChildren().get(0).requestFocus();
        });
    }

    public DurationHandler(Label broadcast, TextFlow textFlowField, Label title, Label subTitle, Tab scansTab,
            Tab employeeTab, FlowPane employeeListPane, FlowPane scanListPane, Button resetButton,
            TextField durationInput, Label durationValue, List<TextField> scanFields, List<TextField> employeeFields,
            List<Label> scanLabels, List<Label> rateLabels, Label averageValue, TabPane tabPane) {

        this.scansTab = scansTab;
        this.employeeListPane = employeeListPane;
        this.scanListPane = scanListPane;
        this.resetButton = resetButton;
        this.scanFields = scanFields;
        this.employeeFields = employeeFields;
        this.scanLabels = scanLabels;
        this.durationInput = durationInput;
        this.durationValue = durationValue;
        this.employeeTab = employeeTab;
        this.averageValue = averageValue;
        this.employeeTab = employeeTab;
        this.tabPane = tabPane;

        broadcaster = new Broadcaster(broadcast);
    }
}
