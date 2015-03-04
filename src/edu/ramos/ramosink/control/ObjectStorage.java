package edu.ramos.ramosink.control;

public class ObjectStorage {
	private static ObjectStorage instance;
	private String outputDir;
	private String backgroundImage;
	
	private ObjectStorage(){
		outputDir = getClass().getClassLoader().getResource("./").toString();
		backgroundImage = getClass().getResource("../resources/mask.bmp").toString();
	}
	
	
	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}


	/**
	 * Singleton method
	 * @return Returns the only instance
	 */
	public static ObjectStorage getInstance(){
		if(instance == null) instance = new ObjectStorage();
		return instance;
	}
}
