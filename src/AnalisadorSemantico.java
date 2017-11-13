/*Analisador Semantico
* Todos os métodos LABaseVisitor são sobreescritos
* para que o analisador caminhe na arvore de derivação e
* quando pertinente foram adicionados os erro semanticos.
* */
import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.ArrayList;


public class AnalisadorSemantico extends LABaseVisitor<String>  {
	
	private PilhaDeTabelas pilhaTabela;
    private SaidaParser sp;
    private Mensagens mensagem;

    //Variaveis Auxiliares
    private String tipoMaisVar;
    private boolean atr;
    private String nomeAtr;
    private String tipoAtr;
    private String VetorAtr;
    private int countPar;
    ArrayList<String> termos = new ArrayList<String>();
    ArrayList<String> funcoes = new ArrayList<String>();

    //Construtor padrão
    public AnalisadorSemantico(SaidaParser sp){
        this.sp = sp;
        mensagem = new Mensagens(sp);
        atr = false;
    }

    //Retorna o tipo da variavel
    public String getTipo(String tipo){
        if(tipo.matches("[+|-]?[0-9]*"))
            return "inteiro";
        else if(tipo.matches("[+|-]?[0-9]+[.]?[0-9]*"))
            return "real";
        else
            return "literal";
    }

    //Retorna o numero de parametros de uma função
    public int getNumeroParam(String param){
        String[] partes;
        for(String funcao : funcoes){
            partes = funcao.split(",");
            if(partes[0].equals(param))
                return Integer.parseInt(partes[1]);
        }
        return -1;
    }

    //Retorna o tipo de retorno da função
    public String getRetFuncao(String param) {
        String[] partes;
        for (String funcao : funcoes) {
            partes = funcao.split(",");
            if (partes[0].equals(param))
                return partes[2];
        }
        return null;
    }


    @Override
    public String visitPrograma(LAParser.ProgramaContext ctx){
        //programa : declaracoes 'algoritmo' corpo 'fim_algoritmo';
        if(ctx.children != null){
			//Cria escopo global
			pilhaTabela = new PilhaDeTabelas();
			pilhaTabela.empilhar(new TabelaDeSimbolos("global"));
                visitDeclaracoes(ctx.declaracoes());
                visitCorpo(ctx.corpo());
            pilhaTabela.desempilhar();
        }
        if(sp.isModificado()){
		    sp.println("Fim da compilacao");
        }
        return null;
    }

    @Override
    public String visitDeclaracoes(LAParser.DeclaracoesContext ctx) {
        //declaracoes : decl_local_global declaracoes | ;
        if(ctx.children != null){
            visitDecl_local_global(ctx.decl_local_global());
            visitDeclaracoes(ctx.declaracoes());
        }
        return null;
    }

    @Override
    public String visitDecl_local_global(LAParser.Decl_local_globalContext ctx) {
       //decl_local_global : declaracao_local | declaracao_global;
        if(ctx.declaracao_local() != null)
            visitDeclaracao_local(ctx.declaracao_local());
        else if (ctx.declaracao_global() != null)
            visitDeclaracao_global(ctx.declaracao_global());
        return null;
    }

