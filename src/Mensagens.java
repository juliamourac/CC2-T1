public class Mensagens {

   private SaidaParser sp;

   public Mensagens(SaidaParser sp){this.sp = sp;}


   public void erro_Ja_Declarado(int linha, String idem) {
        sp.println("Linha " + linha + ": identificador " + idem + " ja declarado anteriormente");
   }

    public void erro_Tipo_Nao_Declarado(int linha, String tipo) {
        sp.println("Linha " + linha + ": tipo " + tipo + " nao declarado");
    }

    public void erro_Retorno_Nao_Permitido(int linha) {
        sp.println("Linha "+ linha +": comando retorne nao permitido nesse escopo");
    }

    public void erro_Atribuicao_Nao_Compativel(int linha, String nome) {
        sp.println("Linha " + linha + ": atribuicao nao compativel para " + nome);
    }



}
