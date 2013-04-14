/**
 * 
 */
package ep1.fluxoemredes.estruturas;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import ep1.estruturas.Aresta;
import ep1.estruturas.Grafo;
import ep1.fluxoemredes.exception.SimplexProblemaIlimitadoException;

/**
 * Representa uma Árvore de Solução Ótima (ASO).
 * 
 * @author wanderley
 * @author alex
 */
public class ArvoreSolucaoOtima extends Grafo {

	public final static int INF = Integer.MAX_VALUE / 4;

	private int numeroVertices;
	private int[][] custo;
	private boolean[][] naArvore;
	private int[] predecessor;
	private int[] sucessor;
	private int[] profundidade;
	private int[] demanda;
	private int[][] x;
	private int[] y;
	private int[] rotulo;
	private int[] naArvoreVertices;
	private int rotuloAtual;
	private int paiComum;
	private int raiz;

	private String nome;

	/**
	 * Recebe dois vértice u e v de um grafo G e devolve um caminho que vai de u
	 * até v em G.
	 * 
	 * @param u
	 *           vértice
	 * @param v
	 *           vértice
	 * @return devolve um caminho entre u e v no grafo que ambos estão contidos.
	 */
	public List<Integer> caminho (int u, int v) {
		LinkedList<Integer> ret = new LinkedList<Integer> ();

		LinkedList<Integer> caminhoU = new LinkedList<Integer> ();
		LinkedList<Integer> caminhoV = new LinkedList<Integer> ();

		int ui = u, vi = v;

		/*
		 * Vamos deixar os vértices no mesmo nível.
		 * 
		 * Para facilitar o restante do método o caminhoV contém o caminho de
		 * predecessorComum (u,v) até v.
		 */
		while (profundidade[ui] > profundidade[vi]) {
			caminhoU.addLast (ui);
			ui = predecessor[ui];
		}
		while (profundidade[vi] > profundidade[ui]) {
			caminhoV.addFirst (vi);
			vi = predecessor[vi];
		}

		/* Vamos encontrar o ancestral comum. */
		while (ui != vi) {
			caminhoU.addLast (ui);
			caminhoV.addFirst (vi);

			ui = predecessor[ui];
			vi = predecessor[vi];
		}
		ret.addAll (caminhoU);
		// Adicionando o ancestral comum
		ret.addLast (ui);
		ret.addAll (caminhoV);

		paiComum = ui;

		return ret;
	}

	/**
	 * Recebe um inteiro e gera uma classe com n vértices.
	 * 
	 * @param n
	 *           número de vértices do grafo
	 */
	public ArvoreSolucaoOtima (int n) {
		numeroVertices = n;
		custo = new int[n][n];
		naArvore = new boolean[n][n];
		predecessor = new int[n];
		sucessor = new int[n];
		profundidade = new int[n];
		demanda = new int[n];
		x = new int[n][n];
		y = new int[n];
		rotulo = new int[n];

		Arrays.fill (predecessor, -1);
		Arrays.fill (profundidade, -1);
		Arrays.fill (demanda, 0);
		Arrays.fill (rotulo, 0);

		this.setVertices (new LinkedList<Integer> ());

		for (int i = 0; i < n; i++) {
			Arrays.fill (custo[i], INF);
			Arrays.fill (x[i], 0);

			this.getVertices ().add (i);
		}
	}

	/**
	 * Construtor simples
	 */
	public ArvoreSolucaoOtima () {
		super ();
	}

