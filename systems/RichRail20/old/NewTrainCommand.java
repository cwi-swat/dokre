package com.ns.richrail.command;

public class NewTrainCommand extends NewCommand
{
	private String id;
	public NewTrainCommand(String id)
	{
		super();
		this.id = id;
	}
	public String getId()
	{
		return id;
	}
}
