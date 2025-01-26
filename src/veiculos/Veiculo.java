package veiculos;

import java.awt.Image;
import java.util.Queue;

import javax.swing.ImageIcon;

import util.Localizacao;

/**
 * Representa a entidate Veículo
 * 
 * @author Paulo Henrique Ribeiro Alves and Kauê Oliveira Silva
 */
public abstract class Veiculo {
    private Localizacao localizacaoAtual;
    protected Queue<Localizacao> caminho;
    private Image imagem;
    private String placa;
    private String cor;

    public Veiculo(String placa, Localizacao localizacao, Queue<Localizacao> caminho) {
        this.localizacaoAtual = localizacao;
        this.caminho = caminho;
        this.placa = placa;

        // adicionando imagem default
        String tipoVeiculoInstanciado = this.getClass().getSimpleName();
        String enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-cima.png";
        this.imagem = new ImageIcon(enderecoImagem.toLowerCase()).getImage();
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public Image getImagem() {
        return imagem;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCor() {
        return cor;
    }

    public abstract Localizacao getEspacoOcupado();

    @Override
    public String toString() {
        return "Veiculo [localizacaoAtual=" + localizacaoAtual + ", placa=" + placa + ", cor=" + cor + "]";
    }

    public Localizacao getProximaLocalizacao() {
        return caminho.peek();
    }

    public void setImagem(Image imagem) {
        this.imagem = imagem;
    }

    public boolean executarAcao() {
        // se o veiculo ja chegou ao seu destino
        if (caminho.size() == 0)
            return false;

        // variaveis auxiliares para atualizar a imagem do veiculo com base na direcao que esta indo
        String tipoVeiculoInstanciado = this.getClass().getSimpleName();
        String enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-cima.png"; // image default

        Localizacao localizacaoAnterior = getLocalizacaoAtual();
        Localizacao proximaLocalizacao = caminho.poll();

        // atualizar localizacao atual
        setLocalizacaoAtual(proximaLocalizacao);

        String direcao = determinarDirecao(localizacaoAnterior, proximaLocalizacao);

        switch (direcao) {
            case "direita":
                enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-direita.png";
                break;

            case "esquerda":
                enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-esquerda.png";
                break;

            case "cima":
                enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-cima.png";
                break;

            case "baixo":
                enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-baixo.png";
                break;

            case "diagonal-esquerda-cima":
                enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-diagonal-esquerda-cima.png";
                break;

            case "diagonal-esquerda-baixo":
                enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-diagonal-esquerda-baixo.png";
                break;

            case "diagonal-direita-cima":
                enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-diagonal-direita-cima.png";
                break;

            case "diagonal-direita-baixo":
                enderecoImagem = "src/imagens/" + tipoVeiculoInstanciado + "/" + tipoVeiculoInstanciado + "-diagonal-direita-baixo.png";
                break;

            default:
                break;
        }

        setImagem(new ImageIcon(enderecoImagem.toLowerCase()).getImage());

        return true;
    }

    protected static String determinarDirecao(Localizacao localizacaoAtual, Localizacao proximaLocalizacao) {
        int variacaoDeX = proximaLocalizacao.getX() - localizacaoAtual.getX();
        int variacaoDeY = proximaLocalizacao.getY() - localizacaoAtual.getY();

        if (variacaoDeX < 0 && variacaoDeY == 0) return "esquerda";
        if (variacaoDeX > 0 && variacaoDeY == 0) return "direita";
        if (variacaoDeX == 0 && variacaoDeY < 0) return "cima";
        if (variacaoDeX == 0 && variacaoDeY > 0) return "baixo";

        if (variacaoDeX < 0 && variacaoDeY < 0) return "diagonal-esquerda-cima";
        if (variacaoDeX < 0 && variacaoDeY > 0) return "diagonal-esquerda-baixo";
        if (variacaoDeX > 0 && variacaoDeY < 0) return "diagonal-direita-cima";
        if (variacaoDeX > 0 && variacaoDeY > 0) return "diagonal-direita-baixo";

        return "nenhum"; 
    }
}