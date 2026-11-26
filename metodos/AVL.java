package metodos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AVL {
    private NoAvl raiz;
    private boolean balancear;
    private int quant;

    public AVL() {
        this.raiz = null;
        this.quant = 0;
        this.balancear = false;
    }

    public void inserir(Emprestimo emp) {
        this.raiz = this.inserir(emp, this.raiz);
    }

    private NoAvl inserir(Emprestimo emp, NoAvl no) {
        if (no == null) {
            NoAvl novo = new NoAvl(emp);
            this.balancear = true;
            this.quant++;
            return novo;
        } else {
            int comp = emp.getNomeUsuario().trim().compareTo(no.getNome());
            if (comp == 0) {
                no.getEmprestimos().add(emp);
                this.balancear = false;
                return no;
            } else if (comp < 0) {
                no.setEsq(this.inserir(emp, no.getEsq()));
                no = this.balancearDir(no);
                return no;
            } else {
                no.setDir(this.inserir(emp, no.getDir()));
                no = this.balancearEsq(no);
                return no;
            }
        }
    }

    private NoAvl balancearDir(NoAvl no) {
        if (this.balancear) {
            switch (no.getFatorBalanceamento()) {
                case 1:
                    no.setFatorBalanceamento(0);
                    this.balancear = false;
                    break;
                case 0:
                    no.setFatorBalanceamento(-1);
                    break;
                case -1:
                    no = this.rotacaoDireita(no);
            }
        }
        return no;
    }

    private NoAvl balancearEsq(NoAvl no) {
        if (this.balancear) {
            switch (no.getFatorBalanceamento()) {
                case -1:
                    no.setFatorBalanceamento(0);
                    this.balancear = false;
                    break;
                case 0:
                    no.setFatorBalanceamento(1);
                    break;
                case 1:
                    no = this.rotacaoEsquerda(no);
            }
        }
        return no;
    }

    private NoAvl rotacaoDireita(NoAvl no) {
        NoAvl temp1, temp2;
        temp1 = no.getEsq();
        if (temp1.getFatorBalanceamento() == -1) {
            no.setEsq(temp1.getDir());
            temp1.setDir(no);
            no.setFatorBalanceamento(0);
            no = temp1;
        } else {
            temp2 = temp1.getDir();
            temp1.setDir(temp2.getEsq());
            temp2.setEsq(temp1);
            no.setEsq(temp2.getDir());
            temp2.setDir(no);
            if (temp2.getFatorBalanceamento() == -1)
                no.setFatorBalanceamento(1);
            else
                no.setFatorBalanceamento(0);
            if (temp2.getFatorBalanceamento() == 1)
                temp1.setFatorBalanceamento(-1);
            else
                temp1.setFatorBalanceamento(0);
            no = temp2;
        }
        no.setFatorBalanceamento(0);
        this.balancear = false;
        return no;
    }

    private NoAvl rotacaoEsquerda(NoAvl no) {
        NoAvl temp1, temp2;
        temp1 = no.getDir();
        if (temp1.getFatorBalanceamento() == 1) {
            no.setDir(temp1.getEsq());
            temp1.setEsq(no);
            no.setFatorBalanceamento(0);
            no = temp1;
        } else {
            temp2 = temp1.getEsq();
            temp1.setEsq(temp2.getDir());
            temp2.setDir(temp1);
            no.setDir(temp2.getEsq());
            temp2.setEsq(no);
            if (temp2.getFatorBalanceamento() == 1)
                no.setFatorBalanceamento(-1);
            else
                no.setFatorBalanceamento(0);
            if (temp2.getFatorBalanceamento() == -1)
                temp1.setFatorBalanceamento(1);
            else
                temp1.setFatorBalanceamento(0);
            no = temp2;
        }
        no.setFatorBalanceamento(0);
        this.balancear = false;
        return no;
    }

    public ArrayList<Emprestimo> pesquisar(String nome) {
        NoAvl no = this.pesquisar(nome.trim(), this.raiz);
        if (no == null) {
            return null;
        }
        return no.getEmprestimos();
    }

    private NoAvl pesquisar(String nome, NoAvl no) {
        if (no != null) {
            int comp = nome.compareTo(no.getNome());
            if (comp < 0) {
                no = this.pesquisar(nome, no.getEsq());
            } else if (comp > 0) {
                no = this.pesquisar(nome, no.getDir());
            }
        }
        return no;
    }

    public void pesquisarEGravar(ArrayList<String> nomes, String arquivoSaida) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(arquivoSaida, StandardCharsets.UTF_8))) {
            for (int i = 0; i < nomes.size(); i++) {
                String nome = nomes.get(i).trim();
                if (nome.isEmpty()) {
                    continue;
                }
                ArrayList<Emprestimo> encontrados = pesquisar(nome);

                out.write("NOME: " + nome);
                out.newLine();

                if (encontrados == null) {
                    out.write("NÃO POSSUI EMPRÉSTIMOS");
                    out.newLine();
                } else {
                    for (int j = 0; j < encontrados.size(); j++) {
                        Emprestimo e = encontrados.get(j);
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
        }
    }
}
