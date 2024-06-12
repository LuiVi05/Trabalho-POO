public class Cliente extends Utilizador{

    private int nServicos;
    private float distanciaTotalServicos;
    private float duracaoTotalServicos;
    private float custoTotalServicos;
    private String morada;

    public Cliente(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aMorada) {

        super(aLogin, aPassword, aNome, aEstado, aEmail, "Cliente");
        this.morada = aMorada;
    }
    
    //Getters e Setters
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

    public float getDuracaoTotalServicos() {
        return duracaoTotalServicos;
    }

    public void setDuracaoTotalServicos(float aDuracaoTotalServicos) {
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
}
