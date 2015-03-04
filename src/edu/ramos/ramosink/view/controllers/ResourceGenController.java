package edu.ramos.ramosink.view.controllers;

import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import edu.ramos.ramosink.application.Main;
import edu.ramos.ramosink.control.ImageRendering;
import edu.ramos.ramosink.model.Status;
import edu.ramos.ramosink.view.BaseController;

public class ResourceGenController extends BaseController {

	private boolean locked = false;

	@FXML
	private ListView<String> filesList;

	@FXML
	private CheckBox imageBox;

	@FXML
	private CheckBox videoBox;

	@FXML
	private Button generateButton;

	@FXML
	private void addFile() {
		if (!locked) {

			// Launching the FileChooser
			FileChooser fileChooser = new FileChooser();

			fileChooser.setTitle("Add Files");

			fileChooser.setInitialDirectory(new File(System
					.getProperty("user.home")));

			fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("InkML File", "*.xml"));

			List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

			// Adding the files to list
			if (files != null) {
				for (File file : files) {
					// Checking if already exists
					boolean isNew = true;

					for (String listItem : filesList.getItems()) {
						if (listItem.equals(file.getPath()))
							isNew = false;
					}

					// Add file
					if (isNew)
						filesList.getItems().add(file.getPath());
				}
			}

		}
	}

	@FXML
	private void removeFile() {
		if (!locked) {
			String item = filesList.getSelectionModel().getSelectedItem();
			filesList.getItems().remove(item);
		}
	}

	@FXML
	private void generate() {
		if (!locked) {
			// Starts the Resources Processing
			lockControls();

			if (filesList.getItems().isEmpty()) {
				Main.showMsg("Empty List");
			} else {
				new Thread(new Runnable() {

					@Override
					public void run() {
						int i = 1;
						if (imageBox.isSelected()) {
							for (String path : filesList.getItems()) {
								ImageRendering render = new ImageRendering(
										path, i, filesList.getItems().size());

								render.generateLastFrame();

								i++;
							}
						}

						i = 1;
						if (videoBox.isSelected()) {
							for (String path : filesList.getItems()) {
								ImageRendering render = new ImageRendering(
										path, i, filesList.getItems().size());

								render.run();

								i++;
							}
						}

						unlockControls();
						Main.changeStatus(Status.IDLE);
					}
				}).run();

			}

		}
	}

	private void lockControls() {
		filesList.setDisable(true);
		imageBox.setDisable(true);
		videoBox.setDisable(true);
		generateButton.setText("Cancel");
		locked = true;
	}

	private void unlockControls() {
		filesList.setDisable(false);
		imageBox.setDisable(false);
		videoBox.setDisable(false);
		generateButton.setText("Generate");
		locked = false;
	}

	public boolean getLockedStatus() {
		return locked;
	}
}
