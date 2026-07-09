// App.java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final String FICHEIRO_DADOS = "dados.txt";

    private static List<Veiculo> catalogo = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        carregarDados();

        // Se ainda nao ha dados guardados, comeca com um catalogo de exemplo (so carros esportivos)
        if (catalogo.isEmpty()) {
            catalogo.add(new CarroImportado("Nissan", "GT-R R35", 2020, 32000.0, 45000, Origem.JAPAO));
            catalogo.add(new CarroImportado("Toyota", "Supra GR", 2022, 38000.0, 12000, Origem.JAPAO));
            catalogo.add(new CarroImportado("Honda", "NSX", 2021, 45000.0, 8000, Origem.JAPAO));
            catalogo.add(new CarroImportado("Mazda", "RX-7 FD", 1999, 9000.0, 120000, Origem.JAPAO));
            catalogo.add(new CarroImportado("Subaru", "WRX STI", 2016, 14000.0, 60000, Origem.JAPAO));
            catalogo.add(new CarroImportado("Porsche", "911 Carrera S", 2020, 55000.0, 25000, Origem.ALEMANHA));
            catalogo.add(new CarroImportado("BMW", "M4 Competition", 2023, 48000.0, 15000, Origem.ALEMANHA));
            catalogo.add(new CarroImportado("Mercedes-AMG", "GT", 2019, 52000.0, 30000, Origem.ALEMANHA));
            catalogo.add(new CarroImportado("Audi", "R8 V10", 2021, 60000.0, 18000, Origem.ALEMANHA));
            catalogo.add(new CarroImportado("Volkswagen", "Golf GTI", 2022, 22000.0, 20000, Origem.ALEMANHA));
            catalogo.add(new CarroImportado("Ferrari", "488 GTB", 2018, 120000.0, 22000, Origem.ITALIA));
            catalogo.add(new CarroImportado("Lamborghini", "Huracan", 2020, 150000.0, 10000, Origem.ITALIA));
            catalogo.add(new CarroImportado("Alfa Romeo", "4C", 2017, 35000.0, 40000, Origem.ITALIA));
            catalogo.add(new CarroImportado("Ford", "Mustang GT", 2024, 28000.0, 5000, Origem.AFRICA_DO_SUL));
            catalogo.add(new CarroImportado("Chevrolet", "Corvette C8", 2025, 65000.0, 3000, Origem.AFRICA_DO_SUL));
            catalogo.add(new CarroImportado("Toyota", "GR86", 2026, 26000.0, 1000, Origem.MAPUTO));
        }

        // Clientes de exemplo (contactos e datas ficticias, so para teste)
        if (clientes.isEmpty()) {
            clientes.add(new Cliente("Egas Lemos", "841234561", "12/03/1998", ""));
            clientes.add(new Cliente("Guideone Madeira", "842345672", "25/07/1995", ""));
            clientes.add(new Cliente("Almiro Madeira", "843456783", "09/11/2000", ""));
            clientes.add(new Cliente("Leyla da Gloria", "844567894", "18/05/1999", ""));
            clientes.add(new Cliente("Adriano Junior", "845678905", "03/02/1997", ""));
            clientes.add(new Cliente("Higino Baptista", "846789016", "30/09/1996", ""));
            clientes.add(new Cliente("Alberto Alface", "847890127", "14/06/2001", ""));
            clientes.add(new Cliente("Joana Uelemo", "848901238", "21/01/1998", ""));
            clientes.add(new Cliente("Alberto Charua", "849012349", "07/12/1994", ""));
        }

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> registarCarro();
                case 2 -> registarMoto();
                case 3 -> registarCliente();
                case 4 -> listarCatalogo(catalogo);
                case 5 -> pesquisarPorMarca();
                case 6 -> ordenarCatalogo();
                case 7 -> marcarVendido();
                case 8 -> removerVeiculo();
                case 9 -> listarClientes();
                case 10 -> removerCliente();
                case 11 -> mostrarEstatisticas();
                case 12 -> {
                    System.out.println("A gerar site atualizado...");
                    gerarSiteHTML();
                    System.out.println("Pronto! Abre ou atualiza (F5) o teu 'index.html' no navegador.");
                }
                case 0 -> {
                    salvarDados();
                    System.out.println("Dados guardados. Ate ja!");
                }
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);

        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== IT IMPORTS - SISTEMA GESTOR ===");
        System.out.println("1.  Registar Novo Carro");
        System.out.println("2.  Registar Nova Moto");
        System.out.println("3.  Registar Novo Cliente Interessado");
        System.out.println("4.  Listar Catalogo Completo");
        System.out.println("5.  Pesquisar Veiculo por Marca");
        System.out.println("6.  Ordenar Catalogo (preco/ano)");
        System.out.println("7.  Marcar Veiculo como Vendido");
        System.out.println("8.  Remover Veiculo");
        System.out.println("9.  Listar Clientes Registados");
        System.out.println("10. Remover Cliente");
        System.out.println("11. Ver Estatisticas do Negocio");
        System.out.println("12. Gerar/Atualizar Site HTML");
        System.out.println("0.  Sair (guarda os dados automaticamente)");
    }

    // ===================== REGISTO =====================

    private static void registarCarro() {
        System.out.println("\n-- Novo Carro --");
        String marca = lerTexto("Marca: ");
        String modelo = lerTexto("Modelo: ");
        int ano = lerInteiro("Ano: ");
        double preco = lerDouble("Preco de Origem ($): ");
        int km = lerInteiro("Quilometragem (KM): ");
        Origem origem = lerOrigem();
        catalogo.add(new CarroImportado(marca, modelo, ano, preco, km, origem));
        salvarDados();
        System.out.println("Carro adicionado com sucesso ao catalogo!");
    }

    private static void registarMoto() {
        System.out.println("\n-- Nova Moto --");
        String marca = lerTexto("Marca: ");
        String modelo = lerTexto("Modelo: ");
        int ano = lerInteiro("Ano: ");
        double preco = lerDouble("Preco de Origem ($): ");
        int km = lerInteiro("Quilometragem (KM): ");
        int cilindrada = lerInteiro("Cilindrada (cc): ");
        Origem origem = lerOrigem();
        catalogo.add(new MotoImportada(marca, modelo, ano, preco, km, cilindrada, origem));
        salvarDados();
        System.out.println("Moto adicionada com sucesso ao catalogo!");
    }

    // Mostra a lista de origens e devolve a escolha valida do utilizador
    private static Origem lerOrigem() {
        System.out.println("De onde vem o veiculo?");
        Origem.mostrarOpcoes();
        while (true) {
            int escolha = lerInteiro("Escolha a origem: ");
            Origem origem = Origem.porIndice(escolha);
            if (origem != null) return origem;
            System.out.println("Opcao invalida, tenta novamente.");
        }
    }

    private static void registarCliente() {
        System.out.println("\n-- Novo Cliente --");
        String nome = lerTexto("Nome do Cliente: ");
        String celular = lerTexto("Numero de Celular: ");
        String data = lerTexto("Data de Nascimento (DD/MM/AAAA): ");
        String interesse = lerTexto("Veiculo de interesse (opcional, ENTER para saltar): ");
        clientes.add(new Cliente(nome, celular, data, interesse));
        salvarDados();
        System.out.println("Cliente registado com sucesso!");
    }

    // ===================== CONSULTAS =====================

    private static void listarCatalogo(List<Veiculo> lista) {
        System.out.println("\n-- Catalogo (" + lista.size() + " veiculo(s)) --");
        if (lista.isEmpty()) {
            System.out.println("Nenhum veiculo encontrado.");
            return;
        }
        for (Veiculo v : lista) {
            System.out.println(v.getTipo() + " " + v);
        }
    }

    private static void pesquisarPorMarca() {
        String marca = lerTexto("\nMarca a pesquisar: ");
        List<Veiculo> resultado = new ArrayList<>();
        for (Veiculo v : catalogo) {
            if (v.getMarca().equalsIgnoreCase(marca)) {
                resultado.add(v);
            }
        }
        listarCatalogo(resultado);
    }

    private static void ordenarCatalogo() {
        System.out.println("\n1. Preco final (menor -> maior)");
        System.out.println("2. Ano (mais recente primeiro)");
        int op = lerInteiro("Escolha o criterio: ");
        if (op == 1) {
            catalogo.sort(Comparator.comparingDouble(Veiculo::calcularPrecoFinal));
        } else if (op == 2) {
            catalogo.sort(Comparator.comparingInt(Veiculo::getAno).reversed());
        } else {
            System.out.println("Opcao invalida.");
            return;
        }
        listarCatalogo(catalogo);
    }

    private static void marcarVendido() {
        int id = lerInteiro("\nID do veiculo vendido: ");
        for (Veiculo v : catalogo) {
            if (v.getId() == id) {
                if (v.isVendido()) {
                    System.out.println("Este veiculo ja estava marcado como vendido.");
                    return;
                }
                v.marcarComoVendido();
                salvarDados();
                System.out.printf("Vendido! Preco de venda: $%.2f  |  Lucro (25%%): $%.2f%n",
                        v.calcularPrecoVenda(), v.calcularLucro());
                return;
            }
        }
        System.out.println("Veiculo nao encontrado.");
    }

    private static void removerVeiculo() {
        int id = lerInteiro("\nID do veiculo a remover: ");
        boolean removido = catalogo.removeIf(v -> v.getId() == id);
        if (removido) {
            salvarDados();
            System.out.println("Veiculo removido.");
        } else {
            System.out.println("Veiculo nao encontrado.");
        }
    }

    private static void listarClientes() {
        System.out.println("\n-- Clientes (" + clientes.size() + ") --");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente registado.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    private static void removerCliente() {
        int id = lerInteiro("\nID do cliente a remover: ");
        boolean removido = clientes.removeIf(c -> c.getId() == id);
        if (removido) {
            salvarDados();
            System.out.println("Cliente removido.");
        } else {
            System.out.println("Cliente nao encontrado.");
        }
    }

    private static void mostrarEstatisticas() {
        System.out.println("\n-- Estatisticas do Negocio --");
        long disponiveis = catalogo.stream().filter(v -> !v.isVendido()).count();
        long vendidos = catalogo.stream().filter(Veiculo::isVendido).count();
        double valorTotalDisponivel = catalogo.stream().filter(v -> !v.isVendido())
                .mapToDouble(Veiculo::calcularPrecoFinal).sum();
        double lucroTotal = catalogo.stream().filter(Veiculo::isVendido)
                .mapToDouble(Veiculo::calcularLucro).sum();
        double faturacaoTotal = catalogo.stream().filter(Veiculo::isVendido)
                .mapToDouble(Veiculo::calcularPrecoVenda).sum();

        System.out.println("Veiculos no catalogo: " + catalogo.size());
        System.out.println("  Disponiveis: " + disponiveis + "   Vendidos: " + vendidos);
        System.out.printf("Valor em stock (disponiveis): $%.2f%n", valorTotalDisponivel);
        System.out.printf("Faturacao total (vendidos): $%.2f%n", faturacaoTotal);
        System.out.printf("Lucro total acumulado (25%% por venda): $%.2f%n", lucroTotal);
        System.out.println("Clientes registados: " + clientes.size());

        catalogo.stream().max(Comparator.comparingDouble(Veiculo::calcularPrecoFinal))
                .ifPresent(v -> System.out.println("Veiculo mais caro: " + v.getMarca() + " " + v.getModelo()));
    }

    // ===================== PERSISTENCIA =====================

    private static void salvarDados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHEIRO_DADOS))) {
            for (Veiculo v : catalogo) {
                if (v instanceof CarroImportado c) {
                    writer.write(String.join(";", "CARRO", "" + c.getId(), c.getMarca(), c.getModelo(),
                            "" + c.getAno(), "" + c.getPrecoOrigem(), "" + c.getQuilometragem(), "" + c.isVendido(),
                            c.getOrigem().name()));
                    writer.newLine();
                } else if (v instanceof MotoImportada m) {
                    writer.write(String.join(";", "MOTO", "" + m.getId(), m.getMarca(), m.getModelo(),
                            "" + m.getAno(), "" + m.getPrecoOrigem(), "" + m.getQuilometragem(), "" + m.isVendido(),
                            "" + m.getCilindrada(), m.getOrigem().name()));
                    writer.newLine();
                }
            }
            for (Cliente c : clientes) {
                String interesse = (c.getVeiculoInteresse() == null) ? "" : c.getVeiculoInteresse();
                writer.write(String.join(";", "CLIENTE", "" + c.getId(), c.getNome(), c.getCelular(),
                        c.getDataNascimento(), interesse));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao guardar dados: " + e.getMessage());
        }
    }

    private static void carregarDados() {
        java.io.File ficheiro = new java.io.File(FICHEIRO_DADOS);
        if (!ficheiro.exists()) return;

        int linhasIgnoradas = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(ficheiro))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                // Cada linha e lida isoladamente: se uma linha estiver num formato antigo
                // ou corrompido, ignora-se so essa linha em vez de travar o programa todo.
                try {
                    String[] p = linha.split(";", -1);
                    switch (p[0]) {
                        case "CARRO" -> catalogo.add(new CarroImportado(Integer.parseInt(p[1]), p[2], p[3],
                                Integer.parseInt(p[4]), Double.parseDouble(p[5]), Integer.parseInt(p[6]),
                                Boolean.parseBoolean(p[7]), Origem.valueOf(p[8])));
                        case "MOTO" -> catalogo.add(new MotoImportada(Integer.parseInt(p[1]), p[2], p[3],
                                Integer.parseInt(p[4]), Double.parseDouble(p[5]), Integer.parseInt(p[6]),
                                Boolean.parseBoolean(p[7]), Integer.parseInt(p[8]), Origem.valueOf(p[9])));
                        case "CLIENTE" -> clientes.add(new Cliente(Integer.parseInt(p[1]), p[2], p[3], p[4],
                                p.length > 5 ? p[5] : ""));
                        default -> linhasIgnoradas++;
                    }
                } catch (RuntimeException erroLinha) {
                    linhasIgnoradas++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }

        if (linhasIgnoradas > 0) {
            System.out.println("Aviso: " + linhasIgnoradas + " linha(s) do dados.txt estavam num formato antigo/invalido e foram ignoradas.");
        }
    }

    // ===================== LEITURA SEGURA =====================

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido, introduz um numero inteiro.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(teclado.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido, introduz um numero (ex: 15000.50).");
            }
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return teclado.nextLine().trim();
    }

    // ===================== SITE =====================

    private static void gerarSiteHTML() {
        long disponiveis = catalogo.stream().filter(v -> !v.isVendido()).count();
        double valorStock = catalogo.stream().filter(v -> !v.isVendido()).mapToDouble(Veiculo::calcularPrecoFinal).sum();

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"pt\">\n<head>\n");
        html.append("<meta charset=\"UTF-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        html.append("<title>IT Imports</title>\n");
        html.append("<style>\n").append(CSS).append("\n</style>\n</head>\n<body>\n");

        html.append("<header class=\"topbar\"><div class=\"wrap\"><span class=\"brand\">IT Imports</span>");
        html.append("<span class=\"tag\">Importacao de veiculos - Beira</span></div></header>\n");

        html.append("<section class=\"hero\"><div class=\"wrap\">");
        html.append("<h1>Carros e motos importados,<br>prontos para a Beira.</h1>");
        html.append("<p class=\"lede\">Selecionamos veiculos no exterior e tratamos de todo o processo de importacao ate chegar ate si.</p>");
        html.append("<div class=\"stats\">");
        html.append("<div class=\"stat\"><strong>").append(disponiveis).append("</strong><span>Disponiveis</span></div>");
        html.append("<div class=\"stat\"><strong>").append(clientes.size()).append("</strong><span>Clientes</span></div>");
        html.append("<div class=\"stat\"><strong>$").append(String.format("%,.0f", valorStock)).append("</strong><span>Em stock</span></div>");
        html.append("</div></div></section>\n");

        html.append("<section class=\"catalogo\"><div class=\"wrap\">\n<h2>Catalogo</h2>\n<div class=\"grid\">\n");
        for (Veiculo v : catalogo) {
            String estadoClasse = v.isVendido() ? "badge vendido" : "badge disponivel";
            String estadoTexto = v.isVendido() ? "Vendido" : "Disponivel";
            html.append("<article class=\"card\">\n");
            html.append("  <div class=\"card-top\"><span class=\"tipo\">").append(v.getTipo()).append("</span>");
            html.append("<span class=\"").append(estadoClasse).append("\">").append(estadoTexto).append("</span></div>\n");
            html.append("  <h3>").append(v.getMarca()).append(" ").append(v.getModelo()).append("</h3>\n");
            html.append("  <p class=\"meta\">").append(v.getAno()).append(" - ")
                    .append(String.format("%,d", v.getQuilometragem())).append(" km</p>\n");
            html.append("  <p class=\"origem\">Importado de ").append(v.getOrigem().getNomeExibicao())
                    .append(" - custo de importacao $").append(String.format("%,.2f", v.getOrigem().getCustoImportacao())).append("</p>\n");
            html.append("  <p class=\"price\">$").append(String.format("%,.2f", v.calcularPrecoFinal())).append("</p>\n");
            html.append("  <p class=\"price-sub\">Preco base: $").append(String.format("%,.2f", v.getPrecoOrigem())).append("</p>\n");
            if (v.isVendido()) {
                html.append("  <p class=\"lucro\">Lucro da venda: $").append(String.format("%,.2f", v.calcularLucro())).append("</p>\n");
            }
            html.append("</article>\n");
        }
        html.append("</div></div></section>\n");

        html.append("<section class=\"clientes\"><div class=\"wrap\">\n<h2>Clientes interessados</h2>\n");
        if (clientes.isEmpty()) {
            html.append("<p class=\"empty\">Ainda nao ha clientes registados.</p>\n");
        } else {
            html.append("<table>\n<thead><tr><th>Nome</th><th>Contacto</th><th>Interesse</th></tr></thead>\n<tbody>\n");
            for (Cliente c : clientes) {
                String interesse = (c.getVeiculoInteresse() == null || c.getVeiculoInteresse().isBlank()) ? "-" : c.getVeiculoInteresse();
                html.append("<tr><td>").append(c.getNome()).append("</td><td>").append(c.getCelular())
                        .append("</td><td>").append(interesse).append("</td></tr>\n");
            }
            html.append("</tbody>\n</table>\n");
        }
        html.append("</div></section>\n");

        html.append("<footer><div class=\"wrap\">IT Imports - Beira, Mocambique</div></footer>\n");
        html.append("</body>\n</html>");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("index.html"))) {
            writer.write(html.toString());
        } catch (IOException e) {
            System.out.println("Erro ao gravar HTML: " + e.getMessage());
        }
    }

    private static final String CSS = """
        :root{
          --bg:#ffffff; --ink:#1d1d1f; --muted:#6e6e73; --line:#e5e5e7;
          --accent:#0071e3; --ok:#1d7a3d; --warn:#8a8a8e; --card:#fafafa;
        }
        *{box-sizing:border-box;}
        body{margin:0;background:var(--bg);color:var(--ink);
          font-family:-apple-system,BlinkMacSystemFont,"SF Pro Display","Segoe UI",Helvetica,Arial,sans-serif;
          -webkit-font-smoothing:antialiased;}
        .wrap{max-width:1080px;margin:0 auto;padding:0 24px;}
        .topbar{border-bottom:1px solid var(--line);position:sticky;top:0;background:rgba(255,255,255,.85);backdrop-filter:blur(10px);z-index:10;}
        .topbar .wrap{display:flex;align-items:center;justify-content:space-between;height:52px;}
        .brand{font-weight:600;letter-spacing:-.01em;font-size:15px;}
        .tag{color:var(--muted);font-size:13px;}
        .hero{padding:96px 0 72px;text-align:center;}
        .hero h1{font-size:44px;line-height:1.1;font-weight:600;letter-spacing:-.02em;margin:0 0 18px;}
        .hero .lede{color:var(--muted);font-size:19px;max-width:560px;margin:0 auto 40px;line-height:1.5;}
        .stats{display:flex;justify-content:center;gap:56px;}
        .stat{display:flex;flex-direction:column;}
        .stat strong{font-size:28px;font-weight:600;}
        .stat span{color:var(--muted);font-size:13px;margin-top:2px;}
        section h2{font-size:28px;font-weight:600;letter-spacing:-.01em;margin:0 0 28px;}
        .catalogo{padding:64px 0;background:var(--card);border-top:1px solid var(--line);border-bottom:1px solid var(--line);}
        .grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(240px,1fr));gap:16px;}
        .card{background:#fff;border:1px solid var(--line);border-radius:16px;padding:22px;transition:transform .15s ease, box-shadow .15s ease;}
        .card:hover{transform:translateY(-2px);box-shadow:0 8px 24px rgba(0,0,0,.06);}
        .card-top{display:flex;justify-content:space-between;align-items:center;margin-bottom:14px;}
        .tipo{font-size:12px;color:var(--muted);text-transform:uppercase;letter-spacing:.04em;}
        .badge{font-size:12px;padding:3px 10px;border-radius:100px;font-weight:500;}
        .badge.disponivel{background:#eaf7ee;color:var(--ok);}
        .badge.vendido{background:#f0f0f2;color:var(--warn);}
        .card h3{margin:0 0 4px;font-size:19px;font-weight:600;letter-spacing:-.01em;}
        .meta{color:var(--muted);font-size:14px;margin:0 0 8px;}
        .origem{color:var(--muted);font-size:13px;margin:0 0 14px;}
        .price{font-size:22px;font-weight:600;margin:0;}
        .price-sub{color:var(--muted);font-size:13px;margin:2px 0 0;}
        .lucro{color:var(--ok);font-size:13px;font-weight:500;margin:6px 0 0;}
        .clientes{padding:64px 0;}
        table{width:100%;border-collapse:collapse;font-size:15px;}
        th{text-align:left;color:var(--muted);font-weight:500;font-size:13px;text-transform:uppercase;letter-spacing:.03em;padding:0 0 12px;border-bottom:1px solid var(--line);}
        td{padding:14px 0;border-bottom:1px solid var(--line);}
        tr:last-child td{border-bottom:none;}
        .empty{color:var(--muted);}
        footer{border-top:1px solid var(--line);padding:28px 0;color:var(--muted);font-size:13px;text-align:center;}
        @media(max-width:600px){.hero h1{font-size:32px;}.stats{gap:32px;}}
        """;
}
