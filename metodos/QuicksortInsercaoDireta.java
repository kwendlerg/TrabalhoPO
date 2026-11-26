package metodos;

public class QuicksortInsercaoDireta {
    private static final int LIMITE = 20;

    private Emprestimo[] lista;
    private int quant;
    private InsercaoDireta insercao;

    public QuicksortInsercaoDireta(Emprestimo[] lista) {
        this.lista = lista;
        this.quant = lista.length;
        this.insercao = new InsercaoDireta(lista);
    }

    public void quickSortInsercao() {
        if (this.quant > 1)
            ordena(0, this.quant - 1);
    }

    private void ordena(int esq, int dir) {
        // trecho com 20 elementos ou menos: chama a Inserção Direta
        if (dir - esq + 1 <= LIMITE) {
            this.insercao.ordena(esq, dir);
            return;
        }

        int i = esq, j = dir;
        Emprestimo pivo, temp;

        pivo = this.lista[(i + j) / 2];
        do {
            while (this.lista[i].compararCom(pivo) < 0)
                i++;
            while (this.lista[j].compararCom(pivo) > 0)
                j--;
            if (i <= j) {
                temp = this.lista[i];
                this.lista[i] = this.lista[j];
                this.lista[j] = temp;
                i++;
                j--;
            }
        } while (i <= j);

        if (esq < j)
            ordena(esq, j);
        if (dir > i)
            ordena(i, dir);
    }
}
