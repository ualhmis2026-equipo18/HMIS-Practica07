package pruebas.prueba01;

import java.util.ArrayList;
import java.util.List;

import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Par;

public class Summarize {
		private ArrayList<Par<String, ArrayList<Integer>>> datosOrigen = new ArrayList<>();
		private AVLTree<Par<String, AVLTree<Par<Integer, Integer>>>> datosDestino = new AVLTree<>();
	
		public void add(String clave, Integer...valores) {
			ArrayList<Integer> aux = null;
			int posCurr = this.datosOrigen.indexOf(new Par<>(clave, null));
			if (posCurr == -1) {
				this.datosOrigen.add(new Par<>(clave, aux = new ArrayList<>()));
			}else {
				aux = this.datosOrigen.get(posCurr).getValue();
			}
			aux.addAll(List.of(valores));
		}
		
		/**
	     * Recorre los datos de datosOrigen y genera un resumen de frecuencias
	     * que se guarda en datosDestino.
	     * Para cada clave con su lista de números, crea un árbol AVL interno donde
	     * cada valor indica cuántas veces aparece ese número.  
	     * Si un número se repite, se aumenta su contador; si no existe, se añade con 1.
	     */
		public void summarize() {
			this.datosDestino = new AVLTree<Par<String,AVLTree<Par<Integer,Integer>>>>();
			for(Par<String, ArrayList<Integer>> datoCurr : this.datosOrigen) {
				String clave = datoCurr.getKey();
				ArrayList<Integer> valores = datoCurr.getValue();
				
				AVLTree<Par<Integer, Integer>> numFrec = new AVLTree<Par<Integer,Integer>>();
				
				for(Integer valor : valores) {
	                Par<Integer, Integer> buscado = new Par<>(valor, null);
					Par<Integer, Integer> existente = numFrec.find(buscado);
					
					if(existente == null) {
						numFrec.add(new Par<>(valor,1));
					} else {
						int frecReal = existente.getValue() + 1;
						numFrec.remove(existente);
						numFrec.add(new Par<>(valor, frecReal));
					}
				}
				this.datosDestino.add(new Par<>(clave, numFrec));
			}
		}
		
		@Override
		public String toString() {
			return this.datosOrigen.toString() + "\n" + this.datosDestino.toString(); 
		}
		
		public static void main(String[] args) {
			Summarize prueba = new Summarize();
			prueba.add("clave03", 3, 3, 3);
			prueba.add("clave02", 5, 1, 4, 5, 4);
			prueba.add("clave03", 1, 2, 3, 4, 5, 4, 5);
			prueba.add("clave01", 1, 2, 3, 4, 4, 3 ,2 ,1);
			prueba.summarize();
			String salidaEsperada = "[clave03 <[3, 3, 3, 1, 2, 3, 4, 5, 4, 5]>, clave02 <[5, 1, 4, 5, 4]>, clave01 <[1, 2, 3, 4, 4, 3, 2, 1]>]\n" +
	                				"[clave01 <[1 <2>, 2 <2>, 3 <2>, 4 <2>]>, clave02 <[1 <1>, 4 <2>, 5 <2>]>, clave03 <[1 <1>, 2 <1>, 3 <4>, 4 <2>, 5 <2>]>]";
			System.out.println(prueba.toString().equals(salidaEsperada) ? "¡OK!!!" : "¡¡¡Error!!!");
		}
}
