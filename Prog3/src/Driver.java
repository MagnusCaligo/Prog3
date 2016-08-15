import java.util.Iterator;

import data_structures.BinarySearchTree;
import data_structures.HashTable;

public class Driver {

	public Driver(){
		
		LatinDictionary dick = new LatinDictionary();
		dick.loadDictionary("Latin.txt");
		System.out.println(dick.getDefinition("amare"));

	}
	
	
	public static void main(String args[]){
		new Driver();
	}
	
}
