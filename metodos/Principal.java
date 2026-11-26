package metodos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Principal {

    private static final String PASTA_ENTRADA = "entrada/";
    private static final String PASTA_SAIDA = "saida/";
    private static final String ARQUIVO_NOMES = PASTA_ENTRADA + "nomes.txt";

    private static final int[] TAMANHOS = {1000, 5000, 10000, 50000};
    private static final String[] TIPOS = {"alea", "inv", "ord"};
    private static final int REPETICOES = 5;

    private static final String[] METODOS = {"Heap", "Quick", "QuickIns", "ABB", "AVL", "Hash"};

    public static void main(String[] args) throws IOException {
        new File(PASTA_SAIDA).mkdirs();

        // aqui ele lê os 400 nomes uma vez só, fora da medição de tempo
        String[] vetNomes = ManipuladorArquivos.carregarNomes(ARQUIVO_NOMES);
        ArrayList<String> nomes = new ArrayList<>();
        for (int i = 0; i < vetNomes.length; i++) {
            nomes.add(vetNomes[i]);
        }

        for (int m = 0; m < METODOS.length; m++) {
            System.out.println("===== " + METODOS[m] + " =====");
            for (int t = 0; t < TAMANHOS.length; t++) {
                for (int k = 0; k < TIPOS.length; k++) {
                    String sufixo = TAMANHOS[t] + TIPOS[k];
                    String entrada = PASTA_ENTRADA + "emprestimo" + sufixo + ".txt";
                    String saida = PASTA_SAIDA + METODOS[m] + sufixo + ".txt";

                    // aqui ele roda o procedimento 5 vezes e divide o tempo por 5
                    // o arquivo de saída é sobrescrito a cada execução
                    long inicio = System.nanoTime();
                    for (int r = 0; r < REPETICOES; r++) {
                        executarProcedimento(METODOS[m], entrada, saida, nomes);
                    }
                    long fim = System.nanoTime();

                    double media = (fim - inicio) / REPETICOES / 1000000.0;
                    System.out.println("emprestimo" + sufixo + ".txt -> " + media + " ms");
                }
            }
            System.out.println();
        }
        System.out.println("Concluído.");
    }

    // aqui ele carrega o arquivo, ordena ou pesquisa, e grava a saída
    private static void executarProcedimento(String metodo, String entrada, String saida,
                                             ArrayList<String> nomes) throws IOException {
        Emprestimo[] lista = ManipuladorArquivos.carregarEmprestimos(entrada);

        switch (metodo) {
            case "Heap":
                new Heapsort(lista).heapSort();
                ManipuladorArquivos.gravarEmprestimos(saida, lista);
                break;

            case "Quick":
                new Quicksort(lista).quickSort();
                ManipuladorArquivos.gravarEmprestimos(saida, lista);
                break;

            case "QuickIns":
                new QuicksortInsercaoDireta(lista).quickSortInsercao();
                ManipuladorArquivos.gravarEmprestimos(saida, lista);
                break;

            case "ABB":
                ABB abb = new ABB();
                for (int i = 0; i < lista.length; i++) {
                    abb.inserir(lista[i]);
                }
                abb.balancear();
                abb.pesquisarEGravar(nomes, saida);
                break;

            case "AVL":
                AVL avl = new AVL();
                for (int i = 0; i < lista.length; i++) {
                    avl.inserir(lista[i]);
                }
                avl.pesquisarEGravar(nomes, saida);
                break;

            case "Hash":
                HashingEncadeado hash = new HashingEncadeado(lista.length);
                hash.inserirTodos(lista);
                hash.pesquisarEGravar(nomes, saida);
                break;
        }
    }
}
