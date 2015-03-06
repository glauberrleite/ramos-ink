package edu.ramos.ramosink.application;

/**
 * RAMOS Ink converts InkML files received from a Livescribe Smartpen's penlet
 * to image and/or video. This tool is a part of the RAMOS system, developed to
 * run in an independent way. Copyright (C) 2015 Glauber Rodrigues Leite
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import edu.ramos.ramosink.view.BaseController;
import edu.ramos.ramosink.view.BaseFrame;
import edu.ramos.ramosink.view.controllers.RootLayoutController;
import edu.ramos.ramosink.view.frames.HelpFrame;
import edu.ramos.ramosink.view.frames.ResourceGenFrame;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * RAMOS Ink main class. To get the latest implementation, check the project
 * github page: https://github.com/glauberrleite/ramos-ink
 * 
 * @author glauberrleite
 *
 */
public class Main extends Application {

	private static BorderPane rootLayout;
	private static BaseFrame resourceGen, help;
	private static BaseController rootController;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Setting the basic app properties
			primaryStage.setTitle("RAMOS Ink");
			primaryStage.setFullScreen(false);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(
					new Image(getClass().getClassLoader().getResourceAsStream(
							"edu/ramos/ramosink/resources/logo.png")));

			// Loads the Layout defined by the respective .fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader()
					.getResource("edu/ramos/ramosink/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);

			rootController = loader.getController();

			// Shows a pane inside the root pane
			showGen();

			// Starting GUI
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showGen() {
		resourceGen = new ResourceGenFrame();
		rootLayout.setCenter(resourceGen.getAnchorPane());
	}

	public static void showHelp() {
		help = new HelpFrame();
		rootLayout.setCenter(help.getAnchorPane());
	}

	public static ProgressBar getProgressBar() {
		return ((RootLayoutController) rootController).getProgressBar();
	}

	public static Label getStatus() {
		return ((RootLayoutController) rootController).getStatus();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
