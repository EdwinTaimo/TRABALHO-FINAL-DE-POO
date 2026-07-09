// CarroImportado.java
public class CarroImportado extends Veiculo {
    private static final double TAXA_BASE = 0.35; // 35% de taxa de alfandega base para carros

    public CarroImportado(String marca, String modelo, int ano, double precoOrigem, int quilometragem, Origem origem) {
        super(marca, modelo, ano, precoOrigem, quilometragem, origem);
        this.taxaAlfandegaBase = TAXA_BASE;
    }

    public CarroImportado(int id, String marca, String modelo, int ano, double precoOrigem, int quilometragem, boolean vendido, Origem origem) {
        super(id, marca, modelo, ano, precoOrigem, quilometragem, vendido, origem);
        this.taxaAlfandegaBase = TAXA_BASE;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }
}
