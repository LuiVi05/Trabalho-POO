import java.util.HashMap;

public class GereInterface {

    private HashMap<Integer, String> interfaces;

    public GereInterface() {
        this.interfaces = new HashMap<>();
        inicializarInterfaces();
    }

    public void inicializarInterfaces() {
        interfaces.put(1, "*****************************************\n" +
                "|                                       |\n" +
                "|                QuickRide              |\n" +
                "|                                       |\n" +
                "|      Bem-vindo a melhor aplicacao     |\n" +
                "|           transporte do mundo         |\n" +
                "|                                       |\n" +
                "|              Bom apetite!             |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(2, "*****************************************\n" +
                "|                                       |\n" +
                "|          Escolhe uma opcao:           |\n" +
                "|          1. Autenticar Conta          |\n" +
                "|          2. Criar Conta               |\n" +
                "|          0. Encerrar Aplicacao        |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(3, "*****************************************\n" +
                "|                                       |\n" +
                "|                Adeus!                 |\n" +
                "|                                       |\n" +
                "|           Obrigado por usar           |\n" +
                "|               QuickRide               |\n" +
                "|                                       |\n" +
                "|           Tenha um bom dia!           |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(4, "*****************************************\n" +
                "|                                       |\n" +
                "|    Que tipo de conta deseja criar?    |\n" +
                "|                                       |\n" +
                "|          Escolhe uma opcao:           |\n" +
                "|          1. Gestor                    |\n" +
                "|          2. Condutor                  |\n" +
                "|          3. Cliente                   |\n" +
                "|          0. Voltar                    |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(5, "*****************************************\n" +
                "|                GESTOR                 |\n" +
                "|                                       |\n" +
                "|        Escolhe uma opcao:             |\n" +
                "|        1. Gerir Utilizadores          |\n" +
                "|        2. Gerir Servicos              |\n" +
                "|        3. Registar Novo Veiculo       |\n" +
                "|        4. Monitorizacao de Logs       |\n" +
                "|        5. Persistencia de Dados       |\n" +
                "|        6. Alterar Dados da Conta      |\n" +
                "|        7. Notificacoes                |\n" +
                "|        8. Pedir Remocao de Conta      |\n" +
                "|        0. Terminar Sessao             |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(6, "*****************************************\n" +
                "|               CONDUTOR                |\n" +
                "|                                       |\n" +
                "|      Escolhe uma opcao:               |\n" +
                "|      1. Ver Meus Servicos             |\n" +
                "|      2. Aceitar/Rejeitar Servico      |\n" +
                "|      3. Registar Conclusao de Servico |\n" +
                "|      4. Estatisticas Pessoais         |\n" +
                "|      5. Alterar Dados da Conta        |\n" +
                "|      6. Notificacoes                  |\n" +
                "|      7. Pedir Remocao de Conta        |\n" +
                "|      0. Terminar Sessao               |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(7, "*****************************************\n" +
                "|                CLIENTE                |\n" +
                "|                                       |\n" +
                "|     Escolhe uma opcao:                |\n" +
                "|     1. Solicitar Servico              |\n" +
                "|     2. Ver Meus Servicos              |\n" +
                "|     3. Confirmar Conclusao de Servico |\n" +
                "|     4. Estatisticas Pessoais          |\n" +
                "|     5. Alterar Dados da Conta         |\n" +
                "|     6. Notificacoes                   |\n" +
                "|     7. Pedir Remocao de Conta         |\n" +
                "|     0. Terminar Sessao                |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(8, "*****************************************\n" +
                "|                                       |\n" +
                "|     Que informacao deseja alterar?    |\n" +
                "|                                       |\n" +
                "|          Escolhe uma opcao:           |\n" +
                "|          1. Username                  |\n" +
                "|          2. Password                  |\n" +
                "|          3. Nome                      |\n" +
                "|          4. Email                     |\n" +
                "|          0. Sair                      |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(9, "*****************************************\n" +
                "|                                       |\n" +
                "|     Que informacao deseja alterar?    |\n" +
                "|                                       |\n" +
                "|          Escolhe uma opcao:           |\n" +
                "|          1. Username                  |\n" +
                "|          2. Password                  |\n" +
                "|          3. Nome                      |\n" +
                "|          4. Email                     |\n" +
                "|          5. Morada                    |\n" +
                "|          0. Sair                      |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(10, "*****************************************\n" +
                "|                                       |\n" +
                "|     Que informacao deseja alterar?    |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. Username                   |\n" +
                "|         2. Password                   |\n" +
                "|         3. Nome                       |\n" +
                "|         4. Email                      |\n" +
                "|         5. Carta de Conducao          |\n" +
                "|         0. Sair                       |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(11, "*****************************************\n" +
                "|           GERIR UTILIZADORES          |\n" +
                "|                                       |\n" +
                "|  Escolhe uma opcao:                   |\n" +
                "|  1. Criar Utilizador                  |\n" +
                "|  2. Aprovar/Rejeitar Registo          |\n" +
                "|  3. Ativar/Desativar Conta            |\n" +
                "|  4. Aprovar/Rejeitar Remocao de Conta |\n" +
                "|  5. Listar Utilizadores               |\n" +
                "|  6. Pesquisar Utilizadores            |\n" +
                "|  0. Voltar                            |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(12, "*****************************************\n" +
                "|                                       |\n" +
                "| Deseja ativar ou desativar uma conta? |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. Ativar                     |\n" +
                "|         2. Desativar                  |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(13, "*****************************************\n" +
                "|          LISTAR UTILIZADORES          |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. Listar Todos               |\n" +
                "|         2. Listar Gestores            |\n" +
                "|         3. Listar Condutores          |\n" +
                "|         4. Listar Clientes            |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(14, "*****************************************\n" +
                "|         PESQUISAR UTILIZADORES        |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. Pesquisar por Login        |\n" +
                "|         2. Pesquisar Condutores       |\n" +
                "|            por Habilitacao            |\n" +
                "|         3. Pesquisar Condutores       |\n" +
                "|            por Distancia Acumulada    |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(15, "*****************************************\n" +
                "|                                       |\n" +
                "|    Que tipo de habilitacao possui?    |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. B                          |\n" +
                "|         2. A1                         |\n" +
                "|         3. A2                         |\n" +
                "|         4. AM                         |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(16, "*****************************************\n" +
                "|                                       |\n" +
                "|     Que tipo de habilitacao quer?     |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. B                          |\n" +
                "|         2. A1                         |\n" +
                "|         3. A2                         |\n" +
                "|         4. AM                         |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(17, "*****************************************\n" +
                "|             GERIR SERVICOS            |\n" +
                "|                                       |\n" +
                "|      Escolhe uma opcao:               |\n" +
                "|      1. Aprovar/Rejeitar Sercico      |\n" +
                "|      2. Atribuir Novo Condutor        |\n" +
                "|      3. Listar Servicos               |\n" +
                "|      4. Pesquisar Servicos            |\n" +
                "|      5. Listar Trajetos               |\n" +
                "|      0. Voltar                        |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(18, "*****************************************\n" +
                "|            VER MEUS SERVICOS          |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. Listar Todos               |\n" +
                "|         2. Pesquisar Pendentes        |\n" +
                "|         3. Pesquisar A Decorrer       |\n" +
                "|         4. Pesquisar Concluidos       |\n" +
                "|         0. Sair                       |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(19, "*****************************************\n" +
                "|          GERIR DADOS MANUALMENTE       |\n" +
                "|                                       |\n" +
                "|      Escolha uma opcao:               |\n" +
                "|      1. Ler dados de um ficheiro      |\n" +
                "|      2. Gravar dados num ficheiro     |\n" +
                "|      3. Exportar servicos para CSV    |\n" +
                "|      0. Voltar                        |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(20, "*****************************************\n" +
                "|            TIPO DE ORDENACAO          |\n" +
                "|                                       |\n" +
                "|         1. Distancia Total            |\n" +
                "|         2. Numero de Servicos         |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(21, "*****************************************\n" +
                "|             LISTAR SERVICOS           |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. Listar Todos               |\n" +
                "|         2. Pesquisar Pendentes        |\n" +
                "|         3. Pesquisar A Decorrer       |\n" +
                "|         4. Pesquisar Concluidos       |\n" +
                "|         0. Sair                       |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(22, "*****************************************\n" +
                "|            TIPO DE ORDENACAO          |\n" +
                "|                                       |\n" +
                "|         1. Distancia                  |\n" +
                "|         2. Duracao                    |\n" +
                "|         3. Data                       |\n" +
                "|         4. ID                         |\n" +
                "|         5. Custo                      |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(23, "*****************************************\n" +
                "|           PESQUISAR SERVICOS          |\n" +
                "|                                       |\n" +
                "|         Escolhe uma opcao:            |\n" +
                "|         1. Pesquisar por ID           |\n" +
                "|         2. Pesquisar por estado       |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(24, "*****************************************\n" +
                "|        ESCOLHA O ESTADO DO SERVICO    |\n" +
                "|                                       |\n" +
                "|         1. A espera de aprovacao      |\n" +
                "|         2. Aprovado pelo gestor       |\n" +
                "|         3. Aceite pelo condutor       |\n" +
                "|         4. Rejeitado pelo condutor    |\n" +
                "|         5. Concluido pelo condutor    |\n" +
                "|         6. Confirmado pelo cliente    |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

        interfaces.put(25, "*****************************************\n" +
                "|             LISTAR TRAJETOS           |\n" +
                "|                                       |\n" +
                "|         1. Lista todos                |\n" +
                "|         2. Listar por Cliente         |\n" +
                "|         3. Listar por Condutor        |\n" +
                "|         0. Voltar                     |\n" +
                "|                                       |\n" +
                "*****************************************");

    }

    public String getInterface(int aInterfaceId) {

        return interfaces.get(aInterfaceId);
    }

}