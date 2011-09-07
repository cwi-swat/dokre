package nl.hu.richrail.trains;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public abstract class RollingStock extends Observable 
{

	 public static final Set<IdTuple> IDS = new HashSet<IdTuple>();
	 private IdTuple id;
	 
	 public RollingStock(String id)
	 {
		IdTuple idt = new IdTuple(this.getClass().getSimpleName(),id);
		if(!IDS.add(idt))
			throw new TrainException("The rollingstock id must be unique!");
		this.id = idt;
	 }
	 
	 public String getId()
	 {
		 return id.id;
	 }
	 public String getFullId()
	 {
		 return id.prefix +id.id ;
	 }
	 
//	 public void destroy()
//	 {
//		 IDS.remove(id);
//	 }
	 @Override
	 protected void finalize()
	 {
		 System.out.println("RollingStock.finalize()");
		 try{
		 IDS.remove(id);
		 }finally{
			 try {
				super.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 }
	 
	 
	 abstract public int getSeatCount();
	 
	 private class IdTuple
	 {
		 public IdTuple(String prefix, String id)
		 {
			 this.prefix = prefix;
			 this.id = id;
			 
		 }
		 public final String prefix;
		 public final String id;
		 
		 @Override
		 public boolean equals(Object o)
		 {
			 if(o instanceof IdTuple)
			 {
				 IdTuple idt = (IdTuple)o;
				 return idt.prefix.equals(prefix) && idt.id.equals(id);
			 }
			 return false;
		 }
		 @Override
		 public int hashCode()
		 {
			 return (prefix + id).hashCode();
		 }
		 
		 
	 }
	 
}
