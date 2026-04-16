/*
 * @(#)BSTree.java
 */

package practicas.auxiliar;

import java.util.*;

/**
 * This class implements the Collection interface using a binary search tree as
 * the underlying storage structure.
 */

public class BSTree<T extends Comparable<T>> implements Iterable<T> {
	private static class BSTNode<T> {
		private T nodeValue;
		private BSTNode<T> left;
		private BSTNode<T> right;

		public BSTNode(T item) {
			this.nodeValue = item;
			this.left = null;
			this.right = null;
		}
	}

	private BSTNode<T> root;
	private int treeSize;
	private Comparator<T> comp;

	public BSTree() {
		this.root = null;
		this.comp = null;
		this.treeSize = 0;
	}

	public BSTree(Comparator<T> comp) {
		this.root = null;
		this.comp = comp;
		this.treeSize = 0;
	}
	
	public BSTree(ArrayList<T> arr) {
		this.root = null;
		this.comp = null;
		this.treeSize = 0;
		for (T item: arr) {
			this.add(item);
		}
	}
	
	private int compare(T a, T b) {
		return this.comp == null ? a.compareTo(b) : this.comp.compare(a, b);
	}

	private BSTNode<T> findNode(T item) {
		BSTNode<T> current = this.root;
		while (current != null) {
			int cmp = this.compare(item, current.nodeValue);
			if (cmp == 0) break;
			current = (cmp < 0) ? current.left : current.right;
		}
		return current;
	}

	public String toStringBreadthFirstTraversal() {
		Deque<BSTNode<T>> queue = new ArrayDeque<>();
		BSTNode<T> current = this.root;
		String result = "";
		if (current == null) return " ";
		queue.offer(current);
		while (!queue.isEmpty()) {
			current = queue.poll();
			result += current.nodeValue + "  ";
			if (current.left != null)
				queue.offer(current.left);
			if (current.right != null)
				queue.offer(current.right);
		}
		return result;
	}

	public String toStringIterativePreorder() {
		Deque<BSTNode<T>> stack = new ArrayDeque<>();
		BSTNode<T> current;
		String result = "";
		if (root == null) return " ";
		stack.push(root);
		while (!stack.isEmpty()) {
			current = stack.pop();
			result += current.nodeValue + "  ";
			if (current.right != null)
				stack.push(current.right);
			if (current.left != null)
				stack.push(current.left);
		}
		return result;
	}

	public boolean add(T item) {
		try {
			this.root = this.add(item, this.root);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public BSTNode<T> add(T item, BSTNode<T> current) {
		if (current == null) {
			return new BSTNode<>(item);
		}
		int cmp = this.compare(item, current.nodeValue);
		if (cmp == 0) throw new RuntimeException("Elemento repetido");
		if (cmp < 0) {
			current.left = this.add(item, current.left);
		}else {
			current.right = this.add(item,  current.right);
		}
		return current;
		
	}

	public void clear() {
		this.treeSize = 0;
		this.root = null;
	}

	public boolean isEmpty() {
		return this.treeSize == 0;
	}

	public Iterator<T> iterator() {
		return this.toArray().iterator();
	}

	public int size() {
		return this.treeSize;
	}

	private void toArray(BSTNode<T> current, ArrayList<T> arr) {
		if (current == null) return;
		toArray(current.left, arr);
		arr.add(current.nodeValue);
		toArray(current.right, arr);
	}
	
	public ArrayList<T> toArray() {
		ArrayList<T> arr = new ArrayList<>();
		toArray(this.root, arr);
		return arr;
	}

	@Override
	public String toString() {
		return this.toArray().toString();
	}

	public T find(T item) {
		BSTNode<T> t = findNode(item);
		return t != null ? t.nodeValue : null;
	}

	private String preorderDisplay(BSTNode<T> current) {
		String result = "";
		if (current == null) return "";
		result += current.nodeValue + "  ";
		result += preorderDisplay(current.left);
		result += preorderDisplay(current.right);
		return result;
	}

	public String preorderDisplay() {
		return preorderDisplay(root);
	}

	public boolean contains(T o) {
		return this.find(o) != null;
	}
	
	public static void main(String[] args) {
		BSTree<Integer> arbol = new BSTree<>(Comparator.<Integer>reverseOrder());
		arbol.add(3);
		arbol.add(3);
		arbol.add(33);
		arbol.add(333);
		arbol.add(3333);
		arbol.add(33333);
		arbol.add(333333);
		System.out.println(arbol);
		
		BSTree<Integer> arbol2 = new BSTree<>(arbol.toArray());
		System.out.println(arbol2);
	}
}