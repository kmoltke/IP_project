package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TFIDFTest {
    private TFIDF classUnderTest;
    private PageLibrary library; 
    private InvertedIndex corpus; 
    
    @Test
    void calculateScore_twoWordDocument_calculatesCorrectTFIDF() {
        setUp();

        // Create test input 
        List<String> testList = new ArrayList<>();
        testList.add("word1");
        testList.add("word2");
        testList.add("word2");

        // Create expected output 
        Map<String, Double> expected = new HashMap<>();
        double tf_1 = 1 / 3.0;        
        double idf_1 = Math.log10(4 / 3.0);
        expected.put("word1", tf_1 * idf_1);
        double tf_2 = 2 / 3.0;
        double idf_2 = Math.log10(4 / 2.0);
        expected.put("word2", tf_2 * idf_2);

        // Calculate actual output 
        Map<String, Double> actual = classUnderTest.calculateScore(testList, library.getSize(), corpus);

        assertEquals(expected, actual);
    }

    private void setUp() {
        classUnderTest = new TFIDF();
        library = new PageLibrary("data/test-file-ranking.txt", classUnderTest);
        corpus = library.getInvertedIndex();
    }
}
