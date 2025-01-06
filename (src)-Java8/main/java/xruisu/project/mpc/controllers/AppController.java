package xruisu.project.mpc.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xruisu.project.mpc.MPC;
import xruisu.project.mpc.app.App;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.logic.DurationHandler;
import xruisu.project.mpc.logic.LogicHandler;
import xruisu.project.mpc.utility.display.Broadcaster;
import xruisu.project.mpc.utility.display.DisplayManager;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;

public class AppController extends FXMLVariables {

        private static Logger logger = new Logger();

        private DurationHandler calcTimeToDuration;
        private LogicHandler logicHandler;
        private Broadcaster broadcaster;
        private DisplayManager display;

        private App mpc;

        @FXML
        public void initialize() {
                logger.info("Initializing controller...");

                mainPane.setOnMouseMoved(mouseEvent -> {
                        mpc.setNotification();
                        if (PropertiesController.hasProps() || DataManager.isHandicapped()) {
                                exValues.setImage(new Image(
                                                getClass().getResource(
                                                                "/assets/images/PROPS/notification/notify-properties256.png")
                                                                .toExternalForm()));
                        }
                });

                onEnable();
                mpc.handleEvents();
        }

        public void showReportViewer() {
                try {
                        FXMLLoader fxmlLoader = new FXMLLoader(MPC.class.getResource("/report" + ".fxml"));
                        Parent root = fxmlLoader.load();

                        Stage stage = new Stage();
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.setScene(new Scene(root));
                        stage.getIcons().addAll(
                                        new Image(getClass()
                                                        .getResource("/assets/images/REPORT-VIEWER/report-viewer-icon256.png")
                                                        .toExternalForm()),
                                        new Image(getClass()
                                                        .getResource("/assets/images/REPORT-VIEWER/report-viewer-icon48.png")
                                                        .toExternalForm()),
                                        new Image(getClass()
                                                        .getResource("/assets/images/REPORT-VIEWER/report-viewer-icon32.png")
                                                        .toExternalForm()),
                                        new Image(getClass()
                                                        .getResource("/assets/images/REPORT-VIEWER/report-viewer-icon16.png")
                                                        .toExternalForm()));

                        stage.setOnCloseRequest(closeEvent -> {
                                DataManager.getPrevious().setLength(0);
                                DataManager.getReport().setLength(0);
                                logger.info("Report Viewer closed after viewing, clearing old metric data...");
                                logger.info("Updating finalization status...");
                                DataManager.setFinalized(true);
                                logger.info("Finalization status has been updated.");
                        });
                        stage.setTitle("MPC Report Viewer");
                        stage.setResizable(false);
                        stage.showAndWait();
                } catch (Exception e) {
                        logger.warn(ExceptionHandling.critical());
                        logger.warn(e.toString());
                        e.printStackTrace();
                }
        }

        private void setDefaultProperties() {
                logger.info("Applying application properties...");

                mpc.initialize();

                mpc.setDateLabel();

                display.importFonts();

                display.addOnStart();

                broadcaster.broadcast("Enter a valid start-end time.", Color.LIGHTGREEN);
        }

        public void showMetricProperties() {
                try {
                        FXMLLoader fxmlLoader = new FXMLLoader(MPC.class.getResource("/properties" + ".fxml"));
                        Parent root = fxmlLoader.load();

                        Stage stage = new Stage();
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.setScene(new Scene(root));
                        stage.getIcons().addAll(
                                        new Image(getClass()
                                                        .getResource("/assets/images/PROPS/properties-icon256.png")
                                                        .toExternalForm()),
                                        new Image(getClass()
                                                        .getResource("/assets/images/PROPS/properties-icon48.png")
                                                        .toExternalForm()),
                                        new Image(getClass()
                                                        .getResource("/assets/images/PROPS/properties-icon32.png")
                                                        .toExternalForm()),
                                        new Image(getClass()
                                                        .getResource("/assets/images/PROPS/properties-icon16.png")
                                                        .toExternalForm()));
                        stage.setTitle("Metric Properties");
                        stage.setResizable(false);
                        stage.showAndWait();
                } catch (Exception e) {
                        logger.warn(ExceptionHandling.critical());
                        logger.warn(e.toString());
                        e.printStackTrace();
                }
        }

        private void onEnable() {

                display = new DisplayManager(textFlowField, durationValue, durationValue);
                broadcaster = new Broadcaster(broadcast);
                calcTimeToDuration = new DurationHandler(broadcast, textFlowField, title, subTitle, scansTab,
                                employeeTab, employeeListPane, scanListPane, resetButton, durationInput, durationValue,
                                scanFields, employeeFields, scanLabels, rateLabels, averageValue, tabPane);
                logicHandler = new LogicHandler(broadcast, textFlowField, title, subTitle, employeeListPane,
                                scanListPane, averageValue, resetButton, durationInput, durationValue, scanFields,
                                employeeFields, scanLabels, rateLabels, valuesListPane, valueLabels, timeDateLabel,
                                reportView, exValues, rightAverageText, rightDurationText, leftDurationLabel, scansTab,
                                tabPane, employeeTab);
                mpc = new App(broadcast, textFlowField, title, subTitle, employeeListPane, scanListPane, averageValue,
                                resetButton, durationInput, durationValue,
                                scanFields, employeeFields, scanLabels, rateLabels, valuesListPane,
                                valueLabels, timeDateLabel, reportView, exValues, rightAverageText, rightDurationText,
                                leftDurationLabel, scansTab, employeeTab);

                setDefaultProperties();
                calcTimeToDuration.calculateDurationToMinutes();
                logicHandler.init();

                logger.info("Application started successfully!");
        }
}