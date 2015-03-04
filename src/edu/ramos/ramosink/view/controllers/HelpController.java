package edu.ramos.ramosink.view.controllers;

import javafx.fxml.FXML;
import edu.ramos.ramosink.application.Main;
import edu.ramos.ramosink.view.BaseController;

public class HelpController extends BaseController{
	
	@FXML
	private void back(){
		Main.showGen();
	}
	
}
