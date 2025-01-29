package utilitarios;

import java.awt.Image;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe abstrata que gerencia imagens de um veículo em diferentes direções.
 * Cada veículo pode ter uma imagem associada a cada posição definida na
 * enumeração {@link Direcao}.
 * 
 * Essa classe pode ser estendida para permitir a manipulação de imagens
 * específicas de diferentes tipos de veículos.
 * 
 * @author Paulo Henrique Ribeiro Alves
 */
public abstract class ImagensVeiculo {
    private Map<Direcao, Image> imagens; // Mapeia cada direção a uma imagem correspondente

    /**
     * Construtor da classe ImagensVeiculo.
     * Inicializa o mapa de imagens definindo todas as direções com valor nulo.
     */
    public ImagensVeiculo() {
        imagens = new LinkedHashMap<>();

        for (Direcao direcao : Direcao.values()) {
            imagens.put(direcao, null);
        }
    }

    /**
     * Define uma imagem para uma determinada direção.
     * 
     * @param direcao Direção associada à imagem.
     * @param imagem  Imagem correspondente à direção especificada.
     * @throws IllegalArgumentException Se a direção for nula.
     */
    public void setImagem(Direcao direcao, Image imagem) {
        if (direcao == null) {
            throw new IllegalArgumentException("A direção não pode ser nula.");
        }

        imagens.put(direcao, imagem);
    }

    /**
     * Obtém a imagem correspondente a uma determinada direção.
     * 
     * @param direcao Direção cuja imagem será obtida.
     * @return A imagem correspondente à direção especificada
     * @throws IllegalArgumentException Se a direção for nula.
     */
    public Image getImagem(Direcao direcao) {
        if (direcao == null) {
            throw new IllegalArgumentException("A direção não pode ser nula.");
        }

        return imagens.get(direcao);
    }
}