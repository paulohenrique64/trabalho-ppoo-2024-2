package mapa;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Localizacao;
import veiculos.Veiculo;

public class Estacionamento {
    private static int LARGURA_PADRAO_MAPA = 94;
    private static int ALTURA_PADRAO_MAPA = 50;
    private List<Veiculo> veiculos;   
    private List<Localizacao> vagasDisponiveis;
    private Atendimento atendimento;
    
    public Estacionamento() {
        veiculos = new ArrayList<>();
        vagasDisponiveis = new ArrayList<>();

        vagasDisponiveis.add(new Localizacao(50, 50));
        vagasDisponiveis.add(new Localizacao(100, 100));
        vagasDisponiveis.add(new Localizacao(150, 150));
        vagasDisponiveis.add(new Localizacao(200, 200));
    }

    public int getAltura() {
        return ALTURA_PADRAO_MAPA;
    }

    public int getLargura() {
        return LARGURA_PADRAO_MAPA;
    }

    public void estacionarVeiculo(Veiculo v) {
        veiculos.add(v);
    }

    public boolean desestacionarVeiculo(Veiculo v) {
        return true;
    }

    public void atualizarEstacionamento(Veiculo v) {

    }

    public List<Veiculo> getVeiculo(int x, int y) {
        List<Veiculo> veiculosRetorno = new ArrayList<>();
         
        for (Veiculo v : veiculos) {
            
            if (v.getLocalizacaoAtual().getX() == x && v.getLocalizacaoAtual().getY() == y) {
                veiculosRetorno.add(v);
            }
        }

        return veiculosRetorno;
    }

    public Double getFaturamento() {
        return atendimento.calcularFaturamento();
    }

    public void removerItem(Veiculo v) {
        veiculos.remove(v);
    }

    public void adicionarItem(Veiculo v) {
        veiculos.add(v);
    }
}
