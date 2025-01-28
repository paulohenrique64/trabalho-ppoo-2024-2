package util;

import java.time.Duration;
import java.time.LocalDateTime;

import veiculos.Veiculo;

/**
 * Representa a entidate Ticket, utilizada para armazenar e manipular
 * dados referentes a entrada e saída de veículos de estacionamentos
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Ticket {
    private Veiculo veiculo;
    private LocalDateTime horaChegada;

    public Ticket(Veiculo veiculo) {
        this.veiculo = veiculo;
        horaChegada = LocalDateTime.now();
    }

    public Double getCustoEstacionamento() {
        // calcula o tempo de permanencia em segundos.
        LocalDateTime agora = LocalDateTime.now();
        Duration duracao = Duration.between(horaChegada, agora);
        long segundosNoEstacionamento = duracao.getSeconds();

        // calcula o custo do estacionamento.
        double custo = (segundosNoEstacionamento / 5.0)
                * veiculo.getEspacoOcupado().getX()
                * veiculo.calcularTaxaDanificacaoTerreno();

        // System.out.println("Tempo no estacionamento -> " + segundosNoEstacionamento +
        // " segundos");
        return custo;
    }

    public String getPlacaVeiculo() {
        return veiculo.getPlaca();
    }
}
