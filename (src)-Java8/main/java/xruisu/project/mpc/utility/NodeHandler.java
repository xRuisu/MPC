package xruisu.project.mpc.utility;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NodeHandler {

    public static void setEditable(TableView<?> node, boolean value) {
        node.setEditable(value);
    }

    public static void setFocusOn(Node node) {
        node.requestFocus();
    }

    public static void clear(TextFlow textFlow) {
        textFlow.getChildren().clear();
    }

    public static void clear(Label label) {
        label.setText("");
    }

    public static void clear(TextField textField) {
        textField.setText("");
    }

    public static void disable(Node node) {
        node.setDisable(true);
    }

    public static void disable(Tab node) {
        node.setDisable(true);
    }

    public static void show(Node node) {
        node.setVisible(true);
    }

    public static void hide(Node node) {
        node.setVisible(false);
    }

    // public static void setStyleOn(Object field, String styles) {
    // field.setStyle(styles);
    // }

    public static void setStyleOn(Node node, String style) {
        node.setStyle(style);
    }

    public static void setPromptTextFor(TextField node, String text) {
        node.setPromptText(text);
    }

    public static void setTextFor(TextField node, String text) {
        node.setText(text);
    }

    public static void setTextFor(Label label, String text) {
        label.setText(text);
    }

    public static void setTextFor(Text text, String message) {
        text.setText(message);
    }

    public static void add(TextFlow node, Text textNode) {
        node.getChildren().add(textNode);
    }

    public static void add(TextFlow node, int index, Text textNode) {
        node.getChildren().add(index, textNode);
    }

    public static void remove(TextFlow node, int index) {
        node.getChildren().remove(index);
    }

    public static void set(TextFlow node, int index, Text textNode) {
        if (textNode != null) {
            node.getChildren().set(index, textNode);
        }
    }

    public static void setStyleOn(Text text, String fontFamily,
            int fontSize, Color textColor, String styles) {

        text.setFont(new Font(fontFamily, fontSize));
        text.setFill(textColor);
        text.setStyle(styles);
    }

    public static void setStyleOn(TextFlow textFlow, Text textNode,
            String fontFamily, int fontSize, Color textColor, Color background,
            String styles) {

        textFlow.setBackground(new Background(new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));
        textNode.setFont(new Font(fontFamily, fontSize));
        textNode.setFill(textColor);
        textNode.setStyle(styles);
    }

    public static void setStyleOn(Label label, String fontFamily,
            int fontSize, Color textColor, Color background, String styles) {

        label.setBackground(new Background(new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));
        label.setFont(new Font(fontFamily, fontSize));
        label.setTextFill(textColor);
        label.setStyle(styles);
    }

    public static void setNodeEffectOn(Node node, double scale, int rotate) {
        node.setOnMouseEntered(rotateEvent -> {
            node.setRotate(rotate);
            node.setScaleX(scale);
            node.setScaleY(scale);
        });

        node.setOnMouseExited(rotateEvent -> {
            node.setRotate(0);
            node.setScaleX(1);
            node.setScaleY(1);
        });
    }
}
