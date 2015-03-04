package edu.ramos.ramosink.control;

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

public class RetrieveStrokes {

    public static RetrieveStrokes instance;

    private RetrieveStrokes() {
    }

    /**
     * Singleton method
     * @return The instance of the global object
     */
    public static RetrieveStrokes getInstance() {
        if (instance == null) {
            instance = new RetrieveStrokes();
        }
        return instance;
    }

    /**
     * Method to save the output XML from the Smartpen in a Stroke-type ArrayList
     * @param filePath - The system path from the file containing the points in XML format
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
                    long x = Long.parseLong(stroke.attributeValue("X"));
                    long y = Long.parseLong(stroke.attributeValue("Y"));

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
     * @param filePath - The system path from the file containing the points in XML format
     * @return The time when the writing started
     */
    public long getStartTime(String filePath) {
        long startTime = 0;
        Document document = null;
        
        try {
            document = XMLInterpreter.getInstance().parse(
                    new File(filePath).toURI().toURL());
        } catch (MalformedURLException | DocumentException ex) {
            Logger.getLogger(RetrieveStrokes.class.getName()).log(Level.SEVERE, null, ex);
        }

        Iterator<Element> it = document.getRootElement().elementIterator("strokes");
        
        Element element = (Element) it.next();
        
        startTime = Long.parseLong(element.attributeValue("start"));
       
        return startTime;

    }
}
