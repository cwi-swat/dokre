package controller;

import java.util.Hashtable;

import model.ConcreteCreatorWagonType;
import model.Wagon;
import model.WagonType;

/**
 * Onderhoudt de controle van de wagon objecten.
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/19/2009
 * 
 */

public class WagonControleur
{
	/**
	 * Hashtable van alle wagons.
	 */
	Hashtable<String, Wagon> wagons;
	
	/**
	 * Prorailcontroleur voor de acties.
	 */
	ProRailControleur prorailcontroleur;
	
	/**
	 * ConcreteCreatorWagonType voor het wagon type
	 */
	ConcreteCreatorWagonType creatorWagonType;
	
	/**
	 * Het wagon type.
	 */
	WagonType wagonType;
	
	/**
	 * Maakt het wagonControleurobject aan.
	 * Maakt een hashtable van wagons aan.
	 * @param proRailControleur De prorailcontroleur die gebruik maakt.
	 */
	public WagonControleur(ProRailControleur mijnProrailcontroleur)
	{
		prorailcontroleur = mijnProrailcontroleur;
		wagons = new Hashtable<String, Wagon>();
	}
	
	/**
	 * Maakt een nieuw wagon object aan
	 * en voegt deze toe aan de hashtable.
	 * @param wagonID Het ID van de wagon die wordt gemaakt.
	 * @param aantalStoelen Het aantal stoelen.
	 */
	public void wagonAanmaken(String wagonID, int aantalStoelen)
	{
		if(wagons.containsKey(wagonID) != true)
		{
			Wagon wagon;
			creatorWagonType = new ConcreteCreatorWagonType();
			wagonType = creatorWagonType.creeerWagonType("type A12", aantalStoelen);
			wagon = new Wagon(wagonID, wagonType);
			prorailcontroleur.mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " created with " + aantalStoelen + " seats." );		
			wagons.put(wagonID, wagon);
		}
		else
			prorailcontroleur.mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " already exists.");
	}
	
	/**
	 * haalt aan de hand van de parameter
	 * het Wagonobject op uit de hashtable.
	 * @param wagonID Wagon ID van de wagon die wordt opgehaald.
	 * @return Wagon or null
	 */
	public Wagon geefWagon(String wagonID)
	{
		if(wagons.containsKey(wagonID))
			return wagons.get(wagonID);
		else
			return null;
	}
	
	/**
	 * Deze methdoe geeft de hashtable van alle wagons terug.
	 * @return wagons Hashtable van alle wagons.
	 */
	public Hashtable<String, Wagon> geefAlleWagons()
	{
		return wagons;
	}
	
	/**
	 * Verwijderd het wagon object met id
	 * wagonID uit de hashtable
	 * @param wagonID WagonID van de wagon die wordt verwijderd. 
	 */
	public void wagonVerwijderen(String wagonID)
	{
		if(wagons.containsKey(wagonID))
		{
			prorailcontroleur.probeerWagonsVerwijderen(wagonID);
			wagons.remove(wagonID);
			prorailcontroleur.mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " deleted.");
		}
		else
			prorailcontroleur.mutatieOphalen().nieuweMutatie("Wagon " + wagonID + " does not exist.");
	}
	
}
