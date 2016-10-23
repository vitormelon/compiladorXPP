/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vitor
 */
public class Sintatico {

    private int indice = 0;
    private List<Token> listToken;
    private int max;

    public Sintatico(List<Token> listToken) throws FileNotFoundException, IOException {
        this.listToken = listToken;
        max = this.listToken.size();
    }

    public String expressaoId(int id) {
        List<Expressao> exp = new ArrayList<>();
        //EXP TERMINAIS
        exp.add(new Expressao(1, "digito"));
        exp.add(new Expressao(2, "variavel"));
        exp.add(new Expressao(3, "constante"));
        exp.add(new Expressao(4, "op_arit"));
        exp.add(new Expressao(5, "op_rel"));
        exp.add(new Expressao(6, "pontoVirgula"));
        exp.add(new Expressao(7, "op_atib"));
        exp.add(new Expressao(8, "a_chav"));
        exp.add(new Expressao(9, "f_chav"));
        exp.add(new Expressao(10, "a_par"));
        exp.add(new Expressao(11, "f_par"));
        exp.add(new Expressao(12, "SE"));
        exp.add(new Expressao(13, "SENAO"));
        exp.add(new Expressao(14, "ENQUANTO"));
        exp.add(new Expressao(15, "boleano"));

        //EXPS
        exp.add(new Expressao(0, "programa"));
        exp.add(new Expressao(50, "declaracao"));
        exp.add(new Expressao(51, "digiVar"));
        exp.add(new Expressao(52, "opDigiVar"));
        exp.add(new Expressao(53, "operacao"));
        exp.add(new Expressao(54, "atOpDigiVar"));
        exp.add(new Expressao(55, "atribuicao"));
        exp.add(new Expressao(56, "relacao"));
        exp.add(new Expressao(57, "conteudoBloco"));
        exp.add(new Expressao(58, "bloco"));
        exp.add(new Expressao(59, "conteudoCond"));
        exp.add(new Expressao(60, "condicional"));
        exp.add(new Expressao(61, "condsimples"));

        for (Expressao expressao : exp) {
            if (expressao.getId() == id) {
                return expressao.getPalavra();
            }
        }
        return "--------------------------------------!";


    }

    public void printASA(No arvore, int cont) {
        for (int i = 0; i < cont; i++) {
            System.out.print(" - ");
        }
        System.out.println(expressaoId(arvore.expressao));
        for (No prox : arvore.prox) {
            printASA(prox, cont + 1);
        }
        if (arvore.token != null) {
            for (int i = 0; i < cont; i++) {
                System.out.print(" - ");
            }
            System.out.print(" - ");

            System.out.println("<Token " + arvore.token.getId() + " " + arvore.token.getToken() + " " + arvore.token.getLin() + "," + arvore.token.getCol() + ">"); //imrpime o token
        }
    }

    public No analise() {
        No programa = new No(0, null);
        boolean cond;
        int idx;

        while (indice < max) {

            idx = indice;
            No operacao = operacao();
            No pontoVirgula = pontoVirgula();
            cond = operacao != null && pontoVirgula != null;
            if (cond) {
                programa.prox.add(operacao);
                programa.prox.add(pontoVirgula);
                continue;
            }
            indice = idx;

            No atribuicao = atribuicao();
            cond = atribuicao != null;
            if (cond) {
                programa.prox.add(atribuicao);
                continue;
            }
            indice = idx;

            No declaracao = declaracao();
            cond = declaracao != null;
            if (cond) {
                programa.prox.add(declaracao);
                continue;
            }
            indice = idx;

            No condicional = condicional();
            cond = condicional != null;
            if (cond) {
                programa.prox.add(condicional);
                continue;
            }
            indice = idx;

            No condicionalSimples = condicionalSimples();
            cond = condicionalSimples != null;
            if (cond) {
                programa.prox.add(condicionalSimples);
                continue;
            }
            indice = idx;

            No repeticao = repeticao();
            cond = repeticao != null;
            if (cond) {
                programa.prox.add(repeticao);
                continue;
            }
            indice = idx;

            No digiVar = digiVar();
            pontoVirgula = pontoVirgula();
            cond = digiVar != null && pontoVirgula != null;
            if (cond) {
                programa.prox.add(digiVar);
                continue;
            }
            indice = idx;

            this.erro(listToken.get(indice));

        }
        return programa;
    }

