// Generated from C:/Users/etoal/Documents/Ufscar/CC2/T1/src\LA.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LAParser}.
 */
public interface LAListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LAParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(LAParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(LAParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#declaracoes}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracoes(LAParser.DeclaracoesContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#declaracoes}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracoes(LAParser.DeclaracoesContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#decl_local_global}.
	 * @param ctx the parse tree
	 */
	void enterDecl_local_global(LAParser.Decl_local_globalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#decl_local_global}.
	 * @param ctx the parse tree
	 */
	void exitDecl_local_global(LAParser.Decl_local_globalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#declaracao_local}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracao_local(LAParser.Declaracao_localContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#declaracao_local}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracao_local(LAParser.Declaracao_localContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#variavel}.
	 * @param ctx the parse tree
	 */
	void enterVariavel(LAParser.VariavelContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#variavel}.
	 * @param ctx the parse tree
	 */
	void exitVariavel(LAParser.VariavelContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#mais_var}.
	 * @param ctx the parse tree
	 */
	void enterMais_var(LAParser.Mais_varContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#mais_var}.
	 * @param ctx the parse tree
	 */
	void exitMais_var(LAParser.Mais_varContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#identificador}.
	 * @param ctx the parse tree
	 */
	void enterIdentificador(LAParser.IdentificadorContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#identificador}.
	 * @param ctx the parse tree
	 */
	void exitIdentificador(LAParser.IdentificadorContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#ponteiros_opcionais}.
	 * @param ctx the parse tree
	 */
	void enterPonteiros_opcionais(LAParser.Ponteiros_opcionaisContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#ponteiros_opcionais}.
	 * @param ctx the parse tree
	 */
	void exitPonteiros_opcionais(LAParser.Ponteiros_opcionaisContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#outros_ident}.
	 * @param ctx the parse tree
	 */
	void enterOutros_ident(LAParser.Outros_identContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#outros_ident}.
	 * @param ctx the parse tree
	 */
	void exitOutros_ident(LAParser.Outros_identContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#dimensao}.
	 * @param ctx the parse tree
	 */
	void enterDimensao(LAParser.DimensaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#dimensao}.
	 * @param ctx the parse tree
	 */
	void exitDimensao(LAParser.DimensaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(LAParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(LAParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#mais_ident}.
	 * @param ctx the parse tree
	 */
	void enterMais_ident(LAParser.Mais_identContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#mais_ident}.
	 * @param ctx the parse tree
	 */
	void exitMais_ident(LAParser.Mais_identContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#mais_variaveis}.
	 * @param ctx the parse tree
	 */
	void enterMais_variaveis(LAParser.Mais_variaveisContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#mais_variaveis}.
	 * @param ctx the parse tree
	 */
	void exitMais_variaveis(LAParser.Mais_variaveisContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#tipo_basico}.
	 * @param ctx the parse tree
	 */
	void enterTipo_basico(LAParser.Tipo_basicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#tipo_basico}.
	 * @param ctx the parse tree
	 */
	void exitTipo_basico(LAParser.Tipo_basicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#tipo_basico_ident}.
	 * @param ctx the parse tree
	 */
	void enterTipo_basico_ident(LAParser.Tipo_basico_identContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#tipo_basico_ident}.
	 * @param ctx the parse tree
	 */
	void exitTipo_basico_ident(LAParser.Tipo_basico_identContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#tipo_estendido}.
	 * @param ctx the parse tree
	 */
	void enterTipo_estendido(LAParser.Tipo_estendidoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#tipo_estendido}.
	 * @param ctx the parse tree
	 */
	void exitTipo_estendido(LAParser.Tipo_estendidoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#valor_constante}.
	 * @param ctx the parse tree
	 */
	void enterValor_constante(LAParser.Valor_constanteContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#valor_constante}.
	 * @param ctx the parse tree
	 */
	void exitValor_constante(LAParser.Valor_constanteContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#registro}.
	 * @param ctx the parse tree
	 */
	void enterRegistro(LAParser.RegistroContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#registro}.
	 * @param ctx the parse tree
	 */
	void exitRegistro(LAParser.RegistroContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#declaracao_global}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracao_global(LAParser.Declaracao_globalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#declaracao_global}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracao_global(LAParser.Declaracao_globalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#parametros_opcional}.
	 * @param ctx the parse tree
	 */
	void enterParametros_opcional(LAParser.Parametros_opcionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#parametros_opcional}.
	 * @param ctx the parse tree
	 */
	void exitParametros_opcional(LAParser.Parametros_opcionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(LAParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(LAParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#var_opcional}.
	 * @param ctx the parse tree
	 */
	void enterVar_opcional(LAParser.Var_opcionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#var_opcional}.
	 * @param ctx the parse tree
	 */
	void exitVar_opcional(LAParser.Var_opcionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#mais_parametros}.
	 * @param ctx the parse tree
	 */
	void enterMais_parametros(LAParser.Mais_parametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#mais_parametros}.
	 * @param ctx the parse tree
	 */
	void exitMais_parametros(LAParser.Mais_parametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#declaracoes_locais}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracoes_locais(LAParser.Declaracoes_locaisContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#declaracoes_locais}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracoes_locais(LAParser.Declaracoes_locaisContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#corpo}.
	 * @param ctx the parse tree
	 */
	void enterCorpo(LAParser.CorpoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#corpo}.
	 * @param ctx the parse tree
	 */
	void exitCorpo(LAParser.CorpoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#comandos}.
	 * @param ctx the parse tree
	 */
	void enterComandos(LAParser.ComandosContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#comandos}.
	 * @param ctx the parse tree
	 */
	void exitComandos(LAParser.ComandosContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterCmd(LAParser.CmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitCmd(LAParser.CmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#mais_expressao}.
	 * @param ctx the parse tree
	 */
	void enterMais_expressao(LAParser.Mais_expressaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#mais_expressao}.
	 * @param ctx the parse tree
	 */
	void exitMais_expressao(LAParser.Mais_expressaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#senao_opcional}.
	 * @param ctx the parse tree
	 */
	void enterSenao_opcional(LAParser.Senao_opcionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#senao_opcional}.
	 * @param ctx the parse tree
	 */
	void exitSenao_opcional(LAParser.Senao_opcionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#chamada_atribuicao}.
	 * @param ctx the parse tree
	 */
	void enterChamada_atribuicao(LAParser.Chamada_atribuicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#chamada_atribuicao}.
	 * @param ctx the parse tree
	 */
	void exitChamada_atribuicao(LAParser.Chamada_atribuicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#argumentos_opcional}.
	 * @param ctx the parse tree
	 */
	void enterArgumentos_opcional(LAParser.Argumentos_opcionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#argumentos_opcional}.
	 * @param ctx the parse tree
	 */
	void exitArgumentos_opcional(LAParser.Argumentos_opcionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#selecao}.
	 * @param ctx the parse tree
	 */
	void enterSelecao(LAParser.SelecaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#selecao}.
	 * @param ctx the parse tree
	 */
	void exitSelecao(LAParser.SelecaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#mais_selecao}.
	 * @param ctx the parse tree
	 */
	void enterMais_selecao(LAParser.Mais_selecaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#mais_selecao}.
	 * @param ctx the parse tree
	 */
	void exitMais_selecao(LAParser.Mais_selecaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#constantes}.
	 * @param ctx the parse tree
	 */
	void enterConstantes(LAParser.ConstantesContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#constantes}.
	 * @param ctx the parse tree
	 */
	void exitConstantes(LAParser.ConstantesContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#mais_constantes}.
	 * @param ctx the parse tree
	 */
	void enterMais_constantes(LAParser.Mais_constantesContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#mais_constantes}.
	 * @param ctx the parse tree
	 */
	void exitMais_constantes(LAParser.Mais_constantesContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#numero_intervalo}.
	 * @param ctx the parse tree
	 */
	void enterNumero_intervalo(LAParser.Numero_intervaloContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#numero_intervalo}.
	 * @param ctx the parse tree
	 */
	void exitNumero_intervalo(LAParser.Numero_intervaloContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#intervalo_opcional}.
	 * @param ctx the parse tree
	 */
	void enterIntervalo_opcional(LAParser.Intervalo_opcionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#intervalo_opcional}.
	 * @param ctx the parse tree
	 */
	void exitIntervalo_opcional(LAParser.Intervalo_opcionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#op_unario}.
	 * @param ctx the parse tree
	 */
	void enterOp_unario(LAParser.Op_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#op_unario}.
	 * @param ctx the parse tree
	 */
	void exitOp_unario(LAParser.Op_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#exp_aritmetica}.
	 * @param ctx the parse tree
	 */
	void enterExp_aritmetica(LAParser.Exp_aritmeticaContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#exp_aritmetica}.
	 * @param ctx the parse tree
	 */
	void exitExp_aritmetica(LAParser.Exp_aritmeticaContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#op_multiplicacao}.
	 * @param ctx the parse tree
	 */
	void enterOp_multiplicacao(LAParser.Op_multiplicacaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#op_multiplicacao}.
	 * @param ctx the parse tree
	 */
	void exitOp_multiplicacao(LAParser.Op_multiplicacaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#op_adicao}.
	 * @param ctx the parse tree
	 */
	void enterOp_adicao(LAParser.Op_adicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#op_adicao}.
	 * @param ctx the parse tree
	 */
	void exitOp_adicao(LAParser.Op_adicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#termo}.
	 * @param ctx the parse tree
	 */
	void enterTermo(LAParser.TermoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#termo}.
	 * @param ctx the parse tree
	 */
	void exitTermo(LAParser.TermoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#outros_termos}.
	 * @param ctx the parse tree
	 */
	void enterOutros_termos(LAParser.Outros_termosContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#outros_termos}.
	 * @param ctx the parse tree
	 */
	void exitOutros_termos(LAParser.Outros_termosContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#fator}.
	 * @param ctx the parse tree
	 */
	void enterFator(LAParser.FatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#fator}.
	 * @param ctx the parse tree
	 */
	void exitFator(LAParser.FatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#outros_fatores}.
	 * @param ctx the parse tree
	 */
	void enterOutros_fatores(LAParser.Outros_fatoresContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#outros_fatores}.
	 * @param ctx the parse tree
	 */
	void exitOutros_fatores(LAParser.Outros_fatoresContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#parcela}.
	 * @param ctx the parse tree
	 */
	void enterParcela(LAParser.ParcelaContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#parcela}.
	 * @param ctx the parse tree
	 */
	void exitParcela(LAParser.ParcelaContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#parcela_unario}.
	 * @param ctx the parse tree
	 */
	void enterParcela_unario(LAParser.Parcela_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#parcela_unario}.
	 * @param ctx the parse tree
	 */
	void exitParcela_unario(LAParser.Parcela_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#parcela_nao_unario}.
	 * @param ctx the parse tree
	 */
	void enterParcela_nao_unario(LAParser.Parcela_nao_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#parcela_nao_unario}.
	 * @param ctx the parse tree
	 */
	void exitParcela_nao_unario(LAParser.Parcela_nao_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#outras_parcelas}.
	 * @param ctx the parse tree
	 */
	void enterOutras_parcelas(LAParser.Outras_parcelasContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#outras_parcelas}.
	 * @param ctx the parse tree
	 */
	void exitOutras_parcelas(LAParser.Outras_parcelasContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#chamada_partes}.
	 * @param ctx the parse tree
	 */
	void enterChamada_partes(LAParser.Chamada_partesContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#chamada_partes}.
	 * @param ctx the parse tree
	 */
	void exitChamada_partes(LAParser.Chamada_partesContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#exp_relacional}.
	 * @param ctx the parse tree
	 */
	void enterExp_relacional(LAParser.Exp_relacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#exp_relacional}.
	 * @param ctx the parse tree
	 */
	void exitExp_relacional(LAParser.Exp_relacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#op_opcional}.
	 * @param ctx the parse tree
	 */
	void enterOp_opcional(LAParser.Op_opcionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#op_opcional}.
	 * @param ctx the parse tree
	 */
	void exitOp_opcional(LAParser.Op_opcionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#op_relacional}.
	 * @param ctx the parse tree
	 */
	void enterOp_relacional(LAParser.Op_relacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#op_relacional}.
	 * @param ctx the parse tree
	 */
	void exitOp_relacional(LAParser.Op_relacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#expressao}.
	 * @param ctx the parse tree
	 */
	void enterExpressao(LAParser.ExpressaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#expressao}.
	 * @param ctx the parse tree
	 */
	void exitExpressao(LAParser.ExpressaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#op_nao}.
	 * @param ctx the parse tree
	 */
	void enterOp_nao(LAParser.Op_naoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#op_nao}.
	 * @param ctx the parse tree
	 */
	void exitOp_nao(LAParser.Op_naoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#termo_logico}.
	 * @param ctx the parse tree
	 */
	void enterTermo_logico(LAParser.Termo_logicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#termo_logico}.
	 * @param ctx the parse tree
	 */
	void exitTermo_logico(LAParser.Termo_logicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#outros_termos_logicos}.
	 * @param ctx the parse tree
	 */
	void enterOutros_termos_logicos(LAParser.Outros_termos_logicosContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#outros_termos_logicos}.
	 * @param ctx the parse tree
	 */
	void exitOutros_termos_logicos(LAParser.Outros_termos_logicosContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#outros_fatores_logicos}.
	 * @param ctx the parse tree
	 */
	void enterOutros_fatores_logicos(LAParser.Outros_fatores_logicosContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#outros_fatores_logicos}.
	 * @param ctx the parse tree
	 */
	void exitOutros_fatores_logicos(LAParser.Outros_fatores_logicosContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#fator_logico}.
	 * @param ctx the parse tree
	 */
	void enterFator_logico(LAParser.Fator_logicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#fator_logico}.
	 * @param ctx the parse tree
	 */
	void exitFator_logico(LAParser.Fator_logicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link LAParser#parcela_logica}.
	 * @param ctx the parse tree
	 */
	void enterParcela_logica(LAParser.Parcela_logicaContext ctx);
	/**
	 * Exit a parse tree produced by {@link LAParser#parcela_logica}.
	 * @param ctx the parse tree
	 */
	void exitParcela_logica(LAParser.Parcela_logicaContext ctx);
}