package Facade;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JScrollPane;



public class CommandOutput extends JPanel implements TrainObserver{
	
	private String output = "";
	private JTextArea textArea;
	private TrainFacadeSingleton tfs = TrainFacadeSingleton.getTfs();
	public CommandOutput() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblOutput = new JLabel("Output");
		GridBagConstraints gbc_lblOutput = new GridBagConstraints();
		gbc_lblOutput.insets = new Insets(0, 0, 0, 5);
		gbc_lblOutput.gridx = 0;
		gbc_lblOutput.gridy = 0;
		add(lblOutput, gbc_lblOutput);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		scrollPane.setViewportView(textArea);
		update();
	}
	public void addText(String s) {
		output = s + "\n"+ output;
		//output + "\n" + s;
		textArea.setText(output);
	}
	@Override
	public void update() {
		try {
			String i = "";
			textArea.removeAll();
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
			
			for(String s: tfs.getCommandoutputs()) {
				i = i + s + "\n";
			}
			textArea.setText("\n" + i);
		} catch (Exception e) {

		}
	}
	

}
