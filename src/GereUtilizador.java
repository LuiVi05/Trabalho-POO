import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;


public class GereUtilizador implements Serializable{
    
    private ArrayList<Utilizador> utilizadores;

    public GereUtilizador() {
        this.utilizadores = new ArrayList<Utilizador>();
    }

    public boolean isUtilizadoresEmpty() {
        return utilizadores.isEmpty();
    }
    public boolean criarGestor(String aLogin, String aPassword, String aNome, int aEstado, String aEmail) {
        return registarUtilizador(new Gestor(aLogin, aPassword, aNome, aEstado, aEmail));
    }

    public boolean criarCondutor(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aCartaConducao, boolean aIsDisponivel) {
        return registarUtilizador(new Condutor(aLogin, aPassword, aNome, aEstado, aEmail, aCartaConducao, aIsDisponivel));
    }

    public boolean criarCliente(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aMorada, String aNIF) {
        return registarUtilizador(new Cliente(aLogin, aPassword, aNome, aEstado, aEmail, aMorada, aNIF));
    }

    public boolean registarUtilizador(Utilizador aUtilizador) {
        if (existeUtilizador(aUtilizador)) {
            return false;
        }
        
        boolean isCriado = utilizadores.add(aUtilizador);

        if (isCriado) {
            // Escrever credenciais em credenciais_acesso.txt
            try {
                FileWriter writer = new FileWriter("credenciais_acesso.txt", true);
                writer.write(aUtilizador.getLogin() + " <> " + aUtilizador.getPassword() + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return isCriado;
    }

    public boolean existeUtilizador(Utilizador aUtilizador) {

        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();

            if(aUtilizador instanceof Cliente && utilizador instanceof Cliente) {
                Cliente cliente = (Cliente) utilizador;
                Cliente aCliente = (Cliente) aUtilizador;

                if (cliente.getNIF().equals(aCliente.getNIF())) {
                    return true;
                }
            }

            if (utilizador.getLogin().equals(aUtilizador.getLogin()) || utilizador.getEmail().equals(aUtilizador.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public boolean aprovarUtilizador(String aLogin) {

        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().equals(aLogin) && utilizador.getEstado() == 1) { // Estado 1 - pendente
                utilizador.setEstado(2); // Estado 2 - ativo
                return true;
            }
        }
        return false;
    }

    public boolean rejeitarUtilizador(String aLogin) {

        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().equals(aLogin) && utilizador.getEstado() == 1) { // Estado 1 - pendente
                utilizador.setEstado(4); // Estado 4 - rejeitado
                return true;
            }
        }
        return false;
    }

    public boolean ativarUtilizador(String aLogin) {

        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().equals(aLogin) && utilizador.getEstado() == 3) { // Estado 3 - inativo
                utilizador.setEstado(2); // Estado 2 - ativo
                return true;
            }
        }
        return false;
    }

