package xruisu.project.mpc.utility.system;

public class Logger extends ExceptionHandling implements Logging {

    private static StringBuffer console = new StringBuffer();

    public enum LoggerType {
        INFO, WARN, DEBUG
    }

    @Override
    public void info(String str) {
        callLoggerEvent(str, LoggerType.INFO);
    }

    @Override
    public void warn(String str) {
        callLoggerEvent(str, LoggerType.WARN);
    }

    @Override
    public void debug(String str) {
        callLoggerEvent("~MPC-( ...".concat(str).concat("... )"), LoggerType.DEBUG);
    }

    @Override
    public void callLoggerEvent(String text, LoggerType type) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[3];
        String callerClassName = caller.getClassName();

        try {
            Class.forName(callerClassName);

            // Alternative to getPackageName for Java 8
            String longName = callerClassName.contains(".")
                    ? callerClassName.substring(0, callerClassName.lastIndexOf('.'))
                    : "";
            String[] parts = longName.split("\\.");
            String packageName;

            if (parts.length >= 5) {
                packageName = parts[parts.length - 5] + "." + parts[parts.length - 2];
            } else {
                packageName = longName;
            }

            String loggerFormat = "<" + DateTimeManager.getCurrentTimeAndDate() + "> " + "[" + packageName + "] ("
                    + type + ")] >> ";

            System.out.println(loggerFormat + text);
            getLog().append("\n" + loggerFormat + text);

        } catch (ClassNotFoundException e) {
            warn(critical());
            e.printStackTrace();
        }
    }

    public static StringBuffer getLog() {
        return console;
    }
}