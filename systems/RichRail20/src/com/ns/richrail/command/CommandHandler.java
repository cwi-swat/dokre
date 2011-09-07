package com.ns.richrail.command;

public interface CommandHandler
{
	public void newTrainCommand(String id);

	public void newWagonCommand(String id, String numseats);

	public void addCommand(String add, String to);

	public void getCommand(String type, String id);

	public void delCommand(String type, String id);

	public void remCommand(String remove, String from);
}
