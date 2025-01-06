package xruisu.project.mpc.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.EmployeeProps;
import xruisu.project.mpc.logic.LogicHandler;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;

public class PropertiesController {

    private static Logger logger = new Logger();
    private static Integer propsIndex = 0;

    @FXML
    private TableColumn<EmployeeProps, String> employeeColumn;
    @FXML
    private TableColumn<EmployeeProps, Double> handicapColumn;
    @FXML
    private TableView<EmployeeProps> tableView;
    @FXML
    private TextField volumeField;
    @FXML
    private TextField employeeField;
    @FXML
    private TextField propsDuration;
    @FXML
    private TextField propsScans;
    @FXML
    private TextField propsRate;
    @FXML
    private TextField propsRatePercent;
    @FXML
    private TextField propsContribution;
    @FXML
    private Button closeButton;

    private static boolean hasProps = false;

    @FXML
    private void initialize() {
        logger.info("Opening properties...");

        NodeHandler.setEditable(tableView, true);

        clearFields();
        handleProps();
        setFields();

        // TOTAL VOLUME TEXTFIELD

        volumeField.setOnAction(actionEvent -> {
            try {
                if (!volumeField.getText().isEmpty()) {
                    DataManager.setExplicitVolume(Double.parseDouble(volumeField.getText()));
                    NodeHandler.setStyleOn(volumeField, "-fx-background-color: BLACK; -fx-text-fill: #ffda0b");
                    setHasProps(true);
                }
                DataManager.setVolume(DataManager.getExplicitVolume());
                logger.warn("Volume data value has been explicitly set!");
            } catch (NumberFormatException e) {
                setHasProps(false);
                logger.warn(ExceptionHandling.getInvalidInput());
                NodeHandler.setStyleOn(volumeField, "-fx-background-color: RED; -fx-text-fill: BLACK");
            }
        });

        // EMPLOYEE TOTAL COUNT TEXTFIELD

        employeeField.setOnAction(actionEvent -> {
            try {
                if (!employeeField.getText().isEmpty()) {
                    DataManager.setExplicitEmployeeTotal(Integer.parseInt(employeeField.getText()));
                    NodeHandler.setStyleOn(employeeField, "-fx-background-color: BLACK; -fx-text-fill: #ffda0b");
                    setHasProps(true);
                }
                DataManager.setEmployeeTotal(DataManager.getExplicitEmployeeTotal());
                logger.warn("Employee count data value has been explicitly set!");
            } catch (NumberFormatException e) {
                setHasProps(false);
                logger.warn(ExceptionHandling.getInvalidInput());
                NodeHandler.setStyleOn(employeeField, "-fx-background-color: RED; -fx-text-fill: BLACK");
            }
        });

        // DATA VALUES TITLE PROPERTIES

        // DURATION
        propsDuration.setOnAction(actionEvent -> {
            if (propsDuration.getText().matches("[a-zA-Z\\s]+")) {
                DataManager.setPropsDuration(propsDuration.getText());
                NodeHandler.setStyleOn(propsDuration, "-fx-background-color: BLACK; -fx-text-fill: #ffda0b");
                logger.warn("Duration data title has been explicitly set!");
                setHasProps(true);
            } else {
                setHasProps(false);
                logger.warn("For titles numbers are not allowed, please use pure text!");
                NodeHandler.setStyleOn(propsDuration, "-fx-background-color: RED; -fx-text-fill: BLACK");
            }
            propsDuration.setText(propsDuration.getText());
        });

        // SCANS
        propsScans.setOnAction(actionEvent -> {
            if (propsScans.getText().matches("[a-zA-Z\\s]+")) {
                DataManager.setPropsScans(propsScans.getText());
                NodeHandler.setStyleOn(propsScans, "-fx-background-color: BLACK; -fx-text-fill: #ffda0b");
                logger.warn("Scans data title has been explicitly set!");
                setHasProps(true);
            } else {
                setHasProps(false);
                logger.warn("For titles numbers are not allowed, please use pure text!");
                NodeHandler.setStyleOn(propsScans, "-fx-background-color: RED; -fx-text-fill: BLACK");
            }
            propsScans.setText(propsScans.getText());
        });

        // RATE
        propsRate.setOnAction(actionEvent -> {
            if (propsRate.getText().matches("[a-zA-Z\\s]+")) {
                DataManager.setPropsRate(propsRate.getText());
                NodeHandler.setStyleOn(propsRate, "-fx-background-color: BLACK; -fx-text-fill: #ffda0b");
                logger.warn("Rate data title has been explicitly set!");
                setHasProps(true);
            } else {
                setHasProps(false);
                logger.warn("For titles numbers are not allowed, please use pure text!");
                NodeHandler.setStyleOn(propsRate, "-fx-background-color: RED; -fx-text-fill: BLACK");
            }
            propsRate.setText(propsRate.getText());
        });

        // RATE PERCENTAGE
        propsRatePercent.setOnAction(actionEvent -> {
            if (propsRatePercent.getText().matches("[a-zA-Z\\s]+")) {
                DataManager.setPropsRatePercent(propsRatePercent.getText());
                NodeHandler.setStyleOn(propsRatePercent, "-fx-background-color: BLACK; -fx-text-fill: #ffda0b");
                logger.warn("Rate Percentage data title has been explicitly set!");
                setHasProps(true);
            } else {
                setHasProps(false);
                logger.warn("For titles numbers are not allowed, please use pure text!");
                NodeHandler.setStyleOn(propsRatePercent, "-fx-background-color: RED; -fx-text-fill: BLACK");
            }
            propsRatePercent.setText(propsRatePercent.getText());
        });

        // CONTRIBUTION
        propsContribution.setOnAction(actionEvent -> {
            if (propsContribution.getText().matches("[a-zA-Z\\s]+")) {
                DataManager.setPropsContribution(propsContribution.getText());
                NodeHandler.setStyleOn(propsContribution, "-fx-background-color: BLACK; -fx-text-fill: #ffda0b");
                logger.warn("Contribution data title has been explicitly set!");
                setHasProps(true);
            } else {
                setHasProps(false);
                logger.warn("For titles numbers are not allowed, please use pure text!");
                NodeHandler.setStyleOn(propsContribution, "-fx-background-color: RED;-fx-text-fill: BLACK");
            }
            propsContribution.setText(propsContribution.getText());
        });

        // CLOSE BUTTON PROPERTIES
        closeButton.setOnMouseEntered(mouseEvent -> NodeHandler.setStyleOn(closeButton,
                "-fx-background-color: BLACK; -fx-border-color: AZURE; -fx-text-fill: LIGHTGREEN;"));
        closeButton.setOnMouseExited(mouseEvent -> NodeHandler.setStyleOn(closeButton,
                "-fx-background-color: BLACK; -fx-border-color: AZURE; -fx-text-fill: #ffda0b;"));
        closeButton.setOnAction(buttonEvent -> closeProperties());
        handleTable();
    }

