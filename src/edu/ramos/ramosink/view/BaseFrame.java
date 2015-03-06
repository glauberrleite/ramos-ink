package edu.ramos.ramosink.view;

import java.io.IOException;

import edu.ramos.ramosink.application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * A basic AnchorPane Frame to make quicker the creation of new Frames.
 * 
 * @author glauberrleite
 *
 */
public abstract class BaseFrame {

	protected BaseController controller;
	private AnchorPane pane;

	public BaseFrame(String resource) {
		pane = buildPane(resource);
	}

	/**
	 * A generic way to create an AnchorPane given the resource.
	 * 
	 * @param resource
	 *            The resource's location in classpath.
	 * @return The AnchorPane.
	 */
	private final AnchorPane buildPane(String resource) {
		AnchorPane pane = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader()
					.getResource(resource));
			pane = (AnchorPane) loader.load();

			controller = loader.getController();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return pane;
	}

	public final AnchorPane getAnchorPane() {
		return pane;
	}

	/**
	 * Gives the view controller.
	 * 
	 * @return A controller.
	 */
	public BaseController getController() {
		return controller;
	}

}
