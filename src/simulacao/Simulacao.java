package simulacao;

import java.util.Queue;
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
    private int velocidadeSimulacao;
    private int fluxoVeiculos;
    private Mapa mapa;
    private JanelaSimulacao janelaSimulacao;

    public Simulacao(int velocidadeSimulacao, int fluxoVeiculos) {
        this.velocidadeSimulacao = velocidadeSimulacao;
        this.fluxoVeiculos = fluxoVeiculos;
        this.mapa = new Mapa(velocidadeSimulacao / 2);
        janelaSimulacao = new JanelaSimulacao(mapa);
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
            // spawnandos os veiculos no mapa
            // cada veiculo recem spawnado, por padrao,
            // tem como destino e status atual a entrada do estacionamento
            while (mapa.getQuantidadeVeiculosIndoParaEntradaEstacionamento() < fluxoVeiculos) {
                Veiculo veiculo = null;
                String placa = String.valueOf(random.nextInt(3000, 10000));
                Queue<Localizacao> caminho = Localizacao.carregarCaminho("data/caminho-ate-entrada.txt");
                Localizacao locI = new Localizacao(100, 100); // localizacao inicial do veiculo no mapa
                
                // chances de 7 em 10 de spawnar um carro
                if (random.nextInt(1, 10) > 3) {
                    int cavalosDePotencia = random.nextInt(50, 170);
                    veiculo = new Carro(placa, locI,caminho,4, cavalosDePotencia, new ImagensCarroAzul()); 
                } else {
                    int cilindradas = random.nextInt(50, 200);
                    veiculo = new Moto(placa, locI, caminho, 2, cilindradas, new ImagensMotoVermelha());
                }

                if (veiculo != null)
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
}