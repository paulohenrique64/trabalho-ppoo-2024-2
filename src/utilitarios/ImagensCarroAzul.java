package utilitarios;

import javax.swing.ImageIcon;

public class ImagensCarroAzul extends ImagensVeiculo {
    public ImagensCarroAzul() {
        setImagem(Direcao.BAIXO, new ImageIcon("data/imagens/carro/baixo.png").getImage());
        setImagem(Direcao.CIMA, new ImageIcon("data/imagens/carro/cima.png").getImage());
        setImagem(Direcao.DIREITA, new ImageIcon("data/imagens/carro/direita.png").getImage());
        setImagem(Direcao.ESQUERDA, new ImageIcon("data/imagens/carro/esquerda.png").getImage());

        setImagem(Direcao.DIAGONAL_DIREITA_BAIXO, new ImageIcon("data/imagens/carro/diagonal-direita-baixo.png").getImage());
        setImagem(Direcao.DIAGONAL_DIREITA_CIMA, new ImageIcon("data/imagens/carro/diagonal-direita-cima.png").getImage());
        setImagem(Direcao.DIAGONAL_ESQUERDA_BAIXO, new ImageIcon("data/imagens/carro/diagonal-esquerda-baixo.png").getImage());
        setImagem(Direcao.DIAGONAL_ESQUERDA_CIMA, new ImageIcon("data/imagens/carro/diagonal-esquerda-cima.png").getImage());
    }
}
