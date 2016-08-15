package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class HashTable<K, V> implements DictionaryADT<K, V>{
	
	private int maxSize;
	private int currentSize;
	private int sequenceNumber;
	private UnorderedList<Wrapper>[] storage;
	
	public HashTable(int max){
		this.maxSize = max;
		currentSize = 0;
		storage = new UnorderedList[max];
	}

	@Override
	public boolean contains(K key) {
		int location = (key.hashCode()&0x7FFFFFFF)%maxSize;
		if(location >= maxSize || location < 0 || storage[location] == null) return false;
		if(storage[location].getSize() > 0) return true;
		return false;
	}

	@Override
	public boolean add(K key, V value) {
		int location = (key.hashCode()&0x7FFFFFFF)%maxSize;
		if(location >= maxSize || location < 0) return false;
		if(storage[location] == null)
			storage[location] = new UnorderedList<Wrapper>();
		//if(storage[location].getSize() > 1) return false;
		storage[location].add(new Wrapper(key, value));
		currentSize++;
		sequenceNumber++;
		return true;
	}

	@Override
	public boolean delete(K key) {
		int location = (key.hashCode()&0x7FFFFFFF)%maxSize;
		if(location >= maxSize || location < 0 || storage[location] == null) return false;
		if(storage[location].getSize() > 0){
			storage[location].remove(new Wrapper(key, null));
			currentSize--;
			sequenceNumber++;
			return true;
		}
		return false;
	}

	@Override
	public V getValue(K key) {
		int location = (key.hashCode()&0x7FFFFFFF)%maxSize;
		if(location >= maxSize || location < 0 || storage[location] == null) return null;
		Wrapper wrap =  storage[location].find(new Wrapper(key, null));
		if(wrap == null) return null;
		return (V) wrap.value;
	}

	@Override
	public K getKey(V value) {
		for(int i = 0; i < maxSize; i++){
			if(storage[i] == null || storage[i].getSize() == 0) continue;
			Iterator itter = storage[i].iterator();
			while(itter.hasNext()){
				Wrapper wrap = (Wrapper) itter.next();
				if(wrap.value == value){
					return (K) wrap.key;
				}
			}
		}
		return null;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isFull() {
		return currentSize == maxSize;
	}

	@Override
	public boolean isEmpty() {
		return (currentSize == 0);
	}

	@Override
	public void clear() {
		for(int i = 0; i < maxSize; i++){
			if(storage[i] != null)
				storage[i].clear();
		}
		currentSize = 0;
		
	}

	@Override
	public Iterator<K> keys() {
		return new KeysIterator<K>();
	}

	@Override
	public Iterator<V> values() {
		return new ValuesIterator<V>();
	}
	
	public String toString(){
		String string = "";
		Iterator keyIt = keys();
		while(keyIt.hasNext()){
			K key = (K) keyIt.next();
			string += key.toString() + " : " + this.getValue(key) + "\n";
		}
		return string;
	}
	
	class KeysIterator<K> implements Iterator{

		private int index;
		private K[] keyStorage;
		private int modNumber = sequenceNumber;
		
		public KeysIterator(){
			keyStorage = (K[]) new Object[currentSize]; 
			index = 0;
			Wrapper<K,V> wrap;
			
			for(int i = 0; i < maxSize; i++){
				if(storage[i] == null || storage[i].getSize() == 0) continue;
				Iterator itt = storage[i].iterator();
				while(itt.hasNext()){
					wrap = (Wrapper) itt.next();
					keyStorage[index++] = (K)(wrap.key);
				}
				
			}
			index = 0;
			
			int in, out, h=1;
			K temp;
			int size = keyStorage.length;
			while(h <=size/3)
				h = h*3+1;
			while(h > 0){
				for(out = h; out < size; out++){
					temp = keyStorage[out];
					in = out;
					while(in > h-1 && ((Comparable<K>)keyStorage[in-h]).compareTo(temp) >= 0){
						keyStorage[in] = keyStorage[in-h];
						in -=h;
					}
					keyStorage[in] = temp;
				}
				h = (h-1)/3;
			}
			
			
		}
		
		@Override
		public boolean hasNext() {
			if(modNumber != sequenceNumber) throw new ConcurrentModificationException();
			return index < keyStorage.length;
		}

		@Override
		public Object next() {
			if(modNumber != sequenceNumber) throw new ConcurrentModificationException();
			return keyStorage[index++];
		}
		
	}
	
	class ValuesIterator<V> implements Iterator{
		private int index;
		private V[] valueStorage;
		private int modNumber = sequenceNumber;
		private Wrapper<K,V> wrap;
		
		
		public ValuesIterator(){
			valueStorage = (V[]) new Object[currentSize]; 
			index = 0;
			
			for(int i = 0; i < maxSize; i++){
				if(storage[i] == null || storage[i].getSize() == 0) continue;
				Iterator itt = storage[i].iterator();
				while(itt.hasNext()){
					wrap = (Wrapper) itt.next();
					valueStorage[index++] = (V)(wrap.value);
				}
				
			}
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			if(modNumber != sequenceNumber) throw new ConcurrentModificationException();
			return index < valueStorage.length;
		}

		@Override
		public Object next() {
			if(modNumber != sequenceNumber) throw new ConcurrentModificationException();
			return valueStorage[index++];
		}
	}
	
	class Wrapper<K,V> implements Comparable<Wrapper>{
		public K key;
		public V value;
		
		public Wrapper(K k, V v){
			this.key = k;
			this.value = v;
		}

		@Override
		public int compareTo(Wrapper obj) {
			return ((Comparable<K>)key).compareTo((K) obj.key);
		}
	}

}
