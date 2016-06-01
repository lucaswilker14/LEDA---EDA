package sorting.simpleSorting;

import sorting.AbstractSorting;
import sorting.Util;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {

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
}
