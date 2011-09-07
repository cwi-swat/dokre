package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 *
 */
public class GrafischeWeergave
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel grafischeweergave;
	private JPanel grafischeWeergavePanel;
	private JPanel grafischeWeergaveTekenPanel;
	private int huidigeAantalWagons;
	private int huidigeTrein = -1;
	private int OFFSET = 100;
	private int TRAINLENGTH = 100;
	
	/**
	 * Deze methode initialiseerd de grafische weergave.
	 */
	public GrafischeWeergave()
	{
		System.out.println(this.getClass().getSimpleName() + ".Constructor");
		
		System.out.println(this.getClass().getSimpleName()+ ".creerGUI()");
		creerGui();
		
		System.out.println(this.getClass().getSimpleName()+ ".paneelKoppelen()");
		paneelKoppelen();
	}

	/**
	 * Deze methode maakt de GUI.
	 */
	private void creerGui()
	{
		System.out.println(this.getClass().getSimpleName()+ ".creerGui - begin");
		
		grafischeweergave = new JLabel("Grafische weergave:");
        grafischeweergave.setPreferredSize(new Dimension(800, 20));
        
        grafischeWeergaveTekenPanel = new JPanel();
        grafischeWeergaveTekenPanel.setPreferredSize(new Dimension(790, 280));
        
        System.out.println(this.getClass().getSimpleName()+ ".creerGui - einde");
	}
	
	/**
	 * Deze functie koppeld het paneel aan de hoofdview.
	 */
	private void paneelKoppelen()
	{
		System.out.println(this.getClass().getSimpleName()+".paneelKoppelen() - begin");
		
		grafischeWeergavePanel = new JPanel(new BorderLayout());
		grafischeWeergavePanel.add(grafischeweergave, BorderLayout.NORTH);
		grafischeWeergavePanel.add(grafischeWeergaveTekenPanel, BorderLayout.CENTER);
		
		System.out.println(this.getClass().getSimpleName()+".paneelKoppelen() - begin");
	}
	
	/**
	 * Deze functie geeft de grafische weergave terug.
	 * @return grafischeWeergavePanel Het grafische weergave panel.
	 */
	public JPanel geefGrafischeWeergave()
	{
		System.out.println(this.getClass().getSimpleName()+ ".geefGrafischeWeergave");
		
		return grafischeWeergavePanel;
	}
	
	/**
	 * Deze methode tekent een trein.
	 * @param train Het treinID
	 * @param mijnTrein Het nummer van de trein.
	 */
	public void tekenTrein(String train, int mijnTrein) 
	{
		System.out.println(this.getClass().getSimpleName()+ ".tekenTrein");
		
		huidigeTrein = mijnTrein;
		if(huidigeTrein == 1)
		{
			OFFSET = 10;
		}
		else
		{
			OFFSET = 100;
		}
		if (train != "")
		{
			Graphics g = grafischeWeergaveTekenPanel.getGraphics();
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(30,80+huidigeTrein*OFFSET,80,40);
			g.fillRect(80,60+huidigeTrein*OFFSET,30,30);
			g.drawRoundRect(85, 40+huidigeTrein*OFFSET, 20, 20, 20, 20);
			g.drawRoundRect(85, huidigeTrein*OFFSET, 40, 40, 40, 40);
			g.setColor(Color.BLACK);
			g.fillRoundRect(35, 120+huidigeTrein*OFFSET, 20, 20, 20, 20);
			g.fillRoundRect(80, 120+huidigeTrein*OFFSET, 20, 20, 20, 20);
			g.drawString(train,40,105+huidigeTrein*OFFSET);
		}
    }
	
	/**
	 * Deze methode tekent een wagon.
	 * @param wagon De naam van de wagon.
	 * @param wagonNummer Het wagonnummer.
	 * @param trainNumber De treinnummer.
	 */
	public void tekenWagon(String wagon, int wagonNummer, int trainNummer) 
	{
		System.out.println(this.getClass().getSimpleName()+ ".tekenWagon");
		
		huidigeAantalWagons = wagonNummer;
		
		Graphics g = grafischeWeergaveTekenPanel.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30+huidigeAantalWagons*TRAINLENGTH,80+huidigeTrein*OFFSET,80,40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35+huidigeAantalWagons*TRAINLENGTH, 120+huidigeTrein*OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(80+huidigeAantalWagons*TRAINLENGTH, 120+huidigeTrein*OFFSET, 20, 20, 20, 20);
		g.drawString(wagon,40+huidigeAantalWagons*TRAINLENGTH,105+huidigeTrein*OFFSET);
    }
	
	/**
	 * Deze methode maakt het scherm weer leeg.
	 */
	public void maakSchermLeeg()
	{
		Graphics g = grafischeWeergaveTekenPanel.getGraphics();
		g.clearRect(0,0,790, 500);
	}
}