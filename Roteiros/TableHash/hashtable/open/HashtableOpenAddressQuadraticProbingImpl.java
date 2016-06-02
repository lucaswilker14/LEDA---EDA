package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		initiateInternalTable(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
	}

	@Override
	public void insert(T element) {
		if (isFull()) {
			throw new HashtableOverflowException();
		} else if (element != null && search(element) == null) {
			insertRecursive(element, 0);
		}
	}

	public void insertRecursive(T element, int probe) {
		if (element != null) {
			int functionHash = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);
			if (table[functionHash] == null || table[functionHash].equals(new DELETED())) {
				table[functionHash] = element;
				elements++;
			} else {
				COLLISIONS++;
				probe++;
				insertRecursive(element, probe);
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			if (search(element) != null) {
				table[indexOf(element)] = new DELETED();
				elements--;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T search(T element) {
		if (element != null) {
			int probe = 0;
			int functionHash = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);
			T aux = (T) super.table[functionHash];

			while ((aux == null || !aux.equals(element)) && probe < super.capacity()) {
				probe++;
				functionHash = ((HashFunctionQuadraticProbing<T>) hashFunction).hash(element, probe);
				aux = (T) super.table[functionHash];
			}

			if (aux != null && aux.equals(element))
				return (T) super.table[functionHash];
		}
		return null;
	}

	@Override
	public int indexOf(T element) {
		if(element != null) {
			for (int i = 0; i < super.table.length; i++){
				if (super.table[i] != null && super.table[i].equals(element)){
					return i;
				}
			}
			return -1;
		}
		return -1;
	}
}