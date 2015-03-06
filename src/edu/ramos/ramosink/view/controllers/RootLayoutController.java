package edu.ramos.ramosink.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import edu.ramos.ramosink.application.Main;
import edu.ramos.ramosink.view.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */
public class RootLayoutController extends BaseController {
	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label statusLabel;

	@FXML
	private void help() {
		Main.showHelp();
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}
	
	public Label getStatus(){
		return statusLabel;
	}
}
