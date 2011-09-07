// $ANTLR 3.2 Sep 23, 2009 12:02:23 C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g 2010-04-11 22:25:20

package com.ns.richrail.command;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

public class RichRailParser extends Parser
{
	public static final String[] tokenNames = new String[]
	{
			"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "NUMBER", "WHITESPACE", "'new'", "'train'", "'wagon'", "'numseats'", "'add'", "'to'", "'getnumseats'", "'delete'", "'remove'", "'from'"
	};
	public static final int T__16 = 16;
	public static final int T__15 = 15;
	public static final int T__12 = 12;
	public static final int T__11 = 11;
	public static final int T__14 = 14;
	public static final int T__13 = 13;
	public static final int T__10 = 10;
	public static final int NUMBER = 5;
	public static final int WHITESPACE = 6;
	public static final int ID = 4;
	public static final int EOF = -1;
	public static final int T__9 = 9;
	public static final int T__8 = 8;
	public static final int T__7 = 7;

	// delegates
	// delegators

	public RichRailParser(TokenStream input)
	{
		this(input, new RecognizerSharedState());
	}

	public RichRailParser(TokenStream input, RecognizerSharedState state)
	{
		super(input, state);

	}

	public String[] getTokenNames()
	{
		return RichRailParser.tokenNames;
	}

	public String getGrammarFileName()
	{
		return "C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g";
	}

	public CommandHandler handler;

	// $ANTLR start "command"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:20:1: command : (
	// newcommand | addcommand | getcommand | delcommand | remcommand );
	public final void command() throws RecognitionException
	{
		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:21:2: (
			// newcommand | addcommand | getcommand | delcommand | remcommand )
			int alt1 = 5;
			switch (input.LA(1))
			{
				case 7:
				{
					alt1 = 1;
				}
					break;
				case 11:
				{
					alt1 = 2;
				}
					break;
				case 13:
				{
					alt1 = 3;
				}
					break;
				case 14:
				{
					alt1 = 4;
				}
					break;
				case 15:
				{
					alt1 = 5;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 1, 0, input);

					throw nvae;
			}

			switch (alt1)
			{
				case 1:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:21:4:
					// newcommand
				{
					pushFollow(FOLLOW_newcommand_in_command49);
					newcommand();

					state._fsp--;

				}
					break;
				case 2:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:21:17:
					// addcommand
				{
					pushFollow(FOLLOW_addcommand_in_command53);
					addcommand();

					state._fsp--;

				}
					break;
				case 3:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:21:30:
					// getcommand
				{
					pushFollow(FOLLOW_getcommand_in_command57);
					getcommand();

					state._fsp--;

				}
					break;
				case 4:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:21:43:
					// delcommand
				{
					pushFollow(FOLLOW_delcommand_in_command61);
					delcommand();

					state._fsp--;

				}
					break;
				case 5:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:21:56:
					// remcommand
				{
					pushFollow(FOLLOW_remcommand_in_command65);
					remcommand();

					state._fsp--;

				}
					break;

			}
		}

		finally
		{
		}
		return;
	}

	// $ANTLR end "command"

	// $ANTLR start "newcommand"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:23:1: newcommand : (
	// newtraincommand | newwagoncommand );
	public final void newcommand() throws RecognitionException
	{
		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:24:2: (
			// newtraincommand | newwagoncommand )
			int alt2 = 2;
			int LA2_0 = input.LA(1);

			if ((LA2_0 == 7))
			{
				int LA2_1 = input.LA(2);

				if ((LA2_1 == 8))
				{
					alt2 = 1;
				}
				else
					if ((LA2_1 == 9))
					{
						alt2 = 2;
					}
					else
					{
						NoViableAltException nvae = new NoViableAltException("", 2, 1, input);

						throw nvae;
					}
			}
			else
			{
				NoViableAltException nvae = new NoViableAltException("", 2, 0, input);

				throw nvae;
			}
			switch (alt2)
			{
				case 1:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:24:4:
					// newtraincommand
				{
					pushFollow(FOLLOW_newtraincommand_in_newcommand74);
					newtraincommand();

					state._fsp--;

				}
					break;
				case 2:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:24:22:
					// newwagoncommand
				{
					pushFollow(FOLLOW_newwagoncommand_in_newcommand78);
					newwagoncommand();

					state._fsp--;

				}
					break;

			}
		}

		finally
		{
		}
		return;
	}

	// $ANTLR end "newcommand"

