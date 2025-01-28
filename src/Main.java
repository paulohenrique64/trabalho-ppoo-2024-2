import simulacao.Simulacao;

/**
 *
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class Main {

    public static void main(String[] args) {

        int velocidadeSimulacao = 20; // por padrao 50

        int fluxoVeiculos = 2; // por padrao 2

        Simulacao sim = new Simulacao(velocidadeSimulacao, fluxoVeiculos);
        sim.iniciarSimulacao();
    }
}
