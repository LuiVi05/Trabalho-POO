import java.io.Serializable;

public class Utilizador implements Comparable<Utilizador>, Serializable{

    private String login;
    private String password;
    private String nome;
    private int estado; //1-pendente 2-ativo 3-inativo 4-rejeitado 5-por eliminar
    private String email; 
    private String tipoUtilizador;
    
    public Utilizador(String aLogin, String aPassword, String aNome, int aEstado, String aEmail, String aTipoUtilizador) {

        this.login = aLogin;
        this.password =  aPassword;
        this.nome = aNome;
        this.estado = aEstado;
        this.email = aEmail;
        this.tipoUtilizador = aTipoUtilizador;
    }

    public int compareTo(Utilizador aUtilizador) {
        return this.nome.toLowerCase().compareTo(aUtilizador.getNome().toLowerCase());
      }

    //Getters e Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String aLogin) {
        this.login = aLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String aPassword) {
        this.password = aPassword;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String aNome) {
        this.nome = aNome;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int aEstado) {
        this.estado = aEstado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String aEmail) {
        this.email = aEmail;
    }

    public String getTipoUtilizador() {
        return tipoUtilizador;
    }

    public String toString() {

        return "login: " + login + "\npassword=" + password + "\nnome=" + nome + "\nestado=" + estado + "\nemail=" + email + "\ntipoUtilizador=" + tipoUtilizador;
    }
}
