/**
 * 
 */
package ep1.fluxoemredes;

import ep1.Debug;
import ep1.fluxoemredes.estruturas.ArvoreSolucaoOtima;

/**
 * @author wanderley
 * @author alex
 */
public class Principal {
	public static void main (String[] args) throws Exception {
		ArvoreSolucaoOtima aso = ArvoreSolucaoOtima.construirGrafo (System.in);
		Simplex simplex = new Simplex ();
		simplex.encontraSolucaoOtima (aso);
		
		Debug debug = new Debug ();
		System.out.print (debug.geraSaidaTexto (aso, "Relatório"));
	}
}
