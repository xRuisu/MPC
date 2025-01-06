package xruisu.project.mpc.data;

import java.util.ArrayList;
import java.util.List;

import xruisu.project.mpc.utility.system.DateTimeManager;

public class DataManager extends DateTimeManager {

    private static StringBuffer report = new StringBuffer();
    private static StringBuffer previous = new StringBuffer();

    private static List<Double> handicap = new ArrayList<>();
    private static List<Double> rate = new ArrayList<>();
    private static List<String> emp = new ArrayList<>();

    private static Integer explicitEmployeeTotal = 0;
    private static Integer employeeTotal = 0;
    private static Integer userScans = 0;

    private static boolean isHandicapped = false;

    private static String employee;
    private static String firstField;
    private static String secondField;
    private static String thirdField;

    private static double totalContribution = 0;
    private static double explicitVolume = 0;
    private static double duration = 0;
    private static double average = 0;
    private static double volume = 0;

    private static String propsDuration = "Hour's Worked";
    private static String propsScans = "Total Scan's";
    private static String propsRate = "Rate";
    private static String propsRatePercent = "Rate Percentile";
    private static String propsContribution = "Individual's Contribution";

    public static boolean isHandicapped() {
        return isHandicapped;
    }

    public static void setHandicapped(boolean isHandicapped) {
        DataManager.isHandicapped = isHandicapped;
    }

    public static String getFirstField() {
        return firstField;
    }

    public static void setFirstField(String firstField) {
        DataManager.firstField = firstField;
    }

    public static String getSecondField() {
        return secondField;
    }

    public static void setSecondField(String secondField) {
        DataManager.secondField = secondField;
    }

    public static String getThirdField() {
        return thirdField;
    }

    public static void setThirdField(String thirdField) {
        DataManager.thirdField = thirdField;
    }

    public static String getPropsDuration() {
        final var DURATION_TITLE = "Hours Worked";
        if (propsDuration.isEmpty()) {
            return DURATION_TITLE;
        }
        return propsDuration.concat(":").strip();
    }

    public static void setPropsDuration(String propsDuration) {
        DataManager.propsDuration = propsDuration;
    }

    public static String getPropsScans() {
        return propsScans.concat(":").strip();
    }

    public static void setPropsScans(String propsScans) {
        DataManager.propsScans = propsScans;
    }

    public static String getPropsRate() {
        return propsRate.concat(":").strip();
    }

    public static void setPropsRate(String propsRate) {
        DataManager.propsRate = propsRate;
    }

    public static String getPropsRatePercent() {
        return propsRatePercent.concat(":").strip();
    }

    public static void setPropsRatePercent(String propsRatePercent) {
        DataManager.propsRatePercent = propsRatePercent;
    }

    public static String getPropsContribution() {
        return propsContribution.concat(":").strip();
    }

    public static void setPropsContribution(String propsContribution) {
        DataManager.propsContribution = propsContribution;
    }

    private static Boolean finalized = false;

    public static Boolean getFinalized() {
        return finalized;
    }

    public static void setFinalized(Boolean finalized) {
        DataManager.finalized = finalized;
    }

    public static Integer getEmployeeTotal() {
        return employeeTotal;
    }

    public static void setEmployeeTotal(Integer employeeTotal) {
        DataManager.employeeTotal = employeeTotal;
    }

    public static Integer getExplicitEmployeeTotal() {
        return explicitEmployeeTotal;
    }

    public static void setExplicitEmployeeTotal(Integer explicitEmployeeTotal) {
        DataManager.explicitEmployeeTotal = explicitEmployeeTotal;
    }

    public static Integer getUserScans() {
        return userScans;
    }

    public static void setUserScans(Integer userScans) {
        DataManager.userScans = userScans;
    }

    public static String getEmployee() {
        return employee;
    }

    public static void setEmployee(String employee) {
        DataManager.employee = employee;
    }

    public static double getTotalContribution() {
        return totalContribution;
    }

    public static void setTotalContribution(double totalContribution) {
        DataManager.totalContribution = totalContribution;
    }

    public static Double getExplicitVolume() {
        return explicitVolume;
    }

    public static void setExplicitVolume(Double explicitVolume) {
        DataManager.explicitVolume = explicitVolume;
    }

    public static Double getDuration() {
        return duration;
    }

    public static void setDuration(Double duration) {
        DataManager.duration = duration;
    }

    public static double getAverage() {
        return average;
    }

    public static void setAverage(double average) {
        DataManager.average = average;
    }

    public static Double getVolume() {
        return volume;
    }

    public static void setVolume(Double volume) {
        DataManager.volume = volume;
    }

    public static StringBuffer getReport() {
        return report;
    }

    public static StringBuffer getPrevious() {
        return previous;
    }

    public static List<Double> getHandicap() {
        return handicap;
    }

    public static List<Double> getRate() {
        return rate;
    }

    public static List<String> getEmp() {
        return emp;
    }

}