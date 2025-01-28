package mapa;

import java.util.ArrayList;
import java.util.List;

import util.Localizacao;
import util.StatusGPSVeiculo;
import veiculos.Veiculo;

public class Mapa {
    private final int LARGURA_PADRAO_MAPA = 94;
    private final int ALTURA_PADRAO_MAPA = 50;
    private Estacionamento estacionamento;
    private List<Veiculo> veiculos;
    private int tempoVeiculoVaga;

    public Mapa(int tempoVeiculoVaga) {
        estacionamento = new Estacionamento();
        veiculos = new ArrayList<>();
        this.tempoVeiculoVaga = tempoVeiculoVaga;
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public boolean removerVeiculo(Veiculo v) {
        return veiculos.remove(v);
    }

    public void executarUmPasso() {
        List<Veiculo> veiculosParaRemover = new ArrayList<>();

        // executar um passo em todos os veiculos do jogo
        for (Veiculo v : veiculos) {
            // se o veiculo ainda nao chegou ao seu destino, ele da um passo
            // caso existir um veiculo bem a sua frente, ele nao anda e espera a proxima oportunidade
            // dois veiculos nao podem ocupar o mesmo lugar no espaço
            if (v.getProximaLocalizacao() != null) {
                List<Localizacao> trechoAFrente = v.getTrechoAFrente();
                boolean existeVeiculoAFrente = false;

                for (Localizacao l : trechoAFrente) {
                    if (existeVeiculoNaPosicao(l.getY(), l.getX())) {
                        existeVeiculoAFrente = true;
                    }
                }

                if (!existeVeiculoAFrente)
                    v.executarAcao();
            } 

            // se o veículo chegou ao seu local de destino
            // e o seu local de destino era a entrada do estacionamento
            // e existem vagas disponíveis no estacionamento
            // seta o status INDO_ESTACIONAR no veículo
            if (v.getProximaLocalizacao() == null && v.getStatusGPSVeiculo().equals(StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO)) {
                int vaga = -1;

                if (v.getQuantidadeRodas() >= 4) {
                    vaga = estacionamento.getVagaDisponivel(0, 19);
                } else {
                    vaga = estacionamento.getVagaDisponivel(20, 31);
                }
              
                if (vaga != -1 && estacionamento.estacionarVeiculo(v, vaga, tempoVeiculoVaga)) {
                    v.setStatusGPSVeiculo(StatusGPSVeiculo.INDO_ESTACIONAR);
                    v.setCaminho(Localizacao.carregarCaminho("data/vaga-estacionamento-" + vaga + "-caminho.txt"));
                }
            }

            // se o veículo chegou ao seu local de destino
            // e o seu local de destino era a vaga de estacionamento
            // tentamos desestacionar o veiculo
            // que pode falhar, visto que o veiculo so pode ser
            // desestacionado quando o horario de desestacionamento chegar
            if (v.getProximaLocalizacao() == null && v.getStatusGPSVeiculo() == StatusGPSVeiculo.INDO_ESTACIONAR) {
                if (estacionamento.desestacionarVeiculo(v)) {
                    // se o veiculo conseguir desestacionar
                    // ele eh removido do sistema
                    veiculosParaRemover.add(v); 
                }
            }
        }

        for (Veiculo v : veiculosParaRemover) {
            veiculos.remove(v);
        }
    }


    public boolean existeVeiculoNaPosicao(int x, int y) {
        for (Veiculo v : veiculos) {
            if (v.getLocalizacaoAtual().getX() == y && v.getLocalizacaoAtual().getY() == x)
                return true;
        }

        return false;
    }


    public List<Veiculo> getVeiculoNaPosicao(int x, int y) {
        List<Veiculo> veiculosNaPosicao = new ArrayList<>();

        for (Veiculo v : veiculos) {
            if (v.getLocalizacaoAtual().getX() == y && v.getLocalizacaoAtual().getY() == x)
                veiculosNaPosicao.add(v);
        }

        return veiculosNaPosicao;
    }


    public int getQuantidadeVeiculosIndoParaEntradaEstacionamento() {
        int cont = 0;

        for (Veiculo v : veiculos) {
            if (v.getStatusGPSVeiculo() == StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO) {
                cont++;
            }
        }

        return cont;
    }

    public int getAltura() {
        return ALTURA_PADRAO_MAPA;
    }

    public int getLargura() {
        return LARGURA_PADRAO_MAPA;
    }

    public Double getFaturamentoEstacionamento() {
        return estacionamento.getFaturamento();
    } 
}
