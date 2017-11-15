package dev.hope;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.*;

public class MainClass {

    public static void main(String[] args) throws Exception{


        FileReader reader = new FileReader(new File("src/main/resources/organization_text.txt"));
        FileWriter writer = new FileWriter(new File("src/main/resources/output.txt"));

        BufferedReader br = new BufferedReader(reader);
        BufferedWriter bw = new BufferedWriter(writer);

        String line = "";

        InputStream tokenizerInputStream = new FileInputStream("src/main/resources/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(tokenizerInputStream);
        TokenizerME tokenizer = new TokenizerME(tokenModel);

        InputStream inputStream = new FileInputStream("src/main/resources/en-ner-organization.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
        NameFinderME nameFinder = new NameFinderME(model);

        while ((line=br.readLine()) != null) {

            String[] tokens = tokenizer.tokenize(line);
            Span[] nameSpans = nameFinder.find(tokens);

            bw.write(line+"\n");

            for(Span s: nameSpans){

                bw.write("\t\t");

                for (int i=s.getStart(); i<s.getEnd(); i++){
                    bw.write(tokens[i]+" ");
                }

                bw.write("\n");
            }
            bw.write("\n\n");
            bw.flush();
        }

    }
}
