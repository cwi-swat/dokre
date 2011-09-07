module fcatest
import experiments::Concept::GetFigure;
import experiments::Concept::Fca;
import experiments::Concept::Types;
import vis::Render; 
import vis::Figure; 

import util::Resources;
import lang::java::jdt::Java;
import lang::java::jdt::JDT;
import IO;
import Relation;
import Integer;
import Set;
import List;
import String;
import Real;
import Map;

import AnalysisUtils;
import FactExtraction;
import LatticeAnalyzer;

import LDA;
import FCA;

public loc prj = |project://floreantpos|;
//public loc prj = |project://JHotDraw6|;
public Resource allFacts = extractProject(prj); 

public void testFunction() {
//    rel2cxt( getFactsClassFields() + getFactsClassMethods() );
	//rel[str,str] rels = getFactsClassFieldsWithInheritance( allFacts ); 
	//rel2cxt( rels<1,0> );

//print( getFactsTypesUsed( allFacts ) );
//print( allFacts@types );

    rel2cxt( getFactsClassMethods( allFacts) , "C:/chk/fca/RichRail8methods.cxt" );
    lattice = getLatticeFromCXT( "C:/chk/fca/RichRail8methods.cxt" );
    println( getClassMethodProbs( lattice ) );
}

public void testLDA() {
	//list[loc] files = [];
	for ( i <- [1..8] ) {
	//for ( i <- [9..14] ) {
		if ( i != 7 ) {
			str prjname = "RichRail" + toString( i );
			str filepath = "C:/chk/lda/RichRailTests/" + prjname;
			loc prj = |project://dummy|;
			prj.uri = "project://" + prjname;
			println( prj);
			//Resource allPRJFacts = extractProject(prj);
			source2LDAInput( prj, filepath + ".txt" );

			map[str,value] options = ();
			options += ("est":true);
			options += ("inf":false);
			options += ("dfile": prjname + ".txt");
			options += ("dir":"C:/chk/lda/RichRailTests/");
			options += ("K": 1);
			options += ("alpha":50.0/5.0);
			options += ("beta": 0.1);
			options += ("twords": 10);
			options += ("niters":2000);
			options += ("savestep":5000);
			options += ("modelName": prjname);
			startLDA( options );
		}
	}
}

public void testCombination() {
	// 1-8: a-klas
	// 9-14: innoklas
	// 15-18: b-klas
	// 19-23: Inno oud
	
	for ( i <- [2..2] ) {
	//for ( i <- [9..14] ) {
		real threshold = 1.3;
		if ( i != 7 ) {
			//str prjname = "JHotDraw" + toString( i );
			//str prjname = "javamonopoly";
			//str prjname = "synPos";
			str prjname = "RichRail" + toString( i );
			str filepath = "C:/chk/fca/RichRailTestsSplit/" + prjname;
			loc prj = |project://dummy|;
			prj.uri = "project://" + prjname;
			println( prj);
			Resource allPRJFacts = extractProject(prj);
			println( "Facts from " + prjname + " extracted");

			// classfields
			currentprobs = calcProbSplitClassField( allPRJFacts, filepath ); 
		    println( "probs from classFields: ");
		    printProbs( invert2( currentprobs ) );

		    // called Types
			newprobs = calcProbClassCalledTypes( allPRJFacts, filepath );
		    println( "probs from called Types: ");
		    printProbs( invert2( newprobs ) );
		    currentprobs = mergeProbs( currentprobs, newprobs ) ;

			// called types - split
			newprobs = calcProbClassCalledTypesSplit( allPRJFacts, filepath );
		    println( "probs from called split Types: ");
		    printProbs( invert2( newprobs ) );
		    currentprobs = mergeProbs( currentprobs, newprobs ) ;

		    // called Methods
		    newprobs = calcProbClassMethod( allPRJFacts, filepath );
		    println( "probs from called Methods: ");
		    printProbs( invert2( newprobs ) );
		    currentprobs = mergeProbs( currentprobs, newprobs ) ;
		    newprobs = calcProbClassMethodBottomUp( allPRJFacts, filepath );
		    println( "probs from called bottom up Methods: ");
		    printProbs( invert2( newprobs ) );
		    currentprobs = mergeProbs( currentprobs, newprobs ) ;
		    
			if ( size( allPRJFacts@packages ) > 1) {
				newprobs = calcProbPackageCalls( allPRJFacts, filepath );
			    println( "probs from package calls: ");
			    printProbs( invert2( newprobs ) );
			    currentprobs = mergeProbs( currentprobs, newprobs ) ;
			    threshold = 1.5;
		    }


			//for( t1 <- allPRJFacts@calls) println( t1 );
		
		    //newprobs = calcProbClassCalledMethods( allPRJFacts, filepath );
		    //println( "probs from called Methods: ");
		    //printProbs( invert2( newprobs ) );
		    //currentprobs = mergeProbs( currentprobs, newprobs ) ;
println( currentprobs );
			currentprobs = removeTechnicalConcepts( currentprobs );

		    // compute the overall domain probs
		    println( "final probs: " );
		    printProbsThreshold( invert2( currentprobs ), threshold );
		    
			conceptClasses cc = getConceptualClasses( invert2( currentprobs ), threshold );  
			//conceptAttributes ca = {<"Wagon","seats">};
			//associations ass = {<"Wagon","Train">};
			conceptAttributes ca = {};
			associations ass = getAssociations( cc, getFactsTypesUsed( allPRJFacts ) );
			relations rl = {};
			
			writePlantUML( cc, ca, ass, rl, filepath + "uml.txt");

		    //printUML( invert2( currentprobs ), threshold, filepath );
		    println("");
		}
	}
	
}

