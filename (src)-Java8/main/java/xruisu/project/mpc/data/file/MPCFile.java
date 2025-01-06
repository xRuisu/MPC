package xruisu.project.mpc.data.file;

import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.IDGenerator;

public class MPCFile {

    private static final String MPC_FOLDER_PATH = System.getProperty("user.home") + "\\Desktop//MPC/reports/";

    public static void create() {
        IDGenerator.generateID();

        String mpcFileName = "METRICS REPORT (" + IDGenerator.getID() + ").mpc";
        String mpcFilePath = MPC_FOLDER_PATH + mpcFileName;

        FileListener file = new FileListener(null);
        file.createMPCFile(DataManager.getReport().toString(), MPC_FOLDER_PATH, mpcFilePath);
    }
}