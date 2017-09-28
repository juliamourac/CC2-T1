import java.io.StringWriter;
import java.util.BitSet;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.omg.CORBA.portable.InputStream;

public class ErrorListener implements ANTLRErrorListener {

    SaidaParser sp;

    public ErrorListener(SaidaParser sp) {
        this.sp = sp;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int i, int i1, String string, RecognitionException re) {
       CommonToken tk = (CommonToken) o;
       String valorTk = tk.getText();
       char ultTk = valorTk.charAt(valorTk.length()-1);
       char c = 34;

        if (!sp.isModificado()) {
            if (valorTk.equals("<EOF>"))
                sp.println("Linha " + i + ": erro sintatico proximo a EOF");
            else if ((valorTk.startsWith(String.valueOf(c)) && (ultTk != c)))
                sp.println("Linha " + i + ": " +valorTk.substring(0,1) + " - simbolo nao identificado");
            else if (valorTk.indexOf('@') >= 0 || valorTk.indexOf('!') >= 0 || valorTk.indexOf('|') >= 0 )
               sp.println("Linha " + i + ": " +valorTk + " - simbolo nao identificado");
            else if (valorTk.startsWith("{"))
                sp.println("Linha " + (i+1) + ": comentario nao fechado");
            else
                sp.println("Linha " + i + ": erro sintatico proximo a " + valorTk);
            sp.println("Fim da compilacao");
        }
    }


    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {

    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {
    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {
    }
}
