package mapa;

import java.util.ArrayList;
import java.util.List;

import utilitarios.Localizacao;
import utilitarios.StatusGPSVeiculo;
import veiculos.Veiculo;

/**
 * Representa o mapa do estacionamento, contendo a lógica de movimentação
 * dos veículos e interação com o estacionamento.
 * 
 * O mapa gerencia a movimentação dos veículos, verifica a disponibilidade
 * de vagas e processa a entrada e saída dos veículos do estacionamento.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Mapa {
    private final int LARGURA_PADRAO_MAPA = 94;
    private final int ALTURA_PADRAO_MAPA = 50;
    private Estacionamento estacionamento;
    private List<Veiculo> veiculos;
    private int tempoVeiculoVaga;

    /**
     * Construtor da classe Mapa.
     * 
     * @param tempoVeiculoVaga Tempo que um veículo permanece estacionado antes de
     *                         sair da vaga de estacionamento.
     */
    public Mapa(int tempoVeiculoVaga) {
        estacionamento = new Estacionamento();
        veiculos = new ArrayList<>();
        this.tempoVeiculoVaga = tempoVeiculoVaga;
    }

    /**
     * Adiciona um veículo ao mapa.
     * 
     * @param veiculo Veículo a ser adicionado.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
        veiculo.setCaminho(Localizacao.carregarCaminho("data/caminhos/caminho-ate-entrada.txt"));
    }

    /**
     * Remove um veículo do mapa.
     * 
     * @param v Veículo a ser removido.
     * @return true se o veículo foi removido, false caso contrário.
     */
    public boolean removerVeiculo(Veiculo v) {
        return veiculos.remove(v);
    }

    /**
     * Executa um passo da simulação, movimentando os veículos e processando suas
     * ações.
     * 
     * O método lida com: movimentação dos veículos, entrada no estacionamento,
     * permanência nas vagas e saída do estacionamento.
     */
    public void executarUmPasso() {
        // Lista para auxiliar na remoção dos veículos que já desestacionaram e sairam do mapa.
        // Isso é necessário para economizar memória e manter a lista Veículos controlada.
        List<Veiculo> veiculosParaRemover = new ArrayList<>(); 

        for (Veiculo v : veiculos) {
            //
            // ETAPA 1: Mover todos os veiculos que puderem se mover
            //

            // Verifica se o veículo pode se mover
            if (v.getProximaLocalizacao() != null) {
                List<Localizacao> trechoAFrente = v.getTrechoAFrente();
                boolean existeVeiculoAFrente = false;

                for (Localizacao l : trechoAFrente) 
                    if (existeVeiculoNaPosicao(l.getX(), l.getY())) 
                        existeVeiculoAFrente = true;

                if (!existeVeiculoAFrente)
                    v.executarAcao();

            }

            //
            // ETAPA 2: Gerenciar as próximas ações de cada veículo atualizando seu GPS ou removendo o veículo do mapa
            // 

            // Processa a chegada do veículo na entrada do estacionamento
            if (v.getProximaLocalizacao() == null && v.getStatusGPSVeiculo().equals(StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO)) {
                int vaga = -1;

                if (v.getQuantidadeRodas() >= 4) 
                    vaga = estacionamento.getVagaDisponivel(0, 19);
                else 
                    vaga = estacionamento.getVagaDisponivel(20, 31);

                if (vaga != -1 && estacionamento.estacionarVeiculo(v, vaga, tempoVeiculoVaga)) {
                    // Atualizando o GPS do veículo e alterando sua ROTA (caminho)
                    v.setStatusGPSVeiculo(StatusGPSVeiculo.INDO_ESTACIONAR); 
                    v.setCaminho(Localizacao.carregarCaminho("data/caminhos/caminhos-estacionamento-para-vaga/vaga-" + vaga + ".txt"));
                }
            }

            // Processa a saída do veículo do estacionamento
            if (v.getProximaLocalizacao() == null && v.getStatusGPSVeiculo() == StatusGPSVeiculo.INDO_ESTACIONAR) {
                int vaga = estacionamento.getVagaVeiculoEstacionado(v);

                if (vaga != -1 && estacionamento.desestacionarVeiculo(v)) {
                    // Atualizando o GPS do veículo e alterando sua ROTA (caminho)
                    v.setCaminho(Localizacao.carregarCaminho("data/caminhos/caminhos-estacionamento-para-saida/saida-" + vaga + ".txt"));
                    v.setStatusGPSVeiculo(StatusGPSVeiculo.INDO_EMBORA_DO_ESTACIONAMENTO);
                }
            }

            // Remove o veículo se ele saiu do estacionamento
            if (v.getProximaLocalizacao() == null && v.getStatusGPSVeiculo() == StatusGPSVeiculo.INDO_EMBORA_DO_ESTACIONAMENTO) {
                veiculosParaRemover.add(v);
            }
        }

        for (Veiculo v : veiculosParaRemover) {
            veiculos.remove(v);
        }
    }

    /**
     * Verifica se há um veículo em uma posição específica do mapa.
     * 
     * @param x Coordenada X da posição.
     * @param y Coordenada Y da posição.
     * @return true se existe um veículo na posição, false caso contrário.
     */
    public boolean existeVeiculoNaPosicao(int x, int y) {
        for (Veiculo v : veiculos) 
            if (v.getLocalizacaoAtual().getX() == x && v.getLocalizacaoAtual().getY() == y)
                return true;

        return false;
    }

    /**
     * Retorna uma lista de veículos presentes em uma determinada posição do mapa.
     * 
     * @param x Coordenada X da posição.
     * @param y Coordenada Y da posição.
     * @return Lista de veículos na posição especificada.
     */
    public List<Veiculo> getVeiculoNaPosicao(int x, int y) {
        List<Veiculo> veiculosNaPosicao = new ArrayList<>();

        for (Veiculo v : veiculos) 
            if (v.getLocalizacaoAtual().getX() == x && v.getLocalizacaoAtual().getY() == y)
                veiculosNaPosicao.add(v);

        return veiculosNaPosicao;
    }

    /**
     * Retorna a quantidade de veículos que estão indo para a entrada do
     * estacionamento.
     * 
     * @return Número de veículos a caminho da entrada do estacionamento.
     */
    public int getQuantidadeVeiculosIndoParaEntradaEstacionamento() {
        int cont = 0;

        for (Veiculo v : veiculos) {
            if (v.getStatusGPSVeiculo() == StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO) {
                cont++;
            }
        }

        return cont;
    }

    /**
     * Retorna a altura do mapa.
     * 
     * @return Altura do mapa.
     */
    public int getAltura() {
        return ALTURA_PADRAO_MAPA;
    }

    /**
     * Retorna a largura do mapa.
     * 
     * @return Largura do mapa.
     */
    public int getLargura() {
        return LARGURA_PADRAO_MAPA;
    }

    /**
     * Retorna o faturamento total do estacionamento.
     * 
     * @return Faturamento total do estacionamento.
     */
    public double getFaturamentoEstacionamento() {
        return estacionamento.getFaturamento();
    }
}
