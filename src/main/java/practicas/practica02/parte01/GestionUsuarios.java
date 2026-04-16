package practicas.practica02.parte01;

import java.util.Iterator;

import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Format;

public class GestionUsuarios implements Iterable<Usuario> {
	private AVLTree<Usuario> usuarios;

	public GestionUsuarios() {
		this.usuarios = new AVLTree<>();
	}

	public void addDispositivos(Usuario usuario, Dispositivo... dispositivos) {
		Usuario user = this.usuarios.find(usuario);
		if(user == null) this.usuarios.add(usuario);
		usuario.addDispositivos(dispositivos);
	}

	public boolean enviarMensaje(String nombreUsuario, String nombreDispositivo, String mensaje) {
		Usuario user = this.usuarios.find(new Usuario(nombreUsuario));
		if(user == null) return false;
		user.enviarMensaje(nombreDispositivo, mensaje);
		return true;
	}

	public void clear() {
		for(Usuario usuario : this.usuarios) {
			usuario.clear();
		}
		this.usuarios.clear();
	}

	public String getGradoSimilitud(AVLTree<PalabraFrecuencia> conjunto01, AVLTree<PalabraFrecuencia> conjunto02) {
		double suma = .0;
		boolean disjunto = true;
		for (PalabraFrecuencia palFreq01: conjunto01) {
			PalabraFrecuencia palabraFrecCurr = conjunto02.find(palFreq01);
			if(palabraFrecCurr == null) continue;
			suma += Math.pow(palFreq01.getFrecuencia() - palabraFrecCurr.getFrecuencia(),2);
			disjunto = false;
		}
		return disjunto ? "-1.00" : Format.formatDouble(1.0 / (1 + Math.sqrt(suma))); 
	}

	public String getGradoSimilitud(String nombreUsuario) {
		String result = nombreUsuario + " vs...\n";
		Usuario usuarioCurr = this.usuarios.find(new Usuario(nombreUsuario));
		if (usuarioCurr == null) return null;
		for(Usuario usuario : this.usuarios) {
			if(usuario.equals(usuarioCurr)) continue;
			result += "\t" + usuario.getNick() + ": " + getGradoSimilitud(usuarioCurr.getPalabras(), usuario.getPalabras()).toString() + "\n";
		}
		return result;
	}

	@Override
	public String toString() {
		return this.usuarios.toString();
	}

	@Override
	public Iterator<Usuario> iterator() {
		return this.usuarios.iterator();
	}
}
