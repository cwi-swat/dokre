package com.ns.richrail.command;

public class DelCommand extends Command
{
	private String id;
	private String type;
	public DelCommand(String id, String type)
	{
		super();
		this.id = id;
		this.type = type;
	}
	public String getId()
	{
		return id;
	}
	public String getType()
	{
		return type;
	}
}
