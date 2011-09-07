package Facade;
import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class LogView extends JFrame implements TrainObserver{
	private JTextArea textArea;
	private String lastText ="";
	private TrainFacadeSingleton tfs = TrainFacadeSingleton.getTfs();
	private Date c = Calendar.getInstance().getTime();

	public LogView() {

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addContainerGap())
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
						.addContainerGap())
		);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(""+Calendar.getInstance().getTime());
		scrollPane.setViewportView(textArea);
		getContentPane().setLayout(groupLayout);
		setSize(500,500);
		update();

	}
	@Override
	public void update() {
		try {
			String i = "";
			textArea.removeAll();
			textArea.setText("" + c);
			/*
			String i = tfs.getLogs().get(tfs.getLogs().size()-1);
			if(!(i.equals(lastText))) {
				Calendar cal = Calendar.getInstance();
				String s = textArea.getText();
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				s += "\n" + sdf.format(cal.getTime()) + ": " + tfs.getLogs().get(tfs.getLogs().size()-1);
				textArea.setText(s);
				
			}
			lastText = i; */
			
			for(String s: tfs.getLogs()) {
				i = i + s + "\n";
			}
			textArea.setText(textArea.getText() + "\n" + i);
		} catch (Exception e) {

		}
	}
}
