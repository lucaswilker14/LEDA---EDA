package adt.heap;

public interface MaxHeap<T> {

	public void buildHeap(T[] array);
		
	public boolean isEmpty();	

	public void insert(T element);
	
	public T extractMaximum();

	public T maximum();

	public T[] heapsort(T[] array);
	
	public T[] toArray();
}
