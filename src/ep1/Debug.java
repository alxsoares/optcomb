/**
 * 
 */
package ep1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import ep1.fluxoemredes.Simplex;
import ep1.fluxoemredes.estruturas.ArvoreSolucaoOtima;

/**
 * @author wanderley
 * 
 */
public class Debug {
	public String geraSaidaTexto (ArvoreSolucaoOtima aso, String titulo) {
		StringBuffer sb = new StringBuffer ();

		sb.append (titulo + "\n");
		sb
				.append ("======================================================================\n");

		sb.append ("Custos da solução viável:\n");
		sb.append ("-------------------------\n");

		int custo = 0;
		for (int u = 0; u < aso.getNumeroVertices (); u++) {
			for (int v = 0; v < aso.getNumeroVertices (); v++) {
				custo += aso.getCusto ()[u][v] * aso.getX ()[u][v];
			}
		}
		sb.append (custo + "\n");

		sb.append ("Arestas que estão na arvore:\n");
		sb.append ("----------------------------\n");

		for (int u = 0; u < aso.getNumeroVertices (); u++) {
			for (int v = 0; v < aso.getNumeroVertices (); v++) {
				if (aso.getNaArvore ()[u][v]) {
					sb.append (u + " " + v + " " + aso.getCusto ()[u][v] + "\n");
				}
			}
		}

		for (int u = 0; u < aso.getNumeroVertices (); u++) {
			sb.append ("y[" + u + "]: " + aso.getY ()[u] + "\tdemanda:"
					+ aso.getDemanda ()[u] + "\n");
		}
		sb.append ("x[][]:\n");
		for (int u = 0; u < aso.getNumeroVertices (); u++) {
			for (int v = 0; v < aso.getNumeroVertices (); v++) {
				String tmp = "" + aso.getX ()[u][v];
				while (tmp.length () < 4)
					tmp = " " + tmp;

				sb.append (tmp);
			}
			sb.append ("\n");
		}
		
		return sb.toString ();
	}

	public void geraArquivoPS (String nomeArquivo, String conteudo)
			throws IOException {

		PrintWriter pw = new PrintWriter (nomeArquivo + ".dot");
		pw.print (conteudo);
		pw.close ();

		Runtime.getRuntime ().exec ("./gerar.sh " + nomeArquivo);
		// Runtime.getRuntime ().exec ("gv " + nomeArquivo + ".ps");
	}
}
