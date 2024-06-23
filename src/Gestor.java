    public class Gestor extends Utilizador {
        
        public Gestor(String aLogin, String aPassword, String aNome, int aEstado, String aEmail) {
            
            super(aLogin, aPassword, aNome, aEstado, aEmail, "Gestor");
        }

        public String toString() {

            return "Gestor: " + super.toString();
        }
    }
