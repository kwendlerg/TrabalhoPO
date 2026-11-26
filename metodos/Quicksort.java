package metodos;

public class Quicksort {
    private Emprestimo[] lista;
    private int quant;

    public Quicksort(Emprestimo[] lista) {
        this.lista = lista;
        this.quant = lista.length;
    }

    public void quickSort() {
        if (this.quant > 1)
            ordena(0, this.quant - 1);
    }

    private void ordena(int esq, int dir) {
        int i = esq, j = dir;
        Emprestimo pivo, temp;

        // pivô = elemento do meio
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
