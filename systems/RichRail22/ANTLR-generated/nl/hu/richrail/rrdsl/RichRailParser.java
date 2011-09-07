// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g 2010-04-11 23:43:31

  package nl.hu.richrail.rrdsl;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RichRailParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "NUMBER", "WHITESPACE", "'new'", "'train'", "'wagon'", "'numseats'", "'add'", "'to'", "'getnumseats'", "'delete'", "'remove'", "'from'"
    };
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int NUMBER=5;
    public static final int WHITESPACE=6;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;

    // delegates
    // delegators


        public RichRailParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public RichRailParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return RichRailParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g"; }



    // $ANTLR start "command"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:17:1: command returns [Command result] : ( newcommand | addcommand | getcommand | delcommand | remcommand )* ;
    public final Command command() throws RecognitionException {
        Command result = null;

        Command newcommand1 = null;

        Command addcommand2 = null;

        Command getcommand3 = null;

        Command delcommand4 = null;

        Command remcommand5 = null;


        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:18:5: ( ( newcommand | addcommand | getcommand | delcommand | remcommand )* )
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:20:3: ( newcommand | addcommand | getcommand | delcommand | remcommand )*
            {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:20:3: ( newcommand | addcommand | getcommand | delcommand | remcommand )*
            loop1:
            do {
                int alt1=6;
                switch ( input.LA(1) ) {
                case 7:
                    {
                    alt1=1;
                    }
                    break;
                case 11:
                    {
                    alt1=2;
                    }
                    break;
                case 13:
                    {
                    alt1=3;
                    }
                    break;
                case 14:
                    {
                    alt1=4;
                    }
                    break;
                case 15:
                    {
                    alt1=5;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:20:5: newcommand
            	    {
            	    pushFollow(FOLLOW_newcommand_in_command54);
            	    newcommand1=newcommand();

            	    state._fsp--;

            	     result = newcommand1; 

            	    }
            	    break;
            	case 2 :
            	    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:21:5: addcommand
            	    {
            	    pushFollow(FOLLOW_addcommand_in_command62);
            	    addcommand2=addcommand();

            	    state._fsp--;

            	     result = addcommand2; 

            	    }
            	    break;
            	case 3 :
            	    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:22:5: getcommand
            	    {
            	    pushFollow(FOLLOW_getcommand_in_command70);
            	    getcommand3=getcommand();

            	    state._fsp--;

            	     result = getcommand3; 

            	    }
            	    break;
            	case 4 :
            	    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:23:5: delcommand
            	    {
            	    pushFollow(FOLLOW_delcommand_in_command78);
            	    delcommand4=delcommand();

            	    state._fsp--;

            	     result = delcommand4; 

            	    }
            	    break;
            	case 5 :
            	    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:24:5: remcommand
            	    {
            	    pushFollow(FOLLOW_remcommand_in_command86);
            	    remcommand5=remcommand();

            	    state._fsp--;

            	     result = remcommand5; 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "command"


    // $ANTLR start "newcommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:28:1: newcommand returns [Command result] : ( newtraincommand | newwagoncommand );
    public final Command newcommand() throws RecognitionException {
        Command result = null;

        Command newtraincommand6 = null;

        Command newwagoncommand7 = null;


        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:29:3: ( newtraincommand | newwagoncommand )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==7) ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==8) ) {
                    alt2=1;
                }
                else if ( (LA2_1==9) ) {
                    alt2=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:30:6: newtraincommand
                    {
                    pushFollow(FOLLOW_newtraincommand_in_newcommand120);
                    newtraincommand6=newtraincommand();

                    state._fsp--;

                     result = newtraincommand6; 

                    }
                    break;
                case 2 :
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:31:6: newwagoncommand
                    {
                    pushFollow(FOLLOW_newwagoncommand_in_newcommand129);
                    newwagoncommand7=newwagoncommand();

                    state._fsp--;

                     result = newwagoncommand7; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "newcommand"


    // $ANTLR start "newtraincommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:35:1: newtraincommand returns [Command result] : 'new' 'train' ID ;
    public final Command newtraincommand() throws RecognitionException {
        Command result = null;

        Token ID8=null;

        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:36:3: ( 'new' 'train' ID )
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:37:5: 'new' 'train' ID
            {
            match(input,7,FOLLOW_7_in_newtraincommand161); 
            match(input,8,FOLLOW_8_in_newtraincommand163); 
            ID8=(Token)match(input,ID,FOLLOW_ID_in_newtraincommand165); 
             result = new NewTrainCommand((ID8!=null?ID8.getText():null));

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "newtraincommand"


    // $ANTLR start "newwagoncommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:41:1: newwagoncommand returns [Command result] : 'new' 'wagon' ID ( 'numseats' NUMBER )? ;
    public final Command newwagoncommand() throws RecognitionException {
        Command result = null;

        Token NUMBER9=null;
        Token ID10=null;

        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:42:3: ( 'new' 'wagon' ID ( 'numseats' NUMBER )? )
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:43:5: 'new' 'wagon' ID ( 'numseats' NUMBER )?
            {
            int nums = 20;
            match(input,7,FOLLOW_7_in_newwagoncommand199); 
            match(input,9,FOLLOW_9_in_newwagoncommand201); 
            ID10=(Token)match(input,ID,FOLLOW_ID_in_newwagoncommand203); 
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:44:21: ( 'numseats' NUMBER )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==10) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:44:22: 'numseats' NUMBER
                    {
                    match(input,10,FOLLOW_10_in_newwagoncommand206); 
                    NUMBER9=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_newwagoncommand208); 
                    nums = Integer.parseInt((NUMBER9!=null?NUMBER9.getText():null));

                    }
                    break;

            }

             result = new NewWaggonCommand( (ID10!=null?ID10.getText():null),(ID10!=null?ID10.getText():null),nums); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "newwagoncommand"


    // $ANTLR start "addcommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:48:1: addcommand returns [Command result] : 'add' i1= ID 'to' i2= ID ;
    public final Command addcommand() throws RecognitionException {
        Command result = null;

        Token i1=null;
        Token i2=null;

        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:49:3: ( 'add' i1= ID 'to' i2= ID )
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:50:5: 'add' i1= ID 'to' i2= ID
            {
            match(input,11,FOLLOW_11_in_addcommand242); 
            i1=(Token)match(input,ID,FOLLOW_ID_in_addcommand246); 
            match(input,12,FOLLOW_12_in_addcommand248); 
            i2=(Token)match(input,ID,FOLLOW_ID_in_addcommand252); 
             result =  new AddCommand( (i1!=null?i1.getText():null),(i2!=null?i2.getText():null)); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "addcommand"


    // $ANTLR start "getcommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:54:1: getcommand returns [Command result] : 'getnumseats' type ID ;
    public final Command getcommand() throws RecognitionException {
        Command result = null;

        Token ID12=null;
        RichRailParser.type_return type11 = null;


        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:55:3: ( 'getnumseats' type ID )
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:56:5: 'getnumseats' type ID
            {
            match(input,13,FOLLOW_13_in_getcommand279); 
            pushFollow(FOLLOW_type_in_getcommand281);
            type11=type();

            state._fsp--;

            ID12=(Token)match(input,ID,FOLLOW_ID_in_getcommand283); 
             result =  new GetCommand((type11!=null?input.toString(type11.start,type11.stop):null),(ID12!=null?ID12.getText():null)); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "getcommand"


    // $ANTLR start "delcommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:60:1: delcommand returns [Command result] : ( delwaggoncommand | deltraincommand );
    public final Command delcommand() throws RecognitionException {
        Command result = null;

        Command delwaggoncommand13 = null;

        Command deltraincommand14 = null;


        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:61:3: ( delwaggoncommand | deltraincommand )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==14) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==9) ) {
                    alt4=1;
                }
                else if ( (LA4_1==8) ) {
                    alt4=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:62:6: delwaggoncommand
                    {
                    pushFollow(FOLLOW_delwaggoncommand_in_delcommand312);
                    delwaggoncommand13=delwaggoncommand();

                    state._fsp--;

                    result = delwaggoncommand13;

                    }
                    break;
                case 2 :
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:63:7: deltraincommand
                    {
                    pushFollow(FOLLOW_deltraincommand_in_delcommand322);
                    deltraincommand14=deltraincommand();

                    state._fsp--;

                    result = deltraincommand14;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "delcommand"


    // $ANTLR start "delwaggoncommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:66:1: delwaggoncommand returns [Command result] : 'delete' 'wagon' ID ;
    public final Command delwaggoncommand() throws RecognitionException {
        Command result = null;

        Token ID15=null;

        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:67:1: ( 'delete' 'wagon' ID )
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:68:1: 'delete' 'wagon' ID
            {
            match(input,14,FOLLOW_14_in_delwaggoncommand339); 
            match(input,9,FOLLOW_9_in_delwaggoncommand341); 
            ID15=(Token)match(input,ID,FOLLOW_ID_in_delwaggoncommand343); 
             result = new DelWaggonCommand((ID15!=null?ID15.getText():null));

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "delwaggoncommand"


    // $ANTLR start "deltraincommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:72:1: deltraincommand returns [Command result] : 'delete' 'train' ID ;
    public final Command deltraincommand() throws RecognitionException {
        Command result = null;

        Token ID16=null;

        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:73:1: ( 'delete' 'train' ID )
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:74:3: 'delete' 'train' ID
            {
            match(input,14,FOLLOW_14_in_deltraincommand360); 
            match(input,8,FOLLOW_8_in_deltraincommand362); 
            ID16=(Token)match(input,ID,FOLLOW_ID_in_deltraincommand364); 
             result = new DelTrainCommand((ID16!=null?ID16.getText():null));

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "deltraincommand"


    // $ANTLR start "remcommand"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:78:1: remcommand returns [Command result] : 'remove' i1= ID 'from' i2= ID ;
    public final Command remcommand() throws RecognitionException {
        Command result = null;

        Token i1=null;
        Token i2=null;

        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:79:3: ( 'remove' i1= ID 'from' i2= ID )
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:80:4: 'remove' i1= ID 'from' i2= ID
            {
            match(input,15,FOLLOW_15_in_remcommand387); 
            i1=(Token)match(input,ID,FOLLOW_ID_in_remcommand391); 
            match(input,16,FOLLOW_16_in_remcommand393); 
            i2=(Token)match(input,ID,FOLLOW_ID_in_remcommand397); 
             result = new RemCommand( (i1!=null?i1.getText():null),(i2!=null?i2.getText():null)); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "remcommand"

    public static class type_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "type"
    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:84:1: type : ( ( 'train' ) | ( 'wagon' ) );
    public final RichRailParser.type_return type() throws RecognitionException {
        RichRailParser.type_return retval = new RichRailParser.type_return();
        retval.start = input.LT(1);

        try {
            // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:84:9: ( ( 'train' ) | ( 'wagon' ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==8) ) {
                alt5=1;
            }
            else if ( (LA5_0==9) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:84:11: ( 'train' )
                    {
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:84:11: ( 'train' )
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:84:12: 'train'
                    {
                    match(input,8,FOLLOW_8_in_type417); 

                    }


                    }
                    break;
                case 2 :
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:84:23: ( 'wagon' )
                    {
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:84:23: ( 'wagon' )
                    // /Users/mklijn/Documents/workspaces/J2SE/rich-rail/src/nl/hu/richrail/rrdsl/RichRail.g:84:24: 'wagon'
                    {
                    match(input,9,FOLLOW_9_in_type423); 

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "type"

    // Delegated rules


 

    public static final BitSet FOLLOW_newcommand_in_command54 = new BitSet(new long[]{0x000000000000E882L});
    public static final BitSet FOLLOW_addcommand_in_command62 = new BitSet(new long[]{0x000000000000E882L});
    public static final BitSet FOLLOW_getcommand_in_command70 = new BitSet(new long[]{0x000000000000E882L});
    public static final BitSet FOLLOW_delcommand_in_command78 = new BitSet(new long[]{0x000000000000E882L});
    public static final BitSet FOLLOW_remcommand_in_command86 = new BitSet(new long[]{0x000000000000E882L});
    public static final BitSet FOLLOW_newtraincommand_in_newcommand120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newwagoncommand_in_newcommand129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_7_in_newtraincommand161 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_newtraincommand163 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_newtraincommand165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_7_in_newwagoncommand199 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_newwagoncommand201 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_newwagoncommand203 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_10_in_newwagoncommand206 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NUMBER_in_newwagoncommand208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_addcommand242 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_addcommand246 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_addcommand248 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_addcommand252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_getcommand279 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_type_in_getcommand281 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_getcommand283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_delwaggoncommand_in_delcommand312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deltraincommand_in_delcommand322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_delwaggoncommand339 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_delwaggoncommand341 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_delwaggoncommand343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_deltraincommand360 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_deltraincommand362 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_deltraincommand364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_remcommand387 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_remcommand391 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_remcommand393 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_remcommand397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_8_in_type417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_type423 = new BitSet(new long[]{0x0000000000000002L});

}