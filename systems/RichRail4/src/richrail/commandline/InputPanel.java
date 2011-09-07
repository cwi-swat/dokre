package richrail.commandline;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class InputPanel extends JPanel implements ActionListener, KeyListener{
	
	private JButton execute;
	public JTextField inputfield;
	private CommandLineDelegate delegate;
	
	public InputPanel(){
		
		this.setLayout(new FlowLayout());
		
		inputfield = new JTextField("", 30);
		execute = new JButton("Execute");
		
		this.add(inputfield);
		this.add(execute);
		
	}
	
	public void setDelegate(CommandLineDelegate delegate){
		this.delegate = delegate;
		this.setHandlers();
	}
	
	
	public void setHandlers(){
		execute.addActionListener(this);
		inputfield.addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == execute){
			delegate.execute();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(e.getSource() == inputfield && key == KeyEvent.VK_ENTER){
			delegate.execute();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
