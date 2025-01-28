package veiculos;

import java.util.Queue;

import imagens.ImagensVeiculo;
import util.Localizacao;
import util.StatusGPSVeiculo;

/**
 * Representa a entidate Moto
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String placa, Localizacao localizacao, Queue<Localizacao> caminho, int quantidadeRodas, int cilindradas, ImagensVeiculo imagensVeiculo) {
        super(placa, quantidadeRodas, localizacao, caminho, imagensVeiculo);
        this.cilindradas = cilindradas;
    }

    @Override
    public Localizacao getEspacoOcupado() {
        return new Localizacao(4,4);
    }

    @Override
    public Double calcularTaxaDeDanificacao() {
        return super.calcularTaxaDeDanificacao() * (cilindradas / 100);
    }
}