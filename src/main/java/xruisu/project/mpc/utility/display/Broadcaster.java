package xruisu.project.mpc.utility.display;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.system.MyFonts;

public class Broadcaster extends FXMLVariables {

    public Broadcaster(Label broadcast) {
        this.broadcast = broadcast;
    }

    public String broadcast(String message, Color color) {
        NodeHandler.setStyleOn(broadcast,
                "-fx-font-family: " + MyFonts.getMplus()
                        + "; -fx-font-size: 14; -fx-border-radius: 10; -fx-background-radius: 10;");
        broadcast.setTextFill(color);

        broadcast.setText(message);
        return broadcast.getText();
    }
}
