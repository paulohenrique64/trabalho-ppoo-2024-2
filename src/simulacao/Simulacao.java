package simulacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import mapa.Estacionamento;
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
    private Estacionamento estacionamento;

    public Simulacao() {
        estacionamento = new Estacionamento();
        janelaSimulacao = new JanelaSimulacao(estacionamento, this);
    }

    // este metodo, tem o papel de ser a ponte de comunicacao entre as classes Estacionamento e JanelaSimulacao
    //
    // os veiculos sao instanciados aqui e enviados para uma instancia de Estacionamento
    // que por sua vez, gerenciar as vagas, entrada de veiculos, tickets, etc
    //
    // uma instancia de JanelaSimulacao, contem essa mesma instancia de Estacionamento, manipulada neste metodo
    // logo, todos os veiculos presentes na instancia de Estacionamento, podem ser acessados e devidamente renderizados
    // na instancia de JanelaSimulacao
    public void iniciarSimulacao() {
        Queue<Veiculo> veiculosFora = new LinkedList<>(); // veiculos do lado de fora do estacionamento
        Map<Veiculo, Instant> veiculosDentro = new LinkedHashMap<>(); // veiculos do lado de dentro do estacionamento
        Random random = new Random();
        
        while (true) {
            // podem existir no maximo 10 veiculos do lado de fora do estacionamento
            while (veiculosFora.size() < 10) {
                Veiculo veiculo = null;
                String placa = String.valueOf(random.nextInt(3000, 10000));

                // as chances de "spawnar" um carro e uma moto sao iguais
                if (random.nextBoolean()) {
                    veiculo = new Carro(placa, new Localizacao(100, 100), carregarCaminho("data/caminho-ate-entrada.txt"), 4);
                } else {
                    veiculo = new Moto(placa, new Localizacao(100, 100), carregarCaminho("data/caminho-ate-entrada.txt"), 2);
                }

                if (veiculo != null) {
                    veiculosFora.add(veiculo);

                    // adiciona o veiculo ao fluxo do estacionamento
                    // isso eh necessario, pois janelaSimulacao ira renderizar os veiculos
                    // coletados do estacionamento
                    // portanto, todos os veiculos do jogo precisam estar la para serem renderizados
                    estacionamento.adicionarVeiculoCirculando(veiculo); 
                }
            }

            // fazendo os veiculos que estao do lado de fora dar 1 passo cada um 
            // em direcao a entrada do estacionamento
            for (Veiculo v : veiculosFora) {
                if (v.getProximaLocalizacao() != null) {
                    // se um veiculo ainda nao chegou ate a entrada, antes de fazer ele executar 1 passo
                    // verifica-se se ele pode andar.
                    //
                    // se houver um veiculo bem a sua frente, ele ainda nao pode andar
                    boolean existeVeiculoAFrente = false;
                    List<Localizacao> trechoAFrente = v.getTrechoAFrente();

                    for (Localizacao l : trechoAFrente) {
                        if (estacionamento.existeVeiculoNaPosicao(l.getY(), l.getX())) {
                            existeVeiculoAFrente = true;
                        }
                    }

                    if (!existeVeiculoAFrente)
                        v.executarAcao();
                } 
            }

            Queue<Veiculo> veiculosParadosNaEntrada = new LinkedList<>();

            // apos todos os veiculos darem um passo em direcao a entrada
            // alguma hora um veiculo ira chegar ate a entrada
            //
            // aqui temos uma lista, porem, somente um veiculo por vez ira chegar ate a entrada,
            // pois dois veiculos nao podem ocupar o mesmo espaco
            for (Veiculo v : veiculosFora) {
                if (v.getProximaLocalizacao() == null) {
                    veiculosParadosNaEntrada.add(v);
                }
            }
            
            // nesta etapa, procura-se uma vaga para os veiculos parados na entrada do estacionamento
            while (estacionamento.existeVagaDisponivel() && veiculosParadosNaEntrada.size() > 0) {
                Veiculo veiculo = veiculosParadosNaEntrada.poll();

                // se o veiculo parado na entrada for um carro
                // busca-se uma vaga disponivel dentro do range 0 a 19
                // este sao os numeros das vagas do carros ou veiculos com mais de 4 rodas
                if (veiculo.getClass().getName().equals("veiculos.Carro")
                    && estacionamento.getVagaDisponivel(0, 19) != -1) {

                    int vagaDisponivel = estacionamento.getVagaDisponivel(0, 19);

                    // cada vaga esta linkada a um arquivo contendo o caminho ate a vaga
                    // o veiculo, que possuia o caminho ate a entrada anteriormente
                    // agora eh atualizado com o novo caminho ate a vaga
                    veiculo.setCaminho(carregarCaminho("data/vaga-estacionamento-" + vagaDisponivel + "-caminho.txt"));

                    // este horario passado como parametro para este map, eh o horario em que o veiculo
                    // podera sair da vaga
                    //
                    // OBS: quando o metodo da classe Estacionamento "estacionarVeiculo" eh chamado,
                    // ainda leva alguns ciclos de passos para que o veiculo definitiamente chegue a vaga
                    //
                    // logo, mesmo que o veiculo chegue a vaga, se o horario atual ainda nao eh maior
                    // que o horario definido aqui, o veiculo nao pode sair (desestacionar)
                    veiculosDentro.put(veiculo, Instant.now().plusSeconds(random.nextInt(25, 40)));
                    estacionamento.estacionarVeiculo(veiculo, vagaDisponivel);
                    veiculosFora.remove(veiculo);
                }

                // se o veiculo parado na entrada for uma moto
                // busca-se uma vaga disponivel dentro do range 20 a 31
                // este sao os numeros das vagas das motos
                if (veiculo.getClass().getName().equals("veiculos.Moto")
                    && estacionamento.getVagaDisponivel(20, 31) != -1) {

                    int vagaDisponivel = estacionamento.getVagaDisponivel(20, 31);

                    veiculo.setCaminho(carregarCaminho("data/vaga-estacionamento-" + vagaDisponivel + "-caminho.txt"));
                    
                    veiculosDentro.put(veiculo, Instant.now().plusSeconds(random.nextInt(2, 10)));
                    estacionamento.estacionarVeiculo(veiculo, vagaDisponivel);
                    veiculosFora.remove(veiculo);
                }
            }

            // apos executarmos um passo nos veiculos que estavam indo para a entrada
            // precisamos tambem, executar um passo nos veiculos que esta indo ate a vaga de estacionamento
            //
            // eh assim que o fluxo funciona, 
            // a cada iteracao do while(true), os veiculos executam 1 passo
            List<Veiculo> veiculosParaRemover = new ArrayList<>();

            // veiculosParaRemover ira armazenar os veiculos que foram desestacionados
            // ao desestacionar, um veiculo, paga o ticket e desaparece completamente do sistema
            // nao existe animacao para saida de veiculos do estacionamento 
            for (Veiculo v : veiculosDentro.keySet()) {
                if (v.getProximaLocalizacao() != null) {
                    boolean existeVeiculoAFrente = false;
                    List<Localizacao> trechoAFrente = v.getTrechoAFrente();

                    for (Localizacao l : trechoAFrente) {
                        if (estacionamento.existeVeiculoNaPosicao(l.getY(), l.getX())) {
                            existeVeiculoAFrente = true;
                        }
                    }

                    if (!existeVeiculoAFrente)
                        v.executarAcao();
                } else {
                    // se v.getProximaLocalizacao() == null
                    // significa que o veiculo em questao chegou ate a vaga de estacionamento
                    // logo, o proximo passo eh verificar se o tempo de estacionamento
                    // ja passou antes de desestaciona-lo
                    if (veiculosDentro.get(v).compareTo(Instant.now()) < 0) {
                        estacionamento.desestacionarVeiculo(v);
                        estacionamento.removerVeiculoCirculando(v);
                        janelaSimulacao.atualizarFaturamento(estacionamento.getFaturamento());
                        veiculosParaRemover.add(v);
                    }
                }
            }

            for (Veiculo v : veiculosParaRemover) {
                veiculosDentro.remove(v);
            }

            // a cada iteracao do while(true)
            // a janelaSimulacao eh atualizada de acordo com a List<Veiculo> veiculosCirculando
            // presente na classe Estacionamento
            janelaSimulacao.atualizarJanelaSimulacao();

            // tempo entre cada atualizacao da imagem
            // 60 -> intervalo de 60 milisegundos entre cada atualizacao da imagem
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // carrega as coordenadas do arquivo recebido como parametro em uma fila (caminho a ser percorrido pelo veiculo)
    public Queue<Localizacao> carregarCaminho(String caminhoArquivo) {
        Queue<Localizacao> caminho = new LinkedList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            String[] coordenadas;

            // lendo o restante do caminho ate a vaga de estacionamento
            while ((linha = leitor.readLine()) != null) {
                coordenadas = linha.split(" ");

                if (coordenadas.length == 2) {
                    coordenadas = coordenadas[1].split("\"")[1].split(";");
                    int x = Integer.parseInt(coordenadas[0]);
                    int y = Integer.parseInt(coordenadas[1]);
                    caminho.add(new Localizacao(y, x));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return caminho;
    }
}