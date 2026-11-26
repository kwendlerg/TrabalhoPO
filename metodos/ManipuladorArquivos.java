package metodos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ManipuladorArquivos {

    public static Emprestimo[] carregarEmprestimos(String caminhoArquivo) throws IOException {
        ArrayList<Emprestimo> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    linha = linha.replace("\uFEFF", ""); // Remove eventual BOM UTF-8
                    primeiraLinha = false;
                }

                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String[] campos = linha.split(";");
                if (campos.length < 6) {
                    continue;
                }

                try {
                    int codigo = Integer.parseInt(campos[0].trim());
                    String nome = campos[1].trim();
                    String titulo = campos[2].trim();
                    String autor = campos[3].trim();
                    String dataEmp = campos[4].trim();
                    String dataDev = campos[5].trim();

                    lista.add(new Emprestimo(codigo, nome, titulo, autor, dataEmp, dataDev));
                } catch (NumberFormatException e) {
                }
            }
        }

        return lista.toArray(new Emprestimo[0]);
    }

    public static void gravarEmprestimos(String caminhoArquivo, Emprestimo[] lista) throws IOException {
        File arquivo = prepararArquivoSaida(caminhoArquivo);

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivo, false), StandardCharsets.UTF_8))) {

            for (int i = 0; i < lista.length; i++) {
                bw.write(lista[i].toStringOrdenacao());
                bw.newLine();
            }
        }
    }

    public static String[] carregarNomes(String caminhoArquivo) throws IOException {
        ArrayList<String> nomes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    linha = linha.replace("\uFEFF", "");
                    primeiraLinha = false;
                }

                linha = linha.trim();
                if (!linha.isEmpty()) {
                    nomes.add(linha);
                }
            }
        }

        return nomes.toArray(new String[0]);
    }

    public static String formatarResultadoPesquisa(String nomePesquisado, ArrayList<Emprestimo> encontrados) {
        StringBuilder sb = new StringBuilder();
        sb.append("NOME: ").append(nomePesquisado).append("\n");

        if (encontrados == null || encontrados.isEmpty()) {
            sb.append("NÃO POSSUI EMPRÉSTIMOS").append("\n");
        } else {
            for (int i = 0; i < encontrados.size(); i++) {
                sb.append(encontrados.get(i).toStringPesquisa()).append("\n");
            }
            sb.append("TOTAL: ").append(encontrados.size())
              .append(encontrados.size() == 1 ? " empréstimo" : " empréstimos")
              .append("\n");
        }

        sb.append("\n");
        return sb.toString();
    }

    public static void gravarTexto(String caminhoArquivo, String conteudo) throws IOException {
        File arquivo = prepararArquivoSaida(caminhoArquivo);

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivo, false), StandardCharsets.UTF_8))) {
            bw.write(conteudo);
        }
    }

    private static File prepararArquivoSaida(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }
        return arquivo;
    }
}