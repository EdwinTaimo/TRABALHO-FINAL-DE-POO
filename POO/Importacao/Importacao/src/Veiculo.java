// Veiculo.java
public abstract class Veiculo {
    private static int proximoId = 1;

    // A partir deste ano a taxa de alfandega comeca a subir (carro mais novo = mais caro de nacionalizar)
    private static final int ANO_INICIO_TAXA_EXTRA = 2024;
    private static final int ANO_LIMITE_TAXA_EXTRA = 2026;
    private static final double INCREMENTO_POR_ANO = 0.02; // +2% por cada ano a partir de 2024
    private static final double MARGEM_LUCRO = 0.25; // 25% de lucro em cada venda

    private final int id;
    private String marca;
    private String modelo;
    private int ano;
    private double precoOrigem;
    private int quilometragem;
    private boolean vendido;
    private Origem origem;
    protected double taxaAlfandegaBase; // definida por cada subclasse (carro/moto)

    public Veiculo(String marca, String modelo, int ano, double precoOrigem, int quilometragem, Origem origem) {
        this.id = proximoId++;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.precoOrigem = precoOrigem;
        this.quilometragem = quilometragem;
        this.vendido = false;
        this.origem = origem;
    }

    // Construtor usado ao carregar dados do ficheiro (mantem o ID gravado)
    public Veiculo(int id, String marca, String modelo, int ano, double precoOrigem, int quilometragem, boolean vendido, Origem origem) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.precoOrigem = precoOrigem;
        this.quilometragem = quilometragem;
        this.vendido = vendido;
        this.origem = origem;
        if (id >= proximoId) proximoId = id + 1;
    }

    // Cada subclasse identifica o proprio tipo (usado no site e nos relatorios)
    public abstract String getTipo();

    // Quanto mais recente o carro (a partir de 2024), maior a taxa de alfandega
    private double calcularAjusteTaxaPorAno() {
        if (ano < ANO_INICIO_TAXA_EXTRA) return 0.0;
        int anoConsiderado = Math.min(ano, ANO_LIMITE_TAXA_EXTRA);
        int anosDeAumento = anoConsiderado - (ANO_INICIO_TAXA_EXTRA - 1); // 2024 -> 1, 2025 -> 2, 2026 -> 3
        return anosDeAumento * INCREMENTO_POR_ANO;
    }

    public double getTaxaAlfandegaFinal() {
        return taxaAlfandegaBase + calcularAjusteTaxaPorAno();
    }

    // Custo total de nacionalizar o veiculo (preco de origem + frete da origem + impostos)
    public double calcularPrecoFinal() {
        double frete = origem.getCustoImportacao();
        double valorComFrete = precoOrigem + frete;
        double impostos = valorComFrete * getTaxaAlfandegaFinal();
        return valorComFrete + impostos;
    }

    // Preco de venda ao cliente, ja com a margem de lucro de 25%
    public double calcularPrecoVenda() {
        return calcularPrecoFinal() * (1 + MARGEM_LUCRO);
    }

    // Lucro obtido na venda (25% sobre o custo de nacionalizacao)
    public double calcularLucro() {
        return calcularPrecoFinal() * MARGEM_LUCRO;
    }

    public void marcarComoVendido() {
        this.vendido = true;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public double getPrecoOrigem() { return precoOrigem; }
    public int getQuilometragem() { return quilometragem; }
    public boolean isVendido() { return vendido; }
    public Origem getOrigem() { return origem; }

    @Override
    public String toString() {
        String estado = vendido ? "VENDIDO" : "Disponivel";
        return String.format("[%d] %s %s (%d) - %d KM - Origem: %s - %s: $%.2f",
                id, marca, modelo, ano, quilometragem, origem.getNomeExibicao(), estado, calcularPrecoFinal());
    }
}
