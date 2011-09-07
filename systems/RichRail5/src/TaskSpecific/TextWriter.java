package TaskSpecific;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextWriter extends Writer {
	protected String filePath;

	public TextWriter(String name, String description, String filePath) {
		super(name, description);
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public boolean write() {
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			
//			for(String c : controller.logs)
//			{
//				fileWriter.write(c);
//			}
			fileWriter.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}


}
