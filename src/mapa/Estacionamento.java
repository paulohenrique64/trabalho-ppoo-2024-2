package mapa;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import veiculos.Veiculo;

// Veiculo10 -> 19
// Veiculo10 -> 10:50

/**
 * Representa a entidate Estacionamento onde ocorrerá toda a lógica/fluxo de veículos entrando e saindo
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Estacionamento {
    private Map<Veiculo, Integer> veiculosVaga; // map veiculo para vaga que ele ocupa
    private Map<Veiculo, Instant> veiculosTempo; // map veiculo para o horario que o veiculo podera sair da vaga
    private List<Integer> vagasDisponiveis; // 0 a 19 -> vagas de carros | 20 a 31 -> vagas de moto 
    private Atendimento atendimento;
    
    public Estacionamento() {
        veiculosVaga = new LinkedHashMap<>();
        veiculosTempo = new LinkedHashMap<>();
        vagasDisponiveis = new ArrayList<>();
        atendimento = new Atendimento();

        for (int i = 0; i < 32; i++) {
            vagasDisponiveis.add(i);
        }
    }

    public int getVagaDisponivel() {
        if (vagasDisponiveis.size() == 0) 
            return -1;

        return vagasDisponiveis.getLast();
    }

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

        return vagasPossiveis.get(random.nextInt(0, vagasPossiveis.size()));
    }

    public boolean estacionarVeiculo(Veiculo v, int vaga, int tempoDeEstacionamento) {
        if (veiculosVaga.values().contains(vaga)) {
            return false;
        }

        vagasDisponiveis.remove(Integer.valueOf(vaga));
        veiculosVaga.put(v, vaga);
        atendimento.gerarNovoTicket(v);
        veiculosTempo.put(v, Instant.now().plusSeconds(tempoDeEstacionamento));

        return true;
    }

    public boolean desestacionarVeiculo(Veiculo v) {
        if (!veiculosVaga.containsKey(v))
            return false;

        if (veiculosTempo.get(v).compareTo(Instant.now()) < 0) {
            vagasDisponiveis.add(veiculosVaga.get(v));
            veiculosVaga.remove(v);
            veiculosTempo.remove(v);
            atendimento.finalizarTicket(v);
            return true;
        }

        return false;
    }

    public Double getFaturamento() {
        return atendimento.getFaturamento();
    }
}
