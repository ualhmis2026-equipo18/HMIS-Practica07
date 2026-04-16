 package practicas.practica03.parte02;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import practicas.auxiliar.Format;

import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica03Parte02JUnit5Test {

	String directorioEntrada = System.getProperty("user.dir") + File.separator +
			"src" + File.separator +
			"main" + File.separator +
			"java" + File.separator +
			"practicas" + File.separator +
			"practica03" + File.separator +
			"parte02" + File.separator;
			 
	@Test
	@Order(0)
	public void test00() {
		GestionCentros gestion = new GestionCentros();
		assertFalse(gestion.load("datos.txt"));
		assertTrue(gestion.load(directorioEntrada + "datos.txt"));
		assertTrue(gestion.size() == 4);
		
		//método toString()
		assertEquals("4 centros:\n" + 
					 "\tcentro01 -> 50 estudiantes y 336 asignaturas\n" +
					 "\tcentro02 -> 50 estudiantes y 357 asignaturas\n" + 
					 "\tcentro03 -> 50 estudiantes y 342 asignaturas\n" +
					 "\tcentro04 -> 50 estudiantes y 340 asignaturas\n", 
					 gestion.toString()); 
		
		gestion.clear();
		assertEquals("0 centros:\n", gestion.toString());
		
		gestion.add("centro1", "estudiante1", "asignatura1", 10.0, 8.0, 10.0);
		
		assertEquals("1 centro:\n" + 
					 "\tcentro1 -> 1 estudiante y 1 asignatura\n", 
					 gestion.toString());
		
		gestion.add("centro2", "estudiante1", "asignatura1", 3.0, 6.0, 6.9, 3.6);
		gestion.add("centro2", "estudiante2", "asignatura1", 5.0, 8.5, 9.9, 8.6, 5.5);
		
		assertEquals("2 centros:\n" +
				 	 "\tcentro1 -> 1 estudiante y 1 asignatura\n" +
				 	 "\tcentro2 -> 2 estudiantes y 2 asignaturas\n", 
				 	 gestion.toString());
		
		
		gestion.load(directorioEntrada + "datos.txt");
		//método getAsignaturasCentro()
		assertNull(gestion.getAsignaturasCentro("centro0"));
		assertEquals("[dtq, ist, jhd, jpr, ozq, pcf, usy, yha, yyz, zxj]", gestion.getAsignaturasCentro("centro01").toString());
		assertEquals("[ayi, cxg, gyy, jgc, jzo, kby, qdg, wni, woy, xqz]", gestion.getAsignaturasCentro("centro02").toString());
		assertEquals("[bio, ecm, iav, lkf, lnu, ppz, tqx, vcy, vjq, zsq]", gestion.getAsignaturasCentro("centro03").toString());
		assertEquals("[dgz, htf, htv, lud, nbi, pxx, qmx, spq, usz, xfh]", gestion.getAsignaturasCentro("centro04").toString());
		
		//Podemos ver que las asignaturas no se repiten en diferentes centros
		//método getEstudiantesAsignatura()
		assertNull(gestion.getEstudiantesAsignatura("eda1"));
		assertTrue(gestion.getEstudiantesAsignatura("bio").size() == 35);
		assertTrue(gestion.getEstudiantesAsignatura("xfh").size() == 36);
		assertTrue(gestion.getEstudiantesAsignatura("pcf").size() == 35);
		assertTrue(gestion.getEstudiantesAsignatura("ayi").size() == 35);
		
		//método getEstudiantesCentro
		assertNull(gestion.getEstudiantesCentro("centro00"));
		assertTrue(gestion.getEstudiantesCentro("centro01").size() == 50);
		assertTrue(gestion.getEstudiantesCentro("centro02").size() == 50);
		assertTrue(gestion.getEstudiantesCentro("centro03").size() == 50);
		assertTrue(gestion.getEstudiantesCentro("centro04").size() == 50);
		
		//método getNotaMediaCentro()
		assertNull(gestion.getNotaMediaCentro("centro00"));
		assertEquals("5.03", gestion.getNotaMediaCentro("centro01"));
		assertEquals("5.08", gestion.getNotaMediaCentro("centro02"));
		assertEquals("4.91", gestion.getNotaMediaCentro("centro03"));
		assertEquals("4.95", gestion.getNotaMediaCentro("centro04"));
		
		//método getNotaMediaEstudiante()
		assertNull(gestion.getNotaMediaEstudiante("Amelia"));
		assertEquals("5.33", gestion.getNotaMediaEstudiante("xgc047"));
		assertEquals("5.44", gestion.getNotaMediaEstudiante("rcx732"));
		assertEquals("5.45", gestion.getNotaMediaEstudiante("pwd770"));
		
		//método getNotaMediaAsignatura()
		assertNull(gestion.getNotaMediaAsignatura("eda1"));
		assertEquals("4.82", gestion.getNotaMediaAsignatura("bio"));
		assertEquals("4.81", gestion.getNotaMediaAsignatura("pcf"));
		assertEquals("4.89", gestion.getNotaMediaAsignatura("ayi"));
		
		//Iteramos sobre GestionCentros... ¿es iterable la clase GestionCentros?
		ArrayList<Double> aux = new ArrayList<>();
		for(Entry<String, TreeMap<String, ArrayList<Double>>> par: gestion) {
			for(ArrayList<Double> notas: par.getValue().values()) {
				aux.addAll(notas);
			}
			assertEquals(gestion.getNotaMediaCentro(par.getKey()), Format.formatDouble(MyMath.calculaMedia(aux)));
			aux.clear();
		}
		
		//Nos aseguramos de que se cumplen las dos restricciones principales: (1) 1 alumno pertenece a 1 centro; (2) 1 asignatura pertenece a 1 centro
		assertTrue(gestion.checkEstudiantes());
		assertTrue(gestion.checkAsignaturas());
		
		gestion.clear();
		
		assertNull(gestion.getNotaMediaEstudiante("xgc047"));
		assertNull(gestion.getNotaMediaAsignatura("bio"));
		assertNull(gestion.getNotaMediaCentro("centro1"));
		assertNull(gestion.getEstudiantesAsignatura("bio"));
		assertNull(gestion.getEstudiantesCentro("centro01"));
		
		
		gestion = null;
	}
}