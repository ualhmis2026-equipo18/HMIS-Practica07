package practicas.practica03.parte02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import practicas.auxiliar.Format;


public class GestionCentros implements Iterable<Entry<String, TreeMap<String, ArrayList<Double>>>>{
	protected final TreeMap<String, TreeMap<String, TreeMap<String, ArrayList<Double>>>> datos = new TreeMap<>();

	public boolean load(String fileName) {
		Scanner scan;
		String line;
		String[] items;
		this.datos.clear();
		try {
			scan = new Scanner(new File(fileName));
		} catch (IOException e) {
			return false;
		}
		while (scan.hasNextLine()) {
			line = scan.nextLine().trim();
			if (line.isBlank()) continue;
			if (line.startsWith("%")) continue;
			items = line.toLowerCase().split("\\s+");
			String[] notasStr = Arrays.copyOfRange(items, 3, items.length);
			Double[] notas = new Double[notasStr.length];
			for (int i = 0; i < notasStr.length; i++) {
				notas[i] = Double.parseDouble(notasStr[i]);
			}
			this.add(items[0],items[1],items[2],notas);
		}
		scan.close();
		return true;
	}

	public void add(String centroId, String estudianteId, String asignaturaId, Double... notas) {
		TreeMap<String, TreeMap<String, ArrayList<Double>>> estudiantes = this.datos.get(centroId);
		if(estudiantes == null) estudiantes = new TreeMap<>();
		this.datos.put(centroId, estudiantes);
		TreeMap<String, ArrayList<Double>> asignatura = estudiantes.get(estudianteId);
		if(asignatura == null) asignatura = new TreeMap<String, ArrayList<Double>>();
		estudiantes.put(estudianteId, asignatura);
		ArrayList<Double> notas2 = asignatura.get(asignaturaId);
		if(notas2 == null) notas2 = new ArrayList<Double>();
		asignatura.put(asignaturaId, notas2);
		notas2.addAll(Arrays.asList(notas));
	}

	public void clear() {
		this.datos.clear();
	}

	public int size() {
		return this.datos.size();
	}

	@Override
	public String toString() {
		int numCentros = this.datos.size();
		String result = numCentros + (numCentros == 1 ? " centro:\n" : " centros:\n");
		for(Entry<String, TreeMap<String, TreeMap<String, ArrayList<Double>>>> centroEntry : this.datos.entrySet()) {
			TreeMap<String, TreeMap<String, ArrayList<Double>>> estudiantes = centroEntry.getValue();
			int totalAsignaturas = 0;
			int numEstudiantes = estudiantes.size();
			for (TreeMap<String, ArrayList<Double>> asignaturas : estudiantes.values()) {
				totalAsignaturas += asignaturas.size();
			}
			result += "\t" + centroEntry.getKey() + " -> " + numEstudiantes + (numEstudiantes == 1 ? " estudiante y " : " estudiantes y ") + totalAsignaturas + (totalAsignaturas == 1 ? " asignatura\n" : " asignaturas\n");
		}
		return result;
	}

	public TreeSet<String> getAsignaturasCentro(String centroId) {
		TreeSet<String> asignaturasCentro = new TreeSet<String>();
		TreeMap<String, TreeMap<String, ArrayList<Double>>> centroInfo = this.datos.get(centroId);
		if(centroInfo == null) return null;
		for(TreeMap<String, ArrayList<Double>> asignaturas : centroInfo.values()) {
			asignaturasCentro.addAll(asignaturas.keySet());
		}
		return asignaturasCentro;
	}

	public TreeSet<String> getEstudiantesAsignatura(String asignatura) {
		TreeSet<String> result = new TreeSet<>();
		for(Entry<String, TreeMap<String, TreeMap<String, ArrayList<Double>>>> centroEntry : this.datos.entrySet()) {
			TreeMap<String, TreeMap<String, ArrayList<Double>>> estudiantes = centroEntry.getValue();
			for(String estudiante : estudiantes.keySet()) {
				if(estudiantes.get(estudiante).containsKey(asignatura)) result.add(estudiante);
			}
		}
		return result.isEmpty() ? null : result;
	}

