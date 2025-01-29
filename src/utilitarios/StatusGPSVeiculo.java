package utilitarios;

/**
 * Enum que representa os diferentes status do GPS de um veículo no Mapa
 * 
 * Cada valor no enum indica uma etapa do processo de movimentação do
 * veículo dentro do Mapa:
 * 
 * - {@link #INDO_PARA_ENTRADA_ESTACIONAMENTO}: O veículo está a caminho da entrada do estacionamento.
 * - {@link #INDO_ESTACIONAR}: O veículo está a caminho de uma vaga de estacionamento.
 * - {@link #INDO_EMBORA_DO_ESTACIONAMENTO}: O veículo está saindo do estacionamento e indo para fora do Mapa.
 * 
 * @author Paulo Henrique Ribeiro Alves
 */
public enum StatusGPSVeiculo {
    INDO_PARA_ENTRADA_ESTACIONAMENTO,
    INDO_ESTACIONAR,
    INDO_EMBORA_DO_ESTACIONAMENTO
}
