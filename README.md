# RDF_enhancer
basically this is a project created to help you change/add vocabularies to your RDF base by converting existing relationships/classes to those in your new ontology, it will also help you deduce new triplets (using the new vocabularies) from those already existing with the previous ontologies.
for example suppose you have in an ontology the relationships: "hasParent" and "hasGender" used in an RDF base, and you wanted to add the relationships "hasBrother" and "hasSister" to your ontology and use it in your RDF base, this project will help you to enhance your RDF base by deducing the new relationships from the existing ones like:
< Person1 > :hasParent < Parent1 >
< Person2 > :hasParent < Parent1 >
< Person1 > :hasGender "male"
< Person2 > :hasGender "female"

then we can add the relationships:

< Person1 > :hasSister < Person2 >
< Person2 > :hasBrother < Person1 >

