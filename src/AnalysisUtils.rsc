module AnalysisUtils

import String;
import List;
import IO;
import Set;
import Map;
import Relation;

alias conceptClasses = set[str];
alias conceptAttributes = rel[str,str];
alias associations = rel[str,str]; // check if cardinality too in this tuple
alias relations = rel[str,str]; 

public list[str] javaKeywords = ["abstract","continue","for","new","switch","assert","default",
	"goto","package","synchronized","boolean","do","if","private","this","break","double",
	"implements","protected","throw","byte","else","import","public","throws","case","enum",
	"instanceof","return","transient","catch","extends","int","short","try","char","final",
	"interface","static","void","class","finally","long","strictfp","volatile","const","float",
	"native","super","while"];
public list[str] javaLiterals = ["true","false","null"];
public list[str] javaOftenused = ["String", "java", "equals", "Override"]; // todo: more? chk 15-06-2011	
public list[str] methodActions = ["get", "set", "create", "delete", "update", "attach", 
								"detach", "execute", "add", "check", "remove"];
	
public list[str] javaStopwords = javaKeywords + javaLiterals + javaOftenused + methodActions;
	
// TODO check if this really works good!
private bool isComment = false;
public str removeComment(str line) {

        emptyLine = "";

        /* replaceAll functie is buggy; laat dubbele spaties achter */
        line = replaceAll(replaceAll(line,"  "," "),"\t","");
        
        if (isEmpty(line)) {
                return emptyLine;
        }

        /* als nog geen comment is dan gooi regels weg die helemaal comment zijn */
        if (!isComment) {
                if (startsWith(line,"//")) {
                        return emptyLine;
                }

                if (startsWith(line,"/*") && endsWith(line,"*/")) {
                        return emptyLine;
                }

                /* zoek het begin van comment */
                if (/^<before:.*>\/\/.*$/ := line) {
                        return before;
                }

                if (/^<before:.*>\/\*<rest:.*>$/ := line) {
                        isComment = true;
                        
                        if (/^.*\*\/<after:.*>$/ := rest) {
                                isComment = false;
                                return before + after;
                        }
                        return before;
                }

                return line;
        }

        /* we zitten al in een comment en zoeken nu het einde */
        if (/^<before:.*>\*\/<after:.*>$/ := line) {
                isComment = false;
                return after;
        }
        else {
                return emptyLine;
        }

}

public list[str] splitString( str input ) {
	list[str] l = [];
	for (/<word:\w+>/ := input) {
		l += word; 
	}
	return l;
}

public list[str] splitIdentifiers( str identifier ) {
	list[str] identifierParts = [];
// check if identifier contains underscores
// if yes, split identifier and then check for camelCase with all parts
	list[str] identifierPartsTemp = splitString( replaceAll( identifier, "_", " ") );
	if ( size( identifierPartsTemp ) > 1 )
		for ( part <- identifierPartsTemp) {
			identifierParts += splitCamelCase( part );
		}
	else
		identifierParts = splitCamelCase( identifier );	 

    return identifierParts;
}

private list[str] splitCamelCase( str identifier ) {
	list[str] identifierParts = [];
    if (/<verb:[a-z]*>/ := identifier) {
        if (verb != "" ) {
	        identifierParts += verb;
        	identifier = substring( identifier, size(verb) );
        }
    }
    // if there multiple capitals they must be seen as one word
	if ( /<part:[A-Z][A-Z].*>/ := identifier ) {
		result = resolveMultipleCapitals( < identifier, ""> );
		identifierParts += result[1];
        identifier = substring( identifier, size(result[1]) );
	}
    
    while ( /<part:[A-Z][a-z]*>/ := identifier ) {
		identifierParts += part;
        identifier = substring( identifier, size(part) );
	}
	
    return identifierParts;
}
 
