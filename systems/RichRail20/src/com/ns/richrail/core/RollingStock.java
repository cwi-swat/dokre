package com.ns.richrail.core;

public class RollingStock
{
	private String id;

	protected RollingStock(String id)
	{
		super();
		this.id = id;
	}

	// public API
	public String getId()
	{
		return id;
	}
}
