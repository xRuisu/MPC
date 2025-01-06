package xruisu.project.mpc.data;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public abstract class FXMLVariables {
    @FXML
    public SplitPane mainPane;
    @FXML
    public FlowPane employeeListPane;
    @FXML
    public FlowPane scanListPane;
    @FXML
    public FlowPane valuesListPane;
    @FXML
    public TabPane tabPane;
    @FXML
    public TextField durationInput;
    @FXML
    public TextFlow textFlowField;
    @FXML
    public Label durationValue;
    @FXML
    public List<TextField> scanFields;
    @FXML
    public List<TextField> employeeFields;
    @FXML
    public List<Label> scanLabels;
    @FXML
    public List<Label> rateLabels;
    @FXML
    public Button resetButton;
    @FXML
    public Label averageValue;
    @FXML
    public Tab employeeTab;
    @FXML
    public Label broadcast;
    @FXML
    public Label rateValue;
    @FXML
    public Label subTitle;
    @FXML
    public Tab scansTab;
    @FXML
    public Label title;
    @FXML
    public List<Label> valueLabels;
    @FXML
    public ImageView exValues;
    @FXML
    public ImageView reportView;
    @FXML
    public Label leftDurationLabel;
    @FXML
    public Text rightDurationText;
    @FXML
    public Text rightAverageText;
    @FXML
    public Label timeDateLabel;
}