// recursive function, input[0] is the identifier and input[1] are all already identified capitals 
public tuple[str,str] resolveMultipleCapitals( tuple[str,str] input) {
	tuple[str,str] result = input;
	if ( /<part:[A-Z][A-Z].*>/ := input[0] || size(input[0]) == 1 ) {
		//tuple[str,str] newinput = < subString( input[0], 1 ), input[1] + subString( input[0], 0, 1 )>;
		result = resolveMultipleCapitals( < substring( input[0], 1 ), input[1] + substring( input[0], 0, 1 )> );
	}
	return result;
}
  
public set[str] getIdentifierSuperset( str identifier ) {
	set[str] identifierSuperset = {};
	// todo use a comprehension to make a list with all possible bitmaps
	idlist = splitIdentifiers( identifier );
	partCount = size( idlist );

	for( i <- [1..pow( 2, partCount ) - 1] ) {
		str newID = "";
		curr = i - 1;
	   	for (j <- [1..partCount]) {
	   		if ( curr%2 == 0 ) {
	   			newID = newID + idlist[j-1];
	   		}	
   			curr = curr / 2;
	   	}
	   	identifierSuperset = identifierSuperset + {newID};
	}

	return identifierSuperset;
}

public int pow( int base, int exponent ) {
	if ( exponent == 0 ) return 1;
	
	if ( exponent == 1 ) {
		return base;
	}
	else {	
		return base * pow( base, exponent - 1 );
	}
}

public bool isTrueSubstring( str sinput, str spart ) {
	if (sinput == spart || size(sinput) < size( spart )) return false;
	for (i <- [0..size( sinput )-size(spart)] ) {
		if ( startsWith( substring( sinput, i), spart ) ) return true;
	}
	return false;
	//return (/^<pre:.*?><spart><post:.*>$/ := sinput);
	//return ( size( replaceAll( sinput, spart, "" ) ) < size( sinput ) );
}

public str capitalize(str word) {
   if(/^<letter:[a-z]><rest:.*$>/ := word)
      return toUpperCase(letter) + rest;
   else
     return word;
}

public void writePlantUMLold( set[str] classCandidates, str filepath ) {
	str puml = "@startuml\n";
	puml += "note \"" + filepath + "\" as N1\n";
	for(s <- classCandidates) {
		puml += "class " + s + "\n";
	}
	
	puml += "@enduml";

	loc pumlFile = |file://t|;
	pumlFile.uri = "file://" + filepath;
	writeFile(pumlFile, puml);
}

public conceptClasses getConceptualClasses( map[real, set[str]] probs, real threshold ) {
	list[real] v = toList( domain( probs ) );
	conceptClasses cc = {};
	v = reverse( sort( v ) );
	for( r <- v ) {
		if( r >= threshold ) { 
			cc += probs[r];
		}
	}
	return cc;
}

public void writePlantUML( conceptClasses cc, conceptAttributes ca, associations ass, relations rl, str filepath ) {
	str puml = "@startuml\n";
	puml += "note \"" + filepath + "\" as N1\n";
	for(s <- cc) {
		puml += "class " + s + "\n";
	}
	for(s <- ca) {
		puml += s[0] + " : " + s[1] + "\n";
	}
	for(s <- ass) {
		puml += s[0] + " --\> " + s[1] + "\n";
	}
	
	puml += "@enduml";

	loc pumlFile = |file://t|;
	pumlFile.uri = "file://" + filepath;
	writeFile(pumlFile, puml);
}

public map[real,set[str]] invert2( map[str,real] input) {
	map[real,set[str]] output = ();
	for(i <- input) {
    	if( output[ input[i] ]? ) {
    		output[ input[i] ] = output[ input[i] ] + i;
    	} 
    	else {
    		output += (input[i]:{i});
    	}
	}
	return output;
}

public associations getAssociations( set[str] concepts, rel[str,str] relations ) {
	con = ident( concepts );
	ca1 = con o relations;
	ca2 = ca1 o con;
	associations ca = ca2;
	return ca;
}
