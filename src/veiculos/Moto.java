package veiculos;

import javax.swing.ImageIcon;

import util.Localizacao;

public class Moto extends Veiculo {
    private int quantidadeRodas;

    public Moto(Localizacao localizacao, int quantidadeRodas) {
        super(localizacao, new ImageIcon("src/Imagens/moto.png").getImage());
        this.quantidadeRodas = quantidadeRodas;
    }

    public int getQuantidadeRodas() {
        return quantidadeRodas;
    }

    @Override
    public Localizacao espacoOcupado() {
        return new Localizacao(3,4);
    }
}