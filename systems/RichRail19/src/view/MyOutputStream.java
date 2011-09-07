package view;

import java.io.IOException;
import java.io.OutputStream;



public class MyOutputStream extends OutputStream{

	private MainFrame venster = null;

	
	public MyOutputStream(MainFrame guif){
		//System.out.println("MyOutputStream()");
		this.venster = guif;
	}

	public void write(int b) throws IOException{
		venster.setLog("" + (char)b);
	}
}