public void testFCAJHotDraw() {
	//list[loc] files = [];
			str prjname = "JHotDraw6";
			str filepath = "C:/chk/fca/RichRailTests/" + prjname + "_new";
			loc prj = |project://dummy|;
			prj.uri = "project://" + prjname;
			println( prj);
			Resource allPRJFacts = extractProject(prj);

			//// classfields
			//currentprobs = calcProbSplitClassField( allPRJFacts, filepath ); 
		 //   println( "probs from classFields: ");
		 //   printProbs( invert( currentprobs ) );

		    // called Types
			newprobs = calcProbClassCalledTypes( allPRJFacts, filepath );
		    println( "probs from called Types: ");
		    printProbs( invert( newprobs ) );
		    //currentprobs = mergeProbs( currentprobs, newprobs ) ;

		    // called Types splitted
			newprobs = calcProbClassCalledTypesSplit( allPRJFacts, filepath );
		    println( "probs from called split Types: ");
		    printProbs( invert( newprobs ) );
		    //currentprobs = mergeProbs( currentprobs, newprobs ) ;

		    // called Methods
//		    newprobs = calcProbClassMethod( allPRJFacts, filepath );
//		    println( "probs from called Methods: ");
//		    printProbs( invert( newprobs ) );
//		    currentprobs = mergeProbs( currentprobs, newprobs ) ;
//// test
//		    //newprobs = getClassMethodBottomUpClassProbs( lattice );
//		    newprobs = calcProbClassMethodBottomUp( allPRJFacts, filepath );
//		    println( "probs from called bottom up Methods: ");
//		    printProbs( invert( newprobs ) );
//		    currentprobs = mergeProbs( currentprobs, newprobs ) ;
//		    
//		    // compute the overall domain probs
//		    println( "final probs: " );
//		    printProbsThreshold( invert( currentprobs ), 1.01 );
//		    printUML( invert( currentprobs ), 1.01, filepath );
//		    println("");
}

public void printProbs( map[real, set[str]] probs ) {
	list[real] v = toList( domain( probs ) );
	v = reverse( sort( v ) );
	for( r <- v ) {
		print( toString( r ) + ": " );
		println( probs[r] );
	}
}

private void printProbsThreshold( map[real, set[str]] probs, real threshold ) {
	list[real] v = toList( domain( probs ) );
	v = reverse( sort( v ) );
	for( r <- v ) {
		if( r >= threshold ) { 
			print( toString( r ) + ": " );
			println( probs[r] );
		}
	}
}

