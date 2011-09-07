package com.ns.richrail;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.debug.ParseTreeBuilder;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

import com.ns.richrail.command.Command;
import com.ns.richrail.command.CommandListener;
import com.ns.richrail.command.RichRailLexer;
import com.ns.richrail.command.RichRailParser;

public class Main implements CommandListener
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}

	public Main()
	{
	}

	public void run()
	{
		runCommand("new train z345");
		runCommand("add nr567 to bnk456");
		runCommand("remove x457 from b09809");
		//runCommand("new wagon t567");
		runCommand("new wagon t567 293851324545654");
	}

	public void runCommand(String command )
	{
		// create a stream from the string
		CharStream stream = new ANTLRStringStream(command);
		RichRailLexer lexer = new RichRailLexer(stream);

		// create a token stream that will split the string into its individual tokens
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);

		// create a parser for the tokens
		ParseTreeBuilder builder = new ParseTreeBuilder("RichRail");
		RichRailParser parser = new RichRailParser(tokens);
		TreeAdaptor adaptor = new CommonTreeAdaptor();
		parser.setTreeAdaptor(adaptor);
		parser.listener = this;
		try
		{
			RichRailParser.command_return ret = parser.command();
			CommonTree tree = (CommonTree)ret.getTree();
			System.out.println("----" + tree.toStringTree());
		}
		catch (RecognitionException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void newTrainCommand(String id)
	{
		System.out.println(id);
	}

	@Override
	public void newWagonCommand(String id, String numseats)
	{
		System.out.println(id + " seats=" + numseats);
	}

	@Override
	public void addCommand(String add, String to)
	{
		System.out.println(add);
	}

	@Override
	public void getCommand(String type, String id)
	{
		System.out.println(id);
	}

	@Override
	public void delCommand(String type, String id)
	{
		System.out.println(id);
	}

	@Override
	public void remCommand(String remove, String from)
	{
		System.out.println(remove + " from=" + from);
	}
}
