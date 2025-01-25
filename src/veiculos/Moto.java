package veiculos;

import util.Localizacao;

public class Moto extends Veiculo {
    private int quantidadeRodas;

    public Moto(Localizacao localizacao, int quantidadeRodas) {
        super(localizacao);
        this.quantidadeRodas = quantidadeRodas;
    }

    public int getQuantidadeRodas() {
        return quantidadeRodas;
    }
}