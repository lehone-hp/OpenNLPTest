package dev.hope;

import opennlp.tools.namefind.*;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class MainClass {

    public static void main(String[] args) throws Exception{
        /**
        FileReader reader = new FileReader(new File("src/main/resources/training-org.txt"));
        FileWriter writer = new FileWriter(new File("src/main/resources/training-org2.txt"));

        BufferedReader br = new BufferedReader(reader);
        BufferedWriter bw = new BufferedWriter(writer);

        String line;
        String replace;

        while ((line=br.readLine()) != null){
            replace = line.replaceAll("<START:organization>", "<START:organization> ");
            line = replace;
            replace = line.replaceAll("<END>", " <END>");
            System.out.println("Processed - "+replace);

            bw.write(replace+"\n");
        }
        */

        /**
         * Connect to solr and get all jobs from this JobSite

        CloudSolrClient solr = new CloudSolrClient.Builder()
                .withSolrUrl("http://localhost:8983/solr")
                .sendDirectUpdatesToShardLeadersOnly()
                .build();

        solr.setDefaultCollection("my_collection");

        SolrQuery query = new SolrQuery("*:*");
        query.setRows(21474836);

        QueryResponse response = solr.query(query);
        SolrDocumentList results = response.getResults();

        FileWriter titleWriter = new FileWriter(new File("src/main/resources/titles.txt"));
        FileWriter contentWriter = new FileWriter(new File("src/main/resources/contents.txt"));

        BufferedWriter titleBW = new BufferedWriter(titleWriter);
        BufferedWriter contentBW = new BufferedWriter(contentWriter);

        InputStream modelIn = new FileInputStream("src/main/resources/en-sent.bin");
        SentenceModel sentenceModel = new SentenceModel(modelIn);
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

        for (SolrDocument result : results) {
            String title = (String)result.getFirstValue("title");
            String content = (String)result.getFirstValue("content");

            System.out.println("Processing : "+title);

            titleBW.write(title+".\n");

            String[] sentences = sentenceDetector.sentDetect(content);
            if (sentences[0].charAt(sentences[0].length()-1) == '.'){
                contentBW.write(sentences[0]+"\n");
            } else {
                contentBW.write(sentences[0]+".\n");
            }

        }

        titleBW.flush();
        contentBW.flush();

         */


        /**
         * Training



        try  {
            ObjectStream<String> lineStream =
                    new PlainTextByLineStream(new InputStreamFactory() {
                        @Override
                        public InputStream createInputStream() throws IOException {
                            return new FileInputStream("src/main/resources/training-org.txt");
                        }
                    } , StandardCharsets.UTF_8);

            TokenNameFinderModel model;

            ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
            model = NameFinderME.train("en", "organization", sampleStream, TrainingParameters.defaultParams(),
                    new TokenNameFinderFactory());

            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("src/main/resources/en-ner-organization"));
            model.serialize(modelOut);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }


        FileReader reader = new FileReader(new File("src/main/resources/organization_text.txt"));
        FileWriter writer = new FileWriter(new File("src/main/resources/output.txt"));

        BufferedReader br = new BufferedReader(reader);
        BufferedWriter bw = new BufferedWriter(writer);

        String line = "";

        InputStream tokenizerInputStream = new FileInputStream("src/main/resources/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(tokenizerInputStream);
        TokenizerME tokenizer = new TokenizerME(tokenModel);

        InputStream inputStream = new FileInputStream("src/main/resources/en-ner-organization0.bin");
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
         */
    }
}
