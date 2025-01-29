package simulacao;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Queue;
import java.util.Random;

import mapa.Mapa;
import utilitarios.ImagensCarroAzul;
import utilitarios.ImagensMotoVermelha;
import utilitarios.Localizacao;
import veiculos.Carro;
import veiculos.Moto;
import veiculos.Veiculo;

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
    private static Random rand = new Random();
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

        // Validando a duracao da simulacao
        if (duracaoDaSimulacao < 0)
            duracaoDaSimulacao = 0;
        
        // Validando a velocidade da simulacao
        if (velocidadeSimulacao < 2) 
            this.velocidadeSimulacao = 2;

        // Validando o tamanho do fluxo de veiculos
        if (fluxoVeiculos < 1) 
            this.fluxoVeiculos = 1;

        if (fluxoVeiculos > 7) 
            this.fluxoVeiculos = 7;

        horarioInicioSimulacao = LocalDateTime.now();
        horarioFimSimulacao = horarioInicioSimulacao.plusSeconds(duracaoDaSimulacao);

        this.mapa = new Mapa(this.velocidadeSimulacao / 2);
        janelaSimulacao = new JanelaSimulacao(mapa);
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
                mapa.adicionarVeiculo(getVeiculoParaSimulacao());
            }

            // Executa um passo da simulação, atualiza a interface e o faturamento
            mapa.executarUmPasso();
            janelaSimulacao.atualizarFaturamento(mapa.getFaturamentoEstacionamento());
            janelaSimulacao.atualizarJanelaSimulacao();

            // Tempo de espera entre cada atualização
            try {
                Thread.sleep(velocidadeSimulacao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Montar e exibir o popup/mensagem final ao finalizar a simulacao
        Duration duracao = Duration.between(horarioInicioSimulacao, horarioFimSimulacao);
        long duracaoEmSegundos = duracao.getSeconds();

        String fraseFinal = String.format("%nA simulação foi finalizada com sucesso.");
        fraseFinal += String.format("%nTempo de simulação: %d segundos.%n", duracaoEmSegundos);
        fraseFinal += String.format("Faturamento total do estacionamento: R$ %.2f.%n", mapa.getFaturamentoEstacionamento());

        System.out.println(fraseFinal);
        janelaSimulacao.exibirPopupFinal(fraseFinal);
    }

    /**
     * Cria e retorna um novo veículo para ser adicionado à simulação.
     * 
     * O veículo pode ser um carro ou uma moto, gerado aleatoriamente.
     * Existe uma chance de 70% de ser um carro e 30% de ser uma moto.
     * 
     * @return Um novo objeto do tipo {@link Veiculo}.
     */
    public Veiculo getVeiculoParaSimulacao() {
        Queue<Localizacao> caminho = Localizacao.carregarCaminho("data/caminhos/caminho-ate-entrada.txt");
        String placa = rand.nextInt(10000) + "-" + LocalDateTime.now().getNano();
        Localizacao localizacaoInicial = new Localizacao(100, 100);
        Veiculo veiculo = null;

        // Define aleatoriamente se o veículo será um carro ou uma moto
        if (rand.nextInt(10) > 2) {
            int cavalosDePotencia = rand.nextInt(170) + 50;
            veiculo = new Carro(placa, localizacaoInicial, caminho, 4, cavalosDePotencia, new ImagensCarroAzul());
        } else {
            int cilindradas = rand.nextInt(200) + 50;
            veiculo = new Moto(placa, localizacaoInicial, caminho, 2, cilindradas, new ImagensMotoVermelha());
        }

        return veiculo;
    }
}