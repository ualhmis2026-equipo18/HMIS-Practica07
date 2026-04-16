package practicas.practica02.parte01;

import practicas.auxiliar.AVLTree;

public class Usuario implements Comparable<Usuario> {
	private String nick;
	private AVLTree<Dispositivo> dispositivos;

	public Usuario(String nick) {
		if (nick == null || nick.trim().isEmpty()) throw new RuntimeException("Nick inválido");
		this.nick = nick;
		this.dispositivos = new AVLTree<Dispositivo>();

	}

	public String getNick() {
		return this.nick;
	}

	public void clear() {
		for(Dispositivo dispositivo : this.dispositivos) {
			dispositivo.clear();
		}
		this.dispositivos.clear();
	}

	public void addDispositivos(Dispositivo... dispositivos) {
		for(Dispositivo dispositivo : dispositivos) {
			this.dispositivos.add(new Dispositivo(dispositivo));
		}
	}

	public int getNumDispositivos() {
		return this.dispositivos.size();
	}

	public boolean enviarMensaje(String nombreDispositivo, String mensaje) {
		Dispositivo dispositivo = this.dispositivos.find(new Dispositivo(nombreDispositivo));
		if(this.dispositivos.find(new Dispositivo(nombreDispositivo)) == null) return false;
		dispositivo.enviarMensaje(mensaje);
		return true;
	}

	public int getNumPalabras(String nombreDispositivo) {
		Dispositivo dispositivo = this.dispositivos.find(new Dispositivo(nombreDispositivo));
		if(dispositivo == null) return -1;
		int cont = 0;
		for(PalabraFrecuencia palabraFrec : dispositivo.registroPalabras) {
			cont += palabraFrec.getFrecuencia();
		}
		return cont;
	}

	public AVLTree<PalabraFrecuencia> getPalabras() {
		AVLTree<PalabraFrecuencia> result = new AVLTree<>();
		for(Dispositivo dispositivo : this.dispositivos) {
			for(PalabraFrecuencia palabraFrec : dispositivo) {
				PalabraFrecuencia aux = result.find(palabraFrec);
				if(aux == null) {
					result.add(new PalabraFrecuencia(palabraFrec));
				} else {
					aux.setFrecuencia(aux.getFrecuencia() + palabraFrec.getFrecuencia());
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		String result = this.nick + ": \n";
		for(Dispositivo dispositivo : this.dispositivos) {
			result += "\t" + dispositivo.toString() + "\n";
		}
		return result;
	}

	@Override
	public boolean equals(Object otro) {
		if (otro == null) return false;
		if (this == otro) return true;
		if (!(otro instanceof Usuario)) return false;
		return this.compareTo((Usuario)otro) == 0;
	}

	@Override
	public int compareTo(Usuario otro) {
		return this.nick.compareTo(otro.nick);
	}
}