	/**
	 * Formato da entrada:
	 * 
	 * n m [n = # de vértices, # de arestas] d_0 d_1 .. d_n [demandas] u v c
	 * [aresta uv custo c; nas m linhas seguintes]
	 * 
	 * @param is
	 * @throws Exception
	 */
	public static ArvoreSolucaoOtima construirGrafo (InputStream is)
			throws Exception {
		Scanner scanf = new Scanner (is);

		int n, m, d, u, v, c;

		n = scanf.nextInt ();
		m = scanf.nextInt ();

		ArvoreSolucaoOtima aso = new ArvoreSolucaoOtima (n);

		// demandas
		for (int i = 0; i < n; i++) {
			aso.getDemanda ()[i] = scanf.nextInt ();
		}

		// arestas
		for (int i = 0; i < m; i++) {
			u = scanf.nextInt ();
			v = scanf.nextInt ();
			aso.getCusto ()[u][v] = scanf.nextInt ();
		}

		return aso;
	}

	/**
	 * @return numeroVertices
	 */
	public int getNumeroVertices () {
		return numeroVertices;
	}

	/**
	 * @param numeroVertices
	 *           o atributo numeroVertices
	 */
	public void setNumeroVertices (int numeroVertices) {
		this.numeroVertices = numeroVertices;
	}

	/**
	 * @return custo
	 */
	public int[][] getCusto () {
		return custo;
	}

	/**
	 * @param custo
	 *           o atributo custo
	 */
	public void setCusto (int[][] custo) {
		this.custo = custo;
	}

	/**
	 * @return predecessor
	 */
	public int[] getPredecessor () {
		return predecessor;
	}

	/**
	 * @param predecessor
	 *           o atributo predecessor
	 */
	public void setPredecessor (int[] predecessor) {
		this.predecessor = predecessor;
	}

	/**
	 * @return profundidade
	 */
	public int[] getProfundidade () {
		return profundidade;
	}

	/**
	 * @param profundidade
	 *           o atributo profundidade
	 */
	public void setProfundidade (int[] profundidade) {
		this.profundidade = profundidade;
	}

	/**
	 * @return demanda
	 */
	public int[] getDemanda () {
		return demanda;
	}

	/**
	 * @param demanda
	 *           o atributo demanda
	 */
	public void setDemanda (int[] demanda) {
		this.demanda = demanda;
	}

	/**
	 * Recebe um vértice que será raiz. Preenche o vetor de predecessores e
	 * profundidade.
	 * 
	 * @param raiz
	 *           vértice
	 */
	public void geraPredecessores (int raiz) {

		for (int u : this.getVertices ()) {
			predecessor[u] = -1;
			sucessor[u] = -1;
		}

		predecessor[raiz] = raiz;
		profundidade[raiz] = 0;
		rotulo[raiz] = 0;

		rotuloAtual = 0;
		sucessor[dfs (raiz, 0, -1)] = raiz;

		// for (int i = 0; i < this.numeroVertices; i++) {
		// System.out.println (i + " " + predecessor[i] + " " + sucessor[i] + " "
		// + profundidade[i]);
		// }
	}

	/**
	 * Executa uma busca em profundidade marcando preenchendo os predecessores
	 * dos vértices.
	 * 
	 * @param u
	 *           vértice
	 */
	private int dfs (int u, int prof, int ultimo) {
		boolean ehFolha = true;

		if (ultimo != -1 && sucessor[ultimo] == -1) {
			sucessor[ultimo] = u;
		}
		ultimo = u;

		rotulo[u] = rotuloAtual++;
		for (int v : this.getVertices ()) {
			if (predecessor[v] == -1 && (naArvore[u][v] || naArvore[v][u])) {
				ehFolha = false;

				predecessor[v] = u;
				profundidade[v] = prof + 1;

				ultimo = dfs (v, prof + 1, ultimo);
			}
		}

		if (ehFolha)
			return u;
		return ultimo;
	}

	/**
	 * Apenas inicializa o vetor naArvore com os custos do grafo.
	 */
	public void inicializaNaArvore () {
		for (int i : this.getVertices ()) {
			Arrays.fill (naArvore[i], false);
		}

		for (int u : this.getVertices ()) {
			for (int v : this.getVertices ()) {
				if (custo[u][v] != INF)
					naArvore[u][v] = true;
				if (custo[v][u] != INF)
					naArvore[v][u] = true;
			}
		}
	}

