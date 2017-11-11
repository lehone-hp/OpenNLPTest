package dev.hope;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.InputStream;

public class MainClass {

    public static void main(String[] args) throws Exception{

        InputStream tokenizerInputStream = new FileInputStream("/home/lehone/repos/OpenNLPExample/src/main/resources/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(tokenizerInputStream);

        TokenizerME tokenizer = new TokenizerME(tokenModel);
        String sentence = "Kranite Nigeria Limited commits itself to becoming an international company and a. ";

        String[] tokens = tokenizer.tokenize(sentence);


        InputStream inputStream = new FileInputStream("/home/lehone/repos/OpenNLPExample/src/main/resources/en-ner-organization.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(inputStream);

        NameFinderME nameFinder = new NameFinderME(model);
        Span[] nameSpans = nameFinder.find(tokens);

        for(Span s: nameSpans){
            for (int i=s.getStart(); i<s.getEnd(); i++){
                System.out.print(tokens[i]+" ");
            }
            System.out.println();
        }
    }
}
