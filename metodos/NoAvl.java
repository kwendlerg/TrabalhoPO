package metodos;

import java.util.ArrayList;

public class NoAvl {
    private String nome;
    private ArrayList<Emprestimo> emprestimos;
    private NoAvl esq, dir;
    private int fatorBalanceamento;

    public NoAvl(Emprestimo emp) {
        this.nome = emp.getNomeUsuario().trim();
        this.emprestimos = new ArrayList<>();
        this.emprestimos.add(emp);
        this.fatorBalanceamento = 0;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public NoAvl getEsq() {
        return esq;
    }

    public void setEsq(NoAvl esq) {
        this.esq = esq;
    }

    public NoAvl getDir() {
        return dir;
    }

    public void setDir(NoAvl dir) {
        this.dir = dir;
    }

    public int getFatorBalanceamento() {
        return fatorBalanceamento;
    }

    public void setFatorBalanceamento(int fatorBalanceamento) {
        this.fatorBalanceamento = fatorBalanceamento;
    }
}
