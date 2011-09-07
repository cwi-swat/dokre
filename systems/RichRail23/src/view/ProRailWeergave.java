package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 */
public class ProRailWeergave extends JFrame
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ResultatenWeergave resultaatweergave;
	
	OpdrachtWeergave opdrachtweergave;
	
	GrafischeWeergave grafischeweergave;
	
	TextueleWeergave textueleweergave;
	
	/**
	 * Deze methode initialiseerd de prorailweergave.
	 */
	public ProRailWeergave()
	{
		System.out.println(this.getClass().getSimpleName()+ ".Constructor");
		
		System.out.println(this.getClass().getSimpleName()+ ".creerGui()");
		creerGUI();
	}

	/**
	 * Bouwt de Grafische Gebruikers Interface op.
	 */
	private void creerGUI()
	{
		System.out.println(this.getClass().getSimpleName()+ ".creerGui - begin");
        //Creer en zet de window.
        JFrame frame = new JFrame("ProRail");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        grafischeweergave = new GrafischeWeergave();
        textueleweergave = new TextueleWeergave();
        resultaatweergave = new ResultatenWeergave();
        opdrachtweergave = new OpdrachtWeergave();
        
        frame.getContentPane().add((JPanel)grafischeweergave.geefGrafischeWeergave(), BorderLayout.NORTH);
        frame.getContentPane().add((JPanel)textueleweergave.geefTextueleWeergave(), BorderLayout.WEST);
        frame.getContentPane().add((JPanel)resultaatweergave.geefResultatenWeergave(), BorderLayout.EAST);
        frame.getContentPane().add((JPanel)opdrachtweergave.geefOpdrachtWeergave(), BorderLayout.SOUTH);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
         
        
        System.out.println(this.getClass().getSimpleName()+ ".creerGui - einde");
    }
	
	/**
	 * Voeg een regel resultaat toe aan de huidige resultaten.
	 * @param newResultatenRegel resultaat regel
	 */
	public void voegResultaatRegelToe(String newResultatenRegel)
	{
		System.out.println(this.getClass().getSimpleName()+ ".voegResultaatRegelToe - begin");
		
		System.out.println(this.getClass().getSimpleName()+ ".voegResultaatRegelToe - inkomende waarde: " + newResultatenRegel);
		// TODO Input controle?
		
		resultaatweergave.voegResultaatToe(newResultatenRegel);
		opdrachtweergave.opdrachtregel.requestFocus();
		System.out.println(this.getClass().getSimpleName()+ ".voegResultaatRegelToe - einde");
	}

	/**
	 * Methode geeft de actie door aan de Opdrachtweergave
	 * @param al Object dat gekoppeld wordt met de actionListener.
	 */
	public void koppelActionListener(Object al) 
	{
		System.out.println(this.getClass().getSimpleName()+ ".koppelActionListener - begin");
		
		System.out.println("GenView.koppelActionListener(\""+al.getClass().getSimpleName()+"\")");
		
		opdrachtweergave.koppelActionListener(al);
		
		System.out.println(this.getClass().getSimpleName()+ ".koppelActionListener - einde");
	}
	
	/**
	 * Deze methode geeft de opdracht regel tekst.
	 * @return
	 */
	public String geefOpdrachtRegelTekst()
	{
		System.out.println(this.getClass().getSimpleName()+ ".geefOpdrachtRegelTekst()");
		String tekst = "";
		tekst = opdrachtweergave.opdrachtregel.getText();
		
		System.out.println(this.getClass().getSimpleName()+ " opgehaalde waarde: " + tekst);
	
		opdrachtweergave.maakTekstRegelLeeg();
		
		return tekst;
	}
	
	/**
	 * Deze methode maakt het scherm weer leeg.
	 */
	public void maakSchermLeeg()
	{
		grafischeweergave.maakSchermLeeg();
	}
	
	/**
	 * Deze methode tekent de trein in de grafische weergave.
	 * @param trein Het trein ID
	 * @param treinnummer Het trein nummer.
	 */
	public void tekenTrein(String trein, int treinnummer)
	{
		System.out.println(this.getClass().getSimpleName()+ ".tekenTrein()");
		grafischeweergave.tekenTrein(trein, treinnummer);
	}
	
	/**
	 * Deze methode tekent de wagon
	 * @param wagon Het wagon id.
	 * @param wagonnummer Het wagon nummer.
	 * @param treinnummer Het trein nummer.
	 */
	public void tekenWagon(String wagon, int wagonnummer, int treinnummer)
	{
		System.out.println(this.getClass().getSimpleName()+ ".tekenWagon()");
		grafischeweergave.tekenWagon(wagon, wagonnummer, treinnummer);
	}
	
	/**
	 * Deze methode maakt de tekst in de textuele weergave.
	 * @param wagon String van wagons.
	 * @param trein String van treinen.
	 */
	public void creerTekst(String wagon, String trein)
	{
		textueleweergave.maakSchermLeeg();
		textueleweergave.schrijf(wagon, trein);
	}
}