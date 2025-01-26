package mapa;

import java.util.ArrayList;
import java.util.List;

import util.Ticket;
import veiculos.Veiculo;

public class Atendimento {
    private List<Ticket> ticketsAbertos;
    private List<Ticket> ticketsFinalizados;
    private double faturamentoTotal = 0.0;

    public Atendimento() {
        ticketsAbertos = new ArrayList<>();
        ticketsFinalizados = new ArrayList<>();
    }

    public void gerarNovoTicket(Veiculo veiculo) {
        Ticket ticket = new Ticket(veiculo);
        ticketsAbertos.add(ticket);
    }

    public boolean finalizarTicket(Veiculo veiculo) {
        faturamentoTotal += veiculo.getValorEstacionamento().doubleValue();
        return true;
    }

    public Double calcularFaturamento() {
        return faturamentoTotal;
    }
}
