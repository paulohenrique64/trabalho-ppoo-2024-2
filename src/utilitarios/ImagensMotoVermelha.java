package utilitarios;

import javax.swing.ImageIcon;

public class ImagensMotoVermelha extends ImagensVeiculo {
    public ImagensMotoVermelha() {
        setImagem(Direcao.BAIXO, new ImageIcon("data/imagens/moto/baixo.png").getImage());
        setImagem(Direcao.CIMA, new ImageIcon("data/imagens/moto/cima.png").getImage());
        setImagem(Direcao.DIREITA, new ImageIcon("data/imagens/moto/direita.png").getImage());
        setImagem(Direcao.ESQUERDA, new ImageIcon("data/imagens/moto/esquerda.png").getImage());

        setImagem(Direcao.DIAGONAL_DIREITA_BAIXO, new ImageIcon("data/imagens/moto/diagonal-direita-baixo.png").getImage());
        setImagem(Direcao.DIAGONAL_DIREITA_CIMA, new ImageIcon("data/imagens/moto/diagonal-direita-cima.png").getImage());
        setImagem(Direcao.DIAGONAL_ESQUERDA_BAIXO, new ImageIcon("data/imagens/moto/diagonal-esquerda-baixo.png").getImage());
        setImagem(Direcao.DIAGONAL_ESQUERDA_CIMA, new ImageIcon("data/imagens/moto/diagonal-esquerda-cima.png").getImage());
    }
}
