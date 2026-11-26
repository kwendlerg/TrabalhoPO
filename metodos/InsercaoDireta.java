package metodos;

public class InsercaoDireta {
    private Emprestimo[] lista;
    private int quant;

    public InsercaoDireta(Emprestimo[] lista) {
        this.lista = lista;
        this.quant = lista.length;
    }

    // ordena o vetor inteiro
    public void insertionSort() {
        ordena(0, this.quant - 1);
    }

    // ordena apenas o trecho [esq, dir] do vetor (usado pelo Quicksort + Inserção)
    public void ordena(int esq, int dir) {
        int i, j;
        Emprestimo temp;

        for (i = esq + 1; i <= dir; i++) {
            temp = this.lista[i];
            j = i - 1;
            // só desloca quando o anterior é estritamente maior => método estável
            while ((j >= esq) && (this.lista[j].compararCom(temp) > 0)) {
                this.lista[j + 1] = this.lista[j];
                j--;
            }
            this.lista[j + 1] = temp;
        }
    }
}