//private void printUML( map[real, set[str]] probs, real threshold, str filepath ) {
//	list[real] v = toList( domain( probs ) );
//	conceptClasses cc = {};
//	v = reverse( sort( v ) );
//	for( r <- v ) {
//		if( r >= threshold ) { 
//			cc += probs[r];
//		}
//	}
//	conceptAttributes ca = {<"Wagon","seats">};
//	associations ass = {<"Wagon","Train">};
//	relations rl = {};
//	
//	writePlantUML2( cc, ca, ass, rl, filepath + "uml.txt");
//}
//
//private void printUML2( conceptClasses cc, conceptAttributes ca, associations ass, relations rl, str filepath ) {
//	list[real] v = toList( domain( probs ) );
//	set[str] cc = {};
//	v = reverse( sort( v ) );
//	for( r <- v ) {
//		if( r >= threshold ) { 
//			cc += probs[r];
//		}
//	}
//	writePlantUML( cc, filepath + "uml.txt");
//}

public void testHier() {
    lattice = getLatticeFromCXT( "C:/chk/fca/methods/RichRail5methods.cxt" );
	l = computeConceptLevelsAsc( lattice );
	for(lp <- l) {
		println( lp );
		println( l[lp] );
	}
}

public  map[str,real] mergeProbs( map[str,real] firstprobs, map[str,real] secondprobs ) {
    //map[str,real] firstprobs = ("a":0.5, "b":0.5, "c":0.5);
    //map[str,real] secondprobs = ("a":0.3, "b":0.3, "d":0.7);    
    finalprobs = firstprobs;
    for ( sp <- secondprobs ) {
    	if( finalprobs[ sp ]? ) {
    		finalprobs[ sp ] += secondprobs[ sp ];
    	} 
    	else {
    		finalprobs += (sp: secondprobs[ sp ]);
    	}
    }
    return finalprobs;
}

private map[str,real] removeTechnicalConcepts( map[str,real] currProbs ) {
	// this is a first implementation, to be improved
	map[str,real] result = ();
	techConcepts = {"log","logger","command","gui","exception","write","writer",
					"read","reader","test","display","factory", "context", "expression"};
	for( cp <- currProbs ) {
		partSet = { toLowerCase(stemWord( part )) | part <- splitIdentifiers( cp ) };
		if ( size( partSet & techConcepts ) == 0 ) {
			result += (cp:currProbs[cp]);
		}
	}
	return result;
} 

public void fcatestrender() {
 Figure figure = getFigure(|file://C:/chk/fca/fcatest4.cxt|, "fdp");
 render(use(figure, [height(600), width(600)]));
}


public void testDecomposition() {
	loc f = |file://C:/chk/fca/RichRail5calledtypes.cxt|;
   	property_table vb = readCxt(f);
   	lattice = createLattice( vb );
	println( getPossibleDomainObjects( lattice ));
}

public void testDomainSubconceptFinder() {
	list[loc] files = [];
	for ( i <- [1..8] ) {
		if ( i != 7 ) {
			loc l = |file://t|;
			l.uri = "file://C:/chk/fca/RichRail" + toString( i ) + "calledtypes.cxt";
			files += l;
		}
	}
	for ( f <- files ) {
	   	property_table vb = readCxt(f);
	   	lattice = createLattice( vb );
	   	println( f );
		println( getPossibleDomainObjects( lattice ));
	}
}

public void testClassMethods() {
	list[loc] files = [];
	for ( i <- [1..8] ) {
		if ( i != 7 ) {
			str prjname = "RichRail" + toString( i );
			str filepath = "C:/chk/fca/methods/" + prjname;
			loc prj = |project://dummy|;
			prj.uri = "project://" + prjname;
			println( prj);
			Resource allPRJFacts = extractProject(prj);
			println( "facts extracted" ); 
			// classfields
		    rel2cxt( getFactsClassMethodsSplitNames( allPRJFacts ), filepath + "methods.cxt" );
		    println( "class-method context created" );
		    lattice = getLatticeFromCXT( filepath + "methods.cxt" );
		    firstprobs = getClassMethodProbs( lattice );
		    println( firstprobs );
		}
	}
}

public void testVars() {
	//println( getFactsPackageCalls( allFacts ) );
	println( allFacts@declaredTopTypes );
	//println( allFacts@types );
}

public void executeLDA( str filepath ) {
	map[str,value] options = ();
	options += ("est":true);
	options += ("inf":false);
	options += ("dfile": "ldatest2.txt");
	options += ("dir":"C:/CHK/LDA");
	options += ("K": 6);
	options += ("alpha":50.0/5.0);
	options += ("beta": 0.1);
	options += ("twords": 6);
	options += ("niters":5000);
	options += ("modelName": "controller");
	startLDA( options );
}