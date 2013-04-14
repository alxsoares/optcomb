package ep1.estruturas;

import junit.framework.TestCase;

public class VerticeTeste extends TestCase {
	
	/**
	 * Este teste também serve para testar o método .adicionaAdjacente
	 */
	public void testEhAdjacente () {
		Vertice u = new Vertice ();
		Vertice v = new Vertice ();
		Vertice w = new Vertice ();
		
		u.setId (0);
		v.setId (1);
		w.setId (2);
		
		u.adicionaAdjacente (v);
		
		assertTrue (u.ehAdjacente (v));
		assertFalse (u.ehAdjacente (w));
	}
}
