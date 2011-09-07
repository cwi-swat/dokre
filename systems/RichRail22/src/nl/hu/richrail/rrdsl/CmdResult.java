package nl.hu.richrail.rrdsl;

import nl.hu.richrail.trains.RollingStock;


public class CmdResult<T extends Command>
{
	private String result;
	private RollingStock subject;
	
	public CmdResult(String result,RollingStock subject)
	{
	this.result = result;
	this.subject = subject;
	}
	
	public String getResult(){
		return result;
	}
	public RollingStock getSubject()
	{
		return subject;
	}
}
