package practicas.practica02.parte01;

import java.util.Iterator;
import java.util.LinkedList;

public class Dispositivo implements Comparable<Dispositivo>, Iterable<PalabraFrecuencia>{
	private String modelo;
	protected LinkedList<PalabraFrecuencia> registroPalabras; 

	public Dispositivo(String modelo) {
		this.modelo = modelo.isBlank() ? "noName" : modelo.trim();
		this.registroPalabras = new LinkedList<PalabraFrecuencia>();
	}

	public Dispositivo(Dispositivo otro) {
		this.modelo = otro.modelo;
		this.registroPalabras = new LinkedList<PalabraFrecuencia>();
		for(PalabraFrecuencia palabraFrecuencia : otro.registroPalabras) {
			PalabraFrecuencia newPalabra = new PalabraFrecuencia(palabraFrecuencia.palabra);
			newPalabra.setFrecuencia(palabraFrecuencia.getFrecuencia());
			this.registroPalabras.add(newPalabra);
		}

	}

	public void clear() {
		this.registroPalabras.clear();
	}

	public void enviarMensaje(String mensaje) {
		for(String palabra : mensaje.split("[,. ]+")) {
			int pos = this.registroPalabras.indexOf(new PalabraFrecuencia(palabra));
			if(pos == -1) {
				this.registroPalabras.add(new PalabraFrecuencia(palabra));
			}
			else {
				this.registroPalabras.get(pos).incrementaFreq();
			}
		}
		this.registroPalabras.sort(null);
	}

	@Override
	public String toString() {
		String dispositivo = this.modelo + " -> ";
		return dispositivo += this.registroPalabras.isEmpty() ? "[empty]" : this.registroPalabras.toString();
	}

	@Override
	public boolean equals(Object otro) {
		if (otro == null) return false;
		if (this == otro) return true;
		if (!(otro instanceof Dispositivo)) return false;
		return this.compareTo((Dispositivo)otro) == 0;
	}

	@Override
	public int compareTo(Dispositivo otro) {
		return this.modelo.compareTo(otro.modelo);
	}

	@Override
	public Iterator<PalabraFrecuencia> iterator() {
		return this.registroPalabras.iterator();
	}
}
