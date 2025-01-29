package simulacao;

import java.time.Duration;
import java.time.LocalDateTime;

import mapa.Mapa;
import utilitarios.FabricaDeVeiculos;

/**
 * Representa a simulação do estacionamento, coordenando a movimentação dos
 * veículos
 * e a interação com a interface gráfica.
 * 
 * A simulação gerencia a geração de veículos, a atualização do mapa e a
 * exibição da interface gráfica em tempo real.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Simulacao {
    private int velocidadeSimulacao;
    private int fluxoVeiculos;
    private LocalDateTime horarioInicioSimulacao;
    private LocalDateTime horarioFimSimulacao;
    private Mapa mapa;
    private JanelaSimulacao janelaSimulacao;

    /**
     * Construtor da simulação.
     * 
     * @param velocidadeSimulacao Intervalo de tempo (em milissegundos) entre cada
     *                            atualização.
     * @param fluxoVeiculos       Quantidade máxima de veículos que podem estar na
     *                            entrada simultaneamente.
     * @param duracaoDaSimulacao  Especifica a quantidade de tempo em minutos no qual 
     *                            a simulação deve permanecer executando.
     */
    public Simulacao(int velocidadeSimulacao, int fluxoVeiculos, int duracaoDaSimulacao) {
        this.velocidadeSimulacao = velocidadeSimulacao;
        this.fluxoVeiculos = fluxoVeiculos;

        // Validar velocidadeSimulacao, fluxoVeiculos e duracaoDaSimulacao
        if (duracaoDaSimulacao < 0) duracaoDaSimulacao = 0;
        if (velocidadeSimulacao < 2) this.velocidadeSimulacao = 2; 
        if (fluxoVeiculos < 1) this.fluxoVeiculos = 1;
        if (fluxoVeiculos > 7) this.fluxoVeiculos = 7;

        this.mapa = new Mapa(this.velocidadeSimulacao / 2);
        janelaSimulacao = new JanelaSimulacao(mapa);

        // Marcar o horario de inicio e fim da simulacao
        horarioInicioSimulacao = LocalDateTime.now();
        horarioFimSimulacao = horarioInicioSimulacao.plusSeconds(duracaoDaSimulacao);
    }

    /**
     * Inicia a simulação do estacionamento.
     * 
     * A cada iteração, a simulação adiciona veículos ao mapa, executa um passo na
     * simulação,
     * atualiza a interface gráfica e aguarda um tempo determinado antes da próxima
     * atualização.
     */
    public void iniciarSimulacao() {
        while (horarioFimSimulacao.compareTo(LocalDateTime.now()) > 0) {
            // Garante que o número de veículos na entrada do estacionamento não ultrapasse o limite definido

            while (mapa.getQuantidadeVeiculosIndoParaEntradaEstacionamento() < fluxoVeiculos) {
                mapa.adicionarVeiculo(FabricaDeVeiculos.getVeiculoAleatorio());
            }

            // Executa um passo da simulação, atualiza a interface e o faturamento
            mapa.executarUmPasso();
            janelaSimulacao.atualizarFaturamento(mapa.getFaturamentoEstacionamento());
            janelaSimulacao.atualizarJanelaSimulacao();

            pausarSimulacao();
        }

        finalizarSimulacao();
    }

    public void finalizarSimulacao() {
        // Montar e exibir o popup/mensagem final ao finalizar a simulacao
        Duration duracao = Duration.between(horarioInicioSimulacao, horarioFimSimulacao);
        long duracaoEmSegundos = duracao.getSeconds();

        String fraseFinal = String.format("%nA simulação foi finalizada com sucesso.");
        fraseFinal += String.format("%nTempo de simulação: %d segundos.%n", duracaoEmSegundos);
        fraseFinal += String.format("Faturamento total do estacionamento: R$ %.2f.%n", mapa.getFaturamentoEstacionamento());

        System.out.println(fraseFinal);
        janelaSimulacao.exibirPopupFinal(fraseFinal);
    }

    public void pausarSimulacao() {
        // Tempo de espera entre cada atualização
        try {
            Thread.sleep(velocidadeSimulacao);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}