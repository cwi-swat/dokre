package richrail.commandline;

import java.util.ArrayList;
import java.util.List;

public class Command {
	
	private String command;
	private List<String> parameters;

	// Constructor
	Command(String rawdata) {

		// Isolate command
		String[] commandblocks = rawdata.split(" ");
		this.command = commandblocks[0].toUpperCase();
		
		// Init parameters
		this.parameters = new ArrayList<String>();
		
		// Loop through all elements in rawdata, from 1 to length
		int pointer = 0;
		boolean delimiterfound = false;
		
		for(int i = 1; i < commandblocks.length; i++){
			String block = commandblocks[i];
			
			// Delimiter found (only first time), remove the leading ":" 
			if(block.startsWith(":") && !delimiterfound){
				block = block.substring(1);
				delimiterfound = true;
			}
			
			if(!delimiterfound){
				this.parameters.add(block);
				pointer++;
			} else {
				try {
					this.parameters.set(pointer, this.parameters.get(pointer)+" "+block);
				} catch (IndexOutOfBoundsException e){
					this.parameters.add(block);
				}
			}
		}
	}

	// General
	public String getCommand() {
		return this.command;
	}
	
	public String getClassString() {
        String firstLetter = this.command.substring(0,1);  // Get first letter
        String remainder   = this.command.substring(1);    // Get remainder of word.
        String capitalized = firstLetter.toUpperCase() + remainder.toLowerCase();
		return capitalized;
	}
	
	public int getParamsLength(){
		return this.parameters.size();
	}
	
	public String getParamAt(int index){
		return this.parameters.get(index);
	}

}
