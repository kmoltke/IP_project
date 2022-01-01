package searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class stores information about the individual webpages: URL-link, title
 * and content
 */
public class WebPage {
    // Fields
    private String url;
    private String title;
    private List<String> words;
    private Map<String, Double> scores;

    // Constructors

    /**
     * Creates a WebPage on the basis of a list of strings, structuring it into its
     * separate fields. No validation of input is done; it's the responsibility of
     * the caller.
     *
     * @param webPageInput The collection of Strings a raw web page consists of
     */
    public WebPage(List<String> webPageInput) {
        url = webPageInput.get(0).substring(6);
        title = webPageInput.get(1);
        words = new ArrayList<>();
        words = webPageInput.subList(2, webPageInput.size());
    }

    // Methods

    /**
     * Checks whether the WebPage contains all words in a given string
     * 
     * @param words the List of words to check
     * @return true if the WebPage contains all words and false otherwise
     */
    public boolean containsWords(List<String> words) {
        for (String word : words) {
            if (!this.words.contains(word)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Set scores of unique words on the web page
     * 
     * @param wordScores map of unique words and their score
     */
    public void setScores(Map<String, Double> wordScores) {
        scores = wordScores;
    }

    /**
     * A getter for returning the score of a given word
     * 
     * @param word the word for which the score should be returned
     * @return the score
     */
    public Double getScore(String word) {
        return scores.get(word);
    }

    /**
     * A getter for returning the URL-link of the WebPage object
     *
     * @return Returns the URL of the WebPage object
     */
    public String getUrl() {
        return url;
    }

    /**
     * A getter for returning the title of the WebPage object
     *
     * @return Returns the title of the WebPage object
     */
    public String getTitle() {
        return title;
    }

    /**
     * A getter for returning the words of the WebPage object
     *
     * @return Returns the words of the WebPage object
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * For testing, overriding of Object.equals() and Object.hashCode()
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof WebPage))
            return false;
        WebPage webPage = (WebPage) o;
        return Objects.equals(getUrl(), webPage.getUrl()) && Objects.equals(getTitle(), webPage.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getTitle());
    }

}