	public TreeSet<String> getEstudiantesCentro(String centroId) {
		if(this.datos.get(centroId) == null) return null;
		TreeSet<String> estudiantesCentro = new TreeSet<String>();
		estudiantesCentro.addAll(this.datos.get(centroId).keySet());
		return estudiantesCentro;
	}

	public String getNotaMediaCentro(String centroId) {
		ArrayList<Double> result = new ArrayList<>();
		TreeMap<String, TreeMap<String, ArrayList<Double>>> estudiantesCentro = this.datos.get(centroId);
		if(estudiantesCentro == null) return null;
		for(Entry<String, TreeMap<String, ArrayList<Double>>> estudiantes : estudiantesCentro.entrySet()) {
			for(ArrayList<Double> notas : estudiantes.getValue().values()) {
				result.addAll(notas);
			}
		}
		return result.isEmpty() ? null : Format.formatDouble(MyMath.calculaMedia(result));
	}

	public String getNotaMediaEstudiante(String estudianteId) {
		ArrayList<Double> result = new ArrayList<>();
		for(Entry<String, TreeMap<String, TreeMap<String, ArrayList<Double>>>> centroEntry : this.datos.entrySet()) {
			TreeMap<String, ArrayList<Double>> asignaturas = centroEntry.getValue().get(estudianteId);
			if (asignaturas != null) {
				for (ArrayList<Double> notas : asignaturas.values()) {
					result.addAll(notas);
				}
			}
		}
		return result.isEmpty() ? null : Format.formatDouble(MyMath.calculaMedia(result));
	}

	public String getNotaMediaAsignatura(String asignaturaId) {
		ArrayList<Double> result = new ArrayList<>();
		for(Entry<String, TreeMap<String, TreeMap<String, ArrayList<Double>>>> centroEntry : this.datos.entrySet()) {
			for(TreeMap<String, ArrayList<Double>> asignaturas : centroEntry.getValue().values()) {
				ArrayList<Double> notas = asignaturas.get(asignaturaId);
				if(notas != null) result.addAll(notas);
			}
		}
		return result.isEmpty() ? null : Format.formatDouble(MyMath.calculaMedia(result));
	}

	public boolean checkEstudiantes() {
		for (String centro1 : this.datos.keySet()) {
			for (String centro2 : this.datos.keySet()) {
				if (centro1.equals(centro2)) continue;

				TreeSet<String> est1 = this.getEstudiantesCentro(centro1);
				TreeSet<String> est2 = this.getEstudiantesCentro(centro2);

				if (est1 == null || est2 == null) continue;

				TreeSet<String> interseccion = new TreeSet<>(est1);
				interseccion.retainAll(est2);

				if (!interseccion.isEmpty()) return false;
			}
		}
		return true;
	}

	public boolean checkAsignaturas() {
		for (String centro1 : this.datos.keySet()) {
			for (String centro2 : this.datos.keySet()) {
				if (centro1.equals(centro2)) continue;

				TreeSet<String> asig1 = this.getAsignaturasCentro(centro1);
				TreeSet<String> asig2 = this.getAsignaturasCentro(centro2);

				if (asig1 == null || asig2 == null) continue;

				TreeSet<String> interseccion = new TreeSet<>(asig1);
				interseccion.retainAll(asig2);

				if (!interseccion.isEmpty()) return false;
			}
		}
		return true;
	}

	@Override
	public Iterator<Entry<String, TreeMap<String, ArrayList<Double>>>> iterator() {
		TreeMap<String, TreeMap<String, ArrayList<Double>>> resultado = new TreeMap<>();
		for (Entry<String, TreeMap<String, TreeMap<String, ArrayList<Double>>>> centro : this.datos.entrySet()) {
			TreeMap<String, ArrayList<Double>> estudiantesNotas = new TreeMap<>();
			for (Entry<String, TreeMap<String, ArrayList<Double>>> estudiante : centro.getValue().entrySet()) {
				ArrayList<Double> todasLasNotas = new ArrayList<>();
				for (ArrayList<Double> notasAsignatura : estudiante.getValue().values()) {
					todasLasNotas.addAll(notasAsignatura);
				}
				estudiantesNotas.put(estudiante.getKey(), todasLasNotas);
			}
			resultado.put(centro.getKey(), estudiantesNotas);
		}
		return resultado.entrySet().iterator();
	}

}