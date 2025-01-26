package veiculos;
import java.awt.Image;
import java.util.Queue;
import java.util.Stack;

import util.Localizacao;

public abstract class Veiculo {
    private Localizacao localizacaoAtual;
    private Queue<Localizacao> caminho;
    private Image imagem;
    private String placa;
    private String cor;

    public Veiculo(Localizacao localizacao, Queue<Localizacao> caminho, Image imagem) {
        this.localizacaoAtual = localizacao;
        this.caminho = caminho;
        this.imagem = imagem;
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

    public boolean executarAcao() {
        if (caminho.size() == 0)
            return false;

        Localizacao proximaLocalizacao = caminho.poll();
        setLocalizacaoAtual(proximaLocalizacao);

        return true;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCor() {
        return cor;
    } 

    public abstract Localizacao espacoOcupado();

    @Override
    public String toString() {
        return "Veiculo [localizacaoAtual=" + localizacaoAtual + ", placa=" + placa + ", cor=" + cor + "]";
    }
    
    public abstract Number getValorEstacionamento();
}