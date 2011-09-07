module FCA

import experiments::Concept::GetFigure;
import experiments::Concept::Fca;
import experiments::Concept::Types;
import IO;
import Relation;
import Integer;
import Set;
import List;
import String;
import Map;
import Real;

import AnalysisUtils;
import FactExtraction;

alias lattice_t = rel[concept_t, concept_t];

public void lattice2CXT( lattice_t lattice, str filepath ) {
	// first transform lattice to rel[str,str]
	conceptList = domain( lattice ) + range( lattice );
	// now add all concepts to rel[str,str] list
	rel[str,str] rellist = {};
	for ( con <- conceptList ) {
		rellist += con[0] * con[1];
	}
	// then call rel2cxt
	rel2cxt( rellist, filepath );
}

// print the content of a lattice
public void printLatticeAsText( lattice_t lattice ) {
   	for( con <- domain( lattice ) + range( lattice ) )
   		println( con );
}

public rel[concept_t, concept_t] getLatticeFromCXT( str filepath ) {
	loc f = |file://t|;
	f.uri = "file://" + filepath;
   	property_table pt = readCxt(f);
   	return createLattice( pt );
}

   
public set[concept_t] getSubconceptsFromLatticeTop( lattice_t lattice ) {
	return getSubconceptsFromConcepts( lattice, { getLatticeTop( lattice ) } );
}

public set[concept_t] getSuperconceptsFromLatticeBottom( lattice_t lattice ) {
	return getSuperconceptsFromConcepts( lattice, { getLatticeBottom( lattice ) } );
}

public set[concept_t] getSuperconceptsFromConcepts( lattice_t lattice, set[concept_t] conlist ) {
	return { intent | <concept_t extent, concept_t intent> <- lattice, extent in conlist };
} 

public set[concept_t] getSubconceptsFromConcepts( lattice_t lattice, set[concept_t] conlist ) {
	return { extent | <concept_t extent, concept_t intent> <- lattice, intent in conlist };
} 

// this method horizontally decomposes a lattice into sublattices and returns a set of lattices  
public set[ lattice_t ] decomposeLattice( lattice_t lattice ) {
	set[ lattice_t ] sublattices = {};
	
	// first we determine all concepts directly located beneath the top of the lattice
	set[concept_t] subConcepts = getSubconceptsFromLatticeTop( lattice );

	// then we determine the groups of object which are part of a sublattice
	set[ set[str] ] subLatticeExtents = {};
	
	for ( concept_t subConcept <- subConcepts ) {
		bool addedToSublattice = false;
		set[str] newSLE = {}; 
		
		for ( sle <- subLatticeExtents ) {
			if ( size( subConcept.item & sle ) > 0 ) {
				subLatticeExtents = subLatticeExtents - {{sle}};
				newSLE = newSLE + sle + subConcept.item;
				addedToSublattice = true; 
			}
		}
		if ( addedToSublattice == false ) {
			subLatticeExtents = subLatticeExtents + {{ subConcept.item }}; 
		}
		else {
			subLatticeExtents = subLatticeExtents + {{newSLE}};
		}
	}
	
	// TODO: sort all concept relations from the original lattice according to the identified sublattices	

	return sublattices;
} 

// converts the relations between objects and attributes into a context file 
public loc rel2cxt( rel[str, str] dependencyRel, str filepath ) {
    list[str] d = ["B",""];
    
    list[str] extent = toList(domain(dependencyRel));
    list[str] intent = toList(range(dependencyRel)); 
	d += toString(size(extent));
	d += toString(size(intent));
	d += "";
	d += extent;
	d += intent;
	// create the matrix
	for (str e <- extent) {
		str matrixLine = "";
		set[str] attrib = dependencyRel[e];
		for (str i <- intent) {
			try {
				// check if intent also in relation
				index(attrib)[i];
				matrixLine += "X";
			} 
			catch: matrixLine += ".";
		}
		d += matrixLine;
	}
	
	loc cxtFile = |file://t|;
	cxtFile.uri = "file://" + filepath;
	// create a clean file
	writeFile(cxtFile, "");
	for (str e <- d) {
		appendToFile(cxtFile, e + "\n");
	}

	return cxtFile;
}

private concept_t getLatticeBottom( lattice_t lattice ) {
	return getOneFrom( { extent | <concept_t extent, concept_t intent> <- lattice, extent.item == {} } );
}

private concept_t getLatticeTop( lattice_t lattice ) {
	return getOneFrom( { intent | <concept_t extent, concept_t intent> <- lattice, intent.feature == {} } );
}


