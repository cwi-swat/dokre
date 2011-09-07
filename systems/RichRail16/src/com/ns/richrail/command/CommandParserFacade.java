package com.ns.richrail.command;

import java.io.PrintWriter;

public interface CommandParserFacade
{

	public void attachOutputListener(PrintWriter outputListener);

	public void detachOutputListener(PrintWriter outputListener);

	public void runCommand(String command);

}