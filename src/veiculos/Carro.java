package veiculos;

import java.awt.Image;
import java.util.Queue;
import java.util.Stack;

import javax.swing.ImageIcon;

import util.Localizacao;

public class Carro extends Veiculo {
    private int quantidadePortas;

    public Carro(Localizacao localizacao, Queue<Localizacao> caminho, int quantidadePortas) {
        super(localizacao, caminho, new ImageIcon("src/Imagens/carro.png").getImage());
        this.quantidadePortas = quantidadePortas;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    @Override
    public Localizacao espacoOcupado() {
        return new Localizacao(5, 4);
    }
}
