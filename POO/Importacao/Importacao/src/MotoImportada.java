// MotoImportada.java
public class MotoImportada extends Veiculo {
    private static final double TAXA_BASE = 0.25; // 25% de taxa de alfandega base para motos
    private int cilindrada; // em cc

    public MotoImportada(String marca, String modelo, int ano, double precoOrigem, int quilometragem, int cilindrada, Origem origem) {
        super(marca, modelo, ano, precoOrigem, quilometragem, origem);
        this.taxaAlfandegaBase = TAXA_BASE;
        this.cilindrada = cilindrada;
    }

    public MotoImportada(int id, String marca, String modelo, int ano, double precoOrigem, int quilometragem, boolean vendido, int cilindrada, Origem origem) {
        super(id, marca, modelo, ano, precoOrigem, quilometragem, vendido, origem);
        this.taxaAlfandegaBase = TAXA_BASE;
        this.cilindrada = cilindrada;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }

    public int getCilindrada() { return cilindrada; }
}
