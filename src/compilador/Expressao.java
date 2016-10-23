/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Vitor
 */
public class Expressao {
    private int id;
    private String palavra;

    public Expressao(int id, String palavra) {
        this.id = id;
        this.palavra = palavra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
    
}