	/**
	 * Recebe dois vértices u e v e um paiComum na árvore de (naArvore) e devolve
	 * a aresta que deve sair.
	 * 
	 * @param u
	 *           vértice
	 * @param v
	 *           vértice
	 * @param paiComum
	 *           vértice
	 * @param caminho
	 *           caminho de u a v em T (naArvore)
	 * @return aresta que vai sair de T (naArvore)
	 * @throws SimplexProblemaIlimitadoException
	 */
	public Aresta encontraArestaSaida (int u, int v, int paiComum,
			List<Integer> caminho) throws SimplexProblemaIlimitadoException {

		int tu = u, tv = v;
		Aresta minimo = new Aresta ();
		minimo.setU (-1);
		minimo.setV (-1);
		minimo.setPeso (Integer.MAX_VALUE);

		boolean fUPai = false;

		while (u != paiComum) {
			if (naArvore[u][predecessor[u]]
					&& x[u][predecessor[u]] < minimo.getPeso ()) {
				minimo.setPeso (x[u][predecessor[u]]);
				minimo.setU (u);
				minimo.setV (predecessor[u]);
				fUPai = true;
			}
			u = predecessor[u];
		}

		while (v != paiComum) {
			if (naArvore[predecessor[v]][v]
					&& x[predecessor[v]][v] < minimo.getPeso ()) {
				minimo.setPeso (x[predecessor[v]][v]);
				minimo.setU (predecessor[v]);
				minimo.setV (v);
				fUPai = false;
			}
			v = predecessor[v];
		}

		if (minimo.getPeso () == INF) {
			throw new SimplexProblemaIlimitadoException ("Problema Ilimitado");
		}
		else {
			u = tu;
			v = tv;

			while (u != paiComum) {
				if (naArvore[u][predecessor[u]]) {
					x[u][predecessor[u]] -= minimo.getPeso ();
				}
				else {
					x[predecessor[u]][u] += minimo.getPeso ();
				}
				u = predecessor[u];
			}

			while (v != paiComum) {
				if (naArvore[predecessor[v]][v]) {
					x[predecessor[v]][v] -= minimo.getPeso ();
				}
				else {
					x[v][predecessor[v]] += minimo.getPeso ();
				}
				v = predecessor[v];
			}

			if (fUPai) {
				x[tu][tv] = minimo.getPeso ();
			}
			else {
				x[tu][tv] = minimo.getPeso ();
			}

		}

		return minimo;
	}

	/**
	 * Recebe aresta uv e w e atuliza a árvore para T - w + uv (naArvore)
	 * 
	 * @param u
	 *           vértice
	 * @param v
	 *           vértice
	 * @param w
	 *           aresta
	 * @throws SimplexProblemaIlimitadoException
	 */

	public void atualizaArvore (int u, int v)
			throws SimplexProblemaIlimitadoException {

		int gama = calculaGama (u, v);
		List<Integer> caminho = caminho (u, v);
		Aresta f = encontraArestaSaida (u, v, paiComum, caminho);

		boolean fUPai = false;
		{
			int x = u;
			while (x != paiComum) {
				if (f.getU () == x && f.getV () == predecessor[x])
					fUPai = true;
				x = predecessor[x];
			}

		}

		int f1, f2, aux;
		f1 = f.getU ();
		f2 = f.getV ();

		if (profundidade[f1] == profundidade[f2] + 1) {
			f1 = f.getV ();
			f2 = f.getU ();
		}

		int k = f2;
		int var;

		// e estah no sentido da raiz
		if (fUPai) {
			var = -gama;
		}
		else {
			var = gama;
		}

		do {
			y[k] = y[k] + var;
			k = sucessor[k];
		} while (profundidade[k] > profundidade[f2]);

		// tirando a aresta da árvore
		naArvore[f.getV ()][f.getU ()] = naArvore[f.getU ()][f.getV ()] = false;

		// adiconando nova aresta na árvore
		naArvore[u][v] = true;

		atualizaArvore ();

	}

