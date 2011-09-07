package test;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import model.ConcreteCreatorWagonType;
import model.Wagon;
import model.WagonType;

import org.junit.After;
import org.junit.Before;

public class WagonTest extends TestCase {

	Wagon wagon;
	WagonType wagonType;
	private static ConcreteCreatorWagonType creatorWagonType;
	
	public WagonTest (String naam ){
		super(naam);
	}
	@Before
	public void setUp() throws Exception {
		super.setUp();	
		creatorWagonType = new ConcreteCreatorWagonType();
		wagonType = creatorWagonType.creeerWagonType("type A12", 22);
		wagon = new Wagon("wag1", wagonType);
	}
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		creatorWagonType = null; 
		wagonType = null;
		wagon= null;
	}
	public void testWagonId(){
		String verwacht ="wag1";
		String resultaat = wagon.geefWagonID();
		assertEquals(verwacht, resultaat);
	}
	public void testAantaalzitplatsen(){
		int verwacht = 22;
		int resultaat = wagon.geefAantalStoelen();
		assertEquals(verwacht, resultaat);
	}
	public static Test suite()
	{      
		TestSuite suite = new TestSuite();  
		suite.addTest(new WagonTest("testWagonId"));
		suite.addTest(new WagonTest("testAantaalzitplatsen"));
		return suite;
	}

}
