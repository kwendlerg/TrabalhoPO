package metodos;

public class Emprestimo {
    private int codigoEmprestimo;
    private String nomeUsuario;
    private String tituloLivro;
    private String autor;
    private String dataEmprestimo;
    private String dataDevolucao;

    public Emprestimo(int codigoEmprestimo, String nomeUsuario, String tituloLivro,
                      String autor, String dataEmprestimo, String dataDevolucao) {
        this.codigoEmprestimo = codigoEmprestimo;
        this.nomeUsuario = nomeUsuario;
        this.tituloLivro = tituloLivro;
        this.autor = autor;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public int getCodigoEmprestimo() {
        return codigoEmprestimo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public String getAutor() {
        return autor;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public int compararCom(Emprestimo outro) {
        int compNome = this.nomeUsuario.compareToIgnoreCase(outro.nomeUsuario);
        if (compNome != 0) {
            return compNome;
        }
        return Integer.compare(this.codigoEmprestimo, outro.codigoEmprestimo);
    }

    public String toStringOrdenacao() {
        return codigoEmprestimo + ";" + nomeUsuario + ";" + tituloLivro + ";"
                + autor + ";" + dataEmprestimo + ";" + dataDevolucao;
    }

    public String toStringPesquisa() {
        return "Empréstimo: " + codigoEmprestimo
                + "\tLivro: " + tituloLivro
                + "\tAutor: " + autor + "\n"
                + "Data do empréstimo: " + dataEmprestimo
                + "\tData de devolução: " + dataDevolucao;
    }
}