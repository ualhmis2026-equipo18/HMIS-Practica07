package practicas.practica03.parte01;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Libro implements Comparable<Libro>, Iterable<Entry<String, Integer>>{
	private String autorId;
	private String libroId;
	private final TreeMap<String, Integer> palFreq;
	
	public Libro(String fileName) {
		Scanner scan;
		String linea;
		try { 
			scan = new Scanner(new File(fileName));
		}catch (IOException e) {
			throw new RuntimeException("Archivo no encontrado");
		}
		this.autorId = scan.nextLine().trim();
		this.libroId = scan.nextLine().trim();
		this.palFreq = new TreeMap<String, Integer>();
		
		while (scan.hasNextLine()){
			linea = scan.nextLine().toLowerCase();
			if(linea.isBlank()) continue;
			for(String palabra : linea.split("[.,:; ]+")) {
				add(palabra);
			}
		}
		scan.close();
	}
	
	public Libro(String autorId, String libroId) {
		this.autorId = autorId.trim();
		this.libroId = libroId.trim();
		this.palFreq = new TreeMap<String, Integer>();
	}
	 
	public void clear() {
		this.palFreq.clear();
	}
	 
	public void add(String...palabras) {
		for (String palabra: palabras) {
			Integer freq = this.palFreq.get(palabra);
	        if (freq == null) freq = 0;
	        this.palFreq.put(palabra, freq + 1);
			
		}
	}
	
	public String getAutorIdLibroId() {
		return this.autorId + " - " + this.libroId;
	}
	
	public String getAutorId() {
		return this.autorId;
	}
	
	public String getLibroId() {
		return this.libroId;
	}
	
	public int getNumPalabras() {
		int suma = 0;
		for(String pal : this.palFreq.keySet()) {
			suma += this.palFreq.get(pal);
		}
		return suma;
	}
	
	public TreeSet<String> getPalabras(){
		TreeSet<String> palabras = new TreeSet<String>();
		for(String palabra : this.palFreq.keySet()) {
			palabras.add(palabra);
		}
		return palabras;
	}
	
	@Override
	public String toString() {
		int distintas = this.palFreq.size();
		return this.libroId + " (" + this.autorId + ") -> [" + distintas + (distintas != 1 ? " palabras distintas]" : " palabra distinta]");
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Libro)) return false;
		if (this == o) return true;
		return this.compareTo((Libro)o) == 0;
	}
	
	@Override
	public int compareTo(Libro o) {
		int cmp = this.autorId.compareTo(o.autorId);
		return cmp == 0 ? this.libroId.compareTo(o.libroId) : cmp;
	}
	
	@Override
	public Iterator<Entry<String, Integer>> iterator() {
		return this.palFreq.entrySet().iterator();
	}
}