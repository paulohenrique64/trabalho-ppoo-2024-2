package mapa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import veiculos.Veiculo;

/**
 * Representa a entidate Estacionamento onde ocorrerá toda a lógica/fluxo de veículos entrando e saindo
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Estacionamento {
    private static int LARGURA_PADRAO_MAPA = 94;
    private static int ALTURA_PADRAO_MAPA = 50;
    private List<Veiculo> veiculosCirculando;
    private Map<Veiculo, Integer> veiculosEstacionados; // map veiculo para vaga que ele ocupa
    private List<Integer> vagasDisponiveis; // 0 a 19 -> vagas de carros | 20 a 31 -> vagas de moto 
    private Atendimento atendimento;
    
    public Estacionamento() {
        veiculosEstacionados = new LinkedHashMap<>();
        vagasDisponiveis = new ArrayList<>();
        atendimento = new Atendimento();
        veiculosCirculando = new ArrayList<>();

        for (int i = 0; i < 32; i++) {
            vagasDisponiveis.add(i);
        }
    }

    public void adicionarVeiculoCirculando(Veiculo v) {
        veiculosCirculando.add(v);
    }

    public boolean removerVeiculoCirculando(Veiculo v) {
        return veiculosCirculando.remove(v);
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

    public void estacionarVeiculo(Veiculo v, int numeroVaga) {
        vagasDisponiveis.remove(Integer.valueOf(numeroVaga));
        veiculosEstacionados.put(v, numeroVaga);
        atendimento.gerarNovoTicket(v);
    }

    public boolean desestacionarVeiculo(Veiculo v) {
        if (!veiculosEstacionados.containsKey(v))
            return false;

        vagasDisponiveis.add(veiculosEstacionados.get(v));
        veiculosEstacionados.remove(v);
        atendimento.finalizarTicket(v);

        return true;
    }

    public List<Veiculo> getVeiculoNaPosicao(int x, int y) {
        List<Veiculo> veiculosNaPosicao = new ArrayList<>();

        for (Veiculo v : veiculosCirculando) {
            if (v.getLocalizacaoAtual().getX() == y && v.getLocalizacaoAtual().getY() == x)
                veiculosNaPosicao.add(v);
        }

        return veiculosNaPosicao;
    }

    public boolean existeVeiculoNaPosicao(int x, int y) {
        for (Veiculo v : veiculosCirculando) {
            if (v.getLocalizacaoAtual().getX() == y && v.getLocalizacaoAtual().getY() == x)
                return true;
        }

        return false;
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
        return atendimento.getFaturamento();
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }
}
