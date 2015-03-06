package edu.ramos.ramosink.control;

/**
 *  * RAMOS Ink converts InkML files received from a Livescribe Smartpen's penlet
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
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

/**
 * 
 * @author glauberrleite
 *
 */
public class VideoFactory {

	private String dirPath;
	private int frameRate = 10;
	private int width = 566;
	private int height = 800;
	private String imageFormat = ".png";
	private String outputfile;
	private static VideoFactory instance = null;
	private WorkerTask task;

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
				task.setProgress(50 + ((index * 50) / count));

				bufferedImage = ImageIO.read(curret_image);

				// encode the image to stream #0
				BufferedImage image = new BufferedImage(
						bufferedImage.getWidth(), bufferedImage.getHeight(),
						BufferedImage.TYPE_3BYTE_BGR);

				image.getGraphics().drawImage(bufferedImage, 0, 0, null);

				writer.encodeVideo(streamId, image, time += increment_time,
						TimeUnit.MILLISECONDS);
				System.out.println("encoded image: " + index);
			}
			writer.close();
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

	public WorkerTask getTask() {
		return task;
	}

	public void setTask(WorkerTask task) {
		this.task = task;
	}

}
