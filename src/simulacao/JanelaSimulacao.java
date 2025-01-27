package simulacao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import mapa.Estacionamento;
import util.Localizacao;
import veiculos.Veiculo;

/**
 * Representa a interface so sistema onde os veículos serão desenhados a cada atualização da tela
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public class JanelaSimulacao extends JFrame {
    private Estacionamento estacionamento;
    private VisaoEstacionamento visaoEstacionamento;
    private JLabel faturamentoLabel;
    private Simulacao simulacao;

    public JanelaSimulacao(Estacionamento estacionamento, Simulacao simulacao) {
        this.estacionamento = estacionamento;
        this.simulacao = simulacao;
        visaoEstacionamento = new VisaoEstacionamento(estacionamento.getLargura(), estacionamento.getAltura());
        criarInterface();
    }

    private void ajustarTamanhoExato(int largura, int altura) {
        Insets insets = getInsets(); // obtem as bordas do JFrame
        int larguraTotal = largura + insets.left + insets.right;
        int alturaTotal = altura + insets.top + insets.bottom;
        setSize(larguraTotal, alturaTotal); // define o tamanho total do JFrame
    }

    // Mostra o estado atual do mapa.
    public void atualizarJanelaSimulacao() {
        visaoEstacionamento.preparePaint();

        for (int i = 0; i < estacionamento.getAltura(); i++) {
            for (int j = 0; j < estacionamento.getLargura(); j++) {
                java.util.List<Veiculo> veiculosNaPosicao = estacionamento.getVeiculoNaPosicao(i, j);

                for (Veiculo veiculo : veiculosNaPosicao) {
                    Localizacao localizacao = veiculo.getLocalizacaoAtual();
                    visaoEstacionamento.desenharImagem(localizacao.getX(), localizacao.getY(), veiculo.getImagem(),
                            veiculo.getEspacoOcupado());
                }

            }
        }

        visaoEstacionamento.repaint();
    }

    // Fornece uma visualizacao grafica do mapa. Esta eh
    // uma classe interna que define os componentes da GUI.
    // Ela contém alguns detalhes mais avancados sobre GUI
    // que voce pode ignorar para realizacao do seu trabalho.
    private class VisaoEstacionamento extends JPanel {

        private final int VIEW_SCALING_FACTOR = 10;
        private int larguraEstacionamento, alturaEstacionamento;
        private int xScale, yScale;
        private Dimension tamanho;
        private Graphics g;
        private Image imagemEstacionamento;
        private Image imagemFundo; // Imagem de fundo

        // Cria um novo componente VisaoEstacionamento.
        public VisaoEstacionamento(int largura, int altura) {
            larguraEstacionamento = largura;
            alturaEstacionamento = altura;

            setBackground(Color.white);
            tamanho = new Dimension(0, 0);
            ;

            // Carregar a imagem de fundo
            imagemFundo = new ImageIcon("src/imagens/estacionamento.png").getImage();
        }

        // Informa para o gerenciador GUI o tamanho.
        public Dimension getPreferredSize() {
            return new Dimension(larguraEstacionamento * VIEW_SCALING_FACTOR,
                    alturaEstacionamento * VIEW_SCALING_FACTOR);
        }

        // Prepara para um novo ciclo de exibição. Uma vez que o componente
        // pode ser redimensionado, calcula o "fator de escala" novamente.
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

        // Desenha a imagem para um determinado item.
        public void desenharImagem(int x, int y, Image image, Localizacao espacoOcupado) {
            int imageWidth = xScale * espacoOcupado.getX(); // 3x wider
            int imageHeight = yScale * espacoOcupado.getY(); // 3x taller]

            g.drawImage(image, x * xScale - (imageWidth / 2 - espacoOcupado.getX()),
                    y * yScale - (imageHeight / 2 - espacoOcupado.getY()), imageWidth, imageHeight, this);
        }

        // componente VisaoMapa precisa ser reexibido. Copia a
        // imagem interna para a tela.
        public void paintComponent(Graphics g) {
            if (imagemEstacionamento != null) {
                g.drawImage(imagemEstacionamento, 0, 0, null);
            }
        }
    }

    public void atualizarFaturamento(Double valor) {
        faturamentoLabel.setText(String.format("Faturamento: R$ %.2f", valor));
    }

    private void criarInterface() {
        faturamentoLabel = new JLabel("Faturamento: R$ 0.00");
        faturamentoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        faturamentoLabel.setForeground(Color.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.add(faturamentoLabel);

        Button botaoAumentarVelocidadeSimulacao = new Button("Aumentar velocidade simulacao");
        botaoAumentarVelocidadeSimulacao.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                simulacao.aumentarVelocidadeSimulacao();
            }
    
        });

        Button botaoDiminuirVelocidadeSimulacao = new Button("Diminuir velocidade simulacao");
        botaoDiminuirVelocidadeSimulacao.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                simulacao.diminuirVelocidadeSimulacao();
            }
    
        });

        Button botaoAumentarFluxoVeiculos = new Button("Aumentar fluxo veiculos");
        botaoAumentarFluxoVeiculos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                simulacao.aumentarFluxoVeiculos();
            }
    
        });

        Button botaoDiminuirFluxoVeiculos = new Button("Diminuir fluxo veiculos");
        botaoDiminuirFluxoVeiculos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                simulacao.diminuirFluxoVeiculos();
            }
    
        });

        topPanel.add(botaoAumentarVelocidadeSimulacao);
        topPanel.add(botaoDiminuirVelocidadeSimulacao);
        topPanel.add(botaoDiminuirFluxoVeiculos);
        topPanel.add(botaoAumentarFluxoVeiculos);

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
}
