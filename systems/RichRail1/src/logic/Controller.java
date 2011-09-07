package logic;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import domain.AddWagonToTrain;
import domain.Command;
import domain.CreateEndWagon;
import domain.CreateTrain;
import domain.CreateWagon;
import domain.CreateWagonSeats;
import domain.DeleteTrain;
import domain.DeleteWagon;
import domain.RemoveWagonFromTrain;
import domain.Seats;
import domain.Train;
import domain.Wagon;
import logic.Writer;

import ui.View;

public class Controller implements Observer {
	private ArrayList<View> views;
	private ArrayList<Train> trains = new ArrayList<Train>();
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	private ArrayList<Command> commands;
	private ArrayList<String> logcommands;
	private ArrayList<Writer> writers;

	public Controller() {
		this.views = new ArrayList<View>();
		this.commands = new ArrayList<Command>();
		this.writers = new ArrayList<Writer>();
		this.setLogcommands(new ArrayList<String>());
	}

	public Controller(ArrayList<Command> commands) {
		this();
		this.commands = commands;
	}

	public ArrayList<View> getViews() {
		return views;
	}

	public ArrayList<Train> getTrains() {
		return trains;
	}

	public ArrayList<Wagon> getWagons() {
		return wagons;
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}

	public ArrayList<Writer> getWriters() {
		return writers;
	}

	public ArrayList<String> getLogcommands() {
		return logcommands;
	}
	
	public void setLogcommands(ArrayList<String> logcommands) {
		this.logcommands = logcommands;
	}

	public void setViews(ArrayList<View> views) {
		this.views = views;
	}

	public void setTrains(ArrayList<Train> trains) {
		this.trains = trains;
	}

	public void setWagons(ArrayList<Wagon> wagons) {
		this.wagons = wagons;
	}

	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}

	public void setWriters(ArrayList<Writer> writers) {
		this.writers = writers;
	}

	public void addView(View view) {
		views.add(view);
	}

	public void removeView(View view) {
		if (views.contains(view)) {
			views.remove(view);
		}
	}

	public void addTrain(Train train) {
		trains.add(train);
		update();
	}

	public void removeTrain(Train train) {
		if (trains.contains(train)) {
			trains.remove(train);
			update();
		}
	}

	public void addWagon(Wagon wagon) {
		wagons.add(wagon);
		update();
	}

	public void removeWagon(Wagon wagon) {
		if (wagons.contains(wagon)) {
			wagons.remove(wagon);
			update();
		}
	}

	public void addWriter(Writer writer) {
		writers.add(writer);
	}

	public void removeWriter(Writer writer) {
		if (writers.contains(writer)) {
			writers.remove(writer);
		}
	}
	
	public void addLogcommand(String logcommand) {
		logcommands.add(logcommand);
	}
	
	public void removeLogcommand(String logcommand) {
		if (logcommands.contains(logcommand)) {
			logcommands.remove(logcommand);
		}
	}

		public void split(String commandString) {
			if (writers != null && writers.size() > 0) {
				for (Writer writer : writers) {
					writer.write(commandString);
				}
			}
			String[] word = commandString.split(" ");
			if (word[0].equals("new") || word[0].equals("add") || word[0].equals("remove")
					|| word[0].equals("delete") || word[0].equals("getnumseats")) {
				if (commandString.substring(commandString.length() - 1).equals(";")) {
					if (word[0].equals("new")) {
						if (word[1].equals("train")) {
							CreateTrain c = new CreateTrain();
							c.setTrain(word[2].substring(0, word[2].length() - 1));
							c.execute(this);
						}
	
						else {
							if (word[1].equals("wagon")) {
								try {
									if (word[3].equals("numseats")) {
										CreateWagonSeats command = new CreateWagonSeats();
										command.setWagon(word[2]);
										command.setSeats(Integer.parseInt(word[4].substring(0, word[4].length() - 1)));
										command.execute(this);
									}
								}
								catch(Exception e){
									CreateWagon command = new CreateWagon();
									command.setWagon(word[2].substring(0, word[2].length() - 1));
									command.execute(this);
								}
							}
							
							else if (word[1].equals("endwagon")) {
								CreateEndWagon command = new CreateEndWagon();
								command.setWagon(word[2].substring(0, word[2].length() - 1));
								command.execute(this);
							}
						}
					}
	
					else if (word[0].equals("getnumseats")) {
						Seats command = new Seats();
						if (word[1].equals("wagon")) {
							command.setWagon(word[2].substring(0, word[2].length() - 1));
							command.execute(this);
						} 
						else if (word[1].equals("train")) {
							command.setTrain(word[2].substring(0, word[2].length() - 1));
							command.execute(this);
						}
					} else if (word[0].equals("add")) {
						AddWagonToTrain command = new AddWagonToTrain();
						command.setWagon(word[1]);
						command.setTrain(word[3].substring(0, word[3].length() - 1));
						command.execute(this);
					} else if (word[0].equals("delete")) {
						if (word[1].equals("train")) {
							DeleteTrain command = new DeleteTrain();
							command.setTrain(word[2].substring(0, word[2].length() - 1));
							command.execute(this);
						} else if (word[1].equals("wagon")) {
							DeleteWagon command = new DeleteWagon();
							command.setWagon(word[2].substring(0, word[2].length() - 1));
							command.execute(this);
						}
					} else if (word[0].equals("remove")) {
						RemoveWagonFromTrain command = new RemoveWagonFromTrain();
						command.setWagon(word[1]);
						command.setTrain(word[3].substring(0, word[3].length() - 1));
						command.execute(this);
	
					}
				} else
					JOptionPane.showMessageDialog(null,
							"Invalid command. Missing ; at the end");
	
			} else
				JOptionPane
						.showMessageDialog(null,
								"Invalid command. Known commands are: add,remove,delete,getnumseats ");
		}

	@Override
	public void update() {
		for (View view : views) {
			view.update(this);
		}
	}
}
