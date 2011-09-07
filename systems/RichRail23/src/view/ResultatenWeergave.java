package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Representeert de resultaten
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 04/03/2009
 * 
 */
public class ResultatenWeergave
{

	/**
	 * Resultaten weergave
	 */
	private JTextArea resultatenweergave;
	
	/**
	 * 
	 */
	private JPanel resultatenWeergavePanel;
	
	private JScrollPane scrollPane;
	
	/**
	 * Deze methode maakt de resultatenweergave.
	 */
	public ResultatenWeergave()
	{
		System.out.println(this.getClass().getSimpleName()+ "Constructor");

		System.out.println(this.getClass().getSimpleName()+ ".creerGUI()");
		creerGUI();
		
		System.out.println(this.getClass().getSimpleName()+ ".paneelKoppel()");
		paneelKoppel();
	}

	/**
	 * Deze methode maakt de GUI.
	 */
	private void creerGUI()
	{
		System.out.println(this.getClass().getSimpleName()+ ".creerGui - begin");
		
		resultatenweergave = new JTextArea("Resultaten Weergave:");
        resultatenweergave.setPreferredSize(new Dimension(380, 2000));
        resultatenweergave.setBackground(Color.BLACK);
        resultatenweergave.setForeground(Color.WHITE);
        resultatenweergave.setEditable(false);
        
        scrollPane = new JScrollPane(resultatenweergave);
        scrollPane.setPreferredSize(new Dimension(400, 300));
       
        
        System.out.println(this.getClass().getSimpleName()+ ".creerGui - einde");
	}
	
	/**
	 * Deze methode koppeld het paneel.
	 */
	private void paneelKoppel()
	{
		System.out.println(this.getClass().getSimpleName()+ ".paneelKoppel - begin");		
		
		resultatenWeergavePanel = new JPanel();
		resultatenWeergavePanel.add(scrollPane);
		
		System.out.println(this.getClass().getSimpleName()+ ".paneelKoppel - einde");
	}
	
	/**
	 * Deze methode voegt een regel toe aan het resultatenscherm.
	 * @param newResultatenRegel
	 */
	public void voegResultaatToe(String newResultatenRegel)
	{
		System.out.println(this.getClass().getSimpleName()+ ".voegResultaatToe - begin");
		
		System.out.println(this.getClass().getSimpleName()+ " met waarde: " + newResultatenRegel);
		resultatenweergave.append("\n" + newResultatenRegel);
		
		System.out.println(this.getClass().getSimpleName()+ ".voegResultaatToe - einde");
	}
	
	/**
	 * Deze methode geeft het resultatenweergave object.
	 * @return resultatenWeergavePanel het panel van de resultaten weergave.
	 */
	public JPanel geefResultatenWeergave()
	{
		System.out.println(this.getClass().getSimpleName()+ ".geefResultatenWeergave");
		return resultatenWeergavePanel;
	}
}
