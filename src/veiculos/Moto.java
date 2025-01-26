package veiculos;

import java.util.Queue;
import java.util.Stack;

import javax.swing.ImageIcon;

import util.Localizacao;

public class Moto extends Veiculo {
    private int quantidadeRodas;

    public Moto(Localizacao localizacao, Queue<Localizacao> caminho, int quantidadeRodas) {
        super(localizacao, caminho, new ImageIcon("src/Imagens/moto.png").getImage());
        this.quantidadeRodas = quantidadeRodas;
    }

    public int getQuantidadeRodas() {
        return quantidadeRodas;
    }

    @Override
    public Localizacao espacoOcupado() {
        return new Localizacao(3,4);
    }

    @Override
    public Double getValorEstacionamento() {
        return 7.5;
    }
}