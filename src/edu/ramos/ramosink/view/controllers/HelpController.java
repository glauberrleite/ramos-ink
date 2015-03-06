package edu.ramos.ramosink.view.controllers;

import javafx.fxml.FXML;
import edu.ramos.ramosink.application.Main;
import edu.ramos.ramosink.view.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */
public class HelpController extends BaseController{
	@FXML
	public void back(){
		Main.showGen();
	}
}
