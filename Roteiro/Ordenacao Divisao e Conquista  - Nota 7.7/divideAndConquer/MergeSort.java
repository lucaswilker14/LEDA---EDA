package sorting.divideAndConquer;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm.  
 * The algorithm consists of recursively dividing the unsorted list in the middle,
 * sorting each sublist, and then merging them into one single sorted list.
 * Notice that if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	private T[] numeros;
	private T[] helper;
	private int tamanho;
	
	@SuppressWarnings("unchecked")
	@Override
	public void sort(T[] array,int leftIndex, int rightIndex /*<---->*/ /*isso daqui ja é array.lenght -1*/ ) {
		this.numeros = array;
		this.tamanho = array.length;
		this.helper = (T[]) new Comparable[tamanho];
		divisao(leftIndex, rightIndex); //aqui começa o processo de divisão.
	}
	
	//metodo recursivo aqui
	private void divisao(int leftIndex, int rightIndex){
		if (leftIndex < rightIndex) {
			
			//sempre calculando o valor do meio
			int meio = leftIndex + (rightIndex - leftIndex) / 2;
			
			//passo recursivo - divide do inicio ao meio
			divisao(leftIndex, meio);
			
			//passo recursivo - divide do meio + 1 até o final
			divisao(meio + 1, rightIndex);
			
			//aqui começa o processo de conquista(junção).
			conquista(leftIndex, meio, rightIndex);
		}
	}
	
	private void conquista(int inicio, int meio, int fim){
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
		
		
		while(j<=fim){
			numeros[k] = helper[j];
			j++;
			k++;
		}
	}
}
