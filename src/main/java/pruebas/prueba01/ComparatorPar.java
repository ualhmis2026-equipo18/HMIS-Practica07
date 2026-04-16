package pruebas.prueba01;

import java.util.ArrayList;
import java.util.Comparator;

import practicas.auxiliar.Par;

public class ComparatorPar<K extends Comparable<K>, V extends Comparable<V>> implements Comparator<Par<K,V>>{
	
	/**
     * Compara dos pares según su clave y si son iguales, según su valor.
     * El orden aplicado es descendente tanto para la clave como para el valor.
     *
     * @param p1 primer par a comparar
     * @param p2 segundo par a comparar
     * @return un valor negativo si p2 debe ir antes que p1,
     *         positivo si p1 debe ir antes que p2,
     *         o cero si ambos pares son equivalentes en clave y valor
     */
	@Override
	public int compare(Par<K,V> p1, Par<K,V> p2) {
		//2 líneas máximo
		int cmp = p2.getKey().compareTo(p1.getKey());
		return cmp != 0 ? cmp : p2.getValue().compareTo(p1.getValue());
	}
	
	public static void main(String[] args) {
		ArrayList<Par<String, String>> pares = new ArrayList<>();
		pares.add(new Par<>("z", "h"));
		pares.add(new Par<>("a", "m"));
		pares.add(new Par<>("a", "a"));
		pares.add(new Par<>("j", "a"));
		pares.add(new Par<>("j", "z"));
		pares.add(new Par<>("n", "a"));
		
		pares.sort(new ComparatorPar<>());

		String salidaEsperada = "[z <h>, n <a>, j <z>, j <a>, a <m>, a <a>]";
		System.out.println(pares.toString().equals(salidaEsperada) ? "¡OK!!!" : "¡¡¡Error!!!");
	}
}
