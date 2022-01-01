package searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IDF implements Ranker {

    /**
     * NOTE: if we add 1 to the denominator, test files will fail, since the corpussize will 
     * be smaller than the number of documents where the term appears 
     */
    @Override
    public Map<String, Double> calculateScore(List<String> words, int corpusSize, InvertedIndex corpus) {
        Map<String, Double> idf = new HashMap<>();
        for (String term : words){
            idf.merge(term, Math.log10((double) corpusSize / corpus.lookUp(term).size()), Double::max);
        }
        return idf;
    }

}
