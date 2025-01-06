package xruisu.project.mpc;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import xruisu.project.mpc.utility.VersionUtil;

/*
 * MPC Application
 * Copyright © 2025 Louis Harris | xRuisu
 * Licensed under the GNU General Public License v3.0
 */

/*
 * JavaFX Application (MPC)
 * By Louis Harris | xRuisu (Github)
 * 
 * Assisted by ChatGPT
 * 
 * Inspired by Fedex Express
 * Project Start Date: November 1st, 2024
 *
 * This software is free to use, modify, and distribute under GPL v3 terms.
 * Please credit Louis Harris | xRuisu as the original creator.
 */

public class MPC extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/main"));

        stage.getIcons().addAll(
                new Image(getClass().getResource("/assets/images/MPC/mpc-icon16.png").toExternalForm()),
                new Image(getClass().getResource("/assets/images/MPC/mpc-icon32.png").toExternalForm()),
                new Image(getClass().getResource("/assets/images/MPC/mpc-icon48.png").toExternalForm()),
                new Image(getClass().getResource("/assets/images/MPC/mpc-icon256.png").toExternalForm()));

        stage.setTitle(
                "Metrics Performance Calculator | MPC Version: " + VersionUtil.getAppVersion());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MPC.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}