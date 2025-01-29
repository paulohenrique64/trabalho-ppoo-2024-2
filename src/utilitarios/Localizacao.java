package utilitarios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Representa uma localização no mapa
 * 
 * @author David J. Barnes and Michael Kolling, Luiz Merschmann and Paulo Henrique Ribeiro Alves
 */
public class Localizacao {
    private int x;
    private int y;

    /**
     * Representa uma localização na cidade
     * 
     * @param x Coordenada x: deve ser maior ou igual a 0.
     * @param y Coordenada y: deve ser maior ou igual a 0.
     */
    public Localizacao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Verificacao de igualdade de conteudo de objetos do tipo Localizacao.
     * 
     * @return true: se a localizacao é igual.
     *         false: caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Localizacao)) {
            return false;
        } else {
            Localizacao outro = (Localizacao) obj;
            return x == outro.x && y == outro.y;
        }
    }

    /**
     * @return A representacao da localizacao.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Carrega as coordenadas do arquivo recebido como parâmetro e armazena em uma
     * fila,
     * representando o caminho a ser percorrido por um veículo.
     * 
     * @param caminhoArquivo Caminho do arquivo que contém as coordenadas.
     * @return Uma fila contendo as localizações extraídas do arquivo.
     */
    public static Queue<Localizacao> carregarCaminho(String caminhoArquivo) {
        Queue<Localizacao> caminho = new LinkedList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            String[] coordenadas;

            // Lendo as coordenadas do arquivo e armazenando na fila
            while ((linha = leitor.readLine()) != null) {
                coordenadas = linha.split(" ");

                if (coordenadas.length == 2) {
                    coordenadas = coordenadas[1].split("\"")[1].split(";");
                    // Invertendo os pontos x e y, pois nos arquivos dos caminhos
                    // os pontos x e y estao invertidos
                    int x = Integer.parseInt(coordenadas[1]);
                    int y = Integer.parseInt(coordenadas[0]);
                    caminho.add(new Localizacao(x, y)); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return caminho;
    }

}
