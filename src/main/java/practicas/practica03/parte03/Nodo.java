package practicas.practica03.parte03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Nodo<T extends Comparable<T>> implements Comparable<Nodo<T>> {

	private ArrayList<T> componentes;

	@SafeVarargs
	public Nodo(T... componentes) {
		this.componentes = new ArrayList<>();
		for(T componente : componentes) {
			if(this.componentes.contains(componente)) continue;
			this.componentes.add(componente);
		}
		Collections.sort(this.componentes);

	}

	public Nodo(Nodo<T> otro) {
		this.componentes = new ArrayList<>(otro.componentes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.componentes);
	}

	@Override
	public boolean equals(Object otro) { 
		if (otro == null) return false;
		if (this == otro) return true; 
		if (!(otro instanceof Nodo<?>))return false;
		return this.componentes.equals(((Nodo<?>)otro).componentes);
	}

	@Override
	public int compareTo(Nodo<T> otro) {
		int min = Math.min(this.componentes.size(), otro.componentes.size());
		for (int i = 0; i < min; i++) {
			int cmp = this.componentes.get(i).compareTo(otro.componentes.get(i));
			if (cmp != 0) return cmp;
		}
		return Integer.compare(this.componentes.size(), otro.componentes.size());
	}

	@Override
	public String toString() {
		return this.componentes.isEmpty() ? "[empty]" : this.componentes.toString();
	}

	public void clear() {
		this.componentes.clear();
	}
}