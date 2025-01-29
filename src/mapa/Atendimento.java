package mapa;

import java.util.ArrayList;
import java.util.List;

import utilitarios.Ticket;

/**
 * Representa a entidade Atendimento, que gerencia a emissão e finalização de
 * tickets dentro do estacionamento.
 * Cada estacionamento possui um objeto desta classe para controlar os veículos
 * estacionados e calcular o faturamento.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Atendimento {
    private List<Ticket> ticketsAbertos;
    private double faturamentoTotal;

    /**
     * Construtor da classe Atendimento.
     * Inicializa a lista de tickets abertos e define o faturamento inicial como
     * zero.
     */
    public Atendimento() {
        ticketsAbertos = new ArrayList<>();
        faturamentoTotal = 0.0;
    }

    /**
     * Gera um novo ticket para um veículo que está entrando no estacionamento.
     * 
     * @param placaVeiculo                 Placa do veículo para o qual o ticket será gerado.
     * @param taxaDanificacaoTerreno       Taxa de danificação à terrenos do veículo para
     *                                     o qual o ticket será gerado.
     */
    public void gerarNovoTicket(String placaVeiculo, double taxaDanificacaoTerreno) {
        Ticket ticket = new Ticket(placaVeiculo, taxaDanificacaoTerreno);
        ticketsAbertos.add(ticket);
    }

    /**
     * Finaliza o ticket de um veículo ao sair do estacionamento, calculando o
     * faturamento.
     * 
     * @param placaVeiculo Placa do veículo para o qual o ticket será finalizado.
     * @return {@code true} se o ticket foi finalizado com sucesso, {@code false}
     *         caso contrário.
     */
    public boolean finalizarTicket(String placaVeiculo) {
        Ticket ticketEncontrado = null;

        for (Ticket t : ticketsAbertos) 
            if (t.getPlacaVeiculo().equals(placaVeiculo)) 
                ticketEncontrado = t;

        if (ticketEncontrado == null)
            return false;

        faturamentoTotal += ticketEncontrado.getCustoEstacionamento();
        ticketsAbertos.remove(ticketEncontrado);
        return true;
    }

    /**
     * Obtém o faturamento total do estacionamento até o momento.
     * 
     * @return Valor total faturado pelo estacionamento.
     */
    public double getFaturamento() {
        return faturamentoTotal;
    }
}
