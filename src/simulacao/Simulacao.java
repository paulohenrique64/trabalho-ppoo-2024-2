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
import veiculos.Moto;
import veiculos.Veiculo;

public class Simulacao {
    private JanelaSimulacao janelaSimulacao; // interface grafica
    private Estacionamento estacionamento; // logica do sistema

    public Simulacao() {
        estacionamento = new Estacionamento();
        janelaSimulacao = new JanelaSimulacao(estacionamento);
    }

    public void iniciarSimulacao() {
        List<Veiculo> veiculosRodando = new ArrayList<>();
        veiculosRodando.add(new Moto(new Localizacao(10, 10), 4));
        veiculosRodando.add(new Carro(new Localizacao(10, 10), 4));
        
       

        for (int i = 0; i < veiculosRodando.size(); i++) {
            final int index = i;

            
            Thread teste = new Thread(new Runnable() {
               
                @Override
                public void run() {
                    System.out.println(" thread do " + veiculosRodando.get(index).getClass().getName());
                    estacionamento.estacionarVeiculo(veiculosRodando.get(index));

                    try (BufferedReader leitor = new BufferedReader(
                            new FileReader("data/vaga-estacionamento-"+(index + 1)+"-caminho.txt"))) {
                        String linha;
                        int x;
                        int y;

                        while ((linha = leitor.readLine()) != null) {
                            String[] coordenasString = linha.split(" ");
                            coordenasString = coordenasString[1].split("\"");
                            coordenasString = coordenasString[1].split(";");

                            x = Integer.parseInt(coordenasString[0]);
                            y = Integer.parseInt(coordenasString[1]);

                            // System.out.println("colocando a " + veiculosRodando.get(index).getClass().getName() + " na coordenada " + x + " " + y);

                            // System.out.println(x + " " + y);

                            estacionamento.removerItem(veiculosRodando.get(index));
                            veiculosRodando.get(index).setLocalizacaoAtual(new Localizacao(y, x));
                            estacionamento.adicionarItem(veiculosRodando.get(index));

                            janelaSimulacao.atualizarJanelaSimulacao();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                    }

                    // após o tempo aleatório o carro desaparece do mapa e deixa a vaga disponível
                    estacionamento.desestacionarVeiculo(veiculosRodando.get(index));
                }
            });

            // Agora você deve iniciar o thread
            teste.start();
        }
    }
}
