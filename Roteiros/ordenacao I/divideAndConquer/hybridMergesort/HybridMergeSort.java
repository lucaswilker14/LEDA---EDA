package sorting.divideAndConquer.hybridMergesort;

import sorting.AbstractSorting;
import sorting.Util;

/**
 * A classe HybridMergeSort representa a implementa√ß√£o de uma varia√ß√£o do MergeSort 
 * que pode fazer uso do InsertionSort (um algoritmo h√≠brido) da seguinte forma: 
 * o MergeSort √© aplicado a entradas maiores a um determinado limite. Caso a entrada 
 * tenha tamanho menor ou igual ao limite o algoritmo usa o InsertionSort. 
 * 
 * A implementa√ß√£o h√≠brida deve considerar os seguintes detalhes:
 * - Ter contadores das quantidades de MergeSorts e InsertionSorts aplicados, de 
 *   forma que essa informa√ß√£o possa ser capturada pelo teste.
 * - A cada chamado do m√©todo de sort(T[] array) esses contadores s√£o resetados. E a cada
 *   chamada interna de um merge ou insertion, os contadores MERGESORT_APPLICATIONS e 
 *   INSERTIONSORT_APPLICATIONS s√£o incrementados.
 *  - O InsertionSort utilizado no algoritmo h√≠brido deve ser in-place.
 */
public class HybridMergeSort<T extends Comparable<T>> extends AbstractSorting<T> {
    
	private T[] numeros;
	private T[] helper;
	private int tamanho;
	
	/**
	 * For inputs with size less or equal to this value, the insertionsort
	 * algorithm will be used instead of the mergesort.
	 */
	public static final int SIZE_LIMIT = 4;
	
	protected static int MERGESORT_APPLICATIONS = 0;
	protected static int INSERTIONSORT_APPLICATIONS = 0;
	
	public void sort(T[] array, int leftIndex, int rightIndex) {
		this.numeros = array;
		this.tamanho = array.length;
		this.helper = (T[]) new Comparable[tamanho];
		
		if (array.length <= SIZE_LIMIT) {
			insertionSort(numeros, leftIndex, rightIndex);
			INSERTIONSORT_APPLICATIONS ++;
		
		}else {
			if (leftIndex < rightIndex) {
			int meio = leftIndex + (rightIndex - leftIndex) / 2;

			// passo recursivo - divide do inicio ao meio
			divisao(leftIndex, meio);

			// passo recursivo - divide do meio + 1 atÈ o final
			divisao(meio + 1, rightIndex);

			// aqui comeÁa o processo de conquista(junÁ„o).
			mergeSort(leftIndex, meio, rightIndex);
			MERGESORT_APPLICATIONS ++;
			}
		}
	}
	
	
	public void sort(T[] array) {
		MERGESORT_APPLICATIONS = 0;
		INSERTIONSORT_APPLICATIONS = 0;
		super.sort(array);
	}
	
	private void insertionSort(T[] array, int leftIndex, int rightIndex) {
		
		int j = 0;
		int chave = 0;
		
		for (int i = 1; i < rightIndex; i++) {
			chave = i;
			j = i;
			
			while((j > 0) && (array[j-1].compareTo(array[chave])) > 0){
				array[j+1] = array[j];
				j--;
			}
			Util.swap(array, j, chave);
		}
	}
	
	
	private void mergeSort(int inicio, int meio, int fim){
		
		//copia os numeros do array 'numeros' para o array 'helper'.
		for (int i = 0; i < helper.length; i++) {
			helper[i] = numeros[i];
		}
				
		int i = inicio;
		int j = meio + 1;
		int k = inicio;
				
		//primeiro loop de conquista
		while(i <= meio && j<= fim){
			if (helper[i].compareTo(helper[j]) <= 0) { //se a posicao de i <= a posicao de j...
		 		numeros[k] = helper[i];
				i++;
			}else {
				numeros[k] = helper[j];
				j++;
			}
			k++;
		}
				
		while(i<=meio){
			numeros[k] = helper[i];
			i++;
			k++;
		}
		
		while(j<= fim){
			numeros[k] = helper[j];
			j++;
			k++;
		}
	}	
	
	// metodo recursivo aqui
	private void divisao(int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex) {
			// sempre calculando o valor do meio
			int meio = leftIndex + (rightIndex - leftIndex) / 2;

			// passo recursivo - divide do inicio ao meio
			divisao(leftIndex, meio);

			// passo recursivo - divide do meio + 1 atÈ o final
			divisao(meio + 1, rightIndex);

			// aqui comeÁa o processo de conquista(junÁ„o).
			mergeSort(leftIndex, meio, rightIndex);
			MERGESORT_APPLICATIONS ++;
		}
	}
}
