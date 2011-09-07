package com.ns.richrail.command;

public class AddCommand extends Command
{
	private String id;
	private String toId;
	public AddCommand(String id, String toId)
	{
		super();
		this.id = id;
		this.toId = toId;
	}
	public String getId()
	{
		return id;
	}
	public String getToId()
	{
		return toId;
	}
}
