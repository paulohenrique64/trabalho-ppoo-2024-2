package simulacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import mapa.Estacionamento;
import util.Localizacao;
import veiculos.Carro;
import veiculos.Moto;
import veiculos.Veiculo;

public class Simulacao {
    private JanelaSimulacao janelaSimulacao;
    private Estacionamento estacionamento;

    public Simulacao() {
        estacionamento = new Estacionamento();
        janelaSimulacao = new JanelaSimulacao(estacionamento);
    }

    public List<Queue<Localizacao>> carregarCaminhos() {
        List<Queue<Localizacao>> caminhos = new LinkedList<>();

        for (int i = 0; i < 14; i++) {
            Queue<Localizacao> caminho = new LinkedList<>();
            String arquivo = "data/vaga-estacionamento-" + (i + 1) + "-caminho.txt";

            try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                String linha;

                while ((linha = leitor.readLine()) != null) {
                    String[] coordenadas = linha.split(" ")[1].split("\"")[1].split(";");
                    int x = Integer.parseInt(coordenadas[0]);
                    int y = Integer.parseInt(coordenadas[1]);

                    caminho.add(new Localizacao(y, x));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            caminhos.add(caminho);
        }

        return caminhos;
    }

    public void iniciarSimulacao() {
        List<Veiculo> veiculos = new ArrayList<>();
        
        while (true) {
            while (estacionamento.existeVagaDisponivel()) {
                List<Queue<Localizacao>> caminhos = carregarCaminhos();

                // numero da vaga disponivel
                int vagaDisponivel = estacionamento.getVagaDisponivel();

                // criando veiculo com rota para a vaga disponivel
                Veiculo veiculo = new Carro(new Localizacao(10, 10), caminhos.get(vagaDisponivel), 4);

                // colocando o veiculo em circulacao no estacionamento
                estacionamento.estacionarVeiculo(veiculo, vagaDisponivel);
                System.out.println("este veiculo esta indo estacionar -> " + veiculo.getPlaca());
                // adicionando o veiculo na lista para controle
                veiculos.add(veiculo);
                // System.out.println(veiculo);
                // System.out.println(vagaDisponivel);
                // System.out.println(caminhos.get(vagaDisponivel));
            }

            boolean algumVeiculoChegouNaVaga = false;
            List<Veiculo> veiculosParaRemover = new ArrayList<>();

            while (!algumVeiculoChegouNaVaga) {
                for (Veiculo v : veiculos) {
                    if (!v.executarAcao()) {
                        System.out.println("este veiculo estacionoui -> " + v.getPlaca());
                        estacionamento.desestacionarVeiculo(v);
                        algumVeiculoChegouNaVaga = true;
                        veiculosParaRemover.add(v);
                    }
    
                    janelaSimulacao.atualizarJanelaSimulacao();
                }
    
                try {
                    Thread.sleep(20);
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
