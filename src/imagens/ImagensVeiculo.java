package imagens;

import java.awt.Image;
import java.util.LinkedHashMap;
import java.util.Map;

import util.Direcao;

public abstract class ImagensVeiculo {
    private Map<Direcao, Image> imagens;

    public ImagensVeiculo() {
        imagens = new LinkedHashMap<>();

        for (Direcao direcao : Direcao.values()) {
            imagens.put(direcao, null);
        }
    }

    public void setImagem(Direcao direcao, Image imagem) {
        if (direcao == null) {
            throw new IllegalArgumentException("A posição não pode ser nula.");
        }
        imagens.put(direcao, imagem);
    }

    public Image getImagem(Direcao direcao) {
        if (direcao == null) {
            throw new IllegalArgumentException("A posição não pode ser nula.");
        }
        return imagens.getOrDefault(direcao, null);
    }
}