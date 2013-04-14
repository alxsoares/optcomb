package ep1.estruturas;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * Representação de grafos
 * 
 * @author wanderley
 * @author alex
 */
public class Grafo {

	private List <Integer> vertices;

	/**
	 * Construtor simples
	 */
	public Grafo () {
		this.setVertices (new LinkedList<Integer> ());
	}
	
	/**
	 * @return lista de vertices
	 */
	public List <Integer> getVertices () {
		return vertices;
	}

	/**
	 * @param vertices atribui uma lista de vertices
	 */
	public void setVertices (List <Integer> vertices) {
		this.vertices = vertices;
	}	
	
}
