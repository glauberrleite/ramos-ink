package edu.ramos.ramosink.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import edu.ramos.ramosink.application.Main;

public class VideoFactory {

	private String DIR_PATH;
	private int FRAME_RATE = 10;
	private int WIDTH = 300;
	private int HEIGHT = 300;
	private String IMAGE_FORMAT = ".jpg";
	private String outputfile = "output.avi";
	private static VideoFactory instance = null;

	private VideoFactory() {
	}

	public static VideoFactory getInstance() {
		if (instance == null) {
			instance = new VideoFactory();
		}
		return instance;
	}

	public void generateVideo() {

		File curret_image;
		BufferedImage bufferedImage = null;
		long time = 0;
		double increment_time = 1000 / FRAME_RATE;
		

		try {
			// First, let's make a IMediaWriter to write the file.
			final IMediaWriter writer = ToolFactory.makeWriter(outputfile);

			// We tell it we're going to add one video stream, with id 0,
			// at position 0, and that it will have a fixed frame rate of
			// FRAME_RATE.
			int streamId = writer.addVideoStream(0, 0, WIDTH, HEIGHT);

			// Now, we're going to loop
			// long startTime = System.nanoTime();

			int length = (new File(DIR_PATH)).listFiles().length;
			
			for (int index = 1; (curret_image = new File(DIR_PATH + index
					+ IMAGE_FORMAT)).exists(); index++) {

				// sets progress bar
				Main.setProgressBar(50 + (index*50)/length);
			
				bufferedImage = ImageIO.read(curret_image);

				// encode the image to stream #0
				writer.encodeVideo(streamId, bufferedImage,
						time += increment_time, TimeUnit.MILLISECONDS);
				System.out.println("encoded image: " + index);
				// Thread.sleep(2000);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIMAGE_FORMAT() {
		return IMAGE_FORMAT;
	}

	public void setIMAGE_FORMAT(String IMAGE_FORMAT) {
		this.IMAGE_FORMAT = IMAGE_FORMAT;
	}

	public String getDIR_PATH() {
		return DIR_PATH;
	}

	public void setDIR_PATH(String DIR_PATH) {
		this.DIR_PATH = DIR_PATH;
	}

	public int getFRAME_RATE() {
		return FRAME_RATE;
	}

	public void setFRAME_RATE(int FRAME_RATE) {
		this.FRAME_RATE = FRAME_RATE;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int WIDTH) {
		this.WIDTH = WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int HEIGHT) {
		this.HEIGHT = HEIGHT;
	}

	public String getOutputfile() {
		return outputfile;
	}

	public void setOutputfile(String outputfile) {
		this.outputfile = outputfile;
	}

}
