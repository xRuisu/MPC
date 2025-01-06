package xruisu.project.mpc.utility;

import java.io.InputStream;
import java.util.Properties;

import xruisu.project.mpc.utility.system.Logger;

public class VersionUtil {

    private static Logger logger = new Logger();

    public static String getAppVersion() {
        String version = "Unavailable";
        try (InputStream input = VersionUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                logger.warn("MPC version not found.");
                return version;
            }
            prop.load(input);
            version = prop.getProperty("version");
        } catch (Exception e) {
            logger.warn(e.toString());
            e.printStackTrace();
        }
        return version;
    }
}