package practicas.practica02.parte01;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import practicas.auxiliar.AVLTree;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica02Parte01JUnit5Test {

	@Test
	@Order(0)
	public void test00(){
		PalabraFrecuencia palFreq01 = new PalabraFrecuencia("Hola");
		PalabraFrecuencia palFreq02 = new PalabraFrecuencia(palFreq01);

		assertEquals("hola <1>",palFreq01.toString());
		
		for (int i=0; i<1000; i++) {
			palFreq01.incrementaFreq();
		}
		
		assertEquals("hola <1001>",palFreq01.toString());
		
		assertTrue(palFreq01.compareTo(new PalabraFrecuencia("Adiós")) > 0);
		assertTrue(palFreq01.compareTo(new PalabraFrecuencia("hola")) == 0);//Cuidado con mayúsculas y minúsculas... --> uso del método toLowerCase()
		assertTrue(palFreq01.compareTo(new PalabraFrecuencia("zapaTo")) < 0);
		
		assertTrue(palFreq01.equals(new PalabraFrecuencia("HOla")));
		assertTrue(palFreq01.equals(new PalabraFrecuencia("    HOla    ")));//Cuidado con los espacios en blanco detrás y delante del texto --> uso del método trim()
		
		for (int i=0; i<20000; i++) {
			palFreq01 = new PalabraFrecuencia("Ya hemos terminado");
			palFreq01.incrementaFreq();
		}
		assertEquals("ya hemos terminado <2>",palFreq01.toString());
		
		//Haciendo uso de palFreq02, clon de palFreq01
		for (int i=0; i<1000; i++) {
			palFreq02.incrementaFreq();			
		}
		assertEquals("ya hemos terminado <2>",palFreq01.toString());
		assertEquals("hola <1001>",palFreq02.toString());
		palFreq01 = null;
		palFreq02 = null;
	}
	
	@Test
	@Order(1)
	public void test01(){
 		Dispositivo d01 = new Dispositivo("     iPhone 15"); 
		Dispositivo d02 = new Dispositivo(""); 
		Dispositivo d03 = new Dispositivo("Huawei Matepad 11.5");
		Dispositivo d04 = null;
		Dispositivo d05 = null;
		 
		assertEquals(d02, new Dispositivo("")); 
		
		d04 = new Dispositivo(d02);//Constructor copia
		
		assertEquals(d04, d02);
		
		assertEquals("iPhone 15 -> [empty]", d01.toString());
		assertEquals("noName -> [empty]", d02.toString());
		assertEquals("Huawei Matepad 11.5 -> [empty]", d03.toString());
		assertEquals("noName -> [empty]", d04.toString());
	
		d01.enviarMensaje("palabra01, palabra02, palabra03 ... palabra01, palabra02, palabra02, .... palabra02, palabra03");
		
		assertEquals("iPhone 15 -> [palabra01 <2>, palabra02 <4>, palabra03 <2>]", d01.toString());
		
		d01.enviarMensaje("palabra02, palabra02,,,,,,,palaBRA02, PaLaBrA02....., palabra02, palabra01, palabra01");
		
		assertEquals("iPhone 15 -> [palabra01 <4>, palabra02 <9>, palabra03 <2>]", d01.toString());
		
		d01.enviarMensaje("palabra00, palabra00,,,,,,,palaBRA00, PaLaBrA00....., palabra00, palabra00, palabra00");

		assertEquals("iPhone 15 -> [palabra00 <7>, palabra01 <4>, palabra02 <9>, palabra03 <2>]", d01.toString());
		
		d05 = new Dispositivo(d01); //Constructor copia. Ahora d05 y d01 son clones (pero cada uno con sus datos)
		assertEquals("iPhone 15 -> [palabra00 <7>, palabra01 <4>, palabra02 <9>, palabra03 <2>]", d01.toString());
		assertEquals("iPhone 15 -> [palabra00 <7>, palabra01 <4>, palabra02 <9>, palabra03 <2>]", d05.toString());

		d01.clear();		
		assertEquals("iPhone 15 -> [empty]", d01.toString());
		assertEquals("iPhone 15 -> [palabra00 <7>, palabra01 <4>, palabra02 <9>, palabra03 <2>]", d05.toString()); //¿Por qué? ¿No se supone que son clones?

		d01 = new Dispositivo(d05); //Vamos a seguir probando la funcionalidad del constructor copia
		
		d01.enviarMensaje("palabra00, palabra00, palabra00"); //No debería afectar al dispositivo d05
		assertEquals("iPhone 15 -> [palabra00 <10>, palabra01 <4>, palabra02 <9>, palabra03 <2>]", d01.toString());
		assertEquals("iPhone 15 -> [palabra00 <7>, palabra01 <4>, palabra02 <9>, palabra03 <2>]", d05.toString()); //¿Tu código te indica que la frecuencia de palabra00 es igual a 10? Revisa el constructor copia
		
		
		
		d01 = d05; //Asignación de referencias. Ahora d01 y d05 son sinónimos
		
		assertEquals("iPhone 15 -> [palabra00 <7>, palabra01 <4>, palabra02 <9>, palabra03 <2>]", d01.toString());
		assertEquals("iPhone 15 -> [palabra00 <7>, palabra01 <4>, palabra02 <9>, palabra03 <2>]", d05.toString());

		d01.clear();
		
		assertEquals("iPhone 15 -> [empty]", d01.toString());
		assertEquals("iPhone 15 -> [empty]", d05.toString()); //¿Por qué? ¿Qué ocurre con el constructor copia?

		//Vaciamos contenido
		d01.clear();
		d02.clear();
		d03.clear();
		d04.clear();
		d05.clear();
		
		assertEquals("iPhone 15 -> [empty]", d01.toString());
		assertEquals("noName -> [empty]", d02.toString());
		assertEquals("Huawei Matepad 11.5 -> [empty]", d03.toString());
		assertEquals("noName -> [empty]", d04.toString());
		
		d01 = d02 = d03 = d04 = d05 = null;
	}
	
	@Test
	@Order(2)
	public void test02(){
		Usuario u01 = new Usuario("@barbyLine");
		Usuario u02 = new Usuario("@vinicius");
		
		Dispositivo d01 = new Dispositivo("iPhone 15");
		Dispositivo d02 = new Dispositivo("iPhone 16");
		
		d01.enviarMensaje("palabra01, palabra02, palabra03"); //El dispositivo ha sido utilizado antes de ser asignado a un usuario...
		
		
		u01.addDispositivos(d01, d02, d01, d01, d02); //Añadimos 5 dispositivos al usuario u01
		
		assertTrue(u01.getNumDispositivos() == 2); //¿Por qué el usuario solo tiene 2 dispositivos si hemos insertado 5?
		
		u02.addDispositivos(d01, d02);  
		
		assertTrue(u02.getNumDispositivos() == 2); //Todo ok
		
		
		assertEquals("@barbyLine: \n" +
				   "\tiPhone 15 -> [palabra01 <1>, palabra02 <1>, palabra03 <1>]\n" +
				   "\tiPhone 16 -> [empty]\n",
				   u01.toString());
		assertEquals("@vinicius: \n" +
				   "\tiPhone 15 -> [palabra01 <1>, palabra02 <1>, palabra03 <1>]\n" +
				   "\tiPhone 16 -> [empty]\n",
				   u02.toString());
		
		d01.clear(); //¿Afectará esta operación a los dispositivos iPhone15 que tienen los ususarios?
		d02.clear();
		
		assertEquals("@barbyLine: \n" +
				   "\tiPhone 15 -> [palabra01 <1>, palabra02 <1>, palabra03 <1>]\n" +
				   "\tiPhone 16 -> [empty]\n",
				   u01.toString());
		assertEquals("@vinicius: \n" +
				   "\tiPhone 15 -> [palabra01 <1>, palabra02 <1>, palabra03 <1>]\n" +
				   "\tiPhone 16 -> [empty]\n",
				   u02.toString());
		
		
		d01.enviarMensaje("palabra01, palabra01, palabra01"); //¿Y esta operación?
		
		assertEquals("@barbyLine: \n" +
				   "\tiPhone 15 -> [palabra01 <1>, palabra02 <1>, palabra03 <1>]\n" +
				   "\tiPhone 16 -> [empty]\n",
				   u01.toString());
		assertEquals("@vinicius: \n" +
				   "\tiPhone 15 -> [palabra01 <1>, palabra02 <1>, palabra03 <1>]\n" +
				   "\tiPhone 16 -> [empty]\n",
				   u02.toString());
		
		assertEquals("iPhone 15 -> [palabra01 <3>]", d01.toString()); //¿Cuántos dispositivos iPhone 15 tengo en realidad?
		
		assertFalse(u01.enviarMensaje("iPhone 18", "...")); //Este dispositivo no existe
		assertTrue(u01.enviarMensaje("iPhone 15", "palabra01, palabra01, palabra03, palabra01"));
		assertTrue(u01.enviarMensaje("iPhone 16", "palabra01, palabra01...palabra00"));
		assertTrue(u02.enviarMensaje("iPhone 16", "palabra02, palabra02, palabra03, palabra02"));

		assertEquals("@barbyLine: \n" +
				   "\tiPhone 15 -> [palabra01 <4>, palabra02 <1>, palabra03 <2>]\n" +
				   "\tiPhone 16 -> [palabra00 <1>, palabra01 <2>]\n",
				   u01.toString());
		assertEquals("@vinicius: \n" +
				   "\tiPhone 15 -> [palabra01 <1>, palabra02 <1>, palabra03 <1>]\n" +
				   "\tiPhone 16 -> [palabra02 <3>, palabra03 <1>]\n",
				   u02.toString());
		
		//Método getNumPalabras()
		assertTrue(u01.getNumPalabras("iPhone 18") == -1);
		assertTrue(u01.getNumPalabras("iPhone 15") == 7);
		assertTrue(u01.getNumPalabras("iPhone 16") == 3);

		assertTrue(u02.getNumPalabras("iPhone15") == -1);
		
		assertTrue(u02.getNumPalabras("iPhone 15") == 3);
		assertTrue(u02.getNumPalabras("iPhone 16") == 4);

		//Método getPalabras() Cuidado con este método...no deberíamos modificar las frecuencias de las palabras que contienen los dispositivos de un usuario
		assertEquals("[palabra00 <1>, palabra01 <6>, palabra02 <1>, palabra03 <2>]", u01.getPalabras().toString());
		assertEquals("[palabra01 <1>, palabra02 <4>, palabra03 <2>]", u02.getPalabras().toString());
	
		//Si mostramos de nuevo el contenido de los objetos u01 y u02 nada debe haber cambiado...
		assertEquals("@barbyLine: \n" +
				   "\tiPhone 15 -> [palabra01 <4>, palabra02 <1>, palabra03 <2>]\n" + //Si tu código te indica que la frecuencia de palabra01 es igual a 6 --> revisa bien el método getPalabras()
				   "\tiPhone 16 -> [palabra00 <1>, palabra01 <2>]\n",
				   u01.toString());
		assertEquals("@vinicius: \n" +
				   "\tiPhone 15 -> [palabra01 <1>, palabra02 <1>, palabra03 <1>]\n" +
				   "\tiPhone 16 -> [palabra02 <3>, palabra03 <1>]\n",
				   u02.toString());
		
		u01.clear();
		u02.clear();
		assertEquals("@barbyLine: \n", u01.toString());
		assertEquals("@vinicius: \n", u02.toString());
		
		u01 = u02 = null;
		
		d01.clear();
		d02.clear();
		d01 = d02 = null;
	}
	

	@Test
	@Order(3)
	public void test03() {
		GestionUsuarios gestion = new GestionUsuarios();
			
		AVLTree<PalabraFrecuencia> arbol01 = new AVLTree<>();
		AVLTree<PalabraFrecuencia> arbol02 = new AVLTree<>();
		AVLTree<PalabraFrecuencia> arbol03 = new AVLTree<>();
		PalabraFrecuencia palabra01, palabra02, palabra03;
		
		arbol01.add(palabra01 = new PalabraFrecuencia("palabra01"));
		palabra01.setFrecuencia(25);
		
		arbol01.add(palabra02 = new PalabraFrecuencia("palabra02"));
		palabra02.setFrecuencia(10);
		
		arbol01.add(palabra03 = new PalabraFrecuencia("palabra03"));
		palabra03.setFrecuencia(15);

		arbol02.add(palabra01 = new PalabraFrecuencia("palabra01"));
		palabra01.setFrecuencia(15);
		
		arbol02.add(palabra02 = new PalabraFrecuencia("palabra02"));
		palabra02.setFrecuencia(30);
		
		assertEquals("0.04", gestion.getGradoSimilitud(arbol01, arbol02)); //¿Por qué 0.04? Si no sabes la respuesta, lee "de nuevo" el guion
		assertEquals(gestion.getGradoSimilitud(arbol01, arbol02), gestion.getGradoSimilitud(arbol02, arbol01));
		
		//Modificamos la frecuencia de una palabra
		palabra02.setFrecuencia(15);
		assertEquals("0.08", gestion.getGradoSimilitud(arbol01, arbol02));
		
		//Si los conjuntos no tienen ninguna palabra en común, debe devolver -1
		arbol03.add(new PalabraFrecuencia("Palabra200"));
		assertEquals("-1.00", gestion.getGradoSimilitud(arbol01, arbol03)); 

		//Si se trata del mismo conjunto, el grado de similitud será el valor máximo, es decir, igual a 1
		assertEquals("1.00", gestion.getGradoSimilitud(arbol01, arbol01)); 

		
		gestion.clear();
		gestion = null;
		
	}
	
	@Test
	@Order(4)
	public void test04() {
		GestionUsuarios gestion = new GestionUsuarios();
		Usuario u01 = new Usuario("bob");
		Usuario u02 = new Usuario("ned");
		Usuario u03 = new Usuario("edna");
		Usuario u04 = new Usuario("agnes");
		
		gestion.addDispositivos(u01, new Dispositivo("d01"), new Dispositivo("d02"), new Dispositivo("d03"));
		gestion.addDispositivos(u02, new Dispositivo("d01"), new Dispositivo("d02"));
		gestion.addDispositivos(u03, new Dispositivo("d01"));
		gestion.addDispositivos(u04, new Dispositivo("d01"));
		
		
		assertTrue(gestion.enviarMensaje("bob", "d01", "palabra01..palabra02...palabra01...palabra03...palabra04"));
		assertTrue(gestion.enviarMensaje("bob", "d02", "palabra03..palabra03...palabra03...palabra03...palabra04"));
		assertTrue(gestion.enviarMensaje("bob", "d03", "palabra01..palabra02...palabra01...palabra03...palabra04"));
		assertTrue(gestion.enviarMensaje("ned", "d01", "palabra01..palabra01...palabra03"));
		assertTrue(gestion.enviarMensaje("ned", "d02", "palabra01, palabra04, palabra05"));
		assertTrue(gestion.enviarMensaje("edna", "d01", "palabra04..palabra04...palabra05"));
		assertTrue(gestion.enviarMensaje("agnes", "d01", "palabra14"));

		assertEquals("[palabra01 <4>, palabra02 <2>, palabra03 <6>, palabra04 <3>]", u01.getPalabras().toString());
		assertEquals("[palabra01 <3>, palabra03 <1>, palabra04 <1>, palabra05 <1>]", u02.getPalabras().toString());
		assertEquals("[palabra04 <2>, palabra05 <1>]", u03.getPalabras().toString());
		assertEquals("[palabra14 <1>]", u04.getPalabras().toString());
		

		assertNull(gestion.getGradoSimilitud("apu"));
		assertEquals("ned vs...\n" +
				     "\tagnes: -1.00\n" +
				     "\tbob: 0.15\n" +
				     "\tedna: 0.50\n",
				     gestion.getGradoSimilitud("ned"));
		assertEquals("bob vs...\n" +
					  "\tagnes: -1.00\n" +
					  "\tedna: 0.50\n" +
					  "\tned: 0.15\n",
					  gestion.getGradoSimilitud("bob"));
		assertEquals("edna vs...\n" +
				     "\tagnes: -1.00\n" +
					 "\tbob: 0.50\n" +
					 "\tned: 0.50\n",
					 gestion.getGradoSimilitud("edna"));
		assertEquals("agnes vs...\n" +
			         "\tbob: -1.00\n" +
			         "\tedna: -1.00\n" +
			         "\tned: -1.00\n",
			         gestion.getGradoSimilitud("agnes"));
		

		gestion.clear();
		assertEquals("bob: \n", u01.toString());
		assertEquals("ned: \n", u02.toString());
		assertEquals("edna: \n", u03.toString());
		assertEquals("agnes: \n", u04.toString());		
	}
}