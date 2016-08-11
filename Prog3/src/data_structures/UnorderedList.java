package data_structures;

import java.util.Iterator;

public class UnorderedList<T>{
	private Node<T> head;
	private Node<T> tail;
	private int size;
	
	public UnorderedList(){
		head = null;
		tail = null;
		size = 0;
	}
	
	public void add(T element){
		Node<T> newNode = new Node(element);
		if(head == null){
			head = tail = newNode;
			size++;
			return;
		}
		tail.next = newNode;
		tail = newNode;
		size++;
	}
	
	public boolean contains(T value){
		Node<T> current = head;
		while(current != null && ((Comparable<T>)current).compareTo(value) != 0)
			current = current.next;
		if(current == null) return false;
		return true;
	}
	
	public void clear(){
		head = tail = null;
	}
	
	public T peak(){
		if(head==null) return null;
		T obj = head.data;
		head = head.next;
		size--;
		return obj;
	}
	
	public T remove(T obj){
		Node<T> prev = null;
		Node<T> curr = head;
		
		while(curr != null && ((Comparable<T>)curr.data).compareTo(obj) != 0){
			prev = curr;
			curr = curr.next;
		}
		if(curr == null){
			return null;
		}
		else if(prev==null){
			if(head == null)
				return null;
			head = head.next;
			size--;
			return curr.data;
		}
		else{
			prev.next = curr.next;
			if(curr == tail)
				tail = prev;
			size--;
			return curr.data;
		}
		
	}
	
	public T find(T obj){
		Node<T> curr = head;
		while(curr != null && ((Comparable<T>)curr.data).compareTo(obj) != 0)
			curr = curr.next;
		if(curr == null) return null;
		return curr.data;
	}
	
	public int getSize(){
		return size;
	}
	
	public Iterator iterator(){
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator{

		private Node<T> node = head;
		
		@Override
		public boolean hasNext() {
			return !(node == null);
		}

		@Override
		public Object next() {
			Node<T> prev = node;
			node = node.next;
			return prev.data;
		}
		
	}
	
	class Node<E>{
		public E data;
		public Node next;
		
		public Node(E data){
			this.data = data;
		}
	}

}
