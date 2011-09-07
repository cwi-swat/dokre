package richrail.command;

import javax.swing.JFrame;

import richrail.domain.ObserverableTrainDepot;
import richrail.tools.Log;
import richrail.view.ImageTrainPanel;
import richrail.view.LogObserverPanel;
import richrail.view.SubFrame;
import richrail.view.TextualDepotPanel;

public class NewViewCommand extends AbstractTrainDepotCommand {

	public NewViewCommand(ObserverableTrainDepot trainDepot) {
		super(trainDepot);
		this.setCommandFormat(NEW+WHITESPACE+VIEW+WHITESPACE+VIEWTYPE);
		this.setUndoable(false);
	}

	@Override
	public void execute() {
		// Command piece 2 is the view type
		if (getCommandPieces()[2].equals("log")) 
		{	
			JFrame frame = new SubFrame(getTrainDepot(), new LogObserverPanel());
			frame.setTitle("RichRail - Log view");
			Log.output(getTrainDepot(), "log view created");	
		} 
		else if (getCommandPieces()[2].equals("textualdepot")) 
		{	
			JFrame frame = new SubFrame(getTrainDepot(), new TextualDepotPanel(getTrainDepot()));
			frame.setTitle("RichRail - Textual depot view");
			Log.output(getTrainDepot(), "textual depot view created");
		} 
		else if (getCommandPieces()[2].equals("imagetrain")) 
		{			
			JFrame frame = new SubFrame(getTrainDepot(), new ImageTrainPanel(getTrainDepot()));
			frame.setTitle("RichRail - Image train view");
			Log.output(getTrainDepot(), "image train view created");	
		}
	}

	@Override
	protected void undo() {}
}
