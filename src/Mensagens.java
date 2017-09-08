public class Mensagens {

   private SaidaParser sp;

   public void Mensagens(SaidaParser sp){this.sp = sp;}

   public void erroJaDeclarado(int linha, String idem) {
        sp.println("Linha " + linha + ": identificador " + idem + " ja declarado anteriormente");
   }

    public void erroTipoNaoDeclarado(int linha, String tipo) {
        sp.println("Linha " + linha + ": tipo " + tipo + " nao declarado");
    }

    public void erroRetornoEscopo(int linha) {
        sp.println("Linha "+ linha +": comando retorne nao permitido nesse escopo");
    }

}
