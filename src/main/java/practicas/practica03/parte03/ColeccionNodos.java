package practicas.practica03.parte03;

import java.util.*;
import java.util.Map.Entry;

import practicas.auxiliar.Par;

public class ColeccionNodos <T extends Comparable<T>> implements Iterable<Par<Nodo<T>, Nodo<T>>>{

	protected final HashMap<Nodo<T>, HashSet<Nodo<T>>> data;

	public ColeccionNodos(){
		this.data = new HashMap<>();
	}

	public void add(Nodo<T> nodoOrigen, Nodo<T> nodoDestino) {
		Nodo<T> origen = new Nodo<>(nodoOrigen);
		Nodo<T> destino = new Nodo<>(nodoDestino);

		HashSet<Nodo<T>> vecinos = this.data.get(origen);
		if (vecinos == null) {
			vecinos = new HashSet<>();
			this.data.put(origen, vecinos);
		}
		vecinos.add(destino);
	}

	public int size() {
		return this.data.size();
	}

	public void clear(){
		this.data.clear();
	}

	private TreeMap<Nodo<T>, TreeSet<Nodo<T>>> toOrderedCollection() {
		TreeMap<Nodo<T>, TreeSet<Nodo<T>>> result = new TreeMap<>();
			for(Entry<Nodo<T>, HashSet<Nodo<T>>> nodos : this.data.entrySet()) {
				result.put(nodos.getKey(), new TreeSet<>(nodos.getValue()));
			}
			return result;
	}

	@Override
	public String toString() {
		String result = "";
		TreeMap<Nodo<T>, TreeSet<Nodo<T>>> ordered = this.toOrderedCollection();
		for (Entry<Nodo<T>, TreeSet<Nodo<T>>> entry : ordered.entrySet()) {
			result += entry.getKey().toString() + "\n";
			for (Nodo<T> vecino : entry.getValue()) {
				result += "\t" + vecino.toString() + "\n";
			}
		}
		return result;
	}

	@Override
	public Iterator<Par<Nodo<T>, Nodo<T>>> iterator() {
		ArrayList<Par<Nodo<T>, Nodo<T>>> pares = new ArrayList<>();
		TreeMap<Nodo<T>, TreeSet<Nodo<T>>> ordered = this.toOrderedCollection();
		for (Entry<Nodo<T>, TreeSet<Nodo<T>>> entry : ordered.entrySet()) {
			for (Nodo<T> destino : entry.getValue()) {
				pares.add(new Par<>(entry.getKey(), destino));
			}
		}
		return pares.iterator();
	}


}