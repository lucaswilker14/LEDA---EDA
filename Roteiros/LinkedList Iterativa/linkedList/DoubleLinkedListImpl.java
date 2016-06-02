package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {
	
	DoubleLinkedListNode<T> head;
	DoubleLinkedListNode<T> last;
	
	public DoubleLinkedListImpl(){
		head = new DoubleLinkedListNode<T>();
		last = new DoubleLinkedListNode<T>();
	}
	
	
	@Override
	public void insertFirst(T element) {
		DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<T>(element, head, new DoubleLinkedListNode<T>());
		newHead.next = head;
		newHead.previous = new DoubleLinkedListNode<T>();
		head.previous = newHead;
		if(head.isNIL()){
			last = newHead;
		}
		head = newHead;
	}
	
	

	@Override
	public void removeFirst() {
		if(head.isNIL() == false){
			head = (DoubleLinkedListNode<T>) head.next;
			if(head.isNIL()){
				last = head;
			}
			head.previous = new DoubleLinkedListNode<T>();
		}
	}

	@Override
	public void removeLast() {
		if(last.isNIL() == false){
			last = last.previous;
			if(last.isNIL()){
				head = last;
			}
			last.next = new DoubleLinkedListNode<T>();
		}
	}
}

