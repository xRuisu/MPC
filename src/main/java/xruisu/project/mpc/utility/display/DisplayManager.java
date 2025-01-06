package xruisu.project.mpc.utility.display;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import xruisu.project.mpc.controllers.FinalizeController;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.system.Logger;

public class DisplayManager extends FXMLVariables {

    private static Logger logger = new Logger();

    private final Text HEADER = new Text();
    private Text text = new Text();

    public void addOnStart() {

        NodeHandler.add(textFlowField, text);

        set(false,
                "\n\n ▣ Enter a valid start-end time to get started"
                        + "\n ▣ Ensure time is in military time"
                        + "\n ▣ And your time format includes the dash",
                16, Color.web("#fdefef"), Color.BLACK,
                "-fx-font-family: Franklin Gothic;");
    }

    public void update() {

        clear();

        setHeading();
        textFlowField.getChildren().removeAll(HEADER, text);
        textFlowField.getChildren().addAll(HEADER, text);

        if (FinalizeController.isComplete()) {
            textFlowField.getChildren().removeAll(HEADER, text);
        }
    }

    public void set(boolean includeMarker, String message,
            int fontSize, Color textColor, Color backgroundColor, String styles) {
        NodeHandler.setStyleOn(textFlowField, text, message, fontSize, textColor, backgroundColor, styles);
        if (includeMarker) {
            NodeHandler.setTextFor(text, "\n ➥ ".concat(message));
        } else {
            NodeHandler.setTextFor(text, message);
        }
    }

    private void clear() {
        NodeHandler.clear(textFlowField);
    }

    private void setHeading() {
        if (DataManager.getExplicitEmployeeTotal() <= 0 || DataManager.getExplicitVolume() <= 0) {
            HEADER.setFill(Color.AZURE);
            HEADER.setFont(new Font("Consolas", 17));
            HEADER.setStroke(Color.AZURE);
        } else {
            HEADER.setFill(Color.web("#ffda0b"));
            HEADER.setFont(new Font("Consolas", 17));
            HEADER.setStroke(Color.web("#ffda0b"));
        }

        HEADER.setStrokeWidth(.3);
        HEADER.setWrappingWidth(.3);
        HEADER.setText("\n\n   𝓓 = " + DataManager.getDuration() + "   𝓥 = " + DataManager.getVolume() + "   𝓔 = "
                + DataManager.getEmployeeTotal() + "\n");
        textFlowField.getChildren().add(HEADER);
    }

    public void importFonts() {

        var fontOne = Font.loadFont(getClass().getResource("/assets/fonts/AntonFont.ttf").toExternalForm(), 15);
        var fontTwo = Font.loadFont(getClass().getResource("/assets/fonts/GruppoFont.ttf").toExternalForm(), 15);
        var fontThree = Font.loadFont(getClass().getResource("/assets/fonts/MPlusFont.ttf").toExternalForm(), 15);

        if (fontOne != null || fontTwo != fontThree) {
            logger.info(
                    "Loaded MPC Fonts: " + fontOne.getName() + ", " + fontTwo.getName() + ", " + fontThree.getName());
        } else {
            logger.warn("Failed to load fonts");
        }
    }

    public DisplayManager(TextFlow textFlowField, Label title, Label subtitle) {
        this.textFlowField = textFlowField;
        this.title = title;
        this.subTitle = subtitle;
    }
}
