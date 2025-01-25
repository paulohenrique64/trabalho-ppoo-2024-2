package veiculos;

import util.Localizacao;

public class Carro extends Veiculo {
    private int quantidadePortas;

    public Carro(Localizacao localizacao, int quantidadePortas) {
        super(localizacao);
        this.quantidadePortas = quantidadePortas;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }
}
