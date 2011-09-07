module FactExtraction

import util::Resources;
import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import IO;
import Relation;
import Integer;
import Set;
import List;
import String;
import AnalysisUtils;
import LDA;

public rel[str,str] getFactsCalls( Resource allFacts ) {
  	rel[str, str] ClassCallsClass = {<c,n>| <entity([_,class(c),_]),entity([_,class(n),_])> <- allFacts@calls, c != n }; 
	//println( ClassCallsClass );
	return ClassCallsClass;
}
public rel[str,str] getFactsCalledMethods( Resource allFacts ) {
	rel[str,str] ClassCallsMethods = {};
	try {
	    println( "bla" );
	    ClassCallsMethods += { <c,m> | <entity([ida*,class(c),z*]),entity([idb*,class(_),method(m,_,_)])> <- (allFacts@calls), idb[0].name != "java", idb[0].name != "javax"};
	    ClassCallsMethods += { <c,m> | <entity([ida*,class(c),z*]),entity([idb*,interface(_),method(m,_,_)])> <- (allFacts@calls), idb[0].name != "java", idb[0].name != "javax"};
	    ClassCallsMethods += { <c,m> | <entity([class(c),z*]),entity([idb*,class(_),method(m,_,_)])> <- (allFacts@calls), idb[0].name != "java", idb[0].name != "javax"};
	    ClassCallsMethods += { <c,m> | <entity([class(c),z*]),entity([idb*,interface(_),method(m,_,_)])> <- (allFacts@calls), idb[0].name != "java", idb[0].name != "javax"};
    } catch :
	{
		println("tja");
	    ClassCallsMethods += { <c,m> | <entity([class(c),z*]),entity([class(_),method(m,_,_)])> <- (allFacts@calls)};
	    ClassCallsMethods += { <c,m> | <entity([class(c),z*]),entity([interface(_),method(m,_,_)])> <- (allFacts@calls)};
	}

	rel[str,str] splitMethods = {};
	
	for( relCF <- ClassCallsMethods ) {
		for( wa <- splitIdentifiers( relCF[1] ) ) {
		//for( wa <- getIdentifierSuperset( relCF[1] ) ) {
			if ( toLowerCase( wa ) notin {"get", "set", "create", "delete", "update", "attach", 
							"detach", "execute", "add", "check", "remove"} ) { 
				splitMethods += <relCF[0],capitalize( stemWordPlural( wa ) )>;
			}
		}
	}
	//println( size( stemmedFields ) );
	return splitMethods;
	//return ClassCallsMethods;
}

public rel[str,str] getFactsTypesUsed( Resource allFacts ) {
	// first get all toptypes, as we're only interested in these 
	topTypes = { c | entity([id*,class(c)]) <- (allFacts@declaredTopTypes) };
	// get all type usages and remove all types which are not declared in this project
  	rel[str, str] typesUsed = {<(splitString(c.path))[size(splitString(c.path))-2],n> | <c,entity([_*,class(n)])> <- allFacts@types, n in topTypes }; 
	// remove self references
	typesUsed = typesUsed - ident( domain( typesUsed ) );

	return typesUsed;
}

public rel[str,str] getFactsTypesUsedSplit( Resource allFacts ) {
	rel[str,str] typesUsedSplit = {};
	
	// get called types first
	typesUsed = getFactsTypesUsed( allFacts );
	
	for( relPart <- typesUsed ) {
	// todo: IdentifierSuperset or just splitIdentifiers?
		for( is <- splitIdentifiers( relPart[1] ) ) {
		//for( is <- getIdentifierSuperset( relPart[1] ) ) {
			typesUsedSplit += <relPart[0],is>;
		}
		// use only if not idenitfiersuperset is used 			
		typesUsedSplit += <relPart[0],relPart[1]>;
	}
	return typesUsedSplit;
}


public rel[str,str] getFactsFields() {
    rel[str, str] fields = { <c.path,veld> | <c,entity([id*,field(veld)])> <- ((allFacts@fieldDecls))};
//	f = { c | c <- ((allFacts@fields)<1,0>), entity([id*,field(_)]) := c<0>};

	print(  ({ <c.path,clas> | <c,entity([id*,class(clas)])> <- (allFacts@classes)})<1,0> o 
				{ <c.path,veld> | <c,entity([id*,field(veld)])> <- (allFacts@fieldDecls)}  );

  	return fields; 
}

public rel[str,str] getFactsFieldperClass() {
    rel[str, str] fields = ({ <c.path,clas> | <c,entity([id*,class(clas)])> <- (allFacts@classes)})<1,0> o 
				{ <c.path,veld> | <c,entity([id*,field(veld)])> <- (allFacts@fieldDecls)};

  	return fields; 
}

public rel[str,str] getFactsClassMethods( Resource allFacts ) {
    rel[str, str] methods = { <c,veld> | <entity([id*,class(c)]),entity([id*,method(veld,_,_)])> <- (allFacts@declaredMethods)};
  	return methods; 
}

