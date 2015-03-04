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
	private void help() {
		Main.showHelp();
	}

	public void setStatus(Status status, int index, int size) {
		if (size == 1)
			setStatus(status);
		else
			switch (status) {
			case IDLE: {
				statusLabel.setText("Status: Idle");
				progressBar.setProgress(0);
			}
				break;
			case GENERATING_IMAGE:
				statusLabel.setText("Status: Generating Writing Image " + index
						+ " of " + size);
				break;
			case GENERATING_VIDEO:
				statusLabel.setText("Status: Generating Writing Video " + index
						+ " of " + size);
				break;
			case SUCCESS: {
				statusLabel.setText("Status: Success");
				progressBar.setProgress(1);
			}
				break;
			}

	}

	public void setStatus(Status status) {
		switch (status) {
		case IDLE: {
			statusLabel.setText("Status: Idle");
			progressBar.setProgress(0);
		}
			break;
		case GENERATING_IMAGE:
			statusLabel.setText("Status: Generating Writing Image");
			break;
		case GENERATING_VIDEO:
			statusLabel.setText("Status: Generating Writing Video");
			break;
		case SUCCESS: {
			statusLabel.setText("Status: Success");
			progressBar.setProgress(1);
		}
			break;
		}
	}
	
	public void setProgress(double percentage){
		if(percentage >= 0 || percentage <= 100)
		progressBar.setProgress(percentage/100);
	}
}
