package model;

import java.util.Collection;
import java.util.Hashtable;

/**
 * Instantieerd het trein object
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/18/2009
 * 
 */
public class Trein 
{

	Hashtable<String, Wagon> wagons;
	String treinId;
	
	/**
	 * Nieuw trein object.
	 * Maakt een hashtable aan van wagons.
	 * @param treinID TreinID van de trein.
	 */
	public Trein(String newTreinID)
	{
		System.out.println(this.getClass().getSimpleName() + "Constructor");
		
		treinId = newTreinID;
		wagons = new Hashtable<String, Wagon>();
		
		System.out.println(this.getClass().getSimpleName() + "Trein - nieuwe trein " + treinId);
	}
	
	/**
	 * Ophalen van het trainID
	 * @return treinID ID van de trein die wordt gevraagd.
	 */
	public String geefTreinID()
	{
		System.out.println(this.getClass().getSimpleName() + "geefTreinID()");
		System.out.println(this.getClass().getSimpleName() + "geefTreinID geeft Trein ID: " + treinId);

		return treinId;
	}
	
	/**
	 * Toevoegen van een bestaande wagon aan de trein.
	 * @param wagon Het wagon object dat aan de trein wordt toegevoegd.
	 */
	public void wagonToevoegen(Wagon wagon)
	{
		System.out.println(this.getClass().getSimpleName() + "wagonToevoegen - begin");
		
		String wagonID = wagon.geefWagonID();
		wagons.put(wagonID, wagon);
		
		System.out.println(this.getClass().getSimpleName() + "wagonToevoegen - wagon: " + wagon.geefWagonID()  + " toegevoegd.");		
		System.out.println(this.getClass().getSimpleName() + "wagonToevoegen - eind");
	}
	
	/**
	 * Verwijderen van wagon van de hashtable.
	 * @param wagon Het wagon object dat van de trein wordt verwijderd.
	 */
	public void wagonVerwijderen(Wagon wagon)
	{
		System.out.println(this.getClass().getSimpleName() + "wagonVerwijderen - begin");
		
		String wagonID = wagon.geefWagonID();
		wagons.remove(wagonID);
		
		System.out.println(this.getClass().getSimpleName() + "wagonVerwijderen - wagon: " + wagon.geefWagonID() + " verwijderd.");
		System.out.println(this.getClass().getSimpleName() + "wagonVerwijderen - eind");
	}
	
	/**
	 * Ophalen van een wagon object van de trein.
	 * @param wagonID Het ID van de trein die wordt opgehaald.
	 * @return Wagon Het wagon object dat wordt opgehaald. 
	 */
	public Wagon geefWagon(String wagonID)
	{
		System.out.println(this.getClass().getSimpleName() + "geefWagon("+wagonID+") - binnengekomen");
		if(wagons.containsKey(wagonID))
			return wagons.get(wagonID);
		else
			return null;
	}
	
	/**
	 * Haalt alle wagons van de trein op via een hashTable.
	 * @return wagons Hashtable met alle Wagon objecten die bij de trein horen. 
	 */
	public Hashtable<String, Wagon> geefAlleWagons()
	{
		System.out.println(this.getClass().getSimpleName() + "geefAlleWagons()");
		return wagons;
	}
	
	/**
	 * Geeft voor iedere wagon het aantal stoelen 
	 * en telt deze op voor een treintotaal.
	 * @return aantalStoelen Het aantalstoelen van de trein.
	 */
	public Integer geefAantalStoelen()
	{
		int aantalstoelen = 0;
		Collection<Wagon> iterator = wagons.values();
		
		for (Wagon wagon : iterator) {
			aantalstoelen += wagon.geefAantalStoelen();
		}
		
		return aantalstoelen;
	}
}
