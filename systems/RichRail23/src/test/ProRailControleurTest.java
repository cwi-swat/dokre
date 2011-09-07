package test;

import model.WagonType;
import controller.ProRailControleur;
import controller.TreinControleur;
import controller.WagonControleur;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ProRailControleurTest extends TestCase
{

	private TreinControleur treinControleur;
	private WagonControleur wagonControleur;
	private ProRailControleur prorailControleur;
	private view.ProRailWeergave proRailWeergave;
	WagonType wagonType; 
	
	public ProRailControleurTest(String name) 
	{
		super(name);
	}

	protected void setUp() throws Exception 
	{
		super.setUp();
		proRailWeergave = new view.ProRailWeergave();
		prorailControleur = new ProRailControleur(proRailWeergave);
		treinControleur = new TreinControleur(prorailControleur);
		wagonControleur = new WagonControleur(prorailControleur);
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
		treinControleur = null;
		wagonControleur = null;
		prorailControleur = null;
		proRailWeergave = null;
	}
	
	/**
	 * Controleert of een trein goed wordt aangemaakt.
	 */
	public void testTreinAanmaken()
	{
		treinControleur.treinAanmaken("tr1");
		
		String expected = "tr1";
		String result = treinControleur.geefTrein("tr1").geefTreinID();
		
		assertEquals(expected, result);
	}
	
	/**
	 * Controleert of een trein goed wordt verwijderd.
	 */
	public void testTreinVerwijderen()
	{
		treinControleur.treinVerwijderen("tr1");
		
		Object expected = null;
		Object result = treinControleur.geefTrein("tr1");
		
		assertEquals(expected, result);
	}
	
	/**
	 * Controleert of een wagon goed wordt aangemaakt.
	 */
	public void testWagonAanmaken()
	{
		wagonControleur.wagonAanmaken("wg1", 25);
		wagonControleur.wagonAanmaken("wg2", 20);
		
		String expected1 = "wg1";
		int expected2 = 25;
		String expected3 = "wg2";
		int expected4 =20;
		
		String result1 = wagonControleur.geefWagon("wg1").geefWagonID();
		int result2 = wagonControleur.geefWagon("wg1").geefAantalStoelen();
		String result3 = wagonControleur.geefWagon("wg2").geefWagonID();
		int result4 = wagonControleur.geefWagon("wg2").geefAantalStoelen();
		
		assertEquals(expected1, result1);
		assertEquals(expected2, result2);
		assertEquals(expected3, result3);
		assertEquals(expected4, result4);
		
	}
	
	/**
	 * Controleert of een wagon goed wordt verwijderd.
	 */
	public void testWagonVerwijderen()
	{
		wagonControleur.wagonVerwijderen("wg1");
		
		Object expected = null;
		Object result = wagonControleur.geefWagon("wg1");
		
		assertEquals(expected, result);
	}
	
	/**
	 * Aan deze methode kunnen alle tests worden toegevoegd. 
	 * De suite voert ze 1 voor 1 uit en geeft resultaat terug.
	 * @return
	 */
	public static Test suite()
	{      
		TestSuite suite = new TestSuite();      
		suite.addTest(new ProRailControleurTest("testTreinAanmaken"));	
		suite.addTest(new ProRailControleurTest("testTreinVerwijderen"));
		suite.addTest(new ProRailControleurTest("testWagonAanmaken"));
		suite.addTest(new ProRailControleurTest("testWagonVerwijderen"));
		return suite;
	}
}
