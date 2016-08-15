package data_structures;

import java.util.*;


public class RedBlackTree<K,V> implements DictionaryADT<K,V> {
	private TreeMap<K,V> tree;
	
	public RedBlackTree(){
		tree = new TreeMap<K,V>();
	}


	public boolean contains(K key){
		return tree.containsKey(key);
	}

	public boolean add(K key, V value){
		if(tree.containsKey(key))
			return false;
		tree.put(key, value);
		return true;
	}

	public boolean delete(K key){
		return tree.remove(key) != null;
	}


	public V getValue(K key){
		return tree.get(key);
	}


	public K getKey(V value){
		Iterator<K> keyIter = keys();
		Iterator<V> valueIter = values();
		while(keyIter.hasNext()){
			K tempk = keyIter.next();
			V tempv = valueIter.next();
			if(((Comparable<V>)value).compareTo(tempv)==0)
				return tempk;
		}
		
		return null;
	}


	public int size(){
		return tree.size();
	}


	public boolean isFull(){
		return false;
	}


	public boolean isEmpty(){
		return tree.size() == 0;
	}	


	public void clear(){
		tree.clear();
	}


	public Iterator<K> keys(){
		return tree.keySet().iterator();
	} 

	public Iterator<V> values(){
		return (Iterator<V>) tree.values().iterator();
	}

}
