package richrail.commandline;

import java.awt.GridLayout;

import javax.swing.JFrame;

import richrail.gui.OutputPanel;


@SuppressWarnings("serial")
public class CommandLineGui extends JFrame{
	
	public ConsoleDisplay displaypanel;
	public OutputPanel outputpanel;
	public InputPanel inputpanel;
	
	public CommandLineGui(){
		
		this.setLayout(new GridLayout(2,2));
		
		setSize(800, 400);
		setTitle("RichRail Commandline");
		
		displaypanel = new ConsoleDisplay();
		outputpanel = new OutputPanel();
		inputpanel = new InputPanel();
		
		displaypanel.setSize(400, 200);
		outputpanel.setSize(400, 200);
		inputpanel.setSize(800, 400);
		
		this.add(displaypanel);
		this.add(outputpanel);
		this.add(inputpanel);
		
	}
	
	public void setDelegate(CommandLineDelegate delegate) {
		this.inputpanel.setDelegate(delegate);
	}
	
	public void outputMessage(String message){
		this.outputpanel.append(message);
	}
}
