package model;

/**
* Instantieerd de MijnwagonType.
* 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
* 
* @since 1.0
* @version 1.0, 03/24/2009
* 
*/

public class MijnWagonType implements WagonType
{

	/**
	 * Wagontype Identificatie nummer voor een wagon.
	 */
	String wagonTypeID;
	
	/**
	 * Aantal zitplaatsen in een wagon.
	 */
	int zitplaatsen;
	
	
	/**
	 * Maakt een nieuw type van wagon object aan
	 * @param wagonTypeID Wagon object identificatie nummer.
	 * @param zitplaatsen Aantal stoelen die een wagontype bevat.
	 */
	public MijnWagonType(String newWagonTypeID, int newZitplaatsen)
	{
		System.out.println(this.getClass().getSimpleName() + ".MijnWagonType - begin");
		System.out.println(this.getClass().getSimpleName() + ".MijnWagonType met binnengreken waarden: wagontype:" 
				+ newWagonTypeID + " en zitplaatsen " + newZitplaatsen );
		
		wagonTypeID= newWagonTypeID;
		zitplaatsen = newZitplaatsen;
		
		System.out.println(this.getClass().getSimpleName() + ".MijnWagenType - eind");
	}
	
	/**
	 * Maakt een nieuw wagonType 
	 * @param wagonTypeID Wagon object identificatie nummer.
	 * @param aantalStoelen Aantal stoelen die een wagonType bevat.
	 */
	public void nieuwWagonType(String wagonType, int zitplaatsen)
	{
		System.out.println(this.getClass().getSimpleName() + ".nieuwWagonType - begin");
		
		wagonTypeID = wagonType;
		zetZitplaatsen(zitplaatsen);
		
		System.out.println(this.getClass().getSimpleName() + ".nieuwWagonType - eind");
	}
	
	/**
	 * Geeft de type van wagon terug
	 * @return wagonTypeID de type van wagon.
	 */
	public String geefWagonType()
	{
		System.out.println(this.getClass().getSimpleName() + ".geefWagonType()");
		System.out.println(this.getClass().getSimpleName() + ".geefWagonType - met wagonType: " + wagonTypeID);
		
		return wagonTypeID;
	}
	
	/**
	 * zet het aantaal stoelen in wagon
	 * @param zitplaatsen het aantaal plaatsen van wagontype.
	 */
	public void zetZitplaatsen(int newZitplaatsen)
	{
		System.out.println(this.getClass().getSimpleName() + ".zetZitplaatsen - begin");
		
		System.out.println(this.getClass().getSimpleName() + ".zetZitplaatsen(" + newZitplaatsen+ ")");
		zitplaatsen = newZitplaatsen;
		
		System.out.println(this.getClass().getSimpleName() + ".zetZitplaatsen - eind");
	}
	
	/**
	 * Geeft het aantal stoelen terug
	 * @return aantalStoelen Het aantal stoelen in een wagon.
	 */
	public int geefZitplaatsen() 
	{
		System.out.println(this.getClass().getSimpleName() + ".geefZitplaatsen()");
		System.out.println(this.getClass().getSimpleName() + ".geefZitplaatsen - geeft aantal zitplaatsen terug - aantal " + zitplaatsen);
		
		return zitplaatsen;
	}
}