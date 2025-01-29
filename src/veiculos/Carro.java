package veiculos;

import java.util.Queue;

import utilitarios.ImagensVeiculo;
import utilitarios.Localizacao;

/**
 * Representa um carro no sistema, que é uma subclasse de Veiculo.
 * 
 * O carro tem um atributo adicional chamado 'cavalosDePotencia', que representa
 * a potência do motor do carro em cavalos de potência.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Carro extends Veiculo {
    private int cavalosDePotencia;

    /**
     * Constrói um novo carro com os parâmetros fornecidos.
     * 
     * @param placa             A placa do carro.
     * @param quantidadeRodas   A quantidade de rodas do carro.
     * @param cavalosDePotencia A potência do motor do carro, em cavalos de potência
     *                          (cv).
     * @param imagensVeiculo    O conjunto de imagens do carro para diferentes
     *                          direções.
     */
    public Carro(String placa, int quantidadeRodas, int cavalosDePotencia, ImagensVeiculo imagensVeiculo) {
        super(placa, quantidadeRodas, imagensVeiculo);
        this.cavalosDePotencia = cavalosDePotencia;
    }

    /**
     * Constrói um novo carro com os parâmetros fornecidos.
     * 
     * @param placa             A placa do carro.
     * @param localizacao       A localização inicial do carro.
     * @param caminho           A fila de localizações que o carro irá percorrer.
     * @param quantidadeRodas   A quantidade de rodas do carro.
     * @param cavalosDePotencia A potência do motor do carro, em cavalos de potência
     *                          (cv).
     * @param imagensVeiculo    O conjunto de imagens do carro para diferentes
     *                          direções.
     */
    public Carro(String placa, Localizacao localizacao, Queue<Localizacao> caminho, int quantidadeRodas, int cavalosDePotencia, ImagensVeiculo imagensVeiculo) {
        super(placa, quantidadeRodas, localizacao, caminho, imagensVeiculo);
        this.cavalosDePotencia = cavalosDePotencia;
    }

    /**
     * Retorna o espaço ocupado pelo carro no ambiente, definido como uma
     * localização com coordenadas (8, 7).
     * 
     * @return A localização representando o espaço ocupado pelo carro.
     */
    @Override
    public Localizacao getEspacoOcupado() {
        return new Localizacao(8, 7);
    }

    /**
     * Calcula a taxa de dano que o carro causaria ao terreno, baseada na
     * potência do motor (cavalos de potência).
     * 
     * @return A taxa de dano do terreno causada pelo carro.
     */
    @Override
    public double calcularTaxaDanificacaoTerreno() {
        return super.calcularTaxaDanificacaoTerreno() * (cavalosDePotencia / 100);
    }
}
