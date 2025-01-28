package mapa;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Localizacao;
import util.StatusGPSVeiculo;
import veiculos.Veiculo;

public class Mapa {
    private final int LARGURA_PADRAO_MAPA = 94;
    private final int ALTURA_PADRAO_MAPA = 50;
    private Estacionamento estacionamento;
    private List<Veiculo> veiculos;

    public Mapa() {
        estacionamento = new Estacionamento();
        veiculos = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public boolean removerVeiculo(Veiculo v) {
        return veiculos.remove(v);
    }

    public void executarUmPasso() {
        List<Veiculo> veiculosQueJaEstacionaramEJaPodemSerRemovidos = new ArrayList<>();
        Random random = new Random();

        // executar um passo em todos os veiculos do jogo
        for (Veiculo v : veiculos) {
            // se o veiculo ainda nao chegou ao seu destino, ele da um passo
            // caso existir um veiculo bem a sua frente, ele nao anda e espera a proxima oportunidade
            // dois veiculos nao podem ocupar o mesmo lugar no espaço
            if (v.getProximaLocalizacao() != null) {
                boolean existeVeiculoAFrente = false;
                List<Localizacao> trechoAFrente = v.getTrechoAFrente();

                for (Localizacao l : trechoAFrente) {
                    if (existeVeiculoNaPosicao(l.getY(), l.getX())) {
                        existeVeiculoAFrente = true;
                    }
                }

                System.out.println("existe veiculo a frnete -> " + existeVeiculoAFrente);

                if (!existeVeiculoAFrente)
                    v.executarAcao();
            }

            // se o veículo chegou ao seu local de destino
            // e o seu local de destino era a entrada do estacionamento
            // e existem vagas disponíveis no estacionamento
            // seta o status INDO_ESTACIONAR no veículo
            System.out.println(v.getProximaLocalizacao() != null);
            System.out.println(v.getStatusGPSVeiculo() == StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO);
            System.out.println(v.getProximaLocalizacao() != null);
            if (v.getProximaLocalizacao() == null 
                && v.getStatusGPSVeiculo() == StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO
                && estacionamento.existeVagaDisponivel()) {

                estacionamento.estacionarVeiculo(v, random.nextInt(10, 20));
                v.setStatusGPSVeiculo(StatusGPSVeiculo.INDO_ESTACIONAR);
            }

            System.out.println(estacionamento.teste());
            System.out.println(estacionamento.existeVagaDisponivel());

            // se o veículo chegou ao seu local de destino
            // e o seu local de destino era a vaga de estacionamento
            // tentamos desestacionar o veiculo
            // que pode falhar, visto que o veiculo so pode ser
            // desestacionado quando o horario de desestacionamento chegar
            if (v.getProximaLocalizacao() == null && v.getStatusGPSVeiculo() == StatusGPSVeiculo.INDO_ESTACIONAR) {
                if (estacionamento.desestacionarVeiculo(v)) {
                    // se o veiculo conseguir desestacionar
                    // ele eh removido do sistema
                    veiculosQueJaEstacionaramEJaPodemSerRemovidos.add(v); 
                }
            }
        }

        for (Veiculo v : veiculosQueJaEstacionaramEJaPodemSerRemovidos) {
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
