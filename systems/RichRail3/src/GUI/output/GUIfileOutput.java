package GUI.output;

import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.stream.*;
import javax.xml.stream.events.*;

import datahandler.DataHandler;
import domain.Train;
import domain.Wagon;
import domain.WagonType;

public class GUIfileOutput extends GUIoutput {
	private static final long serialVersionUID = 4370367775674733829L;
	private static final String outputFile = "data.xml"; 

	private XMLEvent newLine;
	private XMLEvent tab;
	private XMLEventFactory eventFactory;
	private XMLOutputFactory outputFactory;
	private XMLEventWriter eventWriter;
	public void notifyOutput(DataHandler handle) {
		try{
			// Create a XMLOutputFactory
			outputFactory = XMLOutputFactory.newInstance();

			// Create XMLEventWriter
			eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(outputFile));

			// Create a EventFactory
			eventFactory = XMLEventFactory.newInstance();
			newLine = eventFactory.createDTD("\n");
			tab = eventFactory.createDTD("\t");

			// Create and write Start Tag
			StartDocument startDocument = eventFactory.createStartDocument();
			eventWriter.add(startDocument);
			eventWriter.add(newLine);

			// Create config open tag
			eventWriter.add(eventFactory.createStartElement("", "", "richrail"));
			eventWriter.add(newLine);

			ArrayList<WagonType> types = handle.getAllWagonTypes();

			for(int i = 0; i < types.size(); i++){
				writeWagon(eventWriter, types.get(i));
			}

			ArrayList<Train> trains = handle.getAllTrains();

			for(int i = 0; i < trains.size(); i++){
				writeTrain(eventWriter, trains.get(i));
			}

			eventWriter.add(eventFactory.createEndElement("", "", "richrail"));
			eventWriter.add(newLine);
			eventWriter.add(eventFactory.createEndDocument());
			eventWriter.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private void writeTrain(XMLEventWriter writer, Train t) throws XMLStreamException{
		writer.add(tab);
		writer.add(eventFactory.createStartElement("","","train"));
		writer.add(eventFactory.createAttribute("name", t.getName()));
		ArrayList<Wagon> wagons = t.getWagons();
		if(wagons.size() != 0)
			writer.add(newLine);
		for(int i = 0; i < wagons.size(); i++){
			writer.add(tab); writer.add(tab);
			writer.add(eventFactory.createStartElement("","","wagon"));
			writer.add(eventFactory.createAttribute("name", wagons.get(i).getType().getName()));
			writer.add(eventFactory.createEndElement("","","wagon"));
			writer.add(newLine);
		}
		if(wagons.size() != 0)
			writer.add(tab);
		writer.add(eventFactory.createEndElement("","","train"));
		writer.add(newLine);
	}

	private void writeWagon(XMLEventWriter writer, WagonType type) throws XMLStreamException{
		writer.add(tab);
		writer.add(eventFactory.createStartElement("", "", "wagontype"));
		writer.add(eventFactory.createAttribute("name", type.getName()));
		writer.add(eventFactory.createAttribute("seats", String.valueOf(type.getSeats())));
		writer.add(eventFactory.createEndElement("","","wagontype"));
		writer.add(newLine);
	}
}