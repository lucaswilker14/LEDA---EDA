package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;

public class AVLTreeImpl<T extends Comparable<T>> 
    extends BSTImpl<T> implements AVLTree<T> {

	//TODO Do not forget: you must override the methods insert and remove conveniently.

	@Override
	public void insert(T element) {

		if (isEmpty() && element != null) {
			root.setData(element);
			root.setLeft(new BSTNode<T>());
			root.setRight(new BSTNode<T>());
		} else if (element != null)
			insert(this.getRoot(), element);

	}

	private void insert(BTNode<T> node, T element) {

		if (element.compareTo(node.getData()) < 0) {

			if (node.getLeft().isEmpty()) {
				node.getLeft().setData(element);

				node.getLeft().setParent(node);
				node.getRight().setParent(node);

				node.getLeft().setLeft(new BSTNode<T>());
				node.getLeft().setRight(new BSTNode<T>());
			} else {
				insert(node.getLeft(), element);
				rebalance((BSTNode<T>) node);
			}

		} else if (element.compareTo(node.getData()) > 0) {

			if (node.getRight().isEmpty()) {

				node.getRight().setData(element);

				node.getLeft().setParent(node);
				node.getRight().setParent(node);

				node.getRight().setLeft(new BSTNode<T>());
				node.getRight().setRight(new BSTNode<T>());

			} else {
				insert(node.getRight(), element);
				rebalance((BSTNode<T>) node);
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {

			BSTNode<T> node = search(element);
			if (!node.isEmpty()) {

				if (node == root && size() == 1) {
					root = new BSTNode<T>();

				} else if (node == root) {
					BSTNode<T> auxNode = sucessor(node.getData());

					if (auxNode == null) {
						auxNode = predecessor(node.getData());
					}

					T aux = auxNode.getData();
					auxNode.setData(node.getData());
					node.setData(aux);

					remove(auxNode);

				} else
					remove(node);
			}
		}
	}

	private void remove(BSTNode<T> node) {

		boolean isLeft = false;

		if (node.getParent() != null) {
			isLeft = node == node.getParent().getLeft();
		}

		// sem filhos
		if (node.getLeft().isEmpty() && node.getRight().isEmpty()) {

			node.setData(null);
			node.setRight(null);
			node.setLeft(null);

			rebalanceUp(node);

		}

		// 1 filho na direita.
		else if (node.getLeft().isEmpty()) {

			if (node.getParent() != null) {
				if (isLeft) {
					node.getParent().setLeft(node.getRight());
				} else
					node.getParent().setRight(node.getRight());
			}

			node.getRight().setParent(node.getParent());
			rebalanceUp(node);

		}

		// 1 filho na esquerda
		else if (node.getRight().isEmpty()) {

			if (node.getParent() != null) {
				if (isLeft) {
					node.getParent().setLeft(node.getLeft());
				} else {
					node.getParent().setRight(node.getLeft());
				}
			}
			node.getLeft().setParent(node.getParent());
			rebalanceUp(node);
		}

		// 2 filhos
		else {

			BSTNode<T> sucessor = sucessor(node.getData());

			T aux = sucessor.getData();
			sucessor.setData(node.getData());
			node.setData(aux);

			remove(sucessor);

		}
	}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if (!node.isEmpty()) {
			return getHeight(node.getRight()) - getHeight(node.getLeft());
		} else {
			return 0;
		}
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {

		int balance = calculateBalance(node);
		if (balance > 1) {
			if (calculateBalance((BSTNode<T>) node.getRight()) < 0) {
				doubleLeftRotation(node);
			} else {
				leftRotation(node);
			}
		} else if (balance < -1) {
			if (calculateBalance((BSTNode<T>) node.getLeft()) > 0) {
				doubleRightRotation(node);
			} else
				rightRotation(node);
		}
	}

	// AUXILIARY
	protected void doubleLeftRotation(BSTNode<T> node) {
		rightRotation((BSTNode<T>) node.getRight());
		leftRotation(node);
	}

	// AUXILIARY
	protected void doubleRightRotation(BSTNode<T> node) {
		leftRotation((BSTNode<T>) node.getLeft());
		rightRotation(node);
	}

	// AUXILIARY
	protected void leftRotation(BSTNode<T> node) {

		BTNode<T> y = node.getRight();

		node.setRight(y.getLeft());
		y.getLeft().setParent(node);

		y.setLeft(node);

		y.setParent(node.getParent());
		node.setParent(y);

		if (node != root) {
			
			if (y.getParent().getLeft() == node){
				y.getParent().setLeft(y);	
			
			}else{
				y.getParent().setRight(y);
			}
			
		}else {
			root = (BSTNode<T>) y;
		}
	}

	// AUXILIARY
	protected void rightRotation(BSTNode<T> node) {

		BTNode<T> y = node.getLeft();

		node.setLeft(y.getRight());
		y.getRight().setParent(node);

		y.setRight(node);

		y.setParent(node.getParent());
		node.setParent(y);

		if (node != root) {
			if (y.getParent().getLeft() == node)
				y.getParent().setLeft(y);
			else
				y.getParent().setRight(y);
		} else {
			root = (BSTNode<T>) y;
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (parent != null) {
			rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}

	// Fiz esse metodo para ficar buscando a altura e reuza-la.
	protected int getHeight(BTNode<T> node) {
		return height((BSTNode<T>) node);
	}
	
}
