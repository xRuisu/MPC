package xruisu.project.mpc.utility.system;

public class ExceptionHandling {

    private static final String INVALID_INPUT = "Invalid input! Please ensure the correct value format is being used such as whole numbers or proper formatting.";
    private static final String CHARACTER_LIMIT = "Input exceeds 15 characters! Please do not exceed 15 characters. This includes whitespace.";

    public static String getInvalidInput() {
        return INVALID_INPUT;
    }

    public static String getCharacterLimit() {
        return CHARACTER_LIMIT;
    }

    public static String critical() {
        return "\n\n A critical error has occurred!\n";
    }
}
