package practicas.auxiliar;

import java.util.*;

/**
 * This class is a balanced binary tree that implements the Collection interface
 * AVL tree single and double rotation methods.
 */

public class AVLTree<T extends Comparable<T>> implements Iterable<T> {
	
	private static class AVLNode<T> {
		private T nodeValue;
		private AVLNode<T> left, right;
		private int height;

		public AVLNode(T item) {
			this.nodeValue = item;
			this.left = null;
			this.right = null;
			this.height = 0;
		}
	}
	
	private AVLNode<T> root;
	private int treeSize;
	private Comparator<T> comp;

	
	public AVLTree() {
		this.root = null;
		this.treeSize = 0;
		this.comp = null;
	}
	
	public AVLTree(Comparator<T> comp) {
		this.root = null;
		this.treeSize = 0;
		this.comp = comp;
	}
	
	private int compare(T a, T b) {
		return comp == null ? a.compareTo(b) : comp.compare(a, b);
 	}
	
	public AVLTree(T[] arr) {
		this();
		for (T item : arr) {
			this.add(item);
		}
	}
	
	public AVLTree(ArrayList<T> arr) {
		this();
		for (T item : arr) {
			this.add(item);
		}
	}

	private AVLNode<T> findNode(T item) {
		AVLNode<T> current = this.root;
		while (current != null) {
			int cmp = this.compare(item, current.nodeValue);
			if (cmp == 0) return current;
			current = (cmp < 0) ? current.left : current.right;
		}
		return null;
	}

	public boolean add(T item) {
		if (item == null) return false;
		try {
			this.root = addNode(this.root, item);
		} catch (Exception r) {
			return false;
		}
		this.treeSize++;
		return true;
	}

	private AVLNode<T> addNode(AVLNode<T> current, T item) {
		if (current == null) {
			return new AVLNode<>(item);
		}
		int cmp = this.compare(item, current.nodeValue);
		if (cmp < 0) {
			current.left = addNode(current.left, item);
			if (height(current.left) - height(current.right) == 2) {
				current = (this.compare(item, current.left.nodeValue) < 0) ? singleRotateRight(current) : doubleRotateRight(current);
			}
			current.height = Math.max(height(current.left), height(current.right)) + 1;
			return current;
		}
		if (cmp > 0) {
			current.right = addNode(current.right, item);
			if (height(current.left) - height(current.right) == -2) {
				current = (this.compare(item, current.right.nodeValue) > 0) ? singleRotateLeft(current) : doubleRotateLeft(current);
			}
			current.height = Math.max(height(current.left), height(current.right)) + 1;
			return current;
		}
		throw new RuntimeException("Elemento duplicado"); 
	}

