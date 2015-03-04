package edu.ramos.ramosink.view.controllers;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import edu.ramos.ramosink.application.Main;
import edu.ramos.ramosink.control.ObjectStorage;
import edu.ramos.ramosink.view.BaseController;

public class ConfigController extends BaseController {

	@FXML
	private TextField background;

	@FXML
	private TextField outputDir;

	@FXML
	private void chooseBackground() {
		if (!Main.getLockedStatus()) {
			// Launching the FileChooser
			FileChooser fileChooser = new FileChooser();

			fileChooser.setTitle("Choose Custom Background");

			fileChooser.setInitialDirectory(new File(System
					.getProperty("user.home")));

			fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("Bitmap File", "*.bmp"));

			File image = fileChooser.showOpenDialog(new Stage());

			if (image != null) {
				// Setting the Textfield
				background.setText(image.getPath());
			}

		}

	}

	@FXML
	private void chooseOutputDir() {
		if (!Main.getLockedStatus()) {
			// Launching the DirectoryChooser
			DirectoryChooser directoryChooser = new DirectoryChooser();

			directoryChooser.setTitle("Choose Custom Output Directory");

			directoryChooser.setInitialDirectory(new File(System
					.getProperty("user.home")));

			File dir = directoryChooser.showDialog(new Stage());

			if (dir != null)
				outputDir.setText(dir.getPath());

		}
	}

	@FXML
	private void save() {
		if (!background.getText().equals(""))
			ObjectStorage.getInstance()
					.setBackgroundImage(background.getText());
		if (!outputDir.getText().equals(""))
			ObjectStorage.getInstance().setOutputDir(outputDir.getText());
	}

	@FXML
	private void back() {
		Main.showGen();
		// Keep the stored values into the controls
		background.setText(ObjectStorage.getInstance().getBackgroundImage());
		outputDir.setText(ObjectStorage.getInstance().getOutputDir());
	}
}
