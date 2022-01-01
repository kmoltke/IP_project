package searchengine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class PageLibraryTest {
    private PageLibrary classUnderTest;

    @BeforeAll
    void setUp() {
        classUnderTest = new PageLibrary("data/test-file-noTitles.txt", new TFIDF());
    }

    @Test
    void listWebPages_ListWithNoTitles_addsWebPageObjectsToList() {
        List<WebPage> expected = listTestPage();
        assertEquals(expected, classUnderTest.getWebPages());
    }

    private List<WebPage> listTestPage() {
        List<WebPage> testList = new ArrayList<>();
        List<String> testPage1 = new ArrayList<>();
        List<String> testPage2 = new ArrayList<>();
        testPage1.add("*PAGE:http://page1.com");
        testPage1.add("title1");
        testPage1.add("word1");
        testPage1.add("word2");
        testList.add(new WebPage(testPage1));
        testPage2.add("*PAGE:http://page2.com");
        testPage2.add("title2");
        testPage2.add("word1");
        testPage2.add("word3");
        testList.add(new WebPage(testPage2));
        return testList;
    }

}