    public void erro(Token tok) {
        System.out.println("<ERRO SINTATICO Linha: " + tok.getLin() + " Coluna: " + tok.getCol() + ">"); //Erro  
        System.exit(1);
    }

    public boolean verIdTok(int id) {
        if (this.indice < this.max) {
            if (id == listToken.get(this.indice).getId()) {
                indice++;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public No digito() {
        int idx = indice;
        boolean cond = verIdTok(100) || verIdTok(101);
        if (cond) {
            No no = new No(1, listToken.get(idx));
            return no;
        }
        indice = idx;
        return null;
    }

    public No variavel() {
        int idx = indice;
        boolean cond = verIdTok(102);
        if (cond) {
            return new No(2, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No constante() {
        int idx = indice;
        boolean cond = verIdTok(120);
        if (cond) {
            return new No(3, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No op_arit() {
        int idx = indice;
        boolean cond = verIdTok(104) || verIdTok(105) || verIdTok(106) || verIdTok(107);
        if (cond) {
            return new No(4, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No op_rel() {
        int idx = indice;
        boolean cond = verIdTok(108) || verIdTok(109) || verIdTok(110) || verIdTok(111) || verIdTok(112) || verIdTok(113);
        if (cond) {
            return new No(5, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No pontoVirgula() {
        int idx = indice;
        boolean cond = verIdTok(119);
        if (cond) {
            return new No(6, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No op_atrib() {
        int idx = indice;
        boolean cond = verIdTok(114);
        if (cond) {
            return new No(7, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No a_chav() {
        int idx = indice;
        boolean cond = verIdTok(117);
        if (cond) {
            return new No(8, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No f_chav() {
        int idx = indice;
        boolean cond = verIdTok(118);
        if (cond) {
            return new No(9, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No a_par() {
        int idx = indice;
        boolean cond = verIdTok(115);
        if (cond) {
            return new No(10, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No f_par() {
        int idx = indice;
        boolean cond = verIdTok(116);
        if (cond) {
            return new No(11, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No SE() {
        int idx = indice;
        boolean cond = verIdTok(126);
        if (cond) {
            return new No(12, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No SENAO() {
        int idx = indice;
        boolean cond = verIdTok(127);
        if (cond) {
            return new No(13, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No ENQUANTO() {
        int idx = indice;
        boolean cond = verIdTok(129);
        if (cond) {
            return new No(14, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No boleano() {
        int idx = indice;
        boolean cond = verIdTok(131) || verIdTok(132);
        if (cond) {
            return new No(15, listToken.get(idx));
        }
        indice = idx;
        return null;
    }

    public No declaracao() {
        int idx = indice;
        No constante = constante();
        No variavel = variavel();
        No pontoVirgula = pontoVirgula();
        boolean cond = constante != null && variavel != null && pontoVirgula != null;
        if (cond) {
            No declaracao = new No(50, null);
            declaracao.prox.add(constante);
            declaracao.prox.add(variavel);
            declaracao.prox.add(pontoVirgula);
            return declaracao;
        }
        indice = idx;
        return null;
    }

    public No digiVar() {
        int idx = indice;
        No digito = digito();
        No variavel = variavel();
        boolean cond = digito != null;
        if (cond) {
            No digiVar = new No(51, null);
            digiVar.prox.add(digito);
            return digiVar;
        } else if (variavel != null) {
            No digiVar = new No(51, null);
            digiVar.prox.add(variavel);
            return digiVar;
        }
        indice = idx;
        return null;
    }

    public No opDigiVar() {
        int idx = indice;
        No op_arit = op_arit();
        No digiVar = digiVar();
        boolean cond = op_arit != null && digiVar != null;
        if (cond) {
            No opDigiVarR = opDigiVar();

            if (opDigiVarR != null) {
                No opDigiVar = new No(52, null);
                opDigiVar.prox.add(op_arit);
                opDigiVar.prox.add(digiVar);
                opDigiVar.prox.add(opDigiVarR);
                return opDigiVar;
            } else {
                No opDigiVar = new No(52, null);
                opDigiVar.prox.add(op_arit);
                opDigiVar.prox.add(digiVar);
                return opDigiVar;
            }
        }

        indice = idx;
        return null;
    }

    public No operacao() {
        int idx = indice;
        No digiVar = digiVar();
        No opDigiVar = opDigiVar();
        boolean cond = digiVar != null && opDigiVar != null;
        if (cond) {
            No operacao = new No(53, null);
            operacao.prox.add(digiVar);
            operacao.prox.add(opDigiVar);
            return operacao;
        }
        indice = idx;
        return null;
    }

    public No atOpDigiVar() {
        int idx = indice;
        No operacao = operacao();
        No digiVar = digiVar();
        boolean cond = operacao != null;
        if (cond) {
            No atOpDigiVar = new No(54, null);
            atOpDigiVar.prox.add(operacao);
            return atOpDigiVar;
        } else if (digiVar != null) {
            No atOpDigiVar = new No(54, null);
            atOpDigiVar.prox.add(digiVar);
            return atOpDigiVar;
        }
        indice = idx;
        return null;
    }

    public No atribuicao() {
        int idx = indice;
        No variavel = variavel();
        No op_atrib = op_atrib();
        No atOpDigiVar = atOpDigiVar();
        No pontoVirgula = pontoVirgula();
        boolean cond = variavel != null && op_atrib != null && atOpDigiVar != null && pontoVirgula != null;
        if (cond) {
            No atribuicao = new No(55, null);
            atribuicao.prox.add(variavel);
            atribuicao.prox.add(op_atrib);
            atribuicao.prox.add(atOpDigiVar);
            atribuicao.prox.add(pontoVirgula);
            return atribuicao;
        }
        indice = idx;
        variavel = variavel();
        op_atrib = op_atrib();
        No boleano = boleano();
        pontoVirgula = pontoVirgula();

        cond = variavel != null && op_atrib != null && boleano != null && pontoVirgula != null;
        if (cond) {
            No atribuicao = new No(55, null);
            atribuicao.prox.add(variavel);
            atribuicao.prox.add(op_atrib);
            atribuicao.prox.add(boleano);
            atribuicao.prox.add(pontoVirgula);
            return atribuicao;
        }

        indice = idx;
        return null;
    }

    public No relacao() {
        int idx = indice;
        No atOpDigiVar = atOpDigiVar();
        No op_rel = op_rel();
        No atOpDigiVar2 = atOpDigiVar();
        boolean cond = atOpDigiVar != null && op_rel != null && atOpDigiVar2 != null;
        if (cond) {
            No relacao = new No(56, null);
            relacao.prox.add(atOpDigiVar);
            relacao.prox.add(op_rel);
            relacao.prox.add(atOpDigiVar2);
            return relacao;
        }
        indice = idx;
        return null;
    }

    public No conteudoBloco() {
        int idx;
        boolean cond;
        No conteudoBloco = new No(57, null);
        while (this.indice < this.max && listToken.get(indice).getId() != 118) {
            idx = indice;
            No operacao = operacao();
            No pontoVirgula = pontoVirgula();
            cond = operacao != null && pontoVirgula != null;
            if (cond) {
                conteudoBloco.prox.add(operacao);
                conteudoBloco.prox.add(pontoVirgula);
                continue;
            }
            indice = idx;

            No atribuicao = atribuicao();
            cond = atribuicao != null;
            if (cond) {
                conteudoBloco.prox.add(atribuicao);
                continue;
            }
            indice = idx;

            No declaracao = declaracao();
            cond = declaracao != null;
            if (cond) {
                conteudoBloco.prox.add(declaracao);
                continue;
            }
            indice = idx;

            No condicional = condicional();
            cond = condicional != null;
            if (cond) {
                conteudoBloco.prox.add(condicional);
                continue;
            }
            indice = idx;

            No condicionalSimples = condicionalSimples();
            cond = condicionalSimples != null;
            if (cond) {
                conteudoBloco.prox.add(condicionalSimples);
                continue;
            }
            indice = idx;

            No repeticao = repeticao();
            cond = repeticao != null;
            if (cond) {
                conteudoBloco.prox.add(repeticao);
                continue;
            }
            indice = idx;

            No digiVar = digiVar();
            pontoVirgula = pontoVirgula();
            cond = digiVar != null && pontoVirgula != null;
            if (cond) {
                conteudoBloco.prox.add(digiVar);
                continue;
            }
            indice = idx;

            this.erro(listToken.get(indice));

        }
        return conteudoBloco;

    }

    public No bloco() {
        int idx = indice;
        No a_chav = a_chav();
        No conteudoBloco = conteudoBloco();
        No f_chav = f_chav();
        boolean cond = a_chav != null && conteudoBloco != null && f_chav != null;
        if (cond) {
            No bloco = new No(58, null);
            bloco.prox.add(a_chav);
            bloco.prox.add(conteudoBloco);
            bloco.prox.add(f_chav);
            return bloco;
        }
        indice = idx;
        return null;
    }

    public No conteudoCond() {
        int idx = indice;
        No relacao = relacao();
        No atOpDigiVar = atOpDigiVar();
        boolean cond = relacao != null;
        if (cond) {
            No conteudoCond = new No(59, null);
            conteudoCond.prox.add(relacao);
            return conteudoCond;
        } else if (atOpDigiVar != null) {
            No conteudoCond = new No(59, null);
            conteudoCond.prox.add(atOpDigiVar);
            return conteudoCond;
        }
        indice = idx;
        return null;
    }

    public No condicional() {
        int idx = indice;
        No condicionalSimples = condicionalSimples();
        if (condicionalSimples != null) {
            No SENAO = SENAO();
            No bloco = bloco();
            boolean cond = condicionalSimples != null && SENAO != null && bloco != null;
            if (cond) {
                No condicional = new No(60, null);
                condicional.prox.add(condicionalSimples);
                condicional.prox.add(bloco);
                return condicional;
            }
        }
        indice = idx;
        return null;
    }

    public No condicionalSimples() {
        int idx = indice;
        No SE = SE();
        if (SE != null) {
            No a_par = a_par();
            No conteudoCond = conteudoCond();
            No f_par = f_par();
            No bloco = bloco();
            boolean cond = SE != null && a_par != null && conteudoCond != null && f_par != null && bloco != null;
            if (cond) {
                No condicional = new No(61, null);
                condicional.prox.add(SE);
                condicional.prox.add(a_par);
                condicional.prox.add(conteudoCond);
                condicional.prox.add(f_par);
                condicional.prox.add(bloco);
                return condicional;
            }
        }
        indice = idx;
        return null;
    }

    public No repeticao() {
        int idx = indice;
        No ENQUANTO = ENQUANTO();
        if (ENQUANTO != null) {
            No a_par = a_par();
            No conteudoCond = conteudoCond();
            No f_par = f_par();
            No bloco = bloco();
            boolean cond = ENQUANTO != null && a_par != null && conteudoCond != null && f_par != null && bloco != null;
            if (cond) {
                No repeticao = new No(60, null);
                repeticao.prox.add(ENQUANTO);
                repeticao.prox.add(a_par);
                repeticao.prox.add(conteudoCond);
                repeticao.prox.add(f_par);
                repeticao.prox.add(bloco);
                return repeticao;
            }
        }
        indice = idx;
        return null;
    }
}
