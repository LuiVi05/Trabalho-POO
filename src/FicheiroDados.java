import java.io.Serializable;

public class FicheiroDados implements Serializable {

    GereUtilizador gereUtilizador = new GereUtilizador();
    GereServico gereServico = new GereServico();
    GereNotificacao gereNotificacao = new GereNotificacao();
    GereTrajeto gereTrajeto = new GereTrajeto();
    GereVeiculo gereVeiculo = new GereVeiculo();

    public FicheiroDados(GereUtilizador aGereUtilizador, GereServico aGereServico, GereNotificacao aGereNotificacao,
            GereTrajeto aGereTrajeto, GereVeiculo aGereVeiculo) {

        gereUtilizador = aGereUtilizador;
        gereServico = aGereServico;
        gereNotificacao = aGereNotificacao;
        gereTrajeto = aGereTrajeto;
        gereVeiculo = aGereVeiculo;

    }

    public GereNotificacao getGereNotificacao() {
        return gereNotificacao;
    }

    public GereServico getGereServico() {
        return gereServico;
    }

    public GereUtilizador getGereUtilizador() {
        return gereUtilizador;
    }

    public GereTrajeto getGereTrajeto() {
        return gereTrajeto;
    }

    public GereVeiculo getGereVeiculo() {
        return gereVeiculo;
    }

}