	// $ANTLR start "newtraincommand"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:26:1: newtraincommand :
	// 'new' 'train' ID ;
	public final void newtraincommand() throws RecognitionException
	{
		Token ID1 = null;

		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:27:2: ( 'new'
			// 'train' ID )
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:27:4: 'new'
			// 'train' ID
			{
				match(input, 7, FOLLOW_7_in_newtraincommand87);
				match(input, 8, FOLLOW_8_in_newtraincommand89);
				ID1 = (Token) match(input, ID, FOLLOW_ID_in_newtraincommand91);
				handler.newTrainCommand((ID1 != null ? ID1.getText() : null));

			}

		}

		finally
		{
		}
		return;
	}

	// $ANTLR end "newtraincommand"

	// $ANTLR start "newwagoncommand"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:30:1: newwagoncommand :
	// 'new' 'wagon' ID ( 'numseats' numseats= NUMBER )? ;
	public final void newwagoncommand() throws RecognitionException
	{
		Token numseats = null;
		Token ID2 = null;

		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:31:2: ( 'new'
			// 'wagon' ID ( 'numseats' numseats= NUMBER )? )
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:31:4: 'new'
			// 'wagon' ID ( 'numseats' numseats= NUMBER )?
			{
				match(input, 7, FOLLOW_7_in_newwagoncommand104);
				match(input, 9, FOLLOW_9_in_newwagoncommand106);
				ID2 = (Token) match(input, ID, FOLLOW_ID_in_newwagoncommand108);
				// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:31:21: (
				// 'numseats' numseats= NUMBER )?
				int alt3 = 2;
				int LA3_0 = input.LA(1);

				if ((LA3_0 == 10))
				{
					alt3 = 1;
				}
				switch (alt3)
				{
					case 1:
						// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:31:22:
						// 'numseats' numseats= NUMBER
					{
						match(input, 10, FOLLOW_10_in_newwagoncommand111);
						numseats = (Token) match(input, NUMBER, FOLLOW_NUMBER_in_newwagoncommand115);

					}
						break;

				}

				handler.newWagonCommand((ID2 != null ? ID2.getText() : null), (numseats != null ? numseats.getText() : null));

			}

		}

		finally
		{
		}
		return;
	}

	// $ANTLR end "newwagoncommand"

	// $ANTLR start "addcommand"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:34:1: addcommand :
	// 'add' add= ID 'to' to= ID ;
	public final void addcommand() throws RecognitionException
	{
		Token add = null;
		Token to = null;

		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:35:2: ( 'add'
			// add= ID 'to' to= ID )
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:35:4: 'add'
			// add= ID 'to' to= ID
			{
				match(input, 11, FOLLOW_11_in_addcommand130);
				add = (Token) match(input, ID, FOLLOW_ID_in_addcommand134);
				match(input, 12, FOLLOW_12_in_addcommand136);
				to = (Token) match(input, ID, FOLLOW_ID_in_addcommand140);
				handler.addCommand((add != null ? add.getText() : null), (to != null ? to.getText() : null));

			}

		}

		finally
		{
		}
		return;
	}

	// $ANTLR end "addcommand"

	// $ANTLR start "getcommand"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:38:1: getcommand :
	// 'getnumseats' type ID ;
	public final void getcommand() throws RecognitionException
	{
		Token ID4 = null;
		RichRailParser.type_return type3 = null;

		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:39:2: (
			// 'getnumseats' type ID )
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:39:4:
			// 'getnumseats' type ID
			{
				match(input, 13, FOLLOW_13_in_getcommand153);
				pushFollow(FOLLOW_type_in_getcommand155);
				type3 = type();

				state._fsp--;

				ID4 = (Token) match(input, ID, FOLLOW_ID_in_getcommand157);
				handler.getCommand((type3 != null ? input.toString(type3.start, type3.stop) : null), (ID4 != null ? ID4.getText() : null));

			}

		}

		finally
		{
		}
		return;
	}

	// $ANTLR end "getcommand"

	// $ANTLR start "delcommand"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:42:1: delcommand :
	// 'delete' type ID ;
	public final void delcommand() throws RecognitionException
	{
		Token ID6 = null;
		RichRailParser.type_return type5 = null;

		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:43:2: (
			// 'delete' type ID )
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:43:4: 'delete'
			// type ID
			{
				match(input, 14, FOLLOW_14_in_delcommand170);
				pushFollow(FOLLOW_type_in_delcommand172);
				type5 = type();

				state._fsp--;

				ID6 = (Token) match(input, ID, FOLLOW_ID_in_delcommand174);
				handler.delCommand((type5 != null ? input.toString(type5.start, type5.stop) : null), (ID6 != null ? ID6.getText() : null));

			}

		}

		finally
		{
		}
		return;
	}

	// $ANTLR end "delcommand"

