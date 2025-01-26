package simulacao;

import java.awt.*;
import javax.swing.*;

import mapa.Estacionamento;
import util.Localizacao;
import veiculos.Veiculo;

public class JanelaSimulacao extends JFrame {
    private Estacionamento estacionamento;
    private VisaoEstacionamento visaoEstacionamento;

    public JanelaSimulacao(Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
        visaoEstacionamento = new VisaoEstacionamento(estacionamento.getLargura(), estacionamento.getAltura());

        getContentPane().add(visaoEstacionamento);
        setTitle("Simulator de Estacionamento");
        pack();
        ajustarTamanhoExato(940, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void ajustarTamanhoExato(int largura, int altura) {
        Insets insets = getInsets(); // Obtém as bordas do JFrame
        int larguraTotal = largura + insets.left + insets.right;
        int alturaTotal = altura + insets.top + insets.bottom;
        setSize(larguraTotal, alturaTotal); // Define o tamanho total do JFrame
    }

    /**
     * Mostra o estado atual do mapa.
     */
    public void atualizarJanelaSimulacao() {
        visaoEstacionamento.preparePaint();

        for (int i = 0; i < estacionamento.getAltura(); i++) {
            for (int j = 0; j < estacionamento.getLargura(); j++) {
                java.util.List<Veiculo> veiculosNaPosicao = estacionamento.getVeiculoNaPosicao(i, j);

                for (Veiculo veiculo : veiculosNaPosicao) {
                    Localizacao localizacao = veiculo.getLocalizacaoAtual();
                    visaoEstacionamento.desenharImagem(localizacao.getX(), localizacao.getY(), veiculo.getImagem(), veiculo.espacoOcupado());
                }

            }
        }

        visaoEstacionamento.repaint();
    }

    /**
     * Fornece uma visualizacao grafica do mapa. Esta eh
     * uma classe interna que define os componentes da GUI.
     * Ela contém alguns detalhes mais avancados sobre GUI
     * que voce pode ignorar para realizacao do seu trabalho.
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
         * Cria um novo componente VisaoEstacionamento.
         */
        public VisaoEstacionamento(int largura, int altura) {
            larguraEstacionamento = largura;
            alturaEstacionamento = altura;

            setBackground(Color.white);
            tamanho = new Dimension(0, 0);;

            // Carregar a imagem de fundo
            imagemFundo = new ImageIcon("src/Imagens/estacionamento.png").getImage(); 
        }

        /**
         * Informa para o gerenciador GUI o tamanho.
         */
        public Dimension getPreferredSize() {
            return new Dimension(larguraEstacionamento * VIEW_SCALING_FACTOR, alturaEstacionamento * VIEW_SCALING_FACTOR);
        }

        /**
         * Prepara para um novo ciclo de exibição. Uma vez que o componente
         * pode ser redimensionado, calcula o "fator de escala" novamente.
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

            // Desenhar o fundo branco e a grade
            // g.setColor(Color.WHITE);
            // g.fillRect(0, 0, tamanho.width, tamanho.height);

            // // Desenhar a grade (linhas horizontais e verticais)
            // g.setColor(Color.gray);
            // for (int i = 0, x = 0; x < tamanho.width; i++, x = i * xScale) {
            //     g.drawLine(x, 0, x, tamanho.height - 1);
            // }
            // for (int i = 0, y = 0; y < tamanho.height; i++, y = i * yScale) {
            //     g.drawLine(0, y, tamanho.width - 1, y);
            // }

            // Desenhar o fundo primeiro
            g.drawImage(imagemFundo, 0, 0, tamanho.width, tamanho.height, this);
        }

        /**
         * Desenha a imagem para um determinado item.
         */
        public void desenharImagem(int x, int y, Image image, Localizacao espacoOcupado) {
            int imageWidth = xScale * espacoOcupado.getX(); // 3x wider
            int imageHeight = yScale * espacoOcupado.getY(); // 3x taller]

            g.drawImage(image, x * xScale + 1, y * yScale + 1, imageWidth - 1, imageHeight - 1, this);
        }

        /**
         * O componente VisaoMapa precisa ser reexibido. Copia a
         * imagem interna para a tela.
         */
        public void paintComponent(Graphics g) {
            if (imagemEstacionamento != null) {
                g.drawImage(imagemEstacionamento, 0, 0, null);
            }
        }
    }

}
