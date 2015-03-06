package edu.ramos.ramosink.control;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import edu.ramos.ramosink.application.Main;
import edu.ramos.ramosink.model.Status;
import edu.ramos.ramosink.model.Stroke;

/**
 * @author glauberrleite
 *
 */
public class ImageRendering extends Thread {

	private final float PEN_TIP_WIDTH = 0.5f;
	private final String IMAGE_FORMAT = "png";
	private long frm_count = 0;
	private final int FRAMERATE = 10;
	private final int AUX_RATE = 1000 / FRAMERATE;
	private String tempDir;
	private String absoluteOutputDir;
	private String inkMLPath;
	private String filename;
	private int index;
	private long time;
	private int size;
	private WorkerTask task;

	public List<Stroke> strokes = new ArrayList<>();

	public ImageRendering(String inkMLPath, WorkerTask task) {
		this.inkMLPath = inkMLPath;
		index = 1;
		size = 1;
		this.task = task;

		// Determining the files
		File file = new File(inkMLPath);
		absoluteOutputDir = file.getParent()
				+ System.getProperty("file.separator");
		tempDir = absoluteOutputDir + "temp"
				+ System.getProperty("file.separator");
		// Taking the filename without the extension
		filename = file.getName().split(".")[0];

		time = new Date().getTime();
	}

	public ImageRendering(String inkMLPath, int index, int length,
			WorkerTask task) {
		this.inkMLPath = inkMLPath;
		this.index = index;
		this.size = length;
		this.task = task;

		// Determining the files
		File file = new File(inkMLPath);
		absoluteOutputDir = file.getParent()
				+ System.getProperty("file.separator");
		tempDir = absoluteOutputDir + "temp"
				+ System.getProperty("file.separator");
		// Taking the filename without the extension
		filename = file.getName();

		time = new Date().getTime();
	}

	public void generateLastFrame() {

		BufferedImage buffer = null;
		Graphics2D graphics2d = null;

		try {

			// Retrieving in a data structure a list of objects representing
			// points of the writing
			strokes = RetrieveStrokes.getInstance().getPointsFromXML(inkMLPath);

			buffer = ImageIO.read(Main.class
					.getResource("../resources/mask.bmp"));

			graphics2d = buffer.createGraphics();

			graphics2d.setColor(Color.BLACK);
			graphics2d.setStroke(new BasicStroke(PEN_TIP_WIDTH));

			if (size > 1) {
				task.setStatus(Status.GENERATING_IMAGE, index, size);
			} else {
				task.setStatus(Status.GENERATING_IMAGE);
			}

			for (int i = 1; i < strokes.size(); i++) {
				task.setProgress((100 * i) / strokes.size());

				// draws
				if (strokes.get(i).getId() == strokes.get(i - 1).getId()) {

					// difference starts here
					float dx = Math.abs(strokes.get(i).getX()
							- strokes.get(i - 1).getX());
					float dy = Math.abs(strokes.get(i).getY()
							- strokes.get(i - 1).getY());

					if (Math.sqrt(dx * dx + dy * dy) < 100) {
						// end here

						graphics2d.drawLine((int) strokes.get(i - 1).getX(),
								(int) strokes.get(i - 1).getY(), (int) strokes
										.get(i).getX(), (int) strokes.get(i)
										.getY());
					}
				} else {

					// draws a point
					graphics2d.drawLine((int) strokes.get(i).getX(),
							(int) strokes.get(i).getY(), (int) strokes.get(i)
									.getX(), (int) strokes.get(i).getY());
				}
			}

			File image = new File(absoluteOutputDir + time + "-" + filename
					+ "_WritingImage" + "." + IMAGE_FORMAT);

			ImageIO.write(buffer, IMAGE_FORMAT.toUpperCase(), image);

			if (size > 1) {
				task.setStatus(Status.SUCCESS, index, size);
			} else {
				task.setStatus(Status.SUCCESS);
			}

			Thread.sleep(3000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates the frames from points
	 */
	private void generateFramesFromPoints() {

		BufferedImage buffer = null;
		Graphics2D graphics2d = null;
		long diff = 0;
		try {

			buffer = ImageIO.read(Main.class
					.getResourceAsStream("../resources/mask.bmp"));

			graphics2d = buffer.createGraphics();

			graphics2d.setColor(Color.BLACK);
			graphics2d.setStroke(new BasicStroke(PEN_TIP_WIDTH));

			for (int i = 1; i < strokes.size(); i++) {
				// progress bar
				task.setProgress((50 * i) / strokes.size());

				// build frames
				diff += strokes.get(i).getTime() - strokes.get(i - 1).getTime();

				if (diff >= AUX_RATE) {
					if (diff == AUX_RATE) {
						diff = 0;
						frm_count++;
						ImageIO.write(buffer, IMAGE_FORMAT.toUpperCase(),
								new File(tempDir + frm_count + "."
										+ IMAGE_FORMAT));

					} else {
						int aux = (int) (diff / AUX_RATE);
						for (int j = 0; j < aux; j++) {
							frm_count++;
							ImageIO.write(buffer, IMAGE_FORMAT.toUpperCase(),
									new File(tempDir + frm_count + "."
											+ IMAGE_FORMAT));
						}
						diff = (diff % AUX_RATE);
					}
				}

				// draws
				if (strokes.get(i).getId() == strokes.get(i - 1).getId()) {

					// difference starts here
					float dx = Math.abs(strokes.get(i).getX()
							- strokes.get(i - 1).getX());
					float dy = Math.abs(strokes.get(i).getY()
							- strokes.get(i - 1).getY());

					if (Math.sqrt(dx * dx + dy * dy) < 100) {
						// end here

						graphics2d.drawLine((int) strokes.get(i - 1).getX(),
								(int) strokes.get(i - 1).getY(), (int) strokes
										.get(i).getX(), (int) strokes.get(i)
										.getY());
						//
					}
				} else {

					// draws a point
					graphics2d.drawLine((int) strokes.get(i).getX(),
							(int) strokes.get(i).getY(), (int) strokes.get(i)
									.getX(), (int) strokes.get(i).getY());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Begin method to the thread
	 */
	private void begin() {

		// Making the temporary directory if it doesn't exist
		if (!new File(tempDir).exists()) {
			new File(tempDir).mkdir();
		}

		if (size > 1) {
			task.setStatus(Status.GENERATING_VIDEO, index, size);
		} else {
			task.setStatus(Status.GENERATING_VIDEO);
		}

		// Retrieving in a data structure a list of objects representing points
		// of the writing
		strokes = RetrieveStrokes.getInstance().getPointsFromXML(inkMLPath);

		generateFramesFromPoints();

		VideoFactory videoFactory = VideoFactory.getInstance();

		videoFactory.setDirPath(tempDir);

		videoFactory.setTask(task);
		videoFactory.setImageFormat("." + IMAGE_FORMAT);
		videoFactory.setFrameRate(10);
		videoFactory.setWidth(566);
		videoFactory.setHeight(800);
		videoFactory.setOutputfile(absoluteOutputDir + time + "-" + filename
				+ "_WritingVideo.mp4");
		videoFactory.generateVideo();

		deleteTempFiles();

		task.setProgress(100);

		if (size > 1) {
			task.setStatus(Status.SUCCESS, index, size);
		} else {
			task.setStatus(Status.SUCCESS);
		}

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteTempFiles() {
		try {
			File dir = new File(tempDir);

			if (dir.listFiles().length > 0) {
				for (File f : dir.listFiles()) {
					f.delete();
				}
			}

			dir.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		this.begin();
	}
}
