class No23 {
    int[] chaves;     // até 2 chaves
    No23[] filhos;    // até 3 filhos
    boolean folha;

    public No23() {
        chaves = new int[2];     // suporta 2 chaves
        filhos = new No23[3];    // suporta 3 filhos
        folha = true;
    }
}

public class Arvore23 {
    private No23 raiz;

    public Arvore23() {
        raiz = new No23();
    }

    // ===== BUSCA =====
    public boolean buscar(int chave) {
        return buscarRec(raiz, chave);
    }

    private boolean buscarRec(No23 no, int chave) {
        if (no == null) return false;

        if (chave == no.chaves[0]) return true;
        if (no.chaves[1] != 0 && chave == no.chaves[1]) return true;

        if (no.folha) return false;

        if (chave < no.chaves[0]) {
            return buscarRec(no.filhos[0], chave);
        } else if (no.chaves[1] == 0 || chave < no.chaves[1]) {
            return buscarRec(no.filhos[1], chave);
        } else {
            return buscarRec(no.filhos[2], chave);
        }
    }

    // ===== INSERÇÃO =====
    public void inserir(int chave) {
        No23 r = raiz;
        if (estaCheio(r)) {
            No23 novaRaiz = new No23();
            novaRaiz.folha = false;
            novaRaiz.filhos[0] = r;
            dividirFilho(novaRaiz, 0, r);
            raiz = novaRaiz;
            inserirNaoCheio(novaRaiz, chave);
        } else {
            inserirNaoCheio(r, chave);
        }
    }

    private void inserirNaoCheio(No23 no, int chave) {
        if (no.folha) {
            if (no.chaves[0] == 0) {
                no.chaves[0] = chave;
            } else if (no.chaves[1] == 0) {
                if (chave < no.chaves[0]) {
                    no.chaves[1] = no.chaves[0];
                    no.chaves[0] = chave;
                } else {
                    no.chaves[1] = chave;
                }
            }
        } else {
            int i;
            if (chave < no.chaves[0]) {
                i = 0;
            } else if (no.chaves[1] == 0 || chave < no.chaves[1]) {
                i = 1;
            } else {
                i = 2;
            }

            // cria o filho se for null
            if (no.filhos[i] == null) {
                no.filhos[i] = new No23();
            }

            if (estaCheio(no.filhos[i])) {
                dividirFilho(no, i, no.filhos[i]);
                if (chave > no.chaves[i]) i++;
            }
            inserirNaoCheio(no.filhos[i], chave);
        }
    }

    private void dividirFilho(No23 pai, int i, No23 filho) {
        No23 z = new No23();
        z.folha = filho.folha;

        int chaveMeio = filho.chaves[0];
        int chaveDireita = filho.chaves[1];

        z.chaves[0] = chaveDireita;
        filho.chaves[1] = 0;

        for (int j = 2; j > i; j--) {
            pai.filhos[j] = pai.filhos[j - 1];
        }
        pai.filhos[i + 1] = z;

        if (pai.chaves[1] == 0) {
            if (chaveMeio < pai.chaves[0]) {
                pai.chaves[1] = pai.chaves[0];
                pai.chaves[0] = chaveMeio;
            } else {
                pai.chaves[1] = chaveMeio;
            }
        }
    }

    private boolean estaCheio(No23 no) {
        if (no == null) return false;
        return no.chaves[0] != 0 && no.chaves[1] != 0;
    }

    // ===== REMOÇÃO =====
    public void remover(int chave) {
        removerRec(raiz, chave);

        if (!raiz.folha && raiz.chaves[0] == 0 && raiz.chaves[1] == 0) {
            raiz = raiz.filhos[0];
        }
    }

    private void removerRec(No23 no, int chave) {
        if (no == null) return;

        if (no.folha) {
            if (no.chaves[0] == chave) {
                no.chaves[0] = no.chaves[1];
                no.chaves[1] = 0;
            } else if (no.chaves[1] == chave) {
                no.chaves[1] = 0;
            }
            return;
        }

        if (no.chaves[0] == chave || no.chaves[1] == chave) {
            int idx = (no.chaves[0] == chave) ? 0 : 1;
            No23 filhoDireito = no.filhos[idx + 1];

            while (filhoDireito != null && !filhoDireito.folha) {
                filhoDireito = filhoDireito.filhos[0];
            }
            if (filhoDireito != null) {
                int sucessor = filhoDireito.chaves[0];
                no.chaves[idx] = sucessor;
                removerRec(no.filhos[idx + 1], sucessor);
            }
            return;
        }

        int i;
        if (chave < no.chaves[0]) {
            i = 0;
        } else if (no.chaves[1] == 0 || chave < no.chaves[1]) {
            i = 1;
        } else {
            i = 2;
        }

        if (no.filhos[i] == null) return;

        removerRec(no.filhos[i], chave);
    }

    // ===== EXIBIR ÁRVORE =====
    public void exibir() {
        exibirRec(raiz, 0);
    }

    private void exibirRec(No23 no, int nivel) {
        if (no == null) return;

        for (int i = 0; i < nivel; i++) {
            System.out.print("   ");
        }

        System.out.print("[");
        if (no.chaves[0] != 0) System.out.print(no.chaves[0]);
        if (no.chaves[1] != 0) System.out.print("," + no.chaves[1]);
        System.out.println("]");

        if (!no.folha) {
            for (int i = 0; i < 3; i++) {
                exibirRec(no.filhos[i], nivel + 1);
            }
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        Arvore23 arvore = new Arvore23();
        arvore.inserir(10);
        arvore.inserir(20);
        arvore.inserir(5);
        arvore.inserir(15);
        arvore.inserir(30);

        System.out.println("Árvore inicial:");
        arvore.exibir();

        System.out.println("\nBusca 15: " + arvore.buscar(15));
        System.out.println("Busca 50: " + arvore.buscar(50));

        System.out.println("\nRemovendo 15...");
        arvore.remover(15);
        arvore.exibir();

        System.out.println("\nRemovendo 10...");
        arvore.remover(10);
        arvore.exibir();
    }
}
