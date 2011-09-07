package com.ns.richrail.command;

public class RemCommand extends Command
{
	private String id;
	private String fromId;
	public RemCommand(String id, String fromId)
	{
		super();
		this.id = id;
		this.fromId = fromId;
	}
	public String getId()
	{
		return id;
	}
	public String getFromId()
	{
		return fromId;
	}
}
