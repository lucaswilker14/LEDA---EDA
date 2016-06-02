package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;
	
	protected int height;
	protected int maxHeight;

	//SET THIS VALUE TO TRUE IF YOU ARE IMPLEMENTING MAX_LEVEL = LEVEL
	//SET THIS VALUE TO FALSE IF YOU ARE IMPLEMENTING MAX_LEVEL != LEVEL
	
	protected boolean USE_MAX_HEIGHT_AS_HEIGHT = false;
	protected double PROBABILITY = 0.5; 
	
	
	public SkipListImpl(int maxHeight) {
		if(USE_MAX_HEIGHT_AS_HEIGHT){
			this.height = maxHeight;
		}else{
			this.height = 1;
		}
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}
	
	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL
	 * Caso esteja-s// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");e usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com 
	 * level=1 e o metodo deve conectar apenas o forward[0].  
	 */
	
	private void connectRootToNil(){
		this.root.forward[0] = NIL;
	}
	
	/**
	 * Metodo que gera uma altura aleatoria para ser atribuida a um novo no no metodo
	 * insert(int,V) 
	 */
	private int randomLevel(){
		int randomLevel = 1;
		double random = Math.random();
		while(Math.random() <= PROBABILITY && randomLevel < maxHeight){
			randomLevel = randomLevel + 1;
		}
		return randomLevel;
	}
	
	@Override
	public void insert(int key, T newValue) {
		int heightNode = randomLevel();
		insert(key, newValue,heightNode);
	}

	@Override
	public void insert(int key, T newValue, int height) {
		SkipListNode[] update = new SkipListNode [height];
		
		SkipListNode<T> x = root;
		
		for (int i = height - 1; i >= 0 ; i--) {
			while(x.getForward(i) != null && x.getForward(i).key < key){
				x = x.getForward(i);
			}
		update[i] = x;
		}
		x = x.getForward(0);
		
		if(x.key == key){
			x.value = newValue;
		}else{
			
			x = new SkipListNode<T>(key, height, newValue);
			
			if(height > this.height){
				this.height = height;
			}
			
			for (int i = 0; i < update.length; i++) {
				if(update[i].getForward(i) == null){
					x.forward[i] = this.NIL;
				}
				else{
					x.getForward()[i] = update[i].forward[i];
				}
				update[i].forward[i] = x;
				
			}
		}
	}

	@Override
	public void remove(int key) {
			if(key != root.key && key != NIL.key) {
			
			SkipListNode<T> aux = root;
			
			for(int i = this.height-1; i >= 0; i--){
				while(aux.forward[i].key < key)
					aux = aux.forward[i];
				if(aux.forward[i].key == key)
					aux.forward[i] = aux.forward[i].forward[i];
			}
			
			while (height > 1 && root.forward[height-1] == NIL) {
				root.forward[--height] = null;
			}
		
		}
	}

	@Override
	public int height() {
		SkipListNode<T> aux = root;
		int altura = this.height;
		while(aux.getForward(altura) == null){
			altura--;
		}
		return altura;
	
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> x = root;
		
		for (int i = height - 1; i >= 0 ; i--) {
			while(x.getForward(i) != null && x.getForward(i).key < key){
				x = x.getForward(i);
			}
		}
		
		SkipListNode<T> result = null;
		if(x.forward[0].key == key)
			result = x.forward[0];
		else if (key == Integer.MIN_VALUE)
			result = root;
		return result;
	}

	@Override
	public int size(){
		int conta = 0;
		SkipListNode<T> aux = root.getForward(0);
		while(aux != NIL){
			aux = aux.getForward(0);
			conta ++;
		}
		
		return conta ;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		
		SkipListNode <T>[] array = new SkipListNode[size() + 2];
		
		SkipListNode<T> aux = root;
		int i = 0;
		while(aux != NIL.getForward(0)){
			array[i] = aux;
			i ++;
			aux = aux.getForward(0);
		}
		return array;
	}

}

