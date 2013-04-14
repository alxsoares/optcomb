/**
 * 
 */
package ep1.fluxoemredes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ep1.Debug;
import ep1.estruturas.Aresta;
import ep1.estruturas.Problema;
import ep1.fluxoemredes.estruturas.ArvoreSolucaoOtima;
import ep1.fluxoemredes.exception.SimplexProblemaIlimitadoException;
import ep1.fluxoemredes.exception.SimplexProblemaInviavelException;

/**
 * @author wanderley
 * @author alex
 * 
 */
public class Simplex {

	private static final int NUMERO_CANDIDATOS = 20;

	/**
	 * Constroi a primeira solução viável para o problema
	 * 
	 * @throws CloneNotSupportedException
	 */
	public ArvoreSolucaoOtima construirSolucaoInicial (
			ArvoreSolucaoOtima original) throws CloneNotSupportedException {

		ArvoreSolucaoOtima aso = (ArvoreSolucaoOtima) original.clone ();
		aso.setRaiz (0);

		for (int u : aso.getVertices ()) {
			for (int v : aso.getVertices ()) {

				if (aso.getCusto ()[u][v] != ArvoreSolucaoOtima.INF) {
					aso.getCusto ()[u][v] = 0;
				}

				if (aso.getCusto ()[v][u] != ArvoreSolucaoOtima.INF) {
					aso.getCusto ()[v][u] = 0;
				}
			}
			Arrays.fill (aso.getX ()[u], 0);
		}
		Arrays.fill (aso.getY (), 0);

		int u = 0;
		aso.getY ()[u] = 0;

		for (int v = 1; v < aso.getNumeroVertices (); v++) {

			// Produtor
			if (aso.getDemanda ()[v] < 0) {
				if (aso.getCusto ()[v][u] == aso.INF) {
					aso.getCusto ()[v][u] = 1;
				}
				aso.getNaArvore ()[v][u] = true;
				aso.getX ()[v][u] = -aso.getDemanda ()[v];
				aso.getY ()[v] = -aso.getCusto ()[v][u]; //aso.getDemanda ()[v];
			}
			else {
				// Consumidor
				if (aso.getCusto ()[u][v] == aso.INF) {
					aso.getCusto ()[u][v] = 1;
				}
				aso.getNaArvore ()[u][v] = true;
				aso.getX ()[u][v] = aso.getDemanda ()[v];
				aso.getY ()[v] =  aso.getCusto ()[u][v]; //aso.getDemanda ()[v];
			}
		}
		aso.geraPredecessores (aso.getRaiz ());
		//aso.atualizaY (aso.getRaiz ());

		return aso;
	}

	public List<Problema> decompor (ArvoreSolucaoOtima inicial,
			ArvoreSolucaoOtima aso) throws SimplexProblemaInviavelException {

		LinkedList<Problema> problemas = new LinkedList<Problema> ();

		// tirando arestas com custo 1 e x 0 da árvore. isto vai gerar diversas
		// componentes

		for (int u : inicial.getVertices ()) {
			for (int v : inicial.getVertices ()) {
				if (inicial.getNaArvore ()[u][v] && inicial.getCusto ()[u][v] == 1) {
					if (inicial.getX ()[u][v] == 0) {
						inicial.getNaArvore ()[u][v] = false;
					}
					else {
						throw new SimplexProblemaInviavelException ();
					}
				}
			}
		}

		boolean[] visitado = new boolean[inicial.getNumeroVertices ()];
		boolean[] marcado = new boolean[inicial.getNumeroVertices ()];

		// descobrindo as componentes
		for (int u = 0; u < inicial.getNumeroVertices (); u++) {
			if (!visitado[u]) {

				Problema problema = new Problema ();

				problema.setRaiz (u);
				problema.setGrafo (aso);
				problema.setVertices (new LinkedList<Integer> ());

				Arrays.fill (marcado, false);

				expandirComponente (u, inicial, marcado);

				for (int v = 0; v < inicial.getNumeroVertices (); v++) {
					if (marcado[v]) {
						problema.getVertices ().add (v);
						visitado[v] = true;
					}
				}

				problemas.add (problema);
			}
		}

		return problemas;
	}

