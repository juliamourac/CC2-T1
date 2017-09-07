import java.io.IOException;
import org.antlr.v4.runtime.misc.*;;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import sun.misc.Cleaner;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;


public class Principal {

    public static void main(String args[]) throws IOException, RecognitionException {
        SaidaParser sp = new SaidaParser();

        String teste = "22-algoritmo_5-3_apostila_LA_1_erro_linha_26_acusado_linha_27.txt";
        String caminho = "C:\\Users\\etoal\\Documents\\Ufscar\\CC2\\CC2-T1\\casosDeTesteT1\\1.arquivos_com_erros_sintaticos\\entrada\\";
        //C:\Users\etoal\Documents\Ufscar\CC2\CC2-T1\casosDeTesteT1\1.arquivos_com_erros_sintaticos\entrada\1-algoritmo_2-2_apostila_LA_1_erro_linha_3_acusado_linha_10
        try {
            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));
            //ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(caminho + teste));
            LALexer lexer = new LALexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LAParser parser = new LAParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener(sp));

            parser.programa();
        } catch (ParseCancellationException pce) {
            if (pce.getMessage() != null) {
                sp.println(pce.getMessage());
            }
        }

        String out = sp.toString();
        File arq1 = new File(args[1]);
        //File arq1 = new File("teste.txt");
        arq1.createNewFile();
        FileWriter arq1W = new FileWriter(arq1, true);
        BufferedWriter arq1B = new BufferedWriter( arq1W );
        arq1B.write(out);
        arq1B.close();
        arq1W.close();




    }
}