import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.io.Serializable;

public class GereServico implements Serializable {

    private ArrayList<Servico> servicos;

    public GereServico() {
        this.servicos = new ArrayList<Servico>();
    }

    public boolean criarServico(Cliente cliente, Trajeto trajeto) {
        return servicos.add(new Servico(cliente, trajeto));
    }

    public boolean aprovarServico(Servico aServico, Condutor aCondutor) {

        if (aServico.getEstado() == 1 && aServico != null && aCondutor != null) { // Estado 1 - pendente
            aServico.setCondutor(aCondutor);
            aServico.setEstado(2); // Estado 2 - aprovado por gestor
            return true;
        }
        return false;
    }

    public boolean rejeitarServicoPorGestor(Servico aServico) {

        if (aServico != null) {
            Iterator<Servico> lista = servicos.iterator();
            while (lista.hasNext()) {

                Servico servico = lista.next();
                if (servico.getId() == aServico.getId()) {
                    lista.remove();
                    return true;
                }

            }
        }
        return false;
    }

    public boolean aceitarServico(Servico aServico, Condutor aCondutor) {

        if (aServico.getEstado() == 2 && aServico.getCondutor().equals(aCondutor)) { // Estado 2 - aprovado por gestor
            aServico.setEstado(3); // Estado 3 - aceito pelo condutor
            aServico.getCondutor().getVeiculo().setDisponivel(false);
            aCondutor.setDisponivel(false);
            return true;
        }
        return false;
    }

    public boolean rejeitarServicoPorCondutor(Servico aServico, Condutor aCondutor) {

        if (aServico.getEstado() == 2 && aServico.getCondutor().equals(aCondutor)) { // Estado 2 - aprovado por gestor
            aServico.setEstado(4); // Estado 4 - rejeitado pelo condutor
            aServico.addCondutorQueRejeitaram(aCondutor);
            return true;
        }
        return false;
    }

    public boolean atribuirNovoCondutor(Servico aServico, Condutor aCondutor) {

        if (aServico.getEstado() == 4 && !(aServico.getCondutor().equals(aCondutor))
                && !(aServico.getCondutoresQueRejeitaram().contains(aCondutor))) { // Estado 4 - rejeitado pelo condutor
            aServico.setCondutor(aCondutor);
            aServico.setEstado(2); // Estado 2 - aprovado por gestor

            return true;
        }
        return false;
    }

    public boolean concluirServico(Servico aServico, float aValorTotal, LocalTime aDuracao, float aDistancia) {

        if (aServico.getEstado() == 3) { // Estado 3 - aceito pelo condutor
            aServico.setEstado(5); // Estado 5 - concluído pelo condutor
            aServico.getCondutor().setDisponivel(true);
            aServico.getCondutor().getVeiculo().setDisponivel(true);
            aServico.setValorTotal(aValorTotal);
            aServico.getTrajeto().setDuracao(aDuracao);
            aServico.getTrajeto().setDistancia(aDistancia);

            return true;
        }
        return false;
    }

    public boolean confirmarFimServico(Servico aServico) {

        if (aServico.getEstado() == 5 && aServico != null) { // Estado 5 - concluído pelo condutor
            aServico.setEstado(6); // Estado 6 - confirmado pelo cliente
            return true;
        }
        return false;
    }

    public Servico getServico(int aId) {
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getId() == aId) {
                return servico;
            }
        }
        return null;
    }

    public ArrayList<Servico> getServicosPorEstado(int aEstado) {

        ArrayList<Servico> servicosEstado = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getEstado() == aEstado) {
                servicosEstado.add(servico);
            }
        }
        return servicosEstado;
    }

    public ArrayList<Servico> listarServicosPorCliente(Cliente aCliente) {
        ArrayList<Servico> servicosCliente = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getCliente().equals(aCliente)) {
                servicosCliente.add(servico);
            }
        }
        return servicosCliente;
    }

    public ArrayList<Servico> listarServicosPorCliente(Cliente aCliente, int aEstado) {
        ArrayList<Servico> servicosCliente = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getCliente().equals(aCliente) && servico.getEstado() == aEstado) {
                servicosCliente.add(servico);
            }
        }
        return servicosCliente;
    }

    public ArrayList<Servico> listarServicosPorCondutor(Condutor aCondutor) {

        ArrayList<Servico> servicosCondutor = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getCondutor().equals(aCondutor)) {
                servicosCondutor.add(servico);
            }
        }
        return servicosCondutor;
    }

    public ArrayList<Servico> listarServicosPorCondutor(Condutor aCondutor, int aEstado) {

        ArrayList<Servico> servicosCondutor = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getCondutor().equals(aCondutor) && servico.getEstado() == aEstado) {
                servicosCondutor.add(servico);
            }
        }
        return servicosCondutor;
    }

    public ArrayList<Servico> getServicos() {
        return servicos;
    }

    public ArrayList<Servico> getServicosConcluidosSortedByDistancia() {
        ArrayList<Servico> sortedList = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getEstado() == 6) { // Estado 6 - confirmado pelo cliente
                sortedList.add(servico);
            }
        }
        sortedList.sort((s1, s2) -> Float.compare(s1.getTrajeto().getDistancia(), s2.getTrajeto().getDistancia()));
        return sortedList;
    }

    public ArrayList<Servico> getServicosConcluidosSortedByDuracao() {
        ArrayList<Servico> sortedList = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getEstado() == 6) {
                sortedList.add(servico);
            }
        }
        sortedList.sort((s1, s2) -> s1.getTrajeto().getDuracao().compareTo(s2.getTrajeto().getDuracao()));
        return sortedList;
    }

    public ArrayList<Servico> getServicosConcluidosSortedByData() {
        ArrayList<Servico> sortedList = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getEstado() == 6) {
                sortedList.add(servico);
            }
        }
        sortedList.sort((s1, s2) -> s1.getTrajeto().getDia().compareTo(s2.getTrajeto().getDia()));
        return sortedList;
    }

    public ArrayList<Servico> getServicosConcluidosSortedByID() {
        ArrayList<Servico> sortedList = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getEstado() == 6) {
                sortedList.add(servico);
            }
        }
        sortedList.sort(Comparator.comparingInt(Servico::getId));
        return sortedList;
    }

    public ArrayList<Servico> getServicosConcluidosSortedByCusto() {
        ArrayList<Servico> sortedList = new ArrayList<>();
        Iterator<Servico> lista = servicos.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            if (servico.getEstado() == 6) {
                sortedList.add(servico);
            }
        }
        sortedList.sort((s1, s2) -> Float.compare(s1.getValorTotal(), s2.getValorTotal()));
        return sortedList;
    }

}
