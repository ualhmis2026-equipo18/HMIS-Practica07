package practicas.practica02.parte01;

public class PalabraFrecuencia implements Comparable<PalabraFrecuencia> {
	public String palabra;
	public int frecuencia;
	
	public PalabraFrecuencia(String palabra) {
		this.palabra = palabra.trim().toLowerCase();
		this.frecuencia = 1;
	}
	
	public PalabraFrecuencia(PalabraFrecuencia otra) {
		this.palabra = otra.palabra;
		this.frecuencia = otra.frecuencia;
	}
	
	public void incrementaFreq() {
		this.frecuencia++;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}
	
	public int getFrecuencia() {
		return this.frecuencia;
	}
	
	@Override
	public String toString() {
		return this.palabra + " <" + this.frecuencia + ">";
	}
	
	@Override
	public boolean equals(Object otra) {
		if (otra == null) return false;
		if (this == otra) return true;
		if (!(otra instanceof PalabraFrecuencia)) return false;
		return this.compareTo((PalabraFrecuencia)otra) == 0;
	}
	
	@Override
	public int compareTo(PalabraFrecuencia otra) {
		return this.palabra.compareTo(otra.palabra);
	}
}
