import java.lang.reflect.Array;
import java.util.ArrayList;

public class GeradorCodigo extends LABaseVisitor<String>  {

    private SaidaParser sp;
    private PilhaDeTabelas pilhaTabela;
    private String tipoMaisVar;

    public GeradorCodigo(SaidaParser sp){this.sp = sp;}

    public String getConversao(String tipoVar) {
        if (tipoVar.equals("inteiro"))
            return "%d";
        else if (tipoVar.equals("real"))
            return "%f";
        else if (tipoVar.equals("literal"))
            return "%s";
        return "";
    }

    @Override
    public String visitPrograma(LAParser.ProgramaContext ctx){
        //programa : declaracoes 'algoritmo' corpo 'fim_algoritmo';
        if(ctx.children != null){
            pilhaTabela = new PilhaDeTabelas();
            pilhaTabela.empilhar(new TabelaDeSimbolos("global"));
            sp.println("#include <stdio.h> \n#include <stdlib.h>");
            visitDeclaracoes(ctx.declaracoes());
            sp.println("\n" + "int main(){");
            visitCorpo(ctx.corpo());
            sp.println("\t" + "return 0; \n}" );
            pilhaTabela.desempilhar();
        }
        return "";
    }

    @Override
    public String visitDeclaracoes(LAParser.DeclaracoesContext ctx) {
        //declaracoes : decl_local_global declaracoes | ;
        if(ctx.children != null){
            visitDecl_local_global(ctx.decl_local_global());
            visitDeclaracoes(ctx.declaracoes());
        }
        return "";
    }

    @Override
    public String visitDecl_local_global(LAParser.Decl_local_globalContext ctx) {
        //decl_local_global : declaracao_local | declaracao_global;
        if(ctx.declaracao_local() != null)
            visitDeclaracao_local(ctx.declaracao_local());
        else if (ctx.declaracao_global() != null)
            visitDeclaracao_global(ctx.declaracao_global());
        return "";
    }

