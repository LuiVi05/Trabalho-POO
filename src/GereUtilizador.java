import java.util.ArrayList;
import java.util.Iterator;

public class GereUtilizador {
    
    private ArrayList<Utilizador> utilizadores;

    public GereUtilizador() {
        this.utilizadores = new ArrayList<Utilizador>();
    }

    public boolean criarGestor(String aLogin, String aPassword, String aNome, int aEstado, String aEmail) {
        return registarUtilizador(new Gestor(aLogin, aPassword, aNome, aEstado, aEmail));
    }

    public boolean criarCondutor(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aCartaConducao, boolean aIsDisponivel) {
        return registarUtilizador(new Condutor(aLogin, aPassword, aNome, aEstado, aEmail, aCartaConducao, aIsDisponivel));
    }

    public boolean criarCliente(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aMorada) {
        return registarUtilizador(new Cliente(aLogin, aPassword, aNome, aEstado, aEmail, aMorada));
    }

    public boolean registarUtilizador(Utilizador aUtilizador) {
        if (existeUtilizador(aUtilizador)) {
            return false;
        }
        aUtilizador.setEstado(1); // Estado 1 - pendente
        return utilizadores.add(aUtilizador);
    }

    public boolean existeUtilizador(Utilizador aUtilizador) {
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().equals(aUtilizador.getLogin()) || utilizador.getEmail().equals(aUtilizador.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public boolean aprovarUtilizador(String aLogin) {
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador aUtilizador = lista.next();
            if (aUtilizador.getLogin().equals(aLogin) && aUtilizador.getEstado() == 1) { // Estado 1 - pendente
                aUtilizador.setEstado(2); // Estado 2 - ativo
                return true;
            }
        }
        return false;
    }

    public boolean rejeitarUtilizador(String aLogin) {
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador aUtilizador = lista.next();
            if (aUtilizador.getLogin().equals(aLogin) && aUtilizador.getEstado() == 1) { // Estado 1 - pendente
                aUtilizador.setEstado(4); // Estado 4 - recusado
                return true;
            }
        }
        return false;
    }

    public boolean ativarUtilizador(String aLogin) {
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador aUtilizador = lista.next();
            if (aUtilizador.getLogin().equals(aLogin) && aUtilizador.getEstado() == 3) { // Estado 3 - inativo
                aUtilizador.setEstado(2); // Estado 2 - ativo
                return true;
            }
        }
        return false;
    }

    public boolean inativarUtilizador(String aLogin) {
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador aUtilizador = lista.next();
            if (aUtilizador.getLogin().equals(aLogin) && aUtilizador.getEstado() == 2) { // Estado 2 - ativo
                aUtilizador.setEstado(3); // Estado 3 - inativo
                return true;
            }
        }
        return false;
    }

    public int autenticarUtilizador(String aLogin, String aPassword) {
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador aUtilizador = lista.next();
            if (aUtilizador.getLogin().equals(aLogin) && aUtilizador.getPassword().equals(aPassword)) {
                switch (aUtilizador.getEstado()) {
                    case 1:
                        return 1; // Estado 1 - pendente
                    case 2:
                        return 2; // Estado 2 - ativo
                    case 3:
                        return 3; // Estado 3 - inativo
                    case 4:
                        return 4; // Estado 4 - recusado
                }
            }
        }
        return 0; // Utilizador não encontrado
    }

    // Get Utilizadores
    public ArrayList<Utilizador> getUtilizadores() {
        return this.utilizadores;
    }
}
