package xruisu.project.mpc.controllers;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.IDGenerator;
import xruisu.project.mpc.data.ReportFormatting;
import xruisu.project.mpc.data.file.FileListener;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.system.Logger;
import xruisu.project.mpc.utility.system.MyFonts;

public class ReportController {

    private static Logger logger = new Logger();

    @FXML
    private ImageView fileSelectButton;
    @FXML
    private ImageView printButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextFlow topText;
    @FXML
    private TextFlow mainText;
    @FXML
    private TextFlow bottomText;
    @FXML
    private Pane reportPane;
    @FXML
    private TextArea disclaimerText;
    @FXML
    private TextField firstField;
    @FXML
    private TextField secondField;
    @FXML
    private TextField thirdField;

    private static StringBuilder header = new StringBuilder();

    private static boolean hasReport = false;
    private final int CONTENT_SIZE = 22;

    @FXML
    public void initialize() {

        if (!header.isEmpty()) {
            NodeHandler.disable(firstField);
            NodeHandler.disable(secondField);
            NodeHandler.disable(thirdField);

            NodeHandler.setTextFor(firstField, DataManager.getFirstField());
            NodeHandler.setTextFor(secondField, DataManager.getSecondField());
            NodeHandler.setTextFor(thirdField, DataManager.getThirdField());
        }

        NodeHandler.hide(bottomText);

        checkReportStatus();
        setHeaderStyles();
        setIcons();
        disable();
        clear();

        handle();

        setContent(MyFonts.getSystem(), CONTENT_SIZE, Color.BLACK, Color.WHITESMOKE, "");
    }

    private void setIcons() {
        fileSelectButton.setImage(new Image(
                getClass().getResource("/assets/images/REPORT-VIEWER/fileopener/folder-report-viewer256.png")
                        .toExternalForm()));
        printButton.setImage(new Image(
                getClass().getResource("/assets/images/REPORT-VIEWER/printer/printer-report-viewer256.png")
                        .toExternalForm()));
        ;

    }

    private void setHeaderStyles() {
        var headerStyle = "-fx-background-color: #1f1f1f; -fx-text-fill:"
                + "white; -fx-border-color: white; -fx-border-width: 0 0 1 0; -fx-border-radius: 30;";

        setHeadingsStyleFor(firstField, headerStyle);
        setHeadingsStyleFor(secondField, headerStyle);
        setHeadingsStyleFor(thirdField, headerStyle);
    }

    private void handle() {

        reset();

        NodeHandler.setNodeEffectOn(printButton, 1.2, 3);
        NodeHandler.setNodeEffectOn(fileSelectButton, 1.2, 3);

        var headerStyle = "-fx-background-color: #1f1f1f; -fx-text-fill:"
                + "lightgreen; -fx-border-color: lightgreen; -fx-border-width: 0 0 1 0; -fx-border-radius: 30;";

        // HEADER FIELD ONE
        firstField.setOnAction(actionEvent -> {
            setHeadingsStyleFor(firstField, headerStyle);

            var input = firstField.getText().toUpperCase();
            firstField.setText(input);
            header.append(input + "\n");
            DataManager.setFirstField(input);

            firstField.setOnKeyPressed(keyEvent -> firstField.setOpacity(0.7));
        });

        // HEADER FIELD TWO
        secondField.setOnAction(actionEvent -> {
            setHeadingsStyleFor(secondField, headerStyle);

            var input = secondField.getText().toUpperCase();
            secondField.setText(input);
            header.append(input + "\n");
            DataManager.setSecondField(input);

            secondField.setOnKeyPressed(keyEvent -> secondField.setOpacity(0.7));
        });

        // HEADER FIELD THREE
        thirdField.setOnAction(actionEvent -> {
            setHeadingsStyleFor(thirdField, headerStyle);

            var input = thirdField.getText().toUpperCase();
            thirdField.setText(input);
            header.append(input);
            DataManager.setThirdField(input);

            thirdField.setOnKeyPressed(keyEvent -> thirdField.setOpacity(0.7));
        });

        fileSelectButton.setOnMouseClicked(clickEvent -> {
            readMPCFile();
        });
        printButton.setOnMouseClicked(clickEvent -> {
            handleWarningDialog();
        });
    }

    private void handleWarningDialog() {
        if (!header.isEmpty()) {
            logger.info("Custom set headers: " + header);
        }
        checkReportStatus();
        if (hasReport) {
            printReport();
        } else {
            callWarningDialog();
        }
    }

    private void checkReportStatus() {
        if (!DataManager.getPrevious().isEmpty()) {
            hasReport = true;
        } else {
            hasReport = false;
        }
    }

