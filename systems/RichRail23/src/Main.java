import javax.swing.JComponent;

import controller.ProRailControleur;

import view.ProRailWeergave;


/**
 * Instantieerd de JComponent.
 * 
 * @author Mark van de Haar
 * @author Vincent Verbeek
 * @author Samir el Aatiaoui
 * 
 * @since 1.0
 * @version 1.0, 03/18/2009
 * 
 */
public class Main extends JComponent
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
            
    		System.out.println("Instantieer Main.init()");
    		ProRailWeergave view = new ProRailWeergave();
        	new ProRailControleur(view);
        	
    }
}