	public int calculaGama (int u, int v) {
		return custo[u][v] + y[u] - y[v];
	}

	/**
	 * Atualiza predecessores, sucessores etc dos vértices da árvore.
	 */
	public void atualizaArvore () {
		geraPredecessores (raiz);
	}

	/**
	 * @return sucessor
	 */
	public int[] getSucessor () {
		return sucessor;
	}

	/**
	 * @param sucessor
	 *           o atributo sucessor
	 */
	public void setSucessor (int[] sucessor) {
		this.sucessor = sucessor;
	}

	/**
	 * @return raiz
	 */
	public int getRaiz () {
		return raiz;
	}

	/**
	 * @param raiz
	 *           o atributo raiz
	 */
	public void setRaiz (int raiz) {
		this.raiz = raiz;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone () throws CloneNotSupportedException {
		ArvoreSolucaoOtima aso = new ArvoreSolucaoOtima (this.numeroVertices);

		aso.numeroVertices = this.numeroVertices;
		for (int i = 0; i < this.numeroVertices; i++) {

			aso.custo[i] = new int[this.numeroVertices];
			aso.naArvore[i] = new boolean[this.numeroVertices];
			aso.x[i] = new int[this.numeroVertices];

			for (int j = 0; j < this.numeroVertices; j++) {
				aso.custo[i][j] = this.custo[i][j];
				aso.naArvore[i][j] = this.naArvore[i][j];
				aso.x[i][j] = this.x[i][j];
			}

		}

		aso.predecessor = new int[this.numeroVertices];
		aso.sucessor = new int[this.numeroVertices];
		aso.profundidade = new int[this.numeroVertices];
		aso.demanda = new int[this.numeroVertices];
		aso.y = new int[this.numeroVertices];
		aso.rotulo = new int[this.numeroVertices];

		for (int i = 0; i < this.numeroVertices; i++) {
			aso.predecessor[i] = this.predecessor[i];
			aso.sucessor[i] = this.sucessor[i];
			aso.profundidade[i] = this.profundidade[i];
			aso.demanda[i] = this.demanda[i];
			aso.y[i] = this.y[i];
			aso.rotulo[i] = this.rotulo[i];
		}

		aso.rotuloAtual = this.rotuloAtual;
		aso.paiComum = this.paiComum;
		aso.raiz = this.raiz;

		return aso;
	}

	/**
	 * @return naArvore
	 */
	public boolean[][] getNaArvore () {
		return naArvore;
	}

	/**
	 * @param naArvore
	 *           o atributo naArvore
	 */
	public void setNaArvore (boolean[][] naArvore) {
		this.naArvore = naArvore;
	}

	/**
	 * @return x
	 */
	public int[][] getX () {
		return x;
	}

	/**
	 * @param x
	 *           o atributo x
	 */
	public void setX (int[][] x) {
		this.x = x;
	}

	/**
	 * @return y
	 */
	public int[] getY () {
		return y;
	}

	/**
	 * @param y
	 *           o atributo y
	 */
	public void setY (int[] y) {
		this.y = y;
	}

	/**
	 * @return rotulo
	 */
	public int[] getRotulo () {
		return rotulo;
	}

	/**
	 * @param rotulo
	 *           o atributo rotulo
	 */
	public void setRotulo (int[] rotulo) {
		this.rotulo = rotulo;
	}

	/**
	 * @return rotuloAtual
	 */
	public int getRotuloAtual () {
		return rotuloAtual;
	}

	/**
	 * @param rotuloAtual
	 *           o atributo rotuloAtual
	 */
	public void setRotuloAtual (int rotuloAtual) {
		this.rotuloAtual = rotuloAtual;
	}

	/**
	 * @return paiComum
	 */
	public int getPaiComum () {
		return paiComum;
	}

	/**
	 * @param paiComum
	 *           o atributo paiComum
	 */
	public void setPaiComum (int paiComum) {
		this.paiComum = paiComum;
	}

	public String imprimirArvore (Aresta uv) throws IOException {
		StringBuffer sb = new StringBuffer ();

		sb.append ("digraph " + this.getNome () + " \n{\n");
		sb.append ("size=\"5,3\";\n");
		sb.append ("ratio=fill;\n");
		sb.append ("root=" + this.getRaiz () + ";\n");

		for (int u : this.getVertices ()) {
			sb.append (u + " [label=\"(" + u + ") d:" + demanda[u] + " y:" + y[u]
					+ "\"" + (this.getRaiz () == u ? ", style=filled, color=\"#EFADAD\"];" : "") + "];\n");
		}

		for (int u : this.getVertices ()) {
			for (int v : this.getVertices ()) {
				if (naArvore[u][v]) {
					sb.append (u + "->" + v + "[label=\"c:" + custo[u][v] + " x:"
							+ x[u][v] + "\"];\n");
				}
			}
		}
		if (uv != null) {
			int u, v;
			u = uv.getU ();
			v = uv.getV ();
			sb.append (u + "->" + v + "[label=\"c:" + custo[u][v] + " x:"
					+ x[u][v] + "\", color=red];\n");
		}
		sb.append ("};\n");

		return sb.toString ();
	}

	@Deprecated
	public void imprimirArvore (int sufixo, Aresta uv, boolean concatenar)
			throws IOException {

		String nomeArquivo = this.getNome () + sufixo;

		PrintWriter arq = new PrintWriter (nomeArquivo + (concatenar ? "c" : "")
				+ ".dot");

		arq.printf ("digraph " + this.getNome () + "%d%s\n{\n", sufixo,
				(concatenar ? "c" : ""));

		arq.printf ("size=\"5,5\";");

		for (int u : this.getVertices ()) {
			arq
					.printf ("%d [label=\"(%d) d:%d y:%d\"];\n", u, u, demanda[u],
							y[u]);
		}

		for (int u : this.getVertices ()) {
			for (int v : this.getVertices ()) {
				if (naArvore[u][v]) {

					arq.printf ("%d->%d[label=\"c:%d x:%d\"];\n", u, v, custo[u][v],
							x[u][v]);
				}
			}
		}
		if (uv != null)
			arq.printf ("%d->%d[label=\"c:%d x:%d\", color=red];\n", uv.getU (),
					uv.getV (), custo[uv.getU ()][uv.getV ()], x[uv.getU ()][uv
							.getV ()]);

		arq.printf ("};\n");
		arq.close ();

		if (!concatenar) {
			Runtime.getRuntime ().exec ("./gerar.sh " + nomeArquivo);
			Runtime.getRuntime ().exec ("gv " + nomeArquivo + ".ps");
		}
		else {
			Runtime.getRuntime ().exec ("./concatenar.sh " + nomeArquivo);
			Runtime.getRuntime ().exec ("./gerar.sh " + nomeArquivo);
		}
	}

	public String getNome () {
		return nome;
	}

	public void setNome (String nome) {
		this.nome = nome;
	}

	public int[] getNaArvoreVertices () {
		return naArvoreVertices;
	}

	public void setNaArvoreVertices (int[] naArvoreVertices) {
		this.naArvoreVertices = naArvoreVertices;
	}

	public int custo () {
		int custo = 0;

		for (int u : this.getVertices ()) {
			for (int v : this.getVertices ()) {
				custo += this.getX ()[u][v] * this.getCusto ()[u][v];
			}
		}
		return custo;
	}

	public void atualizaY (int raiz) {
		int u = sucessor[raiz];
		while (u != raiz) {
			if (naArvore[u][predecessor[u]]) {
				y[u] = y[predecessor[u]] - custo[u][predecessor[u]];
			}
			else {
				y[u] = y[predecessor[u]] + custo[predecessor[u]][u];
			}
			u = sucessor[u];
		}
	}
}
