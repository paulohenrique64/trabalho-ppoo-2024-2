package imagens;

import javax.swing.ImageIcon;

import util.Direcao;

public class ImagensMotoVermelha extends ImagensVeiculo {
    public ImagensMotoVermelha() {
        setImagem(Direcao.BAIXO, new ImageIcon("src/imagens/moto/baixo.png").getImage());
        setImagem(Direcao.CIMA, new ImageIcon("src/imagens/moto/cima.png").getImage());
        setImagem(Direcao.DIREITA, new ImageIcon("src/imagens/moto/direita.png").getImage());
        setImagem(Direcao.ESQUERDA, new ImageIcon("src/imagens/moto/esquerda.png").getImage());

        setImagem(Direcao.DIAGONAL_DIREITA_BAIXO, new ImageIcon("src/imagens/moto/diagonal-direita-baixo.png").getImage());
        setImagem(Direcao.DIAGONAL_DIREITA_CIMA, new ImageIcon("src/imagens/moto/diagonal-direita-cima.png").getImage());
        setImagem(Direcao.DIAGONAL_ESQUERDA_BAIXO, new ImageIcon("src/imagens/moto/diagonal-esquerda-baixo.png").getImage());
        setImagem(Direcao.DIAGONAL_ESQUERDA_CIMA, new ImageIcon("src/imagens/moto/diagonal-esquerda-cima.png").getImage());
    }
}
