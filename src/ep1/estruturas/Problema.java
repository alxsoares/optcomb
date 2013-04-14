/**
 * 
 */
package ep1.estruturas;

import java.util.List;

import ep1.fluxoemredes.estruturas.ArvoreSolucaoOtima;

/**
 * @author wanderley
 * 
 */
public class Problema {
	private ArvoreSolucaoOtima grafo;
	private List<Integer> vertices;
	private Integer raiz;
	
	/**
	 * @return grafo
	 */
	public ArvoreSolucaoOtima getGrafo () {
		return grafo;
	}
	/**
	 * @param grafo o atributo grafo
	 */
	public void setGrafo (ArvoreSolucaoOtima grafo) {
		this.grafo = grafo;
	}
	/**
	 * @return vertices
	 */
	public List<Integer> getVertices () {
		return vertices;
	}
	/**
	 * @param vertices o atributo vertices
	 */
	public void setVertices (List<Integer> vertices) {
		this.vertices = vertices;
	}
	/**
	 * @return raiz
	 */
	public Integer getRaiz () {
		return raiz;
	}
	/**
	 * @param raiz o atributo raiz
	 */
	public void setRaiz (Integer raiz) {
		this.raiz = raiz;
	}

}
