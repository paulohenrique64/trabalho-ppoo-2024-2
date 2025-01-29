package utilitarios;

import java.time.LocalDateTime;
import java.util.Random;

import javax.swing.ImageIcon;

import veiculos.Carro;
import veiculos.Moto;
import veiculos.Veiculo;

public class FabricaDeVeiculos {
    public static final ImagensVeiculo imagensVeiculoCarroAzul = carregarImagensCarroAzul();
    public static final ImagensVeiculo imagensVeiculoMotoVermelha = carregarImagensMotoVermelha();
    private static Random rand = new Random();

    /**
     * Cria e retorna um novo veículo para ser adicionado à simulação.
     * 
     * O veículo pode ser um carro ou uma moto, gerado aleatoriamente.
     * Existe uma chance de 70% de ser um carro e 30% de ser uma moto.
     * 
     * @return Um novo objeto do tipo {@link Veiculo}.
     */
    public static Veiculo getVeiculoAleatorio() {
        String placa = rand.nextInt(10000) + "-" + LocalDateTime.now().getNano();

        // Define aleatoriamente se o veículo será um carro ou uma moto
        if (rand.nextInt(10) > 2) {
            int cavalosDePotencia = rand.nextInt(170) + 50;
            return new Carro(placa, 4, cavalosDePotencia, imagensVeiculoCarroAzul);
        }

        int cilindradas = rand.nextInt(200) + 50;
        return new Moto(placa, 2, cilindradas, imagensVeiculoMotoVermelha);
    }

    private static ImagensVeiculo carregarImagensCarroAzul() {
        ImagensVeiculo imagensVeiculo = new ImagensVeiculo();

        for (Direcao d : Direcao.values())
            imagensVeiculo.setImagem(d, new ImageIcon(String.format("data/imagens/carro/%s.png", d.toString().toLowerCase())).getImage());

        return imagensVeiculo;
    }

    private static ImagensVeiculo carregarImagensMotoVermelha() {
        ImagensVeiculo imagensVeiculo = new ImagensVeiculo();

        for (Direcao d : Direcao.values())
            imagensVeiculo.setImagem(d, new ImageIcon(String.format("data/imagens/moto/%s.png", d.toString().toLowerCase())).getImage());

        return imagensVeiculo;
    }
}
