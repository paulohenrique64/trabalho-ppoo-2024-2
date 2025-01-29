package simulacao;

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
    private Mapa mapa;
    private JanelaSimulacao janelaSimulacao;

    /**
     * Construtor da simulação.
     * 
     * @param velocidadeSimulacao Intervalo de tempo (em milissegundos) entre cada
     *                            atualização.
     * @param fluxoVeiculos       Quantidade máxima de veículos que podem estar na
     *                            entrada simultaneamente.
     */
    public Simulacao(int velocidadeSimulacao, int fluxoVeiculos) {
        this.velocidadeSimulacao = velocidadeSimulacao;

        // Validando a velocidade da simulacao
        if (velocidadeSimulacao <= 0) 
            this.velocidadeSimulacao = 1;

        this.fluxoVeiculos = fluxoVeiculos;

        // Validando o tamanho do fluxo de veiculos
        if (fluxoVeiculos <= 0) 
            this.fluxoVeiculos = 1;

        if (fluxoVeiculos >= 7) 
            this.fluxoVeiculos = 7;

        this.mapa = new Mapa(velocidadeSimulacao / 2);
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
        while (true) {
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