package veiculos;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import utilitarios.Direcao;
import utilitarios.ImagensVeiculo;
import utilitarios.Localizacao;
import utilitarios.StatusGPSVeiculo;

/**
 * Representa um veículo na simulação, e possui informações
 * sua localização, caminho, imagem atual e status do GPS. Esta classe serve
 * como
 * base para os tipos específicos de veículos que podem ser criados.
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public abstract class Veiculo {
    private Localizacao localizacaoAtual;
    protected Queue<Localizacao> caminho; // A fila de locais por onde o veículo passará.
    private ImagensVeiculo imagensVeiculo; // Contém o conjunto de imagens do veículo para diferentes direções.
    private Image imagemAtual;
    private String placa;
    private int quantidadeRodas;
    private StatusGPSVeiculo status; // O status atual do GPS do veículo.

    /**
     * Constrói um novo veículo com os parâmetros fornecidos.
     * 
     * @param placa           A placa do veículo.
     * @param quantidadeRodas A quantidade de rodas do veículo.
     * @param localizacao     A localização inicial do veículo.
     * @param caminho         A fila de locais que o veículo irá percorrer.
     * @param imagensVeiculo  O conjunto de imagens do veículo para diferentes
     *                        direções.
     */
    public Veiculo(String placa, int quantidadeRodas, Localizacao localizacao, Queue<Localizacao> caminho,
            ImagensVeiculo imagensVeiculo) {
        this.placa = placa;
        this.quantidadeRodas = quantidadeRodas;
        this.localizacaoAtual = localizacao;
        this.caminho = caminho;
        this.imagensVeiculo = imagensVeiculo;
        this.status = StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO;
        this.imagemAtual = imagensVeiculo.getImagem(Direcao.CIMA); // imagem default
    }

    /**
     * Constrói um novo veículo com os parâmetros fornecidos.
     * 
     * @param placa           A placa do veículo.
     * @param quantidadeRodas A quantidade de rodas do veículo.
     * @param imagensVeiculo  O conjunto de imagens do veículo para diferentes
     *                        direções.
     */
    public Veiculo(String placa, int quantidadeRodas, ImagensVeiculo imagensVeiculo) {
        this.placa = placa;
        this.quantidadeRodas = quantidadeRodas;
        // A localização default deve ser (100, 100), pois quando o veículo for instanciado pela primeira vez
        // antes de executar um passo e fazer o veículo andar,
        // será verificado se o veículo chegou ao seu destino, que por sinal existe mas ainda não foi percorrido,
        // logo, se o caminho ainda não foi percorrido, a localização atual ainda não foi setada
        // então inicialmente precisamos setar a localização atual aqui
        this.localizacaoAtual = new Localizacao(100, 100); 
        this.caminho = null;
        this.imagensVeiculo = imagensVeiculo;
        this.status = StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO;
        this.imagemAtual = imagensVeiculo.getImagem(Direcao.CIMA); // imagem default
    }

    /**
     * Retorna a quantidade de rodas do veículo.
     * 
     * @return A quantidade de rodas do veículo.
     */
    public int getQuantidadeRodas() {
        return quantidadeRodas;
    }

    /**
     * Retorna a localização atual do veículo.
     * 
     * @return A localização atual do veículo.
     */
    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    /**
     * Retorna a imagem atual do veículo.
     * 
     * @return A imagem atual do veículo.
     */
    public Image getImagem() {
        return imagemAtual;
    }

    /**
     * Retorna a placa do veículo.
     * 
     * @return A placa do veículo.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Método abstrato que retorna o espaço ocupado pelo veículo.
     * Deve ser implementado pelas subclasses.
     * 
     * @return O espaço ocupado pelo veículo.
     */
    public abstract Localizacao getEspacoOcupado();

    /**
     * Calcula a taxa de dano que o veículo causaria ao terreno.
     * 
     * @return A taxa de dano do veículo.
     */
    public Double calcularTaxaDanificacaoTerreno() {
        return 0.7;
    }

    /**
     * Retorna a próxima localização que o veículo deverá alcançar.
     * 
     * @return A próxima localização no caminho do veículo.
     */
    public Localizacao getProximaLocalizacao() {
        if (caminho == null)
            return null;

        return caminho.peek();
    }

    /**
     * Executa uma ação para mover o veículo para a próxima localização no caminho.
     * Se o veículo já chegou ao destino, retorna false.
     * 
     * @return True se a ação foi executada com sucesso (o veículo se moveu);
     *         False caso contrário (veículo chegou ao destino).
     */
    public boolean executarAcao() {
        if (caminho.size() == 0) // se o veiculo ja chegou ao seu destino
            return false;

        Localizacao localizacaoAnterior = getLocalizacaoAtual();
        Localizacao proximaLocalizacao = caminho.poll();

        // atualizar localizacao atual
        localizacaoAtual = proximaLocalizacao;

        // atualizar a imagem do veiculo
        Direcao dir = determinarDirecao(localizacaoAnterior, proximaLocalizacao);
        imagemAtual = imagensVeiculo.getImagem(dir);

        return true;
    }

    /**
     * Determina a direção que o veículo deve seguir com base nas mudanças de
     * posição entre duas localizações.
     * 
     * @param localizacaoAtual   A localização atual do veículo.
     * @param proximaLocalizacao A próxima localização do veículo.
     * @return A direção que o veículo deve seguir.
     */
    protected static Direcao determinarDirecao(Localizacao localizacaoAtual, Localizacao proximaLocalizacao) {
        int variacaoDeX = proximaLocalizacao.getX() - localizacaoAtual.getX();
        int variacaoDeY = proximaLocalizacao.getY() - localizacaoAtual.getY();

        if (variacaoDeX < 0 && variacaoDeY == 0)
            return Direcao.ESQUERDA;
        if (variacaoDeX > 0 && variacaoDeY == 0)
            return Direcao.DIREITA;
        if (variacaoDeX == 0 && variacaoDeY < 0)
            return Direcao.CIMA;
        if (variacaoDeX == 0 && variacaoDeY > 0)
            return Direcao.BAIXO;

        if (variacaoDeX < 0 && variacaoDeY < 0)
            return Direcao.DIAGONAL_ESQUERDA_CIMA;
        if (variacaoDeX < 0 && variacaoDeY > 0)
            return Direcao.DIAGONAL_ESQUERDA_BAIXO;
        if (variacaoDeX > 0 && variacaoDeY < 0)
            return Direcao.DIAGONAL_DIREITA_CIMA;
        if (variacaoDeX > 0 && variacaoDeY > 0)
            return Direcao.DIAGONAL_DIREITA_BAIXO;

        return Direcao.CIMA; // posicao default
    }

    /**
     * Retorna um pedaço do caminho à frente do veículo, contendo no máximo 10
     * localizações.
     * 
     * @return Uma lista com até 10 localizações a frente do veículo.
     */
    public List<Localizacao> getTrechoAFrente() {
        List<Localizacao> pedacoDoCaminhoAFrente = new ArrayList<>();
        Iterator<Localizacao> iterator = caminho.iterator();
        int cont = 0;

        while (iterator.hasNext() && cont < 10) {
            Localizacao pedacoCaminho = iterator.next();
            pedacoDoCaminhoAFrente.add(pedacoCaminho);
            cont++;
        }

        return pedacoDoCaminhoAFrente;
    }

    /**
     * Atualiza o caminho do veículo.
     * 
     * @param caminho A nova fila de localizações do veículo.
     */
    public void setCaminho(Queue<Localizacao> caminho) {
        this.caminho = caminho;
    }

    /**
     * Retorna o status GPS atual do veículo.
     * 
     * @return O status GPS do veículo.
     */
    public StatusGPSVeiculo getStatusGPSVeiculo() {
        return status;
    }

    /**
     * Atualiza o status GPS do veículo.
     * 
     * @param status O novo status GPS do veículo.
     */
    public void setStatusGPSVeiculo(StatusGPSVeiculo status) {
        this.status = status;
    }

    /**
     * Atualiza a imagem do veículo com base na direção fornecida.
     * 
     * @param direcao A direção para a qual o veículo deve ser orientado.
     */
    public void setImagem(Direcao direcao) {
        imagemAtual = imagensVeiculo.getImagem(direcao);
    }
}