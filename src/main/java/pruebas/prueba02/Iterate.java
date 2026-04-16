package pruebas.prueba02;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Iterate implements Iterable<Entry<String, TreeMap<String, String>>> {
	private TreeMap<String, TreeMap<String, ArrayList<String>>> datos = new TreeMap<>();
	
	public void add(String mainKey, String secondKey, String...values) {
		TreeMap<String, ArrayList<String>> mainValues = this.datos.get(mainKey);
		if (mainValues == null) this.datos.put(mainKey, mainValues = new TreeMap<>());
		ArrayList<String> secondValues = mainValues.get(secondKey);
		if (secondValues == null) mainValues.put(secondKey, secondValues = new ArrayList<>());
		secondValues.addAll(List.of(values));
	}
	
	@Override
	public String toString() {
		return this.datos.toString();
	}

	@Override
	public Iterator<Entry<String, TreeMap<String, String>>> iterator() {
		TreeMap<String, TreeMap<String, String>> aux = new TreeMap<>();
		for (String u : this.datos.keySet()) {
			for (ArrayList<String> ws : this.datos.get(u).values()) {
				for (String w : ws) {
					TreeMap<String, String> contadorU = aux.get(w);
					if (contadorU == null) {
						contadorU = new TreeMap<>();
						aux.put(w, contadorU);
					}
					String actual = contadorU.get(u);
					if (actual == null) {
						contadorU.put(u, "*");
					} else {
						contadorU.put(u, actual + "*");
					}
				}
			}
		}
		return aux.entrySet().iterator();
	}

	
	public boolean check(TreeMap<String, TreeMap<String, String>> aux) {
		boolean w01 = "{u01=***, u02=****}".equals(aux.get("w01").toString());
		boolean w02 = "{u01=*, u02=***}".equals(aux.get("w02").toString());
		boolean w03 = "{u01=**, u02=***}".equals(aux.get("w03").toString());
		boolean w04 = "{u01=***, u02=***}".equals(aux.get("w04").toString());
		return w01 && w02 && w03 && w04;
	}
	
	public static void main(String[] args) {
		Iterate ejercicio = new Iterate();
		ejercicio.add("u01",  "d01", "w01", "w02", "w03", "w04", "w01", "w01");
		ejercicio.add("u01",  "d02", "w03", "w04", "w04");
		ejercicio.add("u02",  "d01", "w01", "w02", "w03", "w04");
		ejercicio.add("u02",  "d02", "w01", "w04", "w01", "w04");
		ejercicio.add("u02",  "d03", "w01", "w02", "w03", "w02", "w03");
		
	//	System.out.println(ejercicio);
		TreeMap<String, TreeMap<String, String>> aux = new TreeMap<>();
		for (Entry<String, TreeMap<String, String>> par: ejercicio) {
			aux.put(par.getKey(), par.getValue());
			//System.out.println(par.toString());
		}
		System.out.println(ejercicio.check(aux) ? "¡¡¡OK!!!" : "¡¡¡Error!!!");
	}	
}