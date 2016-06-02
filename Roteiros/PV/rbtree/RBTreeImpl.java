package adt.rbtree;

import java.util.ArrayList;
import java.util.List;

import adt.avltree.AVLTreeImpl;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	
	/** esse daqui foi por minha conta! CAIU NA PROVA 3 DE EDA.
	 * 
	 */
	public int contaVermelhos(){
		int a = contaVermelhos2((RBNode<T>) this.root.getLeft());
		int b = contaVermelhos2((RBNode<T>) this.root.getRight());
		return a+b;
	}
	
	private int contaVermelhos2(RBNode<T> node){
		if (node.isEmpty()) {
			return 0;
		}	
		
		if (node.getColour().equals(Colour.RED)) {
			return (int) node.getData() + contaVermelhos2((RBNode<T>) node.getLeft()) + contaVermelhos2((RBNode<T>) node.getRight());
		}
		
		else if (node.getColour().equals(Colour.BLACK)) {
			return contaVermelhos2((RBNode<T>) node.getLeft()) + contaVermelhos2((RBNode<T>) node.getRight());
		}
		
		return 0;
	}
	
	
	
	protected int blackHeight() {

		int countBlack = 0;

		RBNode<T> aux = (RBNode<T>) this.root.getLeft();

		while (!aux.isEmpty()) {
			if (aux.getColour() == Colour.BLACK) {
				countBlack++;
			}else {
				aux = (RBNode<T>) aux.getLeft();
			}
		}
		return countBlack + 1; // conta os NILS
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		boolean res = verifyChildrenOfRedNodes((RBNode<T>) this.root);
		return res;
 	}
	
	private boolean verifyChildrenOfRedNodes(RBNode<T> node){
		
		if (!node.isEmpty()) {
			RBNode<T> sonLeft = (RBNode<T>) node.getLeft();
			RBNode<T> sonRight = (RBNode<T>) node.getRight();
			if (node.getColour() == Colour.RED) {
				if (sonLeft.getColour() == Colour.RED || sonRight.getColour() == Colour.RED) {
					return false;
				}
			}
			return verifyChildrenOfRedNodes(sonLeft) && verifyChildrenOfRedNodes(sonRight);
		}
		
		return true;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		int a = (blackHeight((RBNode<T>) this.root.getLeft())); //fiz assim para um melhor debug
		int b = blackHeight((RBNode<T>) this.root.getRight());
		return a == b;
	}
	
	private int blackHeight(RBNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		} else {
			if (node.getColour().equals(Colour.BLACK)){
				int a = blackHeight((RBNode<T>) node.getLeft()); //fiz assim para um melhor debug
				int b = blackHeight((RBNode<T>) node.getRight());
				return 1 + Math.max(a,b);
			}
			else {
				int c = blackHeight((RBNode<T>) node.getLeft()); //fiz assim para um melhor debug
				int d = blackHeight((RBNode<T>) node.getRight());
				return Math.max(c,d);
			}
		}
	}

	@Override
	public void insert(T value) {
		if (value != null) {
			RBNode<T> NIL = new RBNode<T>(); //como a raiz nao tem parent, passei um NIL.
			insert(NIL, (RBNode<T>) this.root, value);
		}
	}
	
	private void insert(RBNode<T> parent, RBNode<T> node, T value) {
		if (node.isEmpty()) {
			node.setData(value);
			node.setLeft(new RBNode<T>());
			node.setRight(new RBNode<T>());
			node.setParent(parent);
			node.setColour(Colour.RED);
			fixUpCase1(node);

		} else {
			if ((node.getData().compareTo(value)) < 0) {
				insert(node, (RBNode<T>) node.getRight(), value);
			} else {
				insert(node, (RBNode<T>) node.getLeft(), value);
			}
		}
	}

	@Override
	public RBNode<T>[] extendedPreOrder() {
		List<RBNode<T>> aux  = new ArrayList<RBNode<T>>();
		extendedPreOrder(aux, (RBNode<T>) this.root);
		RBNode<T>[] arrayRBTree = new RBNode[aux.size()];
		return aux.toArray(arrayRBTree);
	}
	
	private void extendedPreOrder(List<RBNode<T>> aux, RBNode<T> node){
		if (!node.isEmpty()) {
			aux.add(node); //RAIZ
			extendedPreOrder(aux, (RBNode<T>) node.getLeft()); //ESQUERD
			extendedPreOrder(aux, (RBNode<T>) node.getRight()); // DIREITA
		}
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		
		// SE FOR RAIZ - PINTA DE PRETO
		
		if (node == (RBNode<T>) this.root) {
			node.setColour(Colour.BLACK);
		}else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		RBNode<T> father = (RBNode<T>) node.getParent(); //melhor pro debug.
		
		//SE O PAI NÃO FOR PRETO CHAMA O CASO 2
		if (((RBNode<T>) father).getColour() != Colour.BLACK){
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
			
		RBNode<T> father = (RBNode<T>) node.getParent();
		RBNode<T> gradma = (RBNode<T>) node.getParent().getParent();
		
		// se o tio eh vermelho?
		if ( (gradma.getRight() != father) && (( (RBNode<T>) gradma.getRight() ).getColour() == Colour.RED)) {
			
			//pinta o pai de preto
			father.setColour(Colour.BLACK);
			
			//pinta o tio de preto
			((RBNode<T>)gradma.getRight()).setColour(Colour.BLACK);
			
			//o avo de vermelho
			gradma.setColour(Colour.RED);
			
			// e chega o avo a partir do caso 1.
			fixUpCase1(gradma);
		
		}else if (gradma.getLeft() != father && (( (RBNode<T>) gradma.getLeft() ).getColour() == Colour.RED)) {
			
			//pinta o pai de preto
			father.setColour(Colour.BLACK);
			
			//pinta o tio de preto
			((RBNode<T>)gradma.getLeft()).setColour(Colour.BLACK);
			
			//o avo de vermelho
			gradma.setColour(Colour.RED);
			
			// e chega o avo a partir do caso 1.
			fixUpCase1(gradma);
		
		}else {
			fixUpCase4(node);
		}
		
	}

	protected void fixUpCase4(RBNode<T> node) {
		
		//O tio eh preto e se esta em zig_zag?
		
		if ( (node.getParent().getRight() == node) && (node.getParent().getParent().getLeft() == node.getParent()) ) {
			
			//aplica a rotacao adequada (para a esquerda no pai)
			leftRotation((RBNode<T>) node.getParent());
			
			//e chama o caso 5 pro antigo pai de node.
			fixUpCase5((RBNode<T>) node.getLeft());
		
		}else if ( (node.getParent().getLeft() == node) && (node.getParent().getParent().getRight() == node.getParent()) ) {
			
			//aplica a rotacao adequada (para a direita no pai)
			rightRotation((RBNode<T>) node.getParent());
			
			//e chama o caso 5 pro antigo pai de node.
			fixUpCase5((RBNode<T>) node.getRight());
		
		}else {
			fixUpCase5(node);
		}
	}

	protected void fixUpCase5(RBNode<T> node) {
		//pinta o pai de preto
		((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
		
		//o avo de vermelho
		((RBNode<T>) node.getParent().getParent()).setColour(Colour.RED);
		
		
		//se pai e filho(node) forem filhos do lado esquerdo
		if ( (node.getParent().getLeft() == node) && (node.getParent().getParent().getLeft() == node.getParent()) ) {
			
			//aplica a rotacao ADEQUEDA no avo
			rightRotation((RBNode<T>) node.getParent().getParent()); 
		
		//se pai e filho(node) forem filhos do lado direito
		}else if ( (node.getParent().getRight() == node) && (node.getParent().getParent().getRight() == node.getParent()) ) {
			//aplica a rotacao ADEQUEDA no avo
			leftRotation((RBNode<T>) node.getParent().getParent()); 
		} 
	}
}