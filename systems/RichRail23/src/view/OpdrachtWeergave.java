package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 *
 */
public class OpdrachtWeergave
{
	
	/**
	 * Opdrachtregel waarmee commando's uitgevoerd kunnen worden.
	 */
	public JTextField opdrachtregel;
	
	/**
	 * Button om het ingevoerde opdracht uit te voeren.
	 */
	private JButton btn_uitvoeren;
	
	/**
	 * 
	 */
	private JLabel lbl_opdracht;
	
	/**
	 * 
	 */
	private JPanel opdrachtWeergavePanel;
	
	/**
	 * Deze methode maakt de gui en koppeld het paneel.
	 */
	public OpdrachtWeergave()
	{
		System.out.println(this.getClass().getSimpleName()+ ".Constructor");
		
		System.out.println(this.getClass().getSimpleName()+ ".CreerGui()");
		creerGui();
		
		System.out.println(this.getClass().getSimpleName()+ ".paneelKoppel");
		paneelKoppel();
	}

	/**
	 * Deze methode maakt de GUI.
	 */
	private void creerGui()
	{
		System.out.println(this.getClass().getSimpleName()+ ".CreerGui - begin");
		
		opdrachtWeergavePanel = new JPanel();
        lbl_opdracht = new JLabel("Uw commando:");
        lbl_opdracht.setPreferredSize(new Dimension(150, 30));
        
        opdrachtregel = new JTextField("");
        opdrachtregel.setName("opdrachtregel");
        opdrachtregel.setPreferredSize(new Dimension(400, 30));
        
        btn_uitvoeren = new JButton("Uitvoeren");
        btn_uitvoeren.setPreferredSize(new Dimension(250, 30));
       
        System.out.println(this.getClass().getSimpleName()+ ".CreerGui - einde");
	}
	
	/**
	 * Deze methode maakt de panels.
	 */
	private void paneelKoppel()
	{
		System.out.println(this.getClass().getSimpleName()+ ".paneelKoppel - begin");
		
		opdrachtWeergavePanel.add(lbl_opdracht);
        opdrachtWeergavePanel.add(opdrachtregel);
        opdrachtWeergavePanel.add(btn_uitvoeren);
        
        System.out.println(this.getClass().getSimpleName()+ ".paneelKoppel - einde");
	}
	
	/**
	 * Deze methode geeft de opdrachtweergave terug.
	 * @return opdrachtWeergavePanel Het opdracht weergave panel.
	 */
	public JPanel geefOpdrachtWeergave()
	{
		System.out.println(this.getClass().getSimpleName()+ ".geefOpdrachtWeergave");
		
		return opdrachtWeergavePanel;
	}
	
	/**
	 * 
	 * @return
	 */
	public String geefOpdrachtRegelText()
	{
		System.out.println(this.getClass().getSimpleName()+ ".CreerGui - begin");
		System.out.println(this.getClass().getSimpleName()+ ".geefOpdrachtRegelText met waarde: " + opdrachtregel.getText());
		
		return opdrachtregel.getText();
	}
	
	/**
	 * 
	 * @param al
	 */
	public void koppelActionListener(Object al) 
	{
		System.out.println(this.getClass().getSimpleName()+ ".CreerGui - begin");
		
		//TODO misschien onderstaande syso aanpassen.
		System.out.println(this.getClass().getSimpleName()+ ".koppelActionListener(\""+al.getClass().getSimpleName()+"\")");
		
		btn_uitvoeren.addActionListener((ActionListener)al);
		
		System.out.println(this.getClass().getSimpleName()+ ".koppelActionListener - begin");
	}
	
	/**
	 * Deze methode maakt de opdrachtregel weer leeg.
	 */
	public void maakTekstRegelLeeg()
	{
		opdrachtregel.setText("");
	}

}
