package ep1.fluxoemredes.estruturas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;

import junit.framework.TestCase;
import ep1.estruturas.Vertice;

public class ArvoreSolucaoOtimaTest extends TestCase {

	public void testCaminho () throws Exception {

		for (int caso = 1;; caso++) {
			InputStream grafo = this.getClass ().getResourceAsStream (
					"grafo." + caso);
			InputStream entrada = this.getClass ().getResourceAsStream (
					"testCaminho.entrada." + caso);
			InputStream saida = this.getClass ().getResourceAsStream (
					"testCaminho.saida." + caso);

			Scanner scanfEntrada, scanfSaida;
			try {
				scanfEntrada = new Scanner (entrada);
				scanfSaida = new Scanner (saida);

			} catch (Exception e) {
				return;
			}

			System.out.println ("Caso: " + caso);
			ArvoreSolucaoOtima aso = ArvoreSolucaoOtima.construirGrafo (grafo);
			aso.inicializaNaArvore ();

			int raiz = scanfEntrada.nextInt ();
			int u = scanfEntrada.nextInt (), v = scanfEntrada.nextInt ();
			
			aso.setRaiz (raiz);
			aso.geraPredecessores (raiz);
			List<Integer> caminho = aso.caminho (u, v);

			// Verificando a resposta
			{
				System.out.print ("  Caminho encontrado: ");
				for (int i = 0; i < caminho.size (); i++) {
					System.out.print (caminho.get (i) + " ");
				}
				System.out.println ();

				int i;
				for (i = 0; i < caminho.size (); i++) {
					assertTrue (caminho.get (i) == scanfSaida.nextInt ());
				}
				assertTrue (scanfSaida.hasNext () == false);
				System.out.println ("  Caso Ok!");
			}
		}
	}

	public void testGeraPredecessores () throws Exception {

		String testes[] = new String[] { "2" };

		for (int caso = 1; caso < testes.length; caso++) {
			InputStream grafo = this.getClass ().getResourceAsStream (
					"grafo." + testes[caso]);
			InputStream entrada = ArvoreSolucaoOtimaTest.class
					.getResourceAsStream ("testGeraPredecessores.entrada."
							+ testes[caso]);

			Scanner scanfEntrada;
			scanfEntrada = new Scanner (entrada);

			System.out.println ("Caso: " + testes[caso]);
			ArvoreSolucaoOtima aso = ArvoreSolucaoOtima.construirGrafo (grafo);
			aso.inicializaNaArvore ();

			int raiz = scanfEntrada.nextInt ();

			aso.setRaiz (raiz);
			aso.geraPredecessores (raiz);
			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				int p = scanfEntrada.nextInt ();
				assertTrue (p == aso.getPredecessor ()[i]);
			}
			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				int d = scanfEntrada.nextInt ();
				assertTrue (d == aso.getProfundidade ()[i]);
			}
			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				int s = scanfEntrada.nextInt ();
				assertTrue (s == aso.getSucessor ()[i]);
			}
		}
	}

	public void testAtualizaArvore () throws Exception {

		String[] testes = new String[] { "2" };

		for (int caso = 0; caso < testes.length; caso++) {

			InputStream grafo = this.getClass ().getResourceAsStream (
					"grafo." + testes[caso]);
			InputStream entrada = this.getClass ().getResourceAsStream (
					"testAtualizaArvore.entrada." + testes[caso]);
			InputStream saida = this.getClass ().getResourceAsStream (
					"testAtualizaArvore.saida." + testes[caso]);

			Scanner scanfEntrada, scanfSaida;
			scanfEntrada = new Scanner (entrada);
			scanfSaida = new Scanner (saida);

			System.out.println ("Caso: " + testes[caso]);
			ArvoreSolucaoOtima aso = ArvoreSolucaoOtima.construirGrafo (grafo);
			aso.inicializaNaArvore ();

			int raiz = scanfEntrada.nextInt ();
			int u = scanfEntrada.nextInt (), v = scanfEntrada.nextInt ();

			aso.setRaiz (raiz);
			aso.geraPredecessores (raiz);
			aso.atualizaArvore (u, v);

			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				System.out.print (aso.getPredecessor ()[i] + " ");
			}
			System.out.println ();
			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				System.out.print (aso.getProfundidade ()[i] + " ");
			}
			System.out.println ();
			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				System.out.print (aso.getSucessor ()[i] + " ");
			}
			System.out.println ();

			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				int p = scanfSaida.nextInt ();
				assertTrue (p == aso.getPredecessor ()[i]);
			}
			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				int d = scanfSaida.nextInt ();
				assertTrue (d == aso.getProfundidade ()[i]);
			}
			for (int i = 0; i < aso.getNumeroVertices (); i++) {
				int s = scanfSaida.nextInt ();
				assertTrue (s == aso.getSucessor ()[i]);
			}

		}
	}

	public void testCalculaGama () throws Exception {
		// TODO Implementar teste unitário
		fail ("Não implementado!");
	}

	public void testEncontraArestaSaida () throws Exception {
		// TODO Implementar teste unitário
		fail ("Não implementado!");
	}

}
