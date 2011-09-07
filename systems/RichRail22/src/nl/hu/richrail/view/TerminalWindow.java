package nl.hu.richrail.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TerminalWindow extends JInternalFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6540648171817067823L;
	private JButton jbSend;
	public final JTextArea jtaResult;
	public final JTextField jtCommand;
	
	
	public TerminalWindow(ActionListener commandbarListener)
	{
		super("RollingStock Terminal", true, true, true);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel cmdBar = new JPanel();
		cmdBar.setLayout(new BorderLayout());
		
		jbSend = new JButton("send");
		jbSend.addActionListener(commandbarListener);
		cmdBar.add(jbSend,BorderLayout.EAST);
		
		jtCommand = new JTextField();
		jtCommand.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER) {
					for(ActionListener a : jbSend.getActionListeners())
						a.actionPerformed(new ActionEvent(jtCommand,1,"enter"));
				}
			}});
		
		cmdBar.add(jtCommand,BorderLayout.CENTER);
		
		c.add(cmdBar,BorderLayout.SOUTH);
		
		jtaResult = new JTextArea();
		jtaResult.setEditable(false);
		jtaResult.setBackground(Color.BLACK);
		jtaResult.setForeground(Color.GREEN);
		
		c.add(jtaResult,BorderLayout.CENTER);
		
		setSize(new Dimension(640,480));
		setVisible(true);
	}

}
