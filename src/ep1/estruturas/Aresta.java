/**
 * 
 */
package ep1.estruturas;

/**
 * Representa uma aresta.
 * 
 * @author wanderley
 * @author alex
 */
public class Aresta implements Comparable<Aresta> {
	private int u, v, peso;

	/**
	 * Construtor simples
	 */
	public Aresta () {
	}

	/**
	 * Recebe dois vértice e cria uma aresta com eles.
	 * 
	 * @param u
	 *           vértice
	 * @param v
	 *           vértice
	 */
	public Aresta (int u, int v) {
		this.u = u;
		this.v = v;
	}

	public Aresta (int u, int v, int peso) {
		this.u = u;
		this.v = v;
		this.peso = peso;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	//@Override
	public int compareTo (Aresta that) {
		return this.getPeso () - that.getPeso ();
	}

	/**
	 * @return u
	 */
	public int getU () {
		return u;
	}

	/**
	 * @param u
	 *           o atributo u
	 */
	public void setU (int u) {
		this.u = u;
	}

	/**
	 * @return v
	 */
	public int getV () {
		return v;
	}

	/**
	 * @param v
	 *           o atributo v
	 */
	public void setV (int v) {
		this.v = v;
	}

	/**
	 * @return peso
	 */
	public int getPeso () {
		return peso;
	}

	/**
	 * @param peso
	 *           o atributo peso
	 */
	public void setPeso (int peso) {
		this.peso = peso;
	}

}
