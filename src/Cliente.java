import java.time.LocalTime;

public class Cliente extends Utilizador {

    private int nServicos;
    private float distanciaTotalServicos;
    private LocalTime duracaoTotalServicos;
    private float custoTotalServicos;
    private String morada;
    private String NIF;

    public Cliente(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aMorada,
            String aNIF) {

        super(aLogin, aPassword, aNome, aEstado, aEmail, "Cliente");
        this.morada = aMorada;
        this.NIF = aNIF;
    }

    public int compareByDistancia(Cliente aCliente) {
        return Float.compare(this.distanciaTotalServicos, aCliente.distanciaTotalServicos);
    }

    public int compareByNumServicos(Cliente aCliente) {
        return Integer.compare(this.nServicos, aCliente.nServicos);
    }

    // Getters e Setters
    public int getNServicos() {
        return nServicos;
    }

    public void setNServicos(int aNServicos) {
        this.nServicos = aNServicos;
    }

    public float getDistanciaTotalServicos() {
        return distanciaTotalServicos;
    }

    public void setDistanciaTotalServicos(float aDistanciaTotalServicos) {
        this.distanciaTotalServicos = aDistanciaTotalServicos;
    }

    public LocalTime getDuracaoTotalServicos() {
        return duracaoTotalServicos;
    }

    public void setDuracaoTotalServicos(LocalTime aDuracaoTotalServicos) {
        this.duracaoTotalServicos = aDuracaoTotalServicos;
    }

    public float getCustoTotalServicos() {
        return custoTotalServicos;
    }

    public void setCustoTotalServicos(float aCustoTotalServicos) {
        this.custoTotalServicos = aCustoTotalServicos;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String aMorada) {
        this.morada = aMorada;
    }

    public String getNIF() {
        return NIF;
    }

    public String toString() {

        return super.toString() + "\nMorada: " + this.morada + "\nNIF: " + this.NIF + "\nN de Serviços: "
                + this.nServicos + "\nDistancia Total de Servicos: " + this.distanciaTotalServicos
                + "\nDuracao Total de Servicos: " + this.duracaoTotalServicos + "\nCusto Total de Servicos: "
                + this.custoTotalServicos + "\n";
    }
}
