
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class RichRailUI extends javax.swing.JFrame implements ActionListener{
	private JScrollPane jPanel1;
	private JTextPane tpTextTrain;
	private JPanel jPanel2;
	private JPanel drawPanel;
	private RichRailController controller = new RichRailController(this);
	private JTextField tfCommand;
	private JTextArea output;
	private JTextArea trainList;
	
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				RichRailUI inst = new RichRailUI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public RichRailUI() 
	{
		super();
		initGUI();
	}
	
	private void initGUI() 
	{
		try 
		{
			this.setTitle("RichRail");
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {7, 7, 7, 7};
			getContentPane().setLayout(thisLayout);
			{
				{
					drawPanel = new JPanel();
					drawPanel.setBackground(Color.WHITE);
					jPanel1 = new JScrollPane(drawPanel);
					//TODO: Waar zijn de scrollbars dan????
				}
				getContentPane().add(jPanel1, new GridBagConstraints(0, 0, 4, 2, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jPanel2 = new JPanel();
				GridBagLayout jPanel2Layout = new GridBagLayout();
				jPanel2.setLayout(jPanel2Layout);
				getContentPane().add(jPanel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					tpTextTrain = new JTextPane();
					jPanel2.add(tpTextTrain, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					tpTextTrain.setText("command");
					jPanel2Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
					jPanel2Layout.rowHeights = new int[] {7, 7, 7, 7};
					jPanel2Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
					jPanel2Layout.columnWidths = new int[] {7, 7, 7, 7};
					
					JTextPane tpOutput = new JTextPane();
					jPanel2.add(tpOutput, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					tpOutput.setText("output");
					
					tfCommand = new JTextField(15);
					jPanel2.add(tfCommand, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				
					
					JButton bExecute = new JButton("execute");
					jPanel2.add(bExecute, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					bExecute.addActionListener(this);
					
					trainList = new JTextArea(15, 15);
					jPanel2.add(getTrainList(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					
					output = new JTextArea(15, 15);
					getOutput().setBackground(Color.black);
					getOutput().setSelectedTextColor(Color.white);
					jPanel2.add(getOutput(), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				}
			}
			setSize(800, 600);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		//controller.read(); doesn't work properly
	}

	public void actionPerformed(ActionEvent event)
	{
		controller.execute(tfCommand.getText());
	}

	public void setOutput(String s) {
		String a = output.getText();
		a += "\n" + s;
		output.setText(a);
	}

	public JTextArea getOutput() {
		return output;
	}

	public void setTrainList(String s) {
		trainList.setText(s);
	}

	public JTextArea getTrainList() {
		return trainList;
	}

	public void draw(ArrayList<String> trains) {
		int currentTrain = -1;
		int OFFSET = 30;
		int TRAINLENGTH = 100;
		int width1 = 20;
		int height1 = 20;
		int width2 = 40;
		int height2 = 40;
		Graphics g = drawPanel.getGraphics();
		g.clearRect(0,0,1000,1000);
		for(String s : trains){
			String[] train = s.split("-");
			int i = train.length;
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(30,80+currentTrain*OFFSET,80,40);
			g.fillRect(80,60+currentTrain*OFFSET,30,30);
			g.drawRoundRect(85, 40+currentTrain*OFFSET, width1, height1, width1, height1);
			g.drawRoundRect(85, currentTrain*OFFSET, width2, height2, width2, height2);
			g.setColor(Color.BLACK);
			g.fillRoundRect(35, 120+currentTrain*OFFSET, width1, height1, width1, height1);
			g.fillRoundRect(80, 120+currentTrain*OFFSET, width1, height1, width1, height1);
			g.drawString(train[0],40,105+currentTrain*OFFSET);
			//w = wagon number
			for(int w = 1; w < i; w++){
				Graphics gr = drawPanel.getGraphics();
				gr.setColor(Color.LIGHT_GRAY);
				gr.fillRect(30+w*TRAINLENGTH,80+currentTrain*OFFSET,80,40);
				gr.setColor(Color.BLACK);
				gr.fillRoundRect(35+w*TRAINLENGTH, 120+currentTrain*OFFSET, width1, height1, width1, height1);
				gr.fillRoundRect(80+w*TRAINLENGTH, 120+currentTrain*OFFSET, width1, height1, width1, height1);
				gr.drawString(train[w],40+w*TRAINLENGTH,105+currentTrain*OFFSET);
			}
			currentTrain += 4;
		}
	}
}
