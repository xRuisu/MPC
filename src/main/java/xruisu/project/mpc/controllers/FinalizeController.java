package xruisu.project.mpc.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xruisu.project.mpc.MPC;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;

public class FinalizeController {

    private static Logger logger = new Logger();

    @FXML
    private Pane mainPane;
    @FXML
    private Label promptLabel;
    @FXML
    private Button finalizeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ProgressBar progressBar;

    private static boolean isComplete = false;
    private static boolean isCanceled = true;

    @FXML
    public void initialize() {

        setComplete(false);
        NodeHandler.hide(progressBar);
        progressBar.setProgress(0.0);

        NodeHandler.setNodeEffectOn(finalizeButton, 1.2, 0);
        NodeHandler.setNodeEffectOn(cancelButton, 1.2, 0);

        progressBar.getStylesheets().add(getClass().getResource("/application-styles.css").toExternalForm());
        finalizeButton.setOnAction(actionEvent -> {
            handleFinalize();
        });
        cancelButton.setOnAction(actionEvent -> handleCancel());
    }

    public void showFinalizePrompt() {
        try {
            var fxmlLoader = new FXMLLoader(MPC.class.getResource("/prompt" + ".fxml"));
            Parent root = fxmlLoader.load();

            var stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.getIcons().addAll(
                    new Image(getClass().getResource("/assets/images/MPC/mpc-icon16.png").toExternalForm()),
                    new Image(getClass().getResource("/assets/images/MPC/mpc-icon32.png").toExternalForm()),
                    new Image(getClass().getResource("/assets/images/MPC/mpc-icon48.png").toExternalForm()),
                    new Image(getClass().getResource("/assets/images/MPC/mpc-icon256.png").toExternalForm()));
            stage.setTitle("Generate Report?");
            stage.setResizable(false);
            stage.showAndWait();

        } catch (Exception e) {
            logger.warn(ExceptionHandling.critical());
            logger.warn(e.toString());
            e.printStackTrace();
        }
    }

    private void handleFinalize() {
        call();
    }

    private void call() {

        NodeHandler.disable(finalizeButton);
        NodeHandler.disable(cancelButton);
        NodeHandler.show(progressBar);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                logger.debug("Starting finalization sequence");
                logger.debug("Finalizing report in 20 ");
                for (int i = 1; i <= 20; i++) {
                    try {
                        logger.debug("" + i);
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        logger.warn(e.toString());
                        e.printStackTrace();
                    }
                    updateProgress(i, 10);
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(succeedEvent -> {
            NodeHandler.show(finalizeButton);
            NodeHandler.show(cancelButton);

            setComplete(true);

            PropertiesController.setHasProps(false);
            handleCancel();
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void handleCancel() {
        var stage = (Stage) progressBar.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    public static boolean isComplete() {
        return isComplete;
    }

    public static void setComplete(boolean isComplete) {
        FinalizeController.isComplete = isComplete;
    }

    public static boolean isCanceled() {
        return isCanceled;
    }

    public static void setCanceled(boolean isCanceled) {
        FinalizeController.isCanceled = isCanceled;
    }
}
