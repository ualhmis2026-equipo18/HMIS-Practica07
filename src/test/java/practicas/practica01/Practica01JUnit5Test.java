 package practicas.practica01;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import practicas.practica01.Configuracion.TIPO_BARAJA;

public class Practica01JUnit5Test {
	@Test
	@Order(0)
	public void test00() { //Clase Configuración
		try {
			TIPO_BARAJA.valueOf("ESPAÑOLA");
		}catch(Exception e) {
			assertTrue(e.getMessage().contains("No enum constant")); //Este es el mensaje que nos da Java cuando intentamos inicializar un enumerado con una etiqueta no válida: Solo puede ser: Española o Francesa (inicial en mayúsculas)
		}
		try {
			TIPO_BARAJA.valueOf("Italiana");
		}catch(Exception e) {
			assertTrue(e.getMessage().contains("No enum constant"));
		}
		 
		practicas.practica01.Configuracion.TIPO_BARAJA tipo01 = TIPO_BARAJA.valueOf("Española");
		TIPO_BARAJA tipo02 = TIPO_BARAJA.Española;
		
		assertEquals(tipo01, tipo02); //Son dos formas distintas de asignación, pero equivalentes
		
		tipo01 = TIPO_BARAJA.Española;
		tipo02 = TIPO_BARAJA.Francesa;
		assertNotEquals(tipo01, tipo02);
		
		assertTrue(tipo01.esValorValido("As")); //Comprobamos que se trata de un valor válido para el tipo de baraja correspondiente a tipo01 (Española)
		assertFalse(tipo01.esValorValido("8"));
		assertTrue(tipo01.esValorValido("Rey"));
		
		assertTrue(tipo02.esValorValido("As")); //Comprobamos que se trata de un valor válido para el tipo de baraja correspondiente a tipo01 (Francesa)
		assertFalse(tipo02.esValorValido("Tres"));
		assertTrue(tipo02.esValorValido("Rey"));
		
		assertEquals("[Oros, Copas, Espadas, Bastos]", tipo01.getPalos().toString());
		assertEquals("[Corazones, Diamantes, Tréboles, Picas]", tipo02.getPalos().toString());
		
		
		assertEquals("[As, 2, 3, 4, 5, 6, 7, Sota, Caballo, Rey]", tipo01.getValores().toString());
		assertEquals("[As, 2, 3, 4, 5, 6, 7, 8, 9, 10, Sota, Reina, Rey]", tipo02.getValores().toString());
		
		assertTrue(tipo01.esPaloValido("Oros")); //Comprobamos que se trata de un valor válido para el tipo de baraja correspondiente a tipo01 (Española)
		assertFalse(tipo01.esPaloValido("vastos"));
		assertTrue(tipo01.esPaloValido("Bastos"));
		
		assertTrue(tipo02.esPaloValido("Tréboles")); //Comprobamos que se trata de un valor válido para el tipo de baraja correspondiente a tipo01 (Francesa)
		assertFalse(tipo02.esPaloValido("Treboles"));
		assertTrue(tipo02.esPaloValido("Picas"));
		
		
		assertTrue(tipo01.getIndicePalo("Oros") == 0); //Obtenemos la posición del palo especificado como parámetro de entrada dentro del array que contiene los posibles palos de la baraja
		assertTrue(tipo01.getIndicePalo("Espadas") == 2);
		
		assertTrue(tipo02.getIndicePalo("Picas") == 3);
		assertTrue(tipo02.getIndicePalo("Corazones") == 0);
		
		assertTrue(tipo01.getIndiceValor("As") == 0); //Obtenemos la posición del valor especificado como parámetro de entrada dentro del array de posibles valores de la baraja
		assertTrue(tipo01.getIndiceValor("Rey") == 9);
		
		assertTrue(tipo02.getIndiceValor("As") == 0);
		assertTrue(tipo02.getIndiceValor("Sota") == 10);	
	}
	
