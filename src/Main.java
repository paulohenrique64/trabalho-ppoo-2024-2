import simulacao.Simulacao;

/**
 *
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Main {

    public static void main(String[] args) {

        int velocidadeSimulacao = 30; // por padrao 50

        int fluxoVeiculos = 4; // por padrao 4

        Simulacao sim = new Simulacao(velocidadeSimulacao, fluxoVeiculos);
        sim.iniciarSimulacao();
    }
}
