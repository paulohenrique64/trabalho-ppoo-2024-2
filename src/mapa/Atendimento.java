package mapa;

import java.util.ArrayList;
import java.util.List;

import util.Ticket;
import veiculos.Veiculo;

public class Atendimento {
    private List<Ticket> ticketsAbertos;
    private List<Ticket> ticketsFinalizados;

    public Atendimento() {
        ticketsAbertos = new ArrayList<>();
        ticketsFinalizados = new ArrayList<>();
    }

    public void gerarNovoTicket(Veiculo veiculo) {

    }

    public boolean finalizarTicket(Veiculo veiculo) {
        return true;
    }

    public Double calcularFaturamento() {
        return 19D;
    }
}
