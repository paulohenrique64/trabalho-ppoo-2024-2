package simulacao;

import java.util.Random;

import imagens.ImagensCarroAzul;
import imagens.ImagensMotoVermelha;
import mapa.Mapa;
import util.Localizacao;
import veiculos.Carro;
import veiculos.Moto;
import veiculos.Veiculo;

/**
 * Representa a área do sistema onde será realizada a comunicação entre a interface (JanelaSimulacao) 
 * e o funcionamento interno do sistema (Estacionamento)
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Simulacao {
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    private int velocidadeSimulacao;
    private int fluxoVeiculos;

    public Simulacao() {
        this.mapa = new Mapa();
        janelaSimulacao = new JanelaSimulacao(mapa);
        velocidadeSimulacao = 20;
        fluxoVeiculos = 7;
    }

    // este metodo, tem o papel de ser a ponte de comunicacao entre as classes Mapa e JanelaSimulacao
    //
    // os veiculos sao instanciados aqui e enviados para uma instancia de Mapa
    // que por sua vez, junto a uma instancia de Estacionamento, gerenciar as vagas, entrada de veiculos, tickets, etc
    //
    // uma instancia de JanelaSimulacao, contem essa mesma instancia de Mapa,
    // logo, todos os veiculos presentes na instancia de Mapa, podem ser acessados e devidamente renderizados
    // na instancia de JanelaSimulacao
    public void iniciarSimulacao() {
        Random random = new Random();
        
        while (true) {
            // spawnandos os veiculos no lado de fora do estacionamento
            while (mapa.getQuantidadeVeiculosIndoParaEntradaEstacionamento() < fluxoVeiculos) {
                Veiculo veiculo = null;
                String placa = String.valueOf(random.nextInt(3000, 10000));

                // as chances de "spawnar" um carro e uma moto sao iguais
                if (random.nextBoolean()) {
                    veiculo = new Carro(
                                        placa, 
                                        new Localizacao(100, 100), 
                                        Localizacao.carregarCaminho("data/caminho-ate-entrada.txt"),  
                                        4, 
                                        random.nextInt(50, 170), 
                                        new ImagensCarroAzul()
                                    );
                } else {
                    veiculo = new Moto(
                                        placa, 
                                        new Localizacao(100, 100), 
                                        Localizacao.carregarCaminho("data/caminho-ate-entrada.txt"), 
                                        2, 
                                        random.nextInt(50, 200), 
                                        new ImagensMotoVermelha()
                                    );
                }

                mapa.adicionarVeiculo(veiculo);
            }

            mapa.executarUmPasso();
            janelaSimulacao.atualizarFaturamento(mapa.getFaturamentoEstacionamento());
            janelaSimulacao.atualizarJanelaSimulacao();

            // tempo entre cada atualizacao da imagem 60 -> intervalo de 60 milisegundos entre cada atualizacao da imagem
            try {
                Thread.sleep(velocidadeSimulacao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void aumentarVelocidadeSimulacao() {
        if (velocidadeSimulacao > 5) {
            velocidadeSimulacao -= 0.20 * velocidadeSimulacao;
            System.out.println("Velocidade da simulacao -> " + velocidadeSimulacao);
        }   
    }

    public void diminuirVelocidadeSimulacao() {
        velocidadeSimulacao += 5;
        System.out.println("Velocidade da simulacao -> " + velocidadeSimulacao);
    }

    public void aumentarFluxoVeiculos() {
        if (fluxoVeiculos < 7) {
            fluxoVeiculos += 1;
            System.out.println("Fluxo de veiculos -> " + fluxoVeiculos);
        }   
    }

    public void diminuirFluxoVeiculos() {
        if (fluxoVeiculos > 1) {
            fluxoVeiculos -= 1;
            System.out.println("Fluxo de veiculos -> " + fluxoVeiculos);
        }     
    }
}