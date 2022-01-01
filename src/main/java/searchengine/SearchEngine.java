package searchengine;

import java.util.List;
import java.util.Set;

/**
 * Responsible for searching the collection of WebPage objects through use of a
 * PageLibrary's collection of WebPages
 */
public class SearchEngine {
    // Fields
    private PageLibrary library;
    private QueryHandler qHandler;
    private Sorter sorter;

    // Constructors

    /**
     * Constructor for the SearchEngine
     *
     * @param dataFilename Source filename of file with raw, initial data
     */
    public SearchEngine(String dataFilename) {
        library = new PageLibrary(dataFilename, new TFIDF());
        qHandler = new QueryHandler();
        sorter = new Sorter();
    }

    // Methods

    /**
     * Search method
     * 
     * @param query the raw query to search the library
     * @return the set of webpages matching the query
     */
    protected Set<WebPage> search(String query) {
        List<List<String>> parsedQuery = qHandler.splitQuery(query);
        Set<WebPage> webPageResults = library.searchIndex(parsedQuery);
        Set<WebPage> sortedWebPageResults = sorter.sortPages(parsedQuery, webPageResults);
        return sortedWebPageResults;
    }

}
