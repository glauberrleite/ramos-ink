package edu.ramos.ramosink.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import edu.ramos.ramosink.application.Main;
import edu.ramos.ramosink.model.Status;
import edu.ramos.ramosink.view.BaseController;

public class RootLayoutController extends BaseController {

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label statusLabel;

	@FXML
	private void config() {
		Main.showConfig();
	}

	@FXML
	private void help() {
		Main.showHelp();
	}

	public void setProgressBar(double percentage) {
		if (percentage >= 0 && percentage <= 1)
			progressBar.setProgress(percentage / 100);
	}

	public void changeStatus(Status status) {
		switch (status) {
		case IDLE: {
			statusLabel.setText("Idle");
			progressBar.setProgress(0);
		}
			break;

		case GENERATING_IMAGE:
			statusLabel.setText("Generating Writing Image");
			break;
		case GENERATING_VIDEO:
			statusLabel.setText("Generating Writing Video");
			break;
		case SUCCESS: {
			statusLabel.setText("Success");
			progressBar.setProgress(1);
		}
			break;
		}
	}

	public void changeStatus(Status status, int index, int size) {

		switch (status) {
		case IDLE: {
			statusLabel.setText("Idle");
			progressBar.setProgress(0);
		}
			break;
		case GENERATING_IMAGE:
			statusLabel.setText("Generating Writing Image " + index + " of "
					+ size);
			break;
		case GENERATING_VIDEO:
			statusLabel.setText("Generating Writing Video " + index + " of "
					+ size);
			break;
		case SUCCESS: {
			statusLabel.setText("Success " + index + " of " + size);
			progressBar.setProgress(1);
		}
			break;
		}
	}

}
