import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class GereVeiculo implements Serializable{

    private ArrayList<Veiculo> veiculos;

    public GereVeiculo() {
        this.veiculos = new ArrayList<Veiculo>();
    }
    
    public boolean criarVeiculo(String aMarca, String aModelo, String aMatricula, int aAnoPrimeiraMatricula, float aDistanciaPercorrida, boolean aIsDisponivel) {
        return registarVeiculo(new Veiculo(aMarca, aModelo, aMatricula, aAnoPrimeiraMatricula, aDistanciaPercorrida, aIsDisponivel));
    }

    public boolean registarVeiculo(Veiculo aVeiculo) {
        
        if(existeVeiculo(aVeiculo)) {

            return false;
        }
        return veiculos.add(aVeiculo);
    }

    public boolean existeVeiculo(Veiculo aVeiculo) {

        Iterator<Veiculo> lista = veiculos.iterator();
        while (lista.hasNext()) {
            Veiculo veiculo = lista.next();
            if (veiculo.getMatricula().equals(aVeiculo.getMatricula())) {
                return true;
            }
        }
        return false;
    }
    public ArrayList<Veiculo> getVeiculos() {
        return veiculos;
    }

}
