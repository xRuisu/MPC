package xruisu.project.mpc.utility.system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");

    private static LocalDateTime now = LocalDateTime.now();

    private static String currentTimeAndDate = dtf.format(now);
    private static String currentDate = df.format(now);
    private static String currentTime = tf.format(now);

    public static String getCurrentTimeAndDate() {
        return currentTimeAndDate;
    }

    public static String getCurrentDate() {
        return currentDate;
    }

    public static String getCurrentTime() {
        return currentTime;
    }
}
