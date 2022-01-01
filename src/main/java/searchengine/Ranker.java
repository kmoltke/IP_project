package searchengine;

import java.util.List;
import java.util.Map;

/**
 * Interface for classes that calculate scores of words in either
 * a single document or relative to an entire corpus.
 *
 */
public interface Ranker {

    /**
     * Method for calculating scores for each word in one document
     * in a corpus
     * 
     * @param words      the document's words
     * @param corpusSize the number of documents in the corpus
     * @param corpus     representation of the corpus with unique words and
     *                   a set of documents that contain the given word
     * @return map of unique words and their score
     */
    Map<String, Double> calculateScore(List<String> words, int corpusSize, InvertedIndex corpus);

}
