package searchengine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Sorter {
    // Fields
    private Map<WebPage, Double> pageScores;
    private Set<WebPage> rankedPages;

    // Methods

    /**
     * Method for sorting a set of webPages based on a given query.
     * 
     * @param parsedQuery the given query
     * @param webPages    the set of web pages to sort
     * @return
     */
    public Set<WebPage> sortPages(List<List<String>> parsedQuery, Set<WebPage> webPages) {
        setPageScores(parsedQuery, webPages);
        rankPages(pageScores);
        return rankedPages;
    }

    /**
     * Method for mapping webpages and their score. For each query, a total score
     * is calculated by adding scores of each word in the query. If a webpage is
     * the result of more than one query, the maximum query score is mapped.
     * 
     * @param parsedQuery A list of queries with a list of words in each query
     * @param webPages    The webpages that result from the given query
     * @param library     The library in which to look up the score of pages
     */
    private void setPageScores(List<List<String>> parsedQuery, Set<WebPage> webPages) {
        pageScores = new HashMap<>();
        for (WebPage webPage : webPages) {
            double maxScore = 0.0;
            for (List<String> query : parsedQuery) {
                double totalQueryScore = 0.0;
                if (webPage.containsWords(query)) {
                    for (String word : query) {
                        totalQueryScore += webPage.getScore(word);
                    }
                }
                if (totalQueryScore >= maxScore) {
                    maxScore = totalQueryScore;
                    pageScores.put(webPage, maxScore);
                }
            }
        }
    }

    /**
     * Ranks/sorts the list of scored WebPages in descending order.
     * 
     * @param webPageScores A map of webpages and their score
     * @source https://howtodoinjava.com/java/sort/java-sort-map-by-values/,
     *         Read: 2021-12-08
     */
    private void rankPages(Map<WebPage, Double> webPageScores) {
        rankedPages = new LinkedHashSet<>();
        webPageScores.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> rankedPages.add(x.getKey()));
    }

}
