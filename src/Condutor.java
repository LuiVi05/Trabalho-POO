import java.time.LocalTime;

public class Condutor extends Utilizador {

    private int nServicos;
    private float distanciaTotalServicos;
    private LocalTime duracaoTotalServicos;
    private float custoTotalServicos;
    private String cartaConducao;
    private double latitude;
    private double longitude;
    private boolean isDisponivel;
    private Veiculo veiculo;

    public Condutor(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aCartaConducao,
            boolean aIsDisponivel) {

        super(aLogin, aPassword, aNome, aEstado, aEmail, "Condutor");
        this.cartaConducao = aCartaConducao;
        this.isDisponivel = true;
    }

    public int compareByDistancia(Condutor aCondutor) {
        return Float.compare(this.distanciaTotalServicos, aCondutor.distanciaTotalServicos);
    }

    public int compareByNumServicos(Condutor aCondutor) {
        return Integer.compare(this.nServicos, aCondutor.nServicos);
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

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public boolean setVeiculo(Veiculo aVeiculo) {
        if (aVeiculo != null) {
            this.veiculo = aVeiculo;
            return true;
        }
        return false;
    }

    public boolean isDisponivel() {
        return isDisponivel;
    }

    public void setDisponivel(boolean aIsDisponivel) {
        this.isDisponivel = aIsDisponivel;
    }

    public String toString() {

        return super.toString() + "\nCarta de Conducao: " + this.cartaConducao + "\nLatitude: " + this.latitude
                + "\nLongitude: " + this.longitude + "\nDisponivel: " + this.isDisponivel + "\n";
    }
}
