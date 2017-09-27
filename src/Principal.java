import java.io.IOException;
import org.antlr.v4.runtime.misc.*;;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;


public class Principal {

    public static void main(String args[]) throws IOException, RecognitionException {

        SaidaParser sp = new SaidaParser();

        String teste = "1.declaracao_leitura_impressao_inteiro.alg";
        String caminho = "C:\\Users\\etoal\\Documents\\Ufscar\\CC2\\CC2-T1\\casosDeTesteT1\\3.arquivos_sem_erros\\1.entrada\\";
        //C:\Users\etoal\Documents\Ufscar\CC2\CC2-T1\casosDeTesteT1\1.arquivos_com_erros_sintaticos\entrada\1-algoritmo_2-2_apostila_LA_1_erro_linha_3_acusado_linha_10

        try {
           ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));
           //ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(caminho + teste));

            LALexer lexer = new LALexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LAParser parser = new LAParser(tokens);

            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener(sp)); //Analisador Sintatico

            LAParser.ProgramaContext context = parser.programa();


            if(!sp.isModificado()){
                sp.reboot();
                AnalisadorSemantico semantico = new AnalisadorSemantico(sp);
                semantico.visitPrograma(context); //Analisador Semântico
                if(sp.toString().equals("Fim da compilacao"));
                    sp.reboot();
            }

            if(!sp.isModificado()){
                sp.reboot();
                GeradorCodigo gerador = new GeradorCodigo(sp);
                gerador.visitPrograma(context);

            }
        } catch (ParseCancellationException pce) {
            if (pce.getMessage() != null) {
                sp.println(pce.getMessage());
            }
        }

        String out = sp.toString();
        System.out.println("Saida: ");
        System.out.println(out);

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