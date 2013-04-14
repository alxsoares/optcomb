Requisistos do sistema
=====================================================================
+ Java 5
+ graphviz
	Este programa foi utilizado para gerar os grafos de cada iteração.
	Para instalar (no ubuntu) basta digitar no console:
		sudo apt-get install graphviz

Executar o EP
=====================================================================
Execute no terminal o seguinte comando:
	java -jar ep1.jar < arquivoEntrada

O arquivoEntrada precisa respeitar o seguinte formato:
 
 Na  primeira linhas  dois  inteiros n  e m,  indicando  o número  de
 vértices e  o número  de arestas  respectivamente. Os  rótulos dos
 vértices são  valores entre 0 e  n-1. Na linha seguinte  n inteiros
 indicando as demandas dos vértices 0, 1, .. , n-1. Nas m linhas
 seguintes três inteiros u, v e c, indicando que existe uma aresta do
 vértice u para o vértice v com custo c.

Após a execução será impresso um relatório indicando o valor da
solução ótima, as arestas que foram utilizadas e a matriz de x. Caso
tenha instalado o programa graphviz, será gera no diretório corrente
os seguintes arquivos:

	+ Solucao_Viavel.ps (neste arquivo estão todas as iterações para
	descobrir primeira solução viável para o problema).

	+ Instancia_Original_Problema[n].ps (estes arquivos recebem sufixos
	1, 2, .. que representam as decomposições do problema original)

	+ Grafo_Final.ps (arquivo do grafo original com o vetor x
	preenchido com os valores da solução ótima)

	Em todos os arquivos você notará que existe um vértice com cor
	diferente. Este vértice é a raiz do problema.

Nesta distribuição foi adicionado um  exemplo de arquivo de entrada:
grafo.exemplo

--
Wanderley Guimarães
Alex Soares
