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
