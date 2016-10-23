/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Vitor
 */
public class Semantico {

    private TabSim TS = new TabSim();

    public void erro(Token tok) {
        System.out.println("<ERRO SEMÂNTICO Linha: " + tok.getLin() + " Coluna: " + tok.getCol() + ">"); //Erro  
        System.exit(1);
    }

    public void analise(No arvore) {

        if (arvore.expressao == 50) { // Testa se uma variavel foi declarada mais de uma vez senao ela armazena na TS
            Token variavel = arvore.prox.get(1).token;
            String busca = TS.ASA.get(variavel.getToken());
            if (busca != null) {
                erro(variavel); //ERRO VARIAVEL JA DECLARADA, NAO PODE SER DECLARADA 2 X
            } else {
                Token tipo = arvore.prox.get(0).token;
                TS.ASA.put(variavel.getToken(), tipo.getToken());
            }

        } else if (arvore.expressao == 2) { // Testa se uma variavel que esta sendo utilizada ja foi declarada.
            Token variavel = arvore.token;
            String busca = TS.ASA.get(variavel.getToken());

            if (busca == null) {
                System.out.println(variavel.getToken());
                erro(variavel); //ERRO VARIAVEL NAO DECLARADA
            }
        } else if (arvore.expressao == 55) {
            String tipoObj = TS.ASA.get(arvore.prox.get(0).token.getToken());
            if (arvore.prox.get(2).expressao == 15) {
                int id = arvore.prox.get(2).token.getId();
                if (id != 132 && id != 131) {
                    erro(arvore.prox.get(2).token);
                }

            } else {
                No digiVar = arvore.prox.get(2).prox.get(0);
                if (digiVar.expressao == 51) {
                    No var = digiVar.prox.get(0);
                    if (var.expressao == 2) {
                        String tipoVar = TS.ASA.get(var.token.getToken());
                        if (tipoVar == null ? tipoObj != null : !tipoVar.equals(tipoObj)) {
                            erro(arvore.prox.get(0).token);
                        }
                    } else {
                        if (var.token.getId() == 101) {
                            if (!"REAL".equals(tipoObj)) {
                                erro(arvore.prox.get(0).token);
                            }
                        } else if (!"INT".equals(tipoObj)) {
                            erro(arvore.prox.get(0).token);
                        }
                    }
                } else {
                    String tipoOp = operacao(arvore.prox.get(2));
                    if (tipoObj == null ? tipoOp != null : !tipoObj.equals(tipoOp)) {
                        erro(arvore.prox.get(0).token);
                    }
                }
            }
        } else if (arvore.expressao == 57) { //nao é permitido fazer declarações dentro de laços de repeticao e condicionais
            bloco(arvore);
        }

        for (No prox : arvore.prox) {
            analise(prox);
        }
    }

    public void bloco(No arv) {
        if (arv.expressao == 50) {
            erro(arv.prox.get(0).token);
        }
        for (No prox : arv.prox) {
            bloco(prox);
        }
    }

    public String operacao(No arvore) {
        if (arvore.token != null) {
            Token variavel = arvore.token;
            if (variavel.getId() == 102) {
                String busca = TS.ASA.get(variavel.getToken());
                if (busca != null) {
                    switch (busca) {
                        case "REAL":
                            return "REAL";
                        case "STR":
                        case "BOOL":
                            erro(variavel);
                            break;
                    }
                }
            } else if (variavel.getId() == 101) {
                return "REAL";
            }
        }

        for (No prox : arvore.prox) {
            String op = operacao(prox);
            if (op != "INT") {
                return op;
            }
        }
        return "INT";
    }
}
