class No23 {
    int[] chaves;
    No23[] filhos;
    boolean folha;

    public No23() {
        chaves = new int[2];   // até 2 chaves
        filhos = new No23[3];  // até 3 filhos
        folha = true;

        // Inicializa filhos como nulos explícitos
        for (int i = 0; i < 3; i++) {
            filhos[i] = null;
        }
    }

    public boolean cheio() {
        return chaves[0] != 0 && chaves[1] != 0;
    }

    public int numChaves() {
        if (chaves[0] == 0) return 0;
        if (chaves[1] == 0) return 1;
        return 2;
    }
}

class Arvore23 {
    No23 raiz;

    public Arvore23() {
        raiz = new No23();
    }

    // INSERÇÃO ======================
    public void inserir(int chave) {
        No23 r = raiz;
        if (r.cheio()) {
            No23 s = new No23();
            s.folha = false;
            s.filhos[0] = r;
            raiz = s;
            dividirFilho(s, 0, r);
            inserirNaoCheio(s, chave);
        } else {
            inserirNaoCheio(r, chave);
        }
    }

    private void inserirNaoCheio(No23 x, int chave) {
        if (x.folha) {
            if (x.chaves[0] == 0) {
                x.chaves[0] = chave;
            } else if (x.chaves[1] == 0) {
                if (chave < x.chaves[0]) {
                    x.chaves[1] = x.chaves[0];
                    x.chaves[0] = chave;
                } else {
                    x.chaves[1] = chave;
                }
            }
        } else {
            int i = x.numChaves() - 1;
            while (i >= 0 && chave < x.chaves[i]) {
                i--;
            }
            i++;
            if (x.filhos[i].cheio()) {
                dividirFilho(x, i, x.filhos[i]);
                if (chave > x.chaves[i]) {
                    i++;
                }
            }
            inserirNaoCheio(x.filhos[i], chave);
        }
    }

    private void dividirFilho(No23 pai, int i, No23 filho) {
        No23 z = new No23();
        z.folha = filho.folha;

        int chaveEsquerda = filho.chaves[0];
        int chaveDireita = filho.chaves[1];

        int chavePromovida = chaveEsquerda;
        z.chaves[0] = chaveDireita;

        // Se não for folha, move os filhos
        if (!filho.folha) {
            z.filhos[0] = filho.filhos[1];
            z.filhos[1] = filho.filhos[2];
        }

        // limpa o filho 
        filho.chaves[0] = 0;
        filho.chaves[1] = 0;
        filho.filhos[1] = null;
        filho.filhos[2] = null;

        // desloca filhos do pai para abrir espaço
        for (int j = pai.numChaves(); j >= i + 1; j--) {
            pai.filhos[j + 1] = pai.filhos[j];
        }
        pai.filhos[i + 1] = z;

        // desloca chaves do pai
        for (int j = pai.numChaves() - 1; j >= i; j--) {
            pai.chaves[j + 1] = pai.chaves[j];
        }
        pai.chaves[i] = chavePromovida;

        pai.folha = false; // agora o pai tem filhos
    }

    // =================== BUSCA ======================
    public boolean buscar(int chave) {
        return buscarRecursivo(raiz, chave);
    }

    private boolean buscarRecursivo(No23 x, int chave) {
        if (x == null) return false;

        for (int i = 0; i < x.numChaves(); i++) {
            if (x.chaves[i] == chave) return true;
        }

        if (x.folha) return false;

        if (chave < x.chaves[0]) return buscarRecursivo(x.filhos[0], chave);
        else if (x.numChaves() == 1 || (x.numChaves() == 2 && chave < x.chaves[1]))
            return buscarRecursivo(x.filhos[1], chave);
        else
            return buscarRecursivo(x.filhos[2], chave);
    }

    // IMPRESSÃO ====================
    public void imprimir(No23 x, String espaco) {
        if (x == null) return;
        System.out.println(espaco + "[" + (x.chaves[0] == 0 ? "" : x.chaves[0]) +
                (x.chaves[1] == 0 ? "" : "," + x.chaves[1]) + "]");
        for (int i = 0; i <= x.numChaves(); i++) {
            imprimir(x.filhos[i], espaco + "   ");
        }
    }

    // REMOÇÃO =====================
    public void remover(int chave) {
        raiz = removerRecursivo(raiz, chave);
    }

    private No23 removerRecursivo(No23 x, int chave) {
        if (x == null) return null;

        if (x.folha) {
            if (x.chaves[0] == chave) x.chaves[0] = x.chaves[1];
            if (x.chaves[1] == chave) x.chaves[1] = 0;
            return x;
        }

        for (int i = 0; i < x.numChaves(); i++) {
            if (x.chaves[i] == chave) {
                x.chaves[i] = 0;
            }
        }
        for (int i = 0; i <= x.numChaves(); i++) {
            x.filhos[i] = removerRecursivo(x.filhos[i], chave);
        }
        return x;
    }
}

public class Main {
    public static void main(String[] args) {
        Arvore23 arvore = new Arvore23();

        // Inserções
        arvore.inserir(10);
        arvore.inserir(20);
        arvore.inserir(5);
        arvore.inserir(6);
        arvore.inserir(4);
       
        System.out.println("Árvore após inserções:");
        arvore.imprimir(arvore.raiz, "");

        //teste de buscapé
        System.out.println("\nBuscar 6: " + arvore.buscar(6));
        System.out.println("Buscar 17: " + arvore.buscar(17));


        // Remoções
        arvore.remover(6);
        arvore.remover(17);

        System.out.println("\nÁrvore após remoções:");
        arvore.imprimir(arvore.raiz, "");
    }
}
