package adt.stack;

import adt.linkedList.DoubleLinkedList;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> list;
	protected int size;
	
	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
	}
	
	@Override
	public void push(T element) throws StackOverflowException {
		if(isFull()){
			throw new StackOverflowException();
		}else{
			list.insert(element);
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if(isEmpty()){
			throw new StackUnderflowException();
		}else{
			T top = top();
			list.removeLast();
			
			return top;
		}
	}

	@Override
	public T top() {
		if (isEmpty()){
			return null;
		}
		return list.toArray()[list.size() - 1];
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return list.size() == size;
	}
}
