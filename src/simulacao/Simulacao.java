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

    public List<Queue<Localizacao>> carregarCaminhos() {
        List<Queue<Localizacao>> caminhos = new LinkedList<>();

        for (int i = 0; i < 32; i++) {
            String arquivo = "data/vaga-estacionamento-" + i + "-caminho.txt";
            Queue<Localizacao> caminho = new LinkedList<>();

            try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
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

            caminhos.add(caminho);
        }

        return caminhos;
    }

    public void iniciarSimulacao() {
        Map<Veiculo, Instant> veiculos = new LinkedHashMap<>();
        Map<Veiculo, Instant> mapTempoNoEstacionamento = new LinkedHashMap<>(); 
        Random random = new Random();
        

        while (true) {
            List<Veiculo> veiculosParaRemover = new ArrayList<>();

            // enquanto existir vaga no estacionamento, serao criados novos veiculos que irao em direcao a essas vagas
            while (estacionamento.existeVagaDisponivel()) {
                List<Queue<Localizacao>> caminhos = carregarCaminhos();

                // se houver vagas para um carro no estacionamento
                // buscando vagas entre o range 0 e 19 (vagas para veiculos com mais de 4 rodas)
                if (estacionamento.getVagaDisponivel(0, 19) != -1) {
                    int vagaDisponivel = estacionamento.getVagaDisponivel(0, 19);
                    String placa = String.valueOf(random.nextInt(3000, 10000));
                    Veiculo veiculo = new Carro(placa, new Localizacao(100, 100), caminhos.get(vagaDisponivel), 4);
                    estacionamento.estacionarVeiculo(veiculo, vagaDisponivel);
                    veiculos.put(veiculo, Instant.now().plusSeconds(veiculos.size() * 2));
                } 
                

                // se houver vagas para uma moto no estacionamento
                // buscando vagas entre o range 20 e 31 (vagas para veiculos com menos de 4 rodas)
                if (estacionamento.getVagaDisponivel(20, 31) != -1) {
                    int vagaDisponivel = estacionamento.getVagaDisponivel(20, 31);
                    String placa = String.valueOf(random.nextInt(3000, 10000));
                    Veiculo veiculo = new Moto(placa, new Localizacao(100, 100), caminhos.get(vagaDisponivel), 2);
                    estacionamento.estacionarVeiculo(veiculo, vagaDisponivel);
                    veiculos.put(veiculo, Instant.now().plusSeconds(veiculos.size()));
                } 
            }

            boolean novaVagaLiberada = false;

            // enquanto nenhuma vaga for liberada para entrada de um novo veiculo
            // todos os veiculos irao caminhar em direcao a suas respectivas vagas de estacionamento
            while (!novaVagaLiberada) {
                for (Veiculo v : veiculos.keySet()) {
                    // se o veiculo ainda nao chegou a vaga
                    if (v.getProximaLocalizacao() != null && veiculos.get(v).compareTo(Instant.now()) < 0) {
                        v.executarAcao();
                    } 

                    // se o veiculo chegou na vaga, eh marcado um tempo para que o veiculo permaneca na vaga
                    if (v.getProximaLocalizacao() == null && !mapTempoNoEstacionamento.containsKey(v)) {
                        int tempoNoEstacionamento = random.nextInt(10, 25);
                        mapTempoNoEstacionamento.put(v, Instant.now().plusSeconds(tempoNoEstacionamento));
                    }

                    // verificar se os veiculos estacionados ja podem ir embora
                    if (mapTempoNoEstacionamento.get(v) != null && mapTempoNoEstacionamento.get(v).compareTo(Instant.now()) < 0) {
                        estacionamento.desestacionarVeiculo(v);
                        veiculosParaRemover.add(v); // armazenar o veiculo para remove-lo do sistema posteriormente
                        novaVagaLiberada = true;
                        mapTempoNoEstacionamento.remove(v);
                        janelaSimulacao.atualizarFaturamento(estacionamento.getFaturamento());
                    }
                }

                janelaSimulacao.atualizarJanelaSimulacao();

                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (Veiculo v : veiculosParaRemover) {
                veiculos.remove(v);
            }
        }
    }
}