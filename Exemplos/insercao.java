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
