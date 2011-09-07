package com.ns.richrail.command;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.ns.richrail.core.PoolException;
import com.ns.richrail.core.RollingStockPool;
import com.ns.richrail.core.Wagon;

public class CommandParser implements CommandHandler, CommandParserFacade
{
	private ArrayList<PrintWriter> outputListeners = new ArrayList<PrintWriter>();

	private void writeLine(String line)
	{
		for (PrintWriter writer : outputListeners)
		{
			writer.println(line);
		}
	}

	public void attachOutputListener(PrintWriter outputListener)
	{
		outputListeners.add(outputListener);
	}

	public void detachOutputListener(PrintWriter outputListener)
	{
		outputListeners.remove(outputListener);
	}

	public void runCommand(String command)
	{
		// create a stream from the string
		CharStream stream = new ANTLRStringStream(command);
		RichRailLexer lexer = new RichRailLexer(stream);

		// create a token stream that will split the string into its individual
		// tokens
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);

		// create a parser for the tokens
		RichRailParser parser = new RichRailParser(tokens);
		parser.handler = this;
		try
		{
			parser.command();
		}
		catch (RecognitionException ex)
		{
			writeLine("command not correct");
		}
	}

	@Override
	public void newTrainCommand(String id)
	{
		try
		{
			RollingStockPool.getInstance().newTrain(id);
			writeLine(String.format("train %s created", id));
		}
		catch (PoolException e)
		{
			writeLine(e.getMessage());
		}
	}

	@Override
	public void newWagonCommand(String id, String numseats)
	{
		try
		{
			if (numseats == null)
			{
				RollingStockPool.getInstance().newWagon(id, null);
				writeLine(String.format("wagon %s created with %s seats", id, Wagon.DEFAULT_SEATS));
			}
			else
			{
				RollingStockPool.getInstance().newWagon(id, Integer.parseInt(numseats));
				writeLine(String.format("wagon %s created with %s seats", id, numseats));
			}
		}
		catch (PoolException e)
		{
			writeLine(e.getMessage());
		}
	}

	@Override
	public void addCommand(String wagonId, String trainId)
	{
		try
		{
			RollingStockPool.getInstance().coupleWagon(wagonId, trainId);
			writeLine(String.format("wagon %s added to train %s", wagonId, trainId));
		}
		catch (PoolException e)
		{
			writeLine(e.getMessage());
		}
	}

	@Override
	public void getCommand(String type, String id)
	{
		try
		{
			if (type.equals("train"))
			{
				int numseats = RollingStockPool.getInstance().countNumSeatsTrain(id);
				writeLine(String.format("number of seats in train %s : %s", id, numseats));
			}
			else
				if (type.equals("wagon"))
				{
					int numseats = RollingStockPool.getInstance().countNumSeatsWagon(id);
					writeLine(String.format("number of seats in wagon %s : %s", id, numseats));
				}
		}
		catch (PoolException e)
		{
			writeLine(e.getMessage());
		}
	}

	@Override
	public void delCommand(String type, String id)
	{
		try
		{
			if (type.equals("train"))
			{
				RollingStockPool.getInstance().deleteTrain(id);
				writeLine(String.format("train %s deleted", id));
			}
			else
				if (type.equals("wagon"))
				{
					RollingStockPool.getInstance().deleteWagon(id);
					writeLine(String.format("wagon %s deleted", id));
				}
		}
		catch (PoolException e)
		{
			writeLine(e.getMessage());
		}
	}

	@Override
	public void remCommand(String wagonId, String trainId)
	{
		try
		{
			RollingStockPool.getInstance().decoupleWagon(wagonId);
			writeLine(String.format("wagon %s removed from train %s", wagonId, trainId));
		}
		catch (PoolException e)
		{
			writeLine(e.getMessage());
		}
	}
}
