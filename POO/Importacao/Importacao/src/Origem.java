// Origem.java
// Representa de onde o veiculo esta a ser importado.
// Cada origem tem um custo de importacao (frete/transporte) proprio.
public enum Origem {
    MAPUTO("Maputo", 450.0),
    ZAMBEZIA("Zambezia", 700.0),
    AFRICA_DO_SUL("Africa do Sul", 1500.0),
    JAPAO("Japao", 3200.0),
    ALEMANHA("Alemanha", 3000.0),
    ITALIA("Italia", 3100.0);

    private final String nomeExibicao;
    private final double custoImportacao;

    Origem(String nomeExibicao, double custoImportacao) {
        this.nomeExibicao = nomeExibicao;
        this.custoImportacao = custoImportacao;
    }

    public String getNomeExibicao() { return nomeExibicao; }
    public double getCustoImportacao() { return custoImportacao; }

    // Mostra a lista numerada para o utilizador escolher no menu
    public static void mostrarOpcoes() {
        Origem[] valores = Origem.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("%d. %s (custo de importacao: $%.2f)%n",
                    i + 1, valores[i].getNomeExibicao(), valores[i].getCustoImportacao());
        }
    }

    public static Origem porIndice(int indice) {
        Origem[] valores = Origem.values();
        if (indice < 1 || indice > valores.length) return null;
        return valores[indice - 1];
    }
}
