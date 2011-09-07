import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {
	private String s = "";

	public Log(){
	}

	public void add(String cmd) throws IOException {
		FileWriter f0 = new FileWriter("log.txt"); 
		s += "   -   " + cmd;
		f0.write(s);
		f0.close();
	}
}
