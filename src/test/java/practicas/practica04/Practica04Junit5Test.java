package practicas.practica04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import practicas.auxiliar.Format;
import practicas.auxiliar.Par;
import practicas.practica03.parte01.Usuario;




@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica04Junit5Test {
	private final String directorioEntrada = System.getProperty("user.dir") + File.separator +
											"src" + File.separator +
											"main" + File.separator +
											"java" + File.separator +
											"practicas" + File.separator +
											"practica04" + File.separator;
	@Test
	@Order(0)
	public void test00() {
		Network<Integer> net = new Network<>();
		assertTrue(net.isDirected());
		assertTrue(net.isEmpty());
		net.addVertex(0);
		net.addVertex(1);
		net.addVertex(2);
		net.addVertex(3);
		net.addVertex(4);
		net.addVertex(5); 
		net.addVertex(6);

		net.addEdge(4, 0, 1);
		net.addEdge(4, 6, 1);
		net.addEdge(4, 3, 1);
		net.addEdge(1, 4, 1);
		net.addEdge(3, 1, 1);
		net.addEdge(3, 2, 1); 
		net.addEdge(2, 5, 1);
		net.addEdge(5, 4, 1);

		assertEquals("{0={}, 1={4=1.0}, 2={5=1.0}, 3={1=1.0, 2=1.0}, 4={0=1.0, 3=1.0, 6=1.0}, 5={4=1.0}, 6={}}", net.toString());
		assertEquals("[0, 3, 6]", net.getNeighbors(4).toString());
		assertEquals("[1, 2]", net.getNeighbors(3).toString());
		assertNull(net.getNeighbors(8));
		assertNull(net.getNeighbors(8));
		assertTrue(net.getWeight(2, 5) == 1.0);
		assertTrue(net.getWeight(0, 3) == -1);
		assertTrue(net.numberOfVertices() == 7);
		assertNull(net.toArrayBreadthFirst(8));
		assertNull(net.toArrayDepthFirst(8));
		assertEquals("[3, 1, 2, 4, 5, 0, 6]", net.toArrayBreadthFirst(3).toString());
		assertEquals("[3, 2, 5, 4, 6, 0, 1]", net.toArrayDepthFirst(3).toString());
	
		//Dijkstra
		assertNull(net.getDijkstra(null, null));
		assertNull(net.getDijkstra(0, 1));
		assertNull(net.getDijkstra(6, 3));
		assertEquals("[1 <0.0>, 4 <1.0>, 0 <2.0>]", net.getDijkstra(1, 0).toString());
		assertEquals("[4 <0.0>, 3 <1.0>, 2 <2.0>, 5 <3.0>]", net.getDijkstra(4, 5).toString());
		
		//Eliminamos vértices
		net.removeVertex(0);
		net.removeVertex(1);
		net.removeVertex(2);
		net.removeVertex(3);
		assertTrue(net.numberOfVertices() == 3);
		net.removeEdge(5, 4);
		net.setWeight(4, 6, 2.0);
		assertEquals("{4={6=2.0}, 5={}, 6={}}", net.toString());
		assertTrue(net.vertexSet().size() == 3);
		
		net.clear();
		assertTrue(net.vertexSet().size() == 0);
		assertEquals("{}", net.toString());
		
		net = null;
	}
	
	@Test
	@Order(1)
	public void test01() {
		ArrayList<Double> arr1 = new ArrayList<>(List.of(5.0, 4.0, 0.0, 0.0));
		ArrayList<Double> arr2 = new ArrayList<>(List.of(4.0, 5.0, 0.0, 0.0));
		ArrayList<Double> arr3 = new ArrayList<>(List.of(0.0, 0.0, 5.0, 4.0));		
		ArrayList<Double> arr4 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));

		try {
			MyMath.getCosineDistance(arr1, new ArrayList<>(List.of(5.5, 2.2)));
		}catch(Exception e) {
			assertEquals("Los arrays deben tener la misma longitud", e.getMessage());
		}
		
		assertEquals("0.00", Format.formatDouble(MyMath.getCosineDistance(arr1, arr1)));
		assertEquals("0.02", Format.formatDouble(MyMath.getCosineDistance(arr1, arr2)));
		assertEquals("1.00", Format.formatDouble(MyMath.getCosineDistance(arr1, arr3)));
		assertEquals("1.00", Format.formatDouble(MyMath.getCosineDistance(arr2, arr3)));
		assertEquals("-1.00", Format.formatDouble(MyMath.getCosineDistance(arr4, arr2)));
		assertEquals("-1.00", Format.formatDouble(MyMath.getCosineDistance(arr2, arr4)));
		assertEquals(MyMath.getCosineDistance(arr1, arr2), MyMath.getCosineDistance(arr2, arr1));
		assertEquals(MyMath.getCosineDistance(arr1, arr3), MyMath.getCosineDistance(arr3, arr1));
		assertEquals(MyMath.getCosineDistance(arr1, arr4), MyMath.getCosineDistance(arr4, arr1));
		assertEquals(MyMath.getCosineDistance(arr2, arr3), MyMath.getCosineDistance(arr3, arr2));
		assertEquals(MyMath.getCosineDistance(arr2, arr4), MyMath.getCosineDistance(arr4, arr2));
		assertEquals(MyMath.getCosineDistance(arr3, arr4), MyMath.getCosineDistance(arr4, arr3));
		arr1.clear();
		arr2.clear();
		arr3.clear();
		arr4.clear();
		arr1 = arr2 = arr3 = arr4 = null;
	}
	
	@Test
	@Order(2)
	public void test02() {
		UsuariosBibliotecaNetwork net = new UsuariosBibliotecaNetwork();
		TreeMap<Usuario, ArrayList<Double>> mapa = new TreeMap<>();
		mapa.put(new Usuario("u00"), new ArrayList<>(List.of(5.0, 4.0, 0.0, 0.0)));
		mapa.put(new Usuario("u01"), new ArrayList<>(List.of(4.0, 5.0, 0.0, 0.0)));
		mapa.put(new Usuario("u02"), new ArrayList<>(List.of(0.0, 0.0, 5.0, 4.0)));
		
		assertEquals("{}", net.toString());
	
		net.computeDistances(mapa);
		assertEquals("{u00={0.02=[u01], 1.00=[u02]}, u01={0.02=[u00], 1.00=[u02]}, u02={1.00=[u00, u01]}}", net.toString());
		
		net.computeDistances(mapa);
		assertEquals("{u00={0.02=[u01], 1.00=[u02]}, u01={0.02=[u00], 1.00=[u02]}, u02={1.00=[u00, u01]}}", net.toString());
		
		mapa.put(new Usuario("u03"), new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0))); //computeDistances ignorará este usuario
		
		net.computeDistances(mapa);
		assertEquals("{u00={0.02=[u01], 1.00=[u02]}, u01={0.02=[u00], 1.00=[u02]}, u02={1.00=[u00, u01]}}", net.toString());

		
		mapa.put(new Usuario("u04"), new ArrayList<>(List.of(5.0, 0.0, 5.0, 0.0))); 
		
		net.computeDistances(mapa);
		assertEquals("{u00={0.02=[u01], 0.45=[u04], 1.00=[u02]}, " +
				      "u01={0.02=[u00], 0.56=[u04], 1.00=[u02]}, " +
				      "u02={0.45=[u04], 1.00=[u00, u01]}, " +
				      "u04={0.45=[u00, u02], 0.56=[u01]}}", 
				      net.toString());
		
		net.clear();
		assertEquals("{}", net.toString());

		net = null;
	}
	
	@Test
	@Order(3)
	public void test03() {
		UsuariosBibliotecaNetwork net = new UsuariosBibliotecaNetwork();
		TreeMap<Usuario, ArrayList<Double>> mapa = new TreeMap<>();
		mapa.put(new Usuario("u00"), new ArrayList<>(List.of(5.0, 5.0, 0.0, 0.0)));
		mapa.put(new Usuario("u01"), new ArrayList<>(List.of(5.0, 5.0, 0.0, 0.0)));
		mapa.put(new Usuario("u02"), new ArrayList<>(List.of(5.0, 5.0, 0.0, 0.0)));
		mapa.put(new Usuario("u03"), new ArrayList<>(List.of(0.0, 0.0, 5.0, 4.0)));
		mapa.put(new Usuario("u04"), new ArrayList<>(List.of(0.0, 0.0, 5.0, 4.0)));
		
		net.computeDistances(mapa);

		assertEquals("{u00={0.00=[u01, u02], 1.00=[u03, u04]}, " +
					  "u01={0.00=[u00, u02], 1.00=[u03, u04]}, " +
				      "u02={0.00=[u00, u01], 1.00=[u03, u04]}, " +
					  "u03={0.00=[u04], 1.00=[u00, u01, u02]}, " +
				      "u04={0.00=[u03], 1.00=[u00, u01, u02]}}",
				       net.toString());
	
		//k = 0
		try {
			net.buildGraph(0); 
		}catch(Exception e) {
			assertEquals(e.getMessage(), "k > 0 && k < 5"); 
		}
		
		//k = 1
		net.buildGraph(1);
		for(Usuario u: net) {
			assertTrue(net.getNeighbors(u).size() == 1);
		}
		String salidaEsperada = "u00 -> [u01]\n" +
								"u01 -> [u00]\n" +
								"u02 -> [u00]\n" +
								"u03 -> [u04]\n" +
								"u04 -> [u03]\n";
		String salidaReal = "";
		for (Usuario u: net) {
			salidaReal += u.toString() + " -> " + net.getNeighbors(u) + "\n";
		}

		assertEquals(salidaEsperada, salidaReal);
		
		//k = 2
		net.buildGraph(2);
		for(Usuario u: net) {
			assertTrue(net.getNeighbors(u).size() == 2);
		}
		assertTrue(net.numberOfEdges() == 10);
		salidaEsperada = "u00 -> [u01, u02]\n" +
						 "u01 -> [u00, u02]\n" +
						 "u02 -> [u00, u01]\n" +
						 "u03 -> [u00, u04]\n" +
						 "u04 -> [u00, u03]\n";
		salidaReal = "";
		for (Usuario u: net) {
			salidaReal += u.toString() + " -> " + net.getNeighbors(u) + "\n";
		}

		assertEquals(salidaEsperada, salidaReal);

		//k = 3
		net.buildGraph(3);
		for(Usuario u: net) {
			assertTrue(net.getNeighbors(u).size() == 3);
		}
		assertTrue(net.numberOfEdges() == 15);
		salidaEsperada = "u00 -> [u01, u02, u03]\n" +
				 		 "u01 -> [u00, u02, u03]\n" +
				 		 "u02 -> [u00, u01, u03]\n" +
				 		 "u03 -> [u00, u01, u04]\n" +
				 		 "u04 -> [u00, u01, u03]\n";
		salidaReal = "";
		for (Usuario u: net) {
			salidaReal += u.toString() + " -> " + net.getNeighbors(u) + "\n";
		}

		assertEquals(salidaEsperada, salidaReal);
		
		//k = 4 (grafo denso)
		net.buildGraph(4);
		for(Usuario u: net) {
			assertTrue(net.getNeighbors(u).size() == 4);
		}
		salidaEsperada = "u00 -> [u01, u02, u03, u04]\n" +
		                 "u01 -> [u00, u02, u03, u04]\n" +
				         "u02 -> [u00, u01, u03, u04]\n" +
				         "u03 -> [u00, u01, u02, u04]\n" +
				         "u04 -> [u00, u01, u02, u03]\n";
		salidaReal = "";
		for (Usuario u: net) {
			salidaReal += u.toString() + " -> " + net.getNeighbors(u) + "\n";
		}
		
		assertEquals(salidaEsperada, salidaReal);
		
		//Algunas comprobaciones adicionales
		assertTrue(net.numberOfEdges() == 20);
		assertEquals(net.getWeight(new Usuario("u04"), new Usuario("u03")), 0.0);
		assertEquals(net.getWeight(new Usuario("u00"), new Usuario("u03")), 1.0);
		assertEquals(net.getWeight(new Usuario("u04"), new Usuario("u01")), 1.0);
		assertEquals(net.getWeight(new Usuario("u03"), new Usuario("u04")), 0.0);
		assertEquals(net.getWeight(new Usuario("u013"), new Usuario("u04")), -1.0);
		
		//k = 5
		try {
			net.buildGraph(5);
		}catch(Exception e) {
			assertEquals(e.getMessage(), "k > 0 && k < 5");
		}
				
		mapa.clear();
		net.clear();
		
		mapa = null;
		net = null;
	}
	
	@Test
	@Order(4)
	public void test04() {
		UsuariosBibliotecaNetwork net = new UsuariosBibliotecaNetwork();
		assertFalse(net.load("datos.txt")); //Ya sabemos que datos.txt contiene información sobre 300 usuarios y 3000 libros
		assertTrue(net.load(directorioEntrada + "datos.txt"));
		
		
		//Vamos a obtener la longitud máximo de los patrones obtenidos según distintos valores de k
		TreeMap<Integer, Integer> k_longitudMax = new TreeMap<>(Map.of(299, 2, 
				                                                       150, 3, 
				                                                       50, 4, 
				                                                       10, 6, 
				                                                       6, 8,
				                                                       4, 12));
		for(int k: k_longitudMax.keySet()) {
			net.buildGraph(k);
			for (Usuario vertex: net) {
				assertTrue(net.getNeighbors(vertex).size() == k);
			}
			int max = Integer.MIN_VALUE;
			for (Usuario vertexO: net) {
				for (Usuario vertexD: net) {
					ArrayList<Par<Usuario, Double>> arr = net.getDijkstra(vertexO, vertexD);
					if (arr == null) continue;
					if (arr.size() > max) {
						max = arr.size();
					}
				}
			}
			assertTrue(net.numberOfEdges() == k * 300);
			assertTrue(max == k_longitudMax.get(k));
		}
	}
}