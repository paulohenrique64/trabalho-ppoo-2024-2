package simulacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mapa.Estacionamento;
import util.Localizacao;
import veiculos.Carro;
import veiculos.Veiculo;

public class Simulacao {
    private JanelaSimulacao janelaSimulacao; // interface grafica
    private Estacionamento estacionamento; // logica do sistema

    public Simulacao() {
        estacionamento = new Estacionamento();
        janelaSimulacao = new JanelaSimulacao(estacionamento);
    }

    public void iniciarSimulacao() {
        Veiculo veiculo = new Carro(new Localizacao(10, 10), 4);
        estacionamento.estacionarVeiculo(veiculo);

         try (BufferedReader leitor = new BufferedReader(new FileReader("data/vaga-estacionamento-1-caminho.txt"))) {
            String linha;
            int x;
            int y;

            while ((linha = leitor.readLine()) != null) {
                String[] coordenasString = linha.split(" ");
                coordenasString = coordenasString[1].split("\"");
                coordenasString = coordenasString[1].split(";");


                // System.out.println(coordenasString[0] + " " + coordenasString[1]);

                x = Integer.parseInt(coordenasString[0]);
                y = Integer.parseInt(coordenasString[1]);

                System.out.println(x + " " + y);

                estacionamento.removerItem(veiculo);
                veiculo.setLocalizacaoAtual(new Localizacao(x, y));
                estacionamento.adicionarItem(veiculo);

                janelaSimulacao.atualizarJanelaSimulacao();
                // estacionamento.atualizarEstacionamento(veiculo);
                // janelaSimulacao.atualizarJanelaSimulacao();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        

        // inicio do algortimo dijkstra enquanto atualiza o carro no mapa indo ate a vaga



        // fim do algoritmo dijkstra quando o carro chega na vaga e espera por um tempo aleatorio
        



        // apos o tempo aleatorio o carro desaparece do mapa e deixa a vaga disponivel para outro carro
        estacionamento.desestacionarVeiculo(veiculo);
    }
}
