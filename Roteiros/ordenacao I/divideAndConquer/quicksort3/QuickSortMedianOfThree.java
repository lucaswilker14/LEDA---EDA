package sorting.divideAndConquer.quicksort3;

import sorting.AbstractSorting;
import sorting.Util;

/**
 * A classe QuickSortMedianOfThree representa uma variação do QuickSort que
 * funciona de forma ligeiramente diferente. Relembre que quando o pivô
 * escolhido divide o array aproximadamente na metade, o QuickSort tem um
 * desempenho perto do ótimo. Para aproximar a entrada do caso ótimo, diversas
 * abordagens podem ser utilizadas. Uma delas é usar a mediana de 3 para achar o
 * pivô. Essa técnica consiste no seguinte: 1. Comparar o elemento mais a
 * esquerda, o central e o mais a direita do intervalo. 2. Ordenar os elemento,
 * tal que: A[left] < A[center] < A[right]. 3. Adotar o A[center] como pivô. 4.
 * Colocar o pivô na penúltima posição A[right-1]. 5. Aplicar o particionamento
 * considerando o vetor menor, de A[left+1] até A[right-1]. 6. Aplicar o
 * algoritmo na metade a esquerda e na metade a direita do pivô.
 */
public class QuickSortMedianOfThree<T extends Comparable<T>> extends AbstractSorting<T> {

	public int medianaDeTres(T[] vetor, int inicio, int fim) {

		int meio = inicio + (fim - inicio) / 2;

		// inicio < meio > fim
		if (vetor[inicio].compareTo(vetor[meio]) == -1 && vetor[meio].compareTo(vetor[fim]) == 1) {
			Util.swap(vetor, meio, fim);
			// inicio > meio and incio < fim
		} else if (vetor[inicio].compareTo(vetor[meio]) == 1 && vetor[inicio].compareTo(vetor[fim]) == -1) {
			Util.swap(vetor, inicio, meio);
			// inicio < meio and inicio > fim
		} else if (vetor[inicio].compareTo(vetor[meio]) == -1 && vetor[inicio].compareTo(vetor[fim]) == 1) {
			Util.swap(vetor, inicio, meio);
			Util.swap(vetor, inicio, fim);
			// inicio > meio and incio > fim and meio < fim
		} else if (vetor[inicio].compareTo(vetor[meio]) == 1 && vetor[inicio].compareTo(vetor[fim]) == 1
				&& vetor[meio].compareTo(vetor[fim]) == -1) {
			Util.swap(vetor, inicio, meio);
			Util.swap(vetor, meio, fim);
			// inicio > meio and incio > fim and meio > fim
		} else if (vetor[inicio].compareTo(vetor[meio]) == 1 && vetor[inicio].compareTo(vetor[fim]) == 1
				&& vetor[meio].compareTo(vetor[fim]) == 1) {
			Util.swap(vetor, inicio, meio);
			Util.swap(vetor, meio, fim);
		}

		return meio;

	}

	private int separar(T[] vetor, int inicio, int fim) {

		T pivo = vetor[inicio];
		int i = inicio + 1;
		int f = fim;

		while (i <= f) {
			if (pivo.compareTo(vetor[i]) == 1) {
				i++;

			} else if (pivo.compareTo(vetor[f]) == -1) {
				f--;

			} else {
				Util.swap(vetor, i, f);
				i++;
				f--;
			}
		}
		vetor[inicio] = vetor[f];
		vetor[f] = pivo;
		return f;
	}

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		int inicio = leftIndex;
		int fim = rightIndex;
		if (inicio < fim) {
			int meio = medianaDeTres(array, inicio, fim);
			Util.swap(array, meio, fim - 1);
			int posicaoPivo = separar(array, inicio, fim);
			sort(array, inicio, posicaoPivo - 1);
			sort(array, posicaoPivo + 1, fim);
		}
	}
}