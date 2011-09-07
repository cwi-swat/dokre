/*
	This module contains specific for the analysis for domain knowledge recovery
	contributor: Christian Köppe - c.koppe@cwi.nl
*/

module LatticeAnalyzer

import experiments::Concept::Types;
import util::Resources;
import Relation;
import Integer;
import Set;
import List;
import String;
import Map;
import Real;
import IO;
import Benchmark;

import FCA;
import AnalysisUtils;
import FactExtraction;
bool inDebug = false;

public map[str,real] calcProbClassCalledMethods( Resource allFacts, str filepath ) {
    rel2cxt( getFactsCalledMethods( allFacts ), filepath + "calledMethods.cxt" );
    lattice = getLatticeFromCXT( filepath + "calledMethods.cxt" );

	rankedConcepts = invert( computeConceptsDownrank( lattice ) );
	levelCount = size( rankedConcepts ) - 1;
	
    return getProbs( rankedConcepts, 2, levelCount, 0.5, getFeature );
}

public map[str,real] calcProbSplitClassField( Resource allFacts, str filepath ) {
    rel2cxt( getFactsStemmedSplitClassFieldsWithInheritance( allFacts ), filepath + "stemmed.cxt" );
    lattice = getLatticeFromCXT( filepath + "stemmed.cxt" );
    newlattice =  trimMOCinLattice( lattice );
println( newlattice );
    lattice2CXT( newlattice, filepath + "stemmedandtrimmed.cxt" );
    
    return getClassFieldProbs( newlattice );
}

public map[str,real] calcProbClassCalledTypes( Resource allFacts, str filepath ) {
    rel2cxt( getFactsTypesUsed( allFacts ), filepath + "calledTypes.cxt" );
    lattice = getLatticeFromCXT( filepath + "calledTypes.cxt" );
    return getCalledTypeProbs( lattice );
}

public map[str,real] calcProbClassCalledTypesSplit( Resource allFacts, str filepath ) {
    rel2cxt( getFactsTypesUsedSplit( allFacts ), filepath + "calledTypesSplit.cxt" );
    lattice = getLatticeFromCXT( filepath + "calledTypesSplit.cxt" );

    newlattice =  trimMACinLattice( lattice );
    lattice2CXT( newlattice, filepath + "calledTypesSplittrimmed.cxt" );
    
    return getCalledTypeSplitProbs( newlattice );
}

public map[str,real] calcProbClassMethod( Resource allFacts, str filepath ) {
    rel2cxt( getFactsClassMethodsSplitNames( allFacts ), filepath + "methods.cxt" );
    lattice = getLatticeFromCXT( filepath + "methods.cxt" );
    
	rankedConcepts = invert( computeConceptsDownrank( lattice ) );
	levelCount = size( rankedConcepts ) - 1;
	
    return getProbs( rankedConcepts, 2, levelCount, 0.5, getFeature );
}

public map[str,real] calcProbClassMethodBottomUp( Resource allFacts, str filepath ) {
    //rel2cxt( getFactsClassMethodsSplitNames( allFacts ), filepath + "methodsBottomUp.cxt" );
    //lattice = getLatticeFromCXT( filepath + "methodsBottomUp.cxt" );
    lattice = getLatticeFromCXT( filepath + "methods.cxt" );
    newlattice =  trimMOCinLattice( lattice );
    lattice2CXT( newlattice, filepath + "methodsBottomUptrimmed.cxt" );

	step1 = computeConceptsDownrank( newlattice );
	step2 = reverseConceptRanks( step1 );
	rankedConcepts = invert( step2 );

	levelCount = size( rankedConcepts ) - 1;
	
    return getProbs( rankedConcepts, levelCount - 2, levelCount, 0.5, getItem );
}

public map[str,real] calcProbPackageCalls( Resource allFacts, str filepath ) {
    rel2cxt( getFactsPackageCalls( allFacts ), filepath + "PackageCalls.cxt" );
    lattice = getLatticeFromCXT( filepath + "PackageCalls.cxt" );

	probsPerPackage = getCalledPackageProbs( lattice );

	classPackages = getFactsClassesPerPackage( allFacts );
    map[str,real] result = ();
    for( i <- classPackages o toRel( probsPerPackage ) ) {
		if (i[0]? && i[1]?)
			result += (i[0]:i[1]);	    
    }

    return result;
}

