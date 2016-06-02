package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return head.isNIL();
	}

	@Override
	public int size() {
		int size = 0;
		if (isEmpty()) {
			return size;
		} else {
			SingleLinkedListNode<T> aux = this.head;
			while (!aux.isNIL()) {
				size = size + 1;
				aux = aux.getNext();
			}
			return size;
		}
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> aux = this.head;
		while (!aux.isNIL() && !aux.getData().equals(element)) {
			aux = aux.getNext();
		}
		return aux.getData();
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element, new SingleLinkedListNode<T>());
		if (isEmpty()) {
			head = newNode;
		} else {
			SingleLinkedListNode<T> aux = this.head;
			while (!aux.getNext().isNIL()) {
				aux = aux.getNext();
			}
			aux.setNext(newNode);
		}
	}

	@Override
	public void remove(T element) {

		T elementProcurado = search(element);
		
		if (elementProcurado != null) {
			
			SingleLinkedListNode<T> previous = this.head;
			SingleLinkedListNode<T> nextAux = this.head.getNext();

			if (this.head.getData().equals(elementProcurado)) {
				this.head = nextAux;
			
			} else {
				while (!nextAux.isNIL() && !nextAux.getData().equals(elementProcurado)) {
					nextAux = nextAux.getNext();
					previous = previous.getNext();
				}
				
				previous.setNext(nextAux.getNext());
			}
		}
	}

	@Override
	public T[] toArray() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not implemented!");
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

	@Override
	public int procuraMaior() {
		T[] array = criaArray();
		
		int maior = (int) array[0];
		
		for (int i = 0; i < array.length; i++) {
			if ((int)array[i] > maior) {
				maior = (int) array[i];
			}
		}
		
		return maior;
	}
	

	@Override
	public int procuraMenor() {
		T[] array = criaArray();
		
		int menor = (int) array[0];
		
		for (int i = 0; i < array.length; i++) {
			if ((int)array[i] < menor) {
				menor = (int) array[i];
			}
		}
		
		return menor;
	}
	
	private T[] criaArray(){
		T[] array = (T[]) new Object[size()];
		
		SingleLinkedListNode<T> aux = this.head;
		
		for (int i = 0; i < array.length; i++) {
			array[i] = aux.getData();
			if (aux.getData() != null) {
				aux = aux.getNext();
			}
		}
		
		return array;
	}

	@Override
	public boolean ordenadaCrescente() {
		return ordenadaCrescente(head);
	}
	
	private boolean ordenadaCrescente(SingleLinkedListNode<T> node){
		boolean ordenada = false;
		SingleLinkedListNode<T> aux = node;
		SingleLinkedListNode<T> nextAux = aux.getNext();
		
		if (!(nextAux.isNIL()) && (int) aux.getData() < (int) nextAux.getData()) {
			ordenada = true;
			ordenadaCrescente(aux.getNext());
		}
		return ordenada;		
	}

	@Override
	public boolean ordenadaDescrescente() {
		return ordenadaDescrescente(head);
	}
	
	private boolean ordenadaDescrescente(SingleLinkedListNode<T> node){
		boolean ordenada = false;
		SingleLinkedListNode<T> aux = node;
		SingleLinkedListNode<T> nextAux = aux.getNext();
		
		if (!(nextAux.isNIL()) && (int) aux.getData() > (int) nextAux.getData()) {
			ordenada = true;
			ordenadaCrescente(aux.getNext());
		}
		return ordenada;		
	}
}