    private void setFields() {
        NodeHandler.setTextFor(employeeField, Integer.toString(DataManager.getExplicitEmployeeTotal()));
        NodeHandler.setTextFor(volumeField, Double.toString(DataManager.getExplicitVolume()));
        NodeHandler.setTextFor(propsContribution, DataManager.getPropsContribution());
        NodeHandler.setTextFor(propsRatePercent, DataManager.getPropsRatePercent());
        NodeHandler.setTextFor(propsDuration, DataManager.getPropsDuration());
        NodeHandler.setTextFor(propsScans, DataManager.getPropsScans());
        NodeHandler.setTextFor(propsRate, DataManager.getPropsRate());
    }

    private void clearFields() {
        propsContribution.clear();
        propsRatePercent.clear();
        employeeField.clear();
        propsDuration.clear();
        volumeField.clear();
        propsScans.clear();
        propsRate.clear();
    }

    private void handleProps() {
        employeeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        handicapColumn
                .setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getHandicap()).asObject());
        handicapColumn.setCellFactory(TextFieldTableCell.forTableColumn(new SafeDoubleStringConverter()));
        handicapColumn.setOnEditCommit(commitEvent -> {
            EmployeeProps employee = commitEvent.getRowValue();
            commitEvent.getTableColumn().setText("-" + commitEvent.getNewValue() * 100 + " Scans");

            employee.setHandicap(commitEvent.getNewValue());
            setPropsIndex(commitEvent.getTablePosition().getRow());

            if (LogicHandler.hasEmployees()) {
                DataManager.getHandicap().set(getPropsIndex(), commitEvent.getNewValue() * 100);
                DataManager.setHandicapped(true);
            }
            logger.info("Updated Handicap for " + employee.getName() + " : " + commitEvent.getNewValue());
        });
    }

    private void handleTable() {
        ObservableList<EmployeeProps> employees = FXCollections.observableArrayList();
        for (int i = 0; i < DataManager.getEmp().size(); i++) {
            if (employees.size() < DataManager.getEmp().size()) {
                if (!DataManager.getEmp().get(i).isBlank()) {
                    employees.add(new EmployeeProps(DataManager.getEmp().get(i), 0.0));
                }
            }
        }
        tableView.setItems(employees);
        volumeField.setText(DataManager.getExplicitVolume().toString());
    }

    private void closeProperties() {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }

    public static Integer getPropsIndex() {
        return propsIndex;
    }

    public static void setPropsIndex(Integer propsIndex) {
        PropertiesController.propsIndex = propsIndex;
    }

    public static boolean hasProps() {
        return PropertiesController.hasProps;
    }

    public static void setHasProps(boolean hasProps) {
        PropertiesController.hasProps = hasProps;
    }

    class SafeDoubleStringConverter extends DoubleStringConverter {
        @Override
        public Double fromString(String value) {
            try {
                return super.fromString(value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid input: " + value + ". Input must be a numeric value.");
                return 0.0;
            }
        }
    }

}