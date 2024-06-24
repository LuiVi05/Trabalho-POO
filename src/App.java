import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class App {

    static GereInterface gereInterface = new GereInterface();
    static GereUtilizador gereUtilizador = new GereUtilizador();
    static GereServico gereServico = new GereServico();
    static GereTrajeto gereTrajeto = new GereTrajeto();
    static GereVeiculo gereVeiculo = new GereVeiculo();
    static GereNotificacao gereNotificacao = new GereNotificacao();
    static InfoSistema infoSistema;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static String username;

    public static void main(String[] args) {
        username="sistema";
        infoSistema = carregarInfoSistema();
        infoSistema.setTotalExecucoes(infoSistema.getTotalExecucoes() + 1);

        System.out.println(gereInterface.getInterface(1));
        System.out.println("Numero total de execucoes: " + infoSistema.getTotalExecucoes());
        System.out.println("Ultimo utilizador: " + infoSistema.getUltimoUtilizador());
        wait(2000);
        clearScreen();

        FicheiroDados ficheiroDados = carregarData();

        if (ficheiroDados != null) {
            gereServico = ficheiroDados.getGereServico();
            gereTrajeto = ficheiroDados.getGereTrajeto();
            gereVeiculo = ficheiroDados.getGereVeiculo();
            gereUtilizador = ficheiroDados.getGereUtilizador();
            gereNotificacao = ficheiroDados.getGereNotificacao();
            registarAcao(username, "dados carregados com sucesso");
        } else {
            registarAcao(username, "falha ao carregar dados");
        }

        while (gereUtilizador.isUtilizadoresEmpty()) {
            System.out.println("Nenhum utilizador encontrado no sistema. Crie um gestor porfavor.\n");

            if (gereUtilizador.criarGestor(leDadosString("Introduza o seu username: ", ""),
                    leDadosString("Introduza a sua password: ", "password"),
                    leDadosString("Introduza o seu nome: ", ""), 2,
                    leDadosString("Introduza o seu email: ", "email"))) {
                clearScreen();
                System.out.println("Gestor criado com sucesso!\n");
                registarAcao(username, "gestor inicial criado com sucesso");
            } else {
                clearScreen();
                System.out.println("Nao foi possivel criar o gestor. Tente novamente.\n");
                registarAcao(username, "falha ao criar gestor inicial");
            }
        }

        int opMenuInicial;
        do {
            opMenuInicial = leDadosInt(gereInterface.getInterface(2));

            switch (opMenuInicial) {
                case 1:
                    clearScreen();
                    registarAcao(username, "tentativa de autenticacao");

                    autenticarConta();
                    break;
                case 2:
                    clearScreen();
                    registarAcao(username, "tentativa de criacao de conta");

                    criarConta();
                    break;
                case 0:
                    encerrarAplicacao();
                    registarAcao(username, "aplicacao encerrada");
                    break;
                default:
                    clearScreen();
                    registarAcao(username, "opcao invalida no menu inicial");

                    System.out.println("Opcao invalida. Tente novamente.\n");
            }
        } while (opMenuInicial != 0);
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {// Esta linha identifica o sistema operativo
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("sh", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();// Esta linha vai imprimir onde ocorreu a exeption
        }
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
                    if (input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                        return input;
                    } else {
                        System.out.println("Email invalido. Tente novamente.\n");
                    }
                } else if (aTipo.equals("NIF")) {
                    if (input.matches("^\\d{9}$")) {
                        return input;
                    } else {
                        System.out.println("NIF invalido. Tente novamente.\n");
                    }

                } else if (aTipo.equals("habilitacao")) {
                    if (input.equals("1")) {

                        return "B";
                    } else if (input.equals("2")) {

                        return "A1";
                    } else if (input.equals("3")) {

                        return "A2";
                    } else if (input.equals("4")) {

                        return "AM";
                    } else {

                        System.out.println("Opcao invalida. Tente novamente.");
                    }
                } else if (aTipo.equals("matricula")) {

                    if (input.matches("^[A-Z]{2}-\\d{2}-\\d{2}$") || input.matches("^\\d{2}-\\d{2}-[A-Z]{2}$") ||
                            input.matches("^\\d{2}-[A-Z]{2}-\\d{2}$") || input.matches("^[A-Z]{2}-\\d{2}-[A-Z]{2}$")) {
                        return input;
                    } else {
                        System.out.println(
                                "Matricula invalida. Siga o seguinte formato: AA-00-00, 00-00-AA, 00-AA-00 ou AA-00-AA.\n");
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
                Float numero = teclado.nextFloat();
                if (numero > 0) {
                    return numero;
                } else {
                    System.out.println("Input invalido. Tente novamente.\n");
                }

            } catch (Exception e) {
                System.out.println("Input invalido. Tente novamente.\n");

            }
        }
    }

    public static double leDadosDouble(String aMensagem) {
        while (true) {
            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                return teclado.nextDouble();
            } catch (Exception e) {
                System.out.println("Input invalido. Tente novamente.\n");
            }
        }
    }

    public static LocalTime leDadosHora(String aMensagem) {

        while (true) {

            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                String input = teclado.nextLine();
                return LocalTime.parse(input, timeFormatter);
            } catch (Exception e) {
                System.out.println("Hora invalida. Tente novamente. Use o formato HH:mm:ss.\n");
            }
        }
    }

    public static LocalDate leDadosData(String aMensagem) {

        while (true) {

            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                String input = teclado.nextLine();
                return LocalDate.parse(input, dateFormatter);
            } catch (Exception e) {
                System.out.println("Data invalida. Tente novamente. Use o formato dd/MM/yyyy.\n");
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

    public static void autenticarConta() {
        username = leDadosString("Introduza o seu username: ", "");
        String password = leDadosString("Introduza a sua password: ", "password");

        Utilizador utilizadorAutenticado = gereUtilizador.autenticarUtilizador(username, password);

        clearScreen();
        if (utilizadorAutenticado != null) {
            if (utilizadorAutenticado.getEstado() == 1) {
                System.out.println(
                        "\nA sua conta encontra-se pendente. Aguarde que um gestor aprove a sua conta porfavor.\n");
                registarAcao(username, "tentativa de autenticacao - conta pendente");
            } else if (utilizadorAutenticado.getEstado() == 2 || utilizadorAutenticado.getEstado() == 5) {
                infoSistema.setUltimoUtilizador(utilizadorAutenticado.getLogin());
                System.out.println("Bem-vindo " + utilizadorAutenticado.getNome() + "\n");
                registarAcao(username, "autenticacao bem-sucedida");

                // Aparece notificacoes
                ArrayList<Notificacao> notificacoes = gereNotificacao
                        .getNotificacoesPorDestinatario(utilizadorAutenticado);
                if (!notificacoes.isEmpty()) {
                    System.out.println("Tem " + notificacoes.size() + " novas notificacoes:\n");
                    Iterator<Notificacao> iterator = notificacoes.iterator();
                    while (iterator.hasNext()) {
                        Notificacao notificacao = iterator.next();
                        System.out.println(notificacao + "\n");
                    }
                }

                if (utilizadorAutenticado.getTipoUtilizador().equals("Gestor")) {
                    aplicacaoGestor(utilizadorAutenticado);
                } else if (utilizadorAutenticado.getTipoUtilizador().equals("Condutor")) {
                    aplicacaoCondutor(utilizadorAutenticado);
                } else if (utilizadorAutenticado.getTipoUtilizador().equals("Cliente")) {
                    aplicacaoCliente(utilizadorAutenticado);
                }

                clearScreen();
                System.out.println("Adeus " + utilizadorAutenticado.getNome() + "\n");

            } else if (utilizadorAutenticado.getEstado() == 3) {
                System.out.println(
                        "\nA sua conta encontra-se inativa. Aguarde que um gestor ative a sua conta porfavor.\n");
                registarAcao(username, "tentativa de autenticacao - conta inativa");
            } else if (utilizadorAutenticado.getEstado() == 4) {
                System.out.println("\nO seu pedido de registo foi rejeitado por um gestor. Desculpe pelo incomodo.\n");
                registarAcao(username, "tentativa de autenticacao - registo rejeitado");
            }
        } else {
            System.out.println("\nUsername ou password incorretos. Tente novamente.\n");
            registarAcao(username, "tentativa de autenticacao - falha");
        }
    }

    public static void criarConta() {

        int opMenuCriarConta;
        boolean contaCriada = false;

        do {

            opMenuCriarConta = leDadosInt(gereInterface.getInterface(4));

            switch (opMenuCriarConta) {

                case 1:

                    clearScreen();
                    String usernameGestor = leDadosString("\nIntroduza o seu username: ", "");
                    if (gereUtilizador.criarGestor(usernameGestor,
                            leDadosString("Introduza a sua password: ", "password"),
                            leDadosString("Introduza o seu nome: ", ""), 1,
                            leDadosString("Introduza o seu email: ", "email"))) {

                        clearScreen();
                        System.out.println("Gestor criado com sucesso!\n");
                        contaCriada = true;
                        registarAcao(usernameGestor, "conta de gestor criada com sucesso");

                        // Notificar gestores sobre novo gestor
                        ArrayList<Utilizador> gestores = gereUtilizador.getUtilizadoresByClassName("Gestor");
                        Iterator<Utilizador> iterator = gestores.iterator();
                        while (iterator.hasNext()) {
                            Utilizador gestor = iterator.next();
                            Notificacao notificacao = new Notificacao(
                                    "Novo gestor registado: Verifique os pedidos de registo", gestor);
                            gereNotificacao.adicionarNotificacao(notificacao);
                        }
                        opMenuCriarConta = 0;
                    } else {

                        clearScreen();
                        System.out.println(
                                "O username ou email que introduziu ja se encontra em uso. Tente novamente.\n");
                        registarAcao(usernameGestor, "tentativa de criacao de conta de gestor - falha");
                    }
                    break;
                case 2:

                    clearScreen();
                    String usernameCondutor = leDadosString("\nIntroduza o seu username: ", "");
                    if (gereUtilizador.criarCondutor(usernameCondutor,
                            leDadosString("Introduza a sua password: ", "password"),
                            leDadosString("Introduza o seu nome: ", ""), 1,
                            leDadosString("Introduza o seu email: ", "email"),
                            leDadosString(gereInterface.getInterface(15), "habilitacao"), true)) {

                        clearScreen();
                        System.out.println("Condutor criado com sucesso!\n");
                        contaCriada = true;
                        registarAcao(usernameCondutor, "conta de condutor criada com sucesso");

                        // Notificar gestores sobre novo condutor
                        ArrayList<Utilizador> gestores = gereUtilizador.getUtilizadoresByClassName("Gestor");
                        Iterator<Utilizador> iterator = gestores.iterator();
                        while (iterator.hasNext()) {
                            Utilizador gestor = iterator.next();
                            Notificacao notificacao = new Notificacao(
                                    "Novo gestor registado: Verifique os pedidos de registo", gestor);
                            gereNotificacao.adicionarNotificacao(notificacao);
                        }
                        opMenuCriarConta = 0;
                    } else {

                        clearScreen();
                        System.out.println(
                                "O username ou email que introduziu ja se encontra em uso. Tente novamente.\n");
                        registarAcao(usernameCondutor, "tentativa de criacao de conta de condutor - falha");
                    }
                    break;
                case 3:

                    clearScreen();
                    String usernameCliente = leDadosString("\nIntroduza o seu username: ", "");
                    if (gereUtilizador.criarCliente(usernameCliente,
                            leDadosString("Introduza a sua password: ", "password"),
                            leDadosString("Introduza o seu nome: ", ""), 1,
                            leDadosString("Introduza o seu email: ", "email"),
                            leDadosString("Introduza a sua morada: ", ""),
                            leDadosString("Introduza o seu NIF: ", "NIF"))) {

                        clearScreen();
                        System.out.println("Cliente criado com sucesso!\n");
                        contaCriada = true;
                        registarAcao(usernameCliente, "conta de cliente criada com sucesso");

                        // Notificar gestores sobre novo cliente
                        ArrayList<Utilizador> gestores = gereUtilizador.getUtilizadoresByClassName("Gestor");
                        Iterator<Utilizador> iterator = gestores.iterator();
                        while (iterator.hasNext()) {
                            Utilizador gestor = iterator.next();
                            Notificacao notificacao = new Notificacao(
                                    "Novo gestor registado: Verifique os pedidos de registo", gestor);
                            gereNotificacao.adicionarNotificacao(notificacao);
                        }
                        opMenuCriarConta = 0;
                    } else {

                        clearScreen();
                        System.out.println(
                                "O username, email ou NIF que introduziu ja se encontra em uso. Tente novamente.\n");
                        registarAcao(usernameCliente, "tentativa de criacao de conta de cliente - falha");
                    }

                case 0:

                    if (contaCriada) {

                        break;
                    } else {

                        clearScreen();
                        break;
                    }

                default:

                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
            }

        } while (opMenuCriarConta != 0);
    }

    public static String encerrarAplicacao() {

        guardarData(gereUtilizador, gereServico, gereNotificacao, gereTrajeto, gereVeiculo);
        guardarInfoSistema(infoSistema);
        clearScreen();
        System.out.println("Encerrando aplicacao.");
        wait(750);
        clearScreen();
        System.out.println("Encerrando aplicacao..");
        wait(750);
        clearScreen();
        System.out.println("Encerrando aplicacao...");
        wait(750);
        clearScreen();
        System.out.println(gereInterface.getInterface(3));
        return gereInterface.getInterface(3);
    }

    public static void aplicacaoGestor(Utilizador aUtilizadorAutenticado) {

        int opMenuGestor;

        do {

            opMenuGestor = leDadosInt(gereInterface.getInterface(5));

            switch (opMenuGestor) {

                case 1:
                    clearScreen();
                    gerirUtilizadores(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a gerencia de utilizadores");
                    break;
                case 2:
                    clearScreen();
                    gerirServicos(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a gerencia de servicos");
                    break;
                case 3:
                    clearScreen();
                    String marca = leDadosString("Introduza a marca do veiculo: ", "");
                    String modelo = leDadosString("Introduza o modelo do veiculo: ", "null");
                    String matricula = leDadosString("Introduza a matricula do veiculo: ", "matricula");
                    int ano = leDadosInt("Introduza o ano da primeira matricula do veiculo: ");
                    float distancia = leDadosFloat("Introduza a distancia total percorrida do veiculo: ");
                    if (gereVeiculo.criarVeiculo(marca, modelo, matricula, ano, distancia, true)) {
                        clearScreen();
                        System.out.println("Veiculo criado com sucesso!\n");
                        registarAcao(username, "criou veiculo com sucesso: " + matricula);
                    } else {
                        clearScreen();
                        System.out
                                .println("O veiculo que introduziu ja se encontra registado. Verifique a matricula.\n");
                        registarAcao(username,
                                "tentativa de criacao de veiculo falhou: " + matricula);
                    }
                    break;
                case 4:
                    clearScreen();
                    System.out.println("Logs:\n");
                    consultarLog();
                    registarAcao(username, "acessou a consulta de logs");
                    break;
                case 5:
                    clearScreen();
                    gerirDadosManualmente();
                    registarAcao(username, "acessou a gerencia de dados manuais");
                    break;
                case 6:
                    clearScreen();
                    escolherOpcaoAlteracao(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a opcao de alteracao de dados");
                    break;
                case 7:
                    clearScreen();
                    consultarNotificacoes(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a consulta de notificacoes");
                    break;
                case 8:
                    clearScreen();
                    if (gereUtilizador.pedirRemocaoUtilizador(aUtilizadorAutenticado)) {
                        ArrayList<Utilizador> gestores = gereUtilizador.getUtilizadoresByClassName("Gestor");
                        Iterator<Utilizador> iterator = gestores.iterator();
                        while (iterator.hasNext()) {
                            Utilizador gestor = iterator.next();
                            Notificacao notificacao = new Notificacao(
                                    "Pedido de remocao de conta: " + username, gestor);
                            gereNotificacao.adicionarNotificacao(notificacao);
                        }
                        clearScreen();
                        System.out.println("Pedido de remocao de conta enviado!\n");
                        registarAcao(username, "pedido de remocao de conta enviado");
                    } else {
                        clearScreen();
                        System.out
                                .println("Ocorreu um erro ao enviar o pedido de remocao de conta. Tente novamente.\n");
                        registarAcao(username,
                                "tentativa de pedido de remocao de conta falhou");
                    }
                    break;
                case 0:
                    registarAcao(username, "saiu da aplicacao do gestor");
                    username="sistema";
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao(username, "tentou acessar uma opcao invalida");
            }

        } while (opMenuGestor != 0);
    }

    public static void gerirUtilizadores(Utilizador aUtilizadorAutenticado) {

        int opMenuGerirUtilizadores;

        do {
            opMenuGerirUtilizadores = leDadosInt(gereInterface.getInterface(11));

            switch (opMenuGerirUtilizadores) {

                case 1:
                    clearScreen();
                    criarConta();
                    registarAcao(username, "acessou a criacao de conta");
                    break;
                case 2:
                    clearScreen();
                    aprovarOuRejeitarRegistoUtilizador();
                    registarAcao(username,
                            "acessou a aprovacao/rejeicao de registo de utilizador");
                    break;
                case 3:
                    clearScreen();
                    ativarDesativarConta();
                    registarAcao(username, "acessou a ativacao/desativacao de conta");
                    break;
                case 4:
                    clearScreen();
                    aprovarOuRejeitarRemocaoConta();
                    registarAcao(username, "acessou a aprovacao/rejeicao de remocao de conta");
                    break;
                case 5:
                    clearScreen();
                    listarUtilizadores();
                    registarAcao(username, "acessou a lista de utilizadores");
                    break;
                case 6:
                    clearScreen();
                    pesquisarUtilizadores();
                    registarAcao(username, "acessou a pesquisa de utilizadores");
                    break;
                case 0:
                    registarAcao(username, "saiu da gerencia de utilizadores");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao(username,
                            "tentou acessar uma opcao invalida na gerencia de utilizadores");
            }
        } while (opMenuGerirUtilizadores != 0);
    }

    public static void aprovarOuRejeitarRegistoUtilizador() {
        ArrayList<Utilizador> utilizadoresPendentes = gereUtilizador.getUtilizadoresPorEstado(1); // Estado 1 - pendente

        if (utilizadoresPendentes.isEmpty()) {
            System.out.println("Nenhum utilizador pendente para aprovacao ou rejeicao.\n");
        } else {
            Iterator<Utilizador> listaUtilizadores = utilizadoresPendentes.iterator();
            while (listaUtilizadores.hasNext()) {
                Utilizador utilizador = listaUtilizadores.next();
                System.out.println("Utilizador: " + utilizador + "\n");
            }

            if (leSimOuNao("\nDeseja aprovar ou rejeitar algum utilizador? Introduza S/s para sim, N/n para nao.\n")) {
                String login;
                Utilizador utilizadorParaAlterar;
                do {
                    login = leDadosString("Introduza o login do utilizador que pretende aprovar ou rejeitar: ", "");
                    utilizadorParaAlterar = gereUtilizador.getUtilizadorPorLogin(login);
                    if (utilizadorParaAlterar == null || utilizadorParaAlterar.getEstado() != 1) {
                        System.out.println("Opcao invalida. Por favor, introduza um login valido.\n");
                    }
                } while (utilizadorParaAlterar == null || utilizadorParaAlterar.getEstado() != 1);

                if (leSimOuNao("\nDeseja aprovar o utilizador? Introduza S/s para sim, N/n para nao")) {
                    if (gereUtilizador.aprovarUtilizador(login)) {
                        registarAcao(login, "utilizador aprovado");

                        if (utilizadorParaAlterar instanceof Condutor) {
                            Condutor condutor = (Condutor) utilizadorParaAlterar;
                            ArrayList<Veiculo> veiculos = gereVeiculo.getVeiculos();

                            Iterator<Veiculo> lista = veiculos.iterator();
                            while (lista.hasNext()) {
                                Veiculo veiculo = lista.next();
                                System.out.println((veiculos.indexOf(veiculo) + 1) + ". " + veiculo);
                            }

                            int opVeiculo;

                            do {
                                opVeiculo = leDadosInt("\nIntroduza o id do veiculo: ");
                                if (opVeiculo < 1 || opVeiculo > veiculos.size()) {
                                    System.out.println("Opcao invalida. Por favor, introduza um id valido.\n");
                                }
                            } while (opVeiculo < 1 || opVeiculo > veiculos.size());

                            Veiculo veiculo = veiculos.get(opVeiculo - 1);

                            if (condutor.setVeiculo(veiculo)) {
                                clearScreen();
                                System.out.println("Veiculo atribuido com sucesso!\n");
                                registarAcao(login, "veiculo atribuido com sucesso");
                            } else {
                                clearScreen();
                                System.out.println("Ocorreu um erro ao atribuir um veiculo!\n");
                                registarAcao(login, "erro ao atribuir veiculo");
                            }
                        }

                        System.out.println("Utilizador aprovado com sucesso!\n");
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao aprovar o utilizador.\n");
                        registarAcao(login, "erro ao aprovar utilizador");
                    }
                } else {
                    if (gereUtilizador.rejeitarUtilizador(login)) {
                        clearScreen();
                        System.out.println("Utilizador rejeitado com sucesso!\n");
                        registarAcao(login, "utilizador rejeitado");
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao rejeitar o utilizador.\n");
                        registarAcao(login, "erro ao rejeitar utilizador");
                    }
                }
            }
        }
    }

    public static void ativarDesativarConta() {
        ArrayList<Utilizador> utilizadoresAtivosInativos = gereUtilizador.getUtilizadoresPorEstado(2); // Estado 2 -
                                                                                                       // ativo
        utilizadoresAtivosInativos.addAll(gereUtilizador.getUtilizadoresPorEstado(3)); // Estado 3 - inativo

        if (utilizadoresAtivosInativos.isEmpty()) {
            System.out.println("Nenhum utilizador ativo ou inativo encontrado.\n");
        } else {
            Iterator<Utilizador> listaUtilizadores = utilizadoresAtivosInativos.iterator();
            while (listaUtilizadores.hasNext()) {
                Utilizador utilizador = listaUtilizadores.next();
                System.out.println("Utilizador: " + utilizador + "\n");
            }

            if (leSimOuNao("\nDeseja ativar ou desativar algum utilizador? Introduza S/s para sim, N/n para nao.\n")) {
                String login;
                Utilizador utilizadorParaAlterar;
                do {
                    login = leDadosString("Introduza o login do utilizador que pretende ativar ou desativar: ", "");
                    utilizadorParaAlterar = gereUtilizador.getUtilizadorPorLogin(login);
                    if (utilizadorParaAlterar == null
                            || (utilizadorParaAlterar.getEstado() != 2 && utilizadorParaAlterar.getEstado() != 3)) {
                        System.out.println("Opcao invalida. Por favor, introduza um login valido.\n");
                    }
                } while (utilizadorParaAlterar == null
                        || (utilizadorParaAlterar.getEstado() != 2 && utilizadorParaAlterar.getEstado() != 3));

                if (utilizadorParaAlterar.getEstado() == 2) {
                    if (gereUtilizador.inativarUtilizador(login)) {
                        clearScreen();
                        System.out.println("Utilizador desativado com sucesso!\n");
                        registarAcao(login, "utilizador desativado com sucesso");
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao desativar o utilizador.\n");
                        registarAcao(login, "erro ao desativar utilizador");
                    }
                } else if (utilizadorParaAlterar.getEstado() == 3) {
                    if (gereUtilizador.ativarUtilizador(login)) {
                        clearScreen();
                        System.out.println("Utilizador ativado com sucesso!\n");
                        registarAcao(login, "utilizador ativado com sucesso");
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao ativar o utilizador.\n");
                        registarAcao(login, "erro ao ativar utilizador");
                    }
                }
            }
        }
    }

    public static void aprovarOuRejeitarRemocaoConta() {

        ArrayList<Utilizador> utilizadoresPorRemover = gereUtilizador.getUtilizadoresPorEstado(5);

        if (utilizadoresPorRemover.isEmpty()) {
            System.out.println("Nenhum utilizador por remover encontrado.\n");
        } else {
            Iterator<Utilizador> listaUtilizadores = utilizadoresPorRemover.iterator();
            while (listaUtilizadores.hasNext()) {
                Utilizador utilizador = listaUtilizadores.next();
                System.out.println("Utilizador: " + utilizador + "\n");
            }

            if (leSimOuNao("\nDeseja aprovar ou rejeitar algum utilizador? Introduza S/s para sim, N/n para nao.\n")) {

                String login;
                Utilizador utilizadorParaAlterar;
                do {
                    login = leDadosString("Introduza o login do utilizador que pretende aprovar ou rejeitar: ", "");
                    utilizadorParaAlterar = gereUtilizador.getUtilizadorPorLogin(login);
                    if (utilizadorParaAlterar == null || utilizadorParaAlterar.getEstado() != 5) {
                        System.out.println("Opcao invalida. Por favor, introduza um login valido.\n");
                    }
                } while (utilizadorParaAlterar == null || utilizadorParaAlterar.getEstado() != 5);

                if (leSimOuNao("\nDeseja aprovar o pedido de remocao de conta? Introduza S/s para sim, N/n para nao")) {

                    if (utilizadorParaAlterar instanceof Cliente) {
                        gereServico.getServicos()
                                .removeAll(gereServico.listarServicosPorCliente((Cliente) utilizadorParaAlterar));
                    } else if (utilizadorParaAlterar instanceof Condutor) {
                        gereServico.getServicos()
                                .removeAll(gereServico.listarServicosPorCondutor((Condutor) utilizadorParaAlterar));
                    }

                    if (gereUtilizador.aceitarPedidoRemocaoUtilizador(login)) {
                        clearScreen();
                        System.out.println("Conta removida com sucesso!\n");
                        registarAcao(login, "conta removida com sucesso");
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao remover o utilizador.\n");
                        registarAcao(login, "erro ao remover utilizador");
                    }
                } else {
                    if (gereUtilizador.rejeitarRemocaoConta(login)) {
                        clearScreen();
                        System.out.println("Pedido de remocao de conta rejeitado com sucesso!\n");
                        registarAcao(login, "pedido de remocao de conta rejeitado com sucesso");
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao rejeitar o pedido de remocao de conta.\n");
                        registarAcao(login, "erro ao rejeitar pedido de remocao de conta");
                    }
                }
            }
        }
    }

    public static void listarUtilizadores() {
        int opListarUtilizadores;
        do {
            opListarUtilizadores = leDadosInt(gereInterface.getInterface(13));

            switch (opListarUtilizadores) {
                case 1:
                    clearScreen();
                    listarTodosUtilizadores();
                    registarAcao("admin", "listou todos os utilizadores");
                    break;
                case 2:
                    clearScreen();
                    listarUtilizadoresPorTipo("Gestor");
                    registarAcao("admin", "listou utilizadores do tipo Gestor");
                    break;
                case 3:
                    clearScreen();
                    listarUtilizadoresPorTipo("Condutor");
                    registarAcao("admin", "listou utilizadores do tipo Condutor");
                    break;
                case 4:
                    clearScreen();
                    listarUtilizadoresPorTipo("Cliente");
                    registarAcao("admin", "listou utilizadores do tipo Cliente");
                    break;
                case 0:
                    clearScreen();
                    registarAcao("admin", "saiu da lista de utilizadores");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao("admin", "tentou acessar uma opcao invalida na lista de utilizadores");
            }
        } while (opListarUtilizadores != 0);
    }

    public static void listarTodosUtilizadores() {
        if (leSimOuNao("Deseja ordenar alfabeticamente? Introduza S/s para sim, N/n para nao.\n")) {
            ArrayList<Utilizador> sortedList = gereUtilizador.getUtilizadoresSortedByNome();
            Iterator<Utilizador> lista = sortedList.iterator();
            while (lista.hasNext()) {
                Utilizador utilizador = lista.next();
                System.out.println(utilizador);
            }
            registarAcao("admin", "listou todos os utilizadores ordenados alfabeticamente");
        } else {
            Iterator<Utilizador> lista = gereUtilizador.getUtilizadores().iterator();
            while (lista.hasNext()) {
                Utilizador utilizador = lista.next();
                System.out.println(utilizador);
            }
            registarAcao("admin", "listou todos os utilizadores sem ordenacao");
        }
    }

    public static void listarUtilizadoresPorTipo(String tipo) {
        if (tipo.equals("Gestor")) {
            if (leSimOuNao("Deseja ordenar alfabeticamente? Introduza S/s para sim, N/n para nao.\n")) {
                ArrayList<Utilizador> sortedList = gereUtilizador.getUtilizadoresSortedByNome();
                Iterator<Utilizador> lista = sortedList.iterator();
                while (lista.hasNext()) {
                    Utilizador utilizador = lista.next();
                    if (utilizador instanceof Gestor) {
                        System.out.println(utilizador);
                    }
                }
                registarAcao("admin", "listou utilizadores do tipo Gestor ordenados alfabeticamente");
            } else {
                Iterator<Utilizador> lista = gereUtilizador.getUtilizadores().iterator();
                while (lista.hasNext()) {
                    Utilizador utilizador = lista.next();
                    if (utilizador instanceof Gestor) {
                        System.out.println(utilizador);
                    }
                }
                registarAcao("admin", "listou utilizadores do tipo Gestor sem ordenacao");
            }
        } else if (tipo.equals("Condutor") || tipo.equals("Cliente")) {
            if (leSimOuNao("Deseja ordenar por algum criterio especifico? Introduza S/s para sim, N/n para nao.\n")) {
                int opTipoOrdenacao = leDadosInt(gereInterface.getInterface(20));
                if (tipo.equals("Condutor")) {
                    switch (opTipoOrdenacao) {
                        case 1:
                            Iterator<Condutor> condutorIteratorDist = gereUtilizador.getCondutoresSortedByDistancia()
                                    .iterator();
                            while (condutorIteratorDist.hasNext()) {
                                Condutor condutor = condutorIteratorDist.next();
                                System.out.println(condutor);
                            }
                            registarAcao("admin", "listou utilizadores do tipo Condutor ordenados por distancia");
                            break;
                        case 2:
                            Iterator<Condutor> condutorIteratorNum = gereUtilizador.getCondutoresSortedByNumServicos()
                                    .iterator();
                            while (condutorIteratorNum.hasNext()) {
                                Condutor condutor = condutorIteratorNum.next();
                                System.out.println(condutor);
                            }
                            registarAcao("admin",
                                    "listou utilizadores do tipo Condutor ordenados por numero de servicos");
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Opcao invalida. Tente novamente.\n");
                            registarAcao("admin", "tentou acessar uma opcao invalida na ordenacao de Condutores");
                    }
                } else if (tipo.equals("Cliente")) {
                    switch (opTipoOrdenacao) {
                        case 1:
                            Iterator<Cliente> clienteIteratorDist = gereUtilizador.getClientesSortedByDistancia()
                                    .iterator();
                            while (clienteIteratorDist.hasNext()) {
                                Cliente cliente = clienteIteratorDist.next();
                                System.out.println(cliente);
                            }
                            registarAcao("admin", "listou utilizadores do tipo Cliente ordenados por distancia");
                            break;
                        case 2:
                            Iterator<Cliente> clienteIteratorNum = gereUtilizador.getClientesSortedByNumServicos()
                                    .iterator();
                            while (clienteIteratorNum.hasNext()) {
                                Cliente cliente = clienteIteratorNum.next();
                                System.out.println(cliente);
                            }
                            registarAcao("admin",
                                    "listou utilizadores do tipo Cliente ordenados por numero de servicos");
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Opcao invalida. Tente novamente.\n");
                            registarAcao("admin", "tentou acessar uma opcao invalida na ordenacao de Clientes");
                    }
                }
            } else if (leSimOuNao("Deseja ordenar alfabeticamente? Introduza S/s para sim, N/n para nao.\n")) {
                ArrayList<Utilizador> sortedList = gereUtilizador.getUtilizadoresSortedByNome();
                Iterator<Utilizador> lista = sortedList.iterator();
                while (lista.hasNext()) {
                    Utilizador utilizador = lista.next();
                    if (tipo.equals("Condutor") && utilizador instanceof Condutor) {
                        System.out.println(utilizador);
                    } else if (tipo.equals("Cliente") && utilizador instanceof Cliente) {
                        System.out.println(utilizador);
                    }
                }
                registarAcao("admin", "listou utilizadores do tipo " + tipo + " ordenados alfabeticamente");
            } else {
                Iterator<Utilizador> iterator = gereUtilizador.getUtilizadores().iterator();
                while (iterator.hasNext()) {
                    Utilizador utilizador = iterator.next();
                    if (tipo.equals("Condutor") && utilizador instanceof Condutor) {
                        System.out.println(utilizador);
                    } else if (tipo.equals("Cliente") && utilizador instanceof Cliente) {
                        System.out.println(utilizador);
                    }
                }
                registarAcao("admin", "listou utilizadores do tipo " + tipo + " sem ordenacao");
            }
        }
    }

    public static void pesquisarUtilizadores() {
        int opPesquisarUtilizadores;

        do {
            opPesquisarUtilizadores = leDadosInt(gereInterface.getInterface(14));

            switch (opPesquisarUtilizadores) {
                case 1:
                    clearScreen();
                    String termoPesquisa = leDadosString("Introduza o login ou parte do login a pesquisar: ", "");
                    ArrayList<Utilizador> resultadoLogin = gereUtilizador.pesquisarUtilizadoresPorLogin(termoPesquisa);
                    if (resultadoLogin.isEmpty()) {
                        System.out.println("Nenhum utilizador encontrado.\n");
                        registarAcao("admin",
                                "pesquisou utilizadores por login: " + termoPesquisa + " - nenhum encontrado");
                    } else {
                        Iterator<Utilizador> lista = resultadoLogin.iterator();
                        while (lista.hasNext()) {
                            Utilizador utilizador = lista.next();
                            System.out.println("Utilizador: " + utilizador);
                        }
                        registarAcao("admin", "pesquisou utilizadores por login: " + termoPesquisa + " - encontrados: "
                                + resultadoLogin.size());
                    }
                    break;
                case 2:
                    clearScreen();
                    int tipoHabilitacao = leDadosInt(gereInterface.getInterface(16));
                    String habilitacao = "";
                    switch (tipoHabilitacao) {
                        case 1:
                            habilitacao = "B";
                            break;
                        case 2:
                            habilitacao = "A1";
                            break;
                        case 3:
                            habilitacao = "A2";
                            break;
                        case 4:
                            habilitacao = "AM";
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Opcao invalida. Tente novamente.\n");
                            registarAcao("admin", "tentou acessar uma opcao invalida na pesquisa por habilitacao");
                            continue;
                    }
                    ArrayList<Condutor> resultadoHabilitacao = gereUtilizador
                            .pesquisarCondutoresPorHabilitacao(habilitacao);
                    if (resultadoHabilitacao.isEmpty()) {
                        System.out.println("Nenhum condutor encontrado.\n");
                        registarAcao("admin",
                                "pesquisou condutores por habilitacao: " + habilitacao + " - nenhum encontrado");
                    } else {
                        Iterator<Condutor> lista = resultadoHabilitacao.iterator();
                        while (lista.hasNext()) {
                            Condutor condutor = lista.next();
                            System.out.println("Condutor:" + condutor);
                        }
                        registarAcao("admin", "pesquisou condutores por habilitacao: " + habilitacao
                                + " - encontrados: " + resultadoHabilitacao.size());
                    }
                    break;
                case 3:
                    clearScreen();
                    float distanciaLimite = leDadosFloat("Introduza a distancia limite: ");
                    ArrayList<Condutor> resultadoDistancia = gereUtilizador
                            .pesquisarCondutoresPorDistanciaAcumulada(distanciaLimite);
                    if (resultadoDistancia.isEmpty()) {
                        System.out.println("Nenhum condutor encontrado.\n");
                        registarAcao("admin", "pesquisou condutores por distancia acumulada: " + distanciaLimite
                                + " - nenhum encontrado");
                    } else {
                        Iterator<Condutor> lista = resultadoDistancia.iterator();
                        while (lista.hasNext()) {
                            Condutor condutor = lista.next();
                            System.out.println("Condutor: " + condutor);
                        }
                        registarAcao("admin", "pesquisou condutores por distancia acumulada: " + distanciaLimite
                                + " - encontrados: " + resultadoDistancia.size());
                    }
                    break;
                case 0:
                    clearScreen();
                    registarAcao("admin", "saiu da pesquisa de utilizadores");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao("admin", "tentou acessar uma opcao invalida na pesquisa de utilizadores");
            }
        } while (opPesquisarUtilizadores != 0);
    }

    public static void gerirServicos(Utilizador aUtilizadorAutenticado) {
        int opMenuGerirServicos;

        do {
            opMenuGerirServicos = leDadosInt(gereInterface.getInterface(17));

            switch (opMenuGerirServicos) {
                case 1:
                    clearScreen();
                    aprovarOuRejeitarServicos();
                    registarAcao(username, "acessou a aprovacao/rejeicao de servicos");
                    break;
                case 2:
                    clearScreen();
                    atribuirNovoCondutor();
                    registarAcao(username, "acessou a atribuicao de novo condutor");
                    break;
                case 3:
                    clearScreen();
                    listarServicos();
                    registarAcao(username, "acessou a lista de servicos");
                    break;
                case 4:
                    clearScreen();
                    pesquisarServicos();
                    registarAcao(username, "acessou a pesquisa de servicos");
                    break;
                case 5:
                    clearScreen();
                    listarTrajetos();
                    registarAcao(username, "acessou a lista de trajetos");
                    break;
                case 0:
                    registarAcao(username, "saiu da gerencia de servicos");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao(username,
                            "tentou acessar uma opcao invalida na gerencia de servicos");
            }
        } while (opMenuGerirServicos != 0);
    }

    public static void aprovarOuRejeitarServicos() {
        ArrayList<Servico> servicos = gereServico.getServicosPorEstado(1);

        if (servicos.isEmpty()) {
            System.out.println("Nenhum servico por aprovar\n");
            registarAcao("admin", "verificou servicos por aprovar - nenhum encontrado");
        } else {
            Iterator<Servico> listaServicos = servicos.iterator();
            while (listaServicos.hasNext()) {
                Servico servico = listaServicos.next();
                System.out.println("Servico: " + servico + "\n");
            }

            if (leSimOuNao("\nDeseja aprovar ou rejeitar algum servico? Introduza S/s para sim, N/n para nao.\n")) {
                int id;
                Servico servico;
                do {
                    id = leDadosInt("Introduza o id do servico: ");
                    servico = gereServico.getServico(id);
                    if (servico == null) {
                        System.out.println("Opcao invalida. Por favor, introduza um id valido.\n");
                    }
                } while (servico == null);

                if (leSimOuNao("\nDeseja aprovar o servico? Introduza S/s para sim, N/n para nao")) {
                    ArrayList<Utilizador> condutores = gereUtilizador.getUtilizadoresByClassName("Condutor");

                    if (condutores.isEmpty()) {
                        System.out.println("Nenhum condutor disponivel.\n");
                        registarAcao("admin", "tentou aprovar servico - nenhum condutor disponivel");
                    } else {
                        Iterator<Utilizador> listaCondutores = condutores.iterator();
                        while (listaCondutores.hasNext()) {
                            Condutor condutor = (Condutor) listaCondutores.next();
                            System.out.println((condutores.indexOf(condutor) + 1) + ": " + condutor + "\n");
                        }

                        int opCondutor;
                        do {
                            opCondutor = leDadosInt("\nIntroduza o id do condutor: ");
                            if (opCondutor < 1 || opCondutor > condutores.size()) {
                                System.out.println("Opcao invalida. Por favor, introduza um id valido.\n");
                            }
                        } while (opCondutor < 1 || opCondutor > condutores.size());

                        Condutor condutor = (Condutor) condutores.get(opCondutor - 1);

                        if (gereServico.aprovarServico(servico, condutor)) {
                            clearScreen();
                            System.out.println("Servico aprovado com sucesso!\n");
                            registarAcao("admin", "aprovou servico com id: " + servico.getId());
                            Notificacao notificacao = new Notificacao("Novo servico atribuido: " + servico.getId(),
                                    condutor);
                            gereNotificacao.adicionarNotificacao(notificacao);
                        } else {
                            clearScreen();
                            System.out.println("Ocorreu um erro ao aprovar o servico.\n");
                            registarAcao("admin", "erro ao aprovar servico com id: " + servico.getId());
                        }
                    }
                } else {
                    if (gereServico.rejeitarServicoPorGestor(servico)) {
                        clearScreen();
                        System.out.println("Servico rejeitado com sucesso!\n");
                        registarAcao("admin", "rejeitou servico com id: " + servico.getId());
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao rejeitar o servico.\n");
                        registarAcao("admin", "erro ao rejeitar servico com id: " + servico.getId());
                    }
                }
            }
        }
    }

    public static void atribuirNovoCondutor() {
        ArrayList<Servico> servicosRejeitados = gereServico.getServicosPorEstado(4);

        if (servicosRejeitados.isEmpty()) {
            clearScreen();
            System.out.println("Nenhum servico rejeitado.\n");
            registarAcao("admin", "verificou servicos rejeitados - nenhum encontrado");
        } else {
            Iterator<Servico> listaServicos = servicosRejeitados.iterator();
            while (listaServicos.hasNext()) {
                Servico servico = listaServicos.next();
                System.out.println("Servico: " + servico + "\n");
            }

            int idServico;
            Servico servico;
            do {
                idServico = leDadosInt("Introduza o id do servico escolhido: ");
                servico = gereServico.getServico(idServico);
                if (servico == null) {
                    System.out.println("Opcao Invalida. Por favor, introduza um id valido.\n");
                }
            } while (servico == null);

            ArrayList<Condutor> condutoresQueNaoRejeitaram = gereUtilizador.getCondutoresQueNaoRejeitaram(servico);

            if (condutoresQueNaoRejeitaram.isEmpty()) {
                clearScreen();
                System.out.println("Nenhum condutor disponivel.\n");
                registarAcao("admin", "tentou atribuir condutor - nenhum condutor disponivel");
            } else {
                Iterator<Condutor> listaCondutores = condutoresQueNaoRejeitaram.iterator();
                while (listaCondutores.hasNext()) {
                    Condutor condutor = listaCondutores.next();
                    System.out.println((condutoresQueNaoRejeitaram.indexOf(condutor) + 1) + ": " + condutor + "\n");
                }

                int opCondutor;
                do {
                    opCondutor = leDadosInt("\nIntroduza o id do condutor: ");
                    if (opCondutor < 1 || opCondutor > condutoresQueNaoRejeitaram.size()) {
                        System.out.println("Opcao invalida. Por favor, introduza um id valido.\n");
                    }
                } while (opCondutor < 1 || opCondutor > condutoresQueNaoRejeitaram.size());

                Condutor condutor = (Condutor) condutoresQueNaoRejeitaram.get(opCondutor - 1);

                if (gereServico.atribuirNovoCondutor(servico, condutor)) {
                    clearScreen();
                    System.out.println("Novo condutor atribuido com sucesso!\n");
                    registarAcao("admin", "atribuiu novo condutor com sucesso ao servico com id: " + servico.getId());
                } else {
                    clearScreen();
                    System.out.println("Ocorreu um erro ao atribuir um novo condutor.\n");
                    registarAcao("admin", "erro ao atribuir novo condutor ao servico com id: " + servico.getId());
                }
            }
        }
    }

    public static void listarServicos() {
        int opListarServicos;
        do {
            opListarServicos = leDadosInt(gereInterface.getInterface(21));

            switch (opListarServicos) {
                case 1:
                    clearScreen();
                    listarTodosServicos();
                    registarAcao("admin", "listou todos os servicos");
                    break;
                case 2:
                    clearScreen();
                    listarServicosPorEstado(1);
                    registarAcao("admin", "listou servicos por estado 1 (pendente)");
                    break;
                case 3:
                    clearScreen();
                    listarServicosPorEstado(3);
                    registarAcao("admin", "listou servicos por estado 3 (em andamento)");
                    break;
                case 4:
                    clearScreen();
                    listarServicosConcluidos();
                    registarAcao("admin", "listou servicos concluidos");
                    break;
                case 0:
                    clearScreen();
                    registarAcao("admin", "saiu da lista de servicos");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao("admin", "tentou acessar uma opcao invalida na lista de servicos");
            }
        } while (opListarServicos != 0);
    }

    public static void listarTodosServicos() {
        Iterator<Servico> lista = gereServico.getServicos().iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico);
        }
        registarAcao("admin", "listou todos os servicos");
    }

    public static void listarServicosConcluidos() {
        if (leSimOuNao("Deseja ordenar por algum criterio especifico? Introduza S/s para sim, N/n para nao.\n")) {
            int opTipoOrdenacao = leDadosInt(gereInterface.getInterface(22));
            switch (opTipoOrdenacao) {
                case 1:
                    listarServicosPorDistancia();
                    registarAcao("admin", "listou servicos concluidos por distancia");
                    break;
                case 2:
                    listarServicosPorDuracao();
                    registarAcao("admin", "listou servicos concluidos por duracao");
                    break;
                case 3:
                    listarServicosPorData();
                    registarAcao("admin", "listou servicos concluidos por data");
                    break;
                case 4:
                    listarServicosPorID();
                    registarAcao("admin", "listou servicos concluidos por ID");
                    break;
                case 5:
                    listarServicosPorCusto();
                    registarAcao("admin", "listou servicos concluidos por custo");
                    break;
                case 0:
                    registarAcao("admin", "saiu da lista de servicos concluidos");
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao("admin", "tentou acessar uma opcao invalida na lista de servicos concluidos");
            }
        } else {
            listarServicosPorEstado(6);
            registarAcao("admin", "listou servicos concluidos por estado");
        }
    }

    public static void listarServicosPorEstado(int estado) {
        Iterator<Servico> lista = gereServico.getServicosPorEstado(estado).iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico);
        }
        registarAcao("admin", "listou servicos por estado: " + estado);
    }

    public static void listarServicosPorDistancia() {
        ArrayList<Servico> sortedList = gereServico.getServicosConcluidosSortedByDistancia();
        Iterator<Servico> lista = sortedList.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico);
        }
        registarAcao("admin", "listou servicos concluidos por distancia");
    }

    public static void listarServicosPorDuracao() {
        ArrayList<Servico> sortedList = gereServico.getServicosConcluidosSortedByDuracao();
        Iterator<Servico> lista = sortedList.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico);
        }
        registarAcao("admin", "listou servicos concluidos por duracao");
    }

    public static void listarServicosPorData() {
        ArrayList<Servico> sortedList = gereServico.getServicosConcluidosSortedByData();
        Iterator<Servico> lista = sortedList.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico);
        }
        registarAcao("admin", "listou servicos concluidos por data");
    }

    public static void listarServicosPorID() {
        ArrayList<Servico> sortedList = gereServico.getServicosConcluidosSortedByID();
        Iterator<Servico> lista = sortedList.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico);
        }
        registarAcao("admin", "listou servicos concluidos por ID");
    }

    public static void listarServicosPorCusto() {
        ArrayList<Servico> sortedList = gereServico.getServicosConcluidosSortedByCusto();
        Iterator<Servico> lista = sortedList.iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico);
        }
        registarAcao("admin", "listou servicos concluidos por custo");
    }

    public static void pesquisarServicos() {
        int opPesquisarServicos;
        do {
            opPesquisarServicos = leDadosInt(gereInterface.getInterface(23));

            switch (opPesquisarServicos) {
                case 1:
                    clearScreen();
                    pesquisarServicosPorID();
                    registarAcao("admin", "pesquisou servico por ID");
                    break;
                case 2:
                    clearScreen();
                    escolherEstadoParaPesquisa();
                    registarAcao("admin", "escolheu estado para pesquisa de servicos");
                    break;
                case 0:
                    clearScreen();
                    registarAcao("admin", "saiu da pesquisa de servicos");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao("admin", "tentou acessar uma opcao invalida na pesquisa de servicos");
            }
        } while (opPesquisarServicos != 0);
    }

    public static void pesquisarServicosPorID() {
        int id = leDadosInt("Introduza o identificador do servico: ");
        Servico servico = gereServico.getServico(id);
        if (servico != null) {
            System.out.println(servico);
            registarAcao("admin", "pesquisou servico por ID: " + id + " - encontrado");
        } else {
            System.out.println("Servico nao encontrado.");
            registarAcao("admin", "pesquisou servico por ID: " + id + " - nao encontrado");
        }
    }

    public static void escolherEstadoParaPesquisa() {
        int estado;
        do {
            estado = leDadosInt(gereInterface.getInterface(24));
            if (estado < 1 || estado > 6) {
                System.out.println("Opcao invalida. Tente novamente.");
                registarAcao("admin",
                        "tentou acessar uma opcao invalida na escolha de estado para pesquisa de servicos");
            }
        } while (estado < 1 || estado > 6);

        listarServicosPorEstado(estado);
        registarAcao("admin", "listou servicos por estado: " + estado);
    }

    public static void listarTrajetos() {
        int opListarTrajetos;
        do {
            opListarTrajetos = leDadosInt(gereInterface.getInterface(25));

            switch (opListarTrajetos) {
                case 1:
                    clearScreen();
                    listarTodosTrajetos();
                    registarAcao("admin", "listou todos os trajetos");
                    break;
                case 2:
                    clearScreen();
                    listarTrajetosPorCliente();
                    registarAcao("admin", "listou trajetos por cliente");
                    break;
                case 3:
                    clearScreen();
                    listarTrajetosPorCondutor();
                    registarAcao("admin", "listou trajetos por condutor");
                    break;
                case 0:
                    clearScreen();
                    registarAcao("admin", "saiu da lista de trajetos");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao("admin", "tentou acessar uma opcao invalida na lista de trajetos");
            }
        } while (opListarTrajetos != 0);
    }

    public static void listarTodosTrajetos() {
        Iterator<Trajeto> lista = gereTrajeto.listarTrajetos().iterator();
        while (lista.hasNext()) {
            Trajeto trajeto = lista.next();
            System.out.println(trajeto);
        }
        registarAcao("admin", "listou todos os trajetos");
    }

    public static void listarTrajetosPorCliente() {
        ArrayList<Utilizador> clientes = gereUtilizador.getUtilizadoresByClassName("Cliente");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            registarAcao("admin", "tentou listar trajetos por cliente - nenhum cliente encontrado");
            return;
        }

        System.out.println("Lista de clientes:");
        Iterator<Utilizador> listaClientes = clientes.iterator();
        while (listaClientes.hasNext()) {
            Utilizador cliente = listaClientes.next();
            System.out.println(cliente.getLogin() + " - " + cliente.getNome());
        }

        Utilizador utilizador;
        do {
            String login = leDadosString("Introduza o login do cliente: ", "");
            utilizador = gereUtilizador.getUtilizadorPorLogin(login);
            if (!(utilizador instanceof Cliente)) {
                System.out.println("Cliente nao encontrado. Tente novamente.");
                utilizador = null;
            }
        } while (utilizador == null);

        Cliente cliente = (Cliente) utilizador;
        Iterator<Servico> lista = gereServico.listarServicosPorCliente(cliente).iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico.getTrajeto());
        }
        registarAcao("admin", "listou trajetos por cliente: " + cliente.getLogin());
    }

    public static void listarTrajetosPorCondutor() {
        ArrayList<Utilizador> condutores = gereUtilizador.getUtilizadoresByClassName("Condutor");
        if (condutores.isEmpty()) {
            System.out.println("Nenhum condutor encontrado.");
            registarAcao("admin", "tentou listar trajetos por condutor - nenhum condutor encontrado");
            return;
        }

        System.out.println("Lista de condutores:");
        Iterator<Utilizador> listaCondutores = condutores.iterator();
        while (listaCondutores.hasNext()) {
            Utilizador condutor = listaCondutores.next();
            System.out.println(condutor.getLogin() + " - " + condutor.getNome());
        }

        Utilizador utilizador;
        do {
            String login = leDadosString("Introduza o login do condutor: ", "");
            utilizador = gereUtilizador.getUtilizadorPorLogin(login);
            if (!(utilizador instanceof Condutor)) {
                System.out.println("Condutor nao encontrado. Tente novamente.");
                utilizador = null;
            }
        } while (utilizador == null);

        Condutor condutor = (Condutor) utilizador;
        Iterator<Servico> lista = gereServico.listarServicosPorCondutor(condutor).iterator();
        while (lista.hasNext()) {
            Servico servico = lista.next();
            System.out.println(servico.getTrajeto());
        }
        registarAcao("admin", "listou trajetos por condutor: " + condutor.getLogin());
    }

    public static void gerirDadosManualmente() {
        int opGerirDados = -1;

        do {
            opGerirDados = leDadosInt(gereInterface.getInterface(19));

            switch (opGerirDados) {
                case 1:
                    clearScreen();
                    FicheiroDados dados = carregarDataManual();
                    if (dados != null) {
                        gereServico = dados.getGereServico();
                        gereTrajeto = dados.getGereTrajeto();
                        gereVeiculo = dados.getGereVeiculo();
                        gereUtilizador = dados.getGereUtilizador();
                        gereNotificacao = dados.getGereNotificacao();
                        registarAcao("admin", "carregou dados manualmente com sucesso");
                    } else {
                        registarAcao("admin", "falhou ao carregar dados manualmente");
                    }
                    break;
                case 2:
                    clearScreen();
                    guardarDataManual();
                    registarAcao("admin", "guardou dados manualmente com sucesso");
                    break;
                case 3:
                    clearScreen();
                    exportarServicosParaCSV();
                    registarAcao("admin", "exportou servicos para CSV");
                    break;
                case 0:
                    registarAcao("admin", "saiu da gerencia de dados manuais");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao("admin", "tentou acessar uma opcao invalida na gerencia de dados manuais");
            }
        } while (opGerirDados != 0);
    }

    public static void aplicacaoCondutor(Utilizador aUtilizadorAutenticado) {

        int opMenuCondutor;

        do {

            opMenuCondutor = leDadosInt(gereInterface.getInterface(6));

            switch (opMenuCondutor) {

                case 1:
                    clearScreen();
                    verMeusServicosCondutor(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a visualizacao de seus servicos");
                    break;
                case 2:
                    clearScreen();
                    aceitarOuRejeitarServico(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a opcao de aceitar ou rejeitar servico");
                    break;
                case 3:
                    clearScreen();
                    registarConlusaoServico(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a opcao de registar conclusao de servico");
                    break;
                case 4:
                    clearScreen();
                    verEstatisticasPessoais(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a visualizacao de estatisticas pessoais");
                    break;
                case 5:
                    clearScreen();
                    escolherOpcaoAlteracao(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a opcao de alteracao de dados");
                    break;
                case 6:
                    clearScreen();
                    consultarNotificacoes(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a consulta de notificacoes");
                    break;
                case 7:
                    clearScreen();
                    if (gereUtilizador.pedirRemocaoUtilizador(aUtilizadorAutenticado)) {
                        ArrayList<Utilizador> gestores = gereUtilizador.getUtilizadoresByClassName("Gestor");
                        Iterator<Utilizador> iterator = gestores.iterator();
                        while (iterator.hasNext()) {
                            Utilizador gestor = iterator.next();
                            Notificacao notificacao = new Notificacao(
                                    "Pedido de remocao de conta: " + username, gestor);
                            gereNotificacao.adicionarNotificacao(notificacao);
                        }
                        clearScreen();
                        System.out.println("Pedido de remocao de conta enviado!\n");
                        registarAcao(username, "pedido de remocao de conta enviado");
                    } else {
                        clearScreen();
                        System.out
                                .println("Ocorreu um erro ao enviar o pedido de remocao de conta. Tente novamente.\n");
                        registarAcao(username, "erro ao enviar pedido de remocao de conta");
                    }
                    break;
                case 0:
                    registarAcao(username, "saiu da aplicacao do condutor");
                    username="sistema";
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao(username,
                            "tentou acessar uma opcao invalida na aplicacao do condutor");
            }

        } while (opMenuCondutor != 0);
    }

    public static void verMeusServicosCondutor(Utilizador aUtilizadorAutenticado) {

        int opVerMeusServicos = -1;

        Condutor condutor = (Condutor) aUtilizadorAutenticado;

        do {

            opVerMeusServicos = leDadosInt(gereInterface.getInterface(18));

            switch (opVerMeusServicos) {

                case 1:
                    clearScreen();
                    gereServico.listarServicosPorCondutor(condutor);
                    registarAcao(condutor.getLogin(), "listou todos os seus servicos");
                    break;
                case 2:
                    clearScreen();
                    gereServico.listarServicosPorCondutor(condutor, 1);
                    registarAcao(condutor.getLogin(), "listou seus servicos pendentes");
                    break;
                case 3:
                    clearScreen();
                    gereServico.listarServicosPorCondutor(condutor, 3);
                    registarAcao(condutor.getLogin(), "listou seus servicos em andamento");
                    break;
                case 4:
                    clearScreen();
                    gereServico.listarServicosPorCondutor(condutor, 6);
                    registarAcao(condutor.getLogin(), "listou seus servicos concluidos");
                    break;
                case 0:
                    clearScreen();
                    registarAcao(condutor.getLogin(), "saiu da visualizacao de seus servicos");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao(condutor.getLogin(),
                            "tentou acessar uma opcao invalida na visualizacao de seus servicos");
            }
        } while (opVerMeusServicos != 0);
    }

    public static void aceitarOuRejeitarServico(Utilizador aUtilizadorAutenticado) {

        Condutor condutor = (Condutor) aUtilizadorAutenticado;

        ArrayList<Servico> servicos = gereServico.listarServicosPorCondutor(condutor, 2);

        if (servicos.isEmpty()) {
            clearScreen();
            System.out.println("Nao ha servicos pendentes para aceitar ou rejeitar.\n");
            registarAcao(condutor.getLogin(), "verificou servicos pendentes - nenhum encontrado");
        } else {
            Iterator<Servico> listaServicos = servicos.iterator();
            while (listaServicos.hasNext()) {
                Servico servico = listaServicos.next();
                System.out.println("Servico: " + servico + "\n");
            }

            if (leSimOuNao("\nDeseja aceitar ou rejeitar algum servico? Introduza S/s para sim, N/n para nao.\n")) {

                int idServico;
                Servico servicoParaAlterar;
                do {
                    idServico = leDadosInt("Introduza o id do servico que pretende aceitar ou rejeitar: ");
                    servicoParaAlterar = gereServico.getServico(idServico);
                    if (servicoParaAlterar == null || !servicoParaAlterar.getCondutor().equals(condutor)
                            || servicoParaAlterar.getEstado() != 2) {
                        System.out.println("Opcao invalida. Por favor, introduza um id valido.\n");
                    }
                } while (servicoParaAlterar == null || !servicoParaAlterar.getCondutor().equals(condutor)
                        || servicoParaAlterar.getEstado() != 2);

                if (leSimOuNao("\nDeseja aceitar o servico? Introduza S/s para sim, N/n para nao")) {

                    if (condutor.isDisponivel()) {

                        if (condutor.getVeiculo().isDisponivel()) {
                            if (gereServico.aceitarServico(servicoParaAlterar, condutor)) {
                                clearScreen();
                                System.out.println("Servico aceito com sucesso!\n");
                                registarAcao(condutor.getLogin(),
                                        "aceitou servico com id: " + servicoParaAlterar.getId());
                            } else {
                                clearScreen();
                                System.out.println("Ocorreu um erro ao aceitar o servico.\n");
                                registarAcao(condutor.getLogin(),
                                        "erro ao aceitar servico com id: " + servicoParaAlterar.getId());
                            }

                        } else {
                            clearScreen();
                            System.out.println("O seu carro esta ocupado!\n");
                            registarAcao(condutor.getLogin(), "tentou aceitar servico com carro ocupado");
                        }

                    } else {

                        clearScreen();
                        System.out.println("Nao e possivel ter dois servicos ativos!\n");
                        registarAcao(condutor.getLogin(), "tentou aceitar servico com dois servicos ativos");
                    }

                } else {
                    if (gereServico.rejeitarServicoPorCondutor(servicoParaAlterar, condutor)) {
                        clearScreen();
                        System.out.println("Servico rejeitado com sucesso!\n");
                        registarAcao(condutor.getLogin(), "rejeitou servico com id: " + servicoParaAlterar.getId());
                        ArrayList<Utilizador> gestores = gereUtilizador.getUtilizadoresByClassName("Gestor");
                        Iterator<Utilizador> iterator = gestores.iterator();
                        while (iterator.hasNext()) {
                            Utilizador gestor = iterator.next();
                            Notificacao notificacao = new Notificacao(
                                    "Servico rejeitado pelo condutor: " + servicoParaAlterar.getId(), gestor);
                            gereNotificacao.adicionarNotificacao(notificacao);
                        }
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao rejeitar o servico.\n");
                        registarAcao(condutor.getLogin(),
                                "erro ao rejeitar servico com id: " + servicoParaAlterar.getId());
                    }
                }
            }
        }
    }

    public static void registarConlusaoServico(Utilizador aUtilizadorAutenticado) {

        Condutor condutor = (Condutor) aUtilizadorAutenticado;

        Servico servico = gereServico.listarServicosPorCondutor(condutor, 3).getFirst();

        if (servico == null) {
            clearScreen();
            System.out.println("Nao ha servicos para concluir.\n");
            registarAcao(condutor.getLogin(), "verificou servicos para concluir - nenhum encontrado");
            return;
        } else {
            System.out.println("Servico: " + servico + "\n");
        }

        if (leSimOuNao("\nDeseja concluir o servico? Introduza S/s para sim, N/n para nao.\n")) {

            LocalTime duracao = leDadosHora("Introduza a duracao do servico: ");
            float distancia = leDadosFloat("Introduza a distancia percorrida: ");
            float valorTotal = leDadosFloat("Introduza o valor total do servico: ");

            if (gereServico.concluirServico(servico, valorTotal, duracao, distancia)) {

                condutor.setLatitude(leDadosDouble("Introduza a sua latitude atual: "));
                condutor.setLongitude(leDadosDouble("Introduza a sua longitude atual: "));
                clearScreen();
                System.out.println("Servico concluido com sucesso!\n");
                registarAcao(condutor.getLogin(), "concluiu servico com id: " + servico.getId());

                Notificacao notificacao = new Notificacao("Seu servico foi concluido: " + servico.getId(),
                        servico.getCliente());
                gereNotificacao.adicionarNotificacao(notificacao);
            } else {
                clearScreen();
                System.out.println("Ocorreu um erro ao concluir o servico.\n");
                registarAcao(condutor.getLogin(), "erro ao concluir servico com id: " + servico.getId());
            }
        }
    }

    public static void aplicacaoCliente(Utilizador aUtilizadorAutenticado) {

        int opMenuCliente;

        do {

            opMenuCliente = leDadosInt(gereInterface.getInterface(7));

            switch (opMenuCliente) {

                case 1:
                    clearScreen();
                    solicitarServico(aUtilizadorAutenticado);
                    registarAcao(username, "solicitou servico");
                    break;
                case 2:
                    clearScreen();
                    verMeusServicosCliente(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a visualizacao de seus servicos");
                    break;
                case 3:
                    clearScreen();
                    confirmarConclusaoServico(aUtilizadorAutenticado);
                    registarAcao(username, "confirmou conclusao de servico");
                    break;
                case 4:
                    clearScreen();
                    verEstatisticasPessoais(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a visualizacao de estatisticas pessoais");
                    break;
                case 5:
                    clearScreen();
                    escolherOpcaoAlteracao(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a opcao de alteracao de dados");
                    break;
                case 6:
                    clearScreen();
                    consultarNotificacoes(aUtilizadorAutenticado);
                    registarAcao(username, "acessou a consulta de notificacoes");
                    break;
                case 7:
                    clearScreen();
                    if (gereUtilizador.pedirRemocaoUtilizador(aUtilizadorAutenticado)) {
                        ArrayList<Utilizador> gestores = gereUtilizador.getUtilizadoresByClassName("Gestor");
                        Iterator<Utilizador> iterator = gestores.iterator();
                        while (iterator.hasNext()) {
                            Utilizador gestor = iterator.next();
                            Notificacao notificacao = new Notificacao(
                                    "Pedido de remocao de conta: " + username, gestor);
                            gereNotificacao.adicionarNotificacao(notificacao);
                        }
                        clearScreen();
                        System.out.println("Pedido de remocao de conta enviado!\n");
                        registarAcao(username, "pedido de remocao de conta enviado");
                    } else {
                        clearScreen();
                        System.out
                                .println("Ocorreu um erro ao enviar o pedido de remocao de conta. Tente novamente.\n");
                        registarAcao(username, "erro ao enviar pedido de remocao de conta");
                    }
                    break;
                case 0:
                    registarAcao(username, "saiu da aplicacao do cliente");
                    username="sistema";
                    
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao(username,
                            "tentou acessar uma opcao invalida na aplicacao do cliente");
            }

        } while (opMenuCliente != 0);
    }

    public static void solicitarServico(Utilizador aUtilizadorAutenticado) {

        Cliente clienteAutenticado = (Cliente) aUtilizadorAutenticado;

        Trajeto trajeto = gereTrajeto.criarTrajeto(leDadosDouble("Introduza a latitude de origem: "),
                leDadosDouble("Introduza a longitude de origem: "));

        if (trajeto != null) {
            if (gereServico.criarServico(clienteAutenticado, trajeto)) {

                clearScreen();
                System.out.println("Servico criado com sucesso!\n");
                registarAcao(clienteAutenticado.getLogin(), "solicitou um novo servico com sucesso");
                ArrayList<Utilizador> gestores = gereUtilizador.getUtilizadoresByClassName("Gestor");
                Iterator<Utilizador> iterator = gestores.iterator();
                while (iterator.hasNext()) {
                    Utilizador gestor = iterator.next();
                    Notificacao notificacao = new Notificacao(
                            "Novo servico solicitado pelo cliente: " + clienteAutenticado.getLogin(), gestor);
                    gereNotificacao.adicionarNotificacao(notificacao);
                }
            } else {
                clearScreen();
                System.out.println("Ocorreu um erro ao criar o servico. Tente novamente.\n");
                registarAcao(clienteAutenticado.getLogin(), "erro ao solicitar um novo servico");
            }
        } else {
            clearScreen();
            System.out.println("Ocorreu um erro ao criar o trajeto. Tente novamente.\n");
            registarAcao(clienteAutenticado.getLogin(), "erro ao criar trajeto para um novo servico");
        }
    }

    public static void verMeusServicosCliente(Utilizador aUtilizadorAutenticado) {

        int opVerMeusServicos = -1;

        Cliente cliente = (Cliente) aUtilizadorAutenticado;

        do {

            opVerMeusServicos = leDadosInt(gereInterface.getInterface(18));

            switch (opVerMeusServicos) {

                case 1:
                    clearScreen();
                    gereServico.listarServicosPorCliente(cliente);
                    registarAcao(cliente.getLogin(), "listou todos os seus servicos");
                    break;
                case 2:
                    clearScreen();
                    gereServico.listarServicosPorCliente(cliente, 1);
                    registarAcao(cliente.getLogin(), "listou seus servicos pendentes");
                    break;
                case 3:
                    clearScreen();
                    gereServico.listarServicosPorCliente(cliente, 3);
                    registarAcao(cliente.getLogin(), "listou seus servicos em andamento");
                    break;
                case 4:
                    clearScreen();
                    gereServico.listarServicosPorCliente(cliente, 6);
                    registarAcao(cliente.getLogin(), "listou seus servicos concluidos");
                    break;
                case 0:
                    clearScreen();
                    registarAcao(cliente.getLogin(), "saiu da visualizacao de seus servicos");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao(cliente.getLogin(),
                            "tentou acessar uma opcao invalida na visualizacao de seus servicos");
            }
        } while (opVerMeusServicos != 0);
    }

    public static void confirmarConclusaoServico(Utilizador aUtilizadorAutenticado) {

        Cliente aCliente = (Cliente) aUtilizadorAutenticado;

        ArrayList<Servico> servicos = gereServico.listarServicosPorCliente(aCliente, 5);

        if (servicos.isEmpty()) {
            clearScreen();
            System.out.println("Nao existem servicos para confirmar.\n");
            registarAcao(aCliente.getLogin(), "verificou servicos para confirmar - nenhum encontrado");
        } else {
            Iterator<Servico> listaServicos = servicos.iterator();
            while (listaServicos.hasNext()) {
                Servico servico = listaServicos.next();
                System.out.println("Servico: " + servico + "\n");
            }

            int idServico;
            Servico servicoParaConfirmar;
            do {
                idServico = leDadosInt("Introduza o id do servico que pretende confirmar: ");
                servicoParaConfirmar = gereServico.getServico(idServico);
                if (servicoParaConfirmar == null) {
                    System.out.println("Opcao Invalida. Por favor, introduza um id valido.\n");
                }
            } while (servicoParaConfirmar == null);

            if (gereServico.confirmarFimServico(servicoParaConfirmar)) {
                clearScreen();
                aCliente.setNServicos(aCliente.getNServicos() + 1);
                aCliente.setDistanciaTotalServicos(
                        aCliente.getDistanciaTotalServicos() + servicoParaConfirmar.getTrajeto().getDistancia());
                aCliente.setDuracaoTotalServicos(aCliente.getDuracaoTotalServicos()
                        .plusHours(servicoParaConfirmar.getTrajeto().getDuracao().getHour())
                        .plusMinutes(servicoParaConfirmar.getTrajeto().getDuracao().getMinute())
                        .plusSeconds(servicoParaConfirmar.getTrajeto().getDuracao().getSecond()));
                aCliente.setCustoTotalServicos(aCliente.getCustoTotalServicos() + servicoParaConfirmar.getValorTotal());

                servicoParaConfirmar.getCondutor()
                        .setNServicos((servicoParaConfirmar.getCondutor().getNServicos() + 1));
                servicoParaConfirmar.getCondutor()
                        .setDistanciaTotalServicos(servicoParaConfirmar.getCondutor().getDistanciaTotalServicos()
                                + servicoParaConfirmar.getTrajeto().getDistancia());
                servicoParaConfirmar.getCondutor()
                        .setDuracaoTotalServicos(servicoParaConfirmar.getCondutor().getDuracaoTotalServicos()
                                .plusHours(servicoParaConfirmar.getTrajeto().getDuracao().getHour())
                                .plusMinutes(servicoParaConfirmar.getTrajeto().getDuracao().getMinute())
                                .plusSeconds(servicoParaConfirmar.getTrajeto().getDuracao().getSecond()));
                servicoParaConfirmar.getCondutor()
                        .setCustoTotalServicos(servicoParaConfirmar.getCondutor().getCustoTotalServicos()
                                + servicoParaConfirmar.getValorTotal());

                servicoParaConfirmar.getCondutor().getVeiculo().setDistanciaPercorrida(
                        (servicoParaConfirmar.getCondutor().getVeiculo().getDistanciaPercorrida()
                                + servicoParaConfirmar.getTrajeto().getDistancia()));

                System.out.println("Servico confirmado com sucesso!\n");
                registarAcao(aCliente.getLogin(),
                        "confirmou conclusao do servico com id: " + servicoParaConfirmar.getId());

                System.out.println("Emissao de Recibo:\n\n");
                System.out.println("ID do Servico: " + servicoParaConfirmar.getId() + "\n" +
                        "Cliente: " + servicoParaConfirmar.getCliente().getNome() + "\n" +
                        "NIF: " + servicoParaConfirmar.getCliente().getNIF() + "\n" +
                        "Valor Total: " + servicoParaConfirmar.getValorTotal() + "\n" +
                        "Trajeto: " + servicoParaConfirmar.getTrajeto() + "\n");
            } else {
                clearScreen();
                System.out.println("Ocorreu um erro ao confirmar o servico. Tente novamente.\n");
                registarAcao(aCliente.getLogin(),
                        "erro ao confirmar conclusao do servico com id: " + servicoParaConfirmar.getId());
            }
        }
    }

    public static void verEstatisticasPessoais(Utilizador aUtilizadorAutenticado) {

        if (aUtilizadorAutenticado.getTipoUtilizador().equals("Cliente")) {

            Cliente clienteAutenticado = (Cliente) aUtilizadorAutenticado;
            System.out.println("Estatisticas Pessoais do Cliente:\n" +
                    "Nome: " + clienteAutenticado.getNome() + "\n" +
                    "Numero de servicos realizados: " + clienteAutenticado.getNServicos() + "\n" +
                    "Distancia total dos servicos: " + clienteAutenticado.getDistanciaTotalServicos() + "\n" +
                    "Duracao total dos servicos: " + clienteAutenticado.getDuracaoTotalServicos() + "\n" +
                    "Custo total dos servicos: " + clienteAutenticado.getCustoTotalServicos());
            registarAcao(clienteAutenticado.getLogin(), "visualizou estatisticas pessoais");

        } else if (aUtilizadorAutenticado.getTipoUtilizador().equals("Condutor")) {

            Condutor condutorAutenticado = (Condutor) aUtilizadorAutenticado;
            System.out.println("Estatisticas Pessoais do Condutor:\n" +
                    "Nome: " + condutorAutenticado.getNome() + "\n" +
                    "Numero de servicos realizados: " + condutorAutenticado.getNServicos() + "\n" +
                    "Distancia total dos servicos: " + condutorAutenticado.getDistanciaTotalServicos() + "\n" +
                    "Duracao total dos servicos: " + condutorAutenticado.getDuracaoTotalServicos() + "\n" +
                    "Custo total dos servicos: " + condutorAutenticado.getCustoTotalServicos());
            registarAcao(condutorAutenticado.getLogin(), "visualizou estatisticas pessoais");
        }
    }

    public static void escolherOpcaoAlteracao(Utilizador aUtilizadorAutenticado) {

        int opMenuAlteracao = -1;

        do {

            if (aUtilizadorAutenticado.getTipoUtilizador().equals("Gestor")) {
                opMenuAlteracao = leDadosInt(gereInterface.getInterface(8));
            } else if (aUtilizadorAutenticado.getTipoUtilizador().equals("Condutor")) {
                opMenuAlteracao = leDadosInt(gereInterface.getInterface(9));
            } else if (aUtilizadorAutenticado.getTipoUtilizador().equals("Cliente")) {
                opMenuAlteracao = leDadosInt(gereInterface.getInterface(10));
            }

            switch (opMenuAlteracao) {

                case 1:
                    String novoLogin = leDadosString("Introduza o seu novo username: ", "");
                    if (gereUtilizador.alterarDadosUtilizador(aUtilizadorAutenticado, novoLogin, opMenuAlteracao)) {
                        clearScreen();
                        System.out.println("Username alterado com sucesso!\n");
                        registarAcao(username, "alterou o username com sucesso");
                        opMenuAlteracao = 0;
                    } else {
                        clearScreen();
                        System.out.println("O username que introduziu ja se encontra em uso. Tente novamente.\n");
                        registarAcao(username, "falhou ao alterar o username");
                    }
                    break;
                case 2:
                    String novaPassword = leDadosString("Introduza a sua password: ", "password");
                    if (gereUtilizador.alterarDadosUtilizador(aUtilizadorAutenticado, novaPassword, opMenuAlteracao)) {
                        clearScreen();
                        System.out.println("Password alterada com sucesso!\n");
                        registarAcao(username, "alterou a password com sucesso");
                        opMenuAlteracao = 0;
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao tentar alterar a password. Tente novamente.\n");
                        registarAcao(username, "falhou ao alterar a password");
                    }
                    break;
                case 3:
                    String novoNome = leDadosString("Introduza o seu novo nome: ", "");
                    if (gereUtilizador.alterarDadosUtilizador(aUtilizadorAutenticado, novoNome, opMenuAlteracao)) {
                        clearScreen();
                        System.out.println("Nome alterado com sucesso!\n");
                        registarAcao(username, "alterou o nome com sucesso");
                        opMenuAlteracao = 0;
                    } else {
                        clearScreen();
                        System.out.println("Ocorreu um erro ao tentar alterar o nome. Tente novamente.\n");
                        registarAcao(username, "falhou ao alterar o nome");
                    }
                    break;
                case 4:
                    String novoEmail = leDadosString("Introduza o seu novo email: ", "email");
                    if (gereUtilizador.alterarDadosUtilizador(aUtilizadorAutenticado, novoEmail, opMenuAlteracao)) {
                        clearScreen();
                        System.out.println("Email alterado com sucesso!\n");
                        registarAcao(username, "alterou o email com sucesso");
                        opMenuAlteracao = 0;
                    } else {
                        clearScreen();
                        System.out.println("O email que introduziu ja se encontra em uso. Tente novamente.\n");
                        registarAcao(username, "falhou ao alterar o email");
                    }
                    break;
                case 5:
                    if (aUtilizadorAutenticado.getTipoUtilizador().equals("Cliente")) {
                        String novaMorada = leDadosString("Introduza a sua nova morada: ", "");
                        if (gereUtilizador.alterarDadosUtilizador(aUtilizadorAutenticado, novaMorada,
                                opMenuAlteracao)) {
                            clearScreen();
                            System.out.println("Morada alterada com sucesso!\n");
                            registarAcao(username, "alterou a morada com sucesso");
                            opMenuAlteracao = 0;
                        } else {
                            clearScreen();
                            System.out.println("Ocorreu um erro ao tentar alterar a morada. Tente novamente.\n");
                            registarAcao(username, "falhou ao alterar a morada");
                        }
                    } else if (aUtilizadorAutenticado.getTipoUtilizador().equals("Condutor")) {
                        String novaCartaConducao = leDadosString(gereInterface.getInterface(15), "habilitacao");
                        if (gereUtilizador.alterarDadosUtilizador(aUtilizadorAutenticado, novaCartaConducao,
                                opMenuAlteracao)) {
                            clearScreen();
                            System.out.println("Carta de conducao alterada com sucesso!\n");
                            registarAcao(username, "alterou a carta de conducao com sucesso");
                            opMenuAlteracao = 0;
                        } else {
                            clearScreen();
                            System.out.println(
                                    "Ocorreu um erro ao tentar alterar a carta de conducao. Tente novamente.\n");
                            registarAcao(username, "falhou ao alterar a carta de conducao");
                        }
                    } else {
                        clearScreen();
                        System.out.println("Opcao invalida. Tente novamente.\n");
                        registarAcao(username,
                                "tentou acessar uma opcao invalida na alteracao de dados");
                    }
                    break;
                case 0:
                    registarAcao(username, "saiu da alteracao de dados");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opcao invalida. Tente novamente.\n");
                    registarAcao(username,
                            "tentou acessar uma opcao invalida na alteracao de dados");
            }
        } while (opMenuAlteracao != 0);
    }

    private static void guardarData(GereUtilizador aGereUtilizador, GereServico aGereServico,
            GereNotificacao aGereNotificacao, GereTrajeto aGereTrajeto, GereVeiculo aGereVeiculo) {
        try {
            FicheiroDados dados = new FicheiroDados(aGereUtilizador, aGereServico, aGereNotificacao, aGereTrajeto,
                    aGereVeiculo);
            if (dados.getGereUtilizador() != null || dados.getGereServico() != null
                    || dados.getGereNotificacao() != null || dados.getGereTrajeto() != null
                    || dados.getGereVeiculo() != null) {
                File ficheiro = new File("dados_apl.dat");
                if (!ficheiro.exists()) {
                    try {
                        ficheiro.createNewFile();
                        System.out.println("ficheiro dados_apl.dat criado com sucesso.");

                    } catch (IOException e) {
                        System.err.println("Erro ao criar ficheiro: " + e.getMessage());

                    }
                }

                FileOutputStream fos = new FileOutputStream("dados_apl.dat");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(dados);
                oos.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static FicheiroDados carregarData() {

        File ficheiro = new File("dados_apl.dat");
        try {
            FileInputStream fos = new FileInputStream(ficheiro);
            BufferedInputStream bis = new BufferedInputStream(fos);
            ObjectInputStream ois = new ObjectInputStream(bis);
            FicheiroDados dados = (FicheiroDados) ois.readObject();
            ois.close();
            System.out.println("Dados carregados com sucesso de dados_apl.dat.");
            return dados;
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro dados_apl.dat. O ficheiro pode estar indisponivel.");
            return null;
        }
    }

    public static void guardarDataManual() {
        String caminhoFicheiro = leDadosString("Introduza o caminho para guardar o ficheiro de dados: ", "");

        try {
            FicheiroDados dados = new FicheiroDados(gereUtilizador, gereServico, gereNotificacao, gereTrajeto,
                    gereVeiculo);
            if (dados.getGereUtilizador() != null || dados.getGereServico() != null
                    || dados.getGereNotificacao() != null || dados.getGereTrajeto() != null
                    || dados.getGereVeiculo() != null) {
                File ficheiro = new File(caminhoFicheiro);
                if (!ficheiro.exists()) {
                    try {
                        ficheiro.createNewFile();
                        System.out.println("Ficheiro criado com sucesso.");
                    } catch (IOException e) {
                        System.err.println("Erro ao criar ficheiro: " + e.getMessage());
                    }
                }

                FileOutputStream fos = new FileOutputStream(caminhoFicheiro);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(dados);
                oos.close();
                System.out.println("Dados guardados com sucesso.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FicheiroDados carregarDataManual() {
        String caminhoFicheiro = leDadosString("Introduza o caminho do ficheiro de dados: ", "");

        File ficheiro = new File(caminhoFicheiro);
        try {
            FileInputStream fos = new FileInputStream(ficheiro);
            BufferedInputStream bis = new BufferedInputStream(fos);
            ObjectInputStream ois = new ObjectInputStream(bis);
            FicheiroDados dados = (FicheiroDados) ois.readObject();
            ois.close();
            System.out.println("Dados carregados com sucesso.");
            return dados;
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro. O ficheiro pode estar indisponivel.");
            return null;
        }
    }

    public static void exportarServicosParaCSV() {

        String caminhoFicheiro = leDadosString("Introduza o caminho do ficheiro CSV: ", "");

        try (PrintWriter writer = new PrintWriter(new File(caminhoFicheiro))) {
            writer.println("Origem,Dia,Hora,Distancia,Duracao");

            List<Servico> servicosConcluidos = gereServico.getServicosPorEstado(6);
            Collections.sort(servicosConcluidos,
                    (s1, s2) -> s2.getTrajeto().getDia().compareTo(s1.getTrajeto().getDia())); // Ordenar do mais
                                                                                               // recente ao mais antigo

            Iterator<Servico> iterator = servicosConcluidos.iterator();
            while (iterator.hasNext()) {
                Servico servico = iterator.next();
                Trajeto trajeto = servico.getTrajeto();
                LocalDate data = trajeto.getDia();
                LocalTime hora = trajeto.getHora();
                float distancia = trajeto.getDistancia();
                LocalTime duracao = trajeto.getDuracao();

                writer.printf("%s,%s,%s,%.2f,%s%n",
                        trajeto.getLatitudeOrigem() + ", " + trajeto.getLongitudeOrigem(),
                        data.format(dateFormatter),
                        hora.format(timeFormatter),
                        distancia,
                        duracao.format(timeFormatter));
            }

            System.out.println("Dados exportados com sucesso para " + caminhoFicheiro);
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao exportar dados: " + e.getMessage());
        }
    }

    public static void registarAcao(String utilizador, String acao) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)))) {
            writer.println(utilizador + " <> " + acao);
        } catch (IOException e) {
            System.out.println("Erro ao registar acao no log: " + e.getMessage());
        }
    }

    public static void consultarLog() {
        List<String> logLinhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("log.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                logLinhas.add(linha);
            }
            Collections.reverse(logLinhas); // mostrar as acoes mais recentes no inicio
            for (String logLinha : logLinhas) {
                System.out.println(logLinha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o log: " + e.getMessage());
        }
    }

    public static InfoSistema carregarInfoSistema() {
        try {
            FileInputStream fis = new FileInputStream("info_sistema.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            InfoSistema infoSistema = (InfoSistema) ois.readObject();
            ois.close();
            fis.close();
            return infoSistema;
        } catch (IOException | ClassNotFoundException e) {
            return new InfoSistema(0, "");
        }
    }

    public static void guardarInfoSistema(InfoSistema infoSistema) {
        try {
            FileOutputStream fos = new FileOutputStream("info_sistema.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(infoSistema);
            out.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Erro ao guardar info_sistema.dat: " + e.getMessage());
        }
    }

    public static void consultarNotificacoes(Utilizador utilizador) {
        ArrayList<Notificacao> notificacoes = gereNotificacao.getNotificacoesPorDestinatario(utilizador);

        if (notificacoes.isEmpty()) {
            System.out.println("Nao tem novas notificacoes.\n");
            registarAcao(utilizador.getLogin(), "consultou notificacoes - nenhuma nova notificacao");
        } else {
            System.out.println("Tem " + notificacoes.size() + " notificacoes:\n");
            registarAcao(utilizador.getLogin(),
                    "consultou notificacoes - " + notificacoes.size() + " novas notificacoes");
            Iterator<Notificacao> iterator = notificacoes.iterator();
            while (iterator.hasNext()) {
                Notificacao notificacao = iterator.next();
                System.out.println(notificacao + "\n");
            }
        }
    }

}
