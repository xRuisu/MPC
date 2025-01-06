package xruisu.project.mpc.data.file;

import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public interface Listener {

    public void openMPCFile(Stage stage, TextFlow mainText);

    public void createMPCFile(String data, String folderPath, String filePath);

    public void createLogFile(String data, String folderPath, String filePath);
}