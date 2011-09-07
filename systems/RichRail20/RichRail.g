grammar RichRail;

options {
	language = Java;
//	output=AST;
}

@header {
package com.ns.richrail.command;
}
@lexer::header {
package com.ns.richrail.command;
}
@members {
public CommandHandler handler;
}
@rulecatch {
}

command
	: newcommand | addcommand | getcommand | delcommand | remcommand;

newcommand
	: newtraincommand | newwagoncommand;

newtraincommand
	: 'new' 'train' ID {handler.newTrainCommand($ID.text);}
	;

newwagoncommand
	: 'new' 'wagon' ID ('numseats' numseats=NUMBER)? {handler.newWagonCommand($ID.text, $numseats.text);}
	;

addcommand
	: 'add' add=ID 'to' to=ID {handler.addCommand($add.text, $to.text);}
	;

getcommand
	: 'getnumseats' type ID {handler.getCommand($type.text, $ID.text);}
	;

delcommand
	: 'delete' type ID {handler.delCommand($type.text, $ID.text);}
	;

remcommand
	: 'remove' remove=ID 'from' from=ID {handler.remCommand($remove.text, $from.text);}
	;

type		: ('train') | ('wagon');
ID		: ('a'..'z')('a'..'z'|'0'..'9')*;
NUMBER		: ('0'..'9')+;
WHITESPACE	: ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ { $channel = HIDDEN; };