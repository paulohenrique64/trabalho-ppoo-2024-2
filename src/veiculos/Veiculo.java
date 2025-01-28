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
 * Representa a entidate Veículo
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public abstract class Veiculo {
    private Localizacao localizacaoAtual;
    protected Queue<Localizacao> caminho;
    private Image imagemAtual;
    private ImagensVeiculo imagensVeiculo;
    private String placa;
    private int quantidadeRodas; 
    private StatusGPSVeiculo status;

    public Veiculo(String placa, int quantidadeRodas, Localizacao localizacao, Queue<Localizacao> caminho, ImagensVeiculo imagensVeiculo) {
        this.placa = placa;
        this.quantidadeRodas = quantidadeRodas;
        this.localizacaoAtual = localizacao;
        this.caminho = caminho;
        this.imagensVeiculo = imagensVeiculo;
        this.status = StatusGPSVeiculo.INDO_PARA_ENTRADA_ESTACIONAMENTO;
        this.imagemAtual = imagensVeiculo.getImagem(Direcao.CIMA); // imagem default
    }

    public int getQuantidadeRodas() {
        return quantidadeRodas;
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public Image getImagem() {
        return imagemAtual;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    public String getPlaca() {
        return placa;
    }

    public abstract Localizacao getEspacoOcupado();

    public Double calcularTaxaDanificacaoTerreno() {
        return 0.7;
    }

    public Localizacao getProximaLocalizacao() {
        return caminho.peek();
    }

    public boolean executarAcao() {
        if (caminho.size() == 0) // se o veiculo ja chegou ao seu destino
            return false;

        Localizacao localizacaoAnterior = getLocalizacaoAtual();
        Localizacao proximaLocalizacao = caminho.poll();

        // atualizar localizacao atual
        setLocalizacaoAtual(proximaLocalizacao);

        // atualizar a imagem do veiculo
        Direcao dir = determinarDirecao(localizacaoAnterior, proximaLocalizacao);
        imagemAtual = imagensVeiculo.getImagem(dir);

        return true;
    }

    protected static Direcao determinarDirecao(Localizacao localizacaoAtual, Localizacao proximaLocalizacao) {
        int variacaoDeX = proximaLocalizacao.getX() - localizacaoAtual.getX();
        int variacaoDeY = proximaLocalizacao.getY() - localizacaoAtual.getY();

        if (variacaoDeX < 0 && variacaoDeY == 0) return Direcao.ESQUERDA;
        if (variacaoDeX > 0 && variacaoDeY == 0) return Direcao.DIREITA;
        if (variacaoDeX == 0 && variacaoDeY < 0) return Direcao.CIMA;
        if (variacaoDeX == 0 && variacaoDeY > 0) return Direcao.BAIXO;

        if (variacaoDeX < 0 && variacaoDeY < 0) return Direcao.DIAGONAL_ESQUERDA_CIMA;
        if (variacaoDeX < 0 && variacaoDeY > 0) return Direcao.DIAGONAL_ESQUERDA_BAIXO;
        if (variacaoDeX > 0 && variacaoDeY < 0) return Direcao.DIAGONAL_DIREITA_CIMA;
        if (variacaoDeX > 0 && variacaoDeY > 0) return Direcao.DIAGONAL_DIREITA_BAIXO;

        return Direcao.CIMA; // posicao default
    }

    public List<Localizacao> getTrechoAFrente() {
        List<Localizacao> pedacoDoCaminhoAFrente = new ArrayList<>();
        Iterator<Localizacao> iterator = caminho.iterator();
        int cont = 0;
        
        while (iterator.hasNext() && cont < 10) {
            pedacoDoCaminhoAFrente.add(iterator.next());
            cont++;
        }

        return pedacoDoCaminhoAFrente;
    }

    public void setCaminho(Queue<Localizacao> caminho) {
        this.caminho = caminho;
    }

    public StatusGPSVeiculo getStatusGPSVeiculo() {
        return status;
    }

    public void setStatusGPSVeiculo(StatusGPSVeiculo status) {
        this.status = status;
    } 

    public void setImagem(Direcao direcao) {
        imagemAtual = imagensVeiculo.getImagem(direcao);
    }
}