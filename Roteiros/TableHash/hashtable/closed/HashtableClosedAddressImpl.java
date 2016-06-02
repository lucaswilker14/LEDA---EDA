package adt.hashtable.closed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

import adt.hashtable.AbstractHashtable;
import adt.hashtable.Util;
import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionDivisionMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
														// the immediate prime
														// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */
	int getPrimeAbove(int number) {
		int primeAbove = number;

		// enquanto o primo nao for primo.
		while (!Util.isPrime(primeAbove))  {
			primeAbove++; // eu sempre acrescento +1. tipo: o number eh 100(nao eh primo)
						  // cai aqui, e vai para 101. EH PRIMO!
		}
		return primeAbove;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(T element) {
		if (element != null && search(element) == null) {
			
			//calcula a funcao de hash
			int funcionHash = ((HashFunctionClosedAddress<T>) (hashFunction)).hash(element);

			// verifica se o espaco la ta vazio
			if (table[funcionHash] == null) {
				//cria o arrayList
				table[funcionHash] = new ArrayList<T>();
			}

			//se o numero do arrayList eh maior que zero eh porque tem algum la, ou seja, COLISAO.
			if (((ArrayList<T>) (table[funcionHash])).size() > 0) {
				COLLISIONS++;
			}

			//sempre add o carinha
			((ArrayList<T>) (table[funcionHash])).add(element);
			this.elements++; //e o numero de elementos da minha hash aumenta.
		}
	}

	@Override
	public void remove(T element) {

		if (element != null && search(element) != null) {

			int funcionHash = ((HashFunctionClosedAddress<T>) (hashFunction)).hash(element);

			//aqui ja faz um for imbutido pra procurar o tal elemento e remove.
			((ArrayList<T>) (table[funcionHash])).remove(element);

			this.elements--; //assim que remover, consequentemente o numero de elementos na minha tabela dimunui.
		}
	}

	@Override
	public T search(T element) {
		if (element != null) {
			int funcionHash = ((HashFunctionClosedAddress<T>) (hashFunction)).hash(element);
			
			//sempre possivel encontrar um espaco vazio.
			//e verificar se naquela funcao hash o cara ta la.
			if ( (table[funcionHash] != null) && ( (ArrayList<T>)(table[funcionHash])).contains(element)) {
				return element;
			}
		}
		return null;
	}

	@Override
	public int indexOf(T element) {
		
		//reaproveito o search
		if (element != null && search(element) != null) {
			int funcionHash = ( (HashFunctionClosedAddress<T>)(hashFunction) ).hash(element);
			return funcionHash; //e so faco retornar a hash.
		}else {
			return -1; //ou o numero magico.
		}
	}
}
