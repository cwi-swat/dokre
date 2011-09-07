package model;

import java.util.Hashtable;

/**
 * Deze klasse houdt alle mutatieregels bij.
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/18/2009
 * 
 */
public class Mutatie
{
	/**
	 * Verzameling van mutaties opgelsagen in mutaties, Integer is de key en mutatieregel is het object.
	 */
	Hashtable<Integer, MutatieRegel> mutaties;
	
	/**
	 * Teller voor het bijhouden van aantal mutaties.
	 */
	Integer mutatieNummer;
	
	/**
	 * Maakt een mutatie object aan.
	 * initialiseerd de hashtable met alle mutaties.
	 */
	public Mutatie()
	{
		System.out.println(this.getClass().getSimpleName() + "Constructor");
		mutaties = new Hashtable<Integer, MutatieRegel>();
		mutatieNummer = 1;
	}
	
	/**
	 * Maakt een nieuwe mutatieregel aan en voegt deze
	 * toe aan de hashtable.
	 * @param mutatie De string van de mutatie die wordt toegevoegd.
	 */
	public void nieuweMutatie(String mutatie)
	{
		System.out.println(this.getClass().getSimpleName() + ".nieuweMutatie - begin");
		System.out.println(this.getClass().getSimpleName() + ".nieuweMutatie(" + mutatie + ")");
		
		MutatieRegel mutatieRegel = new MutatieRegel(mutatie);
		mutaties.put(mutatieNummer, mutatieRegel);
		mutatieNummer++;
		
		System.out.println(this.getClass().getSimpleName() + ".nieuweMutatie - eind");
	}
	
	/**
	 * Haalt de laatste toegevoegde mutatie op uit de hashtable.
	 * @return MutatieRegel Mutatieregel Object waar de mutatie in staat. 
	 */
	public MutatieRegel mutatieRegelOphalen()
	{
		System.out.println(this.getClass().getSimpleName() + ".mutatieRegelOphalen()");
		
		return mutaties.get(mutaties.size());
	}
	
	/**
	 * Geeft de hashtable met alle mutaties terug.
	 * @return mutaties Hashtable met alle mutatieRegels. 
	 */
	public Hashtable<Integer, MutatieRegel> alleMutatieRegelsOphalen()
	{
		System.out.println(this.getClass().getSimpleName() + ".alleMutatiesOphalen()");
		
		return mutaties;
	}
	
}