	// $ANTLR start "remcommand"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:46:1: remcommand :
	// 'remove' remove= ID 'from' from= ID ;
	public final void remcommand() throws RecognitionException
	{
		Token remove = null;
		Token from = null;

		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:47:2: (
			// 'remove' remove= ID 'from' from= ID )
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:47:4: 'remove'
			// remove= ID 'from' from= ID
			{
				match(input, 15, FOLLOW_15_in_remcommand187);
				remove = (Token) match(input, ID, FOLLOW_ID_in_remcommand191);
				match(input, 16, FOLLOW_16_in_remcommand193);
				from = (Token) match(input, ID, FOLLOW_ID_in_remcommand197);
				handler.remCommand((remove != null ? remove.getText() : null), (from != null ? from.getText() : null));

			}

		}

		finally
		{
		}
		return;
	}

	// $ANTLR end "remcommand"

	public static class type_return extends ParserRuleReturnScope
	{
	};

	// $ANTLR start "type"
	// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:50:1: type : ( (
	// 'train' ) | ( 'wagon' ) );
	public final RichRailParser.type_return type() throws RecognitionException
	{
		RichRailParser.type_return retval = new RichRailParser.type_return();
		retval.start = input.LT(1);

		try
		{
			// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:50:7: ( (
			// 'train' ) | ( 'wagon' ) )
			int alt4 = 2;
			int LA4_0 = input.LA(1);

			if ((LA4_0 == 8))
			{
				alt4 = 1;
			}
			else
				if ((LA4_0 == 9))
				{
					alt4 = 2;
				}
				else
				{
					NoViableAltException nvae = new NoViableAltException("", 4, 0, input);

					throw nvae;
				}
			switch (alt4)
			{
				case 1:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:50:9: (
					// 'train' )
				{
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:50:9: (
					// 'train' )
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:50:10:
					// 'train'
					{
						match(input, 8, FOLLOW_8_in_type211);

					}

				}
					break;
				case 2:
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:50:21:
					// ( 'wagon' )
				{
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:50:21:
					// ( 'wagon' )
					// C:\\Users\\ejvos\\Documents\\richrail\\RichRail.g:50:22:
					// 'wagon'
					{
						match(input, 9, FOLLOW_9_in_type217);

					}

				}
					break;

			}
			retval.stop = input.LT(-1);

		}

		finally
		{
		}
		return retval;
	}

	// $ANTLR end "type"

	// Delegated rules

	public static final BitSet FOLLOW_newcommand_in_command49 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_addcommand_in_command53 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_getcommand_in_command57 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_delcommand_in_command61 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_remcommand_in_command65 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_newtraincommand_in_newcommand74 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_newwagoncommand_in_newcommand78 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_7_in_newtraincommand87 = new BitSet(new long[]
	{
		0x0000000000000100L
	});
	public static final BitSet FOLLOW_8_in_newtraincommand89 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_ID_in_newtraincommand91 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_7_in_newwagoncommand104 = new BitSet(new long[]
	{
		0x0000000000000200L
	});
	public static final BitSet FOLLOW_9_in_newwagoncommand106 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_ID_in_newwagoncommand108 = new BitSet(new long[]
	{
		0x0000000000000402L
	});
	public static final BitSet FOLLOW_10_in_newwagoncommand111 = new BitSet(new long[]
	{
		0x0000000000000020L
	});
	public static final BitSet FOLLOW_NUMBER_in_newwagoncommand115 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_11_in_addcommand130 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_ID_in_addcommand134 = new BitSet(new long[]
	{
		0x0000000000001000L
	});
	public static final BitSet FOLLOW_12_in_addcommand136 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_ID_in_addcommand140 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_13_in_getcommand153 = new BitSet(new long[]
	{
		0x0000000000000300L
	});
	public static final BitSet FOLLOW_type_in_getcommand155 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_ID_in_getcommand157 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_14_in_delcommand170 = new BitSet(new long[]
	{
		0x0000000000000300L
	});
	public static final BitSet FOLLOW_type_in_delcommand172 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_ID_in_delcommand174 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_15_in_remcommand187 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_ID_in_remcommand191 = new BitSet(new long[]
	{
		0x0000000000010000L
	});
	public static final BitSet FOLLOW_16_in_remcommand193 = new BitSet(new long[]
	{
		0x0000000000000010L
	});
	public static final BitSet FOLLOW_ID_in_remcommand197 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_8_in_type211 = new BitSet(new long[]
	{
		0x0000000000000002L
	});
	public static final BitSet FOLLOW_9_in_type217 = new BitSet(new long[]
	{
		0x0000000000000002L
	});

}