package com.ns.richrail.command;

public class GetCommand extends Command
{
	private String id;
	private String type;
	public GetCommand(String id, String type)
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