private map[concept_t, int] computeConceptRanksAsc( rel[concept_t, concept_t] lattice ) {
	map[concept_t, int] result = ();
	// get all concepts
	allConcepts = domain( lattice ) + range( lattice );
	
	// get bottom concept
	bottom = getLatticeBottom( lattice );
	//println( bottom );
	//println( allConcepts );
	allConcepts -= {bottom};
	lastLevelConcepts = { bottom };
	int iLevel = 0;
	result += (bottom: iLevel);
	// start with bottom
	while( !isEmpty( allConcepts ) ) {
		newLevelConcepts = getSuperconceptsFromConcepts( lattice, lastLevelConcepts );
		iLevel += 1;
		for( c <- newLevelConcepts ) {
			result += (c:iLevel);
		}
		allConcepts -= newLevelConcepts;
		lastLevelConcepts = newLevelConcepts;
	}
	// now make the top the highest level
	topConcept = getOneFrom( { intent | <concept_t extent, concept_t intent> <- lattice, intent.feature == {} } );
	result += (topConcept: iLevel+1);

	return result;
}

public map[concept_t, int] computeConceptsDownrank( rel[concept_t, concept_t] lattice ) {
	map[concept_t, int] result = ();
	// get all concepts
	allConcepts = domain( lattice ) + range( lattice );
	
	// get bottom concept
	bottom = getLatticeBottom( lattice );

	allConcepts -= {bottom};
	lastLevelConcepts = { bottom };
	int iLevel = 0;
	result += (bottom: iLevel);
	// start with bottom
	do {
		newLevelConcepts = getSuperconceptsFromConcepts( lattice, lastLevelConcepts );
		iLevel += 1;
		for( c <- newLevelConcepts ) {
			result += (c:iLevel);
		}
		allConcepts -= newLevelConcepts;
		lastLevelConcepts = newLevelConcepts;
	} while( size( lastLevelConcepts ) > 1 );

	// now make the top the highest level
	//topConcept = getOneFrom( { intent | <concept_t extent, concept_t intent> <- lattice, intent.feature == {} } );
	//result += (topConcept: iLevel+1);

	return result;
}

private map[concept_t, int] computeConceptRanksDesc( rel[concept_t, concept_t] lattice ) {
	map[concept_t, int] result = ();
	// get all concepts
	allConcepts = domain( lattice ) + range( lattice );
	
	// get top concept
	topConcept = getLatticeTop( lattice );

	allConcepts -= {topConcept};
	lastLevelConcepts = { topConcept };
	int iLevel = 0;
	result += (topConcept: iLevel);
	// start with bottom
	do {
		newLevelConcepts = getSubconceptsFromConcepts( lattice, lastLevelConcepts );
		iLevel += 1;
		for( c <- newLevelConcepts ) {
			result += (c:iLevel);
		}
		allConcepts -= newLevelConcepts;
		lastLevelConcepts = newLevelConcepts;
	} while( size( lastLevelConcepts ) > 1 );
	
	// now make the bottom the highest level
	//bottom = getOneFrom( { extent | <concept_t extent, concept_t intent> <- lattice, extent.item == {} } );
	//result += (bottom: iLevel+1);

	return result;
}

public map[concept_t, int] computeConceptsUprank( lattice_t lattice ) {
	map[concept_t, int] result = ();
	// get all concepts
	allConcepts = domain( lattice ) + range( lattice );
	
	// get top concept
	topConcept = getLatticeTop( lattice );

	allConcepts -= {topConcept};
	lastLevelConcepts = { topConcept };
	int iLevel = 0;
	result += (topConcept: iLevel);
	do {
		newLevelConcepts = getSubconceptsFromConcepts( lattice, lastLevelConcepts );
		iLevel += 1;
		for( c <- newLevelConcepts ) {
			result += (c:iLevel);
		}
		allConcepts -= newLevelConcepts;
		lastLevelConcepts = newLevelConcepts;
	} while( size( lastLevelConcepts ) > 1 );

	// now reverse the ranks as we started from the wrong side with counting
	result = reverseConceptRanks( result );

	return result;
}

public map[concept_t, int] reverseConceptRanks( map[concept_t, int] rankedConcepts ) {
	revRankedConcepts = rankedConcepts;
	
	int levelCount = size( invert( rankedConcepts ) ) - 1;
	for( rc <- rankedConcepts ) {
		revRankedConcepts[ rc ] = levelCount - rankedConcepts[ rc ];
	}
	return revRankedConcepts;
}

public map[concept_t, int] computeConceptLevelsRelative( rel[concept_t, concept_t] lattice ) {
	map[concept_t, int] result = ();
	return result;
}

public set[str] getFeature( concept_t con ) {
	return con.feature;
}

public set[str] getItem( concept_t con ) {
	return con.item;
}


