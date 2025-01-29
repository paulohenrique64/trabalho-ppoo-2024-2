package utilitarios;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Representa um ticket de estacionamento, utilizado para manipular
 * dados referentes à entrada e saída de veículos em estacionamentos.
 * 
 * Um ticket é gerado quando um veículo entra em um estacionamento, armazenando
 * a hora de chegada e possibilitando o cálculo do custo de estacionamento com
 * base no tempo de permanência e características do veículo.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Ticket {
    private String placaVeiculo;
    private double taxaDanificacaoTerreno;
    private LocalDateTime horaChegada;

    /**
     * Constrói um novo ticket para o veículo fornecido, registrando a hora de
     * chegada no momento da criação.
     * 
     * @param veiculo O veículo que está entrando no estacionamento.
     */
    public Ticket(String placaVeiculo, double taxaDanificacaoTerreno) {
        this.placaVeiculo = placaVeiculo;
        this.taxaDanificacaoTerreno = taxaDanificacaoTerreno;
        horaChegada = LocalDateTime.now();
    }

    /**
     * Calcula o custo do estacionamento com base no tempo de permanência do veículo,
     * no espaço ocupado por ele e na taxa de dano ao terreno. O custo é calculado
     * com a seguinte fórmula:
     * 
     * custo = (tempo de permanência em segundos / 2) * taxa de dano ao terreno
     * 
     * @return O custo do estacionamento para o veículo.
     */
    public double getCustoEstacionamento() {
        // Calcular o tempo de permanencia em segundos.
        LocalDateTime agora = LocalDateTime.now();
        Duration duracao = Duration.between(horaChegada, agora);
        long segundosNoEstacionamento = duracao.getSeconds();

        // Calcular o custo do estacionamento.
        double custo = (segundosNoEstacionamento / 2.0) * taxaDanificacaoTerreno;
        return custo;
    }

    /**
     * Retorna a placa do veículo associado a este ticket.
     * 
     * @return A placa do veículo.
     */
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }
}
