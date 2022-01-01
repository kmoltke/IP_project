package searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFIDF implements Ranker {
    private TF TF;
    private IDF IDF; 

    public TFIDF() {
        TF = new TF();
        IDF = new IDF();
    }

    @Override
    public Map<String, Double> calculateScore(List<String> words, int corpusSize, InvertedIndex corpus) {
        Map<String, Double> tf = TF.calculateScore(words, corpusSize, corpus);
        Map<String, Double> idf = IDF.calculateScore(words, corpusSize, corpus);
        Map<String, Double> tfidf = new HashMap<>(); 
        for (String term : words) {
            tfidf.put(term, tf.get(term) * idf.get(term));
        }
        return tfidf;
    }

}
