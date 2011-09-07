package test;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import model.ConcreteCreatorWagonType;
import model.WagonType;

import org.junit.After;
import org.junit.Before;

//TODO: Testen of het werkt om een wagon toe te voegen met 0 stoelen.

public class FactoryMethodeTest extends TestCase  {

	private static ConcreteCreatorWagonType creatorWagonType;
	WagonType wagonType; 

	public FactoryMethodeTest (String naam ){
		super(naam);
	}
	
	@Before
	public void setUp() throws Exception {

		super.setUp();
		creatorWagonType = new ConcreteCreatorWagonType();
		wagonType = creatorWagonType.creeerWagonType("type A12", 22) ;
		 
		
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		creatorWagonType = null; 
		wagonType = null;
	}
	public void testWagonTypeOphalen(){
		String verwacht = "type A12";
		String resultaat = wagonType.geefWagonType();
		assertEquals(verwacht, resultaat);
	}
	public void testZitplaatsenOphalen(){
		int verwacht = 22;
		int resultaat = wagonType.geefZitplaatsen();
		assertEquals(verwacht, resultaat);
	}
	public void testZitPlaatsenToevoegen(){
		
		int verwacht = 11; 
		wagonType.zetZitplaatsen(11);
		int resultaat = wagonType.geefZitplaatsen();
		assertEquals(verwacht, resultaat);
	}
	public void testCreeerNieuwWagonType(){
		int verwachtPlaatsen = 44;
		String verwachtWagonType = "Type B112";
		wagonType.nieuwWagonType("Type B112", 44);
		int resultaatPlaatsen = wagonType.geefZitplaatsen();
		String resultaatWagonType = wagonType.geefWagonType();
		assertEquals(verwachtPlaatsen, resultaatPlaatsen);
		assertEquals(verwachtWagonType, resultaatWagonType);
	}
	
	
	public static Test suite()
	{      
		TestSuite suite = new TestSuite();  
		suite.addTest(new FactoryMethodeTest("testWagonTypeOphalen"));
		suite.addTest(new FactoryMethodeTest("testZitplaatsenOphalen"));
		suite.addTest(new FactoryMethodeTest("testZitPlaatsenToevoegen"));
		suite.addTest(new FactoryMethodeTest("testCreeerNieuwWagonType"));
		return suite;
	}

}
