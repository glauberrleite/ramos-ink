package edu.ramos.ramosink.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

import edu.ramos.ramosink.application.Main;

public class VideoFactory {

	private String dirPath;
	private int frameRate = 10;
	private int width = 566;
	private int height = 800;
	private String imageFormat = ".png";
	private String outputfile;
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
		double increment_time = 1000 / frameRate;

		try {
			// First, let's make a IMediaWriter to write the file.
			final IMediaWriter writer = ToolFactory.makeWriter(outputfile);

			// We tell it we're going to add one video stream, with id 0,
			// at position 0, and that it will have a fixed frame rate of
			// FRAME_RATE.
			int streamId = writer.addVideoStream(0, 0,
					ICodec.ID.CODEC_ID_MPEG4, width, height);

			// Now, we're going to loop
			// long startTime = System.nanoTime();

			int count = (new File(dirPath)).listFiles().length;
			for (int index = 1; (curret_image = new File(dirPath + index
					+ imageFormat)).exists(); index++) {

				// sets progress bar
				Main.setProgress(50 + ((index * 50) / count));

				bufferedImage = ImageIO.read(curret_image);

				// encode the image to stream #0
				BufferedImage image = new BufferedImage(
						bufferedImage.getWidth(), bufferedImage.getHeight(),
						BufferedImage.TYPE_3BYTE_BGR);

				image.getGraphics().drawImage(bufferedImage, 0, 0, null);

				writer.encodeVideo(streamId, image, time += increment_time,
						TimeUnit.MILLISECONDS);
				System.out.println("encoded image: " + index);
				// Thread.sleep(2000);
			}
			writer.close();
			// writer.setForceInterleave(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getImageFormat() {
		return imageFormat;
	}

	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public int getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getOutputfile() {
		return outputfile;
	}

	public void setOutputfile(String outputfile) {
		this.outputfile = outputfile;
	}

}
