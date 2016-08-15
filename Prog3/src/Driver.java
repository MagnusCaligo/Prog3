import java.util.Arrays;
import java.util.Iterator;

import data_structures.HashTable;
import data_structures.RedBlackTree;

public class Driver {

	
	public Driver(){
		
		LatinDictionary dick = new LatinDictionary();
		dick.loadDictionary("Latin.txt");
		
		System.out.println(Arrays.toString(dick.getRange("amarare", "zzzzzzz")));
	}
	
	
	public static void main(String args[]){
		new Driver();
	}
	
	public void printTree(RedBlackTree tree){
		String[] arr = new String[tree.size()];
		int index = 0;
		Iterator it = tree.keys();
		while(it.hasNext())
			arr[index++] = (String) tree.getValue(it.next());
		
		System.out.println(Arrays.toString(arr));
	}
	
}
