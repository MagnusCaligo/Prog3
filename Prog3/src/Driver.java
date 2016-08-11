import java.io.File;
import java.util.Arrays;

import data_structures.HashTable;

public class Driver {

	public Driver(){
		HashTable<String, String> table = new HashTable<String, String>(30000000);
		
		LatinDictionary dic = new LatinDictionary();
		dic.loadDictionary("Latin.txt");
		System.out.println(dic.getDefinition("incolumis"));
		
		String[] strings = dic.getRange("amare", "incolumis");
		System.out.println(Arrays.toString(strings));

	}
	
	
	public static void main(String args[]){
		new Driver();
	}
	
}
