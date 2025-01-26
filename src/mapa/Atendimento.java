package mapa;

import java.util.ArrayList;
import java.util.List;

import util.Ticket;
import veiculos.Veiculo;

/**
 * Representa a entidate Atendimento que estará presente em um objeto do tipo Estacionamento
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Atendimento {
    private List<Ticket> ticketsAbertos;
    private double faturamentoTotal;

    public Atendimento() {
        ticketsAbertos = new ArrayList<>();
        faturamentoTotal = 0.0;
    }

    public void gerarNovoTicket(Veiculo veiculo) {
        Ticket ticket = new Ticket(veiculo);
        ticketsAbertos.add(ticket);
    }

    public boolean finalizarTicket(Veiculo veiculo) {
        Ticket ticketEncontrado = null;

        for (Ticket t : ticketsAbertos) {
            if (t.getPlacaVeiculo().equals(veiculo.getPlaca())) {
                ticketEncontrado = t;
            }
        }

        if (ticketEncontrado == null)
            return false;

        faturamentoTotal += ticketEncontrado.getCustoEstacionamento();
        ticketsAbertos.remove(ticketEncontrado);
        
        return true;
    }

    public Double getFaturamento() {
        return faturamentoTotal;
    }
}
