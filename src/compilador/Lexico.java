package compilador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Vitor
 */
public class Lexico {

    private int lin = 1;
    private int col = 1;

    public void erro(String acc, int lin, int col) {
        System.out.println("<ERRO LEXICO Linha: " + lin + " Coluna: " + col + ">"); //Erro  
        System.exit(1);
    }

    public char getChar(FileReader arq) throws IOException {
        char c;
        c = (char) arq.read();

        if (c == '\n') {
            this.lin++;
            this.col = 0;
        } else {
            this.col++;
        }

        return c;
    }

    public int reservada(String palavra) {
        int id = 0;
        List<Reservada> lista = new ArrayList<>();
        lista.add(new Reservada(120, "INT"));
        lista.add(new Reservada(120, "REAL"));
        lista.add(new Reservada(120, "BOOL"));
        lista.add(new Reservada(120, "LIST"));
        lista.add(new Reservada(120, "STR"));
        lista.add(new Reservada(120, "CARACTERE"));
        lista.add(new Reservada(126, "SE"));
        lista.add(new Reservada(127, "SENAO"));
        lista.add(new Reservada(128, "SENAOSE"));
        lista.add(new Reservada(129, "ENQUANTO"));
        lista.add(new Reservada(130, "PARA"));
        lista.add(new Reservada(131, "VERDADEIRO"));
        lista.add(new Reservada(130, "FALSO"));

        for (Reservada reservada : lista) {
            if (reservada.getPalavra().equals(palavra)) {
                id = reservada.getId();
            }
        }

        return id;
    }

    public List<Token> tokens(String nomeArq) throws FileNotFoundException, IOException {
        File file = new File("C:\\compilador\\"+nomeArq);
        FileReader arq = new FileReader(file);
        List<Token> listTok = new ArrayList<>();
        Token tok;
        char c;
        int state, lin, col, res;

        c = getChar(arq);  //pega o primeiro caractere

        while (arq.ready()) { //not EOF

            state = 0;
            String acc = "";

            if (c == ' ' || c == '\r' || c == '\n' || c == '\t') { // consome espaço \r \n \t
                c = getChar(arq);
                continue;
            } else if (Character.isDigit(c)) { //verifica se é numero
                state = 1;
            } else if (Character.isLetter(c)) { // verifica se é letra
                state = 2;
            } else if (c == '+' || c == '-' || c == '/' || c == '*') { //verifica operadores 
                state = 3;
            } else if (c == '<' || c == '!' || c == '>' || c == '=') { // operador relacional
                state = 4;
            } else if (c == '{' || c == '}' || c == '(' || c == ')') { //delimitadores
                state = 5;
            } else if (c == ';') { // fim da linha
                state = 6;
            } else if (c == '@') { // Comentario
                state = 7;
            } else {
                state = 8; // caractere especial ERRO
            }

            lin = this.lin; // Salva a posição inicial do "token"
            col = this.col;
            int id = 0;
            switch (state) {
                case 1: //digito
                    acc += c; //acumula o token
                    while (arq.ready()) { //nao for final do arquivo
                        c = getChar(arq);
                        if (c == ',') { // verifica se é um float
                            acc += c;
                            state = 11;
                            break;
                        } else if (Character.isDigit(c)) {
                            acc += c;
                        } else if (Character.isLetter(c)) {
                            erro(acc, lin, col);
                        } else {
                            id = 100;
//                            System.out.println("<ID " + id + " " + acc + " " + lin + "," + col + ">"); //imrpime o token
                            tok = new Token(id, acc, lin, col);
                            listTok.add(tok);
                            break;
                        }
                    }
                    if (state == 11) {
                        while (arq.ready()) { //nao for final do arquivo
                            c = getChar(arq);
                            if (Character.isDigit(c)) {
                                acc += c;
                            } else if (Character.isLetter(c) || c == ',') {
                                erro(acc, lin, col);
                            } else {
                                id = 101;
//                                System.out.println("<ID " + id + " " + acc + " " + lin + "," + col + ">"); //imrpime o token
                                tok = new Token(id, acc, lin, col);
                                listTok.add(tok);
                                break;
                            }
                        }
                    }
                    break;
                case 2: //Caractere
                    acc += c; //acumula o token
                    while (arq.ready()) { //nao for final do arquivo
                        c = getChar(arq);
                        if (!Character.isLetter(c) & !Character.isDigit(c)) {
                            res = reservada(acc);
                            if (res > 0) {
                                id = res;
//                                System.out.println("<ID " + id + " " + acc + " " + lin + "," + col + ">"); //imrpime o token
                                tok = new Token(id, acc, lin, col);
                                listTok.add(tok);
                                break;
                            }
                            id = 102;
//                            System.out.println("<ID " + id + " " + acc + " " + lin + "," + col + ">"); //imrpime o token
                            tok = new Token(id, acc, lin, col);
                            listTok.add(tok);
                            break;
                        } else {
                            acc += c;
                        }
                    }

                    break;
                case 3: // operador relacional
                    acc += c;
                    if (c == '+') {
                        id = 104;
                    } else if (c == '-') {
                        id = 105;
                    } else if (c == '*') {
                        id = 106;
                    } else {
                        id = 107;
                    }
//                    System.out.println("<ID " + id + " " + acc + " " + lin + "," + col + ">"); //imrpime o token
                    c = getChar(arq);  //pega o primeiro caractere
                    tok = new Token(id, acc, lin, col);
                    listTok.add(tok);

                    break;
                case 4: //Operador relacional
                    acc += c;
                    if (c == '>') {
                        c = getChar(arq);
                        if (c == '=') {
                            acc += c;
                            id = 110;
                            c = getChar(arq);
                        } else {
                            id = 108;
                        }
                    } else if (c == '<') {
                        c = getChar(arq);
                        if (c == '=') {
                            acc += c;
                            id = 111;
                            c = getChar(arq);
                        } else {
                            id = 109;
                        }
                    } else if (c == '=') {
                        c = getChar(arq);
                        if (c == '=') {
                            acc += c;
                            id = 112;
                            c = getChar(arq);
                        } else {
                            id = 114;
                        }
                    } else if (c == '!') {
                        c = getChar(arq);
                        if (c == '=') {
                            acc += c;
                            id = 113;
                            c = getChar(arq);
                        } else {
                            erro(acc, lin, col);
                        }
                    }
//                    System.out.println("<ID " + id + " " + acc + " " + lin + "," + col + ">"); //imrpime o token
                    tok = new Token(id, acc, lin, col);
                    listTok.add(tok);
                    break;
                case 5:
                    acc += c;
                    if (c == '(') {
                        id = 115;
                    } else if (c == ')') {
                        id = 116;
                    } else if (c == '{') {
                        id = 117;
                    } else if (c == '}') {
                        id = 118;
                    }
//                    System.out.println("<ID " + id + " " + acc + " " + lin + "," + col + ">"); //imrpime o token
                    c = getChar(arq);

                    tok = new Token(id, acc, lin, col);
                    listTok.add(tok);
                    break;
                case 6:
                    acc += c;
                    id = 119;
//                    System.out.println("<ID " + id + " " + acc + " " + lin + "," + col + ">"); //imrpime o token

                    c = getChar(arq);

                    tok = new Token(id, acc, lin, col);
                    listTok.add(tok);
                    break;
                case 7:
                    while (c != '\n') {
                        c = getChar(arq);
                    }
                    break;
                case 8:
                    acc += c;
                    erro(acc, lin, col);

                    break;
            }
        }
        return listTok;
    }
}
