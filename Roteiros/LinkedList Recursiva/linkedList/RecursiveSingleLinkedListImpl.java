package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;
	protected RecursiveSingleLinkedListImpl<T> nillNode;

	public RecursiveSingleLinkedListImpl() {
		this.nillNode = new RecursiveSingleLinkedListImpl<T>(null, null);

	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		if (data == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		if (isEmpty()) {
			return 0;
		} else {
			return 1 + next.size();
		}
	}

	@Override
	public T search(T element) {
		if (isEmpty()) {
			return null;
		} else {
			if (data == element) {
				return data;
			} else {
				return next.search(element);
			}
		}
	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			data = element;
			next = new RecursiveSingleLinkedListImpl<T>();
		} else {
			next.insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (data == element) {
				data = next.getData();
				next = next.getNext();
			} else {
				next.remove(element);
			}
		}
	}

	@Override
	public T[] toArray() {
		int i = 0;
		T[] array = (T[]) new Object[size()];
		toArray(array, this, i);
		return array;

	}

	private void toArray(T[] array, RecursiveSingleLinkedListImpl<T> node, int i) {
		if (!(node.getData() == nillNode.getData())) {
			array[i] = node.getData();
			toArray(array, node.next, i + 1);
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}
}
