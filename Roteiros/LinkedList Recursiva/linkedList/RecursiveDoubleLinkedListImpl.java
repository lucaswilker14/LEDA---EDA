package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			RecursiveDoubleLinkedListImpl<T> newNode = new RecursiveDoubleLinkedListImpl<T>();
			newNode.data = this.data;
			newNode.next = this.next;
			newNode.previous = this;
			this.data = element;
			this.next = newNode;
			this.previous = new RecursiveDoubleLinkedListImpl<T>(); // NIL
		}

	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			if (previous.isEmpty() && next.isEmpty()) {
				previous = null;
				data = null;
				next = null;
			} else {
				this.data = this.getNext().getData();
				this.next = this.getNext().getNext();
			}
		}

	}

	@Override
	public void removeLast() {
		if (isEmpty()) {
			return;

		} else {
			if (next.isEmpty()) { // verifica se o proximo eh o NIL
				this.data = null;
				this.next = null;
				return;
			} else {
				RecursiveDoubleLinkedListImpl<T> nextNode = (RecursiveDoubleLinkedListImpl<T>) next;
				nextNode.removeLast();
			}

		}

	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			setData(element);
			RecursiveDoubleLinkedListImpl<T> nillNode = new RecursiveDoubleLinkedListImpl<T>();
			setNext((RecursiveSingleLinkedListImpl<T>) nillNode);
			nillNode.previous = this;

			if (this.previous == null) {
				this.previous = nillNode;
			}

		} else {
			this.getNext().insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (isEmpty()) {
			return;
		} else {
			if (this.getData().equals(element)) {
				if (previous.isEmpty() && next.isEmpty()) {
					previous = null;
					data = null;
					next = null;
				} else {
					this.data = this.getNext().getData();
					this.next = this.getNext().getNext();

					if (next != null) {
						RecursiveDoubleLinkedListImpl<T> nextDouble = (RecursiveDoubleLinkedListImpl<T>) next;
						nextDouble.previous = this;
					}

				}

			} else {
				this.getNext().remove(element);
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