    @Override
    public String visitDeclaracao_local(LAParser.Declaracao_localContext ctx) {
        /*declaracao_local  : 'declare' variavel
		          |'constante' IDENT ':' tipo_basico '=' valor_constante
		          |'tipo' IDENT ':' tipo;*/

		TabelaDeSimbolos escopoAtual = pilhaTabela.topo();
        if(ctx.getText().startsWith("declare"))
            visitVariavel(ctx.variavel());
        else if (ctx.getText().startsWith("constante")){
            if(!escopoAtual.existeSimbolo(ctx.IDENT().toString())){ //Se o identificador não existe
                visitTipo_basico(ctx.tipo_basico());
                //Insere o simbolo no escopo atual
                escopoAtual.adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo_basico().getText());
                visitValor_constante(ctx.valor_constante());
            }else //Senao erro de ja declarado
                mensagem.erro_Ident_Ja_Declarado(ctx.getStart().getLine(), ctx.IDENT().toString());
        }else if (ctx.getText().startsWith("tipo"))
            if(!escopoAtual.existeSimbolo(ctx.IDENT().toString())){ //Se o identificador não existe
                //Insere o simbolo no escopo atual
                escopoAtual.adicionarSimbolo(ctx.IDENT().getText(), "tipo");
                visitTipo(ctx.tipo());
            }else //Senao erro de ja declarado
                mensagem.erro_Ident_Ja_Declarado(ctx.getStart().getLine(),  ctx.IDENT().toString());
        return null;
    }

    @Override
    public String visitVariavel(LAParser.VariavelContext ctx) {
        //variavel : IDENT dimensao mais_var ':' tipo;
        if(ctx.children != null){
            TabelaDeSimbolos escopoAtual = pilhaTabela.topo();
            visitTipo(ctx.tipo());
            tipoMaisVar = ctx.tipo().getText(); //Variavel para usar no mais variavel
            //Se o identificador não existe
            if(!pilhaTabela.existeSimbolo(ctx.IDENT().toString())){
                //Insere o simbolo no escopo atual
                escopoAtual.adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo().getText());
            }else //Senao erro de ja declarado
                mensagem.erro_Ident_Ja_Declarado(ctx.getStart().getLine(), ctx.IDENT().toString());
            visitDimensao(ctx.dimensao());
            visitMais_var(ctx.mais_var());
        }
        return null;
    }

    @Override
    public String visitMais_var(LAParser.Mais_varContext ctx) {
        //mais_var : ',' IDENT dimensao mais_var | ;
        if(ctx.getText().startsWith(",")){
            TabelaDeSimbolos escopoAtual = pilhaTabela.topo();
             if(!pilhaTabela.existeSimbolo(ctx.IDENT().toString())){ //Se o identificador não existe
                escopoAtual.adicionarSimbolo(ctx.IDENT().toString(), tipoMaisVar); //Insere o simbolo no escopo atual
            }else //Senao erro de ja declarado
                mensagem.erro_Ident_Ja_Declarado(ctx.getStart().getLine(), ctx.IDENT().toString());
            visitDimensao(ctx.dimensao());
            visitMais_var(ctx.mais_var());
        }
        return null;
    }

    @Override
    public String visitIdentificador(LAParser.IdentificadorContext ctx) {
        //identificador : ponteiros_opcionais IDENT dimensao outros_ident;
        if(ctx.children != null){
            TabelaDeSimbolos escopoAtual = pilhaTabela.topo();
            visitPonteiros_opcionais(ctx.ponteiros_opcionais());
            visitDimensao(ctx.dimensao());
            visitOutros_ident(ctx.outros_ident());

            if(ctx.outros_ident().getText().startsWith(".")) { //Se for registro
                //Divide para String para pegar o identificador
                String[] aux = ctx.getText().split("\\.");
                if (!pilhaTabela.existeSimbolo(aux[1])) { //Se o identificador existe, erro de ja declarado
                    mensagem.erro_Ident_Nao_Declarado(ctx.getStart().getLine(), ctx.getText());
                }
            }else if(!escopoAtual.existeSimbolo(ctx.IDENT().toString())){ //Se o identificador existe, erro de ja declarado
               mensagem.erro_Ident_Nao_Declarado(ctx.getStart().getLine(), ctx.IDENT().toString());
           }

        }
        return null;
    }

    @Override
    public String visitPonteiros_opcionais(LAParser.Ponteiros_opcionaisContext ctx) {
        //ponteiros_opcionais : '^' ponteiros_opcionais | ;
        if(ctx.children != null)
            visitPonteiros_opcionais(ctx.ponteiros_opcionais());
        return null;
    }

    //Ignora os outros identificadores
    @Override
    public String visitOutros_ident(LAParser.Outros_identContext ctx) {
        //outros_ident: '.' identificador | ;
        //if(ctx.children != null)
          //  visitIdentificador(ctx.identificador());
        return null;
    }

    @Override
    public String visitDimensao(LAParser.DimensaoContext ctx) {
        //dimensao: '[' exp_aritmetica ']' dimensao | ;
        if(ctx.children != null) {
            visitExp_aritmetica(ctx.exp_aritmetica());
        }
        return null;
    }

    @Override
    public String visitTipo(LAParser.TipoContext ctx) {
        //tipo: registro | tipo_estendido;
        if(ctx.registro() != null)
            visitRegistro(ctx.registro());
        else
            visitTipo_estendido(ctx.tipo_estendido());
        return null;
    }

    @Override
    public String visitMais_ident(LAParser.Mais_identContext ctx) {
        //mais_ident: ',' identificador mais_ident | ;
        if(ctx.children != null){
            visitIdentificador(ctx.identificador());
            visitMais_ident(ctx.mais_ident());
        }
        return null;
    }

    @Override
    public String visitMais_variaveis(LAParser.Mais_variaveisContext ctx) {
        //mais_variaveis: variavel mais_variaveis | ;
        if(ctx.children != null){
            visitVariavel(ctx.variavel());
            visitMais_variaveis(ctx.mais_variaveis());
        }
        return null;
    }

    @Override
    public String visitTipo_basico(LAParser.Tipo_basicoContext ctx) {
        //tipo_basico: 'literal'|'inteiro'|'real'|'logico';
        String aux = ctx.getText();
        //Se for um tipo bssico valido
        if(aux.equals("literal") || aux.equals("inteiro") || aux.equals("real") || aux.equals("logico")) {
            return ctx.getText(); //retorna o tipo basico
        }else { //Senão erro de tipo não declarado
            mensagem.erro_Tipo_Nao_Declarado(ctx.getStart().getLine(), ctx.getText());
            return "erro";
        }
    }

    @Override
    public String visitTipo_basico_ident(LAParser.Tipo_basico_identContext ctx) {
        //tipo_basico_ident: tipo_basico | IDENT;
        if(ctx.tipo_basico() != null){
           return visitTipo_basico(ctx.tipo_basico());
        }else{
            //Se existe o identificado
            if(pilhaTabela.existeSimbolo(ctx.IDENT().toString()))
                return ctx.IDENT().toString(); //retorno o identificador
            else{ //Senão erro de tipo não declarado
                mensagem.erro_Tipo_Nao_Declarado(ctx.getStart().getLine(), ctx.IDENT().toString());
                return "erro";
            }
        }
    }

    @Override
    public String visitTipo_estendido(LAParser.Tipo_estendidoContext ctx) {
        //tipo_estendido: ponteiros_opcionais tipo_basico_ident;
        if(ctx.ponteiros_opcionais() != null){
            visitPonteiros_opcionais(ctx.ponteiros_opcionais());
        }
        return visitTipo_basico_ident(ctx.tipo_basico_ident());
    }

    @Override
    public String visitValor_constante(LAParser.Valor_constanteContext ctx) {
        //valor_constante: CADEIA | NUM_INT | NUM_REAL| 'verdadeiro' | 'falso';
        return ctx.getText();
    }

    @Override
    public String visitRegistro(LAParser.RegistroContext ctx) {
        //registro: 'registro' variavel mais_variaveis 'fim_registro';
        if(ctx.children != null){
            visitVariavel(ctx.variavel());
            visitMais_variaveis(ctx.mais_variaveis());
        }
        return null;
    }

    @Override
    public String visitDeclaracao_global(LAParser.Declaracao_globalContext ctx) {
       // declaracao_global: 'procedimento' IDENT '(' parametros_opcional ')' declaracoes_locais comandos 'fim_procedimento'
         //                |'funcao' IDENT '(' parametros_opcional ')' ':' tipo_estendido declaracoes_locais comandos 'fim_funcao';
        if(ctx.getText().startsWith("procedimento")){
            //Se procedimento não existe, adiciona o identificado na tabela
            if (!pilhaTabela.existeSimbolo(ctx.IDENT().toString())) {
                pilhaTabela.topo().adicionarSimbolo(ctx.IDENT().getText(), "procedimento");
            }
            //Cria escopo do procedimento
            pilhaTabela.empilhar(new TabelaDeSimbolos("procedimento "+ctx.IDENT().getText()));
                visitParametros_opcional(ctx.parametros_opcional());
                visitDeclaracoes_locais(ctx.declaracoes_locais());
                visitComandos(ctx.comandos());
            pilhaTabela.desempilhar();
        }else{
            //Se funcaoo não existe, adiciona o identificado na tabela
            if (!pilhaTabela.existeSimbolo(ctx.IDENT().toString())) {
                pilhaTabela.topo().adicionarSimbolo(ctx.IDENT().getText(), "funcao");
            }
            //Cria escopo da função
            pilhaTabela.empilhar(new TabelaDeSimbolos("funcao "+ctx.IDENT().getText()));
                countPar = 0; //Contador de numero de parametros
                visitParametros_opcional(ctx.parametros_opcional());
                //Adiciona a função na lista de funções
                funcoes.add(ctx.IDENT().getText() +","+ countPar +","+ctx.tipo_estendido().getText());
                visitTipo_estendido(ctx.tipo_estendido());
                visitDeclaracoes_locais(ctx.declaracoes_locais());
                visitComandos(ctx.comandos());
            pilhaTabela.desempilhar();
        }
        return null;
    }

    @Override
    public String visitParametros_opcional(LAParser.Parametros_opcionalContext ctx) {
        //parametros_opcional: parametro | ;
        if(ctx.children != null)
            visitParametro(ctx.parametro());
        return null;
    }

    @Override
    public String visitParametro(LAParser.ParametroContext ctx) {
        //parametro: var_opcional identificador mais_ident ':' tipo_estendido mais_parametros;
        if(ctx.children != null) {
            countPar++; //Para cada parametro soma um no contado
            TabelaDeSimbolos escopoAtual = pilhaTabela.topo();
            visitVar_opcional(ctx.var_opcional());
            //Se o identificador não existe , adiciona o identificador na tabela de simbolos atual
            if(!escopoAtual.existeSimbolo(ctx.identificador().IDENT().getText())){
                escopoAtual.adicionarSimbolo(ctx.identificador().IDENT().getText(), visitTipo_estendido(ctx.tipo_estendido()));
            }else //Senão erro de já declarado
                mensagem.erro_Ident_Ja_Declarado(ctx.getStart().getLine(), ctx.identificador().IDENT().getText());
            visitIdentificador(ctx.identificador());
            visitMais_ident(ctx.mais_ident());
            visitTipo_estendido(ctx.tipo_estendido());
            visitMais_parametros(ctx.mais_parametros());
        }
        return null;
    }

    @Override
    public String visitVar_opcional(LAParser.Var_opcionalContext ctx) {
        //var_opcional: 'var' | ;
        return ctx.getText();
    }

    @Override
    public String visitMais_parametros(LAParser.Mais_parametrosContext ctx) {
        //mais_parametros: ',' parametro | ;
        if(ctx.children != null)
            visitParametro(ctx.parametro());
        return null;
    }

    @Override
    public String visitDeclaracoes_locais(LAParser.Declaracoes_locaisContext ctx) {
        //declaracoes_locais : declaracao_local declaracoes_locais | ;
        if(ctx.children != null){
            visitDeclaracao_local(ctx.declaracao_local());
            visitDeclaracoes_locais(ctx.declaracoes_locais());
        }
        return null;
    }

    @Override
    public String visitCorpo(LAParser.CorpoContext ctx) {
        //corpo : declaracoes_locais comandos;
        if(ctx.children != null){
            visitDeclaracoes_locais(ctx.declaracoes_locais());
            visitComandos(ctx.comandos());
        }
        return null;
    }

    @Override
    public String visitComandos(LAParser.ComandosContext ctx) {
        //comandos : cmd comandos | ;
        if(ctx.children != null){
            visitCmd(ctx.cmd());
            visitComandos(ctx.comandos());
        }
        return null;
    }

    @Override
    public String visitCmd(LAParser.CmdContext ctx) {
        /*cmd : 'leia' '(' identificador mais_ident ')'
                | 'escreva' '(' expressao mais_expressao ')'
                | 'se' expressao 'entao' comandos senao_opcional 'fim_se'
                | 'caso' exp_aritmetica 'seja' selecao senao_opcional 'fim_caso'
                | 'para' IDENT '<-' exp_aritmetica 'ate' exp_aritmetica 'faca' comandos 'fim_para'
                | 'enquanto' expressao 'faca' comandos 'fim_enquanto'
                | 'faca' comandos 'ate' expressao
                | '^' IDENT outros_ident dimensao '<-' expressao
                | IDENT chamada_atribuicao
                | 'retorne' expressao;*/
        if(ctx.children!= null) {
            if (ctx.nomeCmd.equals("leia")) {
                visitIdentificador(ctx.identificador());
                visitMais_ident(ctx.mais_ident());
            } else if (ctx.nomeCmd.equals("escreva")) {
                visitExpressao(ctx.expressao());
                visitMais_expressao(ctx.mais_expressao());
            } else if (ctx.nomeCmd.equals("se")) {
                visitExpressao(ctx.expressao());
                visitComandos(ctx.comandos());
                visitSenao_opcional(ctx.senao_opcional());
            } else if (ctx.nomeCmd.equals("caso")) {
                visitExp_aritmetica(ctx.exp_aritmetica().get(0));
                visitSelecao(ctx.selecao());
                visitSenao_opcional(ctx.senao_opcional());
            } else if (ctx.nomeCmd.equals("para")) {
                //Se o identificador não existe, erro de tipo não declarado
                if (!pilhaTabela.topo().existeSimbolo(ctx.IDENT().toString())) {
                    mensagem.erro_Tipo_Nao_Declarado(ctx.getStart().getLine(), ctx.IDENT().toString());
                }
                visitExp_aritmetica(ctx.exp_aritmetica().get(0));
                visitExp_aritmetica(ctx.exp_aritmetica().get(1));
                visitComandos(ctx.comandos());
            } else if (ctx.nomeCmd.equals("enquanto")) {
                visitExpressao(ctx.expressao());
                visitComandos(ctx.comandos());
            } else if (ctx.nomeCmd.equals("faca")) {
                visitComandos(ctx.comandos());
                visitExpressao(ctx.expressao());
            } else if (ctx.nomeCmd.equals("^")) {
                //Se o identificador não existe, erro de tipo não declarado
                if (!pilhaTabela.topo().existeSimbolo(ctx.IDENT().toString())) {
                    mensagem.erro_Ident_Nao_Declarado(ctx.getStart().getLine(), ctx.IDENT().toString());
                }

                //Inicia a atribuição
                nomeAtr = "^" + ctx.IDENT().toString();
                tipoAtr = pilhaTabela.topo().getValorTipoSimbolo(ctx.IDENT().toString());
                atr = true;

                visitOutros_ident(ctx.outros_ident());
                visitDimensao(ctx.dimensao());
                visitExpressao(ctx.expressao());

                //Para cada termo da atribuição checa a compatibilidade
                for (String termo : termos) {
                    String valorTipoSimbolo = pilhaTabela.topo().getValorTipoSimbolo(termo);
                    if (valorTipoSimbolo == null) {
                        valorTipoSimbolo = "^" + getTipo(termo);
                    }
                    if (!tipoAtr.equals(valorTipoSimbolo)) {
                        mensagem.erro_Atribuicao_Nao_Compativel(ctx.getStart().getLine(), nomeAtr);
                    }
                }
                atr = false;
                termos.clear();
                //Fim atribuição
            } else if (ctx.nomeCmd.equals("IDENT")){
                //IDENT chamada_atribuicao
                //Inicia a atribuição
                nomeAtr = ctx.IDENT().toString();
                tipoAtr = pilhaTabela.topo().getValorTipoSimbolo(ctx.IDENT().toString());
                atr = true;

                //Divide a string no <-
                String[] partes = ctx.chamada_atribuicao().getText().split("<-");
                //Se for um vetor pega a parte [] para adicionar posteriormente se houver erro
                if (partes[0].startsWith("["))
                    VetorAtr = partes[0];
                else
                    VetorAtr = "";

                if (tipoAtr != null)
                    visitChamada_atribuicao(ctx.chamada_atribuicao());
                else if (!pilhaTabela.topo().existeSimbolo(nomeAtr))
                    mensagem.erro_Ident_Nao_Declarado(ctx.getStart().getLine(), nomeAtr);

                //Para cada termo da atribuição checa a compatibilidade
                for (String termo : termos) {
                    String valorTipoSimbolo = pilhaTabela.topo().getValorTipoSimbolo(termo);
                    if (valorTipoSimbolo == null) {
                        valorTipoSimbolo = getTipo(termo);
                    }
                    //Se for resgistro
                    if (tipoAtr.contains("registro")) {
                        String[] regPartes = ctx.chamada_atribuicao().getText().split("<-");
                        regPartes[0] = regPartes[0].substring(1);
                        if(!pilhaTabela.topo().getValorTipoSimbolo(regPartes[0]).equals(valorTipoSimbolo)){
                            mensagem.erro_Atribuicao_Nao_Compativel(ctx.getStart().getLine(), nomeAtr + "." + regPartes[0]);
                        }
                    }

                    if (!tipoAtr.equals(valorTipoSimbolo) && tipoAtr.equals("literal")) {
                        mensagem.erro_Atribuicao_Nao_Compativel(ctx.getStart().getLine(), nomeAtr);
                    }
                }

                atr = false;
                termos.clear();
                //Fim da atribuicao
            } else if (ctx.nomeCmd.equals("retorne")) {
                //Se for escopo globol ou procedimento erro de retonro nao permitido
                if (pilhaTabela.topo().toString().startsWith("Escopo: global") || pilhaTabela.topo().toString().startsWith("Escopo: procedimento"))
                    mensagem.erro_Retorno_Nao_Permitido(ctx.getStart().getLine());
                visitExpressao(ctx.expressao());
            }
        }
        return null;
    }

    @Override
    public String visitMais_expressao(LAParser.Mais_expressaoContext ctx) {
        //mais_expressao : ',' expressao mais_expressao | ;
        if(ctx.children != null){
            visitExpressao(ctx.expressao());
            visitMais_expressao(ctx.mais_expressao());
        }
        return null;
    }

    @Override
    public String visitSenao_opcional(LAParser.Senao_opcionalContext ctx) {
        //senao_opcional : 'senao' comandos | ;
        if(ctx.children != null)
            visitComandos(ctx.comandos());
        return null;
    }

    @Override
    public String visitChamada_atribuicao(LAParser.Chamada_atribuicaoContext ctx) {
        //chamada_atribuicao: '(' argumentos_opcional ')' | outros_ident dimensao '<-' expressao;
        if(ctx.getText().startsWith("(")) {
            visitArgumentos_opcional(ctx.argumentos_opcional());
        }
        else{
            visitOutros_ident(ctx.outros_ident());
            visitDimensao(ctx.dimensao());
            visitExpressao(ctx.expressao());
        }
        return null;
    }

    @Override
    public String visitArgumentos_opcional(LAParser.Argumentos_opcionalContext ctx) {
        //argumentos_opcional: expressao mais_expressao | ;
        if(ctx.children != null){
            visitExpressao(ctx.expressao());
            visitMais_expressao(ctx.mais_expressao());
        }
        return null;
    }

    @Override
    public String visitSelecao(LAParser.SelecaoContext ctx) {
        //selecao: constantes ':' comandos mais_selecao;
        if(ctx.children != null){
            visitConstantes(ctx.constantes());
            visitComandos(ctx.comandos());
            visitMais_selecao(ctx.mais_selecao());
        }
        return null;
    }

    @Override
    public String visitMais_selecao(LAParser.Mais_selecaoContext ctx) {
        //mais_selecao: selecao | ;
        if (ctx.children != null)
            visitSelecao(ctx.selecao());
        return null;
    }

    @Override
    public String visitConstantes(LAParser.ConstantesContext ctx) {
        //constantes: numero_intervalo mais_constantes;
        if (ctx.children != null) {
            visitNumero_intervalo(ctx.numero_intervalo());
            visitMais_constantes(ctx.mais_constantes());
        }
        return null;
    }

    @Override
    public String visitMais_constantes(LAParser.Mais_constantesContext ctx) {
        // mais_constantes: ',' constantes | ;
        if (ctx.children != null)
            visitConstantes(ctx.constantes());
        return null;
    }

    @Override
    public String visitNumero_intervalo(LAParser.Numero_intervaloContext ctx) {
        // numero_intervalo: op_unario NUM_INT intervalo_opcional;
        if (ctx.children != null) {
            visitOp_unario(ctx.op_unario());
            visitIntervalo_opcional(ctx.intervalo_opcional());
        }
        return null;
    }

    @Override
    public String visitIntervalo_opcional(LAParser.Intervalo_opcionalContext ctx) {
        // intervalo_opcional: '..' op_unario NUM_INT | ;
        if (ctx.children != null)
            visitOp_unario(ctx.op_unario());
        return null;
    }

    @Override
    public String visitOp_unario(LAParser.Op_unarioContext ctx) {
        //op_unario: '-' | ;
        return ctx.getText();
    }

    @Override
    public String visitExp_aritmetica(LAParser.Exp_aritmeticaContext ctx) {
        //exp_aritmetica: termo outros_termos;
        if(ctx.children != null){
            visitTermo(ctx.termo());
            visitOutros_termos(ctx.outros_termos());
        }
        return null;
    }

    @Override
    public String visitOp_multiplicacao(LAParser.Op_multiplicacaoContext ctx) {
        //op_multiplicacao: '*'| '/';
        return ctx.getText();
    }

    @Override
    public String visitOp_adicao(LAParser.Op_adicaoContext ctx) {
        //op_adicao : '+' | '-';
        return ctx.getText();
    }

    @Override
    public String visitTermo(LAParser.TermoContext ctx) {
        //termo : fator outros_fatores;
        if(ctx.children != null){
            if(atr){
                //Se não for literal,
                if((tipoAtr.equals("real") || tipoAtr.equals("inteiro") || tipoAtr.equals("logico"))&& ctx.getText().charAt(0) == '"'){
                    mensagem.erro_Atribuicao_Nao_Compativel(ctx.getStart().getLine(), nomeAtr + VetorAtr);
                }else if(!(tipoAtr.equals("literal") && ctx.getText().charAt(0) == '"'))
                    termos.add(ctx.getText()); //Adiciona na lista de termos
            }
            visitFator(ctx.fator());
            visitOutros_fatores(ctx.outros_fatores());
        }
        return null;
    }

    @Override
    public String visitOutros_termos(LAParser.Outros_termosContext ctx) {
        //outros_termos : op_adicao termo outros_termos | ;
        if (ctx.children != null) {
            visitOp_adicao(ctx.op_adicao());
            visitTermo(ctx.termo());
            visitOutros_termos(ctx.outros_termos());
        }
        return null;
    }

    @Override
    public String visitFator(LAParser.FatorContext ctx) {
        //fator : parcela outras_parcelas;
        if(ctx.children != null){
            visitParcela(ctx.parcela());
            visitOutras_parcelas(ctx.outras_parcelas());
        }
        return null;
    }

    @Override
    public String visitOutros_fatores(LAParser.Outros_fatoresContext ctx){
        //outros_fatores : op_multiplicacao fator outros_fatores | ;
        if (ctx.children != null) {
            visitOp_multiplicacao(ctx.op_multiplicacao());
            visitFator(ctx.fator());
            visitOutros_fatores(ctx.outros_fatores());
        }
        return null;
    }

    @Override
    public String visitParcela(LAParser.ParcelaContext ctx) {
        //parcela : op_unario parcela_unario | parcela_nao_unario;
        if (ctx.op_unario() != null) {
            visitOp_unario(ctx.op_unario());
            visitParcela_unario(ctx.parcela_unario());
        } else
            visitParcela_nao_unario(ctx.parcela_nao_unario());
        return null;
    }

    @Override
    public String visitParcela_unario(LAParser.Parcela_unarioContext ctx) {
        /*parcela_unario : '^' IDENT outros_ident dimensao
                | IDENT chamada_partes
                | NUM_INT
                | NUM_REAL
                | '(' expressao ')';*/
        TabelaDeSimbolos escopoAtual = pilhaTabela.topo();
        if (ctx.outros_ident() != null) {
            //Se identificador não existe, erro identificador não declarado
            if (!escopoAtual.existeSimbolo(ctx.IDENT().toString())) {
                mensagem.erro_Ident_Nao_Declarado(ctx.getStart().getLine(),ctx.IDENT().toString());
            }
            visitOutros_ident(ctx.outros_ident());
            visitDimensao(ctx.dimensao());
        } else if (ctx.chamada_partes() != null) {
            //Se identificador não existe, erro identificador não declado
            if (!escopoAtual.existeSimbolo(ctx.IDENT().toString())) {
                String aux = ctx.IDENT().toString();
                //Se registro só pega o comeco de dele
                if (ctx.chamada_partes().getText().startsWith("."))
                    aux += ctx.chamada_partes().getText();
                mensagem.erro_Ident_Nao_Declarado(ctx.getStart().getLine(),aux);
            }

            //Se atribuição de função ou procedimento
            if(ctx.chamada_partes().getText().startsWith("(")){
                String[] partes;
                String aux, chamada, funcaoTipo;
                int nParam;

                chamada = ctx.chamada_partes().getText();
                //Divide a string para pegar as partes da funcção ou procedimento
                aux = chamada.substring(1, chamada.length()-1);
                partes = aux.split(",");
                nParam = getNumeroParam(ctx.IDENT().toString());

                //Se o numero de parametros passado for diferente do declarado, erro incompatibilidade de parametros
                if(nParam != partes.length && nParam > 0){
                    mensagem.erro_Incopatibilidade_de_Parametros(ctx.getStart().getLine(),ctx.IDENT().toString());
                }else{ //Senão verifica cada funcção e checa a compatibilidade do retorno da função
                    funcaoTipo = getRetFuncao(ctx.IDENT().toString());
                    for(int i = 0; i < partes.length; i++){
                       if(pilhaTabela.topo().existeSimbolo(partes[i])){
                           if(!funcaoTipo.equals(pilhaTabela.topo().getValorTipoSimbolo(partes[i])) && !partes[i].contains("(") && !partes[i].contains("[") )
                               mensagem.erro_Incopatibilidade_de_Parametros(ctx.getStart().getLine(),ctx.IDENT().toString());
                       }
                    }
                }
            }
            visitChamada_partes(ctx.chamada_partes());

        } else if (ctx.expressao() != null) {
                visitExpressao(ctx.expressao());
        }
        return null;
    }

    @Override
    public String visitParcela_nao_unario(LAParser.Parcela_nao_unarioContext ctx) {
        //parcela_nao_unario : '&' IDENT outros_ident dimensao | CADEIA;
        if (ctx.outros_ident() != null) {
            TabelaDeSimbolos escopoAtual = pilhaTabela.topo();
            //Se o identificador não existe, erro de identificador não declarado
            if (!escopoAtual.existeSimbolo(ctx.IDENT().toString())) {
                mensagem.erro_Ident_Nao_Declarado(ctx.getStart().getLine(),ctx.IDENT().toString());
            }
            visitOutros_ident(ctx.outros_ident());
            visitDimensao(ctx.dimensao());
        }
        return null;
    }

    @Override
    public String visitOutras_parcelas(LAParser.Outras_parcelasContext ctx) {
        //outras_parcelas : '%' parcela outras_parcelas | ;
        if (ctx.children != null) {
            visitParcela(ctx.parcela());
            visitOutras_parcelas(ctx.outras_parcelas());
        }
        return null;
    }

    @Override
    public String visitChamada_partes(LAParser.Chamada_partesContext ctx) {
        // chamada_partes :  '(' expressao mais_expressao ')'  | outros_ident dimensao | ;
        if (ctx.expressao() != null) {
            visitExpressao(ctx.expressao());
            visitMais_expressao(ctx.mais_expressao());
        } else
        if (ctx.outros_ident() != null) {
            visitOutros_ident(ctx.outros_ident());
            visitDimensao(ctx.dimensao());
        }
        return null;
    }

    @Override
    public String visitExp_relacional(LAParser.Exp_relacionalContext ctx) {
        //exp_relacional : exp_aritmetica op_opcional;
        if(ctx.children != null){
            visitExp_aritmetica(ctx.exp_aritmetica());
            visitOp_opcional(ctx.op_opcional());
        }
        return null;
    }

    @Override
    public String visitOp_opcional(LAParser.Op_opcionalContext ctx) {
        //op_opcional : op_relacional exp_aritmetica | ;
        if (ctx.children != null) {
            visitOp_relacional(ctx.op_relacional());
            visitExp_aritmetica(ctx.exp_aritmetica());
        }
        return null;
    }

    @Override
    public String visitOp_relacional(LAParser.Op_relacionalContext ctx) {
        //op_relacional : '=' | '<>'| '>=' | '<=' | '>' | '<';
        return ctx.getText();
    }

    @Override
    public String visitExpressao(LAParser.ExpressaoContext ctx) {
        //expressao : termo_logico outros_termos_logicos;
        if(ctx.children != null){
            visitTermo_logico(ctx.termo_logico());
            visitOutros_termos_logicos(ctx.outros_termos_logicos());
        }
        return null;
    }

    @Override
    public String visitOp_nao(LAParser.Op_naoContext ctx) {
        //op_nao : 'nao' | ;
        return ctx.getText();
    }

    @Override
    public String visitTermo_logico(LAParser.Termo_logicoContext ctx) {
        //termo_logico : fator_logico outros_fatores_logicos;
        if(ctx.children != null){
            visitFator_logico(ctx.fator_logico());
            visitOutros_fatores_logicos(ctx.outros_fatores_logicos());
        }
        return null;
    }

    @Override
    public String visitOutros_termos_logicos(LAParser.Outros_termos_logicosContext ctx) {
        // outros_termos_logicos : 'ou' termo_logico outros_termos_logicos | ;
        if (ctx.children != null) {
            visitTermo_logico(ctx.termo_logico());
            visitOutros_termos_logicos(ctx.outros_termos_logicos());
        }
        return null;
    }

    @Override
    public String visitOutros_fatores_logicos(LAParser.Outros_fatores_logicosContext ctx) {
        // outros_fatores_logicos : 'e' fator_logico outros_fatores_logicos | ;
        if (ctx.children != null) {
            visitFator_logico(ctx.fator_logico());
            visitOutros_fatores_logicos(ctx.outros_fatores_logicos());
        }
        return null;
    }

    @Override
    public String visitFator_logico(LAParser.Fator_logicoContext ctx) {
        //fator_logico : op_nao parcela_logica;
        if(ctx.children != null){
            visitOp_nao(ctx.op_nao());
            visitParcela_logica(ctx.parcela_logica());
        }
        return null;
    }

    @Override
    public String visitParcela_logica(LAParser.Parcela_logicaContext ctx) {
        //parcela_logica : 'verdadeiro' | 'falso' | exp_relacional;
        if(ctx.exp_relacional() != null){
            visitExp_relacional(ctx.exp_relacional());
        }else{
            if(atr == true && !tipoAtr.equals("logico"))
                mensagem.erro_Atribuicao_Nao_Compativel(ctx.getStart().getLine(), nomeAtr);
            return ctx.getText();
        }
        return null;
    }

}
