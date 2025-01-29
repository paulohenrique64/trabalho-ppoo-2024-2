package simulacao;

import java.awt.*;

import javax.swing.*;

import mapa.Mapa;
import utilitarios.Localizacao;
import veiculos.Veiculo;

/**
 * Representa a interface gráfica do sistema de simulação de estacionamento.
 * Nela, os veículos são desenhados a cada atualização da tela conforme se movem
 * no mapa.
 * 
 * Esta classe herda de JFrame e exibe uma janela que irá conter o mapa do
 * estacionamento (um JPanel com uma imagem do estacionamento de fundo,
 * a visão dos veículos e o faturamento gerado),
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class JanelaSimulacao extends JFrame {
    private Mapa mapa;
    private VisaoEstacionamento visaoEstacionamento; // Visão gráfica do estacionamento.
    private JLabel faturamentoLabel;

    /**
     * Construtor que inicializa a janela de simulação.
     * 
     * @param mapa O mapa que será utilizado para desenhar a simulação.
     */
    public JanelaSimulacao(Mapa mapa) {
        this.mapa = mapa;
        visaoEstacionamento = new VisaoEstacionamento(mapa.getLargura(), mapa.getAltura());
        criarInterface();
    }

    /**
     * Ajusta o tamanho exato da janela considerando as bordas do JFrame.
     * 
     * @param largura A largura da janela.
     * @param altura  A altura da janela.
     */
    private void ajustarTamanhoExato(int largura, int altura) {
        Insets insets = getInsets(); // obtem as bordas do JFrame
        int larguraTotal = largura + insets.left + insets.right;
        int alturaTotal = altura + insets.top + insets.bottom;
        setSize(larguraTotal, alturaTotal); // define o tamanho total do JFrame
    }

    /**
     * Atualiza a visualização da janela, redesenhando os veículos no mapa.
     */
    public void atualizarJanelaSimulacao() {
        visaoEstacionamento.preparePaint();

        for (int y = 0; y < mapa.getAltura(); y++) {
            for (int x = 0; x < mapa.getLargura(); x++) {
                java.util.List<Veiculo> veiculosNaPosicao = mapa.getVeiculoNaPosicao(x, y);

                for (Veiculo v : veiculosNaPosicao) {
                    Localizacao l = v.getLocalizacaoAtual();
                    visaoEstacionamento.desenharImagem(l.getX(), l.getY(), v.getImagem(), v.getEspacoOcupado());
                }
            }
        }

        visaoEstacionamento.repaint();
    }

    /**
     * Exibe um popup de mensagem e fecha a aplicação.
     * 
     * @param mensagem A mensagem que será exibida no popup.
     */
    public void exibirPopupFinal(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);

        // Encerra a aplicação após o fechamento do popup
        System.exit(0);
    }

    /**
     * Atualiza o valor exibido no label de faturamento.
     * 
     * @param valor O valor a ser exibido no label.
     */
    public void atualizarFaturamento(double valor) {
        faturamentoLabel.setText(String.format("Faturamento: R$ %.2f", valor));
    }

    /**
     * Cria a interface gráfica da janela, incluindo o label de faturamento e a
     * visão do estacionamento.
     */
    private void criarInterface() {
        faturamentoLabel = new JLabel("Faturamento: R$ 0.00");
        faturamentoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        faturamentoLabel.setForeground(Color.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.add(faturamentoLabel);

        // Add components
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(visaoEstacionamento, BorderLayout.CENTER);

        setTitle("Simulador de Estacionamento");
        pack();
        ajustarTamanhoExato(940, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Classe interna responsável por desenhar a visão gráfica do estacionamento.
     * 
     * Esta classe é responsável por criar o fundo do estacionamento e desenhar os
     * veículos no mapa.
     */
    private class VisaoEstacionamento extends JPanel {

        private final int VIEW_SCALING_FACTOR = 10;
        private int larguraEstacionamento, alturaEstacionamento;
        private int xScale, yScale;
        private Dimension tamanho;
        private Graphics g;
        private Image imagemEstacionamento;
        private Image imagemFundo; // Imagem de fundo

        /**
         * Construtor que inicializa o componente de visualização do estacionamento.
         * 
         * @param largura A largura do estacionamento.
         * @param altura  A altura do estacionamento.
         */
        public VisaoEstacionamento(int largura, int altura) {
            larguraEstacionamento = largura;
            alturaEstacionamento = altura;

            setBackground(Color.white);
            tamanho = new Dimension(0, 0);
            ;

            // Carregar a imagem de fundo
            imagemFundo = new ImageIcon("data/imagens/estacionamento/estacionamento.png").getImage();
        }

        /**
         * Informa para o gerenciador da GUI o tamanho preferido para o componente.
         * 
         * @return O tamanho preferido do componente.
         */
        public Dimension getPreferredSize() {
            return new Dimension(larguraEstacionamento * VIEW_SCALING_FACTOR,
                    alturaEstacionamento * VIEW_SCALING_FACTOR);
        }

        /**
         * Prepara o ciclo de pintura, ajustando o fator de escala caso necessário.
         */
        public void preparePaint() {
            if (!tamanho.equals(getSize())) { // Se o tamanho mudou...
                tamanho = getSize();

                imagemEstacionamento = visaoEstacionamento.createImage(tamanho.width, tamanho.height);

                g = imagemEstacionamento.getGraphics();

                xScale = tamanho.width / larguraEstacionamento;
                if (xScale < 1) {
                    xScale = VIEW_SCALING_FACTOR;
                }
                yScale = tamanho.height / alturaEstacionamento;
                if (yScale < 1) {
                    yScale = VIEW_SCALING_FACTOR;
                }
            }

            if (g == null) {
                return;
            }

            // Desenhar o fundo primeiro
            g.drawImage(imagemFundo, 0, 0, tamanho.width, tamanho.height, this);
        }

        /**
         * Desenha a imagem de um veículo em uma posição específica no estacionamento.
         * 
         * @param x             A posição X no estacionamento.
         * @param y             A posição Y no estacionamento.
         * @param image         A imagem do veículo.
         * @param espacoOcupado A área que o veículo ocupa no estacionamento.
         */
        public void desenharImagem(int x, int y, Image image, Localizacao espacoOcupado) {
            int imageWidth = xScale * espacoOcupado.getX(); // 3x wider
            int imageHeight = yScale * espacoOcupado.getY(); // 3x taller]

            g.drawImage(image, x * xScale - (imageWidth / 2 - espacoOcupado.getX()),
                    y * yScale - (imageHeight / 2 - espacoOcupado.getY()), imageWidth, imageHeight, this);
        }

        /**
         * Atualiza a tela com a nova imagem do estacionamento.
         * 
         * @param g O objeto gráfico utilizado para desenhar a tela.
         */
        public void paintComponent(Graphics g) {
            if (imagemEstacionamento != null) {
                g.drawImage(imagemEstacionamento, 0, 0, null);
            }
        }
    }
}
