package practicas.practica03.parte01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import practicas.auxiliar.AVLTree;

public class GestionBiblioteca implements Iterable<Entry<String, TreeMap<String,Integer>>>{
	private final String bibliotecaId; 
 	protected final AVLTree<Libro> libros;
	protected final AVLTree<Usuario> usuarios;
	protected final TreeMap<Libro, Usuario> prestamos;
	protected final TreeMap<Usuario, ArrayList<Libro>> historicoPrestamos;
	
	
	public GestionBiblioteca(String bibliotecaId) {
		this.bibliotecaId = bibliotecaId;
		this.libros = new AVLTree<>();
		this.usuarios = new AVLTree<>();
		this.prestamos = new TreeMap<>();
		this.historicoPrestamos = new TreeMap<>();
	}
	
	public void addLibro(String autorId, String libroId) {
		this.libros.add(new Libro(autorId,libroId));
	}
	
	public void addLibro(Libro libro) {
		this.libros.add(libro);
	}
	
	public void addUsuario(String usuarioId) {
		this.usuarios.add(new Usuario(usuarioId));
	}
	
	public void addUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
	}
	
	public void clear() {
		this.libros.clear();
		this.usuarios.clear();
		this.prestamos.clear();
		this.historicoPrestamos.clear();
	}
	
	public boolean prestarLibro(String usuarioId, String autorId, String libroId) {
		Usuario usuario = usuarios.find(new Usuario(usuarioId));
		Libro libro = libros.find(new Libro(autorId, libroId));
		if (usuario == null || libro == null) return false;
		return prestarLibro(usuario, libro);
	}

	private boolean prestarLibro(Usuario usuarioCurr, Libro libroCurr) {
		if(this.prestamos.get(libroCurr) != null) return false;
		this.prestamos.put(libroCurr, usuarioCurr);
		guardarHistorico(libroCurr, usuarioCurr);
		return true;
	}
	
	private void guardarHistorico(Libro libroCurr, Usuario usuarioCurr) {
		ArrayList <Libro> librosUsuario = this.historicoPrestamos.get(usuarioCurr);
		if(librosUsuario == null)
		{
			librosUsuario = new ArrayList<Libro>();
			this.historicoPrestamos.put(usuarioCurr, librosUsuario);
		}
		librosUsuario.add(libroCurr);
		}
 
	public boolean devolverLibro(String usuarioId, String autorId, String libroId) {
		if(this.usuarios.contains(new Usuario(usuarioId)) == false) return false;
		if(this.libros.contains(new Libro(autorId, libroId)) == false) return false;
		return devolverLibro(new Usuario(usuarioId), new Libro(autorId, libroId));
	}

	private boolean devolverLibro(Usuario usuarioCurr, Libro libroCurr) {
		Usuario usuarioPrestamo = this.prestamos.get(libroCurr);
		if(usuarioPrestamo == null) return false;
		if(!usuarioPrestamo.getUsuarioId().equals(usuarioCurr.getUsuarioId())) return false;
		this.prestamos.remove(libroCurr, usuarioPrestamo);
		return true;	
	}
	
	public String getPrestamosActuales(){
		TreeMap<String, ArrayList<String>> result = new TreeMap<>();
		for(Libro libroCurr : this.prestamos.keySet()) {
			Usuario usuario = this.prestamos.get(libroCurr);
			if (!result.containsKey(usuario.getUsuarioId())) {
				result.put(usuario.getUsuarioId(), new ArrayList<>());
			}
			result.get(usuario.getUsuarioId()).add(libroCurr.getAutorId() + " - " + libroCurr.getLibroId());
		}
		return result.toString();
	}

	public TreeMap<String, Integer> getUsuariosLibro(String autorId, String libroId){
		Libro libroCurr = libros.find(new Libro(autorId, libroId));
		return libroCurr == null ? null : getUsuariosLibro(libroCurr);
	}

	private TreeMap<String, Integer> getUsuariosLibro(Libro libroCurr){
		TreeMap<String, Integer> result = new TreeMap<>();
		for(Entry<Usuario, ArrayList<Libro>> UsuarioLibros : this.historicoPrestamos.entrySet()) {
			String usuarioId = UsuarioLibros.getKey().getUsuarioId();
			for(Libro libro : UsuarioLibros.getValue()) {
				if(!libro.equals(libroCurr)) continue;
				Integer contador = result.get(usuarioId);
				if (contador == null) contador = 0;
				
				result.put(usuarioId, contador + 1);
			}
		}
		return result;
	}
	
	public TreeSet<String> getLibrosUsuario(String usuarioId){
		Usuario usuarioCurr = usuarios.find(new Usuario(usuarioId));
		return usuarioCurr == null ? null : getLibrosUsuario(usuarioCurr);
	}
		
	private TreeSet<String> getLibrosUsuario(Usuario usuarioCurr){
		TreeSet<String> result = new TreeSet<>();
		if (!historicoPrestamos.containsKey(usuarioCurr)) return null;
	    for(Entry<Usuario, ArrayList<Libro>> UsuarioLibros : historicoPrestamos.entrySet()) {
	    	String usuarioId = UsuarioLibros.getKey().getUsuarioId();
	    	if(!usuarioId.equals(usuarioCurr.getUsuarioId())) continue;
	    	for(Libro libroCurr : UsuarioLibros.getValue()) {
	    		result.add(libroCurr.getAutorId() + " - " + libroCurr.getLibroId());
	    	}
	    }
		return result;
	}
	@Override
	public String toString() {
		return this.bibliotecaId + " (" + this.libros.size() + " libros y " + this.usuarios.size() + " usuarios" + ")";
	}

	@Override
	public Iterator<Entry<String, TreeMap<String,Integer>>> iterator() {
		TreeMap<String, TreeMap<String,Integer>> result = new TreeMap<>();	
		
		for(Usuario usuario : this.usuarios) {
			TreeMap<String, Integer> aux = new TreeMap<>();;
			for(Libro libro : this.libros) {
				aux.put(libro.getAutorId() + " - " + libro.getLibroId(), 0);
			}
			result.put(usuario.getUsuarioId(), aux);
		}
		
		for(Entry<Usuario, ArrayList<Libro>> UsuarioLibros : this.historicoPrestamos.entrySet()) {
			String idUsuario = UsuarioLibros.getKey().getUsuarioId();
			TreeMap<String, Integer> aux = result.get(idUsuario);
			for(Libro libroCurr : UsuarioLibros.getValue()) {
				String clave = libroCurr.getAutorId() + " - " + libroCurr.getLibroId();
				aux.put(clave, aux.get(clave) + 1);
			}
		}
		return result.entrySet().iterator();
	}
}