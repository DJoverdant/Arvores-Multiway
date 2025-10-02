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
