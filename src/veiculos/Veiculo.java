package veiculos;
import java.awt.Image;
import javax.swing.ImageIcon;

import util.Localizacao;

public abstract class Veiculo {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;
    private String placa;
    private String cor;

    public Veiculo(Localizacao localizacao) {
        this.localizacaoAtual = localizacao;
        localizacaoDestino = null;
        // System.out.println(getClass().getResource("Imagens/veiculo.png"));
        imagem = new ImageIcon("src/Imagens/veiculo.png").getImage();
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }
    
    public Image getImagem() {
        return imagem;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }
    
    public void executarAcao(){
        Localizacao destino = getLocalizacaoDestino();
        if(destino != null){
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);
        }
    }

    public String getPlaca() {
        return placa;
    }

    public String getCor() {
        return cor;
    } 
}