	public boolean remove(T item) {
		if (item == null) return false;
		try {
			this.root = remove(this.root, item);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	private AVLNode<T> remove(AVLNode<T> current, T item) {

		if (current == null)  throw new IllegalArgumentException("Element " + item + " is not present.");
		int cmp = this.compare(item, current.nodeValue);

		if (cmp < 0) {
			current.left = remove(current.left, item);
			if (height(current.right) - height(current.left) == 2) {
				current = (height(current.right.right) >= height(current.right.left)) ? singleRotateLeft(current) : doubleRotateLeft(current);
			}
		} else if (cmp > 0) {
			current.right = remove(current.right, item);
			if (height(current.left) - height(current.right) == 2) {
				current = (height(current.left.left) >= height(current.left.right)) ? singleRotateRight(current) : doubleRotateRight(current);
			}
		} else {
			return removeNode(current);
		}
		current.height = Math.max(height(current.left), height(current.right)) + 1;
		return current;
	}

	private AVLNode<T> removeNode(AVLNode<T> removalNode) {
		AVLNode<T> replacementNode;
		if (removalNode.left != null && removalNode.right != null) {
			replacementNode = findMin(removalNode.right);
			removalNode.right = removeMin(removalNode.right);

			replacementNode.left = removalNode.left;
			replacementNode.right = removalNode.right;

			if (height(replacementNode.left) - height(replacementNode.right) == 2) {
				if (height(replacementNode.left.left) >= height(replacementNode.left.right)) {
					replacementNode = singleRotateRight(replacementNode);
				} else {
					replacementNode = doubleRotateRight(replacementNode);
				}
			}
			replacementNode.height = Math.max(height(replacementNode.left), height(replacementNode.right)) + 1;
		} else {
			replacementNode = (removalNode.left != null) ? removalNode.left : removalNode.right;
			treeSize--;
		}
		removalNode.left = null;
		removalNode.right = null;
		return replacementNode;
	}

	private AVLNode<T> removeMin(AVLNode<T> current) {
		if (current == null) return null;
		if (current.left == null) {
			treeSize--;
			return current.right;
		}
		current.left = removeMin(current.left);
		if (height(current.right) - height(current.left) == 2) {
			current = (height(current.right.right) >= height(current.right.left)) ? singleRotateLeft(current) : doubleRotateLeft(current);
		}
		current.height = Math.max(height(current.left), height(current.right)) + 1;
		return current;
	}

	private AVLNode<T> findMin(AVLNode<T> current) {
		if (current.left == null) return current;
		return findMin(current.left);
	}

	private static <T extends Comparable<T>> int height(AVLNode<T> current) {
		if (current == null) return -1;
		return current.height;
	}

	private static <T extends Comparable<T>> AVLNode<T> singleRotateRight(AVLNode<T> current) {
		AVLNode<T> lc = current.left;

		current.left = lc.right;
		lc.right = current;
		current.height = Math.max(height(current.left), height(current.right)) + 1;
		lc.height = Math.max(height(lc.left), lc.height) + 1;

		return lc;
	}

	private static <T extends Comparable<T>> AVLNode<T> singleRotateLeft(AVLNode<T> p) {
		AVLNode<T> rc = p.right;

		p.right = rc.left;
		rc.left = p;
		p.height = Math.max(height(p.left), height(p.right)) + 1;
		rc.height = Math.max(height(rc.right), rc.height) + 1;

		return rc;
	}

	private static <T extends Comparable<T>> AVLNode<T> doubleRotateRight(AVLNode<T> p) {
		p.left = singleRotateLeft(p.left);
		return singleRotateRight(p);
	}

	private static <T extends Comparable<T>> AVLNode<T> doubleRotateLeft(AVLNode<T> p) {
		p.right = singleRotateRight(p.right);
		return singleRotateLeft(p);
	}

	public void clear() {
		this.root = null;
		this.treeSize = 0;
	}

	
	public boolean contains(T item) {
		return findNode(item) != null;
	}

	public boolean isEmpty() {
		return this.treeSize == 0;
	}

	public int size() {
		return this.treeSize;
	}

	private void toArray(AVLNode<T> current, ArrayList<T> arr) {
		if (current == null) return;
		toArray(current.left, arr);
		arr.add(current.nodeValue);
		toArray(current.right, arr);
	}
	
	public ArrayList<T> toArray() {
		ArrayList<T> arr = new ArrayList<>(this.treeSize);
		toArray(this.root, arr);
		return arr;
	}
	
	public T find(T item) {
		AVLNode<T> t = findNode(item);
		return t == null ? null : t.nodeValue;
	}
	
	@Override
	public String toString() {
       return this.toArray().toString();
	}

	@Override
	public Iterator<T> iterator() {
		return this.toArray().iterator();
	}
	

	public static void main(String []args) {
		AVLTree<Integer> arbol = new AVLTree<>(Comparator.<Integer>reverseOrder());
		arbol.add(3);
		arbol.add(3);
		
		arbol.add(33);
		arbol.add(333);
		arbol.add(33333);
		arbol.add(333333);
		System.out.println(arbol);
		for (Integer item: arbol) {
			System.out.println(item);
		}		
		AVLTree<Integer> arbol2 = new AVLTree<>(arbol.toArray());
		System.out.println(arbol2);
		for (Integer item: arbol2) {
			System.out.println(item);
		}
		AVLTree<Integer> arbol3 = new AVLTree<>(Comparator.<Integer>reverseOrder());
		for (Integer item: arbol2) {
			arbol3.add(item);
		}
		System.out.println(arbol3);
		for (Integer item: arbol3) {
			System.out.println(item);
		}
	}
} 