import java.util.ArrayList;
import java.util.List;

abstract class EmendaParlamentar {
    private String id;
    private String autor;
    private String tipo;
    private int ano;
    private double valorTotal;
    private String programa;
    private List<Documento> documentos;

    public EmendaParlamentar(String id, String autor, String tipo, int ano, String programa) {
        this.id = id;
        this.autor = autor;
        this.tipo = tipo;
        this.ano = ano;
        this.programa = programa;
        this.documentos = new ArrayList<>();
        this.valorTotal = 0;
    }

    public void adicionarDocumento(Documento doc) {
        documentos.add(doc);
        valorTotal += doc.getValor();
    }

    public double calcularValorPorFase(String fase) {
        double soma = 0;
        for (Documento d : documentos) {
            if (d.getFase().equalsIgnoreCase(fase)) {
                soma += d.getValor();
            }
        }
        return soma;
    }

    public void imprimirDocumentos() {
        for (Documento d : documentos) {
            System.out.println("   -> " + d);
        }
    }

    public String getId() { return id; }
    public String getAutor() { return autor; }
    public String getTipo() { return tipo; }
    public int getAno() { return ano; }
    public double getValorTotal() { return valorTotal; }
    public String getPrograma() { return programa; }
    public List<Documento> getDocumentos() { return documentos; }

    public abstract void imprimirDados();
}

class EmendaIndividual extends EmendaParlamentar {
    private String tipoTransferencia;

    public EmendaIndividual(String id, String autor, int ano, String programa, String tipoTransferencia) {
        super(id, autor, "Individual", ano, programa);
        this.tipoTransferencia = tipoTransferencia;
    }

    @Override
    public void imprimirDados() {
        System.out.println("=== Emenda Individual ===");
        System.out.println("ID: " + getId() + " | Autor: " + getAutor());
        System.out.println("Ano: " + getAno() + " | Programa: " + getPrograma());
        System.out.println("Tipo Transferência: " + tipoTransferencia);
        System.out.println("Valor Total: " + getValorTotal());
    }
}

class EmendaBancada extends EmendaParlamentar {
    private List<String> parlamentares;
    private String sugeridaPor;

    public EmendaBancada(String id, int ano, String programa, String sugeridaPor) {
        super(id, "Bancada", "Bancada", ano, programa);
        this.sugeridaPor = sugeridaPor;
        this.parlamentares = new ArrayList<>();
    }

    public void adicionarParlamentar(String nome) {
        parlamentares.add(nome);
    }

    @Override
    public void imprimirDados() {
        System.out.println("=== Emenda de Bancada ===");
        System.out.println("ID: " + getId() + " | Sugerida por: " + sugeridaPor);
        System.out.println("Ano: " + getAno() + " | Programa: " + getPrograma());
        System.out.println("Valor Total: " + getValorTotal());
        System.out.println("Parlamentares: " + parlamentares);
    }
}

class EmendaComissao extends EmendaParlamentar {
    private String linkComissao;
    private String linkRelatorio;
    private List<String> membros;

    public EmendaComissao(String id, String autor, int ano, String programa, String linkComissao, String linkRelatorio) {
        super(id, autor, "Comissão", ano, programa);
        this.linkComissao = linkComissao;
        this.linkRelatorio = linkRelatorio;
        this.membros = new ArrayList<>();
    }

    public void adicionarMembro(String nome) {
        membros.add(nome);
    }

    @Override
    public void imprimirDados() {
        System.out.println("=== Emenda de Comissão ===");
        System.out.println("ID: " + getId() + " | Autor: " + getAutor());
        System.out.println("Ano: " + getAno() + " | Programa: " + getPrograma());
        System.out.println("Valor Total: " + getValorTotal());
        System.out.println("Link Comissão: " + linkComissao);
        System.out.println("Relatório: " + linkRelatorio);
        System.out.println("Membros: " + membros);
    }
}

class EmendaRelator extends EmendaParlamentar {
    private String relator;
    
    public EmendaRelator(String id, String autor, int ano, String programa, String relator) {
        super(id, autor, "Relator", ano, programa);
        this.relator = relator;
    }

    @Override
    public void imprimirDados() {
        System.out.println("=== Emenda de Relator ===");
        System.out.println("ID: " + getId() + " | Autor: " + getAutor());
        System.out.println("Relator: " + relator);
        System.out.println("Ano: " + getAno() + " | Programa: " + getPrograma());
        System.out.println("Valor Total: " + getValorTotal());
    }
}

class Documento {
    private String data;
    private String fase;
    private double valor;

    public Documento(String data, String fase, double valor) {
        this.data = data;
        this.fase = fase;
        this.valor = valor;
    }

    public String getFase() { return fase; }
    public double getValor() { return valor; }

    @Override
    public String toString() {
        return "[Data: " + data + " | Fase: " + fase + " | Valor: " + valor + "]";
    }
}

public class Main {
    public static void main(String[] args) {
        // Emenda Individual
        EmendaIndividual e1 = new EmendaIndividual("E001", "João Campos", 2025, "Saúde", "Finalidade Definida");
        e1.adicionarDocumento(new Documento("2025-02-10", "Empenho", 500000));
        e1.adicionarDocumento(new Documento("2025-03-01", "Pagamento", 300000));
        e1.imprimirDados();
        e1.imprimirDocumentos();
        System.out.println("Total Pago: " + e1.calcularValorPorFase("Pagamento"));
        System.out.println();

        // Emenda Bancada
        EmendaBancada e2 = new EmendaBancada("E002", 2025, "Educação", "Marília Arraes");
        e2.adicionarParlamentar("João Campos");
        e2.adicionarParlamentar("Fernando Monteiro");
        e2.adicionarDocumento(new Documento("2025-02-15", "Empenho", 1000000));
        e2.imprimirDados();
        e2.imprimirDocumentos();
        System.out.println();

        // Emenda Comissão
        EmendaComissao e3 = new EmendaComissao("E003", "Silvio Costa Filho", 2025, "Infraestrutura",
                "www.linkComissao.com.exemplo", "www.linkRelatorio.com.exemplo");
        e3.adicionarMembro("Túlio Gadêlha");
        e3.adicionarDocumento(new Documento("2025-02-20", "Liquidação", 200000));
        e3.imprimirDados();
        e3.imprimirDocumentos();
        System.out.println();

    }
}