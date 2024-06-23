import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class GereNotificacao implements Serializable {
    private ArrayList<Notificacao> notificacoes;

    public GereNotificacao() {
        this.notificacoes = new ArrayList<>();
    }

    public void adicionarNotificacao(Notificacao notificacao) {
        this.notificacoes.add(notificacao);
    }

    public ArrayList<Notificacao> getNotificacoesPorDestinatario(Utilizador destinatario) {
        ArrayList<Notificacao> resultado = new ArrayList<>();
        Iterator<Notificacao> iterator = notificacoes.iterator();
        while (iterator.hasNext()) {
            Notificacao notificacao = iterator.next();
            if (notificacao.getDestinatario().equals(destinatario)) {
                resultado.add(notificacao);
            }
        }
        return resultado;
    }

    public ArrayList<Notificacao> getTodasNotificacoes() {
        return this.notificacoes;
    }
}