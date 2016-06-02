package adt.avltree;

public interface AVLCountAndFill<T extends Comparable<T>> extends AVLTree<T> {

	/**
	 * The number of LL cases
	 * @return
	 */
	public int LLcount();
	
	/**
	 * The number of LR cases
	 * @return
	 */
	public int LRcount();
	
	/**
	 * The number of RR cases
	 * @return
	 */
	public int RRcount();
	
	/**
	 * The number of RL cases
	 * @return
	 */
	public int RLcount();
	
	
	/**
	 * It fills this AVL tree with the elements from the array argument, while avoiding 
	 * any rotation operation.
	 * Any existing elements must be kept in the tree.
	 * @param array
	 */
	public void fillWithoutRebalance(T[] array);
}
