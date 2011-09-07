package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import view.ProRailWeergave;
import model.Mutatie;
import model.Trein;
import model.Wagon;

/**
 * ProRailControleur, verantwoordelijk voor het aansturen van de weergave.
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * 
 * @since 1.0
 * @version 1.0, 03/18/2009
 * 
 */
public class ProRailControleur implements ActionListener
{
	/**
	 * Hier word de gebruikers interface in opgeslagen	 
	 * */
	private ProRailWeergave view;
	
	/**
	 * Hier worden de mutaties aan toegevoegd
	 */
	private Mutatie mutatie;
	
	/**
	 * Hier worden de treinen in geregeld.
	 */
	private TreinControleur treinControleur;
	
	/**
	 * Hier worden de wagons in geregeld.
	 */
	private WagonControleur wagonControleur;
	
	/**
	 * In ProRailControleur wordt de grafische gebruikers interface geinstantieerd.
	 * @param newView - De grafische gebruikers interface.
	 */
	public ProRailControleur(ProRailWeergave newView)
	{
		System.out.println("Instantieer GenController(\""+newView.getClass()+"\")");
		view = newView;
		view.koppelActionListener(this);
		mutatie = new Mutatie();
		treinControleur = new TreinControleur(this);
		wagonControleur = new WagonControleur(this);
	}
	

	/**
	 * @param e De actie evenement die afgehandeld wordt.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("ProRailControleur.actionPerformed");
		regelCommando(view.geefOpdrachtRegelTekst());
		
		//view.voegResultaatRegelToe(view.geefOpdrachtRegelTekst());
		//TODO werkt nog niet helemaal bovenstaande (volgens mijn).
		//TODO: ga met de ingeschoten opdrachtregel naar een methode die de tekst afvangt (Interpreter)
	}
	
	/**
	 * Geeft het mutatieobject terug.
	 * @return mutatie Het mutatieobject.
	 */
	public Mutatie mutatieOphalen()
	{
		return mutatie;
	}
	
	/**
	 * Geeft het treinControleur object terug.
	 * @return treinControleur het treincontroleur object.
	 */
	public TreinControleur treinControleurOphalen()
	{
		return treinControleur;
	}
	
	/**
	 * Geeft het wagonControleur object terug.
	 * @return wagonControleur het wagoncontroleur object.
	 */
	public WagonControleur wagonControleurOphalen()
	{
		return wagonControleur;
	}
	
