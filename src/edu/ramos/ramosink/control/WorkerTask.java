package edu.ramos.ramosink.control;

import edu.ramos.ramosink.model.Status;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * 
 * @author glauberrleite
 *
 */
public class WorkerTask extends Task<Void> {

	boolean imageBoxSelected, videoBoxSelected;
	ObservableList<String> filesListItems;

	public WorkerTask(boolean imageBoxSelected, boolean videoBoxSelected,
			ObservableList<String> filesListItems) {
		this.imageBoxSelected = imageBoxSelected;
		this.videoBoxSelected = videoBoxSelected;
		this.filesListItems = filesListItems;
	}

	@Override
	protected Void call() throws Exception {
		updateTitle("Cancel");
		updateProgress(0, 100);

		int i = 1;
		if (imageBoxSelected) {
			for (String path : filesListItems) {
				ImageRendering render = new ImageRendering(path, i,
						filesListItems.size(), this);

				render.generateLastFrame();

				i++;
			}
		}

		i = 1;
		if (videoBoxSelected) {
			for (String path : filesListItems) {
				ImageRendering render = new ImageRendering(path, i,
						filesListItems.size(), this);

				render.run();

				i++;
			}
		}

		setStatus(Status.IDLE);
		setProgress(0);
		return null;
	}

	public void setStatus(Status status, int index, int size) {
		String gImage = "Status: Generating Writing Image";
		String gVideo = "Status: Generating Writing Video";

		if (size != 1) {
			gImage += " " + index + " of " + size;
			gVideo += " " + index + " of " + size;
		} else
			switch (status) {
			case IDLE: {
				updateMessage("Status: Idle");
				updateProgress(0, 1);
			}
				break;
			case GENERATING_IMAGE:
				updateMessage(gImage);
				break;
			case GENERATING_VIDEO:
				updateMessage(gVideo);
				break;
			case SUCCESS: {
				updateMessage("Status: Success");
				updateProgress(1, 1);
			}
				break;
			}
	}

	public void setStatus(Status status) {
		setStatus(status, 1, 1);
	}

	public void setProgress(double percentage) {
		updateProgress(percentage, 100);
	}

}
