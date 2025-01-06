package xruisu.project.mpc.utility.system;

import xruisu.project.mpc.utility.system.Logger.LoggerType;

public interface Logging {
    void info(String str);

    void debug(String str);

    void warn(String str);

    void callLoggerEvent(String text, LoggerType type);
}