    private void callWarningDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("No Available Report Found");
        alert.setHeaderText("Status: Missing Report");
        alert.setContentText(
                " No report was generated or opened❗\n Please generate/open a report before trying to print.");
        alert.showAndWait();
    }

    private void readMPCFile() {
        logger.info("Opening MPC File...");
        var listener = new FileListener(null);
        if (mainText.getChildren().size() > 0) {
            mainText.getChildren().remove(0);
        }
        listener.openMPCFile((Stage) fileSelectButton.getScene().getWindow(), mainText);
        var builder = new StringBuilder();
        mainText.getChildren().forEach(node -> {
            if (node instanceof Text) {
                builder.append(((Text) node).getText());
            }
        });

        var previousReport = builder.toString();
        DataManager.getPrevious().setLength(0);
        logger.info("Previous report cleared!");

        DataManager.getPrevious().append(previousReport);
        logger.info("Report has been updated from file!");

        NodeHandler.show(topText);
        NodeHandler.show(mainText);
    }

    private void setContent(String font, int fontSize, Color textColor, Color backgroundColor,
            String styles) {

        var currentReport = DataManager.getReport().toString();
        var previousReport = DataManager.getPrevious().toString();
        var blankContent = ReportFormatting.getEmptyContent();

        var displayText = new Text();

        if (currentReport.isEmpty() && !previousReport.isEmpty()) {
            NodeHandler.setTextFor(displayText, previousReport.concat(" REPORT ID: " + IDGenerator.getID()));
        } else if (currentReport.isEmpty() && previousReport.isEmpty()) {
            NodeHandler.setTextFor(displayText, blankContent);
        } else {
            NodeHandler.setTextFor(displayText, previousReport.concat(" REPORT ID: " + IDGenerator.getID()));
        }
        NodeHandler.setStyleOn(mainText, displayText,
                font, fontSize, textColor, backgroundColor, styles);
        NodeHandler.add(mainText, displayText);
    }

    private void setHeadingsStyleFor(TextField textField, String styles) {
        NodeHandler.setStyleOn(textField, styles);
    }

    private void disable() {
        NodeHandler.disable(topText);
        NodeHandler.disable(mainText);
        NodeHandler.disable(bottomText);
    }

    private void clear() {
        NodeHandler.clear(topText);
        NodeHandler.clear(mainText);
        NodeHandler.clear(bottomText);
    }

    private void reset() {
        if (FinalizeController.isComplete() && DataManager.getFinalized()) {
            topText.getChildren().clear();
            mainText.getChildren().clear();
            bottomText.getChildren().clear();

            logger.info("Report has been finalized, clearing data...");
        }
    }

    private void printReport() {

        NodeHandler.hide(topText);
        NodeHandler.hide(bottomText);

        var printerJob = PrinterJob.createPrinterJob();
        if (printerJob == null) {
            logger.info("No available printer was found");
            return;
        }

        var previousReport = DataManager.getPrevious().toString();

        final var BUFFER = new StringBuffer();
        BUFFER.append(ReportFormatting.getHeading()).append(header).append(previousReport).append("\n")
                .append(ReportFormatting.getFooting()).append(ReportFormatting.getDisclaimer())
                .append(ReportFormatting.getInfo());

        var reportText = new Text(BUFFER.toString());
        String[] lines = BUFFER.toString().split("\n");

        var pageLayout = printerJob.getJobSettings().getPageLayout();
        double printableWidth = pageLayout.getPrintableWidth();
        double printableHeight = pageLayout.getPrintableHeight();

        var textFlow = new TextFlow();
        NodeHandler.setStyleOn(mainText, reportText, MyFonts.getGruppo(), 25, Color.BLACK, Color.WHITESMOKE,
                "");
        textFlow.setPrefWidth(printableWidth);

        if (printerJob.showPrintDialog(reportPane.getScene().getWindow())) {
            boolean success = true;
            int currentPageStart = 0;

            while (currentPageStart < lines.length && success) {
                NodeHandler.clear(textFlow);
                double currentHeight = 0;
                int lineIndex = currentPageStart;

                while (lineIndex < lines.length) {

                    var linesGot = lines[lineIndex].trim() + "\n";
                    var line = new Text(linesGot);

                    textFlow.getChildren().add(line);
                    textFlow.applyCss();
                    textFlow.layout();

                    currentHeight = textFlow.getBoundsInParent().getHeight();

                    if (currentHeight > printableHeight) {
                        textFlow.getChildren().remove(line);
                        break;
                    }
                    lineIndex++;
                }

                success = printerJob.printPage(textFlow);
                currentPageStart = lineIndex;
            }
            logger.info("Printing...");
            if (success) {
                printerJob.endJob();
                logger.info("Printing completed successfully");
            } else {
                logger.warn("Printing failed.");
            }
        }
        if (mainText.getChildren().size() > 3) {
            mainText.getChildren().remove(1);
        }
        header.setLength(0);
        logger.info("Custom report headers have been cleared!");
    }
}
