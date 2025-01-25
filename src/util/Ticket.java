package util;

import java.time.LocalDateTime;

import veiculos.Veiculo;

public class Ticket {
    private Veiculo veiculo;
    private LocalDateTime horaChegada;
    private Double valorTotal;

    public Ticket(Veiculo veiculo) {
        this.veiculo = veiculo;
        horaChegada = LocalDateTime.now();
        valorTotal = 0D;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDateTime getHoraChegada() {
        return horaChegada;
    }

    public Double calcularValorGasto() {
        return 10D;
    }
}