	@Test
	@Order(1)
	public void test01() { //Clase Carta (I)
		String[] valoresValidos = {"As", "3", "5", "Sota", "Caballo", "Rey"};
		String[] valoresNoValidos = {"-13", "-6", "0", "9", "8", "13", "15"};
		String[] palosValidos = {"Espadas", "Oros", "Bastos", "Copas"};
		String[] palosNoValidos = {"as", "ORos", "bastos", "coPas"};
		String[] tiposNoValidos =  {"EsPañola", "Inglesa", "Póker"};
		
		//Constructor
		for (String tipo: tiposNoValidos) {
			try {
				new Carta(tipo, "As", "Oros");
			}catch(Exception e) {
				assertEquals(e.getMessage(), "Tipo no válido");
			}	
		}
		for (String valor: valoresNoValidos) {
			try {
				new Carta("Española", valor, "As");
			}catch(Exception e) {
				assertEquals(e.getMessage(), "Valor no válido para este tipo de baraja");
			}	
		}
		for (String palo: palosNoValidos) {
			try {
				new Carta("Española", "As", palo); 
			}catch(Exception e) {
				assertEquals(e.getMessage(), "Palo no válido");
			}	
		}
		
		for (String numero: valoresValidos) {
			for (String palo: palosValidos) {
				assertNotNull(new Carta("Española", numero, palo)); 
			}
		}
		
		//Método toString()
		assertEquals("As de Oros", new Carta("Española", "As", "Oros").toString());
		assertEquals("Sota de Bastos", new Carta("Española", "Sota", "Bastos").toString());
		assertEquals("Caballo de Espadas", new Carta("Española", "Caballo", "Espadas").toString());
		assertEquals("Rey de Copas", new Carta("Española", "Rey", "Copas").toString());
		assertEquals("5 de Oros", new Carta("Española", "5", "Oros").toString());
		
		//Métodos equals() y compareTo()
		assertEquals(new Carta("Española", "As", "Oros"), new Carta("Española", "As", "Oros"));
		assertTrue(new Carta("Española", "As", "Oros").compareTo(new Carta("Española", "Rey", "Copas")) < 0); //Oros < Copas < Espadas < Bastos
		assertTrue(new Carta("Española", "Sota", "Espadas").compareTo(new Carta("Española", "As", "Bastos")) < 0);
		assertTrue(new Carta("Española", "As", "Oros").compareTo(new Carta("Española", "Rey", "Oros")) < 0);
		assertTrue(new Carta("Española", "As", "Oros").compareTo(new Carta("Española", "As", "Oros")) == 0); 
		assertTrue(new Carta("Española", "As", "Bastos").compareTo(new Carta("Española", "Rey", "Oros")) > 0); 
	}
	
	@SuppressWarnings("unlikely-arg-type") 
	@Test
	@Order(2)
	public void test02() { //Clase Carta (II)
		String[] valoresValidos = {"As", "3", "5", "10", "Sota", "Reina", "Rey"};
		String[] valoresNoValidos = {"-13", "-6", "0", "15"};
		String[] palosValidos = {"Tréboles", "Diamantes", "Corazones", "Picas"};
		String[] palosNoValidos = {"as", "ORos", "treboles", "picas"};
		
		for (String valor: valoresNoValidos) {
			try {
				new Carta("Francesa", valor, "Tréboles");
			}catch(Exception e) {
				assertEquals(e.getMessage(), "Valor no válido para este tipo de baraja");
			}	
		}
		for (String palo: palosNoValidos) {
			try {
				new Carta("Francesa", "As", palo);
			}catch(Exception e) {
				assertEquals(e.getMessage(), "Palo no válido");
			}	
		}
		
		for (String numero: valoresValidos) {
			for (String palo: palosValidos) {
				assertNotNull(new Carta("Francesa", numero, palo));
			} 
		}
		
		//Método toString()
		assertEquals("As de Tréboles", new Carta("Francesa", "As", "Tréboles").toString());
		assertEquals("Rey de Picas", new Carta("Francesa", "Rey", "Picas").toString());
		assertEquals("Reina de Diamantes", new Carta("Francesa", "Reina", "Diamantes").toString());
		assertEquals("9 de Corazones", new Carta("Francesa", "9", "Corazones").toString());
		
		//Método equals()
		assertFalse(new Carta("Francesa", "As", "Picas").equals(null));
		
		Carta carta = new Carta("Francesa", "As", "Picas"); 
		assertTrue(carta.equals(carta));
		assertFalse(carta.equals("carta")); //¿Cómo ves esto? Estamos comparando un objeto de tipo Carta con una cadena de texto... 
		
		assertEquals(new Carta("Francesa", "As", "Picas"), new Carta("Francesa", "As", "Picas"));
		
		//Método compareTo()
		assertTrue(new Carta("Francesa", "As", "Tréboles").compareTo(new Carta("Francesa", "Reina", "Picas")) < 0); //Corazones < Diamantes < Tréboles< Picas
		assertTrue(new Carta("Francesa", "10", "Diamantes").compareTo(new Carta("Francesa", "As", "Corazones")) > 0);
		assertTrue(new Carta("Francesa", "As", "Corazones").compareTo(new Carta("Francesa", "Reina", "Picas")) < 0);
		assertTrue(new Carta("Francesa", "As", "Tréboles").compareTo(new Carta("Francesa", "As", "Tréboles")) == 0); 
		assertTrue(new Carta("Francesa", "As", "Picas").compareTo(new Carta("Francesa", "Reina", "Tréboles")) > 0); 		
	}
	
