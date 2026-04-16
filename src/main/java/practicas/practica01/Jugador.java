package practicas.practica01;

import java.util.Iterator;
import java.util.LinkedList;

public class Jugador implements Iterable<Carta> {
	private String id;
	private LinkedList<Carta> mano = new LinkedList<>();
	private boolean ocupado;
	
	public Jugador(String id) {
		this.id = id.trim();
		this.mano = new LinkedList<Carta>();
	} 
	
	public boolean getOcupado() {
		return this.ocupado;
	}  
	
	public void setOcupado(boolean jugando) {
		this.ocupado = jugando;
	}
	
	public String getId() {
		return this.id;
	}
	
	public int size() {
		return this.mano.size();
	}
	
	public void clear() {
		this.mano.clear();
	}
	
	public boolean cogerCarta(Carta carta) {
		if(carta.getRepartida() == true || this.mano.contains(carta)) return false;
		carta.setRepartida(true);
		this.mano.add(carta);
		return true;
		
	}
	
	public boolean devolverCarta(Carta carta) {
		if(carta.getRepartida() == false || !this.mano.contains(carta)) return false;
		carta.setRepartida(false);
		this.mano.remove(carta);
		return true;
	}
	
	@Override
	public String toString() {
		return this.id + " -> " + this.mano.toString();
	}

	@Override
	public Iterator<Carta> iterator() {
		return this.mano.iterator();
	}
}