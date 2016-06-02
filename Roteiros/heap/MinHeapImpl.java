package adt.heap;

import java.util.Arrays;

public class MinHeapImpl<T extends Comparable<T>> implements MinHeap<T> {

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;
	private T[] array;
	private int index;

	public MinHeapImpl() {
		this.array = (T[]) new Comparable[INITIAL_SIZE];
		this.index = -1;
	}

	@Override
	public boolean isEmpty() {
		return this.index == -1;
	}

	/**
	 * metodo criado para sempre pegar o posicao do pai do elemento.
	 * 
	 * @param i
	 * @return
	 */
	private int getParent(int i) {
		int parent = (i - 1) / 2; // melhor para o debug
		return parent;
	}

	/**
	 * metodo criado para sempre pegar o posicao do lado esquerdo do elemento.
	 * 
	 * @param i
	 * @return
	 */
	private int getLeft(int i) {
		int left = (2 * i) + 1;
		if (left > index) {
			return -1;
		}else {
			return left;			
		}
	}

	/**
	 * metodo criado para sempre pegar o posicao do lado direito do elemento.
	 * 
	 * @param i
	 * @return
	 */
	private int getRight(int i) {
		int right = 2 * (i + 1);
		if (right > index) {
			return -1;
		}else {
			return right;			
		}
	}

	@Override
	public void insert(T element) {
		if (element == null) {
			// throw new RuntimeException();
			//OU NAO FAZER NADA

		} else if (this.index == this.array.length - 1) {
			this.array = Arrays.copyOf(this.array, this.array.length + INCREASING_FACTOR); // aumentando
																							// array
			insertElement(element); // e insiro

		} else {
			insertElement(element);
		}
	}

	/**
	 * metodo privado que criei para poder ficar desacoplado.
	 * 
	 * @param element
	 */
	private void insertElement(T element) {
		this.index++;
		this.array[index] = element;
		int i = index;

		while (i > 0 && element.compareTo(this.array[getParent(i)]) < 0) {
			Util.swap(this.array, i, getParent(i));
			i = getParent(i);
		}
	}

	@Override
	public T extractRootElement() {
		if (isEmpty()) {
			return null;
		} else {
			T root = this.array[0]; // guardando a raiz.

			Util.swap(this.array, 0, index); // troca a raiz com o ultimo
												// elemento.
			this.array[index] = null; // talvez nem precise mais e para um
										// melhor debug.

			index--; // decrementa o tamanho. Deixa a raiz oculta até o final.

			heapify(0); // sempre ordenando da cabeca.

			return root;
		}
	}

	@Override
	public T rootElement() {
		if (isEmpty()) {
			return null;
		} else {
			return this.array[0];
		}
	}

	@Override
	public T[] heapsort(T[] array) {

		buildHeap(array); // construindo o array usando tambem o heapify.

		T[] arrayOrdenado = (T[]) new Comparable[index+1];

		for (int i = 0; i < this.array.length; i++) {
			arrayOrdenado[i] = extractRootElement(); // sempre removo a cabeca e
														// add.
		}

		return arrayOrdenado;
	}

	@Override
	public void buildHeap(T[] array) {
		// sets
		this.array = array;
		index = array.length - 1;

		for (int i = getParent(index); i >= 0; i--) { // sempre vindo do final
														// cara garantir a
														// cabeca seja mudada.
			heapify(i);
		}
	}

	
	
	private void heapify(int position) {

		int left = getLeft(position);

		int right = getRight(position);

		if (!isEmpty()) { // condicao mais
																// robusta para
																// nao estoura
																// os indices.
			int minimo = position;

			if ((left != -1) && (this.array[left].compareTo(this.array[minimo]) < 0)) {
				minimo = left; // eu so fico atualizando.
			}

			if ((right != -1) && (this.array[right].compareTo(this.array[minimo]) < 0)) {
				minimo = right;
			}

			if (minimo != position) { // ja sei que teve alguem menor, entao eu
										// aplico o recursivo
				Util.swap(this.array, minimo, position);
				heapify(minimo); // passo recursivo.
			}
		}
	}

	
	@Override
	public T[] toArray() {
		T[] newArray = (T[]) new Comparable[index+1];
		for (int i = 0; i <= index; i++) {
			newArray[i] = this.array[i];
		}
		return newArray;
	}
}
