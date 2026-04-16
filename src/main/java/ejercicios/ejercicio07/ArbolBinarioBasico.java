package ejercicios.ejercicio07;

import java.util.ArrayList;

public class ArbolBinarioBasico<T extends Comparable<T>> {
	private static class Nodo<T> {
		private T dato;
		private Nodo<T> izq, dch;
		public Nodo (T dato) {
			this.dato = dato;
			this.izq = this.dch = null;
		}
	} 
	private Nodo<T> root = null; 
	private int size = 0;
	
	public int size() {
		return this.size;
	}
	
	@Override
	public String toString() {
		if (this.root == null) return "";
		String result = toString(this.root);
		return result.isEmpty() ? "" : "[" + result.substring(0, result.length()-2) + "]";
	}
	
	private String toString(Nodo<T> current) {
		String result = current.izq != null ? toString(current.izq) : "";
		result += current.dato.toString() + ", ";
		return result += current.dch != null ? toString(current.dch) : "";
	}
	
	public boolean add(T dato) {
		try {
			this.root = add(this.root, dato);
			this.size++;
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	private Nodo<T> add(Nodo<T> current, T dato){
		if (current == null) {
			return new Nodo<>(dato);
		}
		int cmp = dato.compareTo(current.dato);
		if (cmp == 0) throw new RuntimeException("Dato repetido");
		if (cmp < 0) {
			current.izq = add(current.izq, dato);
		}else {
			current.dch = add(current.dch, dato);
		}
		return current;
	}
	
	public String displayTree() {
		return this.root == null ? "" : displayTree(this.root, 0);
	}
	
	private String displayTree(Nodo<T> current, int level) {
		String result = current.dch != null ? displayTree(current.dch, level+1) : "";
		for (int i=0; i<level; i++) {
			result += "__";
		}
		result += current.dato.toString() + " (nivel " + level + ")\n";
		return result += current.izq != null ? displayTree(current.izq, level+1) : "";
	}
	
	public int getAltura() {
		return getAltura(this.root);
	}
	
	private int getAltura(Nodo<T> current) {
		if (current == null) return -1;
		return 1 + Math.max(getAltura(current.izq), getAltura(current.dch));
	}
	
	public ArrayList<T> toArray() {
		ArrayList<T> result = new ArrayList<>();
		if (this.root != null) toArray(this.root, result);
		return result;
	}
	
	private void toArray(Nodo<T> current, ArrayList<T> arr) {
		if(current.izq != null) toArray(current.izq,arr);
		arr.add(current.dato);
		if(current.dch != null) toArray(current.dch,arr);
	}
	
	public ArrayList<T> toArrayWithLevel(int level) {
		ArrayList<T> result = new ArrayList<>();
		if (this.root != null) toArrayWithLevel(this.root, result, level, 0);
		return result;
	}
	
	private void toArrayWithLevel(Nodo<T> current, ArrayList<T> arr, int level, int levelCurrent) {
		if(current.izq != null && levelCurrent < level) toArrayWithLevel(current.izq, arr, level, levelCurrent + 1);
		if(levelCurrent == level) arr.add(current.dato);
		if(current.dch != null && levelCurrent < level) toArrayWithLevel(current.dch, arr, level, levelCurrent + 1);
	}
	
	public int getNumHojas() {
		return getNumHojas(this.root);
	}
	
	private int getNumHojas(Nodo<T> current) {
		if(current == null) return 0;
		if(current.izq == null && current.dch == null) return 1;
		return getNumHojas(current.izq) + getNumHojas(current.dch);
	}
	
	public int getNumNodosInternos() {
		return getNumNodosInternos(this.root);
	}
	
	private int getNumNodosInternos(Nodo<T> current) {
		if(current == null) return 0;
		if(current.izq != null || current.dch != null) return 1 + getNumNodosInternos(current.izq) + getNumNodosInternos(current.dch);
		return getNumNodosInternos(current.izq) + getNumNodosInternos(current.dch);
	}
	
	public String getFactoresEquilibrio() {
		String result = this.root == null ? "" : getFactoresEquilibrio(this.root);
		return result.isEmpty() ? "" : "[" + result.substring(0, result.length()-2) + "]";
	}
	
	private String getFactoresEquilibrio(Nodo<T> current) {
		String result = current.izq != null ? getFactoresEquilibrio(current.izq) : "";
	    result += current.dato.toString() + "(" + (getAltura(current.izq) - getAltura(current.dch)) + "), ";
	    return result += current.dch != null ? getFactoresEquilibrio(current.dch) : "";
	}
	
	
	public static void checkArbolInteger() {
		ArbolBinarioBasico<Integer> arbol = new ArbolBinarioBasico<>();
		for (int i=1; i<=10; i++) {
			arbol.add((int)(Math.random()*1000));
		}
	
		System.out.println("Ejecución árbol de enteros");
		System.out.println("**************************");
		System.out.println("Secuencia ordenada de elementos contenidos en el árbol (toString()): " + arbol.toString());
		System.out.println("Secuencia ordenada de elementos contenidos en el árbol (toArray()): " + arbol.toArray());
		System.out.println("Número de elementos contenidos en el árbol = " + arbol.toArray().size());
		System.out.println("Altura del árbol = " + arbol.getAltura());
		System.out.println("Número de hojas = " + arbol.getNumHojas());
		System.out.println("Número de nodos internos = " + arbol.getNumNodosInternos());
		
		System.out.println("Distribución topológica del árbol: \n" + arbol.displayTree());		

		if (!arbol.toArray().toString().equals(arbol.toString())) {
			throw new RuntimeException("Método toArray() no correcto");
		}
		
		ArrayList<Integer> arr = new ArrayList<>();
		for (int i = 0; i <= arbol.getAltura(); i++) {
			ArrayList<Integer> arrAux = new ArrayList<>(arbol.toArrayWithLevel(i));
			System.out.println("Nodos en nivel " + i + ": " + arrAux);
			arr.addAll(arrAux);
		}
		
		arr.sort(null);
		if (!arr.toString().equals(arbol.toString())) {
			throw new RuntimeException("Método toArrayWithLevel() no correcto");
		}
		
		if (!(arbol.getNumHojas() + arbol.getNumNodosInternos() == arbol.size())) {
			throw new RuntimeException("Métodos getNumHojas() y getNumNodosInternos() no correctos");
		}
	}
	
	public static void checkArbolString() {
		ArbolBinarioBasico<String> arbol = new ArbolBinarioBasico<>();
		arbol.add("enero");
		arbol.add("febrero");
		arbol.add("marzo");
		arbol.add("abril");
		arbol.add("mayo");
		arbol.add("junio");
		arbol.add("julio");
		arbol.add("agosto");
		arbol.add("septiembre");
		arbol.add("octubre");
		arbol.add("noviembre");
		arbol.add("diciembre");

		System.out.println("\nEjecución árbol de cadenas de caracteres");
		System.out.println("****************************************");
		System.out.println("Secuencia ordenada de elementos contenidos en el árbol (toString()): " + arbol.toString());
		System.out.println("Secuencia ordenada de elementos contenidos en el árbol (toArray()): " + arbol.toArray());
		System.out.println("Número de elementos contenidos en el árbol = " + arbol.toArray().size());
		System.out.println("Altura del árbol = " + arbol.getAltura());
		System.out.println("Número de hojas = " + arbol.getNumHojas());
		System.out.println("Número de nodos internos = " + arbol.getNumNodosInternos());
		System.out.println("Distribución topológica del árbol: \n" + arbol.displayTree());

		if (!arbol.toArray().toString().equals(arbol.toString())) {
			throw new RuntimeException("Método toArray() no correcto");
		}
		
		ArrayList<String> arr = new ArrayList<>();
		for (int i = 0; i <= arbol.getAltura(); i++) {
			ArrayList<String> arrAux = new ArrayList<>(arbol.toArrayWithLevel(i));
			System.out.println("Nodos en nivel " + i + ": " + arrAux);
			arr.addAll(arrAux);
		}
		if (!arr.toString().equals("[enero, abril, febrero, agosto, marzo, diciembre, junio, mayo, julio, septiembre, octubre, noviembre]")) {
			throw new RuntimeException("Método toArrayWithLevel() no correcto");
		}
		
		if (!(arbol.getNumHojas() + arbol.getNumNodosInternos() == arbol.size())) {
			throw new RuntimeException("Métodos getNumHojas() y getNumNodosInternos() no correctos");
		}
		
		String salidaEsperada = "[abril(-2), agosto(-1), diciembre(0), enero(-3), febrero(-5), julio(0), junio(1), marzo(-2), mayo(-3), noviembre(0), octubre(1), septiembre(2)]";
		if (!arbol.getFactoresEquilibrio().equals(salidaEsperada)) {
			throw new RuntimeException("Método getFactoresEquilibrio no correcto");
		}
	}

	public static void main(String[] args) {
		try{
			checkArbolInteger();
			checkArbolString();
			System.out.println("¡Todo OK!!!");
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
