import java.util.Iterator;

import data_structures.BinarySearchTree;
import data_structures.DictionaryADT;
import data_structures.HashTable;
import data_structures.RedBlackTree;
import data_structures.UnorderedList;

public class LatinDictionary {
    private DictionaryADT<String,String> dictionary;

    // constructor takes no arguments.  Size depends on the datafile.
    // creates an instance of the DictionaryADT. Use your HashTable 
    // implementation in this class (though all three should work).
    // Methods that make modifications to the dictionary modify the
    // DictionaryADT object, not the datafile.
    public LatinDictionary() {
    	
    	dictionary = new HashTable<String, String>(10000);
    	dictionary = new BinarySearchTree<>();
    	dictionary = new RedBlackTree();
    	
        }

    // reads the key=value pairs from the datafile and builds a dictionary structure 
    public void loadDictionary(String fileName) {   
    	DictionaryEntry[] entry = DictionaryReader.getDictionaryArray(fileName);
    	for(DictionaryEntry ent: entry){
    		dictionary.add(ent.getKey(), ent.getValue());
    	}
    }

    // inserts a new Latin word and its definition
    public boolean insertWord(String word, String definition) {
    	return dictionary.add(word, definition);
    }

    // removes the key value pair that is identified by the key from the dictionary
    public boolean deleteWord(String word) {
    	return dictionary.delete(word);
    }

    // looks up the definition of the Latin word
    public String getDefinition(String word) {
    	return dictionary.getValue(word);
    }

    // returns true if the Latin word is already in the dictionary
    public boolean containsWord(String word) {
    	return dictionary.contains(word);
    }
    
    // returns all of the keys in the dictionary within the range start .. finish
    // inclusive, in sorted order. Neither value 'start' or 'finish' need be in the
    // dictionary.  Returns null if there are no keys in the range specified.    
    public String[] getRange(String start, String finish) {
    	
    	UnorderedList<String> list = new UnorderedList<String>();
    	Iterator<String> it = dictionary.keys();
    	while(it.hasNext()){
    		String string = it.next();
    		if(((Comparable<String>)string).compareTo(start) >=0 && ((Comparable<String>)string).compareTo(finish) <=0){
    			list.add(string);
    		}
    	}
    	
    	String[] array = new String[list.getSize()];
    	it = list.iterator();
    	int i = 0;
    	while(it.hasNext())
    		array[i++] = it.next();
    	
    	return array;
    	
    }
            
    // returns an Iterator of the latin words (the keys) in the dictionary,
    // in sorted order.
    public Iterator<String> words() {
    	return dictionary.keys();
    }

    // returns the definitions in the dictionary, in exactly the same order
    // as the words() Iterator
    public Iterator<String> definitions() {
    	return dictionary.values();
    }
}   