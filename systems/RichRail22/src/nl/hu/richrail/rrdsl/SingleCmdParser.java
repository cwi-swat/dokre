package nl.hu.richrail.rrdsl;

import nl.hu.richrail.trains.RollingStockPool;

import org.antlr.runtime.*;

public class SingleCmdParser {

	/**
	 * @param args
	 * @throws RecognitionException 
	 */
	public static CmdResult parse(String command) throws RecognitionException {
		CharStream stream =
			new ANTLRStringStream(command);
		RichRailLexer lexer = new RichRailLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		RichRailParser parser = new RichRailParser(tokenStream);
		Command result = parser.command();
		if(result != null)
			return result.interpret(RollingStockPool.getInstance());
		else
			throw new RecognitionException();
	}

}
