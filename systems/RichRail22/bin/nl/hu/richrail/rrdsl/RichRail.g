grammar RichRail;

options{
 language = Java;
//   output=AST;
//  ASTLabelType=CommonTree;
}
@header{
  package nl.hu.richrail.rrdsl;
}

@lexer::header
{
  package nl.hu.richrail.rrdsl;
}
  
command returns [Command result]
    :
 
  ( newcommand { $result = $newcommand.result; }
  | addcommand { $result = $addcommand.result; }
  | getcommand { $result = $getcommand.result; }
  | delcommand { $result = $delcommand.result; }
  | remcommand { $result = $remcommand.result; })*
    //EOF{ $result = null; }
    ;

newcommand  returns [Command result]
  :
     newtraincommand { $result = $newtraincommand.result; }
   | newwagoncommand { $result = $newwagoncommand.result; }
     
  ;
  
newtraincommand returns [Command result]
  : 
    'new' 'train' ID
    { $result = new NewTrainCommand($ID.text);}
  ;
  
newwagoncommand returns [Command result]
  :
    {int nums = 20;}
   'new' 'wagon' ID ('numseats' NUMBER {nums = Integer.parseInt($NUMBER.text);})?
   { $result = new NewWaggonCommand( $ID.text,$ID.text,nums); }
  ;
  
addcommand returns [Command result] 
  : 
    'add' i1=ID 'to' i2=ID
    { $result =  new AddCommand( $i1.text,$i2.text); }
  ;

getcommand returns [Command result]
  :
    'getnumseats' type ID
    { $result =  new GetCommand($type.text,$ID.text); }
  ;

delcommand  returns [Command result]
  :
     delwaggoncommand {$result = $delwaggoncommand.result;}
     |deltraincommand {$result = $deltraincommand.result;}
  ;

delwaggoncommand returns [Command result]
:
'delete' 'wagon' ID
{ $result = new DelWaggonCommand($ID.text);}

;
deltraincommand returns [Command result]
:
  'delete' 'train' ID
  { $result = new DelTrainCommand($ID.text);}
;

remcommand  returns [Command result]
  :
   'remove' i1=ID 'from' i2=ID
   { $result = new RemCommand( $i1.text,$i2.text); }
  ;

type    : ('train') | ('wagon');

ID    : ('a'..'z')('a'..'z'|'0'..'9')*;
NUMBER    : ('0'..'9')+;
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ { $channel = HIDDEN; } ;
