package searchengine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class InvertedIndexTest {
    private PageLibrary library;
    private InvertedIndex classUnderTest;

    @BeforeAll
    void setUp() {
        // To be tested
        library = new PageLibrary("data/test-file-noTitles.txt", new TFIDF());
        classUnderTest = library.getInvertedIndex();
    }

    @Test
    void mapIndices_TestFile_IndexAllWords() {
        Map<String, Set<WebPage>> testIndex = setUpTestIndex();
        assertEquals(testIndex, classUnderTest.getInvertedIndex());
    }

    @Test 
    void lookUp_NonExistingKey_returnsEmptySet() {
        assertEquals(new HashSet<>(), classUnderTest.lookUp("word4"));
    }

    @Test 
    void lookUp_ExistingKey_returnsMatchingWebPagesSet() {
        Map<String, Set<WebPage>> testIndex = setUpTestIndex();
        assertEquals(testIndex.get("word1"), classUnderTest.lookUp("word1"));
    }

    @Test
    void lookUp_Title_returnsMatchingWebPagesSet() {
        Map<String, Set<WebPage>> testIndex = setUpTestIndex();
        assertEquals(testIndex.get("title2"), classUnderTest.lookUp("title2"));
    }

    private Map<String, Set<WebPage>> setUpTestIndex() {
        HashSet<WebPage> testSetWord1 = new HashSet<>();
        HashSet<WebPage> testSetWord2 = new HashSet<>();
        HashSet<WebPage> testSetWord3 = new HashSet<>();
        testSetWord1.add(library.getWebPages().get(0));
        testSetWord1.add(library.getWebPages().get(1));
        testSetWord2.add(library.getWebPages().get(0));
        testSetWord3.add(library.getWebPages().get(1));
        Map<String, Set<WebPage>> testIndex = new HashMap<>();
        testIndex.put("word1", testSetWord1);
        testIndex.put("word2", testSetWord2);
        testIndex.put("word3", testSetWord3);
        testIndex.put("title1", testSetWord2);
        testIndex.put("title2", testSetWord3);
        return testIndex;
    }
}
