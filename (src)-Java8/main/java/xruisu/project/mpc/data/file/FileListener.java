package xruisu.project.mpc.data.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import xruisu.project.mpc.data.DataManager;
import xruisu.project.mpc.data.security.EncryptionHandler;
import xruisu.project.mpc.utility.NodeHandler;
import xruisu.project.mpc.utility.system.ExceptionHandling;
import xruisu.project.mpc.utility.system.Logger;
import xruisu.project.mpc.utility.system.MyFonts;

public class FileListener extends EncryptionHandler implements Listener {

	private static Logger logger = new Logger();
	private static String fileName;

	public FileListener(TextFlow mainText) {
	}

	@Override
	public void createMPCFile(String data, String folderPath, String filePath) {
		try {
			File folder = new File(folderPath);
			if (!folder.exists() && !folder.mkdirs()) {
				logger.warn("Failed to create directory: " + folderPath);
				return;
			}

			File file = new File(filePath);
			if (file.createNewFile()) {
				logger.info("Report file created: " + file.getName());
			} else {
				logger.info("File already exists, the current file was overwritten.");
			}

			try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8")) {
				writer.write(data);
				logger.info("Report was written to file successfully with UTF-8 encoding.");
			}
			fileName = file.getName();
		} catch (Exception e) {
			logger.warn("An error occurred while creating or writing to the file.");
			logger.warn(ExceptionHandling.critical());
			logger.warn(e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void openMPCFile(Stage stage, TextFlow mainText) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MPC File (*.mpc)", "*.mpc"));

		String dataPath = System.getProperty("user.home");
		File reportsFolder = new File(dataPath + "\\Desktop//MPC/reports/");

		if (reportsFolder.exists() && reportsFolder.isDirectory()) {
			fileChooser.setInitialDirectory(reportsFolder);
		} else {
			logger.warn("Reports folder not found at " + reportsFolder.getPath() + ". Using default directory.");
		}

		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
				char[] buffer = new char[(int) file.length()];
				reader.read(buffer);
				String mpcFileData = new String(buffer);

				Text displayText = new Text(mpcFileData);
				NodeHandler.setStyleOn(mainText, displayText, MyFonts.getGruppo(), 25, Color.BLACK, Color.WHITESMOKE,
						"");
				NodeHandler.add(mainText, 0, displayText);

				logger.info("File opened successfully with UTF-8 encoding: " + file.getName());
			} catch (Exception e) {
				logger.warn("Error reading file, please ensure file is a .mpc file: " + file.getName());
				logger.warn(ExceptionHandling.critical());
				logger.warn(e.toString());
				e.printStackTrace();
			}
		} else {
			logger.info("No file was selected.");
		}
	}

	@Override
	public void createLogFile(String data, String folderPath, String filePath) {
		try {
			File folder = new File(folderPath);
			if (!folder.exists() && !folder.mkdirs()) {
				logger.warn("Failed to create directory: " + folderPath);
				return;
			}

			File file = new File(filePath);
			if (file.createNewFile()) {
				logger.info("Log has been created!");
			} else {
				logger.info("Log for: " + DataManager.getCurrentDate() + " has been updated!");
			}

			try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
				writer.write(data);
				logger.info("Log written to file successfully with UTF-8 encoding.");
			}
		} catch (Exception e) {
			logger.warn("An error occurred while creating or writing to the file.");
			logger.warn(ExceptionHandling.critical());
			logger.warn(e.toString());
			e.printStackTrace();
		}
	}

	public static String getFileName() {
		return fileName;
	}
}
