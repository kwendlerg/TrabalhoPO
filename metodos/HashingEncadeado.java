package metodos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;

// Hashing com vetor encadeado usando o nome do usuário como chave
public class HashingEncadeado {

    // cada posição do vetor guarda uma lista encadeada com os empréstimos que caíram nela
    private ArrayList<LinkedList<Emprestimo>> vetor;
    private int M;

    // aqui ele escolhe M como o menor primo >= quantidade de registros
    public HashingEncadeado(int quantidadeRegistros) {
        this.M = escolherPrimo(quantidadeRegistros);
        this.vetor = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            this.vetor.add(new LinkedList<>());
        }
    }

    // aqui ele transforma o nome em número somando os caracteres e faz o mod M
    public int hashing(String chave) {
        char carac;
        int i, soma = 0;
        for (i = 0; i < chave.length(); i++) {
            carac = chave.charAt(i);
            soma += Character.getNumericValue(carac);
        }
        if (soma < 0) {
            soma = -soma; // aqui ele garante que a posição não fique negativa
        }
        return soma % M;
    }

    // aqui ele calcula a posição e coloca o empréstimo no fim da lista daquela posição
    // se houver colisão, o empréstimo só entra na mesma lista
    public void inserir(Emprestimo emp) {
        int endereco = hashing(emp.getNomeUsuario().trim());
        this.vetor.get(endereco).add(emp);
    }

    public void inserirTodos(Emprestimo[] lista) {
        for (int i = 0; i < lista.length; i++) {
            inserir(lista[i]);
        }
    }

    // aqui ele vai direto na posição calculada e faz a pesquisa sequencial só naquela lista
    // como é chave secundária, ele não para no primeiro: junta todos os empréstimos do nome
    public LinkedList<Emprestimo> pesquisar(String nome) {
        String chave = nome.trim();
        LinkedList<Emprestimo> encontrados = new LinkedList<>();
        for (Emprestimo emp : this.vetor.get(hashing(chave))) {
            if (emp.getNomeUsuario().trim().equals(chave)) {
                encontrados.add(emp);
            }
        }
        return encontrados;
    }

    public void pesquisarEGravar(ArrayList<String> nomes, String arquivoSaida) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(arquivoSaida, StandardCharsets.UTF_8));
        for (int i = 0; i < nomes.size(); i++) {
            String nome = nomes.get(i).trim();
            if (nome.isEmpty()) {
                continue;
            }
            LinkedList<Emprestimo> encontrados = pesquisar(nome);

            out.write("NOME: " + nome);
            out.newLine();

            if (encontrados.isEmpty()) {
                out.write("NÃO POSSUI EMPRÉSTIMOS");
                out.newLine();
            } else {
                for (Emprestimo e : encontrados) {
                    out.write("Empréstimo: " + e.getCodigoEmprestimo()
                            + " - Livro: " + e.getTituloLivro()
                            + " - Autor: " + e.getAutor());
                    out.newLine();
                    out.write("Data do empréstimo: " + e.getDataEmprestimo()
                            + " - Data de devolução: " + e.getDataDevolucao());
                    out.newLine();
                    out.newLine();
                }
                out.write("TOTAL: " + encontrados.size() + " empréstimos");
                out.newLine();
            }
            out.newLine();
        }
        out.close();
    }

    private int escolherPrimo(int n) {
        int m = n;
        if (m < 2) {
            m = 2;
        }
        while (!ehPrimo(m) || formaProibida(m)) {
            m++;
        }
        return m;
    }

    private boolean ehPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // aqui ele evita primos da forma b^i ± j (b = 64, 100, 128, 256 e j até 10)
    // exemplo: 101 = 100 + 1, então 101 não é usado
    private boolean formaProibida(int m) {
        int[] bases = {64, 100, 128, 256};
        int j = 10;
        for (int k = 0; k < bases.length; k++) {
            int potencia = bases[k];
            while (potencia - j <= m) {
                if (m >= potencia - j && m <= potencia + j) {
                    return true;
                }
                potencia = potencia * bases[k];
            }
        }
        return false;
    }
}
