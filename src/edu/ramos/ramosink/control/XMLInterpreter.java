package edu.ramos.ramosink.control;

import java.net.URL;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XMLInterpreter {

	private static XMLInterpreter instance;

	/**
	 * Singleton method
	 * 
	 * @return The instance of the global object
	 */
	public static XMLInterpreter getInstance() {
		if (instance == null) {
			instance = new XMLInterpreter();
		}
		return instance;
	}

	public Document parse(URL url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}

}
