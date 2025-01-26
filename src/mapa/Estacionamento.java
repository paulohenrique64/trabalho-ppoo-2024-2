package mapa;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import util.Localizacao;
import veiculos.Veiculo;

public class Estacionamento {
    private static int LARGURA_PADRAO_MAPA = 94;
    private static int ALTURA_PADRAO_MAPA = 50;
    private Map<Veiculo, Integer> veiculosEstacionados;
    private List<Integer> vagasDisponiveis;
    private Atendimento atendimento;
    
    public Estacionamento() {
        veiculosEstacionados = new LinkedHashMap<>();
        vagasDisponiveis = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            vagasDisponiveis.add(i);
        }
    }

    public boolean existeVagaDisponivel() {
        return vagasDisponiveis.size() > 0;
    }

    public int getVagaDisponivel() {
        return vagasDisponiveis.getLast();
    }

    public void estacionarVeiculo(Veiculo v, int numeroVaga) {
        vagasDisponiveis.remove(Integer.valueOf(numeroVaga));
        veiculosEstacionados.put(v, numeroVaga);
    }

    public boolean desestacionarVeiculo(Veiculo v) {
        if (!veiculosEstacionados.containsKey(v))
            return false;

        // liberar a vaga
        vagasDisponiveis.add(veiculosEstacionados.get(v));

        veiculosEstacionados.remove(v);

        return true;
    }

    public List<Veiculo> getVeiculoNaPosicao(int x, int y) {
        List<Veiculo> veiculosNaPosicao = new ArrayList<>();

        for (Veiculo v : veiculosEstacionados.keySet()) {
            if (v.getLocalizacaoAtual().getX() == y && v.getLocalizacaoAtual().getY() == x)
                veiculosNaPosicao.add(v);
        }

        return veiculosNaPosicao;
    }

    public int getQuantidadeVeiculosEstacionados() {
        return veiculosEstacionados.size();
    }










    public int getAltura() {
        return ALTURA_PADRAO_MAPA;
    }

    public int getLargura() {
        return LARGURA_PADRAO_MAPA;
    }

    public Double getFaturamento() {
        return atendimento.calcularFaturamento();
    }
}
