package practicas.practica02.parte03;

import java.util.ArrayList;
import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Par;


public class GestionUsuarios {
	private AVLTree<Par<String, AVLTree<Par<String, ArrayList<String>>>>> usuarios;

	public GestionUsuarios() {
		this.usuarios = new AVLTree<>();
	}

	public void addDispositivos(String usuario, String... dispositivos) {
		Par<String, AVLTree<Par<String, ArrayList<String>>>> usuarioCurr = this.usuarios.find(new Par<>(usuario, new AVLTree<>()));
		if(usuarioCurr == null) {
			usuarioCurr = new Par<>(usuario, new AVLTree<>());
			this.usuarios.add(usuarioCurr);
		}
		for(String dispositivo : dispositivos) {
			Par<String, ArrayList<String>> dispositivoCurr = usuarioCurr.getValue().find(new Par<>(dispositivo, new ArrayList<>()));
			if(dispositivoCurr != null) continue;
			usuarioCurr.getValue().add(new Par<>(dispositivo, new ArrayList<>()));
		}
	}

	public boolean enviarMensaje(String usuario, String dispositivo, String mensaje) {
		Par<String, AVLTree<Par<String, ArrayList<String>>>> usuarioCurr = this.usuarios.find(new Par<>(usuario, new AVLTree<>()));
		if(usuarioCurr == null) return false;
		Par<String, ArrayList<String>> dispositivoCurr = usuarioCurr.getValue().find(new Par<>(dispositivo, null));
		if(dispositivoCurr == null) return false;
		for(String palabra : mensaje.split("[,\\.\\s]+")) {
			dispositivoCurr.getValue().add(palabra);
		}
		return true;
	}

	public ArrayList<Par<String, Integer>> getPalabras(String usuario) {
		ArrayList<Par<String, Integer>> result = new ArrayList<>();
		Par<String, AVLTree<Par<String, ArrayList<String>>>> usuarioCurr = this.usuarios.find(new Par<>(usuario, new AVLTree<>()));
		if(usuarioCurr == null) return null;
		for(Par<String, ArrayList<String>> dispositivoCurr : usuarioCurr.getValue()) {
			for(String palabraCurr : dispositivoCurr.getValue()) {
				int index = result.indexOf(new Par<String, Integer>(palabraCurr, null));
				if(index == -1) {
					result.add(new Par<String, Integer>(palabraCurr, 1));
				} else {
					result.set(index, new Par<String, Integer>(palabraCurr, result.get(index).getValue() + 1));
				}
			}
		}
		return result;
	}

	public AVLTree<Par<String, ArrayList<String>>> getDispositivos(String usuario) {
		Par<String, AVLTree<Par<String, ArrayList<String>>>> usuarioCurr = this.usuarios.find(new Par<>(usuario, new AVLTree<>()));
		return usuarioCurr == null ? null : usuarioCurr.getValue();
	}

	public void clear() {
		this.usuarios.clear();
	}

	@Override
	public String toString() {
		String result = "";
		for(Par<String, AVLTree<Par<String, ArrayList<String>>>> usuarioCurr : this.usuarios) {
			result += usuarioCurr.getKey() + ":\n";
			for(Par<String, ArrayList<String>> dispositivoCurr : usuarioCurr.getValue()) {
				result += "\t" + dispositivoCurr.getKey() + " -> " + dispositivoCurr.getValue().toString() +"\n";
			}
		}
		return result;
	}
}