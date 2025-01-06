package xruisu.project.mpc.app;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import xruisu.project.mpc.controllers.AppController;
import xruisu.project.mpc.controllers.FinalizeController;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.data.IDGenerator;
import xruisu.project.mpc.data.file.LogFile;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.display.Broadcaster;
import xruisu.project.mpc.utility.display.DisplayManager;
import xruisu.project.mpc.utility.display.DisplayUpdater;
import xruisu.project.mpc.utility.system.Logger;
import xruisu.project.mpc.utility.system.MyFonts;

public class App extends FXMLVariables {

        private static Logger logger = new Logger();

        private DisplayUpdater displayUpdater;
        private Broadcaster broadcaster;
        private DisplayManager display;
        private AppController mpc;

        @FXML
        public void initialize() {

                onEnable();

                setNotification();
                exValues.setImage(new Image(
                                getClass().getResource("/assets/images/PROPS/properties-icon256.png")
                                                .toExternalForm()));

                scanLabels.stream().forEach(label -> NodeHandler.setTextFor(label, "Employee"));
                scanFields.stream().forEach(field -> field.setPromptText("Scans"));
                employeeFields.stream().forEach(field -> NodeHandler.setStyleOn(field,
                                "-fx-background-color: BLACK; -fx-text-fill: #00fffc; -fx-border-color: #00fffc; " +
                                                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-font-family: System; -fx-font-size: 12;"));
                employeeListPane.getChildren().stream().forEach(field -> NodeHandler.setStyleOn(field,
                                "-fx-background-color: BLACK; -fx-text-fill: #00fffc; -fx-font-family: System; -fx-border-radius: 30; -fx-background-radius:30; -fx-font-size: 14;"));
                scanListPane.getChildren().stream().forEach(field -> NodeHandler.setStyleOn(field,
                                "-fx-background-color: BLACK; -fx-text-fill: #00fffc; -fx-border-color: #1f1f1f; -fx-border-radius: 5; -fx-background-radius: 5; -fx-font-family: System; -fx-font-size: 12;"));
        }

        public void setNotification() {
                if (DataManager.getPrevious().isEmpty()) {
                        reportView.setImage(new Image(
                                        getClass().getResource(
                                                        "/assets/images/REPORT-VIEWER/report-viewer-icon256.png")
                                                        .toExternalForm()));
                } else {
                        reportView.setImage(new Image(
                                        getClass().getResource(
                                                        "/assets/images/REPORT-VIEWER/notification/notify-report-viewer256.png")
                                                        .toExternalForm()));

                }
        }

        public void handleEvents() {

                reportView.setOnMouseEntered(mouseEvent -> {
                        reportView.setScaleX(1.2);
                        reportView.setScaleY(1.2);
                        setNotification();
                });
                reportView.setOnMouseExited(mouseEvent -> {
                        reportView.setScaleX(1);
                        reportView.setScaleY(1);
                        setNotification();
                });

                reportView.setOnMouseClicked(clickEvent -> {
                        mpc.showReportViewer();
                });

                exValues.setOnMouseEntered(mouseEvent -> {
                        exValues.setRotate(3);
                        exValues.setScaleX(1.2);
                        exValues.setScaleY(1.2);
                });
                exValues.setOnMouseExited(mouseEvent -> {
                        exValues.setRotate(0);
                        exValues.setScaleX(1);
                        exValues.setScaleY(1);
                });
                exValues.setOnMouseClicked(clickEvent -> {
                        mpc.showMetricProperties();
                        exValues.setImage(new Image(
                                        getClass().getResource("/assets/images/PROPS/properties-icon256.png")
                                                        .toExternalForm()));
                });
                durationInput.setOnMouseEntered(mouseEvent -> {
                        leftDurationLabel.setTextFill(Color.LIGHTGREEN);
                });
                durationInput.setOnMouseExited(mouseEvent -> {
                        leftDurationLabel.setTextFill(Color.LIGHTGRAY);
                });
                rightAverageText.setOnMouseEntered(mouseEvent -> {
                        rightAverageText.setFill(Color.LIGHTGREEN);
                });
                rightAverageText.setOnMouseExited(mouseEvent -> {
                        rightAverageText.setFill(Color.WHITE);
                });
                rightDurationText.setOnMouseEntered(mouseEvent -> {
                        rightDurationText.setFill(Color.LIGHTGREEN);
                });
                rightDurationText.setOnMouseExited(mouseEvent -> {
                        rightDurationText.setFill(Color.WHITE);
                });
                resetButton.setOnMouseEntered(mouseEvent -> {
                        resetButton.setStyle("-fx-border-color: gold; -fx-text-fill:gold");
                });
                resetButton.setOnMouseExited(mouseEvent -> {
                        resetButton.setStyle("-fx-border-color: #3a3a3a; -fx-text-fill: #3a3a3a");
                });
                resetButton.setOnAction(actionEvent -> {

                        NodeHandler.disable(scansTab);

                        resetFieldsAndLabels();

                        NodeHandler.setStyleOn(resetButton, "-fx-border-color: lightgreen; -fx-text-fill: lightgreen;");
                        resetButton.setOnMouseExited(mouseEvent -> {
                                NodeHandler.setStyleOn(resetButton,
                                                "-fx-border-color: darkgray; -fx-text-fill: lightgray");
                        });
                });

        }

        private void resetLogicVariables() {
                DataManager.setExplicitEmployeeTotal(0);
                DataManager.setTotalContribution(0);
                DataManager.setExplicitVolume(0.0);
                DataManager.setHandicapped(false);
                DataManager.setEmployeeTotal(0);
                DataManager.setDuration(0.0);
                DataManager.setAverage(0.0);
                DataManager.setVolume(0.0);
        }

        private void clearLists() {
                DataManager.getHandicap().clear();
                DataManager.getRate().clear();
                DataManager.getEmp().clear();
        }

        private void resetFXML() {

                NodeHandler.setPromptTextFor(durationInput, "0000-0000");
                NodeHandler.setTextFor(durationInput, "");

                NodeHandler.setTextFor(durationValue, "000");
                NodeHandler.setTextFor(averageValue, "0.0");

                NodeHandler.setFocusOn(durationInput);
                NodeHandler.clear(textFlowField);
                NodeHandler.disable(scansTab);
        }

        private void resetFieldsAndLabels() {

                scanFields.stream().forEach(field -> {
                        NodeHandler.setPromptTextFor(field, "");
                        NodeHandler.setTextFor(field, "");
                });

                scanLabels.stream().forEach(label -> label.setText("Employee"));
                employeeFields.stream().forEach(field -> field.setText(""));
                valueLabels.stream().forEach(label -> label.setText(""));
                rateLabels.stream().forEach(label -> label.setText(""));
        }

        private void updateDisplay() {
                display.update();

                var endReport = new Text("\n   Report has been finalized!"
                                + "\n   ▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰"
                                + "\n   ▣ View current report via the report viewer"
                                + "\n   ▣ Previous reports can also be viewed/printed"
                                + "\n   ▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰"
                                + "\n\n  ➔ Date: " + DataManager.getCurrentDate()
                                + "\n  ➔ Time: " + DataManager.getCurrentTime()
                                + "\n  ➔ Report ID:  " + IDGenerator.getID());
                NodeHandler.setStyleOn(textFlowField, endReport, MyFonts.getSystem(), 16, Color.LIGHTGREEN, Color.BLACK,
                                "");

                NodeHandler.add(textFlowField, 0, endReport);
                displayUpdater.updateDisplayData();

                broadcaster.broadcast("Report finalized!", Color.LIGHTGREEN);
        }

        public void reset() {

                logger.info("Resetting all variables...");
                logger.debug("Initiating reset methods");
                logger.debug("Clearing report data");

                initialize();
                logger.debug("Clearing list variables");
                clearLists();
                logger.debug("Resetting logical variables");
                resetLogicVariables();
                logger.debug("Resetting Fields & Labels");
                resetFieldsAndLabels();
                logger.debug("Resetting FXML");
                resetFXML();
                updateDisplay();

                logger.debug("Setting completion/finalization status: current status: "
                                + FinalizeController.isComplete());
                FinalizeController.setComplete(false);
                logger.debug("Status has been updated: current status: " + FinalizeController.isComplete());

                logger.info("MPC was reset successfully!");
                logger.info("Creating/Updating the console log file");
                LogFile.create();

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

        public void setDateLabel() {

                NodeHandler.setTextFor(timeDateLabel, DataManager.getCurrentDate());

                timeDateLabel.setOnMouseEntered(mouseEvent -> {
                        NodeHandler.setTextFor(timeDateLabel, "System Date");
                        NodeHandler.setStyleOn(timeDateLabel, MyFonts.getGruppo(),
                                        14, Color.LIGHTBLUE, Color.web("#1f1f1f"),
                                        "-fx-border-color: lightblue; -fx-background-radius: 100;"
                                                        + " -fx-border-width: 1 1 1 1; -fx-border-radius: 100; -fx-font-weight: 700;");
                });

                timeDateLabel.setOnMouseExited(mouseEvent -> {
                        NodeHandler.setTextFor(timeDateLabel, DataManager.getCurrentDate());
                        NodeHandler.setStyleOn(timeDateLabel, MyFonts.getGruppo(),
                                        14, Color.AZURE, Color.web("#1f1f1f"),
                                        "-fx-border-color: white; -fx-background-radius: 100;"
                                                        + " -fx-border-width: 1 1 1 1; -fx-border-radius: 100; -fx-font-weight: 700;");
                });
                NodeHandler.setStyleOn(timeDateLabel, MyFonts.getGruppo(),
                                14, Color.AZURE, Color.web("#1f1f1f"),
                                "-fx-border-color: white; -fx-background-radius: 100;"
                                                + " -fx-border-width: 1 1 1 1; -fx-border-radius: 100; -fx-font-weight: 700;");
        }

        public App(Label broadcast, TextFlow textFlowField, Label title, Label subTitle, FlowPane employeeListPane,
                        FlowPane scanListPane,
                        Label averageValue, Button resetButton, TextField durationInput, Label durationValue,
                        List<TextField> scanFields, List<TextField> employeeFields, List<Label> scanLabels,
                        List<Label> rateLabels, FlowPane valuesListPane, List<Label> valueLabels,
                        Label timeDateLabel, ImageView reportView, ImageView exValues, Text rightAverageText,
                        Text rightDurationText,
                        Label leftDurationLabel, Tab scansTab, Tab employeeTab) {

                this.broadcast = broadcast;
                this.textFlowField = textFlowField;
                this.title = title;
                this.subTitle = subTitle;
                this.employeeListPane = employeeListPane;
                this.scanListPane = scanListPane;
                this.averageValue = averageValue;
                this.resetButton = resetButton;
                this.durationInput = durationInput;
                this.durationValue = durationValue;
                this.scanFields = scanFields;
                this.employeeFields = employeeFields;
                this.scanLabels = scanLabels;
                this.rateLabels = rateLabels;
                this.valuesListPane = valuesListPane;
                this.valueLabels = valueLabels;
                this.timeDateLabel = timeDateLabel;
                this.reportView = reportView;
                this.exValues = exValues;
                this.rightAverageText = rightAverageText;
                this.rightDurationText = rightDurationText;
                this.leftDurationLabel = leftDurationLabel;
                this.scansTab = scansTab;
                this.employeeTab = employeeTab;
                this.exValues = exValues;

                mpc = new AppController();
                displayUpdater = new DisplayUpdater(title, subTitle, broadcast, textFlowField, employeeListPane,
                                scanListPane, durationInput, durationValue, scanFields, employeeFields, scanLabels);
                display = new DisplayManager(textFlowField, title, subTitle);
                broadcaster = new Broadcaster(broadcast);
        }

}
