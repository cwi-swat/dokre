package model;

/**
 * Instantieerd het Wagon object.
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/18/2009
 * 
 */

public class Wagon 
{
	/**
	 * 
	 */
	String wagonID;
	
	/**
	 * Aantal stoelen in Wagon
	 */
	int aantalStoelen;
	
	/**
	 * 
	 */
	WagonType wagonType;
	
	/**
	 * Maakt een nieuw wagon object aan
	 * @param wagonID Wagon object identificatie nummer.
	 * @param aantalStoelen Aantal stoelen die een wagon bevat.
	 */
	public Wagon(String newWagonID, WagonType wagontype)
	{
		wagonID = newWagonID;
		wagonType = wagontype;
	}
	
	/**
	 * Geeft het wagonID terug
	 * @return wagonID Het wagonID van dit Wagon object.
	 */
	public String geefWagonID()
	{
		return wagonID;
	}
	
	/**
	 * Geeft het aantal stoelen terug
	 * @return aantalStoelen Het aantal stoelen in een wagon.
	 */
	public int geefAantalStoelen()
	{
		return wagonType.geefZitplaatsen();
	}

}
