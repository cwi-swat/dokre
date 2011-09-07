package GUI.input;

import interpreter.DSLmessageHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;



public class GUIfileInput extends GUIinput{
	private static final long serialVersionUID = 1293815364539883201L;
	public static final String inputFile = "data.xml";
	DSLmessageHandler handle;

	public GUIfileInput(DSLmessageHandler handle){
		this.handle = handle;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		InputStream in;
		try {
			in = new FileInputStream(GUIfileInput.inputFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			while(eventReader.hasNext()){
				XMLEvent event = eventReader.nextEvent();
				if(event.isStartElement()){
					StartElement elem = event.asStartElement();
					String name = elem.getName().getLocalPart();
					if(name.equalsIgnoreCase("wagontype")){
						this.addWagonType(elem);
					}
					if(name.equalsIgnoreCase("train")){
						this.addTrain(eventReader, elem);
					}
				}
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (XMLStreamException e) {		
			e.printStackTrace();
		}
	}

	private void addWagonType(StartElement elem){
		String name = "";
		int numSeats = 0;
		@SuppressWarnings("unchecked")
		Iterator<Attribute> attributes = elem.getAttributes();
		while(attributes.hasNext()){
			Attribute attribute = attributes.next();
			if(attribute.getName().toString().equalsIgnoreCase("name")){
				name = attribute.getValue();
			}
			if(attribute.getName().toString().equalsIgnoreCase("seats")){
				try{
					numSeats = Integer.parseInt(attribute.getValue());
				} catch(Exception e){}
			}
		}
		if(name != "" && numSeats != 0){
			handle.parseInput("new wagon " + name + " (numseats " + numSeats + ")");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addTrain(XMLEventReader reader, StartElement elem) throws XMLStreamException{
		String name = "";
		Iterator<Attribute> attributes = elem.getAttributes();
		while(attributes.hasNext()){
			Attribute attribute = attributes.next();
			if(attribute.getName().toString().equalsIgnoreCase("name")){
				name = attribute.getValue();
			}
		}

		if(name == ""){
			return;
		}
		handle.parseInput("new train " + name);	
		String trainName = name;

		// loop through the next elements
		while(reader.hasNext()){
			XMLEvent event = reader.peek();
			if(event.isStartElement()){
				// wagon
				StartElement wagon = event.asStartElement();
				if(!wagon.getName().getLocalPart().equalsIgnoreCase("wagon")){
					return;
				}
				reader.nextEvent();
				attributes = wagon.getAttributes();
				name = "";
				while(attributes.hasNext()){
					Attribute att = attributes.next();
					if(att.getName().toString().equalsIgnoreCase("name")){
						name = att.getValue();
					}
				}
				if(name != ""){
					handle.parseInput("add " + name + " to " + trainName);
				}
				boolean endFound = false;
				while(reader.hasNext() && !endFound){
					event = reader.nextEvent();
					if(event.isEndElement()) endFound = true;
				}
			} else if(event.isEndElement()){
				return;
			} else {
				reader.nextEvent();
			}
		}
		return;
	}
}