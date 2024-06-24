import java.util.ArrayList;
import java.io.Serializable;

public class Servico implements Comparable<Servico>, Serializable {

    private static int contador = 0;
    private int id;
    private int estado; // 1-A espera de aprovacao do gestor 2-Aprovado pelo gestor 3-Aceite pelo
                        // condutor 4-Rejeitado pelo condutor 5-conluido pelo condutor 6-confirmado pelo
                        // cliente
    private Cliente cliente;
    private Condutor condutor;
    private Trajeto trajeto;
    private float valorTotal;
    private ArrayList<Condutor> condutoresQueRejeitaram;

    public Servico(Cliente cliente, Trajeto trajeto) {

        this.id = ++contador;
        this.estado = 1;
        this.cliente = cliente;
        this.trajeto = trajeto;
        this.valorTotal = 0;
        this.condutoresQueRejeitaram = new ArrayList<Condutor>();
    }

    public int compareTo(Servico servico) {
        return Integer.compare(this.id, servico.id);
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int aEstado) {
        this.estado = aEstado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente aCliente) {
        this.cliente = aCliente;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor aCondutor) {
        this.condutor = aCondutor;
    }

    public Trajeto getTrajeto() {
        return trajeto;
    }

    public void setTrajeto(Trajeto aTrajeto) {
        this.trajeto = aTrajeto;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float aValorTotal) {
        this.valorTotal = aValorTotal;
    }

    public ArrayList<Condutor> getCondutoresQueRejeitaram() {
        return condutoresQueRejeitaram;
    }

    public void addCondutorQueRejeitaram(Condutor condutor) {
        this.condutoresQueRejeitaram.add(condutor);
    }

    public String toString() {

        return id + "\nestado=" + estado + "\ncliente=" + cliente + "\ncondutor=" + condutor + "\ntrajeto=" + trajeto
                + "\nvalorTotal=" + valorTotal + "\n";
    }
}
