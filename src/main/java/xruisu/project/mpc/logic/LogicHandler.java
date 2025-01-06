package xruisu.project.mpc.logic;

import java.util.List;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import xruisu.project.mpc.app.App;
import xruisu.project.mpc.controllers.FinalizeController;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.data.file.MPCFile;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.display.Broadcaster;
import xruisu.project.mpc.utility.display.DisplayUpdater;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;

public class LogicHandler extends FXMLVariables {

        private static final Logger logger = new Logger();

        private static boolean hasEmployees = false;
        private static boolean isFilled = false;

        private EmployeeCountHandler countHandler;
        private DisplayUpdater displayUpdater;
        private FinalizeController prompt;
        private ScansHandler scansHandler;
        private Broadcaster broadcaster;
        private Logic logic;
        private App mpc;

        private static int enterCounter = 0;

        private static int Index = 0;

        private void handleEmployeeFields() {
                try {

                        for (Node node : employeeListPane.getChildren()) {
                                if (node instanceof TextField employeeTextField) {
                                        employeeTextField.setOnKeyReleased(event -> {

                                                init();

                                                scansTab.setDisable(!isFilled());

                                                displayUpdater.updateEmployeeDisplay();
                                        });

                                        employeeTextField.setOnAction(actionEvent -> {
                                                if (!employeeTextField.getText().isEmpty()
                                                                && employeeTextField.getText()
                                                                                .matches("^[a-zA-Z0-9 ]+$")) {

                                                        scansTab.setDisable(false);
                                                        employeeTab.setDisable(true);

                                                        DataManager.getEmp().clear();
                                                        DataManager.getHandicap().clear();

                                                        tabPane.getSelectionModel().select(scansTab);
                                                        scanFields.get(0).requestFocus();

                                                        for (var i = 0; i < employeeFields.size(); i++) {
                                                                var employeeField = employeeFields.get(i);

                                                                DataManager.getEmp()
                                                                                .add(employeeField.getText().strip());
                                                                DataManager.getHandicap().add(0.0);
                                                        }
                                                        setHasEmployees(true);

                                                } else {
                                                        broadcaster.broadcast(
                                                                        "Invalid input!\nOnly letters or numbers!",
                                                                        Color.RED);
                                                        NodeHandler.setStyleOn(employeeTextField,
                                                                        "-fx-background-color: BLACK; -fx-text-fill: #00fffc; -fx-border-color: RED; "
                                                                                        + "-fx-border-radius: 5; -fx-background-radius: 5; -fx-font-family: System; -fx-font-size: 12;");
                                                }
                                        });
                                }
                        }
                } catch (Exception e) {
                        logger.warn(ExceptionHandling.critical());
                        logger.warn(e.toString());
                        e.printStackTrace();
                }
        }

        private void handleScanFields() {
                try {
                        for (Node node : scanListPane.getChildren()) {
                                if (node instanceof TextField scansTextField) {
                                        scansTextField.setOnAction(actionEvent -> {
                                                if (!scansTextField.getText().isEmpty()
                                                                && scansTextField.getText().matches("\\d+")) {
                                                        callTask();

                                                        handleEmptyFields();

                                                        prompt.showFinalizePrompt();

                                                        if (FinalizeController.isComplete()) {
                                                                logic.handleLogic();

                                                                logger.info("Creating report file");
                                                                MPCFile.create();

                                                                mpc.reset();
                                                        }
                                                } else {
                                                        broadcaster.broadcast(
                                                                        "Invalid input! \nOnly numbers are allowed here!",
                                                                        Color.RED);
                                                        NodeHandler.setStyleOn(scansTextField,
                                                                        "-fx-background-color: BLACK; -fx-text-fill: #00fffc; -fx-border-color: RED; "
                                                                                        + "-fx-border-radius: 5; -fx-background-radius: 5; -fx-font-family: System; -fx-font-size: 12;");
                                                }

                                        });
                                }
                        }
                } catch (Exception e) {
                        logger.warn(ExceptionHandling.critical());
                        logger.warn(e.toString());
                        e.printStackTrace();
                }

        }

