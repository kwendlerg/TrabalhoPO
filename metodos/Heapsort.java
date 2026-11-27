package metodos;

public class Heapsort {
    private Emprestimo[] lista;
    private int quant;

    public Heapsort(Emprestimo[] lista) {
        this.lista = lista;
        this.quant = lista.length;
    }

    public void heapSort() {
        int dir = this.quant - 1, esq = (dir - 1) / 2;
        Emprestimo temp;

        while (esq >= 0) {
            refazHeap(esq, this.quant - 1);
            esq--;
        }
        while (dir > 0) {
            temp = this.lista[0];
            this.lista[0] = this.lista[dir];
            this.lista[dir] = temp;
            dir--;
            refazHeap(0, dir);
        }
    }

    private void refazHeap(int esq, int dir) {
        int i = esq, mF = 2 * i + 1;
        Emprestimo raiz = this.lista[i];
        boolean heap = false;

        while ((mF <= dir) && (!heap)) {
            if (mF < dir) {
                if (this.lista[mF].compararCom(this.lista[mF + 1]) < 0) {
                    mF++;
                }
            }
            if (raiz.compararCom(this.lista[mF]) < 0) {
                this.lista[i] = this.lista[mF];
                i = mF;
                mF = 2 * i + 1;
            } else {
                heap = true;
            }
        }
        this.lista[i] = raiz;
    }
}
