package veiculos;

import java.util.Queue;

import util.Localizacao;

/**
 * Representa a entidate Moto
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Moto extends Veiculo {
    private int quantidadeRodas;

    public Moto(String placa, Localizacao localizacao, Queue<Localizacao> caminho, int quantidadeRodas) {
        super(placa, localizacao, caminho);
        this.quantidadeRodas = quantidadeRodas;
    }

    public int getQuantidadeRodas() {
        return quantidadeRodas;
    }

    @Override
    public Localizacao getEspacoOcupado() {
        return new Localizacao(4,4);
    }
}