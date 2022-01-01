package searchengine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// TODO: Make tests

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchEngineTest {

    @Test 
    void rankScoredWebPages_oneWordQuery_returnOrderedPages() {
        SearchEngine classUnderTest = new SearchEngine("data/test-file-ranking.txt");
        String query = "word2";

        // Expected
        Set<WebPage> expected = listTestPages();

        Set<WebPage> actual = classUnderTest.search(query);
        assertEquals(expected, actual);
    }

    private Set<WebPage> listTestPages() {
        Set<WebPage> testSet = new LinkedHashSet<>();
        List<String> testPage1 = new ArrayList<>();
        List<String> testPage3 = new ArrayList<>();
        testPage3.add("*PAGE:http://page3.com");
        testPage3.add("title3");
        testPage3.add("word2");
        testPage3.add("word2");
        testPage3.add("word1");
        testSet.add(new WebPage(testPage3));
        testPage1.add("*PAGE:http://page1.com");
        testPage1.add("title1");
        testPage1.add("word1");
        testPage1.add("word2");
        testSet.add(new WebPage(testPage1));
        return testSet;
    }
}
