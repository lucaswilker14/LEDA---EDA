package adt.splaytree;

import adt.avltree.AVLTreeImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;

public class SplayTreeImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements
		SplayTree<T> {
	
	
	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = search(this.root, element);
		if (!node.isEmpty() && node != this.root) {
			splay(node);
		}
		return this.root;
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.isEmpty() || node.getData().equals(element)) {
			return node;

		} else if ((node.getData().compareTo(element)) < 0) { //verificando se node.getDta() < elemento - procurar a direita.
			if (node.getRight().isEmpty()) {
				return node;
			}else {
				return search((BSTNode<T>) node.getRight(), element);				
			}

		} else {
			if (node.getLeft().isEmpty()) {
				return node;
			}else {
				return search((BSTNode<T>) node.getLeft(), element);				
			}
		}
	}


	//sobreescrita do metodo de avl para mudar o rebalance.
	public void insert(T element) {

		if (isEmpty() && element != null) {
			root.setData(element);
			root.setLeft(new BSTNode<T>());
			root.setRight(new BSTNode<T>());
		} else if (element != null){
			BSTNode<T> nodeAux = insert(element, this.getRoot());
			splay(nodeAux);
		}
	}

	private BSTNode<T> insert(T element, BTNode<T> node) {

		if (element.compareTo(node.getData()) < 0) {

			if (node.getLeft().isEmpty()) {
				node.getLeft().setData(element);

				node.getLeft().setParent(node);
				node.getRight().setParent(node);

				node.getLeft().setLeft(new BSTNode<T>());
				node.getLeft().setRight(new BSTNode<T>());
				
				return (BSTNode<T>) node.getLeft();
			
			} else {
				return insert(element, node.getLeft());
			}

		} else if (element.compareTo(node.getData()) > 0) {

			if (node.getRight().isEmpty()) {

				node.getRight().setData(element);

				node.getLeft().setParent(node);
				node.getRight().setParent(node);

				node.getRight().setLeft(new BSTNode<T>());
				node.getRight().setRight(new BSTNode<T>());

				return (BSTNode<T>) node.getRight();
						
			} else {
				return insert(element, node.getRight());
			}
		}
		return null;
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = search(this.root, element);
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		
		if (node == this.root) {	
			remove(node);
		
		}else if (node.getData() != element) { //criei essa condição para verificar de acordo com o meu search.
			splay(node);						//no caso que dizer que nao tem o element.
		
		}else if ((!node.isEmpty()) && (element != null)) {
			remove(node);
			splay(parent);
		
		}else {
			splay(parent);
		}
	}
	
	//remove da bst.
	private void remove(BSTNode<T> node) {
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
			remove((BSTNode<T>) sucessor);
		}
	}
	
	
	private void splay(BSTNode<T> node){
		while(node != this.root){
			
			//caso 1
			
			//se o pai eh raiz e o no e filho a esquerda
			if ((node.getParent() == root) && (node.getParent().getLeft() == node)) {
				zig_right(node); //faz a rotacao para a direita
				
			//se o pai eh raiz e o no e filho a direita
			}else if ((node.getParent() == root) && (node.getParent().getRight() == node)) {
				zig_left(node);//faz a rotacao para a esquerda.
			}	
				
			
			//caso 2
			
			//se o pai nao eh raiz e o no e pai sao filhos a esquerda.
			else if ((node.getParent() != root) && (node.getParent().getLeft() == node) && (node.getParent().getParent().getLeft() == node.getParent())) {
				zig_zig_right(node); //faz duas rotacoes para a direita(avo e depois pai)
				
			//se o pai nao eh raiz e o no e pai sao filhos a direita.
			}else if ((node.getParent() != root) && (node.getParent().getRight() == node) && (node.getParent().getParent().getRight() == node.getParent())) {
				zig_zig_left(node); //faz duas rotacoes para a esquerda(avo e depois pai)
			}
			
			
			//caso 3
			
			//joelho apontando para a direita
			else if ((node.getParent() != this.root) && (node.getParent().getLeft() == node) && (node.getParent().getParent().getRight() == node.getParent())) {
				zig_right((BSTNode<T>) node);
				zig_left((BSTNode<T>) node);
			}
			
			//joelho apontando para a esquerda
			else if ((node.getParent() != this.root) && (node.getParent().getRight() == node) && (node.getParent().getParent().getLeft() == node.getParent())) {
				zig_left((BSTNode<T>) node);
				zig_right((BSTNode<T>) node);
			}
		}
	}
	
	private void zig_right(BSTNode<T> node){
		rightRotation((BSTNode<T>) node.getParent());
	}
	
	private void zig_left(BSTNode<T> node){
		leftRotation((BSTNode<T>) node.getParent());
	}
	
	private void zig_zig_right(BSTNode<T> node){
		rightRotation((BSTNode<T>) node.getParent().getParent());
		rightRotation((BSTNode<T>) node.getParent());
	}
	
	private void zig_zig_left(BSTNode<T> node){
		leftRotation((BSTNode<T>) node.getParent().getParent());
		leftRotation((BSTNode<T>) node.getParent());
	}
}
