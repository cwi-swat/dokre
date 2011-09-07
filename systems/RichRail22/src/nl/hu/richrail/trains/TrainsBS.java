package nl.hu.richrail.trains;
import java.util.Observable;

import nl.hu.richrail.rrdsl.*;

public class TrainsBS extends Observable
{
		// this class has become obsolete
		// Facade will not be implemented
		// this class's responsibilities now lie with the controller
	
//		private RollingStockPool rollingStockPool = RollingStockPool.getInstance();
//		
//		public TrainsBS()
//		{
//			
//		}
//		
//		public void execute(String command)
//		{
//			
//			try{
//			for(Expression s : Parser.parse(command))
//			{
//				
//				setChanged();
//				notifyObservers(s.interpret(RollingStockPool.getInstance()).getResult());
//				
//			}
//			}catch (RrdslException e) {
//				setChanged();
//				notifyObservers(e.getMessage());
//			}
//		}
//		
//		public void createTrain()
//		{
//			
//		}
//		
//		public void deleteTrain()
//		{
//			
//		}
//		
//		public void addRsToTrain()
//		{
//			
//		}
//		public void removeRsFromTrain()
//		{
//			
//		}
}
