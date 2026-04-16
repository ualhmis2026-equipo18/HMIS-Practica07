package practicas.practica01;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import practicas.practica01.Configuracion.TIPO_BARAJA;


public class Partida implements Iterable<Jugador> {
	private TIPO_BARAJA tipo;
	private LinkedList<Jugador> jugadores;
	private Baraja baraja;

	public Partida(String tipo, Jugador...jugadores) {
		try {
			this.tipo = TIPO_BARAJA.valueOf(tipo.trim());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Tipo no válido");
		}

		this.baraja= new Baraja(this.tipo.toString());
		this.jugadores = new LinkedList<Jugador>();

		for(Jugador jugador : jugadores) {
			if(jugador.getOcupado() == true) throw new RuntimeException("El jugador " + jugador.getId() + " ya pertenece a una partida");
			jugador.setOcupado(true);
			this.jugadores.add(jugador);
		}
	}

	public int size() {
		return this.jugadores.size();
	}

	public void clear() {
		this.baraja.clear();
		this.jugadores.clear();
	}

	public boolean init(int numCartas) {
		if(this.baraja.size() == 0 || numCartas <= 0 || numCartas * this.jugadores.size() > this.baraja.size()) return false;

		for(Jugador jugador : this.jugadores) {
			if(jugador.size() != 0) return false;
		}

		Carta cartaRandom;
		Random rnd = new Random();

		int nCortes = rnd.nextInt(10) + 1;
		for(int i = 0; i < nCortes; i++) {
			this.baraja.barajar();
			int puntoCorte = rnd.nextInt(this.baraja.size() - 1) + 1;
			this.baraja.cortarBaraja(puntoCorte);
		}

		for(Jugador jugador : this.jugadores) {
			for(int i = 0; i < numCartas; i++) {	
				do {
					int indiceRnd = rnd.nextInt(this.baraja.size());
					cartaRandom = this.baraja.getCarta(indiceRnd);
				} while (cartaRandom.getRepartida() == true);
				jugador.cogerCarta(cartaRandom);
			}
		}
		return true;
	}

	public int getNumCartas() {
		return this.baraja.size();
	}

	public int getNumCartasRepartidas() {
		int result = 0;
		for(Jugador jugador : this.jugadores) {
			result += jugador.size();
		}

		return result;
	}

	public Jugador getManoGanadora() {
		Carta cartaMayor = new Carta(this.tipo.toString(), this.tipo.getValores().get(0),this.tipo.getPalos().get(0));
		Jugador result = null;
		for(Jugador jugador : this.jugadores) {
			for(Carta carta : jugador) { 
				if(cartaMayor.compareTo(carta) < 0) {
					result = jugador;
					cartaMayor = carta;
				}
			}
		}
		return result;
	}

	public void end() {
		for(Jugador jugador : this.jugadores) {
			for(Carta carta : jugador) {
				carta.setRepartida(false);
			}
			jugador.clear();
		}
	}

	@Override
	public String toString() {
		return "Partida con baraja " + this.tipo + ": " + this.jugadores.toString();
	}

	@Override
	public Iterator<Jugador> iterator() {
		return this.jugadores.iterator();
	}
}