	@Test
	@Order(3)
	public void test03() { //Clase Baraja (I)
		String barajaInicial = "[As de Oros, As de Copas, As de Espadas, As de Bastos, " +
		  				        "2 de Oros, 2 de Copas, 2 de Espadas, 2 de Bastos, " + 
						        "3 de Oros, 3 de Copas, 3 de Espadas, 3 de Bastos, " +
						        "4 de Oros, 4 de Copas, 4 de Espadas, 4 de Bastos, " +
						        "5 de Oros, 5 de Copas, 5 de Espadas, 5 de Bastos, " +
						        "6 de Oros, 6 de Copas, 6 de Espadas, 6 de Bastos, " +
						        "7 de Oros, 7 de Copas, 7 de Espadas, 7 de Bastos, " +
						        "Sota de Oros, Sota de Copas, Sota de Espadas, Sota de Bastos, " +
						        "Caballo de Oros, Caballo de Copas, Caballo de Espadas, Caballo de Bastos, "+
						        "Rey de Oros, Rey de Copas, Rey de Espadas, Rey de Bastos]";
		String barajaOrdenada = "[As de Oros, 2 de Oros, 3 de Oros, 4 de Oros, 5 de Oros, 6 de Oros, 7 de Oros, Sota de Oros, Caballo de Oros, Rey de Oros, " +
		  				         "As de Copas, 2 de Copas, 3 de Copas, 4 de Copas, 5 de Copas, 6 de Copas, 7 de Copas, Sota de Copas, Caballo de Copas, Rey de Copas, " + 
						         "As de Espadas, 2 de Espadas, 3 de Espadas, 4 de Espadas, 5 de Espadas, 6 de Espadas, 7 de Espadas, Sota de Espadas, Caballo de Espadas, Rey de Espadas, " +
						         "As de Bastos, 2 de Bastos, 3 de Bastos, 4 de Bastos, 5 de Bastos, 6 de Bastos, 7 de Bastos, Sota de Bastos, Caballo de Bastos, Rey de Bastos]"; 
		
		try {
			new Baraja("española");
		}catch(Exception e) {
			assertEquals("Tipo no válido", e.getMessage());
		}
		
		Baraja baraja = new Baraja("Española");
	
		assertTrue(baraja.size() == 40);
		assertEquals(barajaInicial, baraja.toString()); //Observa el orden en el que se crean las cartas cuando se inicializa un objeto de tipo Baraja
		 
		baraja.ordenar();
		
		assertEquals(barajaOrdenada, baraja.toString());// Observa ahora el criterio de orden natural establecido

		baraja.barajar(); //Desordenamos la baraja
		assertNotEquals(barajaOrdenada, baraja.toString());
		assertTrue(baraja.size() == 40);
		
		baraja.ordenar();

		//Método cortarBaraja()
		assertFalse(baraja.cortarBaraja(-1)); //Índice de corte no válido
		assertEquals(barajaOrdenada, baraja.toString()); //La baraja se queda como estaba
		
		assertFalse(baraja.cortarBaraja(0)); //Índice de corte no válido
		assertEquals(barajaOrdenada, baraja.toString()); //La baraja se queda como estaba
		
		assertFalse(baraja.cortarBaraja(40)); //Igual que antes
		assertEquals(barajaOrdenada, baraja.toString());
		
		
		assertTrue(baraja.cortarBaraja(7)); //Índice de corte = 7
		assertEquals("[Sota de Oros, Caballo de Oros, Rey de Oros, " +
				      "As de Copas, 2 de Copas, 3 de Copas, 4 de Copas, 5 de Copas, 6 de Copas, 7 de Copas, Sota de Copas, Caballo de Copas, Rey de Copas, " + 
				      "As de Espadas, 2 de Espadas, 3 de Espadas, 4 de Espadas, 5 de Espadas, 6 de Espadas, 7 de Espadas, Sota de Espadas, Caballo de Espadas, Rey de Espadas, " +
				      "As de Bastos, 2 de Bastos, 3 de Bastos, 4 de Bastos, 5 de Bastos, 6 de Bastos, 7 de Bastos, Sota de Bastos, Caballo de Bastos, Rey de Bastos, "+
				      "As de Oros, 2 de Oros, 3 de Oros, 4 de Oros, 5 de Oros, 6 de Oros, 7 de Oros]",
				       baraja.toString());
		
		baraja.ordenar();
		assertTrue(baraja.cortarBaraja(27)); //Índice de corte = 27
		assertEquals("[Sota de Espadas, Caballo de Espadas, Rey de Espadas, " +
			 	      "As de Bastos, 2 de Bastos, 3 de Bastos, 4 de Bastos, 5 de Bastos, 6 de Bastos, 7 de Bastos, Sota de Bastos, Caballo de Bastos, Rey de Bastos, " +
				      "As de Oros, 2 de Oros, 3 de Oros, 4 de Oros, 5 de Oros, 6 de Oros, 7 de Oros, Sota de Oros, Caballo de Oros, Rey de Oros, " +
			          "As de Copas, 2 de Copas, 3 de Copas, 4 de Copas, 5 de Copas, 6 de Copas, 7 de Copas, Sota de Copas, Caballo de Copas, Rey de Copas, " + 
			          "As de Espadas, 2 de Espadas, 3 de Espadas, 4 de Espadas, 5 de Espadas, 6 de Espadas, 7 de Espadas]",
  			           baraja.toString());
		
		
		assertTrue(baraja.cortarBaraja(27)); //Partiendo de la baraja anterior, volvemos a cortar con índice de corte = 27
		assertEquals("[5 de Copas, 6 de Copas, 7 de Copas, Sota de Copas, Caballo de Copas, Rey de Copas, " +
				      "As de Espadas, 2 de Espadas, 3 de Espadas, 4 de Espadas, 5 de Espadas, 6 de Espadas, 7 de Espadas, Sota de Espadas, Caballo de Espadas, Rey de Espadas, " +
		 	          "As de Bastos, 2 de Bastos, 3 de Bastos, 4 de Bastos, 5 de Bastos, 6 de Bastos, 7 de Bastos, Sota de Bastos, Caballo de Bastos, Rey de Bastos, " +
			          "As de Oros, 2 de Oros, 3 de Oros, 4 de Oros, 5 de Oros, 6 de Oros, 7 de Oros, Sota de Oros, Caballo de Oros, Rey de Oros, " +
			          "As de Copas, 2 de Copas, 3 de Copas, 4 de Copas]",
		               baraja.toString());

		assertTrue(baraja.cortarBaraja(39)); //Partiendo de la baraja anterior, volvemos a cortar con índice de corte = 39
		assertEquals("[4 de Copas, 5 de Copas, 6 de Copas, 7 de Copas, Sota de Copas, Caballo de Copas, Rey de Copas, " +
				      "As de Espadas, 2 de Espadas, 3 de Espadas, 4 de Espadas, 5 de Espadas, 6 de Espadas, 7 de Espadas, Sota de Espadas, Caballo de Espadas, Rey de Espadas, " +
		 	          "As de Bastos, 2 de Bastos, 3 de Bastos, 4 de Bastos, 5 de Bastos, 6 de Bastos, 7 de Bastos, Sota de Bastos, Caballo de Bastos, Rey de Bastos, " +
			          "As de Oros, 2 de Oros, 3 de Oros, 4 de Oros, 5 de Oros, 6 de Oros, 7 de Oros, Sota de Oros, Caballo de Oros, Rey de Oros, " +
			          "As de Copas, 2 de Copas, 3 de Copas]",
		               baraja.toString());
	
		//Método getCarta()
		assertNull(baraja.getCarta(-1)); //Posición no válida
		assertNull(baraja.getCarta(40)); //Posición no válida
		assertNotNull(baraja.getCarta(39)); //Posición perfectamente válida
		assertEquals("7 de Copas", baraja.getCarta(3).toString());
		
		baraja.clear(); //Limpiamos la baraja, es decir, quitamos todas sus cartas
		assertNull(baraja.getCarta(39)); //Ya no es una posición válida
		assertNull(baraja.getCarta(3));
		assertTrue(baraja.size() == 0);
		 
		assertFalse(baraja.cortarBaraja(35)); //No se puede cortar la baraja porque está vacía
		assertEquals("[]", baraja.toString());
		
		baraja = null;
	}
	
