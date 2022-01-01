package searchengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class QueryHandlerTest {
    /**
     * Test with one string consisting of several words with no "OR"-operator
     */
    @Test
    void splitQuery_queryWithWordsNoOROperator_listOneListOfStrings() {
        // Actual
        String string = "this%20is%20a%20test%20of%20the%20QueryHandler";
        QueryHandler queryHandler = new QueryHandler();

        // Expected
        String[] stringArray = {"this", "is", "a", "test", "of", "the", "queryhandler"};
        List<List<String>> listListString = new ArrayList<>();
        listListString.add(Arrays.asList(stringArray));

        // Test
        assertEquals(listListString, queryHandler.splitQuery(string));
    }

    /**
     * Test with one string with several words and a couple of "OR"-operators surrounded by 1..* "%20" (space).
     */
    @Test
    void splitQuery_queryWithWordsAndOROperator_listSeveralListOfStrings() {
        // Actual
        String string = "this%20is%20a%20OR%20test%20of%20the%20QueryHandler%20%20OR%20%20%20not";
        QueryHandler queryHandler = new QueryHandler();

        // Expected
        String[] stringArray = new String[]{"this", "is", "a"};
        String[] stringArray2 = new String[]{"test", "of", "the", "queryhandler"};
        String[] stringArray3 = new String[]{"not"};
        List<List<String>> listListString = new ArrayList<>();
        listListString.add(Arrays.asList(stringArray));
        listListString.add(Arrays.asList(stringArray2));
        listListString.add(Arrays.asList(stringArray3));

        // Test
        assertEquals(listListString, queryHandler.splitQuery(string));
    }

    /**
     * Test with only a couple of "OR"-operators surrounded by "%20" which should return the null string.
     */
    @Test
    public void splitQuery_queryWithNoWords_emptyListOfListStrings() {
        // Actual
        String string = "%20OR%20%20OR%20";
        QueryHandler queryHandler = new QueryHandler();

        // Expected
        List<List<String>> listListString = new ArrayList<>();

        // Test
        assertEquals(listListString, queryHandler.splitQuery(string));
    }
}
