package sorting.divideAndConquer;

import sorting.AbstractSorting;
import sorting.Util;

/**
 * Quicksort is based on the divide-and-conquer paradigm.
 * The algorithm chooses a pivot element and rearranges the elements of the
 * interval in such a way that all elements lesser than the pivot go to the
 * left part of the array and all elements greater than the pivot, go to the
 * right part of the array. Then it recursively sorts the left and the right parts.
 * Notice that if the list has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {


	@Override
	public void sort(T[] array,int leftIndex, int rightIndex) {
		int inicio = leftIndex;
		int fim = rightIndex;
		if (inicio < fim) {
			int posicaoPivo = retornaPivo(array, inicio, fim);
			sort(array, inicio, posicaoPivo - 1);
			sort(array, posicaoPivo + 1, fim);
		}
	}
	
	
	private int retornaPivo(T[] array, int inicio, int fim){
		
		T pivo = array[inicio];
		int i = inicio + 1;
		int f = fim;
		
		while(i <= f){
			if (pivo.compareTo(array[i]) >= 1) {
				i++;
			}else if (pivo.compareTo(array[f]) <= -1) {
				f--;
			}else {
				Util.swap(array, i, f);
				i++;
				f--;
			}
		}
		
		array[inicio] = array[f];
		array[f] = pivo;
		return f;
	}
}
