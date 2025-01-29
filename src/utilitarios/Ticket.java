package utilitarios;

import java.time.Duration;
import java.time.LocalDateTime;

import veiculos.Veiculo;

/**
 * Representa um ticket de estacionamento, utilizado para armazenar e manipular
 * dados referentes à entrada e saída de veículos em estacionamentos.
 * 
 * Um ticket é gerado quando um veículo entra em um estacionamento, armazenando
 * a hora de chegada e possibilitando o cálculo do custo de estacionamento com
 * base no tempo de permanência e características do veículo.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Ticket {
    private Veiculo veiculo;
    private LocalDateTime horaChegada;

    /**
     * Constrói um novo ticket para o veículo fornecido, registrando a hora de
     * chegada no momento da criação.
     * 
     * @param veiculo O veículo que está entrando no estacionamento.
     */
    public Ticket(Veiculo veiculo) {
        this.veiculo = veiculo;
        horaChegada = LocalDateTime.now();
    }

    /**
     * Calcula o custo do estacionamento com base no tempo de permanência do
     * veículo,
     * no espaço ocupado por ele e na taxa de dano ao terreno. O custo é calculado
     * com a seguinte fórmula:
     * 
     * <pre>
     * custo = (tempo de permanência em segundos / 5) * largura do espaço ocupado
     * * taxa de dano ao terreno
     * </pre>
     * 
     * @return O custo do estacionamento para o veículo.
     */
    public Double getCustoEstacionamento() {
        // Calcular o tempo de permanencia em segundos.
        LocalDateTime agora = LocalDateTime.now();
        Duration duracao = Duration.between(horaChegada, agora);
        long segundosNoEstacionamento = duracao.getSeconds();

        // Calcular o custo do estacionamento.
        double custo = (segundosNoEstacionamento / 5.0)
                * veiculo.getEspacoOcupado().getX()
                * veiculo.calcularTaxaDanificacaoTerreno();

        // System.out.println("Tempo do veiculo " + veiculo.getPlaca() + " no estacionamento: " + segundosNoEstacionamento + " segundos");
        return custo;
    }

    /**
     * Retorna a placa do veículo associado a este ticket.
     * 
     * @return A placa do veículo.
     */
    public String getPlacaVeiculo() {
        return veiculo.getPlaca();
    }
}