    @Override
    public String visitDeclaracao_local(LAParser.Declaracao_localContext ctx) {
        /*declaracao_local  : 'declare' variavel
		          |'constante' IDENT ':' tipo_basico '=' valor_constante
		          |'tipo' IDENT ':' tipo;*/

        String declaracao_local = "";
        if(ctx.getText().startsWith("declare")) {
            declaracao_local += visitVariavel(ctx.variavel());
            sp.println(declaracao_local);
            return declaracao_local;
        }
        else if (ctx.getText().startsWith("constante")){
            declaracao_local += "#define ";
            declaracao_local += ctx.IDENT().toString() + " ";
            declaracao_local += visitValor_constante(ctx.valor_constante());
            sp.println(declaracao_local);
            pilhaTabela.topo().adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo_basico().getText());
            return declaracao_local;
        }else if (ctx.getText().startsWith("tipo")) {
            pilhaTabela.topo().adicionarSimbolo(ctx.IDENT().getText(), "tipo");
            return declaracao_local;
        }
        return "";
    }

    @Override
    public String visitVariavel(LAParser.VariavelContext ctx) {
        //variavel : IDENT dimensao mais_var ':' tipo;
        if(ctx.children != null){
            String variavel = "";
            variavel += "\t" + visitTipo(ctx.tipo()) + " ";
            tipoMaisVar = ctx.tipo().getText();
            pilhaTabela.topo().adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo().getText());
            variavel += visitDimensao(ctx.dimensao());
            variavel += ctx.IDENT().toString();
            if(tipoMaisVar.equals("literal")){
                variavel += "[80]";
            }
            variavel += visitMais_var(ctx.mais_var()) + ";";
            //variavel += tipoMaisVar + ";";

            return variavel;
        }
        return "";
    }

    @Override
    public String visitMais_var(LAParser.Mais_varContext ctx) {
        //mais_var : ',' IDENT dimensao mais_var | ;
        if(ctx.getText().startsWith(",")){
            String mais_var = "";
            mais_var += ", " + ctx.IDENT().toString();
            pilhaTabela.topo().adicionarSimbolo(ctx.IDENT().toString(), tipoMaisVar);
            mais_var += visitDimensao(ctx.dimensao());
            mais_var += visitMais_var(ctx.mais_var());
            return  mais_var;
        }
        return "";
    }

    @Override
    public String visitIdentificador(LAParser.IdentificadorContext ctx) {
        //identificador : ponteiros_opcionais IDENT dimensao outros_ident;
        if(ctx.children != null){
            String identificador = "";
            identificador += visitPonteiros_opcionais(ctx.ponteiros_opcionais());
            identificador += ctx.IDENT().toString();
            identificador += visitDimensao(ctx.dimensao());
            identificador += visitOutros_ident(ctx.outros_ident());
            return identificador;
        }
        return "";
    }

    @Override
    public String visitPonteiros_opcionais(LAParser.Ponteiros_opcionaisContext ctx) {
        //ponteiros_opcionais : '^' ponteiros_opcionais | ;
        if(ctx.children != null) {
            String pont_opcionais = "";
            pont_opcionais += "^";
            pont_opcionais += visitPonteiros_opcionais(ctx.ponteiros_opcionais());
            return pont_opcionais;
        }
        return "";
    }

    @Override
    public String visitOutros_ident(LAParser.Outros_identContext ctx) {
        //outros_ident: '.' identificador | ;
        if(ctx.children != null)
            return "." + visitIdentificador(ctx.identificador());
        else
            return "";
    }

    @Override
    public String visitDimensao(LAParser.DimensaoContext ctx) {
        //dimensao: '[' exp_aritmetica ']' dimensao | ;
        if(ctx.children != null) {
            String dimensao = "";
            dimensao += " [" + visitExp_aritmetica(ctx.exp_aritmetica()) + "] " ;
            dimensao += visitDimensao(ctx.dimensao());
            return dimensao;
        }
        return "";
    }

    @Override
    public String visitTipo(LAParser.TipoContext ctx) {
        //tipo: registro | tipo_estendido;
        if(ctx.registro() != null)
            return visitRegistro(ctx.registro());
        else
            return visitTipo_estendido(ctx.tipo_estendido());
    }

    @Override
    public String visitMais_ident(LAParser.Mais_identContext ctx) {
        //mais_ident: ',' identificador mais_ident | ;
        if(ctx.children != null){
            String mais_ident = "";
            mais_ident += ", " + visitIdentificador(ctx.identificador());
            mais_ident += visitMais_ident(ctx.mais_ident());
            return mais_ident;
        }
        return "";
    }

    @Override
    public String visitMais_variaveis(LAParser.Mais_variaveisContext ctx) {
        //mais_variaveis: variavel mais_variaveis | ;
        if(ctx.children != null){
            String mais_variavel = "";
            mais_variavel += visitVariavel(ctx.variavel());
            mais_variavel += visitMais_variaveis(ctx.mais_variaveis());
            return mais_variavel;
        }
        return "";
    }

    @Override
    public String visitTipo_basico(LAParser.Tipo_basicoContext ctx) {
        //tipo_basico: 'literal'|'inteiro'|'real'|'logico';
        if(ctx.getText().equals("literal"))
            return "char";
        else if(ctx.getText().equals("inteiro"))
            return "int";
        else if(ctx.getText().equals("real"))
            return "float";
        else if(ctx.getText().equals("logico"))
            return "boolean";
        return "";
    }

    @Override
    public String visitTipo_basico_ident(LAParser.Tipo_basico_identContext ctx) {
        //tipo_basico_ident: tipo_basico | IDENT;
        if(ctx.tipo_basico() != null){
            return visitTipo_basico(ctx.tipo_basico());
        }else
            return ctx.IDENT().toString();
    }

    @Override
    public String visitTipo_estendido(LAParser.Tipo_estendidoContext ctx) {
        //tipo_estendido: ponteiros_opcionais tipo_basico_ident;
        if(ctx.children != null){
            String tipo_estentido = "";
            tipo_estentido += visitPonteiros_opcionais(ctx.ponteiros_opcionais());
            tipo_estentido += visitTipo_basico_ident(ctx.tipo_basico_ident());
            return tipo_estentido;
        }
        return "";
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
            String registro = "";
            registro += "\tstruct {";
            registro += visitVariavel(ctx.variavel());
            registro += visitMais_variaveis(ctx.mais_variaveis());
            registro += "\t}";
            return registro;
        }
        return "";
    }

    @Override
    public String visitDeclaracao_global(LAParser.Declaracao_globalContext ctx) {
       // declaracao_global: 'procedimento' IDENT '(' parametros_opcional ')' declaracoes_locais comandos 'fim_procedimento'
         //                |'funcao' IDENT '(' parametros_opcional ')' ':' tipo_estendido declaracoes_locais comandos 'fim_funcao';
        String declaracao_global = "";
        if(ctx.getText().startsWith("procedimento")){
            pilhaTabela.empilhar(new TabelaDeSimbolos("procedimento "+ctx.IDENT().getText()));
            declaracao_global += "void" + ctx.IDENT().getText() + " (";
            declaracao_global += visitParametros_opcional(ctx.parametros_opcional()) + "){";
            declaracao_global += visitDeclaracoes_locais(ctx.declaracoes_locais());
            visitComandos(ctx.comandos());
            declaracao_global += "}";
            sp.println(declaracao_global);
            pilhaTabela.desempilhar();
        }else{
            pilhaTabela.empilhar(new TabelaDeSimbolos("funcao "+ctx.IDENT().getText()));
            declaracao_global += visitTipo_estendido(ctx.tipo_estendido());
            declaracao_global += " " + ctx.IDENT().getText() + "(";
            declaracao_global += visitParametros_opcional(ctx.parametros_opcional()) + "){";
            declaracao_global += visitDeclaracoes_locais(ctx.declaracoes_locais());
            declaracao_global += visitComandos(ctx.comandos());
            declaracao_global += "}";
            sp.println(declaracao_global);
            pilhaTabela.desempilhar();
        }
        return "";
    }

    @Override
    public String visitParametros_opcional(LAParser.Parametros_opcionalContext ctx) {
        //parametros_opcional: parametro | ;
        if(ctx.children != null)
            return visitParametro(ctx.parametro());
        return "";
    }

    @Override
    public String visitParametro(LAParser.ParametroContext ctx) {
        //parametro: var_opcional identificador mais_ident ':' tipo_estendido mais_parametros;
        if(ctx.children != null) {
            String parametros = "";
            visitVar_opcional(ctx.var_opcional());
            visitIdentificador(ctx.identificador());
            visitMais_ident(ctx.mais_ident());
            visitTipo_estendido(ctx.tipo_estendido());
            visitMais_parametros(ctx.mais_parametros());
        }
        return "";
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
            return "," + visitParametro(ctx.parametro());
        return "";
    }

    @Override
    public String visitDeclaracoes_locais(LAParser.Declaracoes_locaisContext ctx) {
        //declaracoes_locais : declaracao_local declaracoes_locais | ;
        if(ctx.children != null){
            String declaracoes_locais = "";
            declaracoes_locais += visitDeclaracao_local(ctx.declaracao_local());
            declaracoes_locais += visitDeclaracoes_locais(ctx.declaracoes_locais());
            return declaracoes_locais;
        }
        return "";
    }

    @Override
    public String visitCorpo(LAParser.CorpoContext ctx) {
        //corpo : declaracoes_locais comandos;
        if(ctx.children != null){
            String corpo = "";
            corpo += visitDeclaracoes_locais(ctx.declaracoes_locais());
            corpo += visitComandos(ctx.comandos());
            return corpo;
        }
        return "";
    }

    @Override
    public String visitComandos(LAParser.ComandosContext ctx) {
        //comandos : cmd comandos | ;
        if(ctx.children != null){
            String cmd = "";
            cmd += visitCmd(ctx.cmd());
            cmd += visitComandos(ctx.comandos());
            return cmd;
        }
        return "";
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
        String cmd = "";
        if(ctx.getText().startsWith("leia")){
            //'leia' '(' identificador mais_ident ')'
            if((pilhaTabela.topo().getValorTipoSimbolo(ctx.identificador().getText()).equals("literal")))
                cmd += "\tgets(";
            else {
                cmd += "\tscanf(";
                cmd += "\"" + getConversao(pilhaTabela.topo().getValorTipoSimbolo(ctx.identificador().getText())) + "\"";
                cmd += ",&";
            }
            cmd += visitIdentificador(ctx.identificador());
            cmd += visitMais_ident(ctx.mais_ident()) + ");";
            sp.println(cmd);
        }else if (ctx.getText().startsWith("escreva")){
            //'escreva' '(' expressao mais_expressao ')'
            cmd += "\tprintf(";
            cmd += "\"" + getConversao(pilhaTabela.topo().getValorTipoSimbolo(ctx.expressao().getText())) +"\"";
            cmd += "," + ctx.expressao().getText();
            cmd += visitExpressao(ctx.expressao());
            cmd += visitMais_expressao(ctx.mais_expressao()) + ");";
            sp.println(cmd);
        }else if (ctx.getText().startsWith("se")){
            visitExpressao(ctx.expressao());
            visitComandos(ctx.comandos());
            visitSenao_opcional(ctx.senao_opcional());
        }else if(ctx.getText().startsWith("para")){
            visitExp_aritmetica(ctx.exp_aritmetica().get(0));
            visitExp_aritmetica(ctx.exp_aritmetica().get(1));
            visitComandos(ctx.comandos());
        }else if(ctx.getText().startsWith("enquanto")){
            visitExpressao(ctx.expressao());
            visitComandos(ctx.comandos());
        }else if(ctx.getText().startsWith("faca")){
            visitComandos(ctx.comandos());
            visitExpressao(ctx.expressao());
        }else if(ctx.getText().startsWith("^")){
            visitOutros_ident(ctx.outros_ident());
            visitDimensao(ctx.dimensao());
            visitExpressao(ctx.expressao());
        }else if(ctx.getText().startsWith("IDENT")){
            visitChamada_atribuicao(ctx.chamada_atribuicao());
        }else if(ctx.getText().startsWith("retorne")){
            visitExpressao(ctx.expressao());
        }
        return "";
    }

    @Override
    public String visitMais_expressao(LAParser.Mais_expressaoContext ctx) {
        //mais_expressao : ',' expressao mais_expressao | ;
        if(ctx.children != null){
            String mais_expressao = "";
            mais_expressao += visitExpressao(ctx.expressao());
            mais_expressao += visitMais_expressao(ctx.mais_expressao());
            return mais_expressao;
        }
        return "";
    }

    @Override
    public String visitSenao_opcional(LAParser.Senao_opcionalContext ctx) {
        //senao_opcional : 'senao' comandos | ;
        if(ctx.children != null)
            return "else " + visitComandos(ctx.comandos());
        return "";
    }

    @Override
    public String visitChamada_atribuicao(LAParser.Chamada_atribuicaoContext ctx) {
        //chamada_atribuicao: '(' argumentos_opcional ')' | outros_ident dimensao '<-' expressao;
        String chamada_atribuicao = "";
        if(ctx.getText().startsWith("(")) {
            chamada_atribuicao += "(" + visitArgumentos_opcional(ctx.argumentos_opcional()) + ")";
            return chamada_atribuicao;
        }
        else{
            chamada_atribuicao += visitOutros_ident(ctx.outros_ident());
            chamada_atribuicao += visitDimensao(ctx.dimensao()) + " = ";
            chamada_atribuicao += visitExpressao(ctx.expressao()) + ";";
            return chamada_atribuicao;
        }
    }

    @Override
    public String visitArgumentos_opcional(LAParser.Argumentos_opcionalContext ctx) {
        //argumentos_opcional: expressao mais_expressao | ;
        if(ctx.children != null){
            String argumentos_opcional = "";
            argumentos_opcional += visitExpressao(ctx.expressao());
            argumentos_opcional += visitMais_expressao(ctx.mais_expressao());
            return argumentos_opcional;
        }
        return "";
    }

    @Override
    public String visitSelecao(LAParser.SelecaoContext ctx) {
        //selecao: constantes ':' comandos mais_selecao;
        if(ctx.children != null){
            visitConstantes(ctx.constantes());
            visitComandos(ctx.comandos());
            visitMais_selecao(ctx.mais_selecao());
        }
        return "";
    }

    @Override
    public String visitMais_selecao(LAParser.Mais_selecaoContext ctx) {
        //mais_selecao: selecao | ;
        if (ctx.children != null)
            visitSelecao(ctx.selecao());
        return "";
    }

    @Override
    public String visitConstantes(LAParser.ConstantesContext ctx) {
        //constantes: numero_intervalo mais_constantes;
        if (ctx.children != null) {
            visitNumero_intervalo(ctx.numero_intervalo());
            visitMais_constantes(ctx.mais_constantes());
        }
        return "";
    }

    @Override
    public String visitMais_constantes(LAParser.Mais_constantesContext ctx) {
        // mais_constantes: ',' constantes | ;
        if (ctx.children != null)
            visitConstantes(ctx.constantes());
        return "";
    }

    @Override
    public String visitNumero_intervalo(LAParser.Numero_intervaloContext ctx) {
        // numero_intervalo: op_unario NUM_INT intervalo_opcional;
        if (ctx.children != null) {
            visitOp_unario(ctx.op_unario());
            visitIntervalo_opcional(ctx.intervalo_opcional());
        }
        return "";
    }

    @Override
    public String visitIntervalo_opcional(LAParser.Intervalo_opcionalContext ctx) {
        // intervalo_opcional: '..' op_unario NUM_INT | ;
        if (ctx.children != null)
            visitOp_unario(ctx.op_unario());
        return "";
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
        return "";
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
            visitFator(ctx.fator());
            visitOutros_fatores(ctx.outros_fatores());
        }
        return "";
    }

    @Override
    public String visitOutros_termos(LAParser.Outros_termosContext ctx) {
        //outros_termos : op_adicao termo outros_termos | ;
        if (ctx.children != null) {
            visitOp_adicao(ctx.op_adicao());
            visitTermo(ctx.termo());
            visitOutros_termos(ctx.outros_termos());
        }
        return "";
    }

    @Override
    public String visitFator(LAParser.FatorContext ctx) {
        //fator : parcela outras_parcelas;
        if(ctx.children != null){
            visitParcela(ctx.parcela());
            visitOutras_parcelas(ctx.outras_parcelas());
        }
        return "";
    }

    @Override
    public String visitOutros_fatores(LAParser.Outros_fatoresContext ctx){
        //outros_fatores : op_multiplicacao fator outros_fatores | ;
        if (ctx.children != null) {
            visitOp_multiplicacao(ctx.op_multiplicacao());
            visitFator(ctx.fator());
            visitOutros_fatores(ctx.outros_fatores());
        }
        return "";
    }

    @Override
    public String visitParcela(LAParser.ParcelaContext ctx) {
        //parcela : op_unario parcela_unario | parcela_nao_unario;
        if (ctx.op_unario() != null) {
            visitOp_unario(ctx.op_unario());
            visitParcela_unario(ctx.parcela_unario());
        } else
            visitParcela_nao_unario(ctx.parcela_nao_unario());
        return "";
    }

    @Override
    public String visitParcela_unario(LAParser.Parcela_unarioContext ctx) {
        /*parcela_unario : '^' IDENT outros_ident dimensao
                | IDENT chamada_partes
                | NUM_INT
                | NUM_REAL
                | '(' expressao ')';*/
        if (ctx.getText().startsWith("^")) {
                visitOutros_ident(ctx.outros_ident());
                visitDimensao(ctx.dimensao());
        } else if (ctx.getText().startsWith("IDENT")) {
                visitChamada_partes(ctx.chamada_partes());
        } else if (ctx.getText().startsWith("(")) {
                visitExpressao(ctx.expressao());
        }
        return "";
    }

    @Override
    public String visitParcela_nao_unario(LAParser.Parcela_nao_unarioContext ctx) {
        //parcela_nao_unario : '&' IDENT outros_ident dimensao | CADEIA;
        if (ctx.outros_ident() != null) {
            visitOutros_ident(ctx.outros_ident());
            visitDimensao(ctx.dimensao());
        }
        return "";
    }

    @Override
    public String visitOutras_parcelas(LAParser.Outras_parcelasContext ctx) {
        //outras_parcelas : '%' parcela outras_parcelas | ;
        if (ctx.children != null) {
            visitParcela(ctx.parcela());
            visitOutras_parcelas(ctx.outras_parcelas());
        }
        return "";
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
        return "";
    }

    @Override
    public String visitExp_relacional(LAParser.Exp_relacionalContext ctx) {
        //exp_relacional : exp_aritmetica op_opcional;
        if(ctx.children != null){
            visitExp_aritmetica(ctx.exp_aritmetica());
            visitOp_opcional(ctx.op_opcional());
        }
        return "";
    }

    @Override
    public String visitOp_opcional(LAParser.Op_opcionalContext ctx) {
        //op_opcional : op_relacional exp_aritmetica | ;
        if (ctx.children != null) {
            visitOp_relacional(ctx.op_relacional());
            visitExp_aritmetica(ctx.exp_aritmetica());
        }
        return "";
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
            String expressao = "";
            expressao += visitTermo_logico(ctx.termo_logico());
            expressao += visitOutros_termos_logicos(ctx.outros_termos_logicos());
            return expressao;
        }
        return "";
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
            String termo_logico = "";
            termo_logico += visitFator_logico(ctx.fator_logico());
            termo_logico += visitOutros_fatores_logicos(ctx.outros_fatores_logicos());
            return termo_logico;
        }
        return "";
    }

    @Override
    public String visitOutros_termos_logicos(LAParser.Outros_termos_logicosContext ctx) {
        // outros_termos_logicos : 'ou' termo_logico outros_termos_logicos | ;
        if (ctx.children != null) {
            String outros_termos_logicos = "";
            outros_termos_logicos += "|| " + visitTermo_logico(ctx.termo_logico());
            outros_termos_logicos += visitOutros_termos_logicos(ctx.outros_termos_logicos());
            return outros_termos_logicos;
        }
        return "";
    }

    @Override
    public String visitOutros_fatores_logicos(LAParser.Outros_fatores_logicosContext ctx) {
        // outros_fatores_logicos : 'e' fator_logico outros_fatores_logicos | ;
        if (ctx.children != null) {
            String outros_fatores_logicos = "";
            outros_fatores_logicos += "&& " + visitFator_logico(ctx.fator_logico());
            outros_fatores_logicos += visitOutros_fatores_logicos(ctx.outros_fatores_logicos());
            return outros_fatores_logicos;
        }
        return "";
    }

    @Override
    public String visitFator_logico(LAParser.Fator_logicoContext ctx) {
        //fator_logico : op_nao parcela_logica;
        if(ctx.children != null){
            String fator_logico = "";
            fator_logico += visitOp_nao(ctx.op_nao());
            fator_logico += visitParcela_logica(ctx.parcela_logica());
            return fator_logico;
        }
        return "";
    }

    @Override
    public String visitParcela_logica(LAParser.Parcela_logicaContext ctx) {
        //parcela_logica : 'verdadeiro' | 'falso' | exp_relacional;
        if(ctx.exp_relacional() != null){
            return visitExp_relacional(ctx.exp_relacional());
        }else{
            return ctx.getText();
        }
    }

}
