import java.util.ArrayList;
import java.io.Serializable;

public class GereTrajeto implements Serializable {

    private ArrayList<Trajeto> trajetos;

    public GereTrajeto() {
        this.trajetos = new ArrayList<Trajeto>();
    }

    public Trajeto criarTrajeto(double aLatitudeOrigem, double aLongitudeOrigem) {

        Trajeto novoTrajeto = new Trajeto(aLatitudeOrigem, aLongitudeOrigem);

        if (trajetos.add(novoTrajeto)) {

            return novoTrajeto;
        }
        return null;
    }

    public ArrayList<Trajeto> listarTrajetos() {
        return trajetos;
    }
}
