package controller;

import java.util.Hashtable;
import model.Trein;

/**
 * Onderhoudt de controle van de trein objecten.
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/18/2009
 * 
 */

public class TreinControleur
{
	/**
	 * Hashtable van treinen
	 */
	Hashtable<String, Trein> treinen;
	
	/**
	 * Het prorailcontroleur object.
	 */
	ProRailControleur prorailcontroleur;
	
	/**
	 * Maakt het treinControleur object aan.
	 * Maakt een hashtable van alle treinen aan.
	 */
	public TreinControleur(ProRailControleur mijnProrailcontroleur)
	{
		prorailcontroleur = mijnProrailcontroleur;
		treinen = new Hashtable<String, Trein>();
	}
	
	/**
	 * Maakt een nieuw trein object aan
	 * en voegt deze toe aan de hashtable.
	 * @param treinID De ID van de trein die moet worden aangemaakt.
	 */
	public void treinAanmaken(String treinID)
	{
		if(treinen.containsKey(treinID) != true)
		{
			Trein trein = new Trein(treinID);
			treinen.put(treinID, trein);
			prorailcontroleur.mutatieOphalen().nieuweMutatie("Train " + treinID + " created.");
		}
		else
			prorailcontroleur.mutatieOphalen().nieuweMutatie("Train " + treinID + " already exists.");
	}
	
	/**
	 * haalt aan de hand van de parameter
	 * het Treinobject op uit de hashtable.
	 * @param treinID Het treinID van de trein die moet worden opgehaald.
	 * @return Trein or null
	 */
	public Trein geefTrein(String treinID)
	{
		if(treinen.containsKey(treinID))
			return treinen.get(treinID);
		else
			return null;
	}
	
	/**
	 * Geeft alle treinen terug.
	 * @return treinen Hashtable met alle treinen.
	 */
	public Hashtable<String, Trein> geefAlleTreinen()
	{
		return treinen;
	}
	
	/**
	 * Verwijderd het trein object met id
	 * treinID uit de hashtable
	 * @param treinID Het treinID van de te verwijderen trein.
	 */
	public void treinVerwijderen(String treinID)
	{
		if(treinen.containsKey(treinID))
		{
			treinen.remove(treinID);
			prorailcontroleur.mutatieOphalen().nieuweMutatie("Train " + treinID + " deleted.");
		}
		else
			prorailcontroleur.mutatieOphalen().nieuweMutatie("Train " + treinID + " does not exist.");
	}

}
