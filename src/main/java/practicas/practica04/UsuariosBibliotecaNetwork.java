package practicas.practica04;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.Scanner;

import practicas.auxiliar.Format;
import practicas.practica03.parte01.Usuario;

public class UsuariosBibliotecaNetwork extends Network<Usuario>{
	private TreeMap<Usuario, TreeMap<String, TreeSet<Usuario>>> datos = new TreeMap<>();
	
	@Override
	public String toString() {
		return this.datos.toString();
	}
	
	@Override
	public void clear() {
		super.clear();
		this.datos.clear();
	}
	
	public boolean load(String fileName) {
		Scanner scan = null;
		this.adjacencyList.clear();
		try{
			scan = new Scanner(new File(fileName));
		}catch(Exception e){
			return false;
		}
		TreeMap<Usuario, ArrayList<Double>> fileLoaded = new TreeMap<>();
		while(scan.hasNextLine()){
			String line = scan.nextLine().trim(); 
			if (line.isEmpty() || line.startsWith("%")) continue;
			String[] parts = line.split(",");
			Usuario usuario = new Usuario(parts[0]);
			ArrayList<Double> vector = new ArrayList<>();
			for (int i = 1; i < parts.length; i++) {
				vector.add(Double.parseDouble(parts[i]));
			}

			fileLoaded.put(usuario, vector);
		}
		scan.close();
		computeDistances(fileLoaded);
		fileLoaded.clear();
		return true;
	}
	
	public void computeDistances(TreeMap<Usuario, ArrayList<Double>> fileLoaded) {
		this.datos.clear();
		for (Entry<Usuario, ArrayList<Double>> entryU : fileLoaded.entrySet()) {
			Usuario u = entryU.getKey();
			ArrayList<Double> vectorU = entryU.getValue();
			double normaU = 0.0;
			for (double x : vectorU) normaU += x * x;
			if (normaU == 0.0) continue;
			TreeMap<String, TreeSet<Usuario>> distanciasU = new TreeMap<>();
			for (Entry<Usuario, ArrayList<Double>> entryV : fileLoaded.entrySet()) {
				Usuario v = entryV.getKey();
				if (u.equals(v)) continue;
				ArrayList<Double> vectorV = entryV.getValue();
				double normaV = 0.0;
				for (double x : vectorV) normaV += x * x;
				if (normaV == 0.0) continue;
				double distancia = MyMath.getCosineDistance(vectorU, vectorV);
				if (distancia < 0) continue;
				String distStr = Format.formatDouble(distancia);
				TreeSet<Usuario> set = distanciasU.get(distStr);
				if (set == null) {
					set = new TreeSet<>();
					distanciasU.put(distStr, set);
				}
				set.add(v);
			}
			if (!distanciasU.isEmpty()) {
				this.datos.put(u, distanciasU);
			}
		}
	}
	
	public void buildGraph (int k) {
		if (k <= 0 || k >= this.datos.size()) throw new RuntimeException("k > 0 && k < " + this.datos.size());
		this.adjacencyList.clear();
		this.setDirected(true);
		for (Entry<Usuario, TreeMap<String, TreeSet<Usuario>>> entry : this.datos.entrySet()) {
			Usuario origen = entry.getKey();
			this.addVertex(origen);
			HashMap<Usuario, Double> vecinos = new HashMap<>();
			int contador = 0;
			for (Entry<String, TreeSet<Usuario>> distEntry : entry.getValue().entrySet()) {
				double distancia = Double.parseDouble(distEntry.getKey());
				for (Usuario destino : distEntry.getValue()) {
					if (contador == k) break;
					this.addVertex(destino);
					vecinos.put(destino, distancia);
					contador++;
				}
				if (contador == k) break;
			}
			this.adjacencyList.put(origen, vecinos);
		}
	}
}