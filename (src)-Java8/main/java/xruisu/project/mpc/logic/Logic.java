package xruisu.project.mpc.logic;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.FXMLVariables;
import xruisu.project.mpc.data.ReportFormatting;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;

public class Logic extends FXMLVariables {

    private static Logger logger = new Logger();

    public void handleLogic() {
        onEnable();
        try {
            double newRate = 0;
            double newAverage = 0;
            double totalContribution = 0;
            double individualsContribution = 0;

            if (DataManager.getRate().size() < rateLabels.size()) {
                for (int i = 0; i < DataManager.getEmp().size(); i++) {

                    // EMPLOYEE & DURATION (DECIMAL)
                    String employee = DataManager.getEmp().get(i).toUpperCase();
                    double durationToDecimal = (DataManager.getDuration() / 60);
                    // Example Logic: [3hrs (180 minutes) / 60 = 3 (hours)]

                    // SCANS
                    double scansToNum = Double.parseDouble(valueLabels.get(i).getText())
                            * 10 / 10;
                    double handicap = DataManager.getHandicap().get(i);

                    double handicappedScans = (scansToNum - handicap);
                    // Example Logic: [1,000 - 3.0(300) = 700 (scans)]
                    String handicapToString = Double.toString(handicappedScans);

                    // VOLUME
                    double newVolume = DataManager.getVolume()
                            - DataManager.getHandicap().get(i);
                    // Example Logic: [4,000 - 3.0 (300) = 3,700 (volume/pieces)]
                    DataManager.setVolume(newVolume);

                    // RATE
                    newRate = +handicappedScans / DataManager.getDuration();
                    double shortRate = newRate * 10.0 / 10.0;
                    // Example Logic: [3.4 (rate) / 180 (duration) = 1.8 (2 rate(rounded)]
                    DataManager.getRate().add(i, shortRate);

                    // AVERAGE
                    newAverage = +DataManager.getVolume()
                            / (DataManager.getDuration()
                                    * DataManager.getEmployeeTotal());
                    // Example Logic: [4,000 (volume) / (180 * 4 (3 hours * 4 (employees))) = 5.0
                    // (average)]
                    DataManager.setAverage(newAverage);

                    // RATE PERCENT
                    double esr = (newRate / DataManager.getAverage()) * 100;
                    // Example Logic: [(4.7 (rate) / 5 (average)) * 100 = 94%]
                    double employeeSuccessRate = esr;

                    // INDIVIDUAL CONTRIBUTION
                    individualsContribution = (scansToNum / DataManager.getVolume() * 100);
                    // Example Logic: [(1300 (scans) / 4,000 (volume/pieces)) * 100 = 32.5% of 100%]

                    // TOTAL CONTRIBUTION
                    totalContribution += individualsContribution;

                    // Reassign updated values (scans)
                    valueLabels.get(i).setText(handicapToString);

                    String scans = valueLabels.get(i).getText();
                    double rate = DataManager.getRate().get(i);

                    if (DataManager.getRate().get(i) > 0.0
                            && DataManager.getEmp().size() > 0) {
                        ReportFormatting.setReportContent(employee, durationToDecimal,
                                newVolume, scans, rate, employeeSuccessRate, individualsContribution, i);
                    }

                }
                DataManager.setTotalContribution(totalContribution);
                ReportFormatting.setTotalContributionFormat();
            }
        } catch (Exception e) {
            logger.warn(ExceptionHandling.critical());
            logger.warn(e.toString());
            e.printStackTrace();
        }
    }

    private void onEnable() {

        rateLabels = employeeListPane.getChildren().stream().filter(node -> node instanceof Label)
                .map(node -> (Label) node).collect(Collectors.toList());

        valueLabels = valuesListPane.getChildren().stream().filter(node -> node instanceof Label)
                .map(node -> (Label) node).collect(Collectors.toList());
    }

    public Logic(List<Label> rateLabels, List<Label> valueLabels, FlowPane employeeListPane, FlowPane valuesListPane) {
        this.rateLabels = rateLabels;
        this.valueLabels = valueLabels;
        this.employeeListPane = employeeListPane;
        this.valuesListPane = valuesListPane;
    }
}
