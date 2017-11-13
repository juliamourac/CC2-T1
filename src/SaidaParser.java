public class SaidaParser {

    StringBuffer conteudo;
    boolean modificado;

    public SaidaParser() {
        conteudo = new StringBuffer();
        modificado = false;
    }

    public void println(String texto) {
        if(!modificado) modificado = true;
        conteudo.append(texto);
        conteudo.append("\n");
    }
    //Adicionado o metodo rebbot que reinicia o saida parser
    public void reboot(){
        conteudo.setLength(0);
        modificado = false;
    }
    
    public boolean isModificado() {
        return modificado;
    }

    @Override
    public String toString() {
        return conteudo.toString();
    }
}