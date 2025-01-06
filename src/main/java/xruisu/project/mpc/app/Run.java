package xruisu.project.mpc.app;

import xruisu.project.mpc.MPC;
import xruisu.project.mpc.utility.system.Logger;

public class Run {

    private static Logger logger = new Logger();

    public static void main(String[] args) {
        System.out.println("\n...Running... " + " \n\n");
        logger.info("Starting MPC...");
        MPC.main(args);
    }
}