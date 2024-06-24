import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class Trajeto implements Serializable {

    private double latitudeOrigem;
    private double longitudeOrigem;
    private LocalTime hora;
    private LocalDate dia;
    private LocalTime duracao;
    private float distancia;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Trajeto(double aLatitudeOrigem, double aLongitudeOrigem) {
        this.latitudeOrigem = aLatitudeOrigem;
        this.longitudeOrigem = aLongitudeOrigem;
        this.hora = LocalTime.now(); // Preenche automaticamente com a hora atual do sistema
        this.dia = LocalDate.now(); // Preenche automaticamente com a data atual do sistema
        this.duracao = LocalTime.of(0, 0, 0); // Inicialmente a duração é 0
        this.distancia = 0; // Inicialmente a distancia é 0
    }

    // Getters e Setters
    public double getLatitudeOrigem() {
        return latitudeOrigem;
    }

    public void setLatitudeOrigem(double aLatitudeOrigem) {
        this.latitudeOrigem = aLatitudeOrigem;
    }

    public double getLongitudeOrigem() {
        return longitudeOrigem;
    }

    public void setLongitudeOrigem(double aLongitudeOrigem) {
        this.longitudeOrigem = aLongitudeOrigem;
    }

    public LocalTime getHora() {
        return hora;
    }

    public LocalDate getDia() {
        return dia;
    }

    public LocalTime getDuracao() {
        return duracao;
    }

    public void setDuracao(LocalTime aDuracao) {
        this.duracao = aDuracao;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float aDistancia) {
        this.distancia = aDistancia;
    }

    public String getHoraFormatada() {
        return hora.format(timeFormatter);
    }

    public String getDiaFormatado() {
        return dia.format(dateFormatter);
    }

    public String getDuracaoFormatada() {
        return duracao.format(timeFormatter);
    }

    public String toString() {
        return "latitudeOrigem=" + latitudeOrigem + ", longitudeOrigem=" + longitudeOrigem
                + ", hora=" + getHoraFormatada() + ", dia=" + getDiaFormatado() + ", duracao="
                + getDuracaoFormatada() + ", distancia=" + distancia;
    }
}
