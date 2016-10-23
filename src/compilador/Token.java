/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Vitor
 */
public class Token {
    
    private int id;
    private String token;
    private int lin;
    private int col;

    public Token(int id, String token, int lin, int col) {
        this.id = id;
        this.token = token;
        this.lin = lin;
        this.col = col;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLin() {
        return lin;
    }

    public void setLin(int lin) {
        this.lin = lin;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    
    
}
