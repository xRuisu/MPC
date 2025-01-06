package xruisu.project.mpc.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReportFormatting {

        public static String getHeading() {
                return "✦-------------------------✦ "
                                + "【 MPC REPORT 】"
                                + " ✦------------------------✦\n";
        }

        public static void setReportContent(String employee, double duration, double volume, String scans, double rate,
                        double esr, double icr, int index) {

                final int realIndex = index + 1;

                double handicap = DataManager.getHandicap().get(index);

                BigDecimal bd1 = new BigDecimal(rate).setScale(1, RoundingMode.HALF_UP);
                double rateRounded = bd1.doubleValue();

                BigDecimal bd2 = new BigDecimal(DataManager.getAverage()).setScale(1, RoundingMode.HALF_UP);
                double averageRounded = bd2.doubleValue();

                BigDecimal bd3 = new BigDecimal(duration).setScale(1, RoundingMode.HALF_UP);
                double durationRounded = bd3.doubleValue();

                String barLine = repeat("▰", 27 - employee.length());
                if (employee.length() <= 3) {
                        barLine = repeat("▰", 26 - employee.length());
                }
                if (employee.length() > 10) {
                        barLine = repeat("▰", 30 - employee.length());
                }
                String employeeLine = "\n |" + barLine + "【 (" + realIndex + ") " + employee + " 】" + barLine
                                + "\n |";
                String durationLine = "\n |    ➤ " + DataManager.getPropsDuration() + " " + DataManager.getDuration()
                                + " mins" + " ~ ("
                                + durationRounded + " /hrs)";
                String scansLine = "\n |    ➤ " + DataManager.getPropsScans() + " ~ (" + scans + " scans of " + volume
                                + " pieces)";
                String handicapLine = "\n |    ➤ Handicap: ~ ( -" + handicap + " scans) ";
                String rateLine = "\n |    ➤ " + DataManager.getPropsRate() + " ~ (" + rateRounded + "ppm / "
                                + averageRounded + "ppm)";
                String rpLine = "\n |    ➤ " + DataManager.getPropsRatePercent() + " ~ (" + Math.round(esr) + "%)";
                String tcrLine = "\n |    ➤ " + DataManager.getPropsContribution() + " ~ (" + Math.round(icr)
                                + "%)\n |";

                StringBuffer format = new StringBuffer();
                format.append(employeeLine).append(durationLine).append(scansLine).append(handicapLine)
                                .append(rateLine).append(rpLine).append(tcrLine);

                DataManager.getReport().append(format);
                DataManager.getPrevious().append(format);

                System.out.println("\n\n " + employee + " \n" + " DURATION: " + duration + "\n VOLUME: " + volume
                                + "\n SCANS: " + scans + "\n RATES: " + rate + "\n RATE PERCENTILE: " + esr
                                + "\n INDIVIDUAL CONTRIBUTION: " + icr + "\n\n");
        }

        public static String getEmptyContent() {
                return "\n  ▣ There was no report to generate. \n"
                                + "  ▣ To view previously saved reports, select the folder icon.\n"
                                + "  ▣ Previous or current reports can also be printed via the print button.\n";
        }

        public static void setTotalContributionFormat() {
                String barLine = repeat("▰", 55);

                String TCR = "\n\n" + barLine + "\n |    ➤ Total Contribution: "
                                + (int) DataManager.getTotalContribution()
                                + "%" + "\n" + barLine + "\n\n";
                DataManager.getReport().append(TCR);
                DataManager.getPrevious().append(TCR);
        }

        public static String getFooting() {
                String barLine = repeat("▰", 55);

                return "\n\n" + barLine + "\n"
                                + " ▣ Generated with MPC: A Metrics Application         \n"
                                + " ▣ Developed by: Louis Harris (xRuisu)               \n"
                                + " ▣ Project Repository: https://github.com/xruisu/mpc \n"
                                + barLine + "\n";
        }

        public static String getInfo() {
                String reportID = IDGenerator.getID() == null
                                ? "Report opened via file opener, report id can be located via the file name."
                                : IDGenerator.getID();
                String endOfReport = "\n\nEND OF REPORT";
                String info = "\n\n DATE: " + DataManager.getCurrentDate() + "\n TIME: "
                                + DataManager.getCurrentTime() + "\nREPORT ID: " + reportID + "\n\n";
                return info.concat(endOfReport);
        }

        public static String getDisclaimer() {

                StringBuffer buffer = new StringBuffer();

                String barLine = repeat("▰", 84);
                String title = "\nDISCLAIMER:\n";
                String disclaimer = "The calculations and metrics presented in this report are based on the data provided and the methodologies implemented within the application. While every effort has been made to ensure accuracy, these results may not fully reflect all variables or nuances of actual performance due to potential data discrepancies or limitations in the calculation process. Users are advised to review the results critically and consult additional resources or a professional if precise accuracy is required, especially in critical applications.";

                buffer.append("\n\n" + barLine).append(title)
                                .append(disclaimer).append("\n" + barLine);

                return buffer.toString();
        }

        private static String repeat(String str, int count) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < count; i++) {
                        builder.append(str);
                }
                return builder.toString();
        }
}
