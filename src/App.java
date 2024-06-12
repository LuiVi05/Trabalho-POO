import java.util.Scanner;

public class App {

    GereUtilizador gereUtilizador = new GereUtilizador();

    public static void main(String[] args) {

        System.out.println("*****************************************\n" +
                   "*                                       *\n" +
                   "*             WELCOME TO                *\n" +
                   "*                                       *\n" +
                   "*              FoodDash                 *\n" +
                   "*                                       *\n" +
                   "*         Enjoy Your Meal!              *\n" +
                   "*                                       *\n" +
                   "*****************************************");
        wait(2000);
        clearScreen();
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {};
    }

    public static void wait(int aTempo) {
        try {
            Thread.sleep(aTempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        }
    }

    public static int leDadosInt(String aMensagem) {

        while (true) {

            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                return teclado.nextInt();
            } catch (Exception e) {
                System.out.println("Input invalido. Tente novamente.\n");

            }
        }
    }

    public static String leDadosString(String aMensagem, String aTipo) {
        while (true) {
            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                String input = teclado.nextLine();

                if (aTipo.equals("password")) {
                    if (input.length() >= 8 && input.matches(".*[A-Za-z].*") && input.matches(".*[0-9].*")) {
                        return input;
                    } else {
                        System.out.println(
                                "Password invalida. Deve ter pelo menos 8 caracteres e conter letras e numeros.\n");
                    }
                } else if (aTipo.equals("email")) {
                    if (input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        return input;
                    } else {
                        System.out.println("Email invalido. Tente novamente.\n");
                    }
                } else if (aTipo.equals("nif")) {
                    if (input.matches("\\d{9}")) {
                        return input;
                    } else {
                        System.out.println("NIF invalido. Deve conter exatamente 9 digitos.\n");
                    }
                } else if (aTipo.equals("contactoTelefonico")) {
                    if (input.matches("\\d{9,15}")) {
                        return input;
                    } else {
                        System.out.println("Contacto telefonico invalido. Deve conter entre 9 a 15 digitos.\n");
                    }
                } else {
                    return input;
                }
            } catch (Exception e) {
                System.out.println("Input invalido. Tente novamente.\n");
            }
        }
    }

    public static float leDadosFloat(String aMensagem) {

        while (true) {

            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                return teclado.nextFloat();
            } catch (Exception e) {
                System.out.println("Input invalido. Tente novamente.\n");

            }
        }
    }

    public static boolean leSimOuNao(String aMensagem) {

        while (true) {

            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                String resposta = teclado.nextLine();

                if (resposta.equalsIgnoreCase("s") || resposta.equalsIgnoreCase("n")) {
                    return resposta.equalsIgnoreCase("s") ? true : false;

                } else {
                    System.out.println("Input invalido. Tente novamente.\n");
                }
            } catch (Exception e) {
                System.out.println("Input invalido. Tente novamente.\n");
            }
        }
    }

}
