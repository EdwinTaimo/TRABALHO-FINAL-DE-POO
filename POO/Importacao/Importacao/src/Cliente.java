// Cliente.java
public class Cliente {
    private static int proximoId = 1;

    private final int id;
    private String nome;
    private String celular;
    private String dataNascimento;
    private String veiculoInteresse; // marca/modelo que o cliente quer (opcional)

    public Cliente(String nome, String celular, String dataNascimento, String veiculoInteresse) {
        this.id = proximoId++;
        this.nome = nome;
        this.celular = celular;
        this.dataNascimento = dataNascimento;
        this.veiculoInteresse = veiculoInteresse;
    }

    public Cliente(int id, String nome, String celular, String dataNascimento, String veiculoInteresse) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.dataNascimento = dataNascimento;
        this.veiculoInteresse = veiculoInteresse;
        if (id >= proximoId) proximoId = id + 1;
    }

    // Getters para o Java conseguir ler ao montar o HTML
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCelular() { return celular; }
    public String getDataNascimento() { return dataNascimento; }
    public String getVeiculoInteresse() { return veiculoInteresse; }

    @Override
    public String toString() {
        String interesse = (veiculoInteresse == null || veiculoInteresse.isBlank()) ? "-" : veiculoInteresse;
        return String.format("[%d] %s - %s (Interesse: %s)", id, nome, celular, interesse);
    }
}
