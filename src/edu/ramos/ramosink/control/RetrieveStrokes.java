package edu.ramos.ramosink.control;

/**
 * Copyright 2001-2010 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided that the
 * following conditions are met:
 * 
 * 1. Redistributions of source code must retain copyright statements and
 * notices. Redistributions must also contain a copy of this document.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The name "DOM4J" must not be used to endorse or promote products derived
 * from this Software without prior written permission of MetaStuff, Ltd. For
 * written permission, please contact dom4j-info@metastuff.com.
 * 
 * 4. Products derived from this Software may not be called "DOM4J" nor may
 * "DOM4J" appear in their names without prior written permission of MetaStuff,
 * Ltd. DOM4J is a registered trademark of MetaStuff, Ltd.
 * 
 * 5. Due credit should be given to the DOM4J Project -
 * http://dom4j.sourceforge.net
 * 
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL METASTUFF, LTD. OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import edu.ramos.ramosink.model.Stroke;

/**
 *
 * @author glauberrleite
 *
 */

public class RetrieveStrokes {

	/* Depending on the mask used, a margin correction may be necessary.
	 * It occurs because of the difference between the size and margins of real paper, and
	 * the size and margins of the mask file.
	 * Negatives margin values moves x for the left and y for up. */
	private static final long X_MARGIN_CORRECTION = - 65;
	private static final long Y_MARGIN_CORRECTION = - 25;
	
	public static RetrieveStrokes instance;

	private RetrieveStrokes() {
	}

	/**
	 * Singleton method
	 * 
	 * @return The instance of the global object
	 */
	public static RetrieveStrokes getInstance() {
		if (instance == null) {
			instance = new RetrieveStrokes();
		}
		return instance;
	}

	/**
	 * Method to save the output XML from the Smartpen in a Stroke-type
	 * ArrayList
	 * 
	 * @param filePath
	 *            - The system path from the file containing the points in XML
	 *            format
	 * @return A Data Structure with objects Stroke representing the points
	 */
	public ArrayList<Stroke> getPointsFromXML(String filePath) {

		ArrayList<Stroke> points = new ArrayList<>();

		try {
			Document document = XMLInterpreter.getInstance().parse(
					new File(filePath).toURI().toURL());

			Element root = document.getRootElement();

			int id = 0;

			// Entering in each the strokes field using an Iterator
			for (Iterator<?> i = root.elementIterator("strokes"); i.hasNext();) {
				Element strokes = (Element) i.next();

				// Entering in each the stroke field using an Iterator
				for (Iterator<?> j = strokes.elementIterator("stroke"); j
						.hasNext();) {
					Element stroke = (Element) j.next();

					long time = Long.parseLong(stroke.attributeValue("Time"));
					long x = Long.parseLong(stroke.attributeValue("X")) + X_MARGIN_CORRECTION;
					long y = Long.parseLong(stroke.attributeValue("Y")) + Y_MARGIN_CORRECTION;

					Stroke s = new Stroke(id, time, x / 10, y / 10);

					// Adding the stroke to the data structure
					points.add(s);
				}

				// id represents a continuous writing in contact with the paper,
				// delimited by the smartpen's events penDown and penUp
				id++;
			}

		} catch (DocumentException | MalformedURLException e) {
		}

		return points;
	}

	/**
	 * Gets the time from the file when the first stroke was made
	 * 
	 * @param filePath
	 *            - The system path from the file containing the points in XML
	 *            format
	 * @return The time when the writing started
	 */
	public long getStartTime(String filePath) {
		long startTime = 0;
		Document document = null;

		try {
			document = XMLInterpreter.getInstance().parse(
					new File(filePath).toURI().toURL());
		} catch (MalformedURLException | DocumentException ex) {
			Logger.getLogger(RetrieveStrokes.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		Iterator<Element> it = document.getRootElement().elementIterator(
				"strokes");

		Element element = (Element) it.next();

		startTime = Long.parseLong(element.attributeValue("start"));

		return startTime;

	}
}