public map[str,real] getProbs( map[int, set[concept_t]] rankedConcepts, 
									 int startLevel, 
									 int eindLevel, 
									 real highestProb, 
									 set[str](concept_t) getConceptPartFunc )
{
	map[str,real] probs = ();
	
	int levelCount = size( rankedConcepts ) - 1;
	real probSteps = highestProb / toReal( levelCount - 1);
	for( i <- [startLevel .. eindLevel] ) {
		for( concept_t c <- rankedConcepts[i]) {
			real prob = round(probSteps * toReal( i )*100.0) / 100.0;
			levelProbs = { (attr: prob) | attr <- getConceptPartFunc( c ) };
			for(p <- levelProbs) {
				probs += p;
			}
		}
	}	
	
	return probs;
}

// extract the object-sets from the concepts with more than one object 
// directly connected to the concept
private set[ set[str] ] getMultiObjectConcepts( lattice_t lattice ) {

	set[ set[str] ] moc = {}; 
	for( con <- range( lattice ) ) {
		// we need to subtract the extents from the directly underlying concepts
		set[str] undercon = { ucon.item | <concept_t ucon,concept_t concept> <- (lattice), concept == con};
		//set[str] mocCandidate = con[0] - undercon; 
		//if ( size( mocCandidate ) > 1 )
		//	moc += {{mocCandidate}};
		if (size( con[0] )+1 > size( undercon ) ) {
			moc += {{con[0] - undercon}};
		} 
   	}
   	
   	//for( mocEl <- moc )
   	//	println( mocEl );
   	return moc;
}

// extract the attribute-sets from the concepts with more than one attribute 
// directly connected to the concept
private set[ set[str] ] getMultiAttributeConcepts( lattice_t lattice ) {

	set[ set[str] ] mac = {}; 
	for( con <- domain( lattice ) ) {
		// we need to subtract the intents from the directly upperlying concepts
		set[str] uppercon = { ucon.feature | <concept_t concept,concept_t ucon> <- (lattice), concept == con};
		if ( size( con[1] - uppercon ) > 1 )
			mac += {{con[1] - uppercon}};
   	}
   	
   	return mac;
}

private set[str] trimMOC( set[str] moc ) {
//if( inDebug ) println("start trimMOC");
	map[str,set[str]] supersets = ();
	for (o <- moc ) {
		supersets += (o: getIdentifierSuperset( o )); 
	}
	trimmedMOC = moc;
	for (o <- moc) {
		for (to <- trimmedMOC) {
			if ( o in supersets[to] && o != to ) 
			//if ( o in getIdentifierSuperset( to ) && o != to ) 
				trimmedMOC -= o; 
		}
	}
//if( inDebug ) println("end trimMOC");
	return trimmedMOC;
}

// look in the MOCs of a lattice for objectnames which are part of other objectnames and remove these
// objects from the lattice
private lattice_t trimMOCinLattice( lattice_t lattice ) {
	set[set[str]] moclist = getMultiObjectConcepts( lattice );
	
// trim the MOCs
	map[set[str],set[str]] trimmedMOCs = ();
	for ( moc <- moclist ) {
		tm = trimMOC(moc);
		if (tm != moc)
			trimmedMOCs += ( moc: tm );
		//trimmedMOCs += ( moc: trimMOC(moc) );
	}	

	lattice_t trimmedLattice = {};
	// loop through all concepts
	for ( conrel <- lattice ) {
		// the 2 concepts in the relation
		c1 = conrel[0];
		c2 = conrel[1];
	//		loop through all MOCs  a subset of the concept extent
		for ( moc <- trimmedMOCs ) {
	//			if MOC is a subset of the concept extent: remove MOC and add trimmedMOC
			if ( moc <= c1[0] ) {
				c1[0] -= moc;
				//c1[0] += trimMOC( moc );
				c1[0] += trimmedMOCs[ moc ];
			}
			if ( moc <= c2[0] ) {
				c2[0] -= moc;
				//c2[0] += trimMOC( moc );
				c2[0] += trimmedMOCs[ moc ];
			}
		}
		trimmedLattice += <c1,c2>;
	} 
	return trimmedLattice;
}

private lattice_t trimMACinLattice( lattice_t lattice ) {
	set[set[str]] maclist = getMultiAttributeConcepts( lattice );
	
// trim the MACs
	map[set[str],set[str]] trimmedMACs = ();
	for ( mac <- maclist ) {
		trimmedMACs += ( mac: trimMOC(mac) );
	}	

	lattice_t trimmedLattice = {};
	// loop through all concepts
	for ( conrel <- lattice ) {
		// the 2 concepts in the relation
		c1 = conrel[0];
		c2 = conrel[1];
	//		loop through all MACs  a subset of the concept intent
		for ( mac <- maclist ) {
	//			if MAC is a subset of the concept intent: remove MAC and add trimmedMAC
			if ( mac <= c1[1] ) {
				c1[1] -= mac;
				//c1[1] += trimMOC( mac );
				c1[1] += trimmedMACs[ mac ];
			}
			if ( mac <= c2[1] ) {
				c2[1] -= mac;
				//c2[1] += trimMOC( mac );
				c2[1] += trimmedMACs[ mac ];
			}
		}
		trimmedLattice += <c1,c2>;
	} 
	return trimmedLattice;
}