    public boolean inativarUtilizador(String aLogin) {

        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().equals(aLogin) && utilizador.getEstado() == 2) { // Estado 2 - ativo
                utilizador.setEstado(3); // Estado 3 - inativo
                return true;
            }
        }
        return false;
    }

    public Utilizador autenticarUtilizador(String aLogin, String aPassword) {

        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().equals(aLogin) && utilizador.getPassword().equals(aPassword)) {
                
                return utilizador;
            }
        }
        return null;
    }

    public boolean pedirRemocaoUtilizador(Utilizador aUtilizador) {

            if (aUtilizador != null && aUtilizador.getEstado() == 2) { // Estado 2 - ativo
                aUtilizador.setEstado(5); // Estado 5 - por eliminar
                return true;
            }
        
        return false;
    }

    public boolean aceitarPedidoRemocaoUtilizador(String aLogin) {
        
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().equals(aLogin) && utilizador.getEstado() == 5) { // Estado 5 - por eliminar        
                utilizadores.remove(utilizador);
                
                return true;
            }
        }
        return false;
    }

    public boolean rejeitarRemocaoConta(String aLogin) {

        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().equals(aLogin) && utilizador.getEstado() == 5) { // Estado 5 - por eliminar        
                utilizador.setEstado(2); // Estado 2 - ativo
                return true;
            }
        }
        return false;
    }

    public ArrayList<Condutor> getCondutoresQueNaoRejeitaram(Servico aServico) {
        
        ArrayList<Utilizador> condutores = getUtilizadoresByClassName("Condutor");

        ArrayList<Condutor> condutoresQueRejeitaram = aServico.getCondutoresQueRejeitaram();
        
        ArrayList<Condutor> resultado = new ArrayList<>();

        Iterator<Utilizador> lista = condutores.iterator();

        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador instanceof Condutor) { 

                Condutor condutor = (Condutor) utilizador;
                if (!condutoresQueRejeitaram.contains(condutor)) {
                    resultado.add(condutor);
                }
                
            }
        }
        return resultado;
        
    }

    public boolean alterarDadosUtilizador(Utilizador aUtilizador, String aAlteracao, int aOpAlteracao){

        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            
            if (utilizador.getLogin().equals(aUtilizador.getLogin())) {
                
                switch (aOpAlteracao) {
                    case 1:

                        String loginAuxiliar = utilizador.getLogin();

                        utilizador.setLogin(aAlteracao);

                        Iterator<Utilizador> listaVerifica = utilizadores.iterator();
                        while (lista.hasNext()) {
                            Utilizador utilizadorAtualizado = listaVerifica.next();
                            if (existeUtilizador(utilizadorAtualizado)) {

                                utilizador.setLogin(loginAuxiliar);
                                return false;
                            }
                        }
                        break;
                    case 2:

                        utilizador.setPassword(aAlteracao);
                        break;
                    case 3:

                        utilizador.setNome(aAlteracao);
                        break;
                    case 4:

                        String emailAuxiliar = utilizador.getEmail();

                        utilizador.setEmail(aAlteracao);

                        listaVerifica = utilizadores.iterator();
                        while(lista.hasNext()) {
                            Utilizador utilizadorAtualizado = lista.next();
                            if(existeUtilizador(utilizadorAtualizado)) {

                                utilizador.setEmail(emailAuxiliar);
                                return false;
                            }
                        }
                        break;
                    case 5:
                        if(utilizador.getTipoUtilizador().equals("Cliente")){

                            Cliente cliente = (Cliente) utilizador;
                            cliente.setMorada(aAlteracao);
                        } else if (utilizador.getTipoUtilizador().equals("Condutor")) {

                            Condutor condutor = (Condutor) utilizador;
                            condutor.setCartaConducao(aAlteracao);
                        }
                        break;
                }
                return true;
            }
        }
        return false;
    }

    // Get Utilizadores
    public ArrayList<Utilizador> getUtilizadores() {
        return this.utilizadores;
    }

    public ArrayList<Utilizador> getUtilizadoresByClassName(String aClassName) {

        ArrayList<Utilizador> utilizadoresByClassName = new ArrayList<Utilizador>();
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getTipoUtilizador().equals(aClassName) &&  (utilizador.getEstado() == 2 ||utilizador.getEstado() == 5)) {
                utilizadoresByClassName.add(utilizador);
            }
        }
        return utilizadoresByClassName;
    }

    public ArrayList<Utilizador> getUtilizadoresPorEstado(int aEstado) {
        ArrayList<Utilizador> utilizadoresPorEstado = new ArrayList<>();
        Iterator<Utilizador> iterator = utilizadores.iterator();
        while (iterator.hasNext()) {
            Utilizador utilizador = iterator.next();
            if (utilizador.getEstado() == aEstado) {
                utilizadoresPorEstado.add(utilizador);
            }
        }
        return utilizadoresPorEstado;
    }

    public Utilizador getUtilizadorPorLogin(String aLogin) {
        Iterator<Utilizador> iterator = utilizadores.iterator();
        while (iterator.hasNext()) {
            Utilizador utilizador = iterator.next();
            if (utilizador.getLogin().equals(aLogin)) {
                return utilizador;
            }
        }
        return null;
    }

    public ArrayList<Utilizador> getUtilizadoresSortedByNome() {
        ArrayList<Utilizador> sortedList = new ArrayList<>(this.utilizadores);
        sortedList.sort((u1, u2) -> u1.getNome().compareToIgnoreCase(u2.getNome()));
        return sortedList;
    }

    // lista de clientes por distancia
    public ArrayList<Cliente> getClientesSortedByDistancia() {
        ArrayList<Cliente> sortedList = new ArrayList<>();
        Iterator<Utilizador> lista = this.utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador instanceof Cliente) {
                sortedList.add((Cliente) utilizador);
            }
        }
        sortedList.sort((c1, c2) -> c1.compareByDistancia(c2));
        return sortedList;
    }

    // lista de clientes por n de servicos
    public ArrayList<Cliente> getClientesSortedByNumServicos() {
        ArrayList<Cliente> sortedList = new ArrayList<>();
        Iterator<Utilizador> lista = this.utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador instanceof Cliente) {
                sortedList.add((Cliente) utilizador);
            }
        }
        sortedList.sort((c1, c2) -> c1.compareByNumServicos(c2));
        return sortedList;
    }

    // lista de condutores por distancia
    public ArrayList<Condutor> getCondutoresSortedByDistancia() {
        ArrayList<Condutor> sortedList = new ArrayList<>();
        Iterator<Utilizador> lista = this.utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador instanceof Condutor) {
                sortedList.add((Condutor) utilizador);
            }
        }
        sortedList.sort((c1, c2) -> c1.compareByDistancia(c2));
        return sortedList;
    }

    // lista de condutores por n de servicos
    public ArrayList<Condutor> getCondutoresSortedByNumServicos() {
        ArrayList<Condutor> sortedList = new ArrayList<>();
        Iterator<Utilizador> lista = this.utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador instanceof Condutor) {
                sortedList.add((Condutor) utilizador);
            }
        }
        sortedList.sort((c1, c2) -> c1.compareByNumServicos(c2));
        return sortedList;
    }

    // Pesquisa avancada por login
    public ArrayList<Utilizador> pesquisarUtilizadoresPorLogin(String termo) {
        ArrayList<Utilizador> resultado = new ArrayList<>();
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador.getLogin().toLowerCase().contains(termo.toLowerCase())) {
                resultado.add(utilizador);
            }
        }
        return resultado;
    }

    // Pesquisa por habilitacao de conducao
    public ArrayList<Condutor> pesquisarCondutoresPorHabilitacao(String habilitacao) {
        ArrayList<Condutor> resultado = new ArrayList<>();
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador instanceof Condutor) {
                Condutor condutor = (Condutor) utilizador;
                if (condutor.getCartaConducao().equalsIgnoreCase(habilitacao)) {
                    resultado.add(condutor);
                }
            }
        }
        return resultado;
    }

    // Pesquisa por distancia acumulada
    public ArrayList<Condutor> pesquisarCondutoresPorDistanciaAcumulada(float distanciaLimite) {
        ArrayList<Condutor> resultado = new ArrayList<>();
        Iterator<Utilizador> lista = utilizadores.iterator();
        while (lista.hasNext()) {
            Utilizador utilizador = lista.next();
            if (utilizador instanceof Condutor) {
                Condutor condutor = (Condutor) utilizador;
                if (condutor.getDistanciaTotalServicos() > distanciaLimite) {
                    resultado.add(condutor);
                }
            }
        }
        return resultado;
    }
}

