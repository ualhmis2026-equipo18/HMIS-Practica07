package practicas.practica02.parte02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Format;
import practicas.auxiliar.Par;


public class Practica02Parte02JUnit5Test {
		 			
	@Test
	@Order(0)
	public void test00() {
		AVLTree<Par<String, ArrayList<Double>>> arbol = new AVLTree<>();
		ArrayList<Double> aux = null;
		
		assertTrue(arbol.add(new Par<>("Oswald", aux = new ArrayList<Double>())));
		
		assertTrue(aux.size() == 0);
		assertTrue(arbol.find(new Par<>("Oswald", null)).getValue().size() == 0);
		
		
		aux.add(3.0);
		aux.add(2.0);
		aux.add(4.0);
		aux.add(7.0);
		
		assertTrue(arbol.find(new Par<>("Oswald", null)).getValue().size() == 4);
		
		aux.clear();
		
		assertTrue(arbol.find(new Par<>("Oswald", null)).getValue().size() == 0);
		
		
		aux = new ArrayList<Double>();
		aux.add(3.0);
		aux.add(2.0);
		aux.add(8.0);
		aux.add(3.0);
		
		assertTrue(arbol.find(new Par<>("Oswald", null)).getValue().size() == 0);
		
		arbol.find(new Par<>("Oswald", null)).setValue(aux);
		
		assertTrue(arbol.find(new Par<>("Oswald", null)).getValue().size() == 4);
		
		
		assertTrue(arbol.add(new Par<>("Edward", aux))); 
		
		assertTrue(arbol.find(new Par<>("Edward", null)).getValue().size() == 4);

		aux.clear();
		
		assertTrue(arbol.find(new Par<>("Oswald", null)).getValue().size() == 0);
		assertTrue(arbol.find(new Par<>("Edward", null)).getValue().size() == 0);
		
		aux.add(3.0);
		aux.add(2.0);
		aux.add(8.0);
		aux.add(3.0);
		
		assertEquals("[3.0, 2.0, 8.0, 3.0]", arbol.find(new Par<>("Oswald", null)).getValue().toString());
		assertEquals("[3.0, 2.0, 8.0, 3.0]", arbol.find(new Par<>("Edward", null)).getValue().toString());
		
		arbol.find(new Par<>("Edward", null)).setValue(new ArrayList<>(List.of(12.0, 13.0)));
	
		assertEquals("[3.0, 2.0, 8.0, 3.0]", arbol.find(new Par<>("Oswald", null)).getValue().toString());
		assertEquals("[12.0, 13.0]", arbol.find(new Par<>("Edward", null)).getValue().toString());
		
		aux.clear();
		
		assertEquals("[]", arbol.find(new Par<>("Oswald", null)).getValue().toString());
		assertEquals("[12.0, 13.0]", arbol.find(new Par<>("Edward", null)).getValue().toString());
		
		aux = arbol.find(new Par<>("Edward", null)).getValue();
		
		for(Par<String, ArrayList<Double>> par: arbol) {
			par.getValue().clear();
		}
		arbol.clear();
		
		assertTrue(aux.size() == 0);
		
	}
	
	@Test
	@Order(1)
	public void test01() {
		Sujeto sujeto1 = new Sujeto("@oswald");
		Sujeto sujeto2 = new Sujeto("@edwarD");
		Sujeto sujeto3 = new Sujeto("@Alfred");
		
		assertTrue(sujeto1.add("prueba01", 2.0, 5.0, 8.9, 7.2, 0., .0));
		assertFalse(sujeto1.add("prueba01", 6.0, 5.0, 0., 2.0, 2.0));
		assertTrue(sujeto2.add("prueba01", 3.0, 2.0, 5.0, 8.0, 5.0));
		assertTrue(sujeto2.add("prueba02", 8.0, 5.0, 7.5, 9.0, 12.0));
		assertTrue(sujeto3.add("prueba02", 4., 4., .4, .4));
		assertTrue(sujeto3.add("prueba03", 4., 4., 4.));
		
		assertTrue(sujeto1.getNumPuntuaciones() == 11);
		assertTrue(sujeto1.getNumPuntuaciones("pruebas0001") == -1);
		assertTrue(sujeto1.getNumPuntuaciones("prueba01") == 11);
		
		assertTrue(sujeto3.getNumPuntuaciones() == 7);
		assertTrue(sujeto3.getNumPuntuaciones("prueba02") == 4);
		
		assertTrue(sujeto1.getMaximaPuntuacion() == 8.9);
		assertEquals(Format.formatDouble(sujeto2.getMaximaPuntuacion()), "12.00");
		assertEquals(Format.formatDouble(sujeto3.getMaximaPuntuacion()), "4.00");
		
		assertTrue(sujeto2.getMaximaPuntuacion("prueba03") == -1);
		
		assertTrue(sujeto2.getMaximaPuntuacion() == sujeto2.getMaximaPuntuacion("prueba02"));
	
		assertEquals("@oswald=<1 prueba>", sujeto1.toString());
		assertEquals("@edwarD=<2 pruebas>", sujeto2.toString());
		assertEquals("@Alfred=<2 pruebas>", sujeto3.toString());
		
		assertTrue(sujeto1.add("prueba02"));
		assertTrue(sujeto1.add("prueba03"));
		assertTrue(sujeto1.add("prueba04"));
		assertFalse(sujeto1.add("prueba02", 15., 19.));
		assertFalse(sujeto1.add("prueba03", 1.5, 1.9, 2.));
		
		assertEquals("@oswald=<4 pruebas>", sujeto1.toString());

		String salidaEsperada = "prueba01 <3.46>\n" + 
								"prueba02 <17.00>\n" + 
								"prueba03 <1.80>\n" + 
								"prueba04 <0.00>\n";
								
		String salidaReal = "";
		
		for (Par<String, String> par : sujeto1) {
			salidaReal += par.toString() + "\n";
		}
		assertEquals(salidaEsperada, salidaReal);
		
		sujeto1.clear();
		sujeto2.clear();
		sujeto3.clear();
		salidaEsperada = salidaReal = null;
		sujeto1 = sujeto2 = sujeto3 = null;
	}
}