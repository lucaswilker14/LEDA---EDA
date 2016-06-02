package sorting.variationsOfSelectionsort;


import java.util.Arrays;

import sorting.AbstractSorting;
import sorting.Util;

/**
 * This algorithm simulates a logical partitioning of the input array by considering 
 * different indexing, that is, the first sub-array is indexed by even elements and
 * the second sub-array is indexed by odd elements. Then, it applies a complete selectionsort
 * in the first sub-array considering neighbours (even). After that, 
 * it applies a complete selectionsort in the second sub-array considering
 * neighbours (odd).  After that, the algorithm performs a merge between elements indexed
 * by even and odd numbers.
 */
public class OddEvenSelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

private T[] helper;
	
	/*private void verificaArray(T[] array)throws RuntimeException{
		
		if (array == null) {
			throw new RuntimeException("Vetor não pode ser null");
		}
	}*/
	
	@Override
	public void sort(T[] array,int leftIndex, int rightIndex)throws RuntimeException{
		
		//verificaArray(array); //tentei lancar uma exception pra verificar se ovetor eh null. Mas tinha que mexer na classe do professor(ABSTRACTSORTING).
		
		for (int i = 0; i < array.length; i++) {
			int menor = i;
			
			for (int j = i+2; j <= rightIndex; j = j + 2) {
				if (array[j].compareTo(array[menor]) < 0) {
					menor = j;
				}
			}Util.swap(array, menor, i);
		}
		
		merge(array, leftIndex, rightIndex);
	}
	
	private void merge(T[] array, int inicio, int fim){
		
		helper = Arrays.copyOf(array, array.length);
		
		int i = inicio;
		int j = inicio + 1;
		int k = inicio;
		
		
		while(i<=fim && j<=fim){
			
			if (helper[i].compareTo(helper[j]) < 0) {
				array[k] = helper[i];
				i = i + 2;
			
			}else {
				array[k] = helper[j];
				j = j + 2;
			}
			k++;
		}
		
		while(i<=fim){
			array[k] = helper[i];
			i = i + 2;
			k++;
		}
		
		while(j<=fim){
			array[k] = helper[j];
			j = j + 2;
			k++;
		}
	}
}
