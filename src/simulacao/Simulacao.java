package simulacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.swing.JLabel;

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

  public List<Queue<Localizacao>> carregarCaminhosCarro() {
    List<Queue<Localizacao>> caminhos = new LinkedList<>();

    for (int i = 0; i < 14; i++) {
      Queue<Localizacao> caminho = new LinkedList<>();
      String arquivo = "data/dataCarro/vaga-estacionamento-" + (i + 1) + "-caminho.txt";

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

  public List<Queue<Localizacao>> carregarCaminhosMoto() {
    List<Queue<Localizacao>> caminhos = new LinkedList<>();
    for (int i = 0; i < 6; i++) {
      Queue<Localizacao> caminho = new LinkedList<>();
      String arquivo = "data/dataMoto/vaga-estacionamento-" + (i + 1) + "-caminho.txt";

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
    Random random = new Random();

    while (true) {
      while (estacionamento.existeVagaDisponivel()) {
        List<Queue<Localizacao>> caminhosCarro = carregarCaminhosCarro();
        List<Queue<Localizacao>> caminhosMoto = carregarCaminhosMoto();

        int vagaDisponivel = estacionamento.getVagaDisponivel();
        Veiculo veiculo;

        if (random.nextBoolean() && vagaDisponivel < 14) {
          veiculo = new Carro(new Localizacao(10, 10), caminhosCarro.get(vagaDisponivel), 4);
        } else if (vagaDisponivel < 6) {
          veiculo = new Moto(new Localizacao(10, 10), caminhosMoto.get(vagaDisponivel), 2);
        } else {
          continue;
        }

        estacionamento.estacionarVeiculo(veiculo, vagaDisponivel);
        veiculos.add(veiculo);
      }

      boolean algumVeiculoChegouNaVaga = false;
      List<Veiculo> veiculosParaRemover = new ArrayList<>();

      while (!algumVeiculoChegouNaVaga) {
        for (Veiculo v : veiculos) {
          if(v.getProximaLocalizacao() != null){
          if (estacionamento.getVeiculoNaPosicao(v.getProximaLocalizacao().getY()+3, v.getProximaLocalizacao().getX()+3).size() == 0 && !v.executarAcao()) {
            janelaSimulacao.atualizarFaturamento(estacionamento.getFaturamento());
            estacionamento.desestacionarVeiculo(v);
            algumVeiculoChegouNaVaga = true;
            veiculosParaRemover.add(v);
          }
        }
        }
        janelaSimulacao.atualizarJanelaSimulacao();

        try {
          Thread.sleep(50);
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