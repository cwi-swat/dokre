package model;

/**
 * Deze klasse maakt een nieuwe mutatieregel.
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/18/2009
 * 
 */
public class MutatieRegel
{
	
	/**
	 * De recente Mutatie.
	 */
	String mijnMutatie;
	
	/**
	 * Maakt de mutatieregel aan en zet de mijnMutatie op basis
	 * van de gegevens string
	 * @param mutatie
	 */
	public MutatieRegel(String mutatie)
	{
		System.out.println(this.getClass().getSimpleName() + "Constructor");
		mijnMutatie = mutatie;
	}
	
	/**
	 * Geeft de waarde van mijnMutatie terug
	 * @return String
	 */
	public String geefMutatieWaarde()
	{
		System.out.println(this.getClass().getSimpleName() + ".geefMutatieWaarde()");
		System.out.println(this.getClass().getSimpleName() + ".geefMutatieWaarde waarde opgehaald: " + mijnMutatie);
		return mijnMutatie;
	}
}
