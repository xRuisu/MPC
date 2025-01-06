package xruisu.project.mpc.data.file;

import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.utility.system.Logger;

public class LogFile {

    private static final String LOG_FOLDER_PATH = System.getProperty("user.home") + "\\Desktop//MPC/logs/";
    private static final String LOG_FILE_NAME = "MPC LOG" + DataManager.getCurrentDate() + ".txt";
    private static final String LOG_FILE_PATH = LOG_FOLDER_PATH + LOG_FILE_NAME;

    public static void create() {
        FileListener file = new FileListener(null);
        file.createLogFile(Logger.getLog().toString(), LOG_FOLDER_PATH, LOG_FILE_PATH);
    }
}