public rel[str,str] getFactsClassMethodsSplitNames( Resource allFacts ) {
	rel[str,str] pureMethods = getFactsClassMethods( allFacts );
	rel[str,str] splitMethods = {};
	
	for( relCF <- pureMethods ) {
		for( wo <- getIdentifierSuperset( relCF[0] ) ) {
			for( wa <- splitIdentifiers( relCF[1] ) ) {
			//for( wa <- getIdentifierSuperset( relCF[1] ) ) {
			// todo: replace with methodActions-list from AnalysisUtils chk
				if ( toLowerCase( wa ) notin {"get", "set", "create", "delete", "update", "attach", 
								"detach", "execute", "add", "check", "remove"} ) { 
					splitMethods += <capitalize( stemWordPlural( wo )),capitalize( stemWordPlural( wa ) )>;
					//splitMethods += <wo,capitalize( stemWordPlural( wa ) )>;
				}
				//stemmedFields += <wo,stemWord( toLowerCase( wa ) )>;
			}
		}
	}
	//println( size( stemmedFields ) );
	return splitMethods;
}

public rel[str,str] getFactsVariables() {
    rel[str, str] fields = { <c.path,veld> | <c,entity([id*,method(veld,_,_),_])> <- ((allFacts@variables))};
  	return fields; 
}

public rel[str,str] getFactsClassFields( Resource allFacts) {
    rel[str, str] fields = { <readable(c),f> | <c,entity([id*,field(f)])> <- (allFacts@declaredFields)};
  	return fields; 
}

public rel[str,str] getFactsClassTypes( Resource allFacts ) {
	for ( test2 <- allFacts@methodBodies ) {
		println( test2 );
	}
    rel[str, str] fields = { <readable(c),f> | <c,entity([id*,field(f)])> <- (allFacts@declaredFields)};
  	return fields; 
}

public rel[str,str] getFactsClassFieldsWithInheritance( Resource allFacts ) {
	rel[str,str] allFields;
    rel[str, str] fields = { <c,f> | <entity([id*,class(c)]),entity([id*,field(f)])> <- (allFacts@declaredFields)};
    // now add fields from the transitive closure of the extends relation
    rel[str,str] classExtends = { <cSub, cSuper> | <entity([id*,class(cSub)]),entity([id*,class(cSuper)])> <- (allFacts@extends), cSuper != "Object" }; 

    allFields = fields + classExtends o fields;

  	return allFields; 
}

public rel[str,str] getFactsStemmedSplitClassFieldsWithInheritance( Resource allFacts ) {
	rel[str,str] pureFields = getFactsClassFieldsWithInheritance( allFacts );
	rel[str,str] stemmedFields = {};
	
	for( relCF <- pureFields ) {
		for( wo <- getIdentifierSuperset( relCF[0] ) ) {
			//for( wa <- splitIdentifiers( relCF[1] ) ) {
			for( wa <- getIdentifierSuperset( relCF[1] ) ) {
				stemmedFields += <capitalize( stemWordPlural( wo )),capitalize( stemWordPlural( wa ) )>;
				//stemmedFields += <wo,capitalize( stemWordPlural( wa ) )>;
			}
		}
	}
	return stemmedFields;
}

public rel[str,str] getFactsPackageCalls ( Resource allFacts ) {
	rel[str,str] packageCalls = {};

	try {
	    packCalls = { <ida,idb> | <entity([ida*,class(_),z*]),entity([idb*,class(_),_])> <- (allFacts@calls), idb[0].name != "java", idb[0].name != "javax"};
	    packCalls += { <ida,idb> | <entity([ida*,class(_),z*]),entity([idb*,interface(_),_])> <- (allFacts@calls), idb[0].name != "java", idb[0].name != "javax"};
	    //methods = { <ida+package(ida1),idb+package(idb1)> | <entity([ida*,package(ida1),class(_),z*]),entity([idb*,package(idb1),class(_),_])> <- (allFacts@calls), idb[0].name != "java", idb[0].name != "javax"};
	    
	    for ( pc <- packCalls ) {
	    	str packageOne = "";
	    	str packageTwo = "";
	    	visit( pc[0] ) {
	    		case package(p): packageOne += "." + p;
	    	}
	    	//for (p2 <- m[1]) {
	    	//	packageTwo += "." + p2.name;
	    	//}
			visit( pc[1] ) {
	    		case package(p): packageTwo += "." + p;
			}
			// ignore calls to org.apache and calls from the default package (e.g. from a main class) 
	    	if ( !startsWith( packageTwo, ".org.apache" ) && !isEmpty( packageOne) ) 
	    		packageCalls += <substring( packageOne,1), substring( packageTwo,1)>;
	    }
	   	packageCalls += <"XXX", "XXX">;
	   	packageCalls += ident( carrier( packageCalls ));
	}
	catch: return {};

	return packageCalls;
}

public rel[str,str] getFactsClassesPerPackage( Resource allFacts ) {
	rel[str,str] classPerPackage = {};

    classPack = { <c,ida> | entity([ida*,class(c)]) <- (allFacts@declaredTopTypes)};
    classPack += { <i,ida> | entity([ida*,interface(i)]) <- (allFacts@declaredTopTypes)};
    
    for ( cp <- classPack ) {
    	str packageOne = "";
    	visit( cp[1] ) {
    		case package(p): packageOne += "." + p;
    	}
    	if (!isEmpty( packageOne ))  // we can have classes in the default package
			classPerPackage += <cp[0], substring( packageOne,1)>;
    }

    return classPerPackage;
}