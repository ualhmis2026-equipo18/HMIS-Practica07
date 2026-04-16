package practicas.practica02.parte02;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Format;
import practicas.auxiliar.Par;


public class Sujeto implements Comparable<Sujeto>, Iterable<Par<String,String>> {

	private String sujetoID;
	private AVLTree<Par<String, ArrayList<Double>>> pruebaPuntuaciones;

	public Sujeto(String sujetoID) {
		this.sujetoID = sujetoID.trim();
		this.pruebaPuntuaciones = new AVLTree<Par<String,ArrayList<Double>>>();
	}

	public void clear() {
		for(Par<String, ArrayList<Double>> pruebaPuntuacion: this.pruebaPuntuaciones) {
			pruebaPuntuacion.getValue().clear();
		}
		this.pruebaPuntuaciones.clear();
	}

	public boolean add(String pruebaID, Double...puntuaciones) {
		Par<String, ArrayList<Double>> pruebaCurrent = this.pruebaPuntuaciones.find(new Par<>(pruebaID, new ArrayList<>()));
		if(pruebaCurrent == null) {
			this.pruebaPuntuaciones.add(new Par<>(pruebaID, new ArrayList<>(List.of(puntuaciones))));
		} else {
			pruebaCurrent.getValue().addAll(List.of(puntuaciones));
		}
		return pruebaCurrent == null;
	}

	public double getMaximaPuntuacion() {
		double max = Double.MIN_VALUE;
		for(Par<String, ArrayList<Double>> pruebaCurrent : this.pruebaPuntuaciones) {
			if(pruebaCurrent == null) continue;
			for(Double puntuacionCurr : pruebaCurrent.getValue()) {
				if(puntuacionCurr == null) continue;
				if(max < puntuacionCurr) max = puntuacionCurr;
			}
		}
		return max;
	}

	public double getMaximaPuntuacion(String pruebaID) {
		double max = .0;
		Par<String, ArrayList<Double>> prueba = this.pruebaPuntuaciones.find(new Par<>(pruebaID, new ArrayList<>()));
		if(prueba == null) return -1;
		for(Double puntuacionCurr : prueba.getValue()) {
			if(puntuacionCurr == null) continue;
			if(max < puntuacionCurr) max = puntuacionCurr;
		}
		return max;
	}

	public int getNumPuntuaciones() {
		int cont = 0;
		for(Par<String, ArrayList<Double>> pruebaCurrent : this.pruebaPuntuaciones) {
			cont += pruebaCurrent.getValue().size();
		}
		return cont;
	}

	public int getNumPuntuaciones(String pruebaID) {
		Par<String, ArrayList<Double>> pruebaCurrent = this.pruebaPuntuaciones.find(new Par<>(pruebaID, new ArrayList<>()));
		return pruebaCurrent == null ? -1 : pruebaCurrent.getValue().size();
	}

	@Override
	public String toString() {
		return this.sujetoID + "=<" + this.pruebaPuntuaciones.size() + (this.pruebaPuntuaciones.size() == 1 ? " prueba>" : " pruebas>");
	}

	@Override
	public boolean equals(Object o) {
		return this.compareTo((Sujeto)o) == 0; 
	}

	@Override
	public int compareTo(Sujeto o) {
		return this.sujetoID.compareTo(o.sujetoID);
	}

	@Override
	public Iterator<Par<String, String>> iterator() {
		ArrayList<Par<String, String>> result = new ArrayList<>();
		for(Par<String, ArrayList<Double>> pruebaCurr : this.pruebaPuntuaciones) {
			double suma = 0;
			double mediaAritmetica = 0;
			if(pruebaCurr == null) continue;
			if(pruebaCurr.getValue().size() == 0) {
				mediaAritmetica = 0;
			} else {
				for(Double puntuacionCurr : pruebaCurr.getValue()) {
					suma += puntuacionCurr;
				}
				mediaAritmetica = suma/pruebaCurr.getValue().size();
			}
			result.add(new Par<>(pruebaCurr.getKey(), Format.formatDouble(mediaAritmetica)));
		}
		return result.iterator();
	}
}