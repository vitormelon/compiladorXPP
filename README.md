# compiladorXPP - Compilador para a linguagem X


∙ Nome do software desenvolvido: 
	
	- XXP.
	
	
∙ Descrição da Linguagem X 
	
	- A linguagem X basea-se num pseudocódigo, também conhecida como português estruturado, que são sequencias pré-definidas de instruções, ou seja, seguem um padrão de intruções que pode ser intendida por qualquer programador, independente da linguagem utilizada.


∙ Descrição da linguagem de destino 
	
	- A linguagem destino é a linguagem JAVA.


∙ Descrição da gramática 
	A gramática da linguagem X constitui das seguintes regras:
	-> Regras léxicas e sintáticas
	
	  <variavel> := a...z
	  <digito> := 0...9 | 0...9 , 0...9
	  <digiVar> := <digito> | <variavel>
	  <opArit> := + | -| * | /
	  <opDigiVar> := <opArit><digiVar><opDigivar> | <opArit><digiVar>
	  <operacao> := <digiVar><opDigivar>
	  <atOpDigiVar> := <operacao> | <digiVar>
	  <op_rel> := > | < | >= | <= | == | !=	  
	  <relacao> := <atOpDigiVar><op_rel><atOpDigiVar>	  
	  <atribuicao> := <variavel>< = ><atOpDigiVar>< ; >	  
	  <declaracao> := <P_CONST><variavel>< ; >
	  <conteudoCond> := <relacao> | <atOpDigiVar>
	  <condicionalSimples> := <SE>< (><conteudoCond>< ) ><bloco>
	  <condicional> := <SE>< (><conteudoCond>< ) ><bloco><SENAO><bloco>
	  <repeticao> := <ENQUANTO>< (><conteudoCond>< ) ><bloco>
	  <conteudoBloco> := <operacao><conteudoBloco> | <atribuicao><conteudoBloco> | <declaracao><conteudoBloco> | <condicional>		  <conteudoBloco> | <condicionalSimples><conteudoBloco> | <repeticao><conteudoBloco> | <digiVar><conteudoBloco>
	  <bloco> := < { ><conteudoBloco>< } >
	  <constante> := INT|REAL|BOOL|LIST|STR|CARACT
	  <pontoVirgula> :=  <;>
	  <op_atrib> :=  <=>
	  <a_chav> := <{>
	  <f_chav> := <}>
	  <a_par> := <(>
	  <f_par> := <)>
	  <booleano> := <VERDADEIRO> | <FALSO>
	  <SE> := <SE>
	  <SENAO> := <SENAO>
	  <ENQUANTO> := <ENQUANTO> .
	  
	  
	-> Regras semânticas
	  	-Uma variavel não pode ser declarada duas vezes.
	  	-Toda variavel tem de ser de ser declarada antes de ser utilizadas.
	  	-Toda variavel declarada como inteira deve receber dados inteiros.
	  	-Toda variavel declarada como real deve receber dados reais, lembrando que um dado real pode ser do tipo: real + inteiro.
	  	-Uma variavel não pode ser declarada dentro de uma estrutura de repetição ou de condição.
	  	-Toda variavel declarada como booleana deve receber VERDADEIRO ou FALSO.




∙ Descrição do funcionamento do software
	
	O projeto do software foi desenvolvido na plataforma JAVA com nome compilador, a classe principal a ser executada é a compilador.java, quando executado o software aparecerá uma janela onde será colocado o nome do exemplo a ser traduzido, por exemplo: exemplo.txt, logo após a confirmação do nome do código o programa irá traduzir a linguagem "X" para a linguagem JAVA e criará um arquivo do tipo .obj com o mesmo nome do arquivo da linguagem "X", esse arquivo terá o código da linguagem destino.
