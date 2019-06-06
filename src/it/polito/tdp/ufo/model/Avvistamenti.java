package it.polito.tdp.ufo.model;

public class Avvistamenti {
	
	private int anno;
	private int numero;
	
	public Avvistamenti(int anno, int numero) {
		super();
		this.anno = anno;
		this.numero = numero;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return String.format("Avvistamenti [anno=%s, numero=%s]", anno, numero);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anno;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avvistamenti other = (Avvistamenti) obj;
		if (anno != other.anno)
			return false;
		return true;
	}

}
