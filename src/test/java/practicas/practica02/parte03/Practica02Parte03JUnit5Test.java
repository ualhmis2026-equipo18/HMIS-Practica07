package practicas.practica02.parte03;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica02Parte03JUnit5Test {

	@Test
	@Order(0)
	public void test00(){
		GestionUsuarios gestion = new GestionUsuarios();
		
		gestion.addDispositivos("bob", "d01", "d02", "d03");
		gestion.addDispositivos("ned", "d01", "d02");
		gestion.addDispositivos("edna", "d01");
		gestion.addDispositivos("agnes", "d01");
		
		
		assertTrue(gestion.enviarMensaje("bob", "d01", "palabra01..palabra02...palabra01...palabra03...palabra04"));
		assertTrue(gestion.enviarMensaje("bob", "d02", "palabra03..palabra03...palabra03...palabra03...palabra04"));
		assertTrue(gestion.enviarMensaje("bob", "d03", "palabra01..palabra02.,,,,..    palabra01...palabra03...palabra04"));
		assertTrue(gestion.enviarMensaje("ned", "d01", "palabra01..palabra01...palabra03"));
		assertTrue(gestion.enviarMensaje("ned", "d02", "palabra01, palabra04, palabra05"));
		assertTrue(gestion.enviarMensaje("edna", "d01", "palabra04..palabra04...palabra05"));
		assertTrue(gestion.enviarMensaje("agnes", "d01", "palabra14"));

		assertEquals("agnes:\n" + 
			         "\td01 -> [palabra14]\n"+
			         "bob:\n" + 
			         "\td01 -> [palabra01, palabra02, palabra01, palabra03, palabra04]\n" +
			         "\td02 -> [palabra03, palabra03, palabra03, palabra03, palabra04]\n" +
			         "\td03 -> [palabra01, palabra02, palabra01, palabra03, palabra04]\n" +
			         "edna:\n" + 
			         "\td01 -> [palabra04, palabra04, palabra05]\n" +
			         "ned:\n" + 
			         "\td01 -> [palabra01, palabra01, palabra03]\n" +
			         "\td02 -> [palabra01, palabra04, palabra05]\n",
			         gestion.toString());

		assertEquals("[palabra01 <4>, palabra02 <2>, palabra03 <6>, palabra04 <3>]", gestion.getPalabras("bob").toString());
		assertEquals("[palabra01 <3>, palabra03 <1>, palabra04 <1>, palabra05 <1>]", gestion.getPalabras("ned").toString());
		assertEquals("[palabra04 <2>, palabra05 <1>]", gestion.getPalabras("edna").toString());
		assertEquals("[palabra14 <1>]", gestion.getPalabras("agnes").toString());
		
		assertTrue(gestion.getDispositivos("bob").size() == 3);
		assertTrue(gestion.getDispositivos("ned").size() == 2);
		assertTrue(gestion.getDispositivos("edna").size() == 1);
		assertTrue(gestion.getDispositivos("agnes").size() == 1);
		
		gestion.clear();
		
		assertNull(gestion.getDispositivos("bob"));
		assertNull(gestion.getDispositivos("ned"));
		assertNull(gestion.getDispositivos("edna"));
		assertNull(gestion.getDispositivos("agnes"));
	}
}