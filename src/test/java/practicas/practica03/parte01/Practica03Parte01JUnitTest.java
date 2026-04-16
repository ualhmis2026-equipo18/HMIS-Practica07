 package practicas.practica03.parte01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica03Parte01JUnitTest {
	String directorioEntrada = System.getProperty("user.dir") + File.separator +
			                  "src" + File.separator +
			                  "main" + File.separator +
			                  "java" + File.separator +
						      "practicas" + File.separator +
			   				  "practica03" + File.separator +
			   				  "parte01" + File.separator;
	
	@Test
	@Order(0) 
	public void test00() {
		Libro libro01 = new Libro("autor02", "libro01");
		Libro libro02 = new Libro("autor01", "libro02");
		Libro libro03 = new Libro("autor01", "libro03");
		
		//getKey() 
		assertEquals("autor02 - libro01", libro01.getAutorIdLibroId());
		assertEquals("autor01 - libro02", libro02.getAutorIdLibroId()); 
		assertEquals("autor01 - libro03", libro03.getAutorIdLibroId());

		//toString() 
		assertEquals("libro01 (autor02) -> [0 palabras distintas]", libro01.toString());
		assertEquals("libro02 (autor01) -> [0 palabras distintas]", libro02.toString());

		//Insertamos palabas en book1
		libro01.add("adios", "mundo", "cruel", "adios");

		//toString()
		assertEquals("libro01 (autor02) -> [3 palabras distintas]", libro01.toString());

		//Insertamos palabras en book2
		libro02.add("hola", "mundo", "maravilloso", "adios", "mundo", "adios");
		libro02.add("hola", "mundo", "maravilloso");

		//toString()
		assertEquals("libro02 (autor01) -> [4 palabras distintas]", libro02.toString());

		//Insertamos palabras en book3
		libro03.add("hola", "hola", "hola", "hola");
		assertEquals("libro03 (autor01) -> [1 palabra distinta]", libro03.toString());
		
		//getNumPalabras() y getPalabras()
		assertTrue(libro01.getNumPalabras() == 4);
		assertEquals("[adios, cruel, mundo]", libro01.getPalabras().toString());
		
		assertTrue(libro02.getNumPalabras() == 9);
		assertEquals("[adios, hola, maravilloso, mundo]", libro02.getPalabras().toString());
		
		//equals() y compareTo()
		assertNotEquals(libro01, libro02);
		assertEquals(libro02, new Libro("   autor01     ", "libro02   "));
		assertTrue(libro01.compareTo(libro02) > 0);
		assertTrue(libro01.compareTo(new Libro("autor02", "libro01")) == 0);
		assertTrue(libro01.compareTo(new Libro("autor02", "libro00")) > 0);
		assertTrue(libro01.compareTo(new Libro("autor03", "libro01")) < 0);

		//Iterar sobre un libro equivale a iterar sobre los objetos palabrasFrecuencia que contiene --> atención al iterador de Libro

		ArrayList<String> salidaEsperada = new ArrayList<>();

		int cont = 0;
		salidaEsperada.add("adios=2");
		salidaEsperada.add("cruel=1");
		salidaEsperada.add("mundo=1");

		for (Entry<String, Integer> palabraFreq : libro01) {
			assertEquals(salidaEsperada.get(cont++), palabraFreq.toString());
		}
		salidaEsperada.clear();

		salidaEsperada.add("adios=2");
		salidaEsperada.add("hola=2");
		salidaEsperada.add("maravilloso=2");
		salidaEsperada.add("mundo=3");

		cont = 0;
		for (Entry<String, Integer> palabraFreq : libro02) {
			assertEquals(salidaEsperada.get(cont++), palabraFreq.toString());
		}
		salidaEsperada.clear();
		
		//¿Cómo se ordenarían los tres libros?
		TreeSet<Libro> libros = new TreeSet<>(List.of(libro01, libro02, libro03));
		assertEquals("[libro02 (autor01) -> [4 palabras distintas], libro03 (autor01) -> [1 palabra distinta], libro01 (autor02) -> [3 palabras distintas]]", libros.toString());
			
		libro01.clear();
		libro02.clear();
		libro03.clear();
		
		assertEquals("libro01 (autor02) -> [0 palabras distintas]", libro01.toString());
		assertEquals("libro02 (autor01) -> [0 palabras distintas]", libro02.toString());
		assertEquals("libro03 (autor01) -> [0 palabras distintas]", libro03.toString());
		
		libro01 = libro02 = libro03 = null;
	}

	@Test
	@Order(1)
	public void test01() {
		try {
			new Libro("libro01"); //No va a encontrar el archivo --> excepción
			throw new RuntimeException("Esta línea no debería ejecutarse nunca"); 
		}catch(Exception e) {
			assertTrue(true); //Una forma de indicar que no se hace nada
		}
		Libro libro01 = new Libro(directorioEntrada + "libro01.txt");
		Libro libro02 = new Libro(directorioEntrada + "libro02.txt");
		Libro libro03 = new Libro(directorioEntrada + "libro03.txt");
		Libro libro04 = new Libro(directorioEntrada + "libro04.txt");

		assertEquals("El Principito (Antoine de Saint-Exupéry) -> [192 palabras distintas]", libro01.toString());
		assertEquals("El Quijote (Cervantes) -> [804 palabras distintas]", libro02.toString());
		assertEquals("Moby Dick (Herman Melville) -> [1084 palabras distintas]", libro03.toString());
		assertEquals("Alicia en el país de las maravillas (Lewis Carroll) -> [337 palabras distintas]", libro04.toString());
		
		assertTrue(libro01.getNumPalabras() == 442);
		assertTrue(libro02.getNumPalabras() == 2497);
		assertTrue(libro03.getNumPalabras() == 4590);
		assertTrue(libro04.getNumPalabras() == 735);
		
		libro01.clear();
		libro02.clear();
		libro03.clear();
		libro04.clear();
		
		assertTrue(libro01.getNumPalabras() == 0);
		assertTrue(libro02.getNumPalabras() == 0);
		assertTrue(libro03.getNumPalabras() == 0);
		assertTrue(libro04.getNumPalabras() == 0);
		
		libro01 = libro02 = libro03 = libro04 = null;
	}
	
	@Test
	@Order(2)
	public void test02() {
		TreeMap<String, Usuario> usuarios = new TreeMap<>();
		usuarios.put("usuario01", new Usuario("usuario01"));
		usuarios.put("usuario03", new Usuario("usuario03"));
		usuarios.put("usuario00", new Usuario("usuario00"));
		usuarios.put("usuario02", new Usuario("usuario02"));
		
		usuarios.get("usuario01").setDocumentoId("33234524U");
		usuarios.get("usuario02").setDireccion("Calle Gorrión núm. 13");
		usuarios.get("usuario03").setDocumentoId("25678456J");
		usuarios.get("usuario03").setDireccion("Calle Gaviota núm. 33");
		
		assertEquals("usuario00", usuarios.get("usuario00").toString());
		assertEquals("usuario01 <33234524U>", usuarios.get("usuario01").toString());
		assertEquals("usuario02 - Calle Gorrión núm. 13", usuarios.get("usuario02").toString());
		assertEquals("usuario03 <25678456J> - Calle Gaviota núm. 33", usuarios.get("usuario03").toString());
		assertEquals("[usuario00, usuario01, usuario02, usuario03]", usuarios.keySet().toString());
	}
	
	@Test
	@Order(3)
	public void test03() {
		GestionBiblioteca biblioteca = new GestionBiblioteca("Biblioteca UAL");
		final int NUM_LIBROS = 5;
		final int NUM_USUARIOS = 3;

		biblioteca.addLibro("autor00", "libro01");
		biblioteca.addLibro("autor01", "libro01");
		biblioteca.addLibro("autor02", "libro01");
		biblioteca.addLibro("autor00", "libro02");
		biblioteca.addLibro("autor00", "libro03");
		biblioteca.addUsuario("usuario01");
		biblioteca.addUsuario("usuario02");
		biblioteca.addUsuario("usuario03");
 
		//toString()
		assertEquals("Biblioteca UAL (" + NUM_LIBROS + " libros y " + NUM_USUARIOS + " usuarios)", biblioteca.toString());

		//prestarLibro() y devolverLibro()
		assertFalse(biblioteca.prestarLibro("usuario19", "autor00", "libro01")); //El usuario usuario19 no existe
		assertFalse(biblioteca.prestarLibro("usuario01", "autor00", "libro19")); //El libro libro19 no existe
		assertFalse(biblioteca.prestarLibro("usuario01", "autor19", "libro01")); //El autor autor19 no existe
		assertTrue(biblioteca.prestarLibro("usuario01", "autor00", "libro01")); 
		assertFalse(biblioteca.prestarLibro("usuario01", "autor00", "libro01")); //El libro ya está prestado
		assertTrue(biblioteca.prestarLibro("usuario01", "autor01", "libro01"));
		assertTrue(biblioteca.prestarLibro("usuario02", "autor00", "libro03"));
		assertFalse(biblioteca.devolverLibro("usuario19", "autor00", "libro01")); 
		assertFalse(biblioteca.devolverLibro("usuario02", "autor00", "libro19"));
		assertFalse(biblioteca.devolverLibro("usuario02", "autor19", "libro01"));
		assertFalse(biblioteca.devolverLibro("usuario02", "autor00", "libro01")); //Es usuario01 quien lo tiene prestado
		assertTrue(biblioteca.devolverLibro("usuario01", "autor00", "libro01")); 
		assertTrue(biblioteca.prestarLibro("usuario02", "autor00", "libro01")); 
		assertTrue(biblioteca.devolverLibro("usuario02", "autor00", "libro01"));
		assertTrue(biblioteca.prestarLibro("usuario02", "autor00", "libro01")); 
		assertTrue(biblioteca.prestarLibro("usuario02", "autor00", "libro02")); 
		assertFalse(biblioteca.devolverLibro("usuario02", "autor02", "libro01")); //No está prestado 
		

		assertEquals("{usuario01=[autor01 - libro01], usuario02=[autor00 - libro01, autor00 - libro02, autor00 - libro03]}", 
				     biblioteca.getPrestamosActuales());

		assertTrue(biblioteca.devolverLibro("usuario02",  "autor00", "libro02"));
		assertTrue(biblioteca.devolverLibro("usuario02",  "autor00", "libro03"));
		
		assertEquals("{usuario01=[autor01 - libro01], usuario02=[autor00 - libro01]}", biblioteca.getPrestamosActuales());
		 
		assertTrue(biblioteca.devolverLibro("usuario01",  "autor01", "libro01"));
		assertTrue(biblioteca.devolverLibro("usuario02",  "autor00", "libro01"));
		
		assertEquals("{}", biblioteca.getPrestamosActuales());
		
		assertTrue(biblioteca.prestarLibro("usuario01", "autor01", "libro01"));
		assertTrue(biblioteca.prestarLibro("usuario01", "autor00", "libro01"));
		assertTrue(biblioteca.prestarLibro("usuario02", "autor00", "libro03")); 
		assertTrue(biblioteca.devolverLibro("usuario02", "autor00", "libro03"));
		assertTrue(biblioteca.prestarLibro("usuario01", "autor00", "libro03"));
		assertTrue(biblioteca.devolverLibro("usuario01", "autor00", "libro03"));
		assertTrue(biblioteca.prestarLibro("usuario01", "autor00", "libro03"));
		assertTrue(biblioteca.devolverLibro("usuario01", "autor00", "libro03"));
		assertTrue(biblioteca.prestarLibro("usuario01", "autor00", "libro03"));
		assertTrue(biblioteca.devolverLibro("usuario01", "autor00", "libro03"));
		assertTrue(biblioteca.prestarLibro("usuario02", "autor00", "libro03"));
		
		assertEquals("{usuario01=2}", biblioteca.getUsuariosLibro("autor01", "libro01").toString());
		assertEquals("{usuario01=2, usuario02=2}", biblioteca.getUsuariosLibro("autor00", "libro01").toString());
		assertEquals("{}", biblioteca.getUsuariosLibro("autor02", "libro01").toString());
		assertEquals("{usuario02=1}", biblioteca.getUsuariosLibro("autor00", "libro02").toString());
		assertEquals("{usuario01=3, usuario02=3}", biblioteca.getUsuariosLibro("autor00", "libro03").toString());
		assertNull(biblioteca.getUsuariosLibro("autor03", "libro03"));
		
		assertTrue(biblioteca.prestarLibro("usuario01", "autor02", "libro01"));
		assertTrue(biblioteca.devolverLibro("usuario01", "autor02", "libro01"));
		assertTrue(biblioteca.prestarLibro("usuario01", "autor02", "libro01"));
		assertTrue(biblioteca.devolverLibro("usuario01", "autor02", "libro01"));
		assertTrue(biblioteca.prestarLibro("usuario02", "autor02", "libro01"));
		
		assertEquals("{usuario01=2, usuario02=1}", biblioteca.getUsuariosLibro("autor02", "libro01").toString());
	
		assertNull(biblioteca.getLibrosUsuario("usuario03"));
		assertNull(biblioteca.getLibrosUsuario("usuario30 "));
		assertEquals("[autor00 - libro01, autor00 - libro03, autor01 - libro01, autor02 - libro01]", biblioteca.getLibrosUsuario("usuario01").toString());
		assertEquals("[autor00 - libro01, autor00 - libro02, autor00 - libro03, autor02 - libro01]", biblioteca.getLibrosUsuario("usuario02").toString());

		biblioteca.clear();
		biblioteca = null;
	}

	@Test
	@Order(4)
	public void test04() {
		GestionBiblioteca biblioteca = new GestionBiblioteca("Picasso");
		ArrayList<String> usuariosId = new ArrayList<>(List.of("usuario00", "usuario01", "usuario02", "usuario03"));	
		ArrayList<String> autoresId = new ArrayList<>(List.of("a00", "a01", "a02", "a03"));	
		ArrayList<String> librosId = new ArrayList<>(List.of("l00", "l01", "l02", "l03"));
		
		
		for (int i=0; i<4; i++) {
			biblioteca.addLibro(new Libro(autoresId.get(i), librosId.get(i)));
			biblioteca.addUsuario(new Usuario(usuariosId.get(i)));
		}

		//Realizamos 1000 préstamos-devoluciones
		for (int i=0; i<1000; i++) {
			int pos = i%2 == 0 ? (i < 500 ? 0 : 2) : (i < 500 ? 1 : 3);
			assertTrue(biblioteca.prestarLibro(usuariosId.get(pos), autoresId.get(pos), librosId.get(pos)));
			assertTrue(biblioteca.devolverLibro(usuariosId.get(pos), autoresId.get(pos), librosId.get(pos)));
		}
		
		assertEquals("{}", biblioteca.getPrestamosActuales());
		
		String salidaEsperada= "usuario00: {a00 - l00=250, a01 - l01=0, a02 - l02=0, a03 - l03=0}\n" +
							   "usuario01: {a00 - l00=0, a01 - l01=250, a02 - l02=0, a03 - l03=0}\n" +
							   "usuario02: {a00 - l00=0, a01 - l01=0, a02 - l02=250, a03 - l03=0}\n" +
							   "usuario03: {a00 - l00=0, a01 - l01=0, a02 - l02=0, a03 - l03=250}\n";

		String salidaReal = "";
		for (Entry<String, TreeMap<String, Integer>> par: biblioteca) {
			salidaReal += par.getKey() + ": " + par.getValue().toString() + "\n";
		}

		assertEquals(salidaEsperada, salidaReal);

		//Añadimos a la biblioteca un usuario y dos libros más 
		usuariosId.add("usuario04");
		autoresId.add("a04");
		librosId.add("l04");
		autoresId.add("a05");
		librosId.add("l05");

		biblioteca.addLibro(new Libro(autoresId.get(4), librosId.get(4)));
		biblioteca.addLibro(new Libro(autoresId.get(5), librosId.get(5)));
		biblioteca.addUsuario(new Usuario(usuariosId.get(4)));

		//Los usuarios 0, 1, 2 y 3 toman prestados el libro 4, de manera que el usuario 4 no toma prestado ningún libro
		//El libro a05 - l05 no se toma prestado por ningún usuario
		assertTrue(biblioteca.prestarLibro(usuariosId.get(0), autoresId.get(4), librosId.get(4)));
		assertTrue(biblioteca.devolverLibro(usuariosId.get(0), autoresId.get(4), librosId.get(4)));
		assertTrue(biblioteca.prestarLibro(usuariosId.get(1), autoresId.get(4), librosId.get(4)));
		assertTrue(biblioteca.devolverLibro(usuariosId.get(1), autoresId.get(4), librosId.get(4)));
		assertTrue(biblioteca.prestarLibro(usuariosId.get(2), autoresId.get(4), librosId.get(4)));
		assertTrue(biblioteca.devolverLibro(usuariosId.get(2), autoresId.get(4), librosId.get(4)));
		assertTrue(biblioteca.prestarLibro(usuariosId.get(3), autoresId.get(4), librosId.get(4)));
		assertTrue(biblioteca.devolverLibro(usuariosId.get(3), autoresId.get(4), librosId.get(4)));
		 
		//Observa que esta vez ignoramos los títulos de los libros (para simplificar interpretación de la tabla)
		salidaEsperada= "usuario00: [250, 0, 0, 0, 1, 0]\n" +
				   		"usuario01: [0, 250, 0, 0, 1, 0]\n" +
				   		"usuario02: [0, 0, 250, 0, 1, 0]\n" +
				   		"usuario03: [0, 0, 0, 250, 1, 0]\n" +
				   		"usuario04: [0, 0, 0, 0, 0, 0]\n";

		salidaReal = "";
		
		
		for (Entry<String, TreeMap<String, Integer>> par: biblioteca) {
			salidaReal += par.getKey() + ": " + par.getValue().values().toString() + "\n";
		}

		assertEquals(salidaEsperada, salidaReal);
		
		usuariosId.clear();
		autoresId.clear();
		librosId.clear();
		biblioteca.clear();
		
		usuariosId = null;
		autoresId = null;
		librosId =  null;
		biblioteca = null;
	}
}