	@Test
	@Order(4)
	public void test04() { //Clase Baraja (II)
		String barajaInicial = "[As de Corazones, As de Diamantes, As de Tréboles, As de Picas, " +
								"2 de Corazones, 2 de Diamantes, 2 de Tréboles, 2 de Picas, " +
								"3 de Corazones, 3 de Diamantes, 3 de Tréboles, 3 de Picas, " +
								"4 de Corazones, 4 de Diamantes, 4 de Tréboles, 4 de Picas, " +
								"5 de Corazones, 5 de Diamantes, 5 de Tréboles, 5 de Picas, " +
								"6 de Corazones, 6 de Diamantes, 6 de Tréboles, 6 de Picas, " +
								"7 de Corazones, 7 de Diamantes, 7 de Tréboles, 7 de Picas, " +
								"8 de Corazones, 8 de Diamantes, 8 de Tréboles, 8 de Picas, " +
								"9 de Corazones, 9 de Diamantes, 9 de Tréboles, 9 de Picas, " +
								"10 de Corazones, 10 de Diamantes, 10 de Tréboles, 10 de Picas, " +
								"Sota de Corazones, Sota de Diamantes, Sota de Tréboles, Sota de Picas, " +
								"Reina de Corazones, Reina de Diamantes, Reina de Tréboles, Reina de Picas, " +
								"Rey de Corazones, Rey de Diamantes, Rey de Tréboles, Rey de Picas]"; 
		String barajaOrdenada = "[As de Corazones, 2 de Corazones, 3 de Corazones, 4 de Corazones, 5 de Corazones, 6 de Corazones, 7 de Corazones, 8 de Corazones, 9 de Corazones, 10 de Corazones, Sota de Corazones, Reina de Corazones, Rey de Corazones, " +
								 "As de Diamantes, 2 de Diamantes, 3 de Diamantes, 4 de Diamantes, 5 de Diamantes, 6 de Diamantes, 7 de Diamantes, 8 de Diamantes, 9 de Diamantes, 10 de Diamantes, Sota de Diamantes, Reina de Diamantes, Rey de Diamantes, " +
								 "As de Tréboles, 2 de Tréboles, 3 de Tréboles, 4 de Tréboles, 5 de Tréboles, 6 de Tréboles, 7 de Tréboles, 8 de Tréboles, 9 de Tréboles, 10 de Tréboles, Sota de Tréboles, Reina de Tréboles, Rey de Tréboles, " +
								 "As de Picas, 2 de Picas, 3 de Picas, 4 de Picas, 5 de Picas, 6 de Picas, 7 de Picas, 8 de Picas, 9 de Picas, 10 de Picas, Sota de Picas, Reina de Picas, Rey de Picas]";
		
		Baraja baraja = new Baraja("Francesa");
		assertTrue(baraja.size() == 52);
		assertEquals(barajaInicial, baraja.toString());
		
		baraja.ordenar();
		
		assertEquals(barajaOrdenada, baraja.toString());

		baraja.barajar();
		assertNotEquals(barajaOrdenada, baraja.toString());
		assertTrue(baraja.size() == 52);
		
		baraja.ordenar();
		
		assertFalse(baraja.cortarBaraja(-1));
		assertEquals(barajaOrdenada, baraja.toString());
		
		assertFalse(baraja.cortarBaraja(0));
		assertEquals(barajaOrdenada, baraja.toString());
		
		assertFalse(baraja.cortarBaraja(52));
		assertEquals(barajaOrdenada, baraja.toString());
		
		baraja.ordenar();
		assertTrue(baraja.cortarBaraja(27));
		assertEquals("[2 de Tréboles, 3 de Tréboles, 4 de Tréboles, 5 de Tréboles, 6 de Tréboles, 7 de Tréboles, 8 de Tréboles, 9 de Tréboles, 10 de Tréboles, Sota de Tréboles, Reina de Tréboles, Rey de Tréboles, " +
				      "As de Picas, 2 de Picas, 3 de Picas, 4 de Picas, 5 de Picas, 6 de Picas, 7 de Picas, 8 de Picas, 9 de Picas, 10 de Picas, Sota de Picas, Reina de Picas, Rey de Picas, " +
				      "As de Corazones, 2 de Corazones, 3 de Corazones, 4 de Corazones, 5 de Corazones, 6 de Corazones, 7 de Corazones, 8 de Corazones, 9 de Corazones, 10 de Corazones, Sota de Corazones, Reina de Corazones, Rey de Corazones, " +
			          "As de Diamantes, 2 de Diamantes, 3 de Diamantes, 4 de Diamantes, 5 de Diamantes, 6 de Diamantes, 7 de Diamantes, 8 de Diamantes, 9 de Diamantes, 10 de Diamantes, Sota de Diamantes, Reina de Diamantes, Rey de Diamantes, " +
			          "As de Tréboles]",
  			           baraja.toString());
		
			
		baraja.clear();
		
		assertTrue(baraja.size() == 0);
		
		assertFalse(baraja.cortarBaraja(35));
		assertEquals("[]", baraja.toString());
		
		baraja = null;
	}
	
