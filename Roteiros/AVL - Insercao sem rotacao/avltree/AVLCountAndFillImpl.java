package adt.avltree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import adt.bst.BSTNode;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T>{

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;
	
	public AVLCountAndFillImpl() {
		LLcounter = 0;
		LRcounter = 0;
		RRcounter = 0;
		RLcounter = 0;
	}

	public void rebalance(BSTNode<T> node) {

		int balance = calculateBalance(node);
		if (balance > 1) {
			if (calculateBalance((BSTNode<T>) node.getRight()) < 0) {
				doubleLeftRotation(node);
				RLcounter++; //(nó desbalanceado pesando para a direita e filho a direita pesa para a esquerda
			} else {
				leftRotation(node);
				RRcounter++; //(nó desbalanceado pesando para a direita e filho a direita pesa para direita ou balanceado)
			}
		} else if (balance < -1) {
			if (calculateBalance((BSTNode<T>) node.getLeft()) > 0) {
				doubleRightRotation(node);
				LRcounter++; //(nó desbalanceado pesando para a esquerda e filho a esquerda pesa para a direita
			}else {
				rightRotation(node);
				LLcounter++; //(nó desbalanceado pesando para a esquerda e filho a esquerda pesa para esquerda ou balanceado
			}
		}
	}
	
	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		if (array == null || array.length == 0)
			return ;
		
//		T[] order = this.order();
		Comparable<Integer>[] arrayOrdenado = (Comparable<Integer>[])this.order();
		List<T> list = new ArrayList<T>(array.length + arrayOrdenado.length);
		
		Collections.addAll(list, array);
		
		Collections.addAll(list, (T[]) arrayOrdenado);
		
		Collections.sort(list);
		
		this.root = new BSTNode<T>(); 
		
		Deque<Integer[]> fila = new LinkedList<Integer[]>();
		
		Integer[] element = this.getElement(0, list.size() - 1);
		
		fila.add(element);
		
		int contador = 0;
		
		while(contador < list.size()) {
			
			element = fila.remove();
			
			insert(list.get(element[1]));
			
			contador++;
			
			if (element[0] != element[1]) {
				fila.addLast(this.getElement(element[0], element[1] - 1));
			}
			if (element[1] != element[2]) {
				fila.addLast(this.getElement(element[1] + 1, element[2]));
			}
		}
	}
	
	private Integer[] getElement(int inicio, int fim) { 
		Integer[] array = new Integer[3];
		array[0] = inicio;
		array[1] = inicio + (fim - inicio) / 2;
		array[2] = fim;
		return array;
	}
}
