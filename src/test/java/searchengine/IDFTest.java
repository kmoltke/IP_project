package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)


public class IDFTest {
    private IDF classUnderTest;
    private PageLibrary library; 
    private InvertedIndex corpus; 
    
    @Test
    void calculateScore_threeWordDocument_correctInverseDocumentFrequency() {
        setUp();

        // Create test input 
        List<String> testList = new ArrayList<>();
        testList.add("word1");
        testList.add("word2");
        testList.add("word3");

        // Create expected output 
        Map<String, Double> expected = new HashMap<>();
        expected.put("word1", Math.log10(4.0 / 3.0));
        expected.put("word3", Math.log10(4.0 / 2.0));
        expected.put("word2", Math.log10(4.0 / 2.0));

        // Calculate actual output 
        Map<String, Double> actual = classUnderTest.calculateScore(testList, library.getSize(), corpus);

        assertEquals(expected, actual);
    }

    private void setUp() {
        classUnderTest = new IDF();
        library = new PageLibrary("data/test-file-ranking.txt", classUnderTest);
        corpus = library.getInvertedIndex();
    }
}
