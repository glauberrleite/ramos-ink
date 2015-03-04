package edu.ramos.ramosink.application;

import edu.ramos.ramosink.model.Status;
import edu.ramos.ramosink.view.BaseFrame;
import edu.ramos.ramosink.view.controllers.ResourceGenController;
import edu.ramos.ramosink.view.controllers.RootLayoutController;
import edu.ramos.ramosink.view.frames.ConfigFrame;
import edu.ramos.ramosink.view.frames.HelpFrame;
import edu.ramos.ramosink.view.frames.ResourceGenFrame;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;

/**
 * 
 * @author glauberrleite
 *
 */
public class Main extends Application {

	private static BorderPane rootLayout;
	private static RootLayoutController rootController;
	private static BaseFrame resourceGen, config, help;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("RAMOS Ink");
			primaryStage.setResizable(false);
			primaryStage.setFullScreen(false);
			primaryStage.getIcons().add(
					new Image(getClass().getResource("../resources/logo.png").toString()));

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass()
					.getResource("../view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			rootController = loader.getController();

			Scene scene = new Scene(rootLayout);

			primaryStage.setScene(scene);
			primaryStage.show();

			intFrames();

			showGen();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void intFrames() {
		resourceGen = new ResourceGenFrame();
		config = new ConfigFrame();
		help = new HelpFrame();
	}

	public static void showGen() {
		rootLayout.setCenter(resourceGen.getAnchorPane());
	}

	public static void showConfig() {
		rootLayout.setCenter(config.getAnchorPane());
	}

	public static void showHelp() {
		rootLayout.setCenter(help.getAnchorPane());
	}

	public static boolean getLockedStatus() {
		return ((ResourceGenController) resourceGen.getController())
				.getLockedStatus();
	}

	public static void showMsg(String text) {
		// Image image = new Image("edu/ramos/ramosink/resources/warning.png",
		// 50, 50, false, false);
		Label msg = new Label(text);
		msg.setFont(new Font(20));

		Stage temp = (new Stage());

		temp.setScene(new Scene(msg));

		// temp.getIcons().add(new
		// Image("edu/ramos/ramosink/resources/warning.png"));
		temp.show();
	}

	public static void changeStatus(Status status) {
		rootController.changeStatus(status);
	}

	public static void changeStatus(Status status, int index, int size) {
		rootController.changeStatus(status, index, size);
	}

	public static void setProgressBar(double percentage) {
		rootController.setProgressBar(percentage);
	}

}