private set[str] getPDOfromCalledTypes( lattice_t lattice ) {
	set[str] possibleDomainObjects = {};
	subTopconcepts = getSubconceptsFromLatticeTop( lattice );
	combinations = {<c1,c2> | <c1,c2> <- subTopconcepts * subTopconcepts, c1 != c2 };
	for( <c1,c2> <- combinations ) {
		if ( (c1[1] < c2[0]) && !(c2[1] < c1[0]) ) {
			possibleDomainObjects += c2[1];
		}
	}
	
	return possibleDomainObjects;
}

private set[str] getPDOfromClassFields( lattice_t lattice ) {
	set[str] possibleDomainObjects = {};
	superBottomconcepts = getSuperconceptsFromLatticeBottom( lattice );
	for(c <- superBottomconcepts) {
		possibleDomainObjects += c[0];
	}
	
	return possibleDomainObjects;
}

private map[str,real] getCalledTypeProbs( rel[concept_t, concept_t] lattice ) {
	map[str,real] probs = ();
	possDomObjects = getPDOfromCalledTypes( lattice );
	for( s <- possDomObjects ) {
		probs += (s: 0.6);
	}
	return probs;
}

private map[str,real] getCalledTypeSplitProbs( rel[concept_t, concept_t] lattice ) {
	map[str,real] probs = ();

	rankedLattice = invert( computeConceptsDownrank( lattice ) );
	//rankedLattice = invert( computeConceptRanksAsc( lattice ) );
	levelCount = size( rankedLattice ) - 1;
	
	// only take the upper half of the lattice
	int startLevel = levelCount / 2; 
	
    return getProbs( rankedLattice, startLevel, levelCount, 0.5, getFeature );

	//candidateConcepts = getSubconceptsFromLatticeTop( lattice ); 
	//for( concept_t c <- candidateConcepts ) {
	// //possDomObjects = 
	//	for( s <- c.feature ) {
	//		probs += (s: 0.4);
	//	}
	//}
	//return probs;
}

private map[str,real] getClassFieldProbs( lattice_t lattice ) {
	map[str,real] probs = ();
	
	//levelsSorted = invert( computeConceptRanksAsc( lattice ) );
	levelsSorted = invert( computeConceptsDownrank( lattice ) );
println( levelsSorted );
	int levelCount = size( levelsSorted ) - 1;
	real probSteps = 0.4 / toReal( levelCount - 1);
	for( i <- [levelCount-2 .. levelCount-1] ) {
		for( concept_t c <- levelsSorted[levelCount - i]) {
			real prob = round(probSteps * toReal( i )*100.0) / 100.0;
			levelProbs = { (attr: prob) | attr <- c.item };
			for(p <- levelProbs) {
				probs += p;
			}
		}
	}	
	
	return probs;
}

private map[str,real] getCalledMethodProbs( lattice_t lattice ) {
	map[str,real] probs = ();
	
	//levelsSorted = invert( computeConceptRanksAsc( lattice ) );
	levelsSorted = invert( computeConceptsDownrank( lattice ) );
	int levelCount = size( levelsSorted ) - 1;
	real probSteps = 0.4 / toReal( levelCount - 1);
	for( i <- [levelCount-2 .. levelCount-1] ) {
		for( concept_t c <- levelsSorted[levelCount - i]) {
			real prob = round(probSteps * toReal( i )*100.0) / 100.0;
			levelProbs = { (attr: prob) | attr <- c.item };
			for(p <- levelProbs) {
				probs += p;
			}
		}
	}	
	
	return probs;
}

private map[str,real] getCalledPackageProbs( rel[concept_t, concept_t] lattice ) {
	map[str,real] probs = ();

	set[str] possibleDomainPackages = {};
	subTopconcepts = getSubconceptsFromLatticeTop( lattice );

	for( concept_t s <- subTopconcepts ) {
		possibleDomainPackages += s.feature;
	}
	possibleDomainPackages -= "XXX";

	combinations = {<c1,c2> | <c1,c2> <- subTopconcepts * subTopconcepts, c1 != c2 };
	for( <c1,c2> <- combinations ) {
		if ( (c1[1] < c2[0]) && !(c2[1] < c1[0]) ) {
			possibleDomainPackages -= c1[1];
		}
	}
	
	for( s <- possibleDomainPackages ) {
		probs += (s: 0.4);
	}
	return probs;
}

