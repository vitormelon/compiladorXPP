/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Vitor
 */
public class Tradutor {

    public Tradutor() {
    }

    public void traduzir(List<Token> tokens, String nomeArq) throws FileNotFoundException, IOException {
        BufferedWriter out;
        out = new BufferedWriter(new FileWriter("C:\\compilador\\" + nomeArq));
        for (Token token : tokens) {
            switch (token.getId()) {
                case 120:
                    switch (token.getToken()) {
                        case "INT":
                            out.write("int ");
                            break;
                        case "REAL":
                            out.write("float ");
                            break;
                        case "BOOL":
                            out.write("Boolean ");
                            break;
                        case "STR":
                            out.write("String ");
                            break;
                        case "CARACT":
                            out.write("char ");
                            break;
                    }
                    break;
                case 126:
                    out.write("if");
                    break;
                case 127:
                    out.write("else");
                    break;
                case 129:
                    out.write("while");
                    break;
                case 131:
                    out.write("true");
                    break;
                case 132:
                    out.write("false");
                    break;
                case 119:
                    out.write(";");
                    out.write('\r');
                    out.write('\n');
                    break;
                case 117:
                    out.write("{");
                    out.write('\r');
                    out.write('\n');
                    break;
                default:
                    out.write(token.getToken());
            }
        }
        out.close();
    }
}
