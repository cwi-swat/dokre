package model;

/**
 * Verantwoordelijk voor het maken van WagonTypen
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/24/2009
 * 
 */
public class ConcreteCreatorWagonType implements CreatorWagonType{


	/**
	* Instantieerd de object ConcreteCreatorWagonType.
	*/
	public ConcreteCreatorWagonType ()
	{
		System.out.println(this.getClass().getSimpleName() + ".Constructor");
	}
	
	/**
	* Instantieerd de object MijnWagonType().
	* @return MijnWagonType.
	*/
	public WagonType creeerWagonType(String wagonType, int zitPlaatsen)
	{
		System.out.println(this.getClass().getSimpleName() + ".creerWagonType(" + wagonType + ", " + zitPlaatsen);
		return new MijnWagonType(wagonType, zitPlaatsen);
	}
}