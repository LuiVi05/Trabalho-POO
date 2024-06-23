import java.io.Serializable;
import java.time.LocalDateTime;

public class Notificacao implements Serializable {
    private String mensagem;
    private LocalDateTime dataHora;
    private Utilizador destinatario;

    public Notificacao(String mensagem, Utilizador destinatario) {
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Utilizador getDestinatario() {
        return destinatario;
    }

    public String toString() {
        return "Notificacao: " + mensagem + " | Data e Hora: " + dataHora + " | Destinatario: " + destinatario.getLogin();
    }
}
