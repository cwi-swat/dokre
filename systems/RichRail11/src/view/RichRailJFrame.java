package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.UiController;
import controller.output.GraphicDisplay;
import controller.output.MessageLog;
import controller.output.TextLog;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class RichRailJFrame extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5844762997602162584L;
	public JButton jButtonExecute;
	public JButton jButtonDuplicateGraphic;
	private JScrollPane jScrollPane2;
	public JButton jButtonDuplicateText;
	private JScrollPane jScrollPane1;
	private JSplitPane jSplitPane1;
	private JLabel jLabelCmdHistory;
	private JLabel jLabelLog;
	private JTextArea jTextAreaLog;
	private JPanel jPanel7;
	private JPanel jPanelUpperPanel;
	public JTextField jTextFieldCmd;
	private JLabel jLabel1;
	private JPanel jPanel5;
	private JPanel jPanel4;
	private JPanel jPanel3;
	private JPanel jPanelLowerPanel;
	private UiController uiController;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auto-generated main method to display this JFrame
	 * 
	 * @param mainController
	 */

	public RichRailJFrame(UiController newGuiController) {
		super();
		uiController = newGuiController;
	}

	public void initGUI() {
		try {
			setVisible(true);
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("train.gif")).getImage());
			setTitle("Rich Rail");

			{
				jSplitPane1 = new JSplitPane();
				getContentPane().add(jSplitPane1, BorderLayout.CENTER);
				jSplitPane1.setPreferredSize(new java.awt.Dimension(784, 342));
				jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
				jSplitPane1.setDividerLocation(500);
				{
					jPanelUpperPanel = new JPanel();
					jSplitPane1.add(jPanelUpperPanel, JSplitPane.TOP);
					jPanelUpperPanel.add(new GraphicDisplay());
					BorderLayout jPanel1Layout = new BorderLayout();
					jPanelUpperPanel.setLayout(jPanel1Layout);
				}
				{
					jPanelLowerPanel = new JPanel();
					jSplitPane1.add(jPanelLowerPanel, JSplitPane.BOTTOM);
					BorderLayout jPanel2Layout = new BorderLayout();
					jPanelLowerPanel.setLayout(jPanel2Layout);
					jPanelLowerPanel.setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
					{
						jPanel7 = new JPanel();
						GridBagLayout jPanel7Layout = new GridBagLayout();
						jPanel7Layout.rowWeights = new double[] { 0.1 };
						jPanel7Layout.rowHeights = new int[] { 7 };
						jPanel7Layout.columnWeights = new double[] { 0.1, 0.1 };
						jPanel7Layout.columnWidths = new int[] { 7, 7 };
						jPanel7.setLayout(jPanel7Layout);
						jPanelLowerPanel.add(jPanel7, BorderLayout.CENTER);
						{
							jPanel3 = new JPanel();
							jPanel7.add(jPanel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
							BorderLayout jPanel3Layout = new BorderLayout();
							jPanel3.setLayout(jPanel3Layout);
							jPanel3.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
							{
								jLabelCmdHistory = new JLabel();
								jPanel3.add(jLabelCmdHistory, BorderLayout.NORTH);
								jLabelCmdHistory.setText("Current trains and wagons:");
								jLabelCmdHistory.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));
							}
							{
								jScrollPane1 = new JScrollPane();
								jPanel3.add(jScrollPane1, BorderLayout.CENTER);

								{
									TextLog tl = new TextLog();
									jScrollPane1.setViewportView(tl);
								}

							}
						}
						{
							jPanel4 = new JPanel();
							jPanel7.add(jPanel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
							BorderLayout jPanel4Layout = new BorderLayout();
							jPanel4.setLayout(jPanel4Layout);
							jPanel4.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
							{
								jLabelLog = new JLabel();
								jPanel4.add(jLabelLog, BorderLayout.NORTH);
								jLabelLog.setText("Output log:");
								jLabelLog.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));
							}
							{
								jScrollPane2 = new JScrollPane();
								jPanel4.add(jScrollPane2, BorderLayout.CENTER);
								jScrollPane2.setPreferredSize(new java.awt.Dimension(84, 22));
								{
									jTextAreaLog = new JTextArea();
									jScrollPane2.setViewportView(jTextAreaLog);
								}
							}
						}
					}
					{
						jPanel5 = new JPanel();
						jPanelLowerPanel.add(jPanel5, BorderLayout.SOUTH);
						jPanel5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						{
							jLabel1 = new JLabel();
							jPanel5.add(jLabel1);
							jLabel1.setText("Enter command:");
						}
						{
							jTextFieldCmd = new JTextField(50);
							jTextFieldCmd.addActionListener(uiController);
							jPanel5.add(jTextFieldCmd);
						}
						{
							jButtonExecute = new JButton();
							jPanel5.add(jButtonExecute);
							jButtonExecute.setText("Execute");
							jButtonExecute.addActionListener(uiController);
						}
						{
							jButtonDuplicateGraphic = new JButton();
							jPanel5.add(jButtonDuplicateGraphic);
							jButtonDuplicateGraphic.setText("Duplicate graphicdisplay");
							jButtonDuplicateGraphic.addActionListener(uiController);
						}
						{
							jButtonDuplicateText = new JButton();
							jPanel5.add(jButtonDuplicateText);
							jButtonDuplicateText.setText("Duplicate textdisplay");
							jButtonDuplicateText.addActionListener(uiController);
						}
					}
				}
			}

			jTextFieldCmd.requestFocusInWindow();

			pack();
			this.setSize(1000, 800);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	public JTextArea getjTextAreaLog() {
		return jTextAreaLog;
	}

	public JTextField getjTextFieldCmd() {
		return jTextFieldCmd;
	}

	public void setGraphicDisplay(GraphicDisplay defaultDisplay) {
		// Clear the panel
		jPanelUpperPanel.removeAll();
		jPanelUpperPanel.setPreferredSize(defaultDisplay.getPreferredSize());

		// Add the display
		jPanelUpperPanel.add(defaultDisplay);
	}

	public void setTextOutput(TextLog textlog) {
		jScrollPane1.setViewportView(textlog);
	}

	public void setMessageLog(MessageLog messagelog) {
		jScrollPane2.setViewportView(messagelog);
	}
}
