package veiculos;

import java.util.Queue;

import util.Localizacao;

/**
 * Representa a entidate Carro 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Carro extends Veiculo {
    private int quantidadePortas;

    public Carro(String placa, Localizacao localizacao, Queue<Localizacao> caminho, int quantidadePortas) {
        super(placa, localizacao, caminho);
        this.quantidadePortas = quantidadePortas;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    @Override
    public Localizacao getEspacoOcupado() {
        return new Localizacao(8, 7);
    }
}
