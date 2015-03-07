package edu.ramos.ramosink.view.controllers;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import edu.ramos.ramosink.application.Main;
import edu.ramos.ramosink.control.WorkerTask;
import edu.ramos.ramosink.view.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */
public class ResourceGenController extends BaseController {

	Task<Void> worker;

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
			if (filesList.getItems().isEmpty()) {
				JOptionPane.showMessageDialog(null, "No File selected");
			} else if (!imageBox.isSelected() && !videoBox.isSelected()) {
				JOptionPane.showMessageDialog(null, "No Process selected");
			} else {

				lockControls();

				// Unbinding the properties
				worker = new WorkerTask(imageBox.isSelected(),
						videoBox.isSelected(), filesList.getItems());
				Main.getStatus().textProperty().unbind();
				Main.getProgressBar().progressProperty().unbind();

				// Associating properties to the task, turning possible to
				// manipulate the GUI outside the JavaFX Main Thread
				Main.getStatus().textProperty().bind(worker.messageProperty());
				Main.getProgressBar().progressProperty()
						.bind(worker.progressProperty());

				// When the task is over, unlock the controls
				worker.setOnSucceeded(event -> {
					unlockControls();
					JOptionPane.showMessageDialog(null,
							"All processes concluded with success");
				});

				// The thread will run in background
				new Thread(worker).start();
			}
		} else {
			// Send a cancel signal to the task
			worker.cancel();

			JOptionPane
					.showMessageDialog(null,
							"Task cancelled... All items already processed will keep existing");
			// unbinding
			Main.getStatus().textProperty().unbind();
			Main.getProgressBar().progressProperty().unbind();
			Main.getStatus().setText("Status: Idle");
			Main.getProgressBar().setProgress(0);
			unlockControls();
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
