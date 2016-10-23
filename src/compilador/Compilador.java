package compilador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Vitor & Eder
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {


        String nomeArq = JOptionPane.showInputDialog("Nome do arquivo: ");

        Lexico lex = new Lexico();
        List<Token> listToken = lex.tokens(nomeArq + ".txt");
        Sintatico sin = new Sintatico(listToken);
        No no = sin.analise();

        sin.printASA(no, 0);
        Semantico sem = new Semantico();

        sem.analise(no);
        Tradutor trad = new Tradutor();

        trad.traduzir(listToken,nomeArq + ".obj");
        int a = 2;
    }
}
