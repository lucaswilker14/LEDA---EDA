package adt.linkedList.ordered;

import java.util.Comparator;

import adt.linkedList.SingleLinkedListNode;

/**
 * Para testar essa classe voce deve implementar seu comparador. Primeiro
 * implemente todos os métodos requeridos. Depois implemente dois comparadores
 * (com idéias opostas) e teste sua classe com eles. Dependendo do comparador
 * que você utilizar a lista funcionar como ascendente ou descendente, mas a
 * implemntação dos métodos é a mesma.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class OrderedSingleLinkedListImpl<T> implements OrderedSingleLinkedList<T> {

	private SingleLinkedListNode<T> head;
	private Comparator<T> comparator;

	public OrderedSingleLinkedListImpl() {
	}

	public OrderedSingleLinkedListImpl(Comparator<T> comparator) {
		this.comparator = comparator;
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	@Override
	public int size() {
		if (isEmpty()) {
			return 0;

		} else {
			int size = 0;
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
		if (isEmpty() || (element == null)) {
			return null;
		} else {
			SingleLinkedListNode<T> aux = this.head;
			while (!aux.isNIL() && !aux.getData().equals(element)) {
				aux = aux.getNext();
			}
			return aux.getData();
		}
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> NIL = new SingleLinkedListNode<>();
		SingleLinkedListNode<T> node = new SingleLinkedListNode<T>(element, NIL);

		if (element == null) {
			this.head = NIL;
		}
		if (isEmpty()) {
			this.head = node;

		} else if (this.comparator.compare(node.getData(), this.head.getData()) < 0) {
			node.setNext(this.head);
			this.head = node;

		} else {
			boolean inseriuOrdem = false;
			SingleLinkedListNode<T> aux = this.head;
			SingleLinkedListNode<T> aux2 = this.head.getNext();

			while (!aux2.isNIL() && inseriuOrdem == false) {
				if (this.comparator.compare(node.getData(), aux.getData()) > 0) {
					if (this.comparator.compare(aux2.getData(), node.getData()) > 0) {// TALVEZ
																						// nem
																						// precise
																						// disso.
						SingleLinkedListNode<T> aux3 = aux2;
						aux.setNext(node);
						node.setNext(aux3);
						inseriuOrdem = true;
					}

					aux = aux.getNext();
					aux2 = aux2.getNext();

				}
			}

			if (!inseriuOrdem) {
				aux.setNext(node);
			}
		}
	}

	@Override
	public void remove(T element) {
		if (search(element) != null) {

			if (this.head.getData().equals(element)) {
				this.head = this.head.getNext();

			} else {

				SingleLinkedListNode<T> NIL = new SingleLinkedListNode<>();
				// uso de dois apontadores para simular o previous.
				SingleLinkedListNode<T> aux = this.head;
				SingleLinkedListNode<T> aux2 = this.head.getNext();

				while (!aux2.isNIL() && !aux2.getData().equals(element)) {
					aux = aux.getNext();
					aux2 = aux2.getNext();
				}

				aux.setNext(aux2.getNext());
				aux2.setNext(NIL);

			}
		} else {
			throw new RuntimeException("element contains not in list");
		}
	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Object[size()];

		SingleLinkedListNode<T> aux = this.head;

		for (int i = 0; i < array.length; i++) {
			array[i] = aux.getData();
			aux = aux.getNext();
		}

		return array;
	}

	@Override
	public T minimum() {
		if (isEmpty()) {
			return null;
		}else {
			return this.head.getData();			
		}
	}

	@Override
	public T maximum() {
		if (isEmpty()) {
			return null;
		}else {			
			T maximum = this.head.getData();
			SingleLinkedListNode<T> next = this.head.getNext();
			
			while (!next.isNIL()) {
				if ((this.comparator.compare(next.getData(), maximum)) > 0) {
					maximum = next.getData();
				}
				next = next.getNext();
			}
			
			return maximum;
		}
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
		inverterList();
	}

	/**
	 * metodo que fiz para poder inverter a lista dependendo do comparator.
	 */
	private void inverterList() {
		SingleLinkedListNode<T> node = this.head; //"amarro" a cabeca
		SingleLinkedListNode<T> NIL = new SingleLinkedListNode<T>();
		setHead(NIL); //reseto a cabeca original.

		while (!node.isNIL()) {
			insert(node.getData()); // aqui vai inserir dependendo do
									// comparator.
			node = node.getNext();
		}
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}
}