	@Test
	@Order(5)
	public void test05() { //Clase Jugador
		Jugador jugador01 = new Jugador("@escacho");
		Jugador jugador02 = new Jugador("@rocher");
		
		Carta carta01 = new Carta("Española", "As", "Oros");
		Carta carta02 = new Carta("Española", "2", "Bastos");
		Carta carta03 = new Carta("Española", "As", "Espadas");
		Carta carta04 = new Carta("Española", "As", "Espadas"); //Las cartas carta03 y carta04 son objetos diferentes, pero...carta04.equals(carta03) == true, ¿verdad?
		
		assertTrue(jugador01.cogerCarta(carta01));
		assertTrue(jugador01.cogerCarta(carta02));
		assertTrue(jugador02.cogerCarta(carta03));
		
		assertFalse(jugador02.cogerCarta(carta02)); //Esta carta ya está repartida (la tiene el jugador01)
		assertFalse(jugador02.cogerCarta(carta03)); //No se puede añadir de nuevo la misma carta
		
		assertFalse(jugador02.cogerCarta(carta04)); //¿Por qué no puede cogerla si son objetos diferentes? ¿O son iguales?
		
		assertEquals("@escacho -> [As de Oros, 2 de Bastos]", jugador01.toString());
		assertEquals("@rocher -> [As de Espadas]", jugador02.toString()); 

		assertFalse(jugador01.devolverCarta(carta03)); //No puede devolverla porque no la tiene...
		assertFalse(jugador01.devolverCarta(new Carta("Española", "As", "Oros"))); // Y en este caso, ¿por qué no puede devolverla?
		
		assertTrue(carta01.getRepartida());
		assertFalse(new Carta("Española", "As", "Oros").getRepartida());
		 
		assertTrue(jugador01.devolverCarta(carta01));
		assertTrue(jugador02.devolverCarta(carta03));

		assertTrue(jugador02.cogerCarta(carta01));
		 
		assertEquals("@escacho -> [2 de Bastos]", jugador01.toString());
		assertEquals("@rocher -> [As de Oros]", jugador02.toString());
		
		carta01.setRepartida(false);
		assertFalse(jugador02.devolverCarta(carta01)); //¿Por qué no puede devolverla?
				
		jugador01.clear(); //Ya no tiene ninguna carta
		jugador02.clear(); //Ya no tiene ninguna carta
		
		assertEquals("@escacho -> []", jugador01.toString());
		assertEquals("@rocher -> []", jugador02.toString());
		
		jugador01 = jugador02 = null;
	}
	
