package edu.ramos.ramosink.application;

import edu.ramos.ramosink.model.Status;
import edu.ramos.ramosink.view.BaseController;
import edu.ramos.ramosink.view.BaseFrame;
import edu.ramos.ramosink.view.controllers.RootLayoutController;
import edu.ramos.ramosink.view.frames.HelpFrame;
import edu.ramos.ramosink.view.frames.ResourceGenFrame;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
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
			primaryStage.setTitle("RAMOS Ink");
			primaryStage.setFullScreen(false);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(
					new Image(getClass().getResourceAsStream(
							"../resources/logo.png")));

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass()
					.getResource("../view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);

			rootController = loader.getController();

			showGen();

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

	public static void setProgress(double percentage){
		((RootLayoutController) rootController).setProgress(percentage);
	}
	
	public static void setStatus(Status status) {
		((RootLayoutController) rootController).setStatus(status);
	}

	public static void setStatus(Status status, int index, int size){
		((RootLayoutController) rootController).setStatus(status, index, size);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
