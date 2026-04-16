package practicas.practica01;

import practicas.practica01.Configuracion.TIPO_BARAJA;

public class Carta implements Comparable<Carta> {

	private final TIPO_BARAJA tipo;
	protected String valor;
	protected String palo;
	protected boolean repartida;

	public Carta(String tipo, String valor, String palo){
		try {		
			this.tipo = TIPO_BARAJA.valueOf(tipo);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Tipo no válido");
		}

		if(!(this.tipo.esValorValido(valor))) throw new IllegalArgumentException("Valor no válido para este tipo de baraja");
		this.valor = valor;

		if(!this.tipo.esPaloValido(palo)) throw new IllegalArgumentException("Palo no válido");
		this.palo = palo;
	} 

	public void setRepartida(boolean estado) {
		this.repartida = estado;
	}
	public boolean getRepartida() {
		return this.repartida;
	}

	@Override 
	public String toString() {
		return this.valor + " de " + this.palo;
	}

	@Override
	public int compareTo(Carta o) {
		int cmpPalo = this.tipo.getIndicePalo(this.palo).compareTo(o.tipo.getIndicePalo(o.palo));
		return cmpPalo != 0 ? cmpPalo : this.tipo.getIndiceValor(this.valor).compareTo((o.tipo.getIndiceValor(o.valor)));
	} 

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (this == o) return true;
		if (!(o instanceof Carta)) return false;
		return this.compareTo((Carta)o) == 0;
	}
}
