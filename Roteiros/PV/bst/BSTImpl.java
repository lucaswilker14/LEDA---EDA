package adt.bst;

import java.util.ArrayList;
import java.util.List;

import adt.bt.BTNode;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

protected  BSTNode<T> root;
	
	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot(){
		return this.root;
	}
	
	@Override
	public boolean isEmpty() {
		return root.isEmpty(); //se data == null
	}

	@Override
	public int height() {
		return height(this.root);
	}
	
	//sempre fazendo o uso de um metodo private para melhor coesao e encapsulamento.
	protected int height(BTNode<T> n) {
		if (n.isEmpty()) {
			return -1;
		} else {
			return 1 + Math.max(height((BSTNode<T>) n.getRight()), height((BSTNode<T>) n.getLeft()));
		}
	}

	//metodo que vai verificar se a raiz pode ser nula ou o elemento.
	@Override
	public BSTNode<T> search(T element) {
		if (element == null || this.root.isEmpty()) {
			return new BSTNode<T>();
		} else {
			return search(this.root, element); //sempre passando a raiz
		}
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.isEmpty() || node.getData().equals(element)) {
			return node;

		} else if ((node.getData().compareTo(element)) < 0) { //verificando se node.getDta() < elemento - procurar a direita.
			return search((BSTNode<T>) node.getRight(), element);

		} else {
			return search((BSTNode<T>) node.getLeft(), element);
		}
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			BSTNode<T> NIL = new BSTNode<T>(); //como a raiz nao tem parent, passei um NIL.
			insert(NIL, this.root, element);
		}
	}

	private void insert(BSTNode<T> parent, BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);

		} else {
			if ((node.getData().compareTo(element)) < 0) {
				insert(node, (BSTNode<T>) node.getRight(), element);
			} else {
				insert(node, (BSTNode<T>) node.getLeft(), element);
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		if (this.root.isEmpty()) {
			return null;
		} else {
			return maximum(this.root);
		}
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		if (node.getRight().isEmpty()) {
			return node;
		} else {
			return maximum((BSTNode<T>) node.getRight()); //sempre buscando o elemento maior que fica a direita
		}
	}

	@Override
	public BSTNode<T> minimum() {
		if (this.root.isEmpty()) {
			return null;
		} else {
			return minimum(this.root);
		}
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		if (node.getLeft().isEmpty()) {
			return node;
		} else {
			return minimum((BSTNode<T>) node.getLeft()); //sempre buscando o elemento menor a esquerda
		}
	}


	@Override
	public BSTNode<T> sucessor(T element) {

		BSTNode<T> nodeSearch = search(element); //sempre usando o metodo search para pegar o elemento.

		if (nodeSearch.isEmpty()) { //embora la no metodo search tratar esse caso, ele tem um retorno diferente.
			return null;

		} else {
			if (!nodeSearch.getRight().isEmpty()) { //se a sua direita nao for vazia.
				return minimum((BSTNode<T>) nodeSearch.getRight()); //usa-se sempre o mininum (o menor do maior mais a esquerda)
			
			} else {
				return sucessor((BSTNode<T>) nodeSearch.getParent(), nodeSearch); //passando o pai do pai("avo"), e o no.
			}
		}
	}

	private BSTNode<T> sucessor(BSTNode<T> parent, BSTNode<T> node) {
		//ficando aqui o passo recursivo
		//sempre subindo na arvore.
		if ((parent != null) && ((!node.equals(parent.getLeft())))) {
			return sucessor((BSTNode<T>) parent.getParent(), (BSTNode<T>) parent);

		} else {
			return parent;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> nodeSearch = search(element);

		if (element == null || nodeSearch.isEmpty()) {
			return null;

		} else {

			if (!nodeSearch.getLeft().isEmpty()) {
				return maximum((BSTNode<T>) nodeSearch.getLeft()); //pegando sempre o menor dos maiores.

			} else {
				return predecessor((BSTNode<T>) nodeSearch.getParent(), nodeSearch);
			}
		}
	}

	private BSTNode<T> predecessor(BSTNode<T> parent, BSTNode<T> node) {

		//aqui fica o passo recursivo.
		if ((parent != null) && ((!node.equals(parent.getRight())))) {
			return predecessor((BSTNode<T>) parent.getParent(), parent); //passando o pai do pai;

		} else {
			return parent;
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if ((!node.isEmpty()) && (element != null)) {
			removeRecursive(node);
		}
	}

	private void removeRecursive(BSTNode<T> node) {
		//geralmente se encaixa para uma folha.
		if (node.getRight().isEmpty() && node.getLeft().isEmpty()) {
			node.setData(null);
			node.setLeft(null);
			node.setRight(null);
		} else if (node.getRight().isEmpty()) {
			node.setData(node.getLeft().getData());
			node.setRight(node.getLeft().getRight());
			node.setLeft(node.getLeft().getLeft());
			node.getRight().setParent(node);
			node.getLeft().setParent(node);

		} else if (node.getLeft().isEmpty()) {
			node.setData(node.getRight().getData());
			node.setLeft(node.getRight().getLeft());
			node.setRight(node.getRight().getRight());
			node.getRight().setParent(node);
			node.getLeft().setParent(node);
		} else {
			T removed_node_value = node.getData();
			BTNode<T> sucessor = sucessor(removed_node_value);
			node.setData(sucessor.getData());
			sucessor.setData(removed_node_value);
			removeRecursive((BSTNode<T>) sucessor);
		}
	}

	@Override
	public T[] preOrder() {
		List<T> aux = new ArrayList<T>();
		//chamada para organizar.
		preOrder(this.root, aux);

		T[] array = (T[]) new Comparable[aux.size()];
		for (int i = 0; i < aux.size(); i++) {
			array[i] = aux.get(i);
		}
		return array;
	}

	private void preOrder(BSTNode<T> node, List<T> aux) {
		//RAIZ, ESRQUEDA , DIREITA
		if (!node.isEmpty()) {
			aux.add(node.getData());
			preOrder((BSTNode) node.getLeft(), aux); //ESQUERDA
			preOrder((BSTNode) node.getRight(), aux); //DIREITA.
		}
	}

	@Override
	public T[] order() {
		List<T> aux = new ArrayList<T>();
		order(this.root, aux);

		T[] array = (T[]) new Comparable[aux.size()];
		for (int i = 0; i < aux.size(); i++) {
			array[i] = aux.get(i);
		}
		return array;
	}

	private void order(BSTNode<T> node, List<T> aux) {
		if (!node.isEmpty()) {
			order((BSTNode) node.getLeft(), aux); //ESQUERDA
			aux.add(node.getData()); //RAIZ
			order((BSTNode) node.getRight(), aux); //DIREITA
		}
	}

	@Override
	public T[] postOrder() {
		List<T> aux = new ArrayList<T>();
		postOrder(this.root, aux);

		T[] array = (T[]) new Comparable[aux.size()];
		for (int i = 0; i < aux.size(); i++) {
			array[i] = aux.get(i);
		}
		return array;
	}

	private void postOrder(BSTNode<T> node, List<T> aux) {
		if (!node.isEmpty()) {
			postOrder((BSTNode<T>) node.getLeft(), aux); //ESQUERDA
			postOrder((BSTNode<T>) node.getRight(), aux); //DIREITA
			aux.add(node.getData()); //RAIZ.
		}
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}
}
