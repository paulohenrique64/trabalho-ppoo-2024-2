import simulacao.Simulacao;

/**
 *
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Main {

    public static void main(String[] args) {
        // Quanto menor, mais rapido a simulacao fica.
        // Minimo: 2
        // Default: 50
        int velocidadeSimulacao = 50; 

        // Quanto maior, maior sera a quantidade de veiculos na simulacao.
        // Maximo: 7
        // Minimo: 1
        // Default: 2
        int fluxoVeiculos = 2; 

        // Duracao da simulacao em segundos
        // Minimo: 0
        // Default: 375
        int duracaoDaSimulacao = 375;

        Simulacao simulacao = new Simulacao(velocidadeSimulacao, fluxoVeiculos, duracaoDaSimulacao);
        simulacao.iniciarSimulacao();
    }
}
