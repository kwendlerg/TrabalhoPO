package metodos;

import java.io.IOException;
import java.util.ArrayList;

public class ABB {

    private static class NoABB {
        String nome;
        ArrayList<Emprestimo> emprestimos;
        NoABB esq, dir;

        NoABB(Emprestimo emprestimo) {
            nome = emprestimo.getNomeUsuario();
            emprestimos = new ArrayList<>();
            emprestimos.add(emprestimo);
        }

        NoABB(String nome, ArrayList<Emprestimo> emprestimos) {
            this.nome = nome;
            this.emprestimos = emprestimos;
        }
    }

    private NoABB raiz;

    public void inserir(Emprestimo emprestimo) {
        raiz = inserir(raiz, emprestimo);
    }

    private NoABB inserir(NoABB no, Emprestimo emprestimo) {
        if (no == null) {
            return new NoABB(emprestimo);
        }

        int comparacao = emprestimo.getNomeUsuario().compareToIgnoreCase(no.nome);
        if (comparacao < 0) {
            no.esq = inserir(no.esq, emprestimo);
        } else if (comparacao > 0) {
            no.dir = inserir(no.dir, emprestimo);
        } else {
            no.emprestimos.add(emprestimo);
        }
        return no;
    }

    private NoABB pesquisar(String nome, NoABB no) {
        if (no == null) {
            return null;
        }
        int comparacao = nome.compareToIgnoreCase(no.nome);
        if (comparacao == 0) {
            return no;
        }
        if (comparacao < 0) {
            return pesquisar(nome, no.esq);
        }
        return pesquisar(nome, no.dir);
    }

    public ArrayList<Emprestimo> pesquisar(String nome) {
        NoABB encontrado = pesquisar(nome, raiz);
        if (encontrado == null) {
            return new ArrayList<>();
        }
        return encontrado.emprestimos;
    }

    private void caminhamentoCentral(NoABB no, ArrayList<NoABB> vetor) {
        if (no != null) {
            caminhamentoCentral(no.esq, vetor);
            vetor.add(no);
            caminhamentoCentral(no.dir, vetor);
        }
    }

    private void construirBalanceada(ArrayList<NoABB> vetor, int inicio, int fim) {
        if (inicio > fim) {
            return;
        }
        int meio = (inicio + fim) / 2;
        NoABB anterior = vetor.get(meio);
        raiz = inserirNoBalanceado(raiz, anterior.nome, anterior.emprestimos);
        construirBalanceada(vetor, inicio, meio - 1);
        construirBalanceada(vetor, meio + 1, fim);
    }

    private NoABB inserirNoBalanceado(NoABB no, String nome,
                                      ArrayList<Emprestimo> emprestimos) {
        if (no == null) {
            return new NoABB(nome, emprestimos);
        }
        if (nome.compareToIgnoreCase(no.nome) < 0) {
            no.esq = inserirNoBalanceado(no.esq, nome, emprestimos);
        } else {
            no.dir = inserirNoBalanceado(no.dir, nome, emprestimos);
        }
        return no;
    }

    public void balancear() {
        ArrayList<NoABB> vetorOrdenado = new ArrayList<>();
        caminhamentoCentral(raiz, vetorOrdenado);
        raiz = null;
        construirBalanceada(vetorOrdenado, 0, vetorOrdenado.size() - 1);
    }

    public void pesquisarEGravar(String[] nomes, String caminhoSaida) throws IOException {
        StringBuilder resultado = new StringBuilder();
        for (String nome : nomes) {
            resultado.append(ManipuladorArquivos.formatarResultadoPesquisa(
                    nome, pesquisar(nome)));
        }
        ManipuladorArquivos.gravarTexto(caminhoSaida, resultado.toString());
    }

    public void pesquisarEGravar(ArrayList<String> nomes, String caminhoSaida)
            throws IOException {
        StringBuilder resultado = new StringBuilder();
        for (String nome : nomes) {
            resultado.append(ManipuladorArquivos.formatarResultadoPesquisa(
                    nome, pesquisar(nome)));
        }
        ManipuladorArquivos.gravarTexto(caminhoSaida, resultado.toString());
    }
}
