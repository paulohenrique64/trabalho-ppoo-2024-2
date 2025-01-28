package mapa;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import util.Localizacao;
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

    public boolean existeVagaDisponivel() {
        return vagasDisponiveis.size() > 0;
    }

    public int getVagaDisponivel() {
        if (!existeVagaDisponivel()) 
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

    public boolean estacionarVeiculo(Veiculo v, int tempoDeEstacionamento) {
        int vagaDisponivel;

        if (v.getQuantidadeRodas() >= 4) {
            vagaDisponivel = getVagaDisponivel(0, 19);
        } else {
            vagaDisponivel = getVagaDisponivel(20, 31);
        }

        if (vagaDisponivel != -1) {
            v.setCaminho(Localizacao.carregarCaminho("data/vaga-estacionamento-" + vagaDisponivel + "-caminho.txt"));
            vagasDisponiveis.remove(Integer.valueOf(vagaDisponivel));
            veiculosVaga.put(v, vagaDisponivel);
            atendimento.gerarNovoTicket(v);

            veiculosTempo.put(v, Instant.now().plusSeconds(tempoDeEstacionamento));
            return true;
        }

        return false;
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

    public boolean possuiVeiculo(Veiculo veiculo) {
        for (Veiculo v : veiculosVaga.keySet()) {
            if (v.getPlaca().equals(veiculo.getPlaca()))
                return true;
        }

        return false;
    }

    public int getQuantidadeVeiculosEstacionados() {
        return veiculosVaga.size();
    }

    public Double getFaturamento() {
        return atendimento.getFaturamento();
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public int teste() {
        return veiculosVaga.size();
    }
}
