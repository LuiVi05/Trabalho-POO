public class Condutor extends Utilizador {
    
    private int nServicos;
    private float distanciaTotalServicos;
    private float duracaoTotalServicos;
    private float custoTotalServicos;
    private String cartaConducao;
    private double latitude;
    private double longitude;
    private boolean isDisponivel;

    public Condutor(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aCartaConducao, boolean aIsDisponivel) {

        super(aLogin, aPassword, aNome, aEstado, aEmail, "Condutor");
        this.cartaConducao = aCartaConducao;
        this.isDisponivel = true;
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

    public String getCartaConducao() {
        return cartaConducao;
    }

    public void setCartaConducao(String aCartaConducao) {
        this.cartaConducao = aCartaConducao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double aLatitude) {
        this.latitude = aLatitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double aLongitude) {
        this.longitude = aLongitude;
    }

    public boolean isDisponivel() {
        return isDisponivel;
    }

    public void setDisponivel(boolean aIsDisponivel) {
        this.isDisponivel = aIsDisponivel;
    }
}
