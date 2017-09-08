import java.lang.reflect.Array;
import java.util.ArrayList;

public class AnalisadorSemantico extends LABaseVisitor<String> {

    private PilhaDeTabelas pilha;
    private SaidaParser sp;
    private Mensagens mensagem = new Mensagens(sp);
    private String tipoMaisVar;

    public AnalisadorSemantico(SaidaParser sp){this.sp = sp;}

    @Override
    public String visitPrograma(LAParser.ProgramaContext ctx) {
        //programa : declaracoes 'algoritmo' corpo 'fim_algoritmo';
        if(ctx.children != null){
            //Cria escopo global
            pilha = new PilhaDeTabelas();
            pilha.empilhar(new TabelaDeSimbolos("global"));
            visitDeclaracoes(ctx.declaracoes());
            visitCorpo(ctx.corpo());
        }
        sp.println("Fim da compilaçao");
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
        if (ctx.declaracao_local() != null)
            visitDeclaracao_local(ctx.declaracao_local());
        else
            visitDeclaracao_global(ctx.declaracao_global());
        return null;
    }

    @Override
    public String visitDeclaracao_local(LAParser.Declaracao_localContext ctx) {
       /* declaracao_local : 'declare' variavel
                |'constante' IDENT ':' tipo_basico '=' valor_constante
                |'tipo' IDENT ':' tipo; */

        if (ctx.children != null){
            TabelaDeSimbolos escopoAtual = pilha.topo();
            if(ctx.getText().startsWith("variavel"))
                visitVariavel(ctx.variavel());
            else if (ctx.getText().startsWith("constante")) {
                if (!escopoAtual.existeSimbolo(ctx.IDENT().toString())) {
                    visitTipo_basico(ctx.tipo_basico());
                    escopoAtual.adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo_basico().getText());
                } else {
                    mensagem.erroJaDeclarado(ctx.getStart().getLine(), ctx.IDENT().toString());
                }
            }
        }
        return null;
    }

    @Override
    public String visitVariavel(LAParser.VariavelContext ctx) {
        //variavel : IDENT dimensao mais_var ':' tipo;
        if(ctx.children != null){
            TabelaDeSimbolos escopoAtual = pilha.topo();
            visitTipo(ctx.tipo());
            tipoMaisVar = ctx.tipo().getText();
            if(!pilha.existeSimbolo(ctx.IDENT().toString())) {
                escopoAtual.adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo().getText());
            }else{
                mensagem.erroJaDeclarado(ctx.getStart().getLine(), ctx.IDENT().toString());
            }
            visitMais_var(ctx.mais_var());
        }
        return null;
    }

    @Override
    public String visitMais_var(LAParser.Mais_varContext ctx) {
        //mais_var : ',' IDENT dimensao mais_var | ;
        if(ctx.children != null){
            TabelaDeSimbolos escopoAtual = pilha.topo();
            if(!pilha.existeSimbolo(ctx.IDENT().toString())){
                escopoAtual.adicionarSimbolo(ctx.IDENT().toString(), tipoMaisVar);
            }
            else {
                mensagem.erroJaDeclarado(ctx.getStart().getLine(), ctx.IDENT().toString());
            }
            visitMais_var(ctx.mais_var());
        }
        return null;
    }

    @Override
    public String visitIdentificador(LAParser.IdentificadorContext ctx) {
        //identificador : ponteiros_opcionais IDENT dimensao outros_ident;
        if(ctx.children != null){
            TabelaDeSimbolos escopoAtual = pilha.topo();
            visitPonteiros_opcionais(ctx.ponteiros_opcionais());
            visitDimensao(ctx.dimensao());
            if(ctx.outros_ident().children == null)
                if(!escopoAtual.existeSimbolo(ctx.IDENT().toString()){
                    mensagem.erroJaDeclarado(ctx.getStart().getLine(), ctx.IDENT().toString());
            }
            visitOutros_ident(ctx.outros_ident());
        }
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
    public String visitPonteiros_opcionais(LAParser.Ponteiros_opcionaisContext ctx) {
        //ponteiros_opcionais : '^' ponteiros_opcionais | ;
        if(ctx.children != null)
            visitPonteiros_opcionais(ctx.ponteiros_opcionais());
        return null;
    }

    @Override
    public String visitOutros_ident(LAParser.Outros_identContext ctx) {
        //outros_ident: '.' identificador | ;
        if(ctx.children != null)
            visitIdentificador(ctx.identificador());
        return null;
    }

    @Override
    public String visitDimensao(LAParser.DimensaoContext ctx) {
        //dimensao: '[' exp_aritmetica ']' dimensao | ;
        if (ctx.exp_aritmetica() != null) {
            visitExp_aritmetica(ctx.exp_aritmetica());
            visitDimensao(ctx.dimensao());
        }
        return null;
    }

    @Override
    public String visitTipo(LAParser.TipoContext ctx) {
        //tipo: registro | tipo_estendido;
        if(ctx.registro() != null){
            visitRegistro(ctx.registro());
        }else if(ctx.tipo_estendido() != null){
            visitTipo_estendido(ctx.tipo_estendido());
        }
        return null;
    }

    @Override
    public String visitMais_variaveis(LAParser.Mais_variaveisContext ctx) {
        //mais_variaveis: variavel mais_variaveis | ;
        if (ctx.children != null) {
            visitVariavel(ctx.variavel());
            visitMais_variaveis(ctx.mais_variaveis());
        }
        return null;
    }

    @Override
    public String visitTipo_basico(LAParser.Tipo_basicoContext ctx) {
        //tipo_basico: 'literal'|'inteiro'|'real'|'logico';
        if(ctx.getText().equals("literal") || ctx.getText().equals("inteiro")
                || ctx.getText().equals("real") || ctx.getText().equals("logico")){
            return ctx.getText();
        }else{
            mensagem.erroTipoNaoDeclarado(ctx.getStart().getLine(),ctx.getText());
            return "errou";
        }
    }

    @Override
    public String visitTipo_basico_ident(LAParser.Tipo_basico_identContext ctx) {
        //tipo_basico_ident: tipo_basico| IDENT;
        if(ctx.tipo_basico() != null){
            return (visitTipo_basico(ctx.tipo_basico());
        }else{
            if (pilha.existeSimbolo(ctx.IDENT().toString())){
                return ctx.IDENT().toString();
            }else{
                mensagem.erroTipoNaoDeclarado(ctx.getStart().getLine(),ctx.IDENT().toString());
                return "errou";
            }
        }
    }

    @Override
    public String visitTipo_estendido(LAParser.Tipo_estendidoContext ctx) {
        //tipo_estendido: ponteiros_opcionais tipo_basico_ident;
        if(ctx.children != null){
            visitPonteiros_opcionais(ctx.ponteiros_opcionais());
        }else{
            return visitTipo_basico_ident(ctx.tipo_basico_ident());
        }
        return null;
    }

    @Override
    public String visitValor_constante(LAParser.Valor_constanteContext ctx) {
        //valor_constante: CADEIA|NUM_INT|NUM_REAL|'verdadeiro'|'falso';
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
        //declaracao_global: 'procedimento' IDENT '(' parametros_opcional ')' declaracoes_locais comandos 'fim_procedimento'
          //               |'funcao' IDENT '(' parametros_opcional ')' ':' tipo_estendido declaracoes_locais comandos 'fim_funcao'

        if (ctx.getText().startsWith("procedimento")) {
            if (!pilha.existeSimbolo(ctx.IDENT().toString())) {
                pilha.topo().adicionarSimbolo(ctx.IDENT().getText(), "procedimento");
            }
            pilha.empilhar(new TabelaDeSimbolos("procedimento "+ctx.IDENT().getText()));
            visitParametros_opcional(ctx.parametros_opcional());
            visitDeclaracoes_locais(ctx.declaracoes_locais());
            visitComandos(ctx.comandos());
            pilha.desempilhar();
        } else if (ctx.getText().startsWith("funcao")) {
            if (!pilha.existeSimbolo(ctx.IDENT().toString())) {
                 pilha.topo().adicionarSimbolo(ctx.IDENT().getText(), "funcao");
               }
               pilha.empilhar(new TabelaDeSimbolos("funcao "+ctx.IDENT().getText()));
               visitParametros_opcional(ctx.parametros_opcional());
               visitTipo_estendido(ctx.tipo_estendido());
               visitDeclaracoes_locais(ctx.declaracoes_locais());
               visitComandos(ctx.comandos());
               pilha.desempilhar();
            }
        return null;
    }

    @Override
    public String visitParametros_opcional(LAParser.Parametros_opcionalContext ctx) {
        //parametros_opcional: parametro | ;
        if (ctx.children != null) {
            visitParametro(ctx.parametro());
        }
        return null;
    }

    @Override
    public String visitParametro(LAParser.ParametroContext ctx) {
        //parametro: var_opcional identificador mais_ident ':' tipo_estendido mais_parametros;
        if (ctx.children != null) {
            TabelaDeSimbolos escopoAtual = pilha.topo();
            visitVar_opcional(ctx.var_opcional());
            if(!pilha.existeSimbolo(ctx.identificador().IDENT().getText())){
                pilha.topo().adicionarSimbolo(ctx.identificador().IDENT().getText(), visitTipo_estendido(ctx.tipo_estendido()));
            }else{
                mensagem.erroJaDeclarado(ctx.identificador().getStart().getLine(), ctx.identificador().IDENT().getText());
            }
            visitIdentificador(ctx.identificador());
            visitMais_ident(ctx.mais_ident());
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
          if (ctx.children != null)
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
        if(ctx.children!= null){
            visitDeclaracoes_locais(ctx.declaracoes_locais());
            visitComandos(ctx.comandos());
        }
        return null;
    }

    @Override
    public String visitComandos(LAParser.ComandosContext ctx) {
        //comandos : cmd comandos | ;
        if(ctx.children!= null){
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

        if(ctx.children!= null){
            TabelaDeSimbolos escopoAtual = pilha.topo();

            if(ctx.getText().startsWith("leia")){
                visitIdentificador(ctx.identificador());
                visitMais_ident(ctx.mais_ident());
            }else if(ctx.getText().startsWith("escreva")){
                visitExpressao(ctx.expressao());
                visitMais_expressao(ctx.mais_expressao());
            }else if(ctx.getText().startsWith("se")){
                visitExpressao(ctx.expressao());
                visitComandos(ctx.comandos());
                visitSenao_opcional(ctx.senao_opcional());
            }else if(ctx.getText().startsWith("caso")){
                visitExp_aritmetica(ctx.exp_aritmetica().get(0)); //get(0)
                visitSelecao(ctx.selecao());
                visitSenao_opcional(ctx.senao_opcional());
            }else if(ctx.getText().startsWith("para")){
                if (!pilha.existeSimbolo(ctx.IDENT().toString()))
                    mensagem.erroJaDeclarado(ctx.getStart().getLine(), ctx.IDENT().toString());
                visitExp_aritmetica(ctx.exp_aritmetica().get(0));
                visitExp_aritmetica(ctx.exp_aritmetica().get(1));
                visitComandos(ctx.comandos());
            }else if(ctx.getText().startsWith("enquanto")){
                visitExpressao(ctx.expressao());
                visitComandos(ctx.comandos());
            }else if (ctx.getText().startsWith("faca")){
                visitComandos(ctx.comandos());
                visitExpressao(ctx.expressao());
            }else if(ctx.getText().startsWith("^")) { //'^' IDENT outros_ident dimensao '<-' expressao
                if (!pilha.existeSimbolo(ctx.IDENT().toString())) {
                    mensagem.erroJaDeclarado(ctx.getStart().getLine(), ctx.IDENT().toString());
                }
                visitOutros_ident(ctx.outros_ident());
                visitDimensao(ctx.dimensao());
                visitExpressao(ctx.expressao());
            } else if(ctx.getText().startsWith("IDENT")){
                if (!pilha.existeSimbolo(ctx.IDENT().toString())) {
                    mensagem.erroTipoNaoDeclarado(ctx.getStart().getLine(), ctx.IDENT().toString());
                }else {
                    visitChamada_atribuicao(ctx.chamada_atribuicao());
                }
            }else if(ctx.getText().startsWith("retorne")){
                if (escopoAtual.toString().startsWith("Escopo: global") || escopoAtual.toString().startsWith("Escopo: procedimento"))
                     mensagem.erroRetornoEscopo(ctx.getStart().getLine());
                     visitExpressao(ctx.expressao());
                }
         }
        return null;
    }

    @Override
    public String visitMais_expressao(LAParser.Mais_expressaoContext ctx) {
        //mais_expressao : ',' expressao mais_expressao | ;
        if (ctx.expressao() != null) {
            visitExpressao(ctx.expressao());
            visitMais_expressao(ctx.mais_expressao());
        }
        return null;
    }

    @Override
    public String visitSenao_opcional(LAParser.Senao_opcionalContext ctx) {
        //senao_opcional : 'senao' comandos | ;
        if (ctx.comandos() != null) {
            visitComandos(ctx.comandos());
        }
        return null;
    }

    @Override
    public String visitChamada_atribuicao(LAParser.Chamada_atribuicaoContext ctx) {
        // chamada_atribuicao: '(' argumentos_opcional ')' | outros_ident dimensao '<-' expressao;
        if (ctx.argumentos_opcional() != null) {
            visitArgumentos_opcional(ctx.argumentos_opcional());
        } else if (ctx.outros_ident() != null) {
            visitOutros_ident(ctx.outros_ident());
            visitDimensao(ctx.dimensao());
            visitExpressao(ctx.expressao());
        }
        return null;
    }

    @Override
    public String visitArgumentos_opcional(LAParser.Argumentos_opcionalContext ctx) {
        //argumentos_opcional: expressao mais_expressao | ;
        if (ctx.children != null) {
            visitExpressao(ctx.expressao());
            visitMais_expressao(ctx.mais_expressao());
        }
        return null;
    }

    @Override
    public String visitSelecao(LAParser.SelecaoContext ctx) {
        //selecao: constantes ':' comandos mais_selecao;
        if (ctx.children != null) {
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
        if(ctx.children != null)
            return ctx.getText();
        return null;
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
        return super.visitTermo(ctx);
    }

    @Override
    public String visitOutros_termos(LAParser.Outros_termosContext ctx) {
        return super.visitOutros_termos(ctx);
    }

    @Override
    public String visitFator(LAParser.FatorContext ctx) {
        return super.visitFator(ctx);
    }

    @Override
    public String visitOutros_fatores(LAParser.Outros_fatoresContext ctx) {
        return super.visitOutros_fatores(ctx);
    }

    @Override
    public String visitParcela(LAParser.ParcelaContext ctx) {
        return super.visitParcela(ctx);
    }

    @Override
    public String visitParcela_unario(LAParser.Parcela_unarioContext ctx) {
        return super.visitParcela_unario(ctx);
    }

    @Override
    public String visitParcela_nao_unario(LAParser.Parcela_nao_unarioContext ctx) {
        return super.visitParcela_nao_unario(ctx);
    }

    @Override
    public String visitOutras_parcelas(LAParser.Outras_parcelasContext ctx) {
        return super.visitOutras_parcelas(ctx);
    }

    @Override
    public String visitChamada_partes(LAParser.Chamada_partesContext ctx) {
        return super.visitChamada_partes(ctx);
    }

    @Override
    public String visitExp_relacional(LAParser.Exp_relacionalContext ctx) {
        return super.visitExp_relacional(ctx);
    }

    @Override
    public String visitOp_opcional(LAParser.Op_opcionalContext ctx) {
        return super.visitOp_opcional(ctx);
    }

    @Override
    public String visitOp_relacional(LAParser.Op_relacionalContext ctx) {
        return super.visitOp_relacional(ctx);
    }

    @Override
    public String visitExpressao(LAParser.ExpressaoContext ctx) {
        return super.visitExpressao(ctx);
    }

    @Override
    public String visitOp_nao(LAParser.Op_naoContext ctx) {
        return super.visitOp_nao(ctx);
    }

    @Override
    public String visitTermo_logico(LAParser.Termo_logicoContext ctx) {
        return super.visitTermo_logico(ctx);
    }

    @Override
    public String visitOutros_termos_logicos(LAParser.Outros_termos_logicosContext ctx) {
        return super.visitOutros_termos_logicos(ctx);
    }

    @Override
    public String visitOutros_fatores_logicos(LAParser.Outros_fatores_logicosContext ctx) {
        return super.visitOutros_fatores_logicos(ctx);
    }

    @Override
    public String visitFator_logico(LAParser.Fator_logicoContext ctx) {
        return super.visitFator_logico(ctx);
    }

    @Override
    public String visitParcela_logica(LAParser.Parcela_logicaContext ctx) {
        return super.visitParcela_logica(ctx);
    }
}