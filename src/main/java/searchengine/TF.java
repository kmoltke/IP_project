package searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TF implements Ranker{

    @Override
    public Map<String, Double> calculateScore(List<String> words, int corpusSize, InvertedIndex corpus) {
        Map<String, Double> tf = new HashMap<>();
        for (String term : words){
            tf.merge(term, 1.0 / words.size(), Double::sum);
        }
        return tf;
    }
    
}
