package logic;

import java.io.FileWriter;
import java.io.IOException;

public class LogWriter extends Writer
{
	private String filePath = "log.rtf";
	
	public LogWriter()
	{
	}
	
	public LogWriter(String filePath)
	{
		this.filePath = filePath;
	}
	
	@Override
	public void write(String text) {
		try {
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(text + "\n");
			fileWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file.");
		}
		
	}
	
}