	private void expandirComponente (int u, ArvoreSolucaoOtima aso,
			boolean[] marcado) {
		marcado[u] = true;
		for (int v = 0; v < aso.getNumeroVertices (); v++) {
			if (!marcado[v]
					&& (aso.getNaArvore ()[u][v] || aso.getNaArvore ()[v][u])) {

				expandirComponente (v, aso, marcado);
			}
		}
	}

	public void encontraSolucaoOtima (ArvoreSolucaoOtima aso)
			throws SimplexProblemaIlimitadoException, IOException,
			CloneNotSupportedException, SimplexProblemaInviavelException {
		ArvoreSolucaoOtima viavel = encontraSolucaoViavel (aso);

		// Pegando arestas da solução inicial
		for (int u : viavel.getVertices ()) {
			aso.getY ()[u] = viavel.getY ()[u];
			for (int v : viavel.getVertices ()) {
				if (viavel.getNaArvore ()[u][v] && viavel.getCusto ()[u][v] == 0) {
					aso.getNaArvore ()[u][v] = true;
					aso.getX ()[u][v] = viavel.getX ()[u][v];
				}
			}
		}

		// Resolvendo os problemas
		aso.setNome ("Instancia_Original");
		List<Problema> problemas = decompor (viavel, aso);

		for (int i = 0; i < problemas.size (); i++) {
			Problema p = problemas.get (i);

			aso.setNome ("Instancia_Original_Problema" + (i + 1));
			aso.setVertices (p.getVertices ());

			aso.setRaiz (p.getRaiz ());
			aso.geraPredecessores (aso.getRaiz ());
			aso.atualizaY (aso.getRaiz ());

			resolvedor (aso);
		}

		// Grafo final
		for (int u = 0; u < aso.getNumeroVertices (); u++) {
			for (int v = 0; v < aso.getNumeroVertices (); v++) {
				if (aso.getCusto ()[u][v] != ArvoreSolucaoOtima.INF) {
					aso.getNaArvore ()[u][v] = true;
				}
			}
		}

		// adicionando todos os vértices na aso
		aso.setVertices (new LinkedList<Integer> ());
		for (int u = 0; u < aso.getNumeroVertices (); u++) {
			aso.getVertices ().add (u);
		}

		aso.setNome ("Grafo_Final");
		Debug dgb = new Debug ();
		dgb.geraArquivoPS ("Grafo_Final", aso.imprimirArvore (null));
	}

	public ArvoreSolucaoOtima encontraSolucaoViavel (ArvoreSolucaoOtima aso)
			throws SimplexProblemaIlimitadoException, IOException,
			CloneNotSupportedException {

		// Procurando a solução inicial

		ArvoreSolucaoOtima inicial = construirSolucaoInicial (aso);
		inicial.setNome ("Solucao_Inicial");

		resolvedor (inicial);

		return inicial;
	}

	public void resolvedor (ArvoreSolucaoOtima aso)
			throws SimplexProblemaIlimitadoException, IOException {

		StringBuffer debug = new StringBuffer ();
		int sufixo = 1;

		while (true) {
			List<Aresta> cand = candidatos (aso);
			if (cand.size () == 0) {
				break;
			}

			for (Aresta aresta : cand) {
				if (aso.calculaGama (aresta.getU (), aresta.getV ()) < 0) {
					debug.append (aso.imprimirArvore (aresta));
					// aso.imprimirArvore (sufixo, aresta, false);

					aso.atualizaArvore (aresta.getU (), aresta.getV ());

					// aso.imprimirArvore (sufixo++, null, true);
					debug.append (aso.imprimirArvore (null));
				}
			}
		}

		Debug dbg = new Debug ();
		dbg.geraArquivoPS (aso.getNome (), debug.toString ());
	}

	public List<Aresta> candidatos (ArvoreSolucaoOtima aso) {

		LinkedList<Aresta> ret = new LinkedList<Aresta> ();
		;

		outter: for (int u : aso.getVertices ()) {
			for (int v : aso.getVertices ()) {

				if (!aso.getNaArvore ()[u][v]
						&& aso.getCusto ()[u][v] != ArvoreSolucaoOtima.INF) {
					int gamma = aso.calculaGama (u, v);
					if (gamma < 0) {
						ret.add (new Aresta (u, v, gamma));
					}
				}

				if (ret.size () == Simplex.NUMERO_CANDIDATOS)
					break outter;
			}
		}
		Collections.sort (ret);

		return ret;
	}
}
