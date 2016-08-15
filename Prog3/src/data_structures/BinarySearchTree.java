package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class BinarySearchTree<K, V> implements DictionaryADT{
	
	private int currentSize;
	private Node<K,V> head;
	private int sequenceNumber;
	

	@Override
	public boolean contains(Object key) {
		return checkKey(head, (K) key) != null;
	}

	@Override
	public boolean add(Object key, Object value) {
		Node<K,V> obj = new Node(key, value);
		Node<K,V> previous = null;
		Node<K,V> current = head;
		while(current != null){
			int dif = ((Comparable<K>) key).compareTo(current.key);
			previous = current;
			if(dif <= 0)
				current = current.left;
			else
				current = current.right;
		}
		currentSize++;
		sequenceNumber++;
		if(head == null){
			head = obj;
			return true;
		}
		if(current == null){
			int dif = ((Comparable<K>) key).compareTo(previous.key);
			if(dif <= 0)
				previous.left = obj;
			else
				previous.right = obj;
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Object key) {
		Node<K,V> previous = null;
		boolean wentLeft = false;
		Node<K,V> curr = head;
		while(curr != null){
			int val = ((Comparable<K>)key).compareTo(curr.key);
			if(val == 0)
				break;
			previous = curr;
			if(val < 0){
				curr = curr.left;
				wentLeft = true;
			}
			else{
				curr = curr.right;
				wentLeft = false;
			}
		}
		if(curr == null)
			return false;
		currentSize--;
		sequenceNumber++;
		int children = 0;
		if(curr.left != null) children++;
		if(curr.right != null) children++;
		if(children == 0){
			if(previous == null){
				head = null;
				return true;
			}
			if(wentLeft)
				previous.left = null;
			else
				previous.right = null;
			return true;
		}else if(children == 1){
			if(previous == null){
				if(curr.left!=null)
					head = curr.left;
				else
					head = curr.right;
				return true;
			}
			if(curr.left != null){
				if(wentLeft)
					previous.left = curr.left;
				else
					previous.right = curr.left;
			}else{
				if(wentLeft)
					previous.left = curr.right;
				else
					previous.right = curr.right;
			}
			return true;
		}else{
			Node<K, V> child = curr.right;
			Node<K, V> parent = null;
			while(child.left!= null){
				parent = child;
				child = child.left;
			}
			if(previous == null){
				parent.right = child.right;
				child.right = head.right;
				child.left = head.left;
				head = child;
				return true;
			}
			if(wentLeft)
				previous.left = child;
			else
				previous.right = child;
			if(parent != null)
				parent.left = child.right;
			child.right = curr.right;
			child.left = curr.left;
			return true;
		}
		
	}

	@Override
	public Object getValue(Object key) {
		Node<K,V> node = checkKey(head, (K) key);
		if(node != null)
			return node.value;
		return null;
	}

	@Override
	public Object getKey(Object value) {
		Node<K,V> node = checkValue(head, (V) value);
		if(node != null)
			return node.key;
		return null;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public void clear() {
		head = null;
		currentSize = 0;
		
	}

	@Override
	public Iterator keys() {
		return new IteratorHelper(){
			@Override
			public Object next(){
				if(modNumber != sequenceNumber) throw new ConcurrentModificationException();
				return storage[index++].key;
			}
		};
	}
	
	@Override
	public Iterator values() {
		return new IteratorHelper(){
			@Override
			public Object next(){
				if(modNumber != sequenceNumber) throw new ConcurrentModificationException();
				return storage[index++].value;
			}
		};
	}
	
	private Node<K,V> checkKey(Node<K,V> node, K key){
		if(node == null) return null;
		int val = ((Comparable<K>)key).compareTo(node.key);
		if(val == 0)
			return node;
		else if(val < 0)
			return checkKey(node.left, key);
		else return checkKey(node.right, key);
	}
	
	private Node<K,V> checkValue(Node<K,V> node, V value){
		if(node == null) return null;
		
		if(((Comparable<V>)value).compareTo(node.value)==0)
			return node;
		
		Node<K,V> left = checkValue(node.left, value);
		if(left != null) return left;
		
		Node<K,V> right = checkValue(node.right, value);
		if(right != null) return right;
		
		return null;
	}
	
	class IteratorHelper implements Iterator{

		protected int modNumber;
		protected int index;
		protected Node<K,V>[] storage;
		
		public IteratorHelper(){
			storage = new Node[currentSize];
			modNumber = sequenceNumber;
			getNodes(head, storage);
			index = 0;
		}
		
		private void getNodes(Node<K,V> node, Node<K,V>[] nodes){
			if(node.left != null)
				getNodes(node.left, nodes);
			nodes[index++] = node;
			if(node.right != null)
				getNodes(node.right, nodes);
		}
		
		@Override
		public boolean hasNext() {
			if(modNumber != sequenceNumber) throw new ConcurrentModificationException();
			return index != currentSize;
		}

		@Override
		public Object next() {
			if(modNumber != sequenceNumber) throw new ConcurrentModificationException();
			return storage[index++];
		}
		
	}
	
	class Node<K, V>{
		public K key;
		public V value;
		
		public Node<K,V> left;
		public Node<K,V> right;
		
		public Node(K k, V v){
			this.key = k;
			this.value = v;
		}
	}

}
