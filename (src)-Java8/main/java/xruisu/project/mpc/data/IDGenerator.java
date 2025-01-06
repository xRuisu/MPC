package xruisu.project.mpc.data;

import java.security.SecureRandom;
import java.util.Random;

import xruisu.project.mpc.utility.system.Logger;

public class IDGenerator {

    private static Logger logger = new Logger();
    private String generatedID = generateRandomChar(3) + generateRandomNumber(3);

    private static String id;

    public static void generateID() {
        IDGenerator id = new IDGenerator();
        if (IDGenerator.id == null) {
            final String ID = id.generatedID;
            setID(ID);
            logger.info("Successfully created report id: " + ID);
        } else {
            String NEW_ID = id.generateRandomChar(3) + id.generateRandomNumber(3);
            if (NEW_ID.equals(getID())) {
                logger.warn("Failed create id, the report id already exists");
                logger.debug("Generating new id");
                logger.info("New id successfully generated: " + NEW_ID);
                setID(NEW_ID);
            } else {
                setID(NEW_ID);
                logger.info("Created Report ID: " + NEW_ID);
            }
        }
    }

    private String generateRandomChar(int length) {

        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            builder.append(randomChar);
        }
        return builder.toString();
    }

    private int generateRandomNumber(int length) {

        Random random = new Random();

        int minValue = (int) Math.pow(10, 3 - 1);
        int maxValue = (int) Math.pow(10, 3) - 1;

        return random.nextInt(maxValue - minValue + 1) + minValue;
    }

    public static String getID() {
        return id;
    }

    private static void setID(String id) {
        IDGenerator.id = id;
    }
}