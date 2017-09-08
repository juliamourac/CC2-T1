// Generated from C:/Users/etoal/Documents/Ufscar/CC2/CC2-T1/src\LA.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LAParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LAVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LAParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(LAParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#declaracoes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracoes(LAParser.DeclaracoesContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#decl_local_global}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl_local_global(LAParser.Decl_local_globalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#declaracao_local}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracao_local(LAParser.Declaracao_localContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#variavel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariavel(LAParser.VariavelContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#mais_var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMais_var(LAParser.Mais_varContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#identificador}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentificador(LAParser.IdentificadorContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#ponteiros_opcionais}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPonteiros_opcionais(LAParser.Ponteiros_opcionaisContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#outros_ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutros_ident(LAParser.Outros_identContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#dimensao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimensao(LAParser.DimensaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(LAParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#mais_ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMais_ident(LAParser.Mais_identContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#mais_variaveis}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMais_variaveis(LAParser.Mais_variaveisContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#tipo_basico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo_basico(LAParser.Tipo_basicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#tipo_basico_ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo_basico_ident(LAParser.Tipo_basico_identContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#tipo_estendido}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo_estendido(LAParser.Tipo_estendidoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#valor_constante}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValor_constante(LAParser.Valor_constanteContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#registro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegistro(LAParser.RegistroContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#declaracao_global}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracao_global(LAParser.Declaracao_globalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#parametros_opcional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros_opcional(LAParser.Parametros_opcionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#parametro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametro(LAParser.ParametroContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#var_opcional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_opcional(LAParser.Var_opcionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#mais_parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMais_parametros(LAParser.Mais_parametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#declaracoes_locais}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracoes_locais(LAParser.Declaracoes_locaisContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#corpo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCorpo(LAParser.CorpoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#comandos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComandos(LAParser.ComandosContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmd(LAParser.CmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#mais_expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMais_expressao(LAParser.Mais_expressaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#senao_opcional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSenao_opcional(LAParser.Senao_opcionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#chamada_atribuicao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChamada_atribuicao(LAParser.Chamada_atribuicaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#argumentos_opcional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentos_opcional(LAParser.Argumentos_opcionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#selecao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelecao(LAParser.SelecaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#mais_selecao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMais_selecao(LAParser.Mais_selecaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#constantes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantes(LAParser.ConstantesContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#mais_constantes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMais_constantes(LAParser.Mais_constantesContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#numero_intervalo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumero_intervalo(LAParser.Numero_intervaloContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#intervalo_opcional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalo_opcional(LAParser.Intervalo_opcionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#op_unario}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_unario(LAParser.Op_unarioContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#exp_aritmetica}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_aritmetica(LAParser.Exp_aritmeticaContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#op_multiplicacao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_multiplicacao(LAParser.Op_multiplicacaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#op_adicao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_adicao(LAParser.Op_adicaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#termo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermo(LAParser.TermoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#outros_termos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutros_termos(LAParser.Outros_termosContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#fator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFator(LAParser.FatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#outros_fatores}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutros_fatores(LAParser.Outros_fatoresContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#parcela}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParcela(LAParser.ParcelaContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#parcela_unario}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParcela_unario(LAParser.Parcela_unarioContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#parcela_nao_unario}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParcela_nao_unario(LAParser.Parcela_nao_unarioContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#outras_parcelas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutras_parcelas(LAParser.Outras_parcelasContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#chamada_partes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChamada_partes(LAParser.Chamada_partesContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#exp_relacional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_relacional(LAParser.Exp_relacionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#op_opcional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_opcional(LAParser.Op_opcionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#op_relacional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_relacional(LAParser.Op_relacionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressao(LAParser.ExpressaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#op_nao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_nao(LAParser.Op_naoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#termo_logico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermo_logico(LAParser.Termo_logicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#outros_termos_logicos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutros_termos_logicos(LAParser.Outros_termos_logicosContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#outros_fatores_logicos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutros_fatores_logicos(LAParser.Outros_fatores_logicosContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#fator_logico}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFator_logico(LAParser.Fator_logicoContext ctx);
	/**
	 * Visit a parse tree produced by {@link LAParser#parcela_logica}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParcela_logica(LAParser.Parcela_logicaContext ctx);
}