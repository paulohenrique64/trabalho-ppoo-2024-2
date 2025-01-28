package veiculos;

import java.util.Queue;

import utilitarios.ImagensVeiculo;
import utilitarios.Localizacao;

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
    public Double calcularTaxaDanificacaoTerreno() {
        return super.calcularTaxaDanificacaoTerreno() * (cilindradas / 100);
    }
}