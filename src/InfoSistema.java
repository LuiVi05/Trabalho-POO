import java.io.Serializable;

public class InfoSistema implements Serializable {
    private int totalExecucoes;
    private String ultimoUtilizador;

    public InfoSistema(int totalExecucoes, String ultimoUtilizador) {
        this.totalExecucoes = totalExecucoes;
        this.ultimoUtilizador = ultimoUtilizador;
    }

    public int getTotalExecucoes() {
        return totalExecucoes;
    }

    public void setTotalExecucoes(int totalExecucoes) {
        this.totalExecucoes = totalExecucoes;
    }

    public String getUltimoUtilizador() {
        return ultimoUtilizador;
    }

    public void setUltimoUtilizador(String ultimoUtilizador) {
        this.ultimoUtilizador = ultimoUtilizador;
    }
}