        private void handleEmptyFields() {
                logger.info("Reformatting empty variables... \n");
                try {
                        for (int i = DataManager.getRate().size(); i < rateLabels.size(); i++) {
                                if (rateLabels.get(i).getText().isBlank()
                                                || scanFields.get(i).getText().isBlank()
                                                || employeeFields.get(i).getText().isBlank()
                                                || valueLabels.get(i).getText().isBlank()) {
                                        employeeFields.get(i).setText("");
                                        rateLabels.get(i).setText("0.0");
                                        valueLabels.get(i).setText("0");
                                        scanFields.get(i).setText("");
                                }
                        }
                } catch (Exception e) {
                        logger.warn(ExceptionHandling.critical());
                        logger.warn("Data requirements not met, please try again using appropriate values!");
                }
        }

        private void callTask() {

                Task<Void> task = new Task<>() {
                        @Override
                        protected Void call() {
                                for (int i = 1; i <= 15; i++) {
                                        try {
                                                scanListPane.getChildren().stream()
                                                                .forEach(field -> NodeHandler.setStyleOn(field,
                                                                                "-fx-background-color: BLACK; -fx-text-fill: GOLD; -fx-border-color: GOLD; -fx-font-family: Consolas; -fx-font-size: 14; -fx-font-weight: 700;"));
                                                Thread.sleep(50);

                                        } catch (InterruptedException e) {
                                                logger.warn(e.toString());
                                                e.printStackTrace();
                                        }

                                        updateProgress(i, 10);
                                }
                                return null;
                        }
                };

                task.setOnSucceeded(event -> {
                        scanListPane.getChildren().stream().forEach(field -> NodeHandler.setStyleOn(field,
                                        "-fx-background-color: BLACK; -fx-text-fill: #00fffc; -fx-border-color: #00fffc; -fx-font-family: Consolas; -fx-font-size: 14; -fx-font-weight: 700;"));
                });

                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();

        }

        public void init() {

                onEnable();

                handleEmployeeFields();
                scansHandler.assignNameToScanLabel();
                handleScanFields();
                countHandler.calculateEmployeeCount();
                scansHandler.calculateScans();
                displayUpdater.updateDisplayData();
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

        public static boolean isFilled() {
                return isFilled;
        }

        public static void setFilled(boolean isFilled) {
                LogicHandler.isFilled = isFilled;
        }

        public static int getIndex() {
                return Index;
        }

        public static void setIndex(int index) {
                Index = index;
        }

        public static boolean hasEmployees() {
                return hasEmployees;
        }

        public static void setHasEmployees(boolean hasEmployees) {
                LogicHandler.hasEmployees = hasEmployees;
        }

        public static int getEnterCounter() {
                return enterCounter;
        }

        public LogicHandler(Label broadcast, TextFlow textFlowField, Label title, Label subTitle,
                        FlowPane employeeListPane, FlowPane scanListPane, Label averageValue, Button resetButton,
                        TextField durationInput, Label durationValue, List<TextField> scanFields,
                        List<TextField> employeeFields, List<Label> scanLabels, List<Label> rateLabels,
                        FlowPane valuesListPane, List<Label> valueLabels, Label timeDateLabel, ImageView reportView,
                        ImageView exValues, Text rightAverageText, Text rightDurationText, Label leftDurationLabel,
                        Tab scansTab, TabPane tabPane, Tab employeeTab) {

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
                this.tabPane = tabPane;
                this.employeeTab = employeeTab;

                mpc = new App(broadcast, textFlowField, title, subTitle, employeeListPane,
                                scanListPane, averageValue, resetButton, durationInput, durationValue,
                                scanFields, employeeFields, scanLabels, rateLabels, valuesListPane, valueLabels,
                                timeDateLabel, reportView, exValues, rightAverageText, rightDurationText,
                                leftDurationLabel, scansTab, employeeTab);
                displayUpdater = new DisplayUpdater(title, subTitle, broadcast, textFlowField, employeeListPane,
                                scanListPane, durationInput, durationValue, scanFields, employeeFields, rateLabels);
                prompt = new FinalizeController();
                logic = new Logic(rateLabels, valueLabels, employeeListPane, valuesListPane);
                countHandler = new EmployeeCountHandler(employeeListPane);
                scansHandler = new ScansHandler(broadcast, valueLabels, scanFields, rateLabels, employeeFields,
                                employeeListPane, scanListPane, valuesListPane, averageValue, durationInput,
                                textFlowField);
                broadcaster = new Broadcaster(broadcast);
        }
}