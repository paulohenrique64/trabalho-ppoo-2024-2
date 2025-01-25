package simulacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void iniciarSimulacao() {
        List<Veiculo> veiculos = new ArrayList<>();
        veiculos.add(new Moto(new Localizacao(10, 10), 4));
        veiculos.add(new Moto(new Localizacao(10, 10), 4));
        veiculos.add(new Carro(new Localizacao(10, 10), 4));
        veiculos.add(new Carro(new Localizacao(10, 10), 4));

        for (int i = 0; i < veiculos.size(); i++) {
            final int numeroVeiculo = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    moverVeiculo(veiculos.get(numeroVeiculo), numeroVeiculo + 1);
                }
            });
            thread.start();
        }
    }

    private void moverVeiculo(Veiculo veiculo, int numero) {
        estacionamento.estacionarVeiculo(veiculo);
        String arquivo = "data/vaga-estacionamento-" + numero + "-caminho.txt";

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                moverParaNovaPosicao(veiculo, linha);
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        estacionamento.desestacionarVeiculo(veiculo);
    }

    private void moverParaNovaPosicao(Veiculo veiculo, String linha) {
        String[] coordenadas = linha.split(" ")[1].split("\"")[1].split(";");
        int x = Integer.parseInt(coordenadas[0]);
        int y = Integer.parseInt(coordenadas[1]);

        synchronized (estacionamento) {
            estacionamento.removerItem(veiculo);
            veiculo.setLocalizacaoAtual(new Localizacao(y, x));
            estacionamento.adicionarItem(veiculo);
            janelaSimulacao.atualizarJanelaSimulacao();
        }
    }
}