	/**
	 * Indien er een wagon wordt verwijderd,
	 * controleerd deze functie of de wagon aan een trein
	 * is gekoppeld. Zo ja, wordt hij losgekoppeld.
	 * @param wagonID Het ID van de wagon.
	 */
	public void probeerWagonsVerwijderen(String wagonID)
	{
		
		Collection<Trein> trainIterator = treinControleur.geefAlleTreinen().values();
		
		for (Trein trein : trainIterator) {
			if(trein.geefAlleWagons().containsKey(wagonID))
			{
				trein.wagonVerwijderen(wagonControleur.geefWagon(wagonID));
				mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " removed from train " + trein.geefTreinID() + ".");
			}		
		}
		
	}
	
	/**
	 * Koppelt een wagon aan een trein
	 * @param treinID ID van de trein die gekoppeld wordt.
	 * @param wagonID ID van de wagon die gekoppeld wordt.
	 */
	public void koppelWagonAanTrein(String treinID, String wagonID)
	{
		Trein trein;
		Wagon wagon;
		System.out.println("koppelWagonAanTrein");
		if(treinControleur.geefTrein(treinID) != null)
		{
			trein = treinControleur.geefTrein(treinID);
			if(wagonControleur.geefWagon(wagonID) != null)
			{
				wagon = wagonControleur.geefWagon(wagonID);
				trein.wagonToevoegen(wagon);
				mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " added to train " + treinID + ".");
			}
			else
			{
				mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " does not exist.");
			}
		}
		else
		{
			mutatieOphalen().nieuweMutatie("Train " + treinID + " does not exist.");
		}
	}
	
	/**
	 * Ontkoppelt een wagon van een trein
	 * @param treinID ID van de trein die ontkoppeld wordt.
	 * @param wagonID ID van de wagon die ontkoppeld wordt.
	 */
	public void ontkoppelWagonVanTrein(String treinID, String wagonID)
	{
		Trein trein;
		Wagon wagon;
		System.out.println("ontkoppelWagonVanTrein");
		if(treinControleur.geefTrein(treinID) != null)
		{
			trein = treinControleur.geefTrein(treinID);
			if(wagonControleur.geefWagon(wagonID) != null)
			{
				
					wagon = wagonControleur.geefWagon(wagonID);
				if(trein.geefAlleWagons().containsKey(wagon.geefWagonID()))
				{
					trein.wagonVerwijderen(wagon);
					mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " removed from train " + treinID + ".");
				}
				else
				{
					mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " is not a part of train " + treinID + ".");
				}
			}
			else
			{
				mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " does not exist.");
			}
		}
		else
		{
			mutatieOphalen().nieuweMutatie("Train " + treinID + " does not exist.");
		}
	}
	
	/**
	 * Deze functie kijkt naar het commando wat is ingegeven.
	 * Afhankelijk van het commando wordt een vervolgmethode
	 * aangeroepen.
	 * @param commando Het ingegeven commando door de gebruiker.
	 */
	public void regelCommando(String commando)
	{
		System.out.println(this.getClass().getSimpleName()+ "regelCommando - begin");
		String[] commandoTekst;
		
		if(!commando.equals(""))
		{
			commandoTekst = commando.split(" ");
			if(commandoTekst[0].equals("new") && commandoTekst.length == 3 || commandoTekst.length == 5)
			{
				if(commandoTekst.length == 3 &&commandoTekst[1].equals("train")) //new train
				{
					treinControleurOphalen().treinAanmaken(commandoTekst[2]);
				}
				else if (commandoTekst[1].equals("wagon")) //new wagon
				{
					System.out.println(commandoTekst[2]);
					if(commandoTekst.length == 5  && commandoTekst[3].equals("numseats")) //new wagon wagonID numseats
					{
						wagonControleurOphalen().wagonAanmaken(commandoTekst[2], Integer.parseInt(commandoTekst[4]));
					}
					else if(commandoTekst.length == 5  && !commandoTekst[3].equals("numseats"))
					{
						mutatieOphalen().nieuweMutatie("Onjuist commando " + commando);
					}
					else
					{
						wagonControleurOphalen().wagonAanmaken(commandoTekst[2], 20);
					}				
				}
				else
				{
					mutatieOphalen().nieuweMutatie("Onjuist commando " + commando);
				}
			}
			else if(commandoTekst[0].equals("add") && commandoTekst.length == 4) //add wg1 to tr1
			{
				koppelWagonAanTrein(commandoTekst[3], commandoTekst[1]);
			}
			else if(commandoTekst[0].equals("getnumseats")) 
			{
				if(commandoTekst.length == 3 &&commandoTekst[1].equals("train")) //getnumseats train tr1
				{
					mutatieOphalen().nieuweMutatie("Number of seats in train " + commandoTekst[2] + " = " + Integer.toString(treinControleurOphalen().geefTrein(commandoTekst[2]).geefAantalStoelen()));
				}
				else if (commandoTekst[1].equals("wagon")) //getnumseats wagon wg1
				{
					mutatieOphalen().nieuweMutatie("Number of seats in wagon " + commandoTekst[2] + " = " + Integer.toString(wagonControleurOphalen().geefWagon(commandoTekst[2]).geefAantalStoelen()));
				}
				else
				{
					mutatieOphalen().nieuweMutatie("Onjuist commando " + commando);
				}
			}
			else if(commandoTekst[0].equals("delete") && commandoTekst.length == 3) 
			{
				if(commandoTekst[1].equals("train")) //delete train
				{
					treinControleurOphalen().treinVerwijderen(commandoTekst[2]);
				}
				else if (commandoTekst[1].equals("wagon")) //delete wagon
				{
					wagonControleurOphalen().wagonVerwijderen(commandoTekst[2]);
				}
				else
				{
					mutatieOphalen().nieuweMutatie("Onjuist commando " + commando);
				}
			}
			else if(commandoTekst[0].equals("remove") && commandoTekst.length == 4) //remove wagon
			{
				ontkoppelWagonVanTrein(commandoTekst[3], commandoTekst[1]);
			}
			else
			{
				mutatieOphalen().nieuweMutatie("Onjuist commando " + commando);
			}
		}
		else
		{
			mutatieOphalen().nieuweMutatie("Geef uw commando...");
		}
		view.voegResultaatRegelToe(mutatieOphalen().mutatieRegelOphalen().geefMutatieWaarde());
		opnieuwTekenen();
		opnieuwSchrijven();
		System.out.println(this.getClass().getSimpleName()+ ".regelCommando - eind");
	}
	
	/**
	 * Deze methode roept voor iedere trein de teken methode van de view aan.
	 */
	public void opnieuwTekenen()
	{
		System.out.println(this.getClass().getSimpleName()+ ".opnieuwTekenen");
		
		view.maakSchermLeeg();
		Collection<Trein> trainIterator = treinControleur.geefAlleTreinen().values();
		int trainNumber = 1;	
		for (Trein trein : trainIterator) {
			int wagonNumber = 1;
			view.tekenTrein(trein.geefTreinID(), trainNumber);			
			Collection<Wagon> wagonIterator = trein.geefAlleWagons().values();
			for (Wagon wagon : wagonIterator) {
				view.tekenWagon(wagon.geefWagonID(), wagonNumber, trainNumber);
				wagonNumber++;
			}
			trainNumber++;
		}
	}
	
	/**
	 * Deze methode vernieuwd voor iedere train of wagon het resultatenscherm op.
	 */
	public void opnieuwSchrijven()
	{
		System.out.println(this.getClass().getSimpleName()+ ".opnieuwSchrijven");
		
		String wagons = "";
		Collection<Wagon> wagonIterator = wagonControleur.geefAlleWagons().values();
		if(!wagonIterator.isEmpty())
		{
			wagons += "\nWagons \n";
		}
		for (Wagon wagon : wagonIterator) {
			wagons += "(" + wagon.geefWagonID() + ":" + Integer.toString(wagon.geefAantalStoelen()) + ")";
		}
		
		String treinen = "";
		Collection<Trein> trainIterator = treinControleur.geefAlleTreinen().values();
		if(!trainIterator.isEmpty())
		{
			treinen += "\nTrains \n";
		}
		for (Trein trein : trainIterator) {
			treinen += "(" + trein.geefTreinID() + ":" + trein.geefAantalStoelen() + ")";
			Collection<Wagon> wagonIterator2 = trein.geefAlleWagons().values();
			for (Wagon wagon : wagonIterator2) {
				treinen += "-(" + wagon.geefWagonID() + ")";
			}
			treinen += "\n";
		}
		
		view.creerTekst(wagons, treinen);
	}
}
