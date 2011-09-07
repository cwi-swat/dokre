package model;

/**
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/24/2009
 * 
 */

public interface WagonType {
	
	public void nieuwWagonType(String wagonType, int zitplaatsen);
	
	public int geefZitplaatsen ();
	
	public void zetZitplaatsen (int zitplaatsen);
	
	public String geefWagonType ();

}