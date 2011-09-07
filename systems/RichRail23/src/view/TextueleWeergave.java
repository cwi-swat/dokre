package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Textuele representatie van de grafische weergave. Het representeert de textuele weergave
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 04/03/2009
 */

public class TextueleWeergave
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Textveld dat de basis is voor de textuele weergave.
	 */
	private JTextArea textueleweergave;
	
	/**
	 * Textuele weergave in de vorm van JPanel.
	 */
	private JPanel textueleWeergavePanel;
	
	private JScrollPane scrollPane;
	
	/**
	 * Maakt en koppeld de textuele weergave aan het paneel
	 */
	public TextueleWeergave()
	{
		System.out.println(this.getClass().getSimpleName()+ "Constructor");
		
		System.out.println(this.getClass().getSimpleName()+ ".creerGui()");
		creerGui();
		
		System.out.println(this.getClass().getSimpleName()+ ".paneelKoppelen()");
		paneelKoppelen();
	}

	/**
	 * Bouwt de Gui op voor het textuele gedeelte.
	 */
	private void creerGui() 
	{
		System.out.println(this.getClass().getSimpleName()+ ".creerGui - begin");
		
		textueleweergave = new JTextArea("Textuele Weergave:");
        textueleweergave.setPreferredSize(new Dimension(380, 2000));
        textueleweergave.setEditable(false);
        
        scrollPane = new JScrollPane(textueleweergave);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        System.out.println(this.getClass().getSimpleName()+ ".creerGui - einde");
	}
	
	/**
	 * Koppelt de textweergave aan het paneel.
	 */
	private void paneelKoppelen()
	{
		System.out.println(this.getClass().getSimpleName()+ ".paneelKoppel - begin");
		
		textueleWeergavePanel = new JPanel();
	    textueleWeergavePanel.add(scrollPane);
	    
	    System.out.println(this.getClass().getSimpleName()+ ".paneelKoppel - einde");
	}
	
	/**
	 * Geeft de textuele weergave
	 * @return
	 */
	public JPanel geefTextueleWeergave()
	{
		System.out.println(this.getClass().getSimpleName()+ ".geefTextueleWeergave");
		
		return textueleWeergavePanel;
	}
	
	/**
	 * Deze methode schrijft een string weg in het scherm.
	 * @param wagon String van alle wagons.
	 * @param trein String van alle treinen.
	 */
	public void schrijf(String wagon, String trein)
	{
		System.out.println(this.getClass().getSimpleName()+ ".draw");
		
		if(!wagon.equals(""))
		{
			textueleweergave.append(wagon);
		}
		if(!trein.equals(""))
		{
			textueleweergave.append(trein);
		}	
	}
	
	/**
	 * Deze methode maakt het scherm weer leeg.
	 */
	public void maakSchermLeeg()
	{
		textueleweergave.setText("Textuele Weergave:");
	}
}