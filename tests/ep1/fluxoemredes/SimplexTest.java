/**
 * 
 */
package ep1.fluxoemredes;

import java.io.InputStream;
import java.util.Scanner;

import ep1.Debug;
import ep1.fluxoemredes.estruturas.ArvoreSolucaoOtima;
import ep1.fluxoemredes.exception.SimplexProblemaInviavelException;

import junit.framework.TestCase;

/**
 * @author wanderley
 * 
 */
public class SimplexTest extends TestCase {

	public void encontraSolucaoViavel (String caso) throws Exception {
		InputStream grafo = this.getClass ()
				.getResourceAsStream ("grafo." + caso);

		ArvoreSolucaoOtima aso = ArvoreSolucaoOtima.construirGrafo (grafo);
		Simplex simplex = new Simplex ();

		ArvoreSolucaoOtima inicial = simplex.encontraSolucaoViavel (aso);

		Debug dgb = new Debug ();
		System.out.print (dgb.geraSaidaTexto (aso, "Caso: grafo." + caso));

		assertTrue (inicial.custo () == 0);
	}

	public void testEncontraSolucaoViavel3 () throws Exception {
		encontraSolucaoViavel ("3");
	}

	public void testEncontraSolucaoViavel4 () throws Exception {
		encontraSolucaoViavel ("4");
	}

	public void encontraSolucaoOtima (String caso) throws Exception {
		InputStream grafo = this.getClass ()
				.getResourceAsStream ("grafo." + caso);
		InputStream saida = this.getClass ().getResourceAsStream (
				"testEncontraSolucaoOtima." + caso);

		ArvoreSolucaoOtima aso = ArvoreSolucaoOtima.construirGrafo (grafo);
		Simplex simplex = new Simplex ();

		simplex.encontraSolucaoOtima (aso);

		Debug dgb = new Debug ();
		System.out.print (dgb.geraSaidaTexto (aso, "Caso: grafo." + caso));

		Scanner scanfSaida = new Scanner (saida);

		int custoOtimo = scanfSaida.nextInt ();

		assertTrue (custoOtimo == aso.custo ());
	}

	public void testEncontraSolucaoOtima3 () throws Exception {
		encontraSolucaoOtima ("3");
	}

	public void testEncontraSolucaoOtima4 () throws Exception {
		encontraSolucaoOtima ("4");
	}

	public void testEncontraSolucaoOtima5 () throws Exception {
		encontraSolucaoOtima ("5");
	}

	public void testEncontraSolucaoOtima6 () throws Exception {

		try {
			encontraSolucaoOtima ("6");
		} catch (SimplexProblemaInviavelException e) {
			// ok
		}

	}

	public void GrafoBipartido (String caso) throws Exception {
		InputStream grafo = this.getClass ().getResourceAsStream (
				"grafo.bipartido." + caso);
		InputStream saida = this.getClass ().getResourceAsStream (
				"saida.grafo.bipartido." + caso);

		Scanner scanfSaida = new Scanner (saida);

		System.out.println ("Caso: grafo.bipartido." + caso);
		ArvoreSolucaoOtima aso = ArvoreSolucaoOtima.construirGrafo (grafo);
		Simplex simplex = new Simplex ();

		simplex.encontraSolucaoOtima (aso);

		int u = scanfSaida.nextInt (), v = scanfSaida.nextInt ();
		assertTrue (scanfSaida.nextInt () == aso.getX ()[u][v]);

	}

	public void testGrafoBipartido1 () throws Exception {
		GrafoBipartido ("1");
	}

	public void testGrafoBipartido2 () throws Exception {
		GrafoBipartido ("2");
	}

}
