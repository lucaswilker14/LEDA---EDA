package adt.rbtree;

import adt.avltree.AVLTree;

/**
 * Interface representando uma árvore vermelha e preta. 
 */
public interface RBTree<T extends Comparable<T>> extends AVLTree<T>{
	/**
	 * Método que retorna um array de RBNode (ao invés de T) como resultado
	 * do percurso da arvore em pre ordem.
	 * @return
	 */
	public RBNode<T>[] extendedPreOrder();
}
