import java.io.Serializable;

public class Veiculo implements Serializable {

    private String marca;
    private String modelo;
    private String matricula;
    private int anoPrimeiraMatricula;
    private float distanciaPercorrida;
    private boolean isDisponivel;

    public Veiculo(String aMarca, String aModelo, String aMatricula, int aAnoPrimeiraMatricula,
            float aDistanciaPercorrida, boolean aIsDisponivel) {

        this.marca = aMarca;
        this.modelo = aModelo;
        this.matricula = aMatricula;
        this.anoPrimeiraMatricula = aAnoPrimeiraMatricula;
        this.distanciaPercorrida = aDistanciaPercorrida;
        this.isDisponivel = true;

    }

    // Getters e Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String aMarca) {
        this.marca = aMarca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String aModelo) {
        this.modelo = aModelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String aMatricula) {
        this.matricula = aMatricula;
    }

    public int getAnoPrimeiraMatricula() {
        return anoPrimeiraMatricula;
    }

    public void setAnoPrimeiraMatricula(int aAnoPrimeiraMatricula) {
        this.anoPrimeiraMatricula = aAnoPrimeiraMatricula;
    }

    public float getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public void setDistanciaPercorrida(float aDistanciaPercorrida) {
        this.distanciaPercorrida = aDistanciaPercorrida;
    }

    public boolean isDisponivel() {
        return isDisponivel;
    }

    public void setDisponivel(boolean aIsDisponivel) {
        this.isDisponivel = aIsDisponivel;
    }

    public String toString() {

        return "Marca: " + this.marca + "\nModelo: " + this.modelo + "\nMatricula: " + this.matricula
                + "\nAno de primeira matricula: " + this.anoPrimeiraMatricula + "\n";
    }
}
