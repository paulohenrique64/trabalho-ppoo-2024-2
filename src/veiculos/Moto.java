package veiculos;

import java.util.Queue;

import utilitarios.ImagensVeiculo;
import utilitarios.Localizacao;

/**
 * Representa uma moto no sistema, que é uma subclasse de Veiculo.
 * 
 * A moto tem um atributo adicional chamado 'cilindradas', que representa
 * o tamanho do motor da moto em centímetros cúbicos. A classe redefine o
 * método {@link Veiculo#getEspacoOcupado()} para especificar o espaço
 * ocupado pela moto no ambiente e também redefine o cálculo da taxa de
 * dano ao terreno.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Moto extends Veiculo {
    private int cilindradas;

    /**
     * Constrói uma nova moto com os parâmetros fornecidos.
     * 
     * @param placa           A placa da moto.
     * @param localizacao     A localização inicial da moto.
     * @param caminho         A fila de localizações que a moto irá percorrer.
     * @param quantidadeRodas A quantidade de rodas da moto.
     * @param cilindradas     O tamanho do motor da moto, em centímetros cúbicos
     *                        (cc).
     * @param imagensVeiculo  O conjunto de imagens da moto para diferentes
     *                        direções.
     */
    public Moto(String placa, Localizacao localizacao, Queue<Localizacao> caminho, int quantidadeRodas, int cilindradas,
            ImagensVeiculo imagensVeiculo) {
        super(placa, quantidadeRodas, localizacao, caminho, imagensVeiculo);
        this.cilindradas = cilindradas;
    }

    /**
     * Retorna o espaço ocupado pela moto no ambiente, definido como uma
     * localização com coordenadas (4, 4).
     * 
     * @return A localização representando o espaço ocupado pela moto.
     */
    @Override
    public Localizacao getEspacoOcupado() {
        return new Localizacao(4, 4);
    }

    /**
     * Calcula a taxa de dano que a moto causaria ao terreno, baseada no
     * tamanho do motor (cilindradas). A taxa de dano é ajustada com base
     * nas cilindradas da moto.
     * 
     * @return A taxa de dano do terreno causada pela moto.
     */
    @Override
    public Double calcularTaxaDanificacaoTerreno() {
        return super.calcularTaxaDanificacaoTerreno() * (cilindradas / 100);
    }
}