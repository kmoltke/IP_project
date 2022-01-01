package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TFTest {
    private TF classUnderTest;
    private PageLibrary library; 
    private InvertedIndex corpus; 
    
    @Test
    void calculateScore_twoWordDocument_correctTermFrequency() {
        setUp();

        // Create test input 
        List<String> testList = new ArrayList<>();
        testList.add("word1");
        testList.add("word1");
        testList.add("word2");

        // Create expected output 
        Map<String, Double> expected = new HashMap<>();
        expected.put("word1", 2.0 / 3);
        expected.put("word2", 1.0 / 3);

        // Calculate actual output 
        Map<String, Double> actual = classUnderTest.calculateScore(testList, testList.size(), corpus);

        assertEquals(expected, actual);
    }

    private void setUp() {
        classUnderTest = new TF();
        library = new PageLibrary("data/test-file-ranking.txt", classUnderTest);
        corpus = library.getInvertedIndex();
    }

}
