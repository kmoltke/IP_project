package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * InvertedIndex with keys for every occurring word in the collection of WebPages in a PageLibrary, and values of sets
 * each containing WebPage objects that has the word occurring at least once in the body of the WebPage.
 */
public class InvertedIndex {
    // Fields
    private Map<String, Set<WebPage>> invertedIndex;

    // Constructors

    /**
     * Constructor for an InvertedIndex instance, relying on passing an initialized PageLibrary
     *
     * @param library Pass an initialized PageLibrary
     */
    public InvertedIndex() {
        invertedIndex = new HashMap<>();
    }

    // Methods

    /**
     * Building the inverted index, basing it on the words of each WebPage from the PageLibrary's collection of WebPages
     */
    public void mapIndices(List<WebPage> webPages) {
        for (WebPage page : webPages) {
            Set<String> uniqueWords = new HashSet<>(page.getWords());
            uniqueWords.addAll(new HashSet<String>(Arrays.asList(page.getTitle().toLowerCase().split(" "))));
            for (String word : uniqueWords) {
                if (invertedIndex.containsKey(word)) {
                    invertedIndex.get(word).add(page);
                } else {
                    Set<WebPage> pages = new HashSet<>();
                    pages.add(page);
                    invertedIndex.put(word, pages);
                }
            }
        }
    }

    /**
     * Look-up method
     * @param word the word to search for in the invertedIndex
     * @return the set of webpages containing the word
     */
    protected Set<WebPage> lookUp(String word) {
        return invertedIndex.get(word) == null ? new HashSet<>() : invertedIndex.get(word);
    }

    /**
     * A getter for returning the inverted index structure with structured content for searching WebPage objects'
     * bodies of words
     *
     * @return Returns the inverted index structure
     */
    public Map<String, Set<WebPage>> getInvertedIndex() {
        return invertedIndex;
    }

    public List<String> getWordsInIndex() {
        List<String> words = new ArrayList<>();
        for (String word : invertedIndex.keySet()) {
            words.add(word);
        }
        return words;
    }
}
