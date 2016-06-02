package adt.heap;


public class MaxHeapImpl<T extends Comparable<? super T>> implements MaxHeap<T> {
	
	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;
	private int proximaPosicao = 0;
	private T[] heap;
	int tamanho;
	
	public MaxHeapImpl(){
		T[] ts = (T[]) new Comparable[INITIAL_SIZE];
		heap = ts;        
		tamanho = -1;
	}
	
	private int parent(int i){
		int pai = (i-1)/2;
		return pai;
	}
	
	private int left(int i){
		int esquerda = 2*i + 1;
		return esquerda;
	}

	private int right(int i){
		int direita = 2*i + 2;
		return direita;
	}

	@Override
	public void buildHeap(T[] array){
		int size = array.length;
		for (int i = (size - 1) / 2; i > 0; i--){
			heapify(i, array);
		}
	}
	
	private void heapify(int position){
		heapify(position, heap);
	}
	
	private void heapify(int position, T[] array){
		int l = left(position);
		int r = right(position);
		int largest;
		
		if ((l <= array.length) && (array[l].compareTo(array[position]) > 0)){
			largest = l;
		}else{
			largest = position;
		}
		if ((r <= array.length) && (array[r].compareTo(array[largest]) > 0)){
			largest = r;
		}
		if (largest != position){
			Util.swap(array, position, largest);
			heapify(largest);
		}
	}
	
	@Override
	public boolean isEmpty(){
		if (proximaPosicao == 0){
			return true;
		}
		return false;
	}
	
	@Override
	public void insert(T element) {
		if(proximaPosicao == INITIAL_SIZE){
			int tamanho = heap.length + INCREASING_FACTOR;
			 T[] ts = (T[]) new Comparable[tamanho];
			T[] aux = ts;
			for(int i = 0; i < heap.length-1; i++){
				aux[i] = heap[i];
			}
			heap = aux;
		}
		if (proximaPosicao == 0) {
			heap[proximaPosicao] = element;
			proximaPosicao++;
		} else { 
			int i = proximaPosicao;
			heap[proximaPosicao] = element;
			while (i > 0 && (heap[parent(i)].compareTo(element) < 0)){
				Util.swap(heap, i, parent(i));
				i = parent(i);
				element = heap[i];
			}
			proximaPosicao++; 
		}
	}
	
	@Override
	public T extractMaximum(){
		if(proximaPosicao >= 0){
			Integer max;
			max = (Integer) heap[0];
			heap[0] = heap[proximaPosicao];
			proximaPosicao --;
			heapify(0);
			T max2 = (T) max;
			return max2;
		}
		return null;
	}

	@Override
	public T maximum() {
		if(!isEmpty()){
			return heap[0];
		}
		return null;
	}

	@Override
	public T[] heapsort(T[] array) {
		buildHeap(array);
		for (int i = 0; i > array.length / 2; i++) {
			Util.swap(array, 1, i);
			tamanho -= 1;
			heapify(1);
		}
		return null;
	}
	
	@Override
	public T[] toArray() {
		return heap;
	}
}