package searchengine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The PageLibrary structures the semi-structured page data from a FileParser
 * into a collection of WebPages
 */
public class PageLibrary {
    // Fields
    private FileParser parser;
    private InvertedIndex invertedIndex;
    private Ranker ranker;
    private List<WebPage> webPages;
    private int size;

    // Constructors

    /**
     * Constructor for a PageLibrary instance
     *
     * @param dataFilename Source filename of file with raw, initial data
     * @param ranker       An initialized ranker used to score the web pages
     */
    public PageLibrary(String dataFilename, Ranker ranker) {
        parser = new FileParser(dataFilename);
        invertedIndex = new InvertedIndex();
        this.ranker = ranker;
        webPages = new ArrayList<>();
        listWebPages();
        size = webPages.size();
        invertedIndex.mapIndices(webPages);
        setScoresWebPages();
    }

    // Methods

    /**
     * Helper-method for structuring the list of String-sections/lists into WebPage
     * objects
     */
    private void listWebPages() {
        for (List<String> page : parser.getPages()) {
            webPages.add(new WebPage(page));
        }
    }

    /**
     * Method for setting word scores on each WebPage in the library.
     * Updates the field webPagesScores by mapping each WebPage to
     * a map of unique words on the page and the corresponding score.
     */
    private void setScoresWebPages() {
        for (WebPage webPage : webPages) {
            webPage.setScores(ranker.calculateScore(webPage.getWords(), size, invertedIndex));
        }
    }

    /**
     * Method for searching an inverted index of WebPages given a processed
     * query with a list of words
     *
     * @param listOfSearchWords The terms to search for in the inverted index
     * @return A set of WebPages for which the search terms occurs in its body
     */
    public Set<WebPage> searchIndex(List<List<String>> listOfSearchWords) {
        Set<WebPage> webPagesUnion = new HashSet<>();
        for (List<String> searchWords : listOfSearchWords) {
            Set<WebPage> webPagesIntersection = new HashSet<>();
            for (int i = 0; i < searchWords.size(); i++) {
                if (i == 0) {
                    webPagesIntersection.addAll(invertedIndex.lookUp(searchWords.get(i)));
                } else {
                    webPagesIntersection.retainAll(invertedIndex.lookUp(searchWords.get(i)));
                }
            }
            webPagesUnion.addAll(webPagesIntersection);
        }
        return webPagesUnion;
    }

    /**
     * Getter method for returning the collection of WebPage objects from the
     * PageLibrary
     *
     * @return The list of WebPage objects
     */
    protected List<WebPage> getWebPages() {
        return webPages;
    }

    /**
     * Getter method for returning the size of the pagelibrary
     *
     * @return The size of pagelibrary
     */
    protected int getSize() {
        return size;
    }

    /**
     * A getter for returning the inverted index
     *
     * @return Returns the inverted index
     */
    public InvertedIndex getInvertedIndex() {
        return invertedIndex;
    }

}
