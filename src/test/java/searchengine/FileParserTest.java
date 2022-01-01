package searchengine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class FileParserTest {
    private FileParser classUnderTest;

    @BeforeAll
    void setUp() {
        classUnderTest = new FileParser("data/test-file-noTitles.txt");
    }

    /***
     * Test that FileParser only adds pages if they 
     * contain a title and at least one word.
     */
    @Test
    void listPages_ListWithNoTitles_ListOnlyPagesWithTitles() {
        List<List<String>> testArr = listTestPage();
        assertArrayEquals(testArr.toArray(), classUnderTest.getPages().toArray());
    }

    private List<List<String>> listTestPage() {
        List<List<String>> testArr = new ArrayList<>();
        List<String> testPage1 = new ArrayList<>();
        List<String> testPage2 = new ArrayList<>();
        testPage1.add("*PAGE:http://page1.com");
        testPage1.add("title1");
        testPage1.add("word1");
        testPage1.add("word2");
        testArr.add(testPage1);
        testPage2.add("*PAGE:http://page2.com");
        testPage2.add("title2");
        testPage2.add("word1");
        testPage2.add("word3");
        testArr.add(testPage2);
        return testArr;
    }

    /***
     * Test that FileParser reads the lines of the given file correctly.
     */
    @Test
    void getFile_returns_correctContent() {
        FileParser classUnderTest = new FileParser("data/test-file.txt");
        List<String> result = classUnderTest.getLines();
        List<String> expected = new ArrayList<>(Arrays.asList("*PAGE:http://page1.com", "title1", "word1", "word2",
                "*PAGE:http://page2.com", "title2", "word1", "word3"));
        assertEquals(expected, result);
    }

}
