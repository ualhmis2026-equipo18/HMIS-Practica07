 package practicas.practica03.parte03;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import practicas.auxiliar.Par;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica03Parte03JUnit5Test {
			
	@Test
	@Order(0)
	public void test00() {
		Nodo<Integer> nodo01 = new Nodo<>(23, 15, 16, 17, 9, 23, 17, 8, 9, 10);
		Nodo<Integer> nodo02 = new Nodo<>(8, 9, 10, 15, 16, 17, 23, 9, 10);
		Nodo<Integer> nodo03 = new Nodo<>(15, 8, 9, 10);
		Nodo<Integer> nodo04 = new Nodo<>(15, 16, 9, 13);
		
		
		assertEquals(nodo01, nodo02);
		assertNotEquals(nodo01, nodo03);
		assertNotEquals(nodo02, nodo04);
		
		assertEquals("[8, 9, 10, 15, 16, 17, 23]", nodo01.toString());
		assertEquals("[8, 9, 10, 15, 16, 17, 23]", nodo02.toString());
		assertEquals("[8, 9, 10, 15]", nodo03.toString());
		assertEquals("[9, 13, 15, 16]", nodo04.toString());
		
		assertTrue(nodo01.compareTo(nodo02) == 0);
		assertTrue(nodo01.compareTo(nodo03) > 0); 
		assertTrue(nodo01.compareTo(nodo04) < 0);
		assertTrue(nodo02.compareTo(nodo03) > 0);
		assertTrue(nodo02.compareTo(nodo04) < 0);
		assertTrue(nodo03.compareTo(nodo04) < 0);
		assertTrue(nodo01.compareTo(new Nodo<>(13)) < 0);
		
		nodo01.clear();
		nodo02.clear(); 
		nodo03.clear();
		nodo04.clear();
	
		assertEquals("[empty]", nodo01.toString());   
		assertEquals("[empty]", nodo02.toString());
		assertEquals("[empty]", nodo03.toString());
		assertEquals("[empty]", nodo04.toString());

		assertTrue(nodo01.compareTo(nodo02) == 0);
		assertTrue(nodo01.compareTo(nodo03) == 0);
		assertTrue(nodo01.compareTo(nodo04) == 0);
		assertTrue(nodo02.compareTo(nodo03) == 0);
		assertTrue(nodo02.compareTo(nodo04) == 0);
		assertTrue(nodo03.compareTo(nodo04) == 0);
		assertTrue(nodo01.compareTo(new Nodo<>(13)) < 0);

		nodo01 = nodo02 = nodo03 = nodo04 = null;
	}
	
	@Test
	@Order(1)
	public void test01() {		
		ArrayList<Nodo<String>> nodos = new ArrayList<>();
		nodos.add(new Nodo<>("amber", "zoe", "dug"));
		nodos.add(new Nodo<>("zander", "homer", "henry","agnes"));
		nodos.add(new Nodo<>("zack"));
		nodos.add(new Nodo<>("homer", "darcy", "hank", "otto"));
		
		//toString()
		assertEquals("[amber, dug, zoe]", nodos.get(0).toString());
		assertEquals("[agnes, henry, homer, zander]", nodos.get(1).toString());
		assertEquals("[zack]", nodos.get(2).toString());
		assertEquals("[darcy, hank, homer, otto]", nodos.get(3).toString());
		
		//Ordenamos en orden ascendente
		nodos.sort(Comparator.naturalOrder());
		
		//toString()
		assertEquals("[agnes, henry, homer, zander]", nodos.get(0).toString());
		assertEquals("[amber, dug, zoe]", nodos.get(1).toString());
		assertEquals("[darcy, hank, homer, otto]", nodos.get(2).toString());
		assertEquals("[zack]", nodos.get(3).toString());
		
		//Ordenamos en orden ascendente
		nodos.sort(Comparator.reverseOrder());
				
		//toString()
		assertEquals("[zack]", nodos.get(0).toString());
		assertEquals("[darcy, hank, homer, otto]", nodos.get(1).toString());
		assertEquals("[amber, dug, zoe]", nodos.get(2).toString());
		assertEquals("[agnes, henry, homer, zander]", nodos.get(3).toString());
		
		for(int i = 0; i < nodos.size(); i++) {
			nodos.get(i).clear();
		}
		nodos.clear();
		nodos = null;
	}
	
	@Test
	@Order(2)
	public void test02() {
		ColeccionNodos<String> coleccion = new ColeccionNodos<>();

		ArrayList<Nodo<String>> nodos = new ArrayList<>();
		nodos.add(new Nodo<>("homer", "henry", "zander", "agnes", "homer"));
		nodos.add(new Nodo<>("darcy", "otto", "homer", "hank"));
		nodos.add(new Nodo<>("zack"));
		nodos.add(new Nodo<>("zoe", "amber", "dug", "zoe"));
		
		coleccion.add(nodos.get(2), nodos.get(1));
		coleccion.add(nodos.get(2), nodos.get(3));
		coleccion.add(nodos.get(3), nodos.get(1));
		coleccion.add(nodos.get(3), nodos.get(3));
		coleccion.add(nodos.get(0), nodos.get(1));
		coleccion.add(nodos.get(0), nodos.get(2));
		coleccion.add(nodos.get(0), nodos.get(3)); 
		
		
		//método toString()
		assertEquals("[agnes, henry, homer, zander]\n" +
						"\t[amber, dug, zoe]\n" +
						"\t[darcy, hank, homer, otto]\n" +
						"\t[zack]\n" +
					 "[amber, dug, zoe]\n" +
					 	"\t[amber, dug, zoe]\n" +
					 	"\t[darcy, hank, homer, otto]\n" +
					 "[zack]\n" +
						"\t[amber, dug, zoe]\n" +
					 	"\t[darcy, hank, homer, otto]\n", 
					 coleccion.toString());		
		
		//Limpiamos los nodos "auxiliares" que hemos creado para insertar en la estructura
		for(int i = 0; i < nodos.size(); i++) {
			nodos.get(i).clear();
		}
		nodos.clear();
		
		//El contenido no debe variar
		
		assertEquals("[agnes, henry, homer, zander]\n" +
						"\t[amber, dug, zoe]\n" +
						"\t[darcy, hank, homer, otto]\n" +
						"\t[zack]\n" +
					 "[amber, dug, zoe]\n" +
					 	"\t[amber, dug, zoe]\n" +
					 	"\t[darcy, hank, homer, otto]\n" +
					 "[zack]\n" +
					 	"\t[amber, dug, zoe]\n" +
					 	"\t[darcy, hank, homer, otto]\n", 
					 coleccion.toString());		

		//Una última comprobación para ver que el iterador está bien implementado...
		String salidaEsperada = "[agnes, henry, homer, zander] <[amber, dug, zoe]>\n" +
				 				"[agnes, henry, homer, zander] <[darcy, hank, homer, otto]>\n" +
				 				"[agnes, henry, homer, zander] <[zack]>\n" +
				 				"[amber, dug, zoe] <[amber, dug, zoe]>\n" +
				 				"[amber, dug, zoe] <[darcy, hank, homer, otto]>\n" +
				 				"[zack] <[amber, dug, zoe]>\n" +
				 				"[zack] <[darcy, hank, homer, otto]>\n";
		String salidaReal = "";
		
		for (Par<Nodo<String>, Nodo<String>> par: coleccion) {
			salidaReal += par + "\n";
		}
		
		assertEquals(salidaEsperada, salidaReal);
		
		coleccion.clear(); 
		nodos = null;
		coleccion = null;
	}
}