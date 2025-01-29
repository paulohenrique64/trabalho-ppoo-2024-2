package mapa;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import veiculos.Veiculo;

/**
 * Representa a entidade Estacionamento, onde ocorrerá toda a lógica e fluxo de
 * veículos estacionados.
 * Gerencia as vagas disponíveis, controla o tempo de permanência dos veículos e
 * calcula o faturamento.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Estacionamento {
    private Map<Veiculo, Integer> veiculosVaga; // Mapeia o veículo para a vaga ocupada
    private Map<Veiculo, Instant> veiculosTempo; // Mapeia o veículo para o horário de saída permitido
    private List<Integer> vagasDisponiveis; // Lista de vagas disponíveis (0 a 19 para carros, 20 a 31 para motos)
    private Atendimento atendimento; // Responsável pela geração e finalização dos tickets

    /**
     * Construtor da classe Estacionamento.
     * Inicializa as estruturas de dados e define todas as vagas como disponíveis.
     */
    public Estacionamento() {
        veiculosVaga = new LinkedHashMap<>();
        veiculosTempo = new LinkedHashMap<>();
        vagasDisponiveis = new ArrayList<>();
        atendimento = new Atendimento();

        for (int i = 0; i < 32; i++) {
            vagasDisponiveis.add(i);
        }
    }

    /**
     * Retorna uma vaga disponível.
     * 
     * @return Número da vaga disponível ou -1 se não houver vagas.
     */
    public int getVagaDisponivel() {
        if (vagasDisponiveis.size() == 0)
            return -1;

        return vagasDisponiveis.get(vagasDisponiveis.size() - 1);
    }

    /**
     * Retorna uma vaga disponível aleatória dentro de um intervalo específico.
     * 
     * @param rangeInicial Início do intervalo de vagas.
     * @param rangeFinal   Fim do intervalo de vagas.
     * @return Número da vaga disponível dentro do intervalo ou -1 se não houver
     *         vagas disponíveis.
     */
    public int getVagaDisponivel(int rangeInicial, int rangeFinal) {
        List<Integer> vagasPossiveis = new ArrayList<>();
        Random random = new Random();

        for (int vaga : vagasDisponiveis) {
            if (vaga >= rangeInicial && vaga <= rangeFinal) {
                vagasPossiveis.add(vaga);
            }
        }

        if (vagasPossiveis.size() == 0)
            return -1;

        return vagasPossiveis.get(random.nextInt(vagasPossiveis.size()));
    }

    /**
     * Estaciona um veículo em uma vaga específica por um determinado tempo.
     * 
     * @param v                     Veículo a ser estacionado.
     * @param vaga                  Número da vaga onde o veículo será estacionado.
     * @param tempoDeEstacionamento Tempo, em segundos, que o veículo permanecerá
     *                              estacionado.
     * @return {@code true} se o veículo foi estacionado com sucesso, {@code false}
     *         caso contrário.
     */
    public boolean estacionarVeiculo(Veiculo v, int vaga, int tempoDeEstacionamento) {
        // Se a vaga já estiver sendo usada por outro veículo
        if (veiculosVaga.values().contains(vaga)) {
            return false;
        }

        veiculosVaga.put(v, vaga);
        veiculosTempo.put(v, Instant.now().plusSeconds(tempoDeEstacionamento));
        vagasDisponiveis.remove(Integer.valueOf(vaga));
        atendimento.gerarNovoTicket(v.getPlaca(), v.calcularTaxaDanificacaoTerreno());
        return true;
    }

    /**
     * Obtém a vaga em que um determinado veículo está estacionado.
     * 
     * @param v Veículo a ser consultado.
     * @return Número da vaga ocupada pelo veículo ou -1 se o veículo não estiver
     *         estacionado.
     */
    public int getVagaVeiculoEstacionado(Veiculo v) {
        if (!veiculosVaga.containsKey(v))
            return -1;

        return veiculosVaga.get(v);
    }

    /**
     * Remove um veículo do estacionamento se o tempo de permanência expirou.
     * 
     * @param v Veículo a ser removido do estacionamento.
     * @return {@code true} se o veículo foi removido com sucesso, {@code false}
     *         caso contrário.
     */
    public boolean desestacionarVeiculo(Veiculo v) {
        // Se o veículo não estive estacionado
        if (!veiculosVaga.containsKey(v))
            return false;

        // Verificar se o tempo expirou
        if (veiculosTempo.get(v).compareTo(Instant.now()) < 0) {
            vagasDisponiveis.add(veiculosVaga.get(v));
            veiculosVaga.remove(v);
            veiculosTempo.remove(v);
            atendimento.finalizarTicket(v.getPlaca());
            return true;
        }

        return false;
    }

    /**
     * Obtém o faturamento total do estacionamento com base nos tickets emitidos.
     * 
     * @return O valor total do faturamento.
     */
    public double getFaturamento() {
        return atendimento.getFaturamento();
    }
}
