package ep1.estruturas;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * Representa um vértice.
 * 
 * @author wanderley
 * @author alex
 */
public class Vertice {

	/** Identificação do vértice */
	private Integer id;

	/** Grafo que este vértice pertence */
	private Grafo grafo;

	/** Lista de vértices adjacentes */
	private List <Vertice> adjacentes;

	/**
	 * Construtor simples.
	 */
	public Vertice () {
		this.setAdjacentes (new LinkedList <Vertice> ());
	}
	
	/**
	 * Construtor que recebe um id
	 * @param id inteiro que identifica um vértice
	 */
	public Vertice (int id) {
		super ();
		this.setId (id);
	}
	
	@Override
	public boolean equals (Object obj) {
		Vertice that = (Vertice) obj;
		return this.getId ().equals (that.getId ());
	}
	
	/**
	 * 
	 * @param v
	 *           vértice
	 * @return verdadeiro se o vértice pertence a lista de adjacentes deste
	 *         vértice e falso caso contrário
	 */
	public Boolean ehAdjacente (Vertice v) {
		if (this.getAdjacentes ().contains (v)) {
			return true;
		}
		return false;
	}

	/**
	 * Recebe um vértice v, e adiciona v na lista de adjacentes deste vértice
	 * 
	 * @param v
	 *           vértice
	 */
	public void adicionaAdjacente (Vertice v) {
		this.getAdjacentes ().add (v);
	}

	/**
	 * @return lista de vértices adjacentes
	 */
	public List <Vertice> getAdjacentes () {
		return adjacentes;
	}

	/**
	 * @param adjacentes
	 *           seta lista de vértices adjacentes
	 */
	public void setAdjacentes (List <Vertice> adjacentes) {
		this.adjacentes = adjacentes;
	}

	/**
	 * @return o grafo que este vértice pertence
	 */
	public Grafo getGrafo () {
		return grafo;
	}

	/**
	 * @param grafo
	 *           atribui o grafo que este vértice pertence
	 */
	public void setGrafo (Grafo grafo) {
		this.grafo = grafo;
	}

	/**
	 * @return inteiro de identificação do grafo
	 */
	public Integer getId () {
		return id;
	}

	/**
	 * @param id
	 *           atribui um inteiro de identificação para o vértice
	 */
	public void setId (Integer id) {
		this.id = id;
	}
}
