package util;

import java.time.LocalDateTime;

import veiculos.Veiculo;

/**
 * Representa a entidate Ticket, utilizada para armazenar e manipular
 * dados referentes a entrada e saída de veículos de estacionamentos
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
        return horaChegada.getSecond() / 7.0 * veiculo.getEspacoOcupado().getX() * veiculo.calcularTaxaDeDanificacao();
    }

    public String getPlacaVeiculo() {
        return veiculo.getPlaca();
    }
}
