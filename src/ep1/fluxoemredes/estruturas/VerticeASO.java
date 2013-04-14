/**
 * 
 */
package ep1.fluxoemredes.estruturas;


/**
 * Representa um vértice uma árvore de solução ótima.
 * 
 * @author wanderley
 * @author alex
 */
public class VerticeASO extends ep1.estruturas.Vertice {
	
	/** Demanda do vértice */
	private Integer demanda;
	
	/** Predecessor no grafo */
	private VerticeASO predecessor;
	
	/** Sucessor do vértice na árvore */
	private VerticeASO sucessor;
	
	/** Profundidade do vértice na árvore */
	private Integer profundidade;
 
	
	public VerticeASO () {
		super ();
		// TODO Auto-generated constructor stub
	}

	public VerticeASO (int id) {
		super (id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the demanda
	 */
	public Integer getDemanda () {
		return demanda;
	}

	/**
	 * @param demanda the demanda to set
	 */
	public void setDemanda (Integer demanda) {
		this.demanda = demanda;
	}

	/**
	 * @return the predecessor
	 */
	public VerticeASO getPredecessor () {
		return predecessor;
	}

	/**
	 * @param predecessor the predecessor to set
	 */
	public void setPredecessor (VerticeASO predecessor) {
		this.predecessor = predecessor;
	}

	/**
	 * @return the sucessor
	 */
	public VerticeASO getSucessor () {
		return sucessor;
	}

	/**
	 * @param sucessor the sucessor to set
	 */
	public void setSucessor (VerticeASO sucessor) {
		this.sucessor = sucessor;
	}

	/**
	 * @return the profundidade
	 */
	public Integer getProfundidade () {
		return profundidade;
	}

	/**
	 * @param profundidade the profundidade to set
	 */
	public void setProfundidade (Integer profundidade) {
		this.profundidade = profundidade;
	}
}
