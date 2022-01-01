package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Responsible for parsing a server search query into partitions given use of search operators such as "OR" etc.
 */
public class QueryHandler {

    /**
     * Constructor for the QueryHandler
     */
    public QueryHandler() {
    }

    // Methods

    /**
     * Method for parsing the query
     *
     * @param searchTerm The full search query, un-parsed
     * @return Returns the query parsed
     */
    protected List<List<String>> splitQuery(String searchTerm) {
        List<List<String>> outputList = new ArrayList<>();
        for (String string :
                searchTerm.split("(?:%20)*OR(?:%20)*")) {
            outputList.add(Arrays.asList(string.toLowerCase().split("(?:%20)+")));
        }
        return outputList;
    }

}
