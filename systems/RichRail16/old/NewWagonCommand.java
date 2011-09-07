package com.ns.richrail.command;

public class NewWagonCommand extends NewCommand
{
	private String id;
	private int numseats;
	public NewWagonCommand(String id, int numseats)
	{
		super();
		this.id = id;
		this.numseats = numseats;
	}
	public String getId()
	{
		return id;
	}
	public int getNumseats()
	{
		return numseats;
	}
}
