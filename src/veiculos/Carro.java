package veiculos;

import java.util.Queue;

import utilitarios.ImagensVeiculo;
import utilitarios.Localizacao;

/**
 * Representa a entidate Carro 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Carro extends Veiculo {
    private int cavalosDePotencia;
    
    public Carro(String placa, Localizacao localizacao, Queue<Localizacao> caminho, int quantidadeRodas, int cavalosDePotencia, ImagensVeiculo imagensVeiculo) {
        super(placa, quantidadeRodas, localizacao, caminho, imagensVeiculo);
        this.cavalosDePotencia = cavalosDePotencia;
    }

    @Override
    public Localizacao getEspacoOcupado() {
        return new Localizacao(8, 7);
    }

    @Override
    public Double calcularTaxaDanificacaoTerreno() {
        return super.calcularTaxaDanificacaoTerreno() * (cavalosDePotencia / 100);
    }
}
