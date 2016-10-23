/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vitor
 */
public class No {
    public int expressao;
    public List<No> prox;
    public Token token;

    public No(int expressao, Token token) {
        this.expressao = expressao;
        this.token = token;
        this.prox = new ArrayList<>();
    }
}
