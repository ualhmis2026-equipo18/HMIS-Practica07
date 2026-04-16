package practicas.practica01;

import java.util.ArrayList;
import java.util.List;

public class Configuracion {
	private static final ArrayList<String> palos_española = new ArrayList<>(List.of("Oros", "Copas", "Espadas", "Bastos"));
	private static final ArrayList<String> palos_francesa = new ArrayList<>(List.of("Corazones", "Diamantes", "Tréboles", "Picas"));
	private static final ArrayList<String> valores_española = new ArrayList<>(List.of("As", "2", "3", "4", "5", "6", "7", "Sota", "Caballo", "Rey"));
	private static final ArrayList<String> valores_francesa = new ArrayList<>(List.of("As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Sota", "Reina", "Rey"));

	public enum TIPO_BARAJA {
		Española, Francesa;
 
		public Boolean esValorValido(String valor) {
			return this == Española ? valores_española.contains(valor) : valores_francesa.contains(valor);
		}
		
		public Boolean esPaloValido(String palo) {
			return this == Española ? palos_española.contains(palo) : palos_francesa.contains(palo);
		}
		
		public Integer getIndicePalo(String palo) {
			return  this == Española ? palos_española.indexOf(palo) : palos_francesa.indexOf(palo);
		}

		public Integer getIndiceValor(String valor) {
			return this == Española ? valores_española.indexOf(valor) : valores_francesa.indexOf(valor);
		}

		public ArrayList<String> getPalos() {
			return this == Española ? palos_española : palos_francesa;
		}
		
		public ArrayList<String> getValores() {
			return this == Española ? valores_española : valores_francesa;
		}
	};
}