	@Test
	@Order(6)
	public void test06() { //Clase Partida
		Jugador jugador01 = new Jugador("@escacho");
		Jugador jugador02 = new Jugador("@rocher");
		Jugador jugador03 = new Jugador("@quintais");
		Jugador jugador04 = new Jugador("@foyot");
		
		try {
			new Partida("Espanyola", jugador01, jugador02, jugador03);
		}catch(Exception e) {
			assertEquals("Tipo no válido", e.getMessage());
		}
		
		Partida partida01 = new Partida("Española", jugador01, jugador02, jugador03);
		
		assertTrue(partida01.size() == 3);
		assertEquals("Partida con baraja Española: [@escacho -> [], @rocher -> [], @quintais -> []]", partida01.toString());
		
		try {
			new Partida("Francesa", jugador01, jugador02, jugador03);
		}catch(Exception e) {
			assertEquals("El jugador " + jugador01.getId() + " ya pertenece a una partida", e.getMessage());
		}
		
		assertFalse(partida01.init(41)); //No se pueden repartir 41 cartas entre 3 jugadores...ya que solo tenemos 40 cartas
		
		assertTrue(partida01.init(9)); //9*3 = 27, ok
	
		for (Jugador jugador: partida01) {
			assertTrue(jugador.getOcupado());
		}
	
		assertTrue(partida01.size() == 3);
		assertTrue(partida01.getNumCartas() == 40);
		assertTrue(partida01.getNumCartasRepartidas() == 27);
		
		assertTrue(jugador01.size() == 9);
		assertTrue(jugador02.size() == 9);
		assertTrue(jugador03.size() == 9);
		
		//Vamos a ver quién es el jugador con la carta mayor
		Carta cartaMayor = new Carta("Española", "As", "Oros"); //Inicializamos cartaMayor comos la carta más pequeña
		Jugador ganador = null;
		
		for (Carta carta: jugador01) {
			if (carta.compareTo(cartaMayor) > 0) {
				cartaMayor = carta;
				ganador = jugador01;
			}
		}
		for (Carta carta: jugador02) {
			if (carta.compareTo(cartaMayor) > 0) {
				cartaMayor = carta;
				ganador = jugador02;
			}
		}
		for (Carta carta: jugador03) {
			if (carta.compareTo(cartaMayor) > 0) {
				cartaMayor = carta;
				ganador = jugador03;
			}
		}
		
		assertEquals(ganador, partida01.getManoGanadora());
		
		partida01.end(); //Finaliza la partida
		assertTrue(partida01.size() == 3); //Sigo teniendo 3 jugadores en estado "ocupado"
		assertTrue(jugador01.getOcupado());
		assertTrue(jugador02.getOcupado()); 
		assertTrue(jugador03.getOcupado());
		assertTrue(jugador01.size() == 0); //Pero ninguno tiene cartas ya que la partida ha terminado
		assertTrue(jugador02.size() == 0);
		assertTrue(jugador03.size() == 0);
		assertTrue(partida01.getNumCartasRepartidas() == 0);
		assertTrue(partida01.getNumCartas() == 40); 
		
		
		partida01.clear(); //Limpiamos partidas...ya ni cartas ni jugadores
		
		assertFalse(partida01.init(3)); 
		assertTrue(partida01.size() == 0);
		assertTrue(partida01.getNumCartas() == 0);
		

		// Igual, pero con partida02
		
		Partida partida02 = new Partida("Francesa", jugador04);

		assertTrue(jugador04.getOcupado());
		assertTrue(partida02.size() == 1);
		assertEquals("Partida con baraja Francesa: [@foyot -> []]", partida02.toString());
		
		assertFalse(partida02.init(-52));
		assertFalse(partida02.init(53)); 
		 
		assertTrue(partida02.init(52)); //número de cartas correcto
		
		assertTrue(partida02.getNumCartasRepartidas() == 52);
		assertEquals(jugador04, partida02.getManoGanadora());
				
		partida02.end();
		assertTrue(jugador04.getOcupado());
		assertTrue(partida02.size() == 1);
		assertTrue(partida02.getNumCartasRepartidas() == 0);
		
		assertTrue(partida02.init(3));
		
		assertTrue(partida02.getNumCartasRepartidas() == 3);
		
		assertFalse(partida02.init(6)); //No puedo iniciar una partida sin antes haberla acabado
		
		assertTrue(partida02.getNumCartasRepartidas()==3);

		partida02.clear();
		assertTrue(partida02.size() == 0);
		assertTrue(partida02.getNumCartas() == 0);
		assertTrue(partida02.getNumCartasRepartidas() == 0);
		
		assertFalse(partida02.init(5)); //Si no hay cartas ni jugadores, ¿cómo se va a poder iniciar una partida?
		
		jugador01 = jugador02 = jugador03 = jugador04 = null; 
	}
}