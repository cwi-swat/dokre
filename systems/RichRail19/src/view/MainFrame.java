package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MainFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel drawpanel;
	private JPanel logPanel;
	private JPanel inputPanel;
	private TextArea commandView;
	private TextArea logView;
	private JTextField inputfield;
	
	private int currentNumberOfWagons;
	private int currentTrain = -1;
	private int OFFSET = 100;
	private int TRAINLENGTH = 100;
	
	public MainFrame(Container pane, JButton newB, JTextField newInput, JPanel newDrawpanel){

		try{
			pane.setSize(new Dimension(1000,800));
			pane.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			
			drawpanel = newDrawpanel;
			drawpanel.setBackground(Color.white);
			drawpanel.setPreferredSize(new Dimension(1000,300));
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridx = 0;
			gbc.gridy = 0;
			pane.add(drawpanel,gbc);
			
			commandView = new TextArea();
			commandView.setSize(new Dimension(400,400));
			commandView.setEditable(false);
			
			
			logPanel = new JPanel();
			JLabel jview = new JLabel("Output");
			logPanel.add(jview);
			logView = new TextArea();
			logView.setForeground(Color.WHITE);
			logView.setBackground(Color.black);
			logPanel.add(logView);
			
			
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 1;
			pane.add(commandView,gbc);
			gbc.gridx = 1;
			pane.add(logPanel,gbc);
			
			
			
			inputPanel = new JPanel(new FlowLayout(0));
			JLabel linput = new JLabel("command");
			inputfield = newInput;
			JButton bexe = newB;
			
			inputPanel.add(linput);
			inputPanel.add(inputfield);
			inputPanel.add(bexe);
			
			gbc.weightx = 1;
			gbc.gridx = 0;
			gbc.gridy = 3;
			pane.add(inputPanel,gbc);
			
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}
	
	public void setLog(String t){
		logView.append(t);
	}
	
	public String getCommand(){
		return inputfield.getText();
	}
	
	public void setData(String s){
		commandView.setText(s);
	}
	
	public void clear(){
		currentTrain=-1;
		currentNumberOfWagons=0;
		Graphics g = drawpanel.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 1200, 300);
	}
	
	public void drawTrain(String train, int i) 
	{
		currentTrain = i;
		Graphics g = drawpanel.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30,80+currentTrain*OFFSET,80,40);
		g.fillRect(80,60+currentTrain*OFFSET,30,30);
		g.drawRoundRect(85, 40+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawRoundRect(85, currentTrain*OFFSET, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(80, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawString(train,40,105+currentTrain*OFFSET);
	
    }
	
	public void drawWagon(String wagon, int wg, int tr) 
	{
		currentNumberOfWagons = wg;
		currentTrain=tr;
		Graphics g = drawpanel.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30+currentNumberOfWagons*TRAINLENGTH,80+currentTrain*OFFSET,80,40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35+currentNumberOfWagons*TRAINLENGTH, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(80+currentNumberOfWagons*TRAINLENGTH, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawString(wagon,40+currentNumberOfWagons*TRAINLENGTH,105+currentTrain*OFFSET);
